package ru.ssharaev.bookmarkkeeper.service;


import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обрабатывает входящие сообщения
 *
 * @author slawi
 * @since 26.09.2020
 */
public interface TelegramUpdateHandler {

    public void handleUpdate(Update update);
}
