package ru.ssharaev.bookmarkkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;

import java.util.List;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Repository
@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findBookmarksByUserId(Long userId);

    Bookmark findBookmarkByMessageIdAndUserId(String messageId, Long userId);

    List<Bookmark> findBookmarkByCategoryIdAndUserId(Long categoryId, Long userId);

    @Query(value = "select * from bookmark where id in " +
            "(select bookmark_id from tag_bookmark where tag_id = :tagId) and user_id = :userId", nativeQuery = true)
    List<Bookmark> findBookmarkByTagId(@Param("tagId") Long tagId, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update bookmark set category_id = :categoryId where id = :bookmarkId and user_id = :userId", nativeQuery = true)
    void updateBookmarkCategory(@Param("categoryId") Long categoryId, @Param("bookmarkId") Long bookmarkId, @Param("userId") Long userId);
}
