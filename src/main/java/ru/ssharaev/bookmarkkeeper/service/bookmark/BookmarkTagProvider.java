package ru.ssharaev.bookmarkkeeper.service.bookmark;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ssharaev.bookmarkkeeper.model.Tag;

import java.util.Set;

/**
 *
 * @author slawi
 * @since 26.09.2020
 */
public interface BookmarkTagProvider {

    Set<Tag> fetchTag(Message message);
}
