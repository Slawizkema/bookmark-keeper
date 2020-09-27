package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;

import java.util.List;

import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseTemplate.*;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramResponseService {
    private final TelegramMessageRender messageConstructor;
    private final TelegramMessageSender telegramMessageSender;

    public void sendTextMessage(String text, long chatId) {
        telegramMessageSender.sendMessage(messageConstructor.createSendMessage(text, chatId));
    }

    public void sendSaveResponse(Bookmark bookmark, long chatId) {
        String messageText = String.format(
                BOOKMARK_SAVED + BOOKMARK_TEMPLATE,
                bookmark.getType(),
                bookmark.getCategory(),
                bookmark.getTags(),
                bookmark.getBody());
        sendTextMessage(messageText, chatId);
    }

    //TODO добавить пагинацию
    public void sendBookmarkList(List<Bookmark> bookmarkList, long chatId) {
        if (bookmarkList.isEmpty()) {
            sendTextMessage(EMPTY_BOOKMARK_LIST, chatId);
        }
        StringBuilder builder = new StringBuilder();
        bookmarkList.forEach(bookmark -> {
            builder.append(String.format(
                    BOOKMARK_TEMPLATE,
                    bookmark.getType(),
                    bookmark.getCategory(),
                    bookmark.getTags(),
                    bookmark.getBody()))
                    .append("\n\n");
        });
        sendTextMessage(builder.toString(), chatId);
    }
}
