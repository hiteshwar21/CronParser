package com.deliveroo.cronParser.builder;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DayOfWeekFieldBuilderTest {

    private final DayOfWeekFieldBuilder builder = new DayOfWeekFieldBuilder();

    @Test
    void describe_wildcard() {
        assertEquals(
                "0 1 2 3 4 5 6",
                builder.describe("*")
        );
    }

    @Test
    void describe_Symbols() {
        assertEquals(
                "3",
                builder.describe("Wed")
        );
    }

    @Test
    void expand_and_describe_singleNthOccurrence() {
        // For input "1#2" (2nd Monday), expand returns encoded int 12 (1 * 10 + 2)
        List<Integer> expanded = builder.expand("1#2");
        assertEquals(1, expanded.size());
        assertEquals(12, expanded.get(0).intValue());

        // Describe should return the encoded integer as string
        String description = builder.describe("1#2");
        assertEquals("12", description);
    }

    @Test
    void expand_and_describe_multipleNthOccurrences_withNormalValues() {
        // Example expression contains nth occurrence and normal day
        List<Integer> expanded = builder.expand("0#1,3");
        // 0#1 -> encoded 1 (0 * 10 + 1 = 1? Actually 0*10+1=1 — but per earlier code it would be 0*10+1=1)
        // 3 is just ordinary day 3 (Wed)
        assertEquals(2, expanded.size());
        assertTrue(expanded.contains(1));  // Encoded nth occurrence
        assertTrue(expanded.contains(3));  // Normal day

        // Description returns string joined with space
        String description = builder.describe("0#1,3");
        // Since encode is 1 and 3, sorted output: "1 3"
        assertEquals("1 3", description);
    }

    @Test
    void expand_and_describe_invalidDay() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.expand("8#2"));
        String expectedMessage = "Invalid day of week";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
