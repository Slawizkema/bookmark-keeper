package ru.ssharaev.bookmarkkeeper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author slawi
 * @since 21.11.2020
 */
@Getter
@AllArgsConstructor
public class PaginationBookmarkList {
    private final int overallCountBookmark;
    private final int pageNum;
    private final List<Bookmark> bookmarkList;
    private final int pageSize;
    private final long categoryId;
}
