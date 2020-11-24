package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.stream.Collectors;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.START_CATEGORY;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.START_MESSAGE;

/**
 * @author slawi
 * @since 22.11.2020
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StartCommandHandler implements CommandHandler {
    private final CategoryRepository categoryRepository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.START;
    }

    @Override
    public void handleCommand(Update update) throws BookmarkKeeperException {
        long userId = update.getMessage().getChatId();
        if (categoryRepository.findByUserId(userId).isEmpty()) {
            categoryRepository.saveAll(START_CATEGORY.stream().map(e -> new BookmarkCategory().setName(e).setUserId(userId)).collect(Collectors.toList()));
        }
        responseService.sendTextMessage(userId, START_MESSAGE);
    }
}
