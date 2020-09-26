package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> tags = message.getEntities().stream()
                .filter(e -> e.getType().equals("hashtag"))
                .map(e -> message.getText().substring(e.getOffset(), e.getOffset() + e.getLength()))
                .collect(Collectors.toSet());
        log.info("Тэги полученные из сообщения: {}", tags);
        return tags;
    }
}
