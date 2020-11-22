package ru.ssharaev.bookmarkkeeper.service.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ssharaev.bookmarkkeeper.model.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Long.parseLong;
import static ru.ssharaev.bookmarkkeeper.model.CallbackType.CATEGORY;
import static ru.ssharaev.bookmarkkeeper.model.CallbackType.SHOW;
import static ru.ssharaev.bookmarkkeeper.service.response.TelegramResponseUtils.*;

/**
 * @author slawi
 * @since 22.11.2020
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ButtonRenderer {
    private final ObjectMapper objectMapper;

    public InlineKeyboardButton getBookmarkButton(Bookmark bookmark, String numberName) {
        return new InlineKeyboardButton(numberName).setCallbackData(
                CallbackData.toJson(new CallbackData(SHOW, parseLong(bookmark.getMessageId())), objectMapper));
    }

    public InlineKeyboardButton getCategoryButton(BookmarkCategory category, CallbackType callbackType) {
        return new InlineKeyboardButton(category.getName()).setCallbackData(
                CallbackData.toJson(new CallbackData(callbackType, category.getId()), objectMapper));
    }

    public InlineKeyboardButton getTagButton(Tag tag) {
        return new InlineKeyboardButton(tag.getName()).setCallbackData(
                CallbackData.toJson(new CallbackData(CATEGORY, tag.getId()), objectMapper));
    }

    public List<InlineKeyboardButton> getBookmarkButtonList(PaginationBookmarkList paginationBookmarkList) {
        int startIndex = paginationBookmarkList.getPageNum() * paginationBookmarkList.getPageSize() + 1;
        List<Bookmark> bookmarkList = paginationBookmarkList.getBookmarkList();
        return IntStream.range(0, bookmarkList.size())
                .boxed()
                .map(e -> getBookmarkButton(bookmarkList.get(e), renderEmojiNumber(e + startIndex)))
                .collect(Collectors.toList());
    }

    public List<InlineKeyboardButton> createInlinePaginationKeyboard(PaginationBookmarkList paginationBookmarkList, CallbackType callbackType) {
        int currentPageElement = paginationBookmarkList.getBookmarkList().size();
        int allElementCount = paginationBookmarkList.getOverallCountBookmark();
        int lastElementOfPage = currentPageElement + paginationBookmarkList.getPageNum() * paginationBookmarkList.getPageSize();

        if (currentPageElement < paginationBookmarkList.getPageSize() && paginationBookmarkList.getPageNum() == 0) {
            return null;
        }
        if (paginationBookmarkList.getPageNum() == 0) {
            return List.of(getPaginationButton(paginationBookmarkList, callbackType, 1));
        }
        if (currentPageElement < paginationBookmarkList.getPageSize() || allElementCount == lastElementOfPage) {
            return List.of(getPaginationButton(paginationBookmarkList, callbackType, -1));
        }
        return List.of(getPaginationButton(paginationBookmarkList, callbackType, -1),
                getPaginationButton(paginationBookmarkList, callbackType, 1));
    }

    private InlineKeyboardButton getPaginationButton(PaginationBookmarkList paginationBookmarkList, CallbackType callbackType, int increment) {
        return new InlineKeyboardButton()
                .setText(increment > 0 ? NEXT_PAGE : PREV_PAGE)
                .setCallbackData(CallbackData.toJson(
                        new CallbackData(
                                callbackType,
                                paginationBookmarkList.getCategoryId(),
                                paginationBookmarkList.getPageNum() + increment),
                        objectMapper));
    }
}
