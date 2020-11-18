package ru.ssharaev.bookmarkkeeper.service.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;
import ru.ssharaev.bookmarkkeeper.service.bookmark.BookmarkSaveService;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SaveCallbackHandler implements CallbackHandler {
    private final BookmarkSaveService bookmarkSaveService;
    private final TelegramResponseService responseService;

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SAVE;
    }

    // TODO:
    //  менять сообщение, а не отправлять второе
    //  второе сообщение отправляется с пустой категорией
    @Override
    public void handle(CallbackQuery callbackQuery, CallbackData callbackData) {
        try {
            bookmarkSaveService.updateBookmarkCategory(callbackQuery.getMessage(), callbackData);
        } catch (UnsupportedOperationException e) {
            sendErrorMessage(callbackQuery, e);
        }
        responseService.sendDeleteKeyboard(callbackQuery);
    }

    //TODO заменить на ExceptionHandler
    private void sendErrorMessage(CallbackQuery callbackQuery, UnsupportedOperationException e) {
        log.error("Ошибка в CallbackQuery {}", callbackQuery, e);
        responseService.sendTextMessage("Категория не найдена, повторите попытку!", callbackQuery.getMessage().getChatId());
    }

}
