package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

/**
 * @author s.sharaev
 */
@Service
@RequiredArgsConstructor
public class FeelingLuckyCommandHandler implements CommandHandler {
    private final BookmarkRepository bookmarkRepository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.FEELING_LUCKY;
    }

    @Override
    public void handleCommand(Update update) throws BookmarkKeeperException {
        Long userId = update.getMessage().getChatId();
        Bookmark bookmark = bookmarkRepository.findRandomBookmark(userId);
        responseService.sendBookmark(userId, bookmark);
    }
}
