package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
public class TelegramMessageConstructorImpl implements TelegramMessageConstructor {
    @Override
    public SendMessage createSendMessage(String messageText, long chatId) {
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(chatId);
        replyMessageToUser.setText(messageText);
        return replyMessageToUser;
    }
}
