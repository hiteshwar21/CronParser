package com.deliveroo.cronParser.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CronParserDescriptionServiceImplTest {

    private final CronParserDescriptionServiceImpl descService = new CronParserDescriptionServiceImpl();

    @Test
    void describeOutputs_for6Inputs() {
        String[] fields = {
                "*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"
        };
        String expected = "minute        0 15 30 45\n" +
                        "hour          0\n" +
                        "day of month  1 15\n" +
                        "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week   1 2 3 4 5\n" +
                        "command       /usr/bin/find";
        String result = descService.describe(fields);
        assertEquals(expected, result.trim());
    }

    @Test
    void describeOutputs_for7Inputs() {
        String[] fields = {
                "*/15", "0", "1,15", "*", "1-5", "2026", "/usr/bin/find"
        };
        String expected = "minute        0 15 30 45\n" +
                "hour          0\n" +
                "day of month  1 15\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   1 2 3 4 5\n" +
                "year          2026\n" +
                "command       /usr/bin/find";
        String result = descService.describe(fields);
        assertEquals(expected, result.trim());
    }

    @Test
    void describeOutputs_forSymbols() {
        String[] fields = {
                "*/15", "0", "1,15", "Jan-mAr", "WED-FRI", "/usr/bin/find"
        };
        String expected = "minute        0 15 30 45\n" +
                "hour          0\n" +
                "day of month  1 15\n" +
                "month         1 2 3\n" +
                "day of week   3 4 5\n" +
                "command       /usr/bin/find";
        String result = descService.describe(fields);
        assertEquals(expected, result.trim());
    }

    @Test
    void describe_handlesAllWildcards_With7Inputs() {
        String[] fields = { "*", "*", "*", "*", "*", "*", "echo test" };
        String result = descService.describe(fields);
        assertTrue(result.contains("minute"));
        assertTrue(result.contains("hour"));
        assertTrue(result.contains("month"));
        assertTrue(result.contains("year"));
        assertTrue(result.contains("command"));
        assertTrue(result.contains("12"));
        assertTrue(result.contains("6"));
        assertTrue(result.contains("2026"));
    }

    @Test
    void describe_handlesAllWildcards() {
        String[] fields = { "*", "*", "*", "*", "*", "*", "echo test" };
        String result = descService.describe(fields);
        assertTrue(result.contains("minute"));
        assertTrue(result.contains("hour"));
        assertTrue(result.contains("month"));
        assertTrue(result.contains("command"));
        assertTrue(result.contains("12"));
        assertTrue(result.contains("6"));
        assertTrue(result.contains("2027"));
    }

    @Test
    void describe_handlesEdgeValuesAndSpacing() {
        String[] fields = { "59", "23", "31", "12", "0", "ls -la" };
        String expected = "minute        59\n" +
                "hour          23\n" +
                "day of month  31\n" +
                "month         12\n" +
                "day of week   0\n" +
                "command       ls -la";
        String result = descService.describe(fields);
        assertEquals(expected, result.trim());
    }
}