package com.kuzmenchuk.oauthservice.exception;

public class UserNotPresentException extends RuntimeException {
    public UserNotPresentException() {
    }

    public UserNotPresentException(String message) {
        super(message);
    }

    public UserNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotPresentException(Throwable cause) {
        super(cause);
    }

    public UserNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
