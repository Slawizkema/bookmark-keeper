package ru.ssharaev.bookmarkkeeper.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Set;

@Data
@Document(indexName = "blog", type = "article")
public class Bookmark {
    private final String messageId;
    private final BookmarkType type;
    private final String category;
    private final String url;
    private final String body;
    private final Set<String> tags;
}
