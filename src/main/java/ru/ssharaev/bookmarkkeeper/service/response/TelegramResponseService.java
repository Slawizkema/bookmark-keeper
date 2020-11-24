package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.*;

import java.util.List;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.*;

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
    private final BookmarkMessageRendererProvider messageRendererProvider;

    public void sendTextMessage(long chatId, String text) {
        messageSender.sendMessage(new SendMessage(chatId, text));
    }

    public void sendSaveResponse(long chatId, Bookmark bookmark, List<BookmarkCategory> categoryList) {
        messageSender.sendMessage(messageRender.createSaveBookmarkSendMessage(chatId, bookmark.getMessageId(), categoryList));
    }

    public void sendFindByCategoryResponse(long chatId, List<BookmarkCategory> bookmarkCategories) {
        messageSender.sendMessage(messageRender.createFindByCategoryMessage(chatId, bookmarkCategories));
    }

    public void sendFindByTagResponse(long chatId, List<Tag> bookmarkTagList) {
        messageSender.sendMessage(messageRender.createFindByTagMessage(chatId, bookmarkTagList));
    }

    public void sendDeleteKeyboard(CallbackQuery callbackQuery) {
        messageSender.sendMessage(messageRender.createDeleteKeyboardMessage(callbackQuery));
    }

    private void sendAnswerCallback(CallbackQuery callbackQuery, String messageText) {
        messageSender.sendMessage(messageRender.createAnswerCallbackQuery(callbackQuery, messageText));
    }

    public void sendUpdateBookmarkAnswer(CallbackQuery callbackQuery, Bookmark bookmark) {
        messageSender.sendMessage(messageRender.createEditMessageTextMessage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), bookmark));
    }

    public void sendBookmark(long chatId, Bookmark bookmark) {
        switch (bookmark.getType()) {
            case FILE:
                messageSender.sendMessage((SendDocument) messageRender.createBookmarkSendMessage(chatId, bookmark));
                break;
            case PHOTO:
                messageSender.sendMessage((SendPhoto) messageRender.createBookmarkSendMessage(chatId, bookmark));
                break;
            default:
                messageSender.sendMessage((SendMessage) messageRender.createBookmarkSendMessage(chatId, bookmark));
        }
    }

    public void sendSaveBookmarkAnswerCallback(CallbackQuery callbackQuery) {
        sendAnswerCallback(callbackQuery, BOOKMARK_SAVED);
    }

    public void sendShowBookmarkAnswerCallback(CallbackQuery callbackQuery) {
        sendAnswerCallback(callbackQuery, SHOW_BOOKMARK);
    }

    public void sendBookmarkListPage(Long chatId, PaginationBookmarkList page, CallbackType callbackType) {
        messageSender.sendMessage(messageRender.createPaginationBookmarkList(chatId, page, callbackType));
    }

    public void sendUpdateBookmarkListPage(CallbackQuery callbackQuery, PaginationBookmarkList page, CallbackType callbackType) {
        messageSender.sendMessage(messageRender.updatePaginationBookmarkList(callbackQuery, page, callbackType));
    }

    public void sendDeleteMessage(CallbackQuery callbackQuery) {
        messageSender.sendMessage(messageRender.createDeleteMessage(callbackQuery));
    }
}
