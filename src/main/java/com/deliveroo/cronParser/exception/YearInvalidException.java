package com.deliveroo.cronParser.exception;

public class YearInvalidException extends CronExpressionInvalidException {
    public YearInvalidException(String message) {
        super("Year field error: " + message);
    }
}
