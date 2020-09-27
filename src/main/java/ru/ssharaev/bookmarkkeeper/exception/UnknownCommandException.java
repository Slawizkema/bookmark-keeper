package ru.ssharaev.bookmarkkeeper.exception;

/**
 * @author slawi
 * @since 27.09.2020
 */
public class UnknownCommandException extends Exception {

    public UnknownCommandException(String message) {
        super(message);
    }

    public UnknownCommandException(String message, Exception cause) {
        super(message, cause);
    }
}
