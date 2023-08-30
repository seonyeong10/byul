package com.byul.exception;

public class CannotCancleOrderException extends RuntimeException {
    public CannotCancleOrderException() {
        super();
    }

    public CannotCancleOrderException(String message) {
        super(message);
    }

    public CannotCancleOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCancleOrderException(Throwable cause) {
        super(cause);
    }

    protected CannotCancleOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
