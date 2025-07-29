package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;
import com.deliveroo.cronParser.utils.SymbolicValueMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
}