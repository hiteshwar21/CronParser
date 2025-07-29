package com.deliveroo.cronParser.input;

public class CLIClient implements ClientInput{
    private final String cronString;

    public CLIClient(String cronString) {
        this.cronString = cronString;
    }

    @Override
    public String readInput() {
        return cronString;
    }
}
