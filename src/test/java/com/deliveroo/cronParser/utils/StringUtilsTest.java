package com.deliveroo.cronParser.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {
    private final StringUtils util = new StringUtils();

    @Test
    public void testNormalInput() {
        String input = "*/15 0 1,15 * 1-5 /usr/bin/find";
        String[] expected = {"*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"};
        assertArrayEquals(expected, util.cleanAndNormalize(input));
    }

    @Test
    public void testLeadingAndTrailingSpaces() {
        String input = "  0 12  * *  ";
        String[] expected = {"0", "12", "*", "*"};
        assertArrayEquals(expected, util.cleanAndNormalize(input));
    }

    @Test
    public void testMultipleSpacesBetweenWords() {
        String input = "0   12    *  *";
        String[] expected = {"0", "12", "*", "*"};
        assertArrayEquals(expected, util.cleanAndNormalize(input));
    }

    @Test
    public void testEmptyString() {
        String input = "";
        String[] expected = {""};
        assertArrayEquals(expected, util.cleanAndNormalize(input));
    }

    @Test
    public void testWhitespaceOnly() {
        String input = "      ";
        String[] expected = {""};
        assertArrayEquals(expected, util.cleanAndNormalize(input));
    }
}
