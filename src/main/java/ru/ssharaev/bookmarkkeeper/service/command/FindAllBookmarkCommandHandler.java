package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.model.PaginationBookmarkList;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.List;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.PAGE_SIZE;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindAllBookmarkCommandHandler implements CommandHandler {
    private final BookmarkRepository bookmarkRepository;
    private final TelegramResponseService responseService;

    @Override
    public CommandType getType() {
        return CommandType.ALL;
    }

    @Override
    public void handleCommand(Update update) throws BookmarkKeeperException {
        Long chatId = update.getMessage().getChatId();
        int overallCountBookmark = bookmarkRepository.findCountBookmarkByUserId(chatId);
        List<Bookmark> bookmarkList = bookmarkRepository.findPagingBookmarkByUserId(chatId, PAGE_SIZE, 0);
        PaginationBookmarkList paginationBookmarkList = new PaginationBookmarkList(overallCountBookmark, 0, bookmarkList, PAGE_SIZE, 0);
        responseService.sendBookmarkListPage(chatId, paginationBookmarkList, CallbackType.ALL);
    }
}
