package ru.ssharaev.bookmarkkeeper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.ssharaev.bookmarkkeeper.model.Bookmark;


public interface BookmarkRepository extends ElasticsearchRepository<Bookmark, String> {

    Page<Bookmark> findById(String id, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"bookmark.id\": \"?0\"}}]}}")
    Page<Bookmark> findByBookmarkIdUsingCustomQuery(String name, Pageable pageable);
}
