package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ssharaev.bookmarkkeeper.telegram.Bot;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramMessageSenderImpl implements TelegramMessageSender {
    private final Bot bot;

    @Override
    public void sendMessage(BotApiMethod sendMessage) {
        log.info("Try to send message {}", sendMessage);
        try {
            bot.execute(sendMessage);
            log.info("Message sent!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(SendDocument sendMessage) {
        log.info("Try to send message {}", sendMessage);
        try {
            bot.execute(sendMessage);
            log.info("Message sent!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(SendPhoto sendMessage) {
        log.info("Try to send message {}", sendMessage);
        try {
            bot.execute(sendMessage);
            log.info("Message sent!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
