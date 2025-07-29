package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;
import com.deliveroo.cronParser.utils.SymbolicValueMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MonthFieldBuilder extends AbstractCronFieldBuilder {
    public MonthFieldBuilder() {
        super(CronConstants.MIN_MONTH, CronConstants.MAX_MONTH);
    }


    private static final Pattern NUMBER = Pattern.compile("\\d+");

    @Override
    protected int parseValue(String part) {
        if (NUMBER.matcher(part).matches()) {
            return Integer.parseInt(part);
        }
        Integer symbolic = SymbolicValueMapper.getMonth(part);
        if (symbolic != null) return symbolic;
        throw new IllegalArgumentException("Invalid month: " + part);
    }
}