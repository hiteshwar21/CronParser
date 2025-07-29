package com.deliveroo.cronParser.utils;

import java.util.regex.Pattern;

public class StringUtils {

    public static final Pattern NTH_OCCURRENCE_PATTERN = Pattern.compile("^([0-6]|[a-zA-Z]{3})#([1-5])$");

    public String[] cleanAndNormalize(String rawInput) {
        String trimmed = rawInput.trim();
        return trimmed.split("\\s+");
    }

    public static String formatRow(String label, String value, int labelWidth) {
        return String.format("%-" + labelWidth + "s%s%n", label, value);
    }
}
