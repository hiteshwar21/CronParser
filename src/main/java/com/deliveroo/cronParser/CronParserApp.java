package com.deliveroo.cronParser;

import com.deliveroo.cronParser.exception.*;
import com.deliveroo.cronParser.handler.CronExpressionHandler;
import com.deliveroo.cronParser.handler.CronExpressionHandlerFactory;
import com.deliveroo.cronParser.input.ClientInput;
import com.deliveroo.cronParser.input.ClientInputFactory;
public class CronParserApp {
    public static void main(String[] args) {
        try {
            // Delegate input acquisition to the input package
            ClientInput clientInput = ClientInputFactory.getClientInput(args);
            String rawInput = clientInput.readInput();

            // Validate, parse, and print
            CronExpressionHandler cronExpressionHandler = CronExpressionHandlerFactory.createDefault();
            String output = cronExpressionHandler.handle(rawInput);
            System.out.println(output);
        } catch (MinuteInvalidException | CommandInvalidException | DayOfWeekInvalidException | MonthInvalidException | HourInvalidException | DayOfMonthInvalidException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (CronExpressionInvalidException e) {
            System.err.println("Cron validation error: " + e.getMessage());
            System.exit(1);
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
