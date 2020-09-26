package ru.ssharaev.bookmarkkeeper.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BookmarkSaveServiceTest {
    BookmarkSaveService bookmarkSaveService = new BookmarkSaveService();

    @Test
    void checkUrl() {
        String text = "Создание телеграм бота с гугл авторизацией, " +
                "обратными вызовами и уведомлениями об обновлении через " +
                "сервер-маршрутизатор https://infostart.ru/1c/articles/932874/";

        String expectedUrl = "https://infostart.ru/1c/articles/932874/";

        Message message = Mockito.mock(Message.class);
        MessageEntity entity = Mockito.mock(MessageEntity.class);
        Mockito.when(entity.getType()).thenReturn("url");
        Mockito.when(entity.getOffset()).thenReturn(120);
        Mockito.when(entity.getLength()).thenReturn(40);

        List<MessageEntity> entities = List.of(entity);
        Mockito.when(message.getMessageId()).thenReturn(1);
        Mockito.when(message.hasEntities()).thenReturn(true);
        Mockito.when(message.getEntities()).thenReturn(entities);
        Mockito.when(message.getText()).thenReturn(text);

        assertEquals(expectedUrl, bookmarkSaveService.createBookmark(message).getUrl());
    }
}