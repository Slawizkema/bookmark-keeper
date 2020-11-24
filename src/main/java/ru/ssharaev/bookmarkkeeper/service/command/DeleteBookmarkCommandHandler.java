package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;
import ru.ssharaev.bookmarkkeeper.service.bookmark.BookmarkSaveService;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.Objects;

import static com.google.common.base.Strings.isNullOrEmpty;


/**
 * @author slawi
 * @since 22.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeleteBookmarkCommandHandler implements CommandHandler {
    private final CategoryRepository categoryRepository;
    private final BookmarkSaveService bookmarkSaveService;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.DELETE;
    }

    @Override
    public void handleCommand(Update update) throws BookmarkKeeperException {
        try {
            String categoryName = fetchArg(update.getMessage());
            if (!isNullOrEmpty(categoryName)) {
                handleDeleteCategory(update, categoryName);
                return;
            }
        } catch (BookmarkKeeperException e) {
            throw new BookmarkKeeperException("Не смогли удалить категорию", e);
        }
        handleDeleteBookmark(update);
    }

    public void handleDeleteBookmark(Update update) throws BookmarkKeeperException {
        if (Objects.isNull(update.getMessage().getReplyToMessage())) {
            sendHelpResponse();
            return;
        }
        long chatId = update.getMessage().getChatId();
        try {
            String messageId = String.valueOf(update.getMessage().getReplyToMessage().getMessageId());
            bookmarkSaveService.deleteBookmark(messageId, chatId);
            responseService.sendTextMessage(update.getMessage().getChatId(), "Закладка удалена!");
        } catch (Exception e) {
            sendHelpResponse();
            throw new BookmarkKeeperException("Не смогли удалить закладку", e);
        }
    }

    private void handleDeleteCategory(Update update, String categoryName) throws BookmarkKeeperException {
        try {
            BookmarkCategory category = categoryRepository.findFirstByName(categoryName);
            categoryRepository.delete(category);
            responseService.sendTextMessage(update.getMessage().getChatId(), "Категория удалена!");
        } catch (Exception e) {
            sendHelpResponse();
            throw new BookmarkKeeperException("Не смогли удалить категорию", e);
        }
    }

    private void sendHelpResponse() {
    }
}
