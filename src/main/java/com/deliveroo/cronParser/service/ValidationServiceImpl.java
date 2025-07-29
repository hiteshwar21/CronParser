package com.deliveroo.cronParser.service;

import com.deliveroo.cronParser.exception.*;
import com.deliveroo.cronParser.utils.SymbolicValueMapper;

import java.util.Map;
import java.util.regex.Pattern;

import static com.deliveroo.cronParser.utils.AnnotationMapper.MACRO_MAP;
import static com.deliveroo.cronParser.utils.CronConstants.*;

public class ValidationServiceImpl implements ValidationService {

    private static final Pattern NUMBER = Pattern.compile("\\d+");

    @Override
    public void validate(String[] fields) {
        validateFieldLength(fields);
        if(fields.length == 2) {
            validateAnnotation(fields);
            return;
        }
        validateMinute(fields[0]);
        validateHour(fields[1]);
        validateDayOfMonth(fields[2]);
        validateMonth(fields[3]);
        validateDayOfWeek(fields[4]);

        if (fields.length == 7) {
            validateYear(fields[5]);
            validateCommand(fields[6]);
        } else {
            validateCommand(fields[5]);
        }
    }

    private void validateFieldLength(String[] fields) {
        if (fields == null || (fields.length != 6 && fields.length != 7 && fields.length != 2)) {
            throw new CronExpressionInvalidException(
                    "Expected either macro or full 6 or 7 fields (minute, hour, day of month, month, day of week, [year],  command), but got " +
                            (fields == null ? 0 : fields.length)
            );
        }
    }

    private void validateAnnotation(String[] fields) {
        if (fields[0].startsWith("@")) {
            String macro = fields[0].toLowerCase();
            if (!MACRO_MAP.containsKey(macro)) {
                throw new AnnotationInvalidException("Unsupported macro: " + macro);
            }
        } else {
            throw new AnnotationInvalidException("Unsupported macro: Should have @ prefix");
        }
    }

    private void validateMinute(String expr) {
        validateField(expr, MIN_MINUTE, MAX_MINUTE, FIELD_MINUTE, null);
    }

    private void validateHour(String expr) {
        validateField(expr, MIN_HOUR, MAX_HOUR, FIELD_HOUR, null);
    }

    private void validateDayOfMonth(String expr) {
        validateField(expr, MIN_DAY_OF_MONTH, MAX_DAY_OF_MONTH, FIELD_DAY_OF_MONTH, null);
    }

    private void validateMonth(String expr) {
        validateField(expr, MIN_MONTH, MAX_MONTH, FIELD_MONTH, SymbolicValueMapper.MONTHS);
    }

    private void validateDayOfWeek(String expr) {
        validateField(expr, MIN_DAY_OF_WEEK, MAX_DAY_OF_WEEK, FIELD_DAY_OF_WEEK, SymbolicValueMapper.DAYS_OF_WEEK);
    }

    private void validateYear(String expr) {
        validateField(expr, MIN_YEAR, MAX_YEAR, FIELD_YEAR , null);
    }

    private void validateField(String expr, int min, int max, String fieldName, Map<String, Integer> symbolicMap) {
        String[] parts = expr.trim().toUpperCase().split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.equals("*")) continue;

            if (part.contains("/")) {
                validateStep(part, min, max, fieldName, symbolicMap);
            } else if (part.contains("-")) {
                validateRange(part, min, max, fieldName, symbolicMap);
            } else {
                validateIndividualValue(part, min, max, fieldName, symbolicMap);
            }
        }
    }

    private void validateStep(String part, int min, int max, String fieldName, Map<String, Integer> symbolicMap) {
        String[] stepParts = part.split("/");
        if (stepParts.length != 2 || stepParts[1].isEmpty()) {
            throw exceptionForField(fieldName, "Invalid step syntax in part: '" + part + "'");
        }

        validateField(stepParts[0], min, max, fieldName, symbolicMap);

        if (!NUMBER.matcher(stepParts[1]).matches()) {
            throw exceptionForField(fieldName, "Step value must be numeric: '" + stepParts[1] + "'");
        }
    }

    private void validateRange(String part, int min, int max, String fieldName, Map<String, Integer> symbolicMap) {
        String[] range = part.split("-");
        if (range.length != 2) {
            throw exceptionForField(fieldName, "Range format invalid: '" + part + "'");
        }

        int start = parseValue(range[0], symbolicMap, fieldName);
        int end = parseValue(range[1], symbolicMap, fieldName);

        if (!((start >= min && start <= max) && (end >= min && end <= max))) {
            throw exceptionForField(fieldName, "Range [" + start + "-" + end + "] out of bounds (" + min + "-" + max + ")");
        }
    }

    private void validateIndividualValue(String part, int min, int max, String fieldName, Map<String, Integer> symbolicMap) {
        int value = parseValue(part, symbolicMap, fieldName);
        if (value < min || value > max) {
            throw exceptionForField(fieldName, "Value " + value + " out of bounds (" + min + "-" + max + ")");
        }
    }

    private int parseValue(String token, Map<String, Integer> symbolicMap, String fieldName) {
        if (NUMBER.matcher(token).matches()) {
            return Integer.parseInt(token);
        } else if (symbolicMap != null && symbolicMap.containsKey(token)) {
            return symbolicMap.get(token);
        } else {
            throw exceptionForField(fieldName, "Invalid symbolic value: '" + token + "'");
        }
    }

    private void validateCommand(String command) {
        if (command == null || command.trim().isEmpty()) {
            throw new CommandInvalidException("Command field must not be empty.");
        }
    }

    private CronExpressionInvalidException exceptionForField(String fieldName, String message) {
        switch (fieldName) {
            case FIELD_MINUTE:
                return new MinuteInvalidException(message);
            case FIELD_HOUR:
                return new HourInvalidException(message);
            case FIELD_DAY_OF_MONTH:
                return new DayOfMonthInvalidException(message);
            case FIELD_MONTH:
                return new MonthInvalidException(message);
            case FIELD_DAY_OF_WEEK:
                return new DayOfWeekInvalidException(message);
            case FIELD_YEAR:
                    return new YearInvalidException(message);
            default:
                return new CronExpressionInvalidException(message);
        }
    }

}