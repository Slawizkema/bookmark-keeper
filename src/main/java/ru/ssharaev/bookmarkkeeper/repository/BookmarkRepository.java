package ru.ssharaev.bookmarkkeeper.repository;

import ru.ssharaev.bookmarkkeeper.model.Bookmark;

import java.util.List;


public interface BookmarkRepository {

    public void saveBookmark(Bookmark bookmark);

    public List<Bookmark> fetchAllBookmarks();


}
