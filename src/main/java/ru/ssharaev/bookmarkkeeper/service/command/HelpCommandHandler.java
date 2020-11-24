package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.HELP_MESSAGE;

/**
 * @author s.sharaev
 */
@Service
@RequiredArgsConstructor
public class HelpCommandHandler implements CommandHandler {
    private final TelegramResponseService telegramResponseService;

    @Override
    public CommandType getType() {
        return CommandType.HELP;
    }

    @Override
    public void handleCommand(Update update) throws BookmarkKeeperException {
        telegramResponseService.sendTextMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}
