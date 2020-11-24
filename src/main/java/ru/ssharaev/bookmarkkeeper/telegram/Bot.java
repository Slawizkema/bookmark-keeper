package ru.ssharaev.bookmarkkeeper.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(onMethod = @__(@Override))
public class Bot extends TelegramWebhookBot {
    private final String botUsername;
    private final String botToken;
    private final String botPath;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(update.getMessage().getChatId());
        replyMessageToUser.setText(update.getMessage().getText());
        sendMessage(replyMessageToUser);
        return replyMessageToUser;
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
