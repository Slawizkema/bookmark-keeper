package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.BookmarkType;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.opengraph.OpenGraph;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import static org.telegram.telegrambots.meta.api.objects.EntityType.URL;
import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.fetchEntityValue;
import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.hasEntity;

/**
 * Закладываемся на то, что в сервис передается сообщение без команд,
 * только с закладкой, которую нужно сохранить
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookmarkSaveService {
    private final BookmarkTagProvider tagProvider;
    private final BookmarkRepository bookmarkRepository;
    private final CategoryRepository categoryRepository;
    private final TelegramResponseService responseService;

    public void saveBookmark(Message message) {
        log.info("Сохраняем сообщение");
        Bookmark bookmark = createBookmark(message);
        bookmark = bookmarkRepository.save(bookmark);
        bookmarkRepository.flush();
        log.info("Saved bookmark: {}", bookmark);
        responseService.sendSaveResponse(bookmark, message.getChatId(), categoryRepository.findAll());
    }

    public String updateBookmarkCategory(Message message, CallbackData callBackData) throws UnsupportedOperationException {
        BookmarkCategory bookmarkCategory = categoryRepository
                .findById(callBackData.getId())
                .orElseThrow(() -> new UnsupportedOperationException("Нет указанной категории!"));
        String bookmarkMessageId = String.valueOf(message.getReplyToMessage().getMessageId());
        Bookmark bookmark = bookmarkRepository.findBookmarkByMessageIdAndUserId(bookmarkMessageId, message.getChatId());
        bookmarkRepository.updateBookmarkCategory(bookmarkCategory.getId(), bookmark.getId(), message.getChatId());
        return bookmarkCategory.getName();
    }

    public Bookmark createBookmark(Message message) {
        String url = null;
        String title = null;
        String description = null;
        if (hasEntity(message, URL)) {
            url = getUrl(message);
            try {
                OpenGraph openGraph = new OpenGraph(url, true);
                title = openGraph.getContent("title");
                description = openGraph.getContent("description").substring(0, 100) + "...";
            } catch (Exception e) {
                log.error("Не смогли получить данные Opengraph для сообщения: {}", message, e);
                title = null;
                description = null;
            }
        }
        return Bookmark.builder()
                .messageId(message.getMessageId().toString())
                .type(getBookmarkType(message))
                .userId(message.getChatId())
                .category(null)
                .title(title)
                .description(description)
                .url(url)
                .body(message.getText())
                .tags(tagProvider.fetchTag(message))
                .build();
    }

    private String getUrl(Message message) {
        return hasEntity(message, URL) ? fetchEntityValue(message, URL) : null;
    }

    /**
     * Определяет тип закладки. Пока поддерживаем 4 типа - текст, фото, ссылка, документ
     *
     * @param message сообщение клиента
     * @return тип закладки
     */
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
        return hasEntity(message, URL) ? BookmarkType.URL : BookmarkType.TEXT;
    }
}
