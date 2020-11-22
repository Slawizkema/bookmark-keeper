package ru.ssharaev.bookmarkkeeper.service.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.service.bookmark.BookmarkSaveService;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SaveBookmarkCallbackHandler implements CallbackHandler {
    private final BookmarkSaveService bookmarkSaveService;
    private final TelegramResponseService responseService;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SAVE;
    }

    @Override
    public void handle(CallbackQuery callbackQuery, CallbackData callbackData) {
        try {
            String bookmarkMessageId = String.valueOf(callbackQuery.getMessage().getReplyToMessage().getMessageId());
            Bookmark bookmark = bookmarkSaveService.updateBookmarkCategory(bookmarkMessageId, callbackQuery.getMessage().getChatId(), callbackData.getId());
            responseService.sendUpdateBookmarkAnswer(callbackQuery, bookmark);
            responseService.sendSaveBookmarkAnswerCallback(callbackQuery);
        } catch (UnsupportedOperationException e) {
            sendErrorMessage(callbackQuery, e);
        }
    }

    //TODO заменить на ExceptionHandler
    private void sendErrorMessage(CallbackQuery callbackQuery, UnsupportedOperationException e) {
        log.error("Ошибка в CallbackQuery {}", callbackQuery, e);
        responseService.sendTextMessage(callbackQuery.getMessage().getChatId(), "Категория не найдена, повторите попытку!");
    }

}
