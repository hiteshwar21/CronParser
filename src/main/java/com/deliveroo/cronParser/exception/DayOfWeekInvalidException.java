package com.deliveroo.cronParser.exception;

public class DayOfWeekInvalidException extends CronExpressionInvalidException {
    public DayOfWeekInvalidException(String message) {
        super("Day of Week field error: " + message);
    }
}