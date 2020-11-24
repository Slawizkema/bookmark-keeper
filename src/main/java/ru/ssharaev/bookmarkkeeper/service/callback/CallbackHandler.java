package ru.ssharaev.bookmarkkeeper.service.callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;

/**
 * @author slawi
 * @since 17.11.2020
 */
public interface CallbackHandler {
    CallbackType getCallbackType();

    void handle(CallbackQuery callbackQuery, CallbackData callbackData) throws BookmarkKeeperException;
}
