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

    public static final String START_MESSAGE = "Привет! " + EmojiParser.parseToUnicode(":wave:") +
            "Я могу помочь тебе хранить твои закладки. Я умею сохранять текст, ссылки, файлы и картинки.\n" +
            "Чтобы сохранить закладку, просто пришли ее в чат и выбери категорию. " +
            "Команда /all покажет все закладки, с помощью команд /categories и /tag " +
            "можно выбрать закладки определенной категории или тэга, а команда /feeling_lucky покажет случайную закладку.\n" +
            "Подробнее про команды и мои возможности можно узнать здесь: /help";

    public static final String HELP_MESSAGE = "Чтобы сохранить закладку, просто пришли ее в чат, после чего выбери категорию.\n" +
            "Команды:" +
            "/all - показывает все закладки.\n" +
            "/categories - покажет все категории, нажав на которые можно посмотреть закладки выбранной категории.\n" +
            "/tag - - покажет все тэги, нажав на которые можно посмотреть закладки выбранной категории.\n" +
            "/feeling_lucky - покажет случайную закладку\n" +
            "/add_categories - добавляет новую категорию. Для добавления введи название команды и новую категорию через пробел. " +
            "Пример: '/add_category категория'\n" +
            "/delete - удаляет закладку или категорию. Для удаления закладки нужно отправить команду в ответ на " +
            "свое оригинальное (самое первое) сообщение с закладкой, " +
            "а для удаления категории ввести название команды и новую категорию через пробел.\n";

    public static final String BOOKMARK_SAVED = "Заметка сохранена!" + EmojiParser.parseToUnicode(":ok_hand:");
    public static final String CATEGORY_SAVED = "Категория сохранена!" + EmojiParser.parseToUnicode(":ok_hand:") + "\n%s";
    public static final String BOOKMARK_CATEGORY = "Категория: %s\n";
    public static final String SELECT_CATEGORY = "\nВыберите категорию:\n";
    public static final String SELECT_TAG = "\nВыберите тэг:\n";
    public static final String EMPTY_BOOKMARK_LIST = "Таких заметок еще нет! ";
    public static final String BOOKMARK_LIST_TEMPLATE = "%s %s\n\n";
    public static final String SHOW_BOOKMARK = "Держи!";
    public static final String BOOKMARK_LIST_TITLE_MESSAGE = "Найденные заметки:";
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
