package ru.ssharaev.bookmarkkeeper.service.response;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkType;
import ru.ssharaev.bookmarkkeeper.model.PaginationBookmarkList;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.isEmpty;
import static java.util.Objects.nonNull;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.BOOKMARK_CATEGORY;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.BOOKMARK_LIST_TEMPLATE;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.BOOKMARK_LIST_TITLE_MESSAGE;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.renderEmojiNumber;

/**
 * @author slawi
 * @since 22.11.2020
 */
@Service
public class BookmarkMessageRendererProvider {
    private final Map<BookmarkType, BookmarkMessageRenderer> rendererMap = Map.of(
            BookmarkType.URL, this::renderUrlBookmark,
            BookmarkType.FILE, this::renderDocumentBookmark,
            BookmarkType.TEXT, this::renderTextBookmark,
            BookmarkType.PHOTO, this::renderPhotoBookmark
    );


    public BookmarkMessageRenderer getRenderer(BookmarkType bookmarkType) {
        return rendererMap.get(bookmarkType);
    }

    public SendMessage renderTextBookmark(Bookmark bookmark, long chatId) {
        StringBuilder builder = getCommonBuilder(bookmark);
        if (!isNullOrEmpty(bookmark.getBody())) {
            builder.append(bookmark.getBody());
        }
        return new SendMessage()
                .setChatId(chatId)
                .setText(builder.toString())
                .setReplyToMessageId(Integer.valueOf(bookmark.getMessageId()));
    }

    public SendMessage renderUrlBookmark(Bookmark bookmark, long chatId) {
        StringBuilder builder = getCommonBuilder(bookmark);
        if (!isNullOrEmpty(bookmark.getDescription())) {
            builder.append(bookmark.getDescription()).append("\n");
        }
        if (!isNullOrEmpty(bookmark.getBody())) {
            builder.append(bookmark.getBody());
        }
        return new SendMessage()
                .setChatId(chatId)
                .setText(builder.toString())
                .setReplyToMessageId(Integer.valueOf(bookmark.getMessageId()));
    }

    public SendDocument renderDocumentBookmark(Bookmark bookmark, long chatId) {
        StringBuilder builder = getCommonBuilder(bookmark);
        return new SendDocument()
                .setChatId(chatId)
                .setDocument(bookmark.getFileId())
                .setCaption(builder.toString())
                .setReplyToMessageId(Integer.valueOf(bookmark.getMessageId()));
    }

    public SendPhoto renderPhotoBookmark(Bookmark bookmark, long chatId) {
        StringBuilder builder = getCommonBuilder(bookmark);
        return new SendPhoto()
                .setChatId(chatId)
                .setPhoto(bookmark.getFileId())
                .setCaption(builder.toString())
                .setReplyToMessageId(Integer.valueOf(bookmark.getMessageId()));
    }


    public StringBuilder getCommonBuilder(Bookmark bookmark) {
        StringBuilder builder = new StringBuilder();
        if (!isEmpty(bookmark.getTags())) {
            bookmark.getTags().forEach(e -> builder.append(e.getName()).append(" "));
            builder.append("\n");
        }
        if (nonNull(bookmark.getCategory())) {
            builder.append(String.format(BOOKMARK_CATEGORY, bookmark.getCategory().getName()));
        }
        if (!isNullOrEmpty(bookmark.getTitle())) {
            builder.append(bookmark.getTitle()).append("\n");
        }
        return builder;
    }

    public String printBookmark(Bookmark bookmark) {
        StringBuilder builder = new StringBuilder();
        if (!isNullOrEmpty(bookmark.getTitle())) {
            builder.append(bookmark.getTitle()).append("\n");
        }
        if (!isNullOrEmpty(bookmark.getBody())) {
            if (bookmark.getBody().length() > 60) {
                builder.append(bookmark.getBody().substring(0, 60)).append("...").append("\n");
                return builder.toString();
            }
            builder.append(bookmark.getBody());
            return builder.toString();
        }
        return builder.toString();
    }

    public String renderBookmarkList(PaginationBookmarkList paginationBookmarkList) {
        int startIndex = paginationBookmarkList.getPageNum() * paginationBookmarkList.getPageSize() + 1;
        List<Bookmark> bookmarkList = paginationBookmarkList.getBookmarkList();
        StringBuilder builder = new StringBuilder();
        builder.append(BOOKMARK_LIST_TITLE_MESSAGE).append("\n");
        IntStream.range(0, bookmarkList.size())
                .boxed()
                .forEach(it -> builder.append(String.format(BOOKMARK_LIST_TEMPLATE, renderEmojiNumber(it + startIndex), printBookmark(bookmarkList.get(it)))));
        return builder.toString();
    }


}
