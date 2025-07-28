package com.deliveroo.cronParser.utils;

public class StringUtils {

    public String[] cleanAndNormalize(String rawInput) {
        String trimmed = rawInput.trim();
        return trimmed.split("\\s+");
    }

    public static String formatRow(String label, String value, int labelWidth) {
        return String.format("%-" + labelWidth + "s%s%n", label, value);
    }
}
