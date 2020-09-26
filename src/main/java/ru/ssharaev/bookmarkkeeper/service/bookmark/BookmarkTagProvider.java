package ru.ssharaev.bookmarkkeeper.service.bookmark;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

/**
 * TODO: в тэги добавляем: тэги сообщения, тэги страницы (с хабра, ютуба и т.п.) и сам сайт в качестве тэга
 *
 * @author slawi
 * @since 26.09.2020
 */
public interface BookmarkTagProvider {

    public Set<String> fetchTag(Message message);
}
