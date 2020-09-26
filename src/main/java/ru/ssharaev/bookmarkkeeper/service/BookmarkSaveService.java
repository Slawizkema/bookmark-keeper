package ru.ssharaev.bookmarkkeeper.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkType;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Закладываемся на то, что в сервис передается сообщение без команд,
 * только с закладкой, которую нужно сохранить
 */
@Service
public class BookmarkSaveService {


    public void saveBookmark(Update update) {

    }

    public Bookmark createBookmark(Message message) {
        return new Bookmark(
                message.getMessageId().toString(),
                getBookmarkCategory(message),
                getUrl(message),
                message.getText(),
                getTags(message)
        );
    }

    private Set<String> getTags(Message message) {
        return message.getEntities().stream()
                .filter(e -> e.getType().equals("hashtag"))
                .map(e -> message.getText().substring(e.getOffset(), e.getOffset() + e.getLength()))
                .collect(Collectors.toSet());
    }

    private String getUrl(Message message) {
        if (!message.hasEntities()) {
            return null;
        }
        MessageEntity entity = message.getEntities().stream()
                .filter(e -> e.getType().equals("url"))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return message.getText().substring(entity.getOffset(), entity.getOffset() + entity.getLength());
    }

    private BookmarkType getBookmarkCategory(Message message) {
        if (!message.hasEntities()) {
            if (message.hasDocument()) {
                return BookmarkType.FILE;
            }
            if (message.hasPhoto()) {
                return BookmarkType.PHOTO;
            }
            return BookmarkType.TEXT;
        }
        return message.getEntities().stream()
                .map(MessageEntity::getType)
                .filter(e -> e.equals("url"))
                .map(e -> {
                    return BookmarkType.URL;
                })
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
