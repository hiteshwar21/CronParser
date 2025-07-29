package com.deliveroo.cronParser.input;

import java.util.List;

public interface ClientInput {
    List<String> readInput() throws Exception;
}
