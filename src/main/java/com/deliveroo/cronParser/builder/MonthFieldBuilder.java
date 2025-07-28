package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;

import java.util.HashMap;
import java.util.Map;

public class MonthFieldBuilder extends AbstractCronFieldBuilder {
    public MonthFieldBuilder() {
        super(CronConstants.MIN_MONTH, CronConstants.MAX_MONTH);
    }
}