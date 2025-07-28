package com.deliveroo.cronParser.handler;

import com.deliveroo.cronParser.service.CronParserDescriptionServiceImpl;
import com.deliveroo.cronParser.service.ValidationServiceImpl;
import com.deliveroo.cronParser.utils.StringUtils;

public class CronExpressionHandlerFactory {
    public static CronExpressionHandler createDefault() {
        return new CronExpressionHandler(
                new StringUtils(),
                new ValidationServiceImpl(),
                new CronParserDescriptionServiceImpl()
        );
    }
}