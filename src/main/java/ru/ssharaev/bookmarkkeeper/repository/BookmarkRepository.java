package ru.ssharaev.bookmarkkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;

import java.util.List;
import java.util.Optional;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Repository
@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findBookmarksByUserId(Long userId);

    @Query(value = "select count(*) from bookmark where user_id = :userId", nativeQuery = true)
    int findCountBookmarkByUserId(@Param("userId") Long userId);

    @Query(value = "select * from bookmark where user_id = :userId limit :pageSize offset :offset", nativeQuery = true)
    List<Bookmark> findPagingBookmarkByUserId(
            @Param("userId") Long userId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset);


    Optional<Bookmark> findByMessageIdAndUserId(String messageId, Long userId);

    Bookmark findBookmarkByMessageIdAndUserId(String messageId, Long userId);

    @Query(value = "select count(*) from bookmark where user_id = :userId and category_id = :categoryId", nativeQuery = true)
    int findCountBookmarkByCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    @Query(value = "select * from bookmark where user_id = :userId and category_id = :categoryId limit :pageSize offset :offset", nativeQuery = true)
    List<Bookmark> findPagingBookmarkByCategoryId(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset);

    @Query(value = "select * from bookmark where id in " +
            "(select bookmark_id from tag_bookmark where tag_id = :tagId) and user_id = :userId limit :pageSize offset :offset", nativeQuery = true)
    List<Bookmark> findPagingBookmarkByTagId(
            @Param("tagId") Long tagId,
            @Param("userId") Long userId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset);

    @Query(value = "select count(*) from bookmark where id in " +
            "(select bookmark_id from tag_bookmark where tag_id = :tagId) and user_id = :userId", nativeQuery = true)
    int findCountBookmarkByTagId(@Param("tagId") Long tagId, @Param("userId") Long userId);
}
