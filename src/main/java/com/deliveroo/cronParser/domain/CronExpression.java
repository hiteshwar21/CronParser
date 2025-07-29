package com.deliveroo.cronParser.domain;

import com.deliveroo.cronParser.builder.*;
import lombok.Getter;

import java.util.List;
@Getter
public class CronExpression {
    private final List<Integer> minutes;
    private final List<Integer> hours;
    private final List<Integer> daysOfMonth;
    public final List<Integer> months;
    private final List<Integer> daysOfWeek;
    private final String command;        // You may or may not need this for occurrence generation

    public CronExpression(List<Integer> minutes, List<Integer> hours,
                          List<Integer> daysOfMonth, List<Integer> months,
                          List<Integer> daysOfWeek, String command) {
        this.minutes = minutes;
        this.hours = hours;
        this.daysOfMonth = daysOfMonth;
        this.months = months;
        this.daysOfWeek = daysOfWeek;
        this.command = command;
    }

    // Getters...

    // Factory method to build from String[] using your builders
    public static CronExpression fromFields(String[] fields) {
        MinutesFieldBuilder mfb = new MinutesFieldBuilder();
        HoursFieldBuilder hfb = new HoursFieldBuilder();
        DayOfMonthFieldBuilder dofb = new DayOfMonthFieldBuilder();
        MonthFieldBuilder mthfb = new MonthFieldBuilder();
        DayOfWeekFieldBuilder dowfb = new DayOfWeekFieldBuilder();

        List<Integer> minutes = mfb.expand(fields[0]);
        List<Integer> hours = hfb.expand(fields[1]);
        List<Integer> daysOfMonth = dofb.expand(fields[2]);
        List<Integer> months = mthfb.expand(fields[3]);
        List<Integer> daysOfWeek = dowfb.expand(fields[4]);

        String command = fields.length >= 6 ? fields[5] : "";

        return new CronExpression(minutes, hours, daysOfMonth, months, daysOfWeek, command);
    }
}

