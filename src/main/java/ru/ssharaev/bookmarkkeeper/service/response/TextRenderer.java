package ru.ssharaev.bookmarkkeeper.service.response;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.PaginationBookmarkList;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.isEmpty;
import static java.util.Objects.nonNull;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.*;

/**
 * @author slawi
 * @since 22.11.2020
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TextRenderer {

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

    public String printFullBookmark(Bookmark bookmark) {
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
        if (!isNullOrEmpty(bookmark.getBody())) {
            builder.append(bookmark.getBody());
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
