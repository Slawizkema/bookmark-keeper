package ru.ssharaev.bookmarkkeeper.service.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.UnknownCommandException;
import ru.ssharaev.bookmarkkeeper.model.CommandType;

import java.util.NoSuchElementException;


public interface CommandHandler {
    CommandType getType();

    void handleCommand(Update update) throws UnknownCommandException;

    default String fetchArg(Message message) throws NoSuchElementException {
        MessageEntity commandEntity = message.getEntities().stream()
                .filter(e -> e.getType().equals("bot_command")).findAny()
                .orElseThrow();
        return message.getText().substring(commandEntity.getOffset() + commandEntity.getLength()).strip();
    }
}
