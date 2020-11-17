package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.List;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShowAllCommandHandler implements CommandHandler {
    private final BookmarkRepository repository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.ALL;
    }

    @Override
    public void handleCommand(Update update) {
        log.info("Send all bookmarks to client");
        List<Bookmark> bookmarkList = repository.findAll();
        responseService.sendBookmarkList(bookmarkList, update.getMessage().getChatId());
    }
}
