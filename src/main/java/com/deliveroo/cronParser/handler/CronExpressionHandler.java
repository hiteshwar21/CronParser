package com.deliveroo.cronParser.handler;

import com.deliveroo.cronParser.service.CronParserDescriptionService;
import com.deliveroo.cronParser.service.ValidationService;
import com.deliveroo.cronParser.utils.StringUtils;

public class CronExpressionHandler {

    private final ValidationService validationService;
    private final CronParserDescriptionService parserService;
    private final StringUtils stringUtils;

    public CronExpressionHandler(StringUtils stringUtils, ValidationService validationService, CronParserDescriptionService parserService) {
        this.stringUtils = stringUtils;
        this.validationService = validationService;
        this.parserService = parserService;
    }

    /**
     * Receives raw input and normalize it for cron fields, validates, and generates table output.
     */
    public String handle(String rawInput) {
        String[] fields = stringUtils.cleanAndNormalize(rawInput);
        validationService.validate(fields);
        return parserService.describe(fields);
    }


}

