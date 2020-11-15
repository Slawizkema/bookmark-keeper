package ru.ssharaev.bookmarkkeeper.service.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;
import ru.ssharaev.bookmarkkeeper.model.CallBackData;
import ru.ssharaev.bookmarkkeeper.model.CommandType;

import java.util.ArrayList;
import java.util.List;

import static ru.ssharaev.bookmarkkeeper.model.CommandType.SAVE;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseTemplate.BOOKMARK_TEMPLATE;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseTemplate.SELECT_CATEGORY;

/**
 * @author slawi
 * @since 26.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramMessageRenderImpl implements TelegramMessageRender {
    private final ObjectMapper objectMapper;

    @Override
    public SendMessage createBookmarkSendMessage(String messageText, long chatId) {
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(chatId);
        replyMessageToUser.setText(messageText);
        return replyMessageToUser;
    }

    @Override
    public SendMessage createBookmarkSendMessage(Bookmark bookmark, long chatId) {
        SendMessage message = new SendMessage();
        String messageText = String.format(
                BOOKMARK_TEMPLATE + SELECT_CATEGORY,
                bookmark.getType(),
                bookmark.getCategory(),
                bookmark.getTags(),
                bookmark.getBody());
        message.setChatId(chatId);
        message.setText(messageText);
        return message;
    }

    @Override
    public SendMessage createSaveBookmarkSendMessage(String messageText, String messageId, long chatId, List<BookmarkCategory> categoryList) {
        log.info("Create SendMessage for chatId={}, text ='{}'", chatId, messageText);
        SendMessage replyMessageToUser = new SendMessage();
        replyMessageToUser.setChatId(chatId);
        replyMessageToUser.setText(messageText);
        replyMessageToUser.setReplyMarkup(createInlineKeyboard(SAVE, categoryList, messageId));
        return replyMessageToUser;
    }

    @SneakyThrows
    private InlineKeyboardMarkup createInlineKeyboard(CommandType commandType, List<BookmarkCategory> categoryList, String messageId) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (BookmarkCategory it : categoryList) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton().setText(it.getName()).setCallbackData(
                    objectMapper.writeValueAsString(new CallBackData(messageId, commandType, it.getName())));
            row.add(inlineKeyboardButton);
        }
        return new InlineKeyboardMarkup(List.of(row));
    }
}
