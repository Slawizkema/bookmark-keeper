package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkType;
import ru.ssharaev.bookmarkkeeper.opengraph.OpenGraph;

import java.util.Map;

import static java.util.Objects.isNull;
import static org.telegram.telegrambots.meta.api.objects.EntityType.URL;
import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.fetchEntityValue;
import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.hasEntity;

/**
 * @author slawi
 * @since 22.11.2020
 */
@Slf4j
@Service
public class BookmarkCreatorProvider {
    private final Map<BookmarkType, BookmarkCreator> creatorMap = Map.of(
            BookmarkType.URL, this::createUrlBookmark,
            BookmarkType.FILE, this::createFileBookmark,
            BookmarkType.TEXT, this::createTextBookmark,
            BookmarkType.PHOTO, this::createPhotoBookmark
    );

    public BookmarkCreator getCreator(BookmarkType bookmarkType) {
        return creatorMap.get(bookmarkType);
    }

    public Bookmark createUrlBookmark(Message message) {
        String url = getUrl(message);
        String title = null;
        String description = null;
        try {
            OpenGraph openGraph = new OpenGraph(url, true);
            title = openGraph.getContent("title");
            description = openGraph.getContent("description").substring(0, 100) + "...";
        } catch (Exception e) {
            log.error("Не смогли получить данные Opengraph для сообщения: {}", message, e);
        }
        return new Bookmark()
                .setTitle(title)
                .setDescription(description)
                .setUrl(url);
    }

    public Bookmark createFileBookmark(Message message) {
        if (isNull(message.getDocument())) {
            return new Bookmark();
        }
        return new Bookmark()
                .setTitle(message.getDocument().getFileName())
                .setFileId(message.getDocument().getFileId());
    }

    public Bookmark createTextBookmark(Message message) {
        return new Bookmark();
    }

    public Bookmark createPhotoBookmark(Message message) {
        PhotoSize photoSize = message.getPhoto().get(0);
        return new Bookmark()
                .setFileId(photoSize.getFileId());
    }


    private String getUrl(Message message) {
        return hasEntity(message, URL) ? fetchEntityValue(message, URL) : null;
    }
}
