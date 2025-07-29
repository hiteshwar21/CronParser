package com.deliveroo.cronParser.utils;

import java.util.HashMap;
import java.util.Map;

public final class SymbolicValueMapper {
    // Immutable Static Maps for Performance and Safety
    public static final Map<String, Integer> MONTHS;
    public static final Map<String, Integer> DAYS_OF_WEEK;

    // Single Source of Truth
    static {
        MONTHS = new HashMap<>();
        MONTHS.put("JAN", 1); MONTHS.put("FEB", 2); MONTHS.put("MAR", 3);
        MONTHS.put("APR", 4); MONTHS.put("MAY", 5); MONTHS.put("JUN", 6);
        MONTHS.put("JUL", 7); MONTHS.put("AUG", 8); MONTHS.put("SEP", 9);
        MONTHS.put("OCT", 10); MONTHS.put("NOV", 11); MONTHS.put("DEC", 12);

        DAYS_OF_WEEK = new HashMap<>();
        DAYS_OF_WEEK.put("SUN", 0); DAYS_OF_WEEK.put("MON", 1); DAYS_OF_WEEK.put("TUE", 2);
        DAYS_OF_WEEK.put("WED", 3); DAYS_OF_WEEK.put("THU", 4); DAYS_OF_WEEK.put("FRI", 5); DAYS_OF_WEEK.put("SAT", 6);
    }

    private SymbolicValueMapper() {}
    public static Integer getMonth(String symbol) {
        // Case-Insensitive Lookup
        return MONTHS.get(symbol.toUpperCase());
    }
    public static Integer getDayOfWeek(String symbol) {
        return DAYS_OF_WEEK.get(symbol.toUpperCase());
    }
}
