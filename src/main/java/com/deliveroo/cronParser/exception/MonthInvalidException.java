package com.deliveroo.cronParser.exception;

public class MonthInvalidException extends CronExpressionInvalidException {
    public MonthInvalidException(String message) {
        super("Month field error: " + message);
    }
}