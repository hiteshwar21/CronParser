package com.deliveroo.cronParser.builder;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DayOfMonthFieldBuilderTest {

    private final DayOfMonthFieldBuilder builder = new DayOfMonthFieldBuilder();

    @Test
    void expand_firstAndLastDay() {
        assertEquals(
                List.of(1, 31),
                builder.expand("1,31")
        );
    }

    @Test
    void describe_everyFifthDay() {
        assertEquals(
                "1 6 11 16 21 26 31",
                builder.describe("1-31/5")
        );
    }
}
