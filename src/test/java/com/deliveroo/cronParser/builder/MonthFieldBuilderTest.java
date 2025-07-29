package com.deliveroo.cronParser.builder;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MonthFieldBuilderTest {

    private final MonthFieldBuilder builder = new MonthFieldBuilder();

    @Test
    void describe_wildcard() {
        assertEquals(
                "1 2 3 4 5 6 7 8 9 10 11 12",
                builder.describe("*")
        );
    }

    @Test
    void describe_Symbols() {
        assertEquals(
                "8",
                builder.describe("Aug")
        );
    }
}
