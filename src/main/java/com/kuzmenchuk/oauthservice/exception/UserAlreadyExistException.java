package com.kuzmenchuk.oauthservice.exception;

/**
 * Custom exception for checking the presence of a user in the database
 * @author kuzmenchuk
 */
public class UserAlreadyExistException extends RuntimeException {
    /**
     * Empty constructor
     */
    public UserAlreadyExistException() {
    }

    /**
     * Exception constructor with message parameter
     * @param message {@code String} detail message when an exception occurs
     */
    public UserAlreadyExistException(String message) {
        super(message);
    }

    /**
     * Exception constructor with message and cause parameters
     * @param message {@code String} detail message when an exception occurs
     * @param cause the cause.  (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor with
     * @param cause the cause.  (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception constructor with
     * @param message {@code String} detail message when an exception occurs
     * @param cause the cause.  (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether suppression is enabled
     *                          or disabled
     * @param writableStackTrace whether the stack trace should
     *                           be writable
     */
    public UserAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}