package ru.ssharaev.bookmarkkeeper.service.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ssharaev.bookmarkkeeper.model.Tag;
import ru.ssharaev.bookmarkkeeper.repository.TagRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.fetchListEntityValue;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramMessageTagProvider implements BookmarkTagProvider {
    private final TagRepository tagRepository;

    /**
     * Вытягивает тэги из сообщения
     *
     * @param message сообщение, отправленное клиентом
     * @return сет с тегами
     */
    @Override
    public Set<Tag> fetchTag(Message message) {
        if (message.getEntities() == null) {
            log.info("У сообщения нет тэгов!");
            return Collections.emptySet();
        }
        Set<Tag> tags = new HashSet<>(fetchListEntityValue(message, EntityType.HASHTAG))
                .stream()
                .map(name -> tagRepository.findOrCreate(name, message.getChatId()))
                .collect(Collectors.toSet());
        log.info("Тэги, полученные из сообщения: {}", tags);
        return tags;
    }


}
