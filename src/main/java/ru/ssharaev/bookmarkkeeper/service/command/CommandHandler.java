package ru.ssharaev.bookmarkkeeper.service.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.CommandType;


public interface CommandHandler {
    CommandType getType();

    void handleCommand(Update update);
}
