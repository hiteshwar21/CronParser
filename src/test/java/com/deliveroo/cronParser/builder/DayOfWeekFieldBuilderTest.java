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
}
