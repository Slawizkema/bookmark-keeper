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

import java.util.List;

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
        Long chatId = callbackQuery.getMessage().getChatId();
        List<Bookmark> bookmarkList = bookmarkRepository.findBookmarkByTagId(callbackData.getId(), chatId);
        responseService.sendBookmarkList(bookmarkList, chatId);
        responseService.sendDeleteKeyboard(callbackQuery);
    }
}
