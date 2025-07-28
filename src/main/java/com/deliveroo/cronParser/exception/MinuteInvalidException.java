package com.deliveroo.cronParser.exception;

public class MinuteInvalidException extends CronExpressionInvalidException {
    public MinuteInvalidException(String message) {
        super("Minute field error: " + message);
    }
}