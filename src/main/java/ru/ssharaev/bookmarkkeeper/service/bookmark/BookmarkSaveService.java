package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkType;

import java.util.Set;

/**
 * Закладываемся на то, что в сервис передается сообщение без команд,
 * только с закладкой, которую нужно сохранить
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookmarkSaveService {
    private final BookmarkTagProvider tagProvider;

    public Bookmark saveBookmark(Message message) {
        log.info("Сохраняем сообщение");
        Bookmark bookmark = createBookmark(message);
        log.info("Saved bookmark: {}", bookmark);
        return bookmark;
    }

    public Bookmark createBookmark(Message message) {
        return new Bookmark(
                message.getMessageId().toString(),
                getBookmarkType(message),
                "Test",
                getUrl(message),
                message.getText(),
                getTags(message)
        );
    }

    private Set<String> getTags(Message message) {
        return tagProvider.fetchTag(message);
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

    private BookmarkType getBookmarkType(Message message) {
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
