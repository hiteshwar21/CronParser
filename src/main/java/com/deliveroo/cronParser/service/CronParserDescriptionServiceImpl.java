package com.deliveroo.cronParser.service;

import com.deliveroo.cronParser.builder.*;
import com.deliveroo.cronParser.utils.StringUtils;

import java.util.List;
import static com.deliveroo.cronParser.utils.CronConstants.*;

public class CronParserDescriptionServiceImpl implements CronParserDescriptionService {

    private static class FieldConfig {
        final String label;
        final CronFieldBuilder builder;
        final int fieldIndex;

        FieldConfig(String label, CronFieldBuilder builder, int fieldIndex) {
            this.label = label;
            this.builder = builder;
            this.fieldIndex = fieldIndex;
        }
    }

    // Registering field order and builders as a List
    private List<FieldConfig> resolveFieldConfigs(int numFields) {
        if (numFields == 6) {
            return List.of(
                    new FieldConfig(FIELD_MINUTE, new MinutesFieldBuilder(), 0),
                    new FieldConfig(FIELD_HOUR, new HoursFieldBuilder(), 1),
                    new FieldConfig(FIELD_DAY_OF_MONTH, new DayOfMonthFieldBuilder(), 2),
                    new FieldConfig(FIELD_MONTH, new MonthFieldBuilder(), 3),
                    new FieldConfig(FIELD_DAY_OF_WEEK, new DayOfWeekFieldBuilder(), 4)
            );
        } else if (numFields == 7) {
            return List.of(
                    new FieldConfig(FIELD_MINUTE, new MinutesFieldBuilder(), 0),
                    new FieldConfig(FIELD_HOUR, new HoursFieldBuilder(), 1),
                    new FieldConfig(FIELD_DAY_OF_MONTH, new DayOfMonthFieldBuilder(), 2),
                    new FieldConfig(FIELD_MONTH, new MonthFieldBuilder(), 3),
                    new FieldConfig(FIELD_DAY_OF_WEEK, new DayOfWeekFieldBuilder(), 4),
                    new FieldConfig(FIELD_YEAR, new YearFieldBuilder(), 5)
            );
        } else {
            throw new IllegalArgumentException("Unsupported cron field count: " + numFields);
        }
    }


    @Override
    public String describe(String[] fields) {
        StringBuilder output = new StringBuilder();
        List<FieldConfig> fieldConfigs = resolveFieldConfigs(fields.length);
        for (FieldConfig config : fieldConfigs) {
            String expanded = config.builder.describe(fields[config.fieldIndex]);
            output.append(StringUtils.formatRow(config.label, expanded, FIELD_LABEL_WIDTH));
        }
        int commandIndex = fieldConfigs.size();
        output.append(StringUtils.formatRow("command", fields[commandIndex], FIELD_LABEL_WIDTH));
        return output.toString();
    }
}
