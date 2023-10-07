package com.testing.geologicalsectionsapi.exception;

public class ValidationException extends RuntimeException {

    final String traceId;
    final String field;
    final String message;
    final String code;

    public ValidationException() {
        super();
        this.traceId = null;
        this.field = null;
        this.message = null;
        this.code = null;
    }

    public ValidationException(String message) {
        super(message);
        this.traceId = null;
        this.field = null;
        this.message = message;
        this.code = null;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.traceId = null;
        this.field = null;
        this.message = message;
        this.code = null;
    }

    public ValidationException(Throwable cause) {
        super(cause);
        this.traceId = null;
        this.field = null;
        this.message = null;
        this.code = null;
    }

    protected ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.traceId = null;
        this.field = null;
        this.message = message;
        this.code = null;
    }

    public ValidationException(final String traceId, final String field, final String message, final String code) {
        super();
        this.traceId = traceId;
        this.field = field;
        this.message = message;
        this.code = code;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
