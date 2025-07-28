package com.deliveroo.cronParser.exception;

public class HourInvalidException extends CronExpressionInvalidException {
    public HourInvalidException(String message) {
        super("Hour field error: " + message);
    }
}
