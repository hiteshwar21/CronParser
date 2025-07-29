# Project Description

This is a Java-based implementation of a Cron Expression Parser.
It accepts a single cron expression as a string or a file containing the cron expression and generates the expanded schedule for each field in a structured table. The output includes individual times for minute, hour, day of month, month, and day of week, along with the command to be executed.

## Requirements

The project is built using Java 11+. It uses Maven for dependency and build management.

1. Java – 11 or higher
2. Maven – 3.x.x

## Building the application using maven

You can build and package the application in the form of a standalone executable JAR using Maven:

```sh
mvn clean package
```

The above command will generate a jar file named:
`cronparser.jar`
Located in the `target/` directory.

## Running tests

The `mvn package` command runs all the unit tests and packages the jar.
To run the test suite separately (without packaging), run:

```sh
mvn test
```

## Running the application

Using a Cron Expression as Argument
1. You can run the application directly from the CLI:
```sh
java -jar target/cronparser.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```
2. Using a File as Input (Batch Mode)
```sh
java -jar target/cronparser.jar file_input.txt
```
Where input.txt contains multiple cron expressions (one per line).

# Problem Statement

Cron Expression Parser
Write a command-line application that parses a cron string and expands each field to show the values at which it will run.
You should support standard cron format:

1. minute (0–59)
2. hour (0–23)
3. day of month (1–31)
4. month (1–12)
5. day of week (0–6)
6. command

A valid cron expression includes five time fields + the command.

Input is a single line cron string passed as a command-line argument.

Example:
Given the expression:

```sh
*/15 0 1,15 * 1-5 /usr/bin/find
```
The expected output is:

```sh
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Supported Features
1. Parsing of wildcards (*), ranges (1-5), lists (1,5,10), and steps (*/15)
2. Detailed validation with field-specific error messages
3. Output formatted as a table (14 char column spacing as per spec)
4. Unit test coverage for all logic (Input, Parser, Builder, Validation)

## Practice Additions
1. Add Extra Fields
   - validateFieldLength - Need to update to accept 7 values too
   - create validateYear
   - create YearInvalidException
   - Add Constants
   - create YearFieldBuilder
   - CronParserDescriptionServiceImpl changes - resolveFieldConfigs
   - Test Cases
   
2. Add Symbol Name Support
   - Create SymbolicValueMapper
   - Update validateMonth, validateDayOfWeek & parseValue in ValidationService
   - Override Abstract class by creating parseValue in Builders
   - Test Cases

3. Wrap Around Notation
   - Discuss if next hour is something we are handling
   - update validateRange
   - update getRange
   
4. Add Batching Support
   - error handling gracefully
   - Modify ClientInput Interface to take a List
   - Update CLIClient to return Collections.singletonList(cronString)
   - Update FileClient to read & return List<String> lines
   - Update Test Cases
   
5. Add @Yearly, @Monthly Support
   - Create an independent mapper to handle the annotation
   - Make changes to validationService based on length and remember to return
   - Add one more check at Handler level based on fieldLength (String is immutable, use a defensive Copy and append)
   - Update Test Cases
6. Add # - Nth Occurrence()
7. Next N occurrence Support
8. Handle SubCommands