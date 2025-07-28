package com.deliveroo.cronParser.exception;

public class CronExpressionInvalidException extends RuntimeException {
    public CronExpressionInvalidException(String message) {
        super(message);
    }
}