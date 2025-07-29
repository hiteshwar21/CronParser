package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;

public class YearFieldBuilder extends AbstractCronFieldBuilder {
    public YearFieldBuilder() {
        super(CronConstants.MIN_YEAR, CronConstants.MAX_YEAR);
    }
}
