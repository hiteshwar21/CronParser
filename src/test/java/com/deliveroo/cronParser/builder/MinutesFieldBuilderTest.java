package com.deliveroo.cronParser.builder;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MinutesFieldBuilderTest {

    private final MinutesFieldBuilder builder = new MinutesFieldBuilder();

    @Test
    void expand_every15Minutes() {
        assertEquals(
                List.of(0, 15, 30, 45),
                builder.expand("*/15")
        );
    }

    @Test
    void expand_singleMinute() {
        assertEquals(
                List.of(22),
                builder.expand("22")
        );
    }

    @Test
    void expand_rangeAndList() {
        assertEquals(
                List.of(5, 6, 7, 9),
                builder.expand("5-7,9")
        );
    }

    @Test
    void describe_combinedInputs() {
        assertEquals(
                "0 10 20 30 40 50",
                builder.describe("0-50/10")
        );
    }
}
