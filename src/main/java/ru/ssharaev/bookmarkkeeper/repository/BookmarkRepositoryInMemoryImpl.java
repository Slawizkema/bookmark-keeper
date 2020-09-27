package ru.ssharaev.bookmarkkeeper.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Slf4j
@Service
public class BookmarkRepositoryInMemoryImpl implements BookmarkRepository {
    private final CopyOnWriteArrayList<Bookmark> bookmarkList = new CopyOnWriteArrayList<>();

    @Override
    public void saveBookmark(Bookmark bookmark) {
        log.info("Save bookmark in memory: {}", bookmark);
        bookmarkList.add(bookmark);
    }

    @Override
    public List<Bookmark> fetchAllBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>(bookmarkList);
        log.info("Fetch {} bookmarks", bookmarks.size());
        return bookmarks;
    }
}
