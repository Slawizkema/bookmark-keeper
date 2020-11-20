package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.Tag;

import java.util.List;

/**
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramMessageRender {

    SendMessage createBookmarkSendMessage(String messageText, long chatId);

    SendMessage createBookmarkSendMessage(Bookmark bookmark, long chatId);

    SendMessage createFindByCategoryMessage(List<BookmarkCategory> categoryList, long chatId);

    SendMessage createSaveBookmarkSendMessage(String messageText, String messageId, long chatId, List<BookmarkCategory> categoryList);

    EditMessageReplyMarkup createDeleteKeyboardMessage(CallbackQuery callbackQuery);

    EditMessageText createEditMessageTextMessage(CallbackQuery callbackQuery, String messageText);

    SendMessage createFindByTagMessage(List<Tag> bookmarkTagList, long chatId);

    SendMessage createSimpleBookmarkList(List<Bookmark> bookmarkList, long chatId);

}
