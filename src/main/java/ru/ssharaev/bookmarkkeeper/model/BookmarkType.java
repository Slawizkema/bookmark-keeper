package ru.ssharaev.bookmarkkeeper.model;

import static com.google.common.base.Strings.isNullOrEmpty;

public enum BookmarkType {
    URL {
        @Override
        public String printBookmark(Bookmark bookmark) {
            StringBuilder builder = new StringBuilder();
            if (!isNullOrEmpty(bookmark.getTitle())) {
                builder.append(bookmark.getTitle())
                        .append("\n");
            }
            if (!isNullOrEmpty(bookmark.getDescription())) {
                if (bookmark.getDescription().length() > 60) {
                    builder.append(bookmark.getDescription(), 0, 60)
                            .append("...")
                            .append("\n");
                    return builder.toString();
                }
                builder
                        .append(bookmark.getDescription())
                        .append("\n")
                        .append(bookmark.getUrl());

                return builder.toString();
            }
            return builder.toString();
        }
    },

    TEXT {
        @Override
        public String printBookmark(Bookmark bookmark) {
            if (bookmark.getBody().length() > 60) {
                return bookmark.getBody().substring(0, 60) + "..." + "\n";
            }
            ;
            return bookmark.getBody();
        }
    },

    PHOTO {
        @Override
        public String printBookmark(Bookmark bookmark) {
            StringBuilder builder = new StringBuilder();
            if (!isNullOrEmpty(bookmark.getTitle())) {
                builder.append(bookmark.getTitle()).append("\n");
            }
            if (!isNullOrEmpty(bookmark.getDescription())) {
                if (bookmark.getBody().length() > 60) {
                    builder.append(bookmark.getDescription(), 0, 60).append("...").append("\n");
                    return builder.toString();
                }
                builder.append(bookmark.getDescription());
                return builder.toString();
            }
            return builder.toString();
        }
    },

    FILE {
        @Override
        public String printBookmark(Bookmark bookmark) {
            StringBuilder builder = new StringBuilder();
            if (!isNullOrEmpty(bookmark.getTitle())) {
                builder.append(bookmark.getTitle()).append("\n");
            }
            if (!isNullOrEmpty(bookmark.getBody())) {
                if (bookmark.getBody().length() > 60) {
                    builder.append(bookmark.getBody(), 0, 60).append("...").append("\n");
                    return builder.toString();
                }
                builder.append(bookmark.getBody());
                return builder.toString();
            }
            return builder.toString();
        }
    };

    public abstract String printBookmark(Bookmark bookmark);

}
