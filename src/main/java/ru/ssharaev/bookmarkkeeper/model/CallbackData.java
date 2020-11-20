package ru.ssharaev.bookmarkkeeper.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Модель для передачи типа колбэка и идентификатора сущности.
 * Ввиду ограничения максимального размера в 64 байта, есть только одно поле с идентификатором,
 * которое в зависимости от типа колбэка может быть идентификатором сообщения, категории или тэга
 *
 * @author slawi
 * @since 15.11.2020
 */
@Data
@Slf4j
@JsonAutoDetect
public class CallbackData {
    private final CallbackType type;
    private final long id;

    public static String toJson(CallbackData callbackData, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(callbackData);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации объекта {}", callbackData, e);
            return "";
        }
    }

    public static CallbackData fromJson(String json, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, CallbackData.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка десериализации объекта {}", json, e);
            return null;
        }
    }
}
