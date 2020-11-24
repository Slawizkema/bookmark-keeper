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
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.Optional;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.CATEGORY_SAVED;

/**
 *
 * @author slawi
 * @since 21.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddCategoryCommandHandler implements CommandHandler {
    private final CategoryRepository categoryRepository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.ADD_CATEGORY;
    }

    @Override
    public void handleCommand(Update update) throws BookmarkKeeperException {
        BookmarkCategory category = new BookmarkCategory();
        String categoryName = Optional.ofNullable(fetchArg(update.getMessage()))
                .orElseThrow(() -> new BookmarkKeeperException("Не удалось разобрать категорию!"));
        category.setName(categoryName);
        categoryRepository.save(category);
        responseService.sendTextMessage(update.getMessage().getChatId(), String.format(CATEGORY_SAVED, categoryName));
    }
}
