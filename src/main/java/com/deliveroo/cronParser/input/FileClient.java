package com.deliveroo.cronParser.input;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileClient implements ClientInput {
    private final String fileName;
    public FileClient(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> readInput() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        // Filter out empty or blank lines (if desired)
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());
    }
}
