package ru.ssharaev.bookmarkkeeper.service.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;
import ru.ssharaev.bookmarkkeeper.model.PaginationBookmarkList;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

import java.util.List;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.PAGE_SIZE;

/**
 * @author slawi
 * @since 21.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindAllPaginationCallbackHandler implements CallbackHandler {
    private final BookmarkRepository bookmarkRepository;
    private final TelegramResponseService responseService;

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.ALL;
    }

    @Override
    public void handle(CallbackQuery callbackQuery, CallbackData callbackData) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int overallCountBookmark = bookmarkRepository.findCountBookmarkByUserId(chatId);
        List<Bookmark> bookmarkList = bookmarkRepository.findPagingBookmarkByUserId(chatId, PAGE_SIZE, callbackData.getPage() * PAGE_SIZE);
        PaginationBookmarkList paginationBookmarkList = new PaginationBookmarkList(overallCountBookmark, callbackData.getPage(), bookmarkList, PAGE_SIZE, callbackData.getId());
        responseService.sendUpdateBookmarkListPage(callbackQuery, paginationBookmarkList, CallbackType.ALL);
        responseService.sendShowBookmarkAnswerCallback(callbackQuery);
    }
}
