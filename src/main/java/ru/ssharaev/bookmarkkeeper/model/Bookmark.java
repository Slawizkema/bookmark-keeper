package ru.ssharaev.bookmarkkeeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;


@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Bookmark {
    private String messageId;
    private BookmarkType type;
    private BookmarkCategory category;
    private String url;
    private String body;
    private Set<String> tags;
}
