package ru.ssharaev.bookmarkkeeper.service.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.CallbackData;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;
import ru.ssharaev.bookmarkkeeper.repository.BookmarkRepository;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

/**
 * @author slawi
 * @since 21.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShowBookmarkCallbackHandler implements CallbackHandler {
    private final TelegramResponseService responseService;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHOW;
    }

    @Override
    public void handle(CallbackQuery callbackQuery, CallbackData callbackData) {
        String messageId = String.valueOf(callbackData.getId());
        Bookmark bookmark = bookmarkRepository.findBookmarkByMessageIdAndUserId(messageId, callbackQuery.getMessage().getChatId());
        responseService.sendBookmark(callbackQuery.getMessage().getChatId(), bookmark);
        responseService.sendDeleteMessage(callbackQuery);
        responseService.sendShowBookmarkAnswerCallback(callbackQuery);
    }
}
