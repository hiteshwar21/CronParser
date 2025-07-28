package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;

import java.util.HashMap;
import java.util.Map;

public class DayOfWeekFieldBuilder extends AbstractCronFieldBuilder {
    public DayOfWeekFieldBuilder() {
        super(CronConstants.MIN_DAY_OF_WEEK, CronConstants.MAX_DAY_OF_WEEK);
    }
}