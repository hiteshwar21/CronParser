package com.deliveroo.cronParser.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCronFieldBuilder implements CronFieldBuilder {
    protected final int min;
    protected final int max;

    public AbstractCronFieldBuilder(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public List<Integer> expand(String expression) {
        List<Integer> results = new ArrayList<>();
        String[] parts = expression.split(",");

        for (String part : parts) {
            if (part.contains("/")) {
                String[] stepParts = part.split("/");
                List<Integer> range = getRange(stepParts[0]);
                int step = Integer.parseInt(stepParts[1]);

                for (int i = 0; i < range.size(); i += step) {
                    results.add(range.get(i));
                }
            } else {
                results.addAll(getRange(part));
            }
        }

        results.removeIf(n -> n < min || n > max);
        Collections.sort(results);
        return results;
    }

    private List<Integer> getRange(String part) {
        List<Integer> range = new ArrayList<>();

        if (part.equals("*")) {
            for (int i = min; i <= max; i++) {
                range.add(i);
            }
        } else if (part.contains("-")) {
            String[] bounds = part.split("-");
            int start = parseValue(bounds[0]);
            int end = parseValue(bounds[1]);
            if (start <= end) {
                // Normal range, e.g., 1-5
                for (int i = start; i <= end; i++) {
                    range.add(i);
                }
            } else {
                // Wrap-around range, e.g., 22-2
                for (int i = start; i <= max; i++) {
                    range.add(i);
                }
                for (int i = min; i <= end; i++) {
                    range.add(i);
                }
            }
        } else {
            range.add(parseValue(part));
        }

        return range;
    }

    @Override
    public String describe(String expression) {
        List<Integer> expanded = expand(expression);
        StringBuilder sb = new StringBuilder();
        for (int val : expanded) {
            sb.append(val).append(" ");
        }
        return sb.toString().trim();
    }

    protected int parseValue(String part) {
        return Integer.parseInt(part);
    }
}

