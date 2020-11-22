package ru.ssharaev.bookmarkkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssharaev.bookmarkkeeper.model.BookmarkCategory;

import java.util.List;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<BookmarkCategory, Long> {

    BookmarkCategory findFirstByName(String name);

    List<BookmarkCategory> findByUserId(long userId);
}
