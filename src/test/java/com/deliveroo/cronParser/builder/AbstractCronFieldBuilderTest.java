package com.deliveroo.cronParser.builder;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AbstractCronFieldBuilderTest {

    // Concrete test subclass for testing
    private static class TestFieldBuilder extends AbstractCronFieldBuilder {
        public TestFieldBuilder() {
            super(1, 10);  // min/max for the use case
        }
    }

    private final AbstractCronFieldBuilder builder = new TestFieldBuilder();

    // Wildcard expands full range
    @Test
    void testExpandWildcard() {
        List<Integer> result = builder.expand("*");
        assertEquals(List.of(1,2,3,4,5,6,7,8,9,10), result);
    }

    // List parsing works
    @Test
    void testExpandCommaList() {
        List<Integer> result = builder.expand("1,3,5");
        assertEquals(List.of(1,3,5), result);
    }

    // Ranges are handled correctly
    @Test
    void testExpandRange() {
        List<Integer> result = builder.expand("2-4");
        assertEquals(List.of(2,3,4), result);
    }

    // Step range expands correctly
    @Test
    void testExpandStepWithinRange() {
        List<Integer> result = builder.expand("1-5/2");
        assertEquals(List.of(1,3,5), result);
    }

    // Whole wildcard with step
    @Test
    void testExpandWildcardWithStep() {
        List<Integer> result = builder.expand("*/3");
        assertEquals(List.of(1,4,7,10), result);  // 1–10, step 3 starting at index 0
    }

    // Step over single range boundary
    @Test
    void testStepExceedingRange() {
        List<Integer> result = builder.expand("1-10/100");  // step larger than range
        assertEquals(List.of(1), result);  // only first value
    }

    // Edge case: out-of-bound values are trimmed
    @Test
    void testOutOfBoundsAreTrimmed() {
        List<Integer> result = builder.expand("0,5,12");
        assertEquals(List.of(5), result);  // only 5 within 1–10
    }

    // Single value input
    @Test
    void testSingleValue() {
        List<Integer> result = builder.expand("6");
        assertEquals(List.of(6), result);
    }

    // describe joins output as string
    @Test
    void testDescribeGeneratesFormattedOutput() {
        String result = builder.describe("2-4");
        assertEquals("2 3 4", result);
    }
}
