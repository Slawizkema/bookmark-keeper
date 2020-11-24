package ru.ssharaev.bookmarkkeeper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;
import ru.ssharaev.bookmarkkeeper.service.bookmark.BookmarkSaveService;
import ru.ssharaev.bookmarkkeeper.service.callback.CallbackHandlerProvider;
import ru.ssharaev.bookmarkkeeper.service.command.CommandService;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;
import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.hasEntity;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramUpdateHandler {

    private final CommandService commandService;
    private final BookmarkSaveService bookmarkSaveService;
    private final TelegramResponseService responseService;
    private final CallbackHandlerProvider callbackHandlerProvider;
    private final ObjectMapper objectMapper;
    private final CategoryRepository categoryRepository;

    public void handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                handleCallback(update.getCallbackQuery());
            } catch (BookmarkKeeperException e) {
                sendErrorMessage(update, e.getMessage());
                return;
            }
            return;
        }
        if (hasEntity(update.getMessage(), EntityType.BOTCOMMAND)) {
            try {
                commandService.handleCommand(update);
            } catch (BookmarkKeeperException e) {
                sendErrorMessage(update, e.getMessage());
                return;
            }
        }
        handleSaveBookmark(update.getMessage());
    }

    private void handleCallback(CallbackQuery callbackQuery) throws BookmarkKeeperException {

        CallbackData callbackData = CallbackData.fromJson(callbackQuery.getData(), objectMapper);
        if (callbackData == null) {
            log.error("Некорректные данные в CallbackQuery {}", callbackQuery);
            throw new BookmarkKeeperException("Не смогли обработать команду");
        }
        callbackHandlerProvider.getCallbackHandler(callbackData.getType()).handle(callbackQuery, callbackData);
    }

    private void handleSaveBookmark(Message message) {
        Bookmark bookmark = bookmarkSaveService.saveBookmark(message);
        responseService.sendSaveResponse(message.getChatId(), bookmark, categoryRepository.findByUserId(message.getChatId()));
    }

    private void sendErrorMessage(Update update, String message) {
        log.error("Ошибка в Update {}", update);
        responseService.sendTextMessage(update.getMessage().getChatId(), message);
    }
}
