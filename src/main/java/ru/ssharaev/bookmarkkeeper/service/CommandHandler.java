package ru.ssharaev.bookmarkkeeper.service;

import org.telegram.telegrambots.meta.api.objects.Update;

@FunctionalInterface
public interface CommandHandler {
    void handleCommand(Update update);
}
