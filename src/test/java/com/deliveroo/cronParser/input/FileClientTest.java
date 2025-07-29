package com.deliveroo.cronParser.input;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileClientTest {

    @TempDir
    Path tempDir;

    @Test
    public void testReadInputReturnsFileContents() throws Exception {
        Path file = tempDir.resolve("testfile.txt");
        List<String> lines = List.of(
                "*/15 0 1,15 * 1-5 /usr/bin/find",
                "0 0 * * * /bin/echo"
        );
        Files.write(file, lines);

        FileClient fileClient = new FileClient(file.toString());
        List<String> result = fileClient.readInput();

        assertEquals(lines, result);
    }

    @Test
    public void testReadInputFileNotFound() {
        FileClient fileClient = new FileClient("nonexistentfile.txt");
        Exception exception = assertThrows(Exception.class, fileClient::readInput);
        String msg = exception.getMessage();
        assertNotNull(msg);
        assertTrue(msg.contains("nonexistentfile.txt") || msg.contains("No such file"));
    }
}