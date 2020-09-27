package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author slawi
 * @since 27.09.2020
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TelegramResponseTemplate {
    public static final String BOOKMARK_SAVED = "Заметка сохранена!";
    public static final String BOOKMARK_TEMPLATE = "\nТип: %s\nКатегория: %s\nТэги: %s\nТело: \n%s";
    public static final String EMPTY_BOOKMARK_LIST = "Заметок еще нет!";
}
