package com.deliveroo.cronParser.input;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClientInputFactoryTest {

    @Test
    public void testGetClientInputReturnsFileClientForTxtFile() {
        String[] args = new String[]{"input.txt"};
        ClientInput clientInput = ClientInputFactory.getClientInput(args);
        assertInstanceOf(FileClient.class, clientInput);
    }

    @Test
    public void testGetClientInputReturnsCLIClientForNotATxtFile() {
        String[] args = new String[]{"*/15 0 1,15 * 1-5 /usr/bin/find"};
        ClientInput clientInput = ClientInputFactory.getClientInput(args);
        assertInstanceOf(CLIClient.class, clientInput);
    }

    @Test
    public void testGetClientInputThrowsExceptionWhenNoArgs() {
        String[] args = new String[]{};
        assertThrows(IllegalArgumentException.class, () -> ClientInputFactory.getClientInput(args));
    }
}
