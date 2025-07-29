package com.deliveroo.cronParser.service;

import com.deliveroo.cronParser.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceImplTest {

    private final ValidationServiceImpl validator = new ValidationServiceImpl();

    // -------------------- VALID CASES -------------------------

    @Test
    void validate_validCronExpression_with6Params_shouldPass() {
        String[] fields = {"*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validate_validCronExpression_with7Params_shouldPass() {
        String[] fields = {"*/15", "0", "1,15", "*", "1-5", "2026", "/usr/bin/find"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validate_allWildcards_with6Params_shouldPass() {
        String[] fields = {"*", "*", "*", "*", "*", "run.sh"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validate_allWildcards_with7Params_shouldPass() {
        String[] fields = {"*", "*", "*", "*", "*", "*", "run.sh"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validate_symbols_shouldPass() {
        String[] fields = {"*", "*", "*", "Jan", "*", "*", "run.sh"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validate_annotation_shouldPass() {
        String[] fields = {"@yearly", "run.sh"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validateDayOfWeek_acceptsValidNthOccurrences_numeric() {
        assertDoesNotThrow(() -> validator.validate(new String[]{
                "0", "0", "1", "1", "1#1", "command"
        }));

        assertDoesNotThrow(() -> validator.validate(new String[]{
                "0", "0", "1", "1", "MON#1", "command"
        }));

        assertDoesNotThrow(() -> validator.validate(new String[]{
                "0", "0", "1", "1", "6#2", "command"
        }));
    }

    // -------------------- STRUCTURE ERRORS --------------------

    @Test
    void validateDayOfWeek_rejectsMalformedNthOccurrence_multipleHashes() {
        DayOfWeekInvalidException ex = assertThrows(DayOfWeekInvalidException.class, () ->
                validator.validate(new String[]{
                        "0", "0", "1", "1", "1#1#1", "command"
                })
        );
        assertTrue(ex.getMessage().contains("#"));
    }

    @Test
    void validate_missingFields_shouldThrow() {
        String[] fields = {"*/15", "0", "1,15", "*"}; // only 4 fields
        CronExpressionInvalidException ex = assertThrows(
                CronExpressionInvalidException.class,
                () -> validator.validate(fields)
        );
        assertTrue(ex.getMessage().contains("6 or 7 fields"));
    }

    @Test
    void validate_missingCommandWithMacro_shouldThrow() {
        String[] fields = {"@yearly"}; // only Macro
        CronExpressionInvalidException ex = assertThrows(
                CronExpressionInvalidException.class,
                () -> validator.validate(fields)
        );
        assertTrue(ex.getMessage().contains("6 or 7 fields"));
    }

    @Test
    void validate_nullArray_shouldThrow() {
        CronExpressionInvalidException ex = assertThrows(
                CronExpressionInvalidException.class,
                () -> validator.validate(null)
        );
        assertTrue(ex.getMessage().contains("6 or 7 fields"));
    }

    // -------------------- FIELD-SPECIFIC ERRORS --------------------

    @Test
    void validate_invalidMacro_shouldThrowAnnotationException() {
        String[] fields = {"@decade", "cmd"};
        Exception e = assertThrows(AnnotationInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("Annotation error"));
    }

    @Test
    void validate_invalidMinuteOutOfRange_shouldThrowMinuteException() {
        String[] fields = {"60", "*", "*", "*", "*", "cmd"};
        Exception e = assertThrows(MinuteInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("out of bounds"));
    }

    @Test
    void validate_invalidHour_shouldThrowHourException() {
        String[] fields = {"0", "24", "*", "*", "*", "cmd"};
        Exception e = assertThrows(HourInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("out of bounds"));
    }

    @Test
    void validate_invalidMonthSymbol_shouldThrowMonthException() {
        String[] fields = {"0", "0", "1", "JANX", "*", "cmd"};
        Exception e = assertThrows(MonthInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("Invalid symbolic value"));
    }

    @Test
    void validate_dayOfWeekOutOfRange_shouldThrowDayOfWeekException() {
        String[] fields = {"0", "0", "1", "1", "8", "run"};
        Exception e = assertThrows(DayOfWeekInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("out of bounds"));
    }

    @Test
    void validate_yearOutOfRange_shouldThrowYearException() {
        String[] fields = {"0", "0", "1", "1", "5", "1970", "run"};
        Exception e = assertThrows(YearInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("out of bounds"));
    }

    // -------------------- STEP & RANGE VALIDATIONS --------------------

    @Test
    void validate_invalidStepSyntax_shouldThrow() {
        String[] fields = {"*/", "*", "*", "*", "*", "run"};
        Exception e = assertThrows(MinuteInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("Invalid step syntax"));
    }

    @Test
    void validate_stepValueNonNumeric_shouldThrow() {
        String[] fields = {"*/x", "*", "*", "*", "*", "run"};
        Exception e = assertThrows(MinuteInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("Step value must be numeric"));
    }

    @Test
    void validate_rangeStartGreaterThanEnd_shouldAllow() {
        String[] fields = {"5-1", "*", "*", "*", "*", "run"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }

    @Test
    void validate_rangeOutOfBounds_shouldThrow() {
        String[] fields = {"0-100", "*", "*", "*", "*", "run"};
        Exception e = assertThrows(MinuteInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("out of bounds"));
    }

    // -------------------- COMMAND FIELD --------------------

    @Test
    void validate_commandIsEmpty_shouldThrow() {
        String[] fields = {"0", "0", "1", "1", "1", " "};
        Exception e = assertThrows(CommandInvalidException.class, () -> validator.validate(fields));
        assertTrue(e.getMessage().contains("Command field must not be empty"));
    }

    @Test
    void validate_validLongCommand_shouldPass() {
        String[] fields = {"0", "0", "1", "1", "1", "echo 'this is a long command with args'"};
        assertDoesNotThrow(() -> validator.validate(fields));
    }
}
