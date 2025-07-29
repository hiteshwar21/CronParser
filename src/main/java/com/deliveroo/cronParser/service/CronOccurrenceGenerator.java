package com.deliveroo.cronParser.service;

import com.deliveroo.cronParser.domain.CronExpression;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CronOccurrenceGenerator {

    private final CronExpression cronExpression;
    private final ZoneId zone;

    public CronOccurrenceGenerator(CronExpression cronExpression, ZoneId zone) {
        this.cronExpression = cronExpression;
        this.zone = zone;
    }

    // Get next N occurrences from 'start' datetime inclusive
    public List<ZonedDateTime> getNextOccurrences(ZonedDateTime start, int count) {
        List<ZonedDateTime> results = new ArrayList<>();
        ZonedDateTime current = start.minusMinutes(1); // so start is inclusive on next check

        while (results.size() < count) {
            current = getNextValidTimeAfter(current);
            results.add(current);
        }
        return results;
    }

    // Helper to find next valid cron time strictly after 'afterTime'
    private ZonedDateTime getNextValidTimeAfter(ZonedDateTime afterTime) {
        ZonedDateTime next = afterTime.plusMinutes(1).withSecond(0).withNano(0);

        while (true) {
            if (!cronExpression.months.contains(next.getMonthValue())) {
                next = next.withDayOfMonth(1).withHour(0).withMinute(0).plusMonths(1);
                continue;
            }

            if (!cronExpression.getMonths().contains(next.getDayOfMonth()) &&
                    !cronExpression.getDaysOfWeek().contains(dayOfWeekToCron(next.getDayOfWeek()))) {
                next = next.plusDays(1).withHour(0).withMinute(0);
                continue;
            }

            if (!cronExpression.getHours().contains(next.getHour())) {
                next = next.withMinute(0).plusHours(1);
                continue;
            }

            if (!cronExpression.getMinutes().contains(next.getMinute())) {
                next = next.plusMinutes(1);
                continue;
            }

            // All fields match cron expression
            return next;
        }
    }

    // Converts java.time.DayOfWeek (1=Mon..7=Sun) to cron representation (0=Sun..6=Sat)
    private int dayOfWeekToCron(DayOfWeek dow) {
        return dow == DayOfWeek.SUNDAY ? 0 : dow.getValue();
    }
}

