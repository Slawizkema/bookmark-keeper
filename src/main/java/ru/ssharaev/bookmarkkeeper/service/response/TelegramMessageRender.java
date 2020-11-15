package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;

import java.util.List;

/**
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramMessageRender {

    SendMessage createBookmarkSendMessage(String messageText, long chatId);

    SendMessage createBookmarkSendMessage(Bookmark bookmark, long chatId);

    SendMessage createSaveBookmarkSendMessage(String messageText, String messageId, long chatId, List<BookmarkCategory> categoryList);
}
