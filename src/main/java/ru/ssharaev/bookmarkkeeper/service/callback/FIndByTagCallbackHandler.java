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
import java.util.Objects;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.PAGE_SIZE;

/**
 * @author slawi
 * @since 18.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FIndByTagCallbackHandler implements CallbackHandler {
    private final BookmarkRepository bookmarkRepository;
    private final TelegramResponseService responseService;

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.BY_TAG;
    }

    @Override
    public void handle(CallbackQuery callbackQuery, CallbackData callbackData) {
        log.info("Callback data: {}", callbackData);

        if (Objects.isNull(callbackData.getPage())) {
            handleNewSearch(callbackQuery, callbackData);
            return;
        }
        handleNextPage(callbackQuery, callbackData);
    }

    private void handleNewSearch(CallbackQuery callbackQuery, CallbackData callbackData) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int overallCountBookmark = bookmarkRepository.findCountBookmarkByTagId(chatId, callbackData.getId());
        List<Bookmark> bookmarkList = bookmarkRepository.findPagingBookmarkByTagId(chatId, callbackData.getId(), PAGE_SIZE, 0);
        PaginationBookmarkList paginationBookmarkList = new PaginationBookmarkList(overallCountBookmark, 0, bookmarkList, PAGE_SIZE, callbackData.getId());
        responseService.sendBookmarkListPage(chatId, paginationBookmarkList, CallbackType.BY_TAG);
        responseService.sendDeleteMessage(callbackQuery);
        responseService.sendShowBookmarkAnswerCallback(callbackQuery);
    }

    private void handleNextPage(CallbackQuery callbackQuery, CallbackData callbackData) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int overallCountBookmark = bookmarkRepository.findCountBookmarkByTagId(chatId, callbackData.getId());
        List<Bookmark> bookmarkList = bookmarkRepository.findPagingBookmarkByTagId(chatId, callbackData.getId(), PAGE_SIZE, callbackData.getPage() * PAGE_SIZE);
        PaginationBookmarkList paginationBookmarkList = new PaginationBookmarkList(overallCountBookmark, callbackData.getPage(), bookmarkList, PAGE_SIZE, callbackData.getId());
        responseService.sendUpdateBookmarkListPage(callbackQuery, paginationBookmarkList, CallbackType.BY_TAG);
        responseService.sendShowBookmarkAnswerCallback(callbackQuery);
    }
}
