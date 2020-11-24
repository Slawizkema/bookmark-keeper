package ru.ssharaev.bookmarkkeeper.exception;

/**
 * @author slawi
 * @since 27.09.2020
 */
public class BookmarkKeeperException extends Exception {

    public BookmarkKeeperException(String message) {
        super(message);
    }

    public BookmarkKeeperException(String message, Exception cause) {
        super(message, cause);
    }
}
