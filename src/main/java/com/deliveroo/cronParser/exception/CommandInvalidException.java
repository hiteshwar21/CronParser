package com.deliveroo.cronParser.exception;

public class CommandInvalidException extends CronExpressionInvalidException {
    public CommandInvalidException(String message) {
        super("Command field error: " + message);
    }
}