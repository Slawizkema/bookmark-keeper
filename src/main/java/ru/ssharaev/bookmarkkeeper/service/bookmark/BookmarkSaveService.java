package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.BookmarkType;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;

import java.util.NoSuchElementException;

import static org.telegram.telegrambots.meta.api.objects.EntityType.URL;
import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.hasEntity;

/**
 * Закладываемся на то, что в сервис передается сообщение без команд,
 * только с закладкой, которую нужно сохранить
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookmarkSaveService {
    private final BookmarkTagProvider tagProvider;
    private final BookmarkRepository bookmarkRepository;
    private final CategoryRepository categoryRepository;
    private final BookmarkCreatorProvider bookmarkCreatorProvider;

    public Bookmark saveBookmark(Message message) {
        log.info("Сохраняем сообщение");
        Bookmark bookmark = createBookmark(message);
        bookmark = bookmarkRepository.save(bookmark);
        log.info("Saved bookmark: {}", bookmark);
        return bookmark;
    }

    public Bookmark updateBookmarkCategory(String bookmarkMessageId, Long userId, long categoryId) throws UnsupportedOperationException {
        Bookmark bookmark = bookmarkRepository.findBookmarkByMessageIdAndUserId(bookmarkMessageId, userId);
        BookmarkCategory bookmarkCategory = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new UnsupportedOperationException("Нет указанной категории!"));
        bookmark.setCategory(bookmarkCategory);
        return bookmark;
    }

    public void deleteBookmark(String messageId, Long userId) throws NoSuchElementException {
        Bookmark bookmark = bookmarkRepository.findByMessageIdAndUserId(messageId, userId).orElseThrow();
        bookmarkRepository.delete(bookmark);
    }

    public Bookmark createBookmark(Message message) {
        BookmarkType bookmarkType = getBookmarkType(message);
        Bookmark bookmark = bookmarkCreatorProvider.getCreator(bookmarkType).create(message);
        return bookmark
                .setType(bookmarkType)
                .setMessageId(message.getMessageId().toString())
                .setUserId(message.getChatId())
                .setBody(message.getText())
                .setTags(tagProvider.fetchTag(message));
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
