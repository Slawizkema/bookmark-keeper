package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.repository.CategoryRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.List;

/**
 * @author slawi
 * @since 18.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindByCategoryCommandHandler implements CommandHandler {
    private final CategoryRepository categoryRepository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.CATEGORIES;
    }

    @Override
    public void handleCommand(Update update) {
        log.info("Create message with categories");
        List<BookmarkCategory> categoryList = categoryRepository.findAll();
        responseService.sendFindByCategoryResponse(categoryList, update.getMessage().getChatId());
    }
}
