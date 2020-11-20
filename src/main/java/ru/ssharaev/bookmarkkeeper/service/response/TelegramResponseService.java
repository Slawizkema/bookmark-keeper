package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.Tag;

import java.util.List;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseTemplate.*;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramResponseService {
    private final TelegramMessageRender messageRender;
    private final TelegramMessageSender messageSender;

    public void sendTextMessage(String text, long chatId) {
        messageSender.sendMessage(messageRender.createBookmarkSendMessage(text, chatId));
    }

    public void sendSaveResponse(Bookmark bookmark, long chatId, List<BookmarkCategory> categoryList) {
        messageSender.sendMessage(messageRender.createSaveBookmarkSendMessage(SELECT_CATEGORY, bookmark.getMessageId(), chatId, categoryList));
    }

    //TODO добавить пагинацию
    public void sendBookmarkList(List<Bookmark> bookmarkList, long chatId) {
        messageSender.sendMessage(messageRender.createSimpleBookmarkList(bookmarkList, chatId));
    }

    public void sendFindByCategoryResponse(List<BookmarkCategory> bookmarkCategories, long chatId) {
        messageSender.sendMessage(messageRender.createFindByCategoryMessage(bookmarkCategories, chatId));
    }

    public void sendFindByTagResponse(List<Tag> bookmarkTagList, long chatId) {
        messageSender.sendMessage(messageRender.createFindByTagMessage(bookmarkTagList, chatId));
    }

    public void sendDeleteKeyboard(CallbackQuery callbackQuery) {
        messageSender.sendEditMessageReplyMarkup(messageRender.createDeleteKeyboardMessage(callbackQuery));
    }

    public void sendUpdateBookmarkAnswer(CallbackQuery callbackQuery, String bookmarkName) {
        String messageText = String.format(
                BOOKMARK_SAVED + BOOKMARK_CATEGORY,
                bookmarkName);
        messageSender.sendMessage(messageRender.createEditMessageTextMessage(callbackQuery, messageText));
    }
}
