package com.deliveroo.cronParser.input;

import java.util.Collections;
import java.util.List;

public class CLIClient implements ClientInput {
    private final String cronString;
    public CLIClient(String cronString) {
        this.cronString = cronString;
    }

    @Override
    public List<String> readInput() {
        // Return a list with the single CLI expression
        return Collections.singletonList(cronString);
    }
}
