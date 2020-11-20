package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

/**
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramMessageSender {

    public void sendMessage(SendMessage sendMessage);

    default void sendMessage(EditMessageText message) {

    }

    ;

    public void sendAnswerCallBackQuery(AnswerCallbackQuery answer);

    public void sendEditMessageReplyMarkup(EditMessageReplyMarkup answer);
}
