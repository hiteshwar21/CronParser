package com.deliveroo.cronParser.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CronParserDescriptionServiceImplTest {

    private final CronParserDescriptionServiceImpl descService = new CronParserDescriptionServiceImpl();

    @Test
    void describe_outputs_expected_table_for_typical_case() {
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
    void describe_handles_all_wildcards() {
        String[] fields = { "*", "*", "*", "*", "*", "echo test" };
        String result = descService.describe(fields);
        assertTrue(result.contains("minute"));
        assertTrue(result.contains("hour"));
        assertTrue(result.contains("month"));
        assertTrue(result.contains("command"));
        // Check full expansion for month (should contain "12") and day of week (should contain "6")
        assertTrue(result.contains("12"));
        assertTrue(result.contains("6"));
    }

   /* @Test
    void describe_handles_symbolic_names() {
        String[] fields = { "0", "0", "1", "JAN,MAR,DEC", "MON-WED", "testcmd" };
        String result = descService.describe(fields);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("3"));   // March
        assertTrue(result.contains("12"));  // December
        assertTrue(result.contains("1 2 3")); // Monday, Tuesday, Wednesday expanded
    }

    @Test
    void describe_handles_edge_values_and_spacing() {
        String[] fields = { "59", "23", "31", "12", "0", "ls -la" };
        String result = descService.describe(fields);
        assertTrue(result.contains("59"));
        assertTrue(result.contains("23"));
        assertTrue(result.contains("31"));
        assertTrue(result.contains("12"));
        assertTrue(result.contains("0"));
        // Alignment: tokens should line up under expected field names
        String[] lines = result.split("\\n");
        for (String line : lines) {
            // All rows must start with their field label, left-aligned to 14 chars
            assertEquals(14, line.indexOf(line.trim().split("\\s+")[1]));
        }
    }*/
}