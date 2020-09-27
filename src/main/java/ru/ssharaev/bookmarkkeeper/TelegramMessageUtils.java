package ru.ssharaev.bookmarkkeeper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author slawi
 * @since 27.09.2020
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TelegramMessageUtils {

    /**
     * Вытаскивает список значений необходимого entity
     *
     * @param message    сообщение клиента
     * @param entityType тип entity
     * @return список значений данного entity
     */
    public static List<String> fetchListEntityValue(Message message, String entityType) {
        return message.getEntities().stream()
                .filter(e -> Objects.equals(entityType, e.getType()))
                .map(MessageEntity::getText)
                .collect(Collectors.toList());
    }

    /**
     * TODO: Изменить exception
     * Вытаскивает значение необходимого entity
     *
     * @param message    сообщение клиента
     * @param entityType тип entity
     * @return значение данного entity
     */
    //
    public static String fetchEntityValue(Message message, String entityType) {
        return fetchListEntityValue(message, entityType).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("entity %s not found", entityType)));
    }

    /**
     * Определяет есть ли в указанном сообщении указанный entity
     *
     * @param message    сообщение клиента
     * @param entityType искомый entity
     * @return есть ли в указанном сообщении указанный entity
     */
    public static boolean hasEntity(Message message, String entityType) {
        if (!message.hasEntities()) {
            return false;
        }
        return message.getEntities().stream()
                .map(MessageEntity::getType)
                .anyMatch(e -> Objects.equals(entityType, e));
    }
}
