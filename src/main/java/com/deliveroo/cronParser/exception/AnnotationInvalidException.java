package com.deliveroo.cronParser.exception;

public class AnnotationInvalidException extends CronExpressionInvalidException {
    public AnnotationInvalidException(String message) {
        super("Annotation error: " + message);
    }
}
