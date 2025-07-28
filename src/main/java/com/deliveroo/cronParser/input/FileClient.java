package com.deliveroo.cronParser.input;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileClient implements ClientInput{
    private final String fileName;

    public FileClient(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String readInput() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}