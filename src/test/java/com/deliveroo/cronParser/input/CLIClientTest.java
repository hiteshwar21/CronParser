package com.deliveroo.cronParser.input;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CLIClientTest {
    @Test
    public void testReadInputReturnsCronString() throws Exception {
        String cronString = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CLIClient cliClient = new CLIClient(cronString);
        assertEquals(cronString, cliClient.readInput());
    }
}