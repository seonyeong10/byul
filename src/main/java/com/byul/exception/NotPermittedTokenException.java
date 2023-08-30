package com.byul.exception;

public class NotPermittedTokenException extends RuntimeException {
    public NotPermittedTokenException() {
        super();
    }

    public NotPermittedTokenException(String message) {
        super(message);
    }

    public NotPermittedTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotPermittedTokenException(Throwable cause) {
        super(cause);
    }

    protected NotPermittedTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
