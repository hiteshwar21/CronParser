package com.deliveroo.cronParser.builder;

import java.util.List;

public interface CronFieldBuilder {
    List<Integer> expand(String expression);
    String describe(String expression);
}
