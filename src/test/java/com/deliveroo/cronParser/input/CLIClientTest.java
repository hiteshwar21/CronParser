package com.deliveroo.cronParser.input;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CLIClientTest {
    @Test
    public void testReadInputReturnsCronString() throws Exception {
        String cronString = "*/15 0 1,15 * 1-5 /usr/bin/find";
        List<String> cronLines = new ArrayList<>();
        cronLines.add(cronString);
        CLIClient cliClient = new CLIClient(cronString);
        assertEquals(cronLines, cliClient.readInput());
    }
}