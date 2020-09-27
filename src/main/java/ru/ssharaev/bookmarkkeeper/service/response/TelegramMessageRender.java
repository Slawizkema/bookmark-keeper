package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramMessageRender {

    public SendMessage createSendMessage(String messageText, long chatId);
}
