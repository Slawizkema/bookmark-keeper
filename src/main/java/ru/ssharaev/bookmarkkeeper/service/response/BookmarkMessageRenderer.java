package ru.ssharaev.bookmarkkeeper.service.response;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;

/**
 * @author slawi
 * @since 22.11.2020
 */
@FunctionalInterface
public interface BookmarkMessageRenderer {

    PartialBotApiMethod<Message> renderBookmark(Bookmark bookmark, long chatId);
}
