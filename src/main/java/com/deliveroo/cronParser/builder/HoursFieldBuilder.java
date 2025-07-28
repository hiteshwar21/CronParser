package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;

public class HoursFieldBuilder extends AbstractCronFieldBuilder {
    public HoursFieldBuilder() {
        super(CronConstants.MIN_HOUR, CronConstants.MAX_HOUR);
    }
}