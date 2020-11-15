package ru.ssharaev.bookmarkkeeper.repository;

import org.springframework.stereotype.Service;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;

import java.util.List;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Service
public class CategoryRepository {

    public List<BookmarkCategory> fetchAllCategory() {
        return List.of(
                new BookmarkCategory("Test"),
                new BookmarkCategory("Prog"),
                new BookmarkCategory("Life")
        );
    }
}
