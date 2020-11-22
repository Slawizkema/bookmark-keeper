package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.UnknownCommandException;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.CATEGORY_SAVED;

/**
 * TODO добавить обработку ошибок - команда без аргумента, команда без команды
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
    public void handleCommand(Update update) throws UnknownCommandException {
        BookmarkCategory category = new BookmarkCategory();
        String categoryName = fetchArg(update.getMessage());
        category.setName(categoryName);
        categoryRepository.save(category);
        responseService.sendTextMessage(update.getMessage().getChatId(), String.format(CATEGORY_SAVED, categoryName));
    }
}
