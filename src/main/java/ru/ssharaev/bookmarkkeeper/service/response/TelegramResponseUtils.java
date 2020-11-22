package ru.ssharaev.bookmarkkeeper.service.response;

import com.vdurmont.emoji.EmojiParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author slawi
 * @since 27.09.2020
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TelegramResponseUtils {
    public static int PAGE_SIZE = 3;

    public static final String BOOKMARK_SAVED = "Заметка сохранена!" + EmojiParser.parseToUnicode(":ok_hand:");
    public static final String CATEGORY_SAVED = "Категория сохранена!" + EmojiParser.parseToUnicode(":ok_hand:") + "\n%s";
    public static final String BOOKMARK_CATEGORY = "Категория: %s\n";
    public static final String SELECT_CATEGORY = "\nВыберите категорию:\n";
    public static final String SELECT_TAG = "\nВыберите тэг:\n";
    public static final String EMPTY_BOOKMARK_LIST = "Таких заметок еще нет! ";
    public static final String BOOKMARK_LIST_TEMPLATE = "%s %s\n\n";
    public static final String SHOW_BOOKMARK = "Держи!";
    public static final String BOOKMARK_LIST_TITLE_MESSAGE = "Найденные заметки:";
    public static final String START_MESSAGE = "Привет! " + EmojiParser.parseToUnicode(":wave:");
    public static final String NEXT_PAGE = EmojiParser.parseToUnicode(":arrow_forward:");
    public static final String PREV_PAGE = EmojiParser.parseToUnicode(":arrow_backward:");

    public static final Map<Integer, String> DIGIT = Map.of(
            0, ":zero:",
            1, ":one:",
            2, ":two:",
            3, ":three:",
            4, ":four:",
            5, ":five:",
            6, ":six:",
            7, ":seven:",
            8, ":eight:",
            9, ":nine:"
    );


    public static final List<String> START_CATEGORY = List.of("Личное", "Учеба", "Работа");


    public static String renderEmojiNumber(int num) {
        return Arrays.stream(String.valueOf(num)
                .split(""))
                .map(Integer::parseInt)
                .map(DIGIT::get)
                .map(EmojiParser::parseToUnicode)
                .collect(Collectors.joining(""));
    }

}
