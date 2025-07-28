package com.deliveroo.cronParser.builder;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HoursFieldBuilderTest {

    private final HoursFieldBuilder builder = new HoursFieldBuilder();

    @Test
    void expand_everyOtherHour() {
        assertEquals(
                List.of(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22),
                builder.expand("*/2")
        );
    }

    @Test
    void expand_range() {
        assertEquals(
                List.of(8, 9, 10),
                builder.expand("8-10")
        );
    }

    @Test
    void expand_list() {
        assertEquals(
                List.of(1, 5, 23),
                builder.expand("1,5,23")
        );
    }
}
