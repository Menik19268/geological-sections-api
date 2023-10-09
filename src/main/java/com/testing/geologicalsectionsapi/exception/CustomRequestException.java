package com.testing.geologicalsectionsapi.exception;

/**
 * Created by menik on 19/08/2019.
 */
public class CustomRequestException extends RuntimeException {

    public CustomRequestException() {
        super();
    }

    public CustomRequestException(String message) {
        super(message);
    }

    public CustomRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRequestException(Throwable cause) {
        super(cause);
    }

    protected CustomRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
