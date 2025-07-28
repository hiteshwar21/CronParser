package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;

public class DayOfMonthFieldBuilder extends AbstractCronFieldBuilder {
    public DayOfMonthFieldBuilder() {
        super(CronConstants.MIN_DAY_OF_MONTH, CronConstants.MAX_DAY_OF_MONTH);
    }
}