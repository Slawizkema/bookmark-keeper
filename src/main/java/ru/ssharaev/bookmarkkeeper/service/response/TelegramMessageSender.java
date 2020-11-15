package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramMessageSender {

    public void sendMessage(SendMessage sendMessage);

    public void sendAnswerCallBackQuery(AnswerCallbackQuery answer);
}
