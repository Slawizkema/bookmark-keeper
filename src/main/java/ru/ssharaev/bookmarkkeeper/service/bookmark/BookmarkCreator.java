package ru.ssharaev.bookmarkkeeper.service.bookmark;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;

/**
 * @author slawi
 * @since 22.11.2020
 */
@FunctionalInterface
public interface BookmarkCreator {

    Bookmark create(Message message);
}
