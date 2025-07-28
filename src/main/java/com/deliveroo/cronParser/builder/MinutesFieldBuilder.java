package com.deliveroo.cronParser.builder;

import com.deliveroo.cronParser.utils.CronConstants;

public class MinutesFieldBuilder extends AbstractCronFieldBuilder {
    public MinutesFieldBuilder() {
        super(CronConstants.MIN_MINUTE, CronConstants.MAX_MINUTE);
    }
}
