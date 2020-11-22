package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ssharaev.bookmarkkeeper.model.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.*;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramMessageRender {
    private final ButtonRenderer buttonRenderer;
    private final TextRenderer textRenderer;
    private final BookmarkMessageRendererProvider messageRendererProvider;

    public PartialBotApiMethod<Message> createBookmarkSendMessage(long chatId, Bookmark bookmark) {
        return messageRendererProvider.getRenderer(bookmark.getType()).renderBookmark(bookmark, chatId);
    }

    public SendMessage createFindByCategoryMessage(long chatId, List<BookmarkCategory> categoryList) {
        SendMessage replyMessageToUser = new SendMessage(chatId, SELECT_CATEGORY);

        List<List<InlineKeyboardButton>> buttons = categoryList.stream()
                .map(e -> List.of(buttonRenderer.getCategoryButton(e, CallbackType.CATEGORY)))
                .collect(Collectors.toList());
        replyMessageToUser.setReplyMarkup(new InlineKeyboardMarkup(buttons));
        return replyMessageToUser;
    }


    public SendMessage createFindByTagMessage(long chatId, List<Tag> bookmarkTagList) {
        SendMessage replyMessageToUser = new SendMessage(chatId, SELECT_TAG);

        List<List<InlineKeyboardButton>> buttons = bookmarkTagList.stream()
                .map(tag -> List.of(buttonRenderer.getTagButton(tag)))
                .collect(Collectors.toList());
        replyMessageToUser.setReplyMarkup(new InlineKeyboardMarkup(buttons));
        return replyMessageToUser;
    }


    public SendMessage createSaveBookmarkSendMessage(long chatId, String messageText, String messageId, List<BookmarkCategory> categoryList) {
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);

        List<List<InlineKeyboardButton>> buttons = categoryList.stream()
                .map(e -> List.of(buttonRenderer.getCategoryButton(e, CallbackType.SAVE)))
                .collect(Collectors.toList());

        return new SendMessage(chatId, SELECT_CATEGORY)
                .setReplyToMessageId(Integer.valueOf(messageId))
                .setReplyMarkup(new InlineKeyboardMarkup(buttons));
    }


    public EditMessageReplyMarkup createDeleteKeyboardMessage(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup messageReplyMarkup = new EditMessageReplyMarkup();
        messageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
        messageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        messageReplyMarkup.setReplyMarkup(null);
        return messageReplyMarkup;
    }


    public DeleteMessage createDeleteMessage(CallbackQuery callbackQuery) {
        return new DeleteMessage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId());
    }


    public EditMessageText createEditMessageTextMessage(long chatId, long editedMessageId, Bookmark bookmark) {
        return new EditMessageText()
                .setChatId(chatId)
                .setMessageId((int) editedMessageId)
                .setText(textRenderer.printFullBookmark(bookmark))
                .disableWebPagePreview();
    }


    public SendMessage createPaginationBookmarkList(long chatId, PaginationBookmarkList paginationBookmarkList, CallbackType callbackType) {

        List<Bookmark> bookmarkList = paginationBookmarkList.getBookmarkList();

        if (bookmarkList.isEmpty()) {
            return new SendMessage(chatId, EMPTY_BOOKMARK_LIST);
        }

        String messageText = textRenderer.renderBookmarkList(paginationBookmarkList);

        List<InlineKeyboardButton> inlineBookmarkList = buttonRenderer.getBookmarkButtonList(paginationBookmarkList);
        List<InlineKeyboardButton> inlinePaginationKeyboard = buttonRenderer.createInlinePaginationKeyboard(paginationBookmarkList, callbackType);

        InlineKeyboardMarkup inlineKeyboardMarkup = Objects.isNull(inlinePaginationKeyboard) ? new InlineKeyboardMarkup(List.of(inlineBookmarkList))
                : new InlineKeyboardMarkup(List.of(inlineBookmarkList, inlinePaginationKeyboard));
        return new SendMessage(chatId, messageText)
                .disableWebPagePreview()
                .setReplyMarkup(inlineKeyboardMarkup);
    }


    public EditMessageText updatePaginationBookmarkList(CallbackQuery callbackQuery, PaginationBookmarkList paginationBookmarkList, CallbackType callbackType) {

        List<Bookmark> bookmarkList = paginationBookmarkList.getBookmarkList();

        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        if (bookmarkList.isEmpty()) {
            return new EditMessageText()
                    .setChatId(chatId)
                    .setMessageId(messageId)
                    .setText(EMPTY_BOOKMARK_LIST);
        }

        String messageText = textRenderer.renderBookmarkList(paginationBookmarkList);

        List<InlineKeyboardButton> inlineBookmarkList = buttonRenderer.getBookmarkButtonList(paginationBookmarkList);
        List<InlineKeyboardButton> inlinePaginationKeyboard = buttonRenderer.createInlinePaginationKeyboard(paginationBookmarkList, callbackType);

        return new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(messageText)
                .disableWebPagePreview()
                .setReplyMarkup(new InlineKeyboardMarkup(List.of(inlineBookmarkList, inlinePaginationKeyboard)));
    }

    public AnswerCallbackQuery createAnswerCallbackQuery(CallbackQuery callbackQuery, String messageText) {
        return new AnswerCallbackQuery()
                .setText(messageText)
                .setCallbackQueryId(callbackQuery.getId());
    }
}
