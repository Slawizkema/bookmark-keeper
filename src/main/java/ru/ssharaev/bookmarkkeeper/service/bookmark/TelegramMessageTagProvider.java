package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.fetchListEntityValue;

@Slf4j
@Service
public class TelegramMessageTagProvider implements BookmarkTagProvider {

    /**
     * Вытягивает тэги из сообщения
     *
     * @param message сообщение, отправленное клиентом
     * @return сет с тегами
     */
    @Override
    public Set<String> fetchTag(Message message) {
        if (message.getEntities() == null) {
            log.info("У сообщения нет тэгов!");
            return Collections.emptySet();
        }
        Set<String> tags = new HashSet<>(fetchListEntityValue(message, EntityType.HASHTAG));
        log.info("Тэги полученные из сообщения: {}", tags);
        return tags;
    }
}
