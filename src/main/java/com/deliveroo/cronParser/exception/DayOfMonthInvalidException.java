package com.deliveroo.cronParser.exception;

public class DayOfMonthInvalidException extends CronExpressionInvalidException {
    public DayOfMonthInvalidException(String message) {
        super("Day of Month field error: " + message);
    }
}