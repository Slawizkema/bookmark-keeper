package ru.ssharaev.bookmarkkeeper.service.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ssharaev.bookmarkkeeper.model.*;

import java.util.ArrayList;
import java.util.List;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseTemplate.*;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramMessageRenderImpl implements TelegramMessageRender {
    private final ObjectMapper objectMapper;

    @Override
    public SendMessage createBookmarkSendMessage(String messageText, long chatId) {
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(chatId);
        replyMessageToUser.setText(messageText);
        return replyMessageToUser;
    }

    @Override
    public SendMessage createBookmarkSendMessage(Bookmark bookmark, long chatId) {
        SendMessage message = new SendMessage();
        String messageText = String.format(
                BOOKMARK_TEMPLATE + SELECT_CATEGORY,
                bookmark.getType(),
                bookmark.getCategory(),
                bookmark.getTags(),
                bookmark.getBody());
        message.setChatId(chatId);
        message.setText(messageText);
        return message;
    }

    @Override
    public SendMessage createFindByCategoryMessage(List<BookmarkCategory> categoryList, long chatId) {
        SendMessage replyMessageToUser = new SendMessage(chatId, SELECT_CATEGORY);
        replyMessageToUser.setReplyMarkup(createInlineKeyboard(CallbackType.CATEGORY, categoryList, null));
        return replyMessageToUser;
    }

    @Override
    public SendMessage createSaveBookmarkSendMessage(String messageText, String messageId, long chatId, List<BookmarkCategory> categoryList) {
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(chatId);
        replyMessageToUser.setText(messageText);
        replyMessageToUser.setReplyMarkup(createInlineKeyboard(CallbackType.SAVE, categoryList, messageId));
        return replyMessageToUser;
    }

    @Override
    public EditMessageReplyMarkup createDeleteKeyboardMessage(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup messageReplyMarkup = new EditMessageReplyMarkup();
        messageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
        messageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        messageReplyMarkup.setReplyMarkup(null);
        return messageReplyMarkup;
    }

    @Override
    public SendMessage createFindByTagMessage(List<Tag> bookmarkTagList, long chatId) {
        SendMessage replyMessageToUser = new SendMessage(chatId, SELECT_TAG);
        replyMessageToUser.setReplyMarkup(createTagInlineKeyboard(CallbackType.BY_TAG, bookmarkTagList, null));
        return replyMessageToUser;
    }

    private InlineKeyboardMarkup createInlineKeyboard(CallbackType callbackType, List<BookmarkCategory> categoryList, String messageId) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (BookmarkCategory it : categoryList) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText(it.getName()).setCallbackData(
                    CallbackData.toJson(new CallbackData(messageId, callbackType, it.getId(), 0), objectMapper));
            row.add(inlineKeyboardButton);
        }
        return new InlineKeyboardMarkup(List.of(row));
    }

    private InlineKeyboardMarkup createTagInlineKeyboard(CallbackType callbackType, List<Tag> tagListList, String messageId) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (Tag it : tagListList) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText(it.getName()).setCallbackData(
                    CallbackData.toJson(new CallbackData(messageId, callbackType, 0, it.getId()), objectMapper));
            row.add(inlineKeyboardButton);
        }
        return new InlineKeyboardMarkup(List.of(row));
    }

}
