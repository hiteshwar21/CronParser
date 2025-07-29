package com.deliveroo.cronParser;

import com.deliveroo.cronParser.exception.*;
import com.deliveroo.cronParser.handler.CronExpressionHandler;
import com.deliveroo.cronParser.handler.CronExpressionHandlerFactory;
import com.deliveroo.cronParser.input.ClientInput;
import com.deliveroo.cronParser.input.ClientInputFactory;

import java.util.List;

public class CronParserApp {
    public static void main(String[] args) {
        try {
            // Delegate input acquisition to the input package
            ClientInput clientInput = ClientInputFactory.getClientInput(args);
            List<String> cronLines = clientInput.readInput();

            CronExpressionHandler handler = CronExpressionHandlerFactory.createDefault();
            boolean errorOccurred = false;
            int lineNum = 1;
            // Validate, parse, and print
            for (String line : cronLines) {
                try {
                    System.out.println("---");  // Separator between entries
                    String output = handler.handle(line);
                    System.out.println(output);
                } catch (MinuteInvalidException | CommandInvalidException | DayOfWeekInvalidException | MonthInvalidException | HourInvalidException | DayOfMonthInvalidException e) {
                    System.err.println("Error at line " + lineNum + ": " + e.getMessage());
                    errorOccurred = true;
                } catch (CronExpressionInvalidException e) {
                    System.err.println("Cron validation error at line " + lineNum + ": " + e.getMessage());
                    errorOccurred = true;
                }
                lineNum++;
            }
            if (errorOccurred) {
                System.exit(1);
            } else {
                System.exit(0);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Usage:  java -jar target/cronparser.jar \"<cron_expression>\" or java -jar target/cronparser.jar <input_file.txt>");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(2);
        }
    }
}