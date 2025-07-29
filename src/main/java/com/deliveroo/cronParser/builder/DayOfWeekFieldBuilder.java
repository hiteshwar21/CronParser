package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;
import com.deliveroo.cronParser.utils.SymbolicValueMapper;

import java.util.*;
import java.util.regex.Pattern;

import static com.deliveroo.cronParser.utils.StringUtils.NTH_OCCURRENCE_PATTERN;

public class DayOfWeekFieldBuilder extends AbstractCronFieldBuilder {
    public DayOfWeekFieldBuilder() {
        super(CronConstants.MIN_DAY_OF_WEEK, CronConstants.MAX_DAY_OF_WEEK);
    }

    private static final Pattern NUMBER = Pattern.compile("\\d+");
    @Override
    protected int parseValue(String part) {
        if (NUMBER.matcher(part).matches()) {
            return Integer.parseInt(part);
        }
        Integer symbolic = SymbolicValueMapper.getDayOfWeek(part);
        if (symbolic != null) return symbolic;
        throw new IllegalArgumentException("Invalid day of week: " + part);
    }

    @Override
    public List<Integer> expand(String expression) {
        List<Integer> results = new ArrayList<>();
        String[] parts = expression.toUpperCase().split(",");

        for (String part : parts) {
            if (NTH_OCCURRENCE_PATTERN.matcher(part).matches()) {
                // Simplified: encode as dayOfWeek * 10 + nth for differentiation
                String[] split = part.split("#");
                int dayOfWeek = parseValue(split[0]);
                int nth = Integer.parseInt(split[1]);
                results.add(dayOfWeek * 10 + nth);
            } else if (part.contains("/")) {
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
        // Same filtering and sorting
        results.removeIf(n -> n < min || n > max * 10 + 5);
        Collections.sort(results);
        return results;
    }
}