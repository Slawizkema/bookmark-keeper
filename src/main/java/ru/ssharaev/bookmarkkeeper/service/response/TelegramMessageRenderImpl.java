package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
public class TelegramMessageRenderImpl implements TelegramMessageRender {
    @Override
    public SendMessage createSendMessage(String messageText, long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("OK!");
        button.setSwitchInlineQuery(String.format("blabla essageId: chatId: %s", chatId));
        row.add(button);
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(chatId);
        replyMessageToUser.setText(messageText);
        replyMessageToUser.setReplyMarkup(inlineKeyboardMarkup);
        return replyMessageToUser;
    }
}
