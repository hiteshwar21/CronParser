package com.deliveroo.cronParser.input;

public class ClientInputFactory {
    public static ClientInput getClientInput(String[] args) {
        if(args.length < 1) {
            throw new IllegalArgumentException("No input provided");
        }
        String arg = args[0];

        if(arg.endsWith(".txt")) {
            return new FileClient(arg);
        } else {
            return new CLIClient(arg);
        }
    }
}
