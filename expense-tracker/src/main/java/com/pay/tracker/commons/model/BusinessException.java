package com.pay.tracker.commons.model;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private Object[] args;
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Object... args) {
        super(message);
        this.args=args;
    }

    public BusinessException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.args=args;
    }
}
