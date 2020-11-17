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
import ru.ssharaev.bookmarkkeeper.exception.UnknownCommandException;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
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
public class TelegramUpdateHandlerImpl implements TelegramUpdateHandler {
    private final CommandService commandService;
    private final BookmarkSaveService bookmarkSaveService;
    private final TelegramResponseService responseService;
    private final CallbackHandlerProvider callbackHandlerProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                handleCallback(update.getCallbackQuery());
            } catch (UnknownCommandException e) {
                sendErrorMessage(update);
                e.printStackTrace();
            }
            return;
        }
        if (hasEntity(update.getMessage(), EntityType.BOTCOMMAND)) {
            try {
                commandService.handleCommand(update);
            } catch (UnknownCommandException e) {
                sendErrorMessage(update);
                e.printStackTrace();
            }
        } else {
            handleSaveBookmark(update.getMessage());
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) throws UnknownCommandException {

        CallbackData callbackData = CallbackData.fromJson(callbackQuery.getData(), objectMapper);
        if (callbackData == null) {
            log.error("Некорректные данные в CallbackQuery {}", callbackQuery);
            throw new UnknownCommandException("Невозможно обработать CallbackQuery " + callbackQuery.toString());
        }
        callbackHandlerProvider.getCallbackHandler(callbackData.getCallbackType()).handle(callbackQuery, callbackData);
    }

    private void handleSaveBookmark(Message message) {
        bookmarkSaveService.saveBookmark(message);
    }

    //TODO заменить на ExceptionHandler
    private void sendErrorMessage(Update update) {
        log.error("Ошибка в Update {}", update);
        responseService.sendTextMessage("Команда не найдена, повторите попытку!", update.getMessage().getChatId());
    }
}
