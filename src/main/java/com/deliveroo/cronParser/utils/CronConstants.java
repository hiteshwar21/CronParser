package com.deliveroo.cronParser.utils;

public class CronConstants {

    public static final int MIN_MINUTE = 0;
    public static final int MAX_MINUTE = 59;

    public static final int MIN_HOUR = 0;
    public static final int MAX_HOUR = 23;

    public static final int MIN_DAY_OF_MONTH = 1;
    public static final int MAX_DAY_OF_MONTH = 31;

    public static final int MIN_MONTH = 1;
    public static final int MAX_MONTH = 12;

    public static final int MIN_DAY_OF_WEEK = 0;
    public static final int MAX_DAY_OF_WEEK = 6;

    public static final int MIN_YEAR = 2025;
    public static final int MAX_YEAR = 2099;

    public static final String FIELD_MINUTE = "minute";
    public static final String FIELD_HOUR = "hour";
    public static final String FIELD_DAY_OF_MONTH = "day of month";
    public static final String FIELD_MONTH = "month";
    public static final String FIELD_DAY_OF_WEEK = "day of week";
    public static final String FIELD_YEAR = "year";

    // For left alignment
    public static final int FIELD_LABEL_WIDTH = 14;
}
