package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.model.Tag;
import ru.ssharaev.bookmarkkeeper.repository.TagRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.List;

/**
 * @author slawi
 * @since 18.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindByTagCommandHandler implements CommandHandler {
    private final TagRepository tagRepository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.TAG;
    }

    @Override
    public void handleCommand(Update update) {
        log.info("Create message with tags");
        List<Tag> tagList = tagRepository.findAll();
        responseService.sendFindByTagResponse(tagList, update.getMessage().getChatId());
    }
}
