package ru.ssharaev.bookmarkkeeper.repository;

import ru.ssharaev.bookmarkkeeper.model.Bookmark;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;

import java.util.List;


public interface BookmarkRepository {

    public void saveBookmark(Bookmark bookmark);

    public List<Bookmark> fetchAllBookmarks();


    Bookmark fetchBookmarkBy(String messageId);

    Bookmark updateBookmarkCategory(String messageId, BookmarkCategory category);
}
