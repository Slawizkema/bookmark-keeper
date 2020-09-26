package ru.ssharaev.bookmarkkeeper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.service.bookmark.BookmarkSaveService;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramMessageConstructorImpl;
import ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseService;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramUpdateHandlerImpl implements TelegramUpdateHandler {
    private final BookmarkSaveService bookmarkSaveService;
    private final TelegramResponseService responseService;
    private final TelegramMessageConstructorImpl messageConstructor;

    @Override
    public void handleUpdate(Update update) {
        Bookmark bookmark = bookmarkSaveService.saveBookmark(update.getMessage());
        responseService.sendMessage(messageConstructor.createSendMessage(bookmark.toString(), update.getMessage().getChatId()));
    }
}
