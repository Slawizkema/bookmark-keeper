package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramMessageSender {

    public void sendMessage(BotApiMethod sendMessage);

    public void sendMessage(SendDocument sendMessage);

    public void sendMessage(SendPhoto sendMessage);
}
