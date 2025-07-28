package com.deliveroo.cronParser.handler;

import com.deliveroo.cronParser.exception.MinuteInvalidException;
import com.deliveroo.cronParser.service.CronParserDescriptionService;
import com.deliveroo.cronParser.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.deliveroo.cronParser.utils.StringUtils;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionHandlerTest {

    private StringUtils stringUtils;
    private ValidationService validationService;
    private CronParserDescriptionService descriptionService;
    private CronExpressionHandler handler;

    @BeforeEach
    void setUp() {
        stringUtils = mock(StringUtils.class);
        validationService = mock(ValidationService.class);
        descriptionService = mock(CronParserDescriptionService.class);

        handler = new CronExpressionHandler(stringUtils, validationService, descriptionService);
    }

    @Test
    void handle_validCronExpression_returnsFormattedOutput() {
        // Arrange
        String rawInput = "*/15 0 1,15 * 1-5 /usr/bin/find";
        String[] parsedFields = new String[]{"*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"};
        String mockOutput = "minute        0 15 30 45\nhour          0\n...";

        when(stringUtils.cleanAndNormalize(rawInput)).thenReturn(parsedFields);
        doNothing().when(validationService).validate(parsedFields);
        when(descriptionService.describe(parsedFields)).thenReturn(mockOutput);

        // Act
        String result = handler.handle(rawInput);

        // Assert
        assertEquals(mockOutput, result);
        verify(stringUtils).cleanAndNormalize(rawInput);
        verify(validationService).validate(parsedFields);
        verify(descriptionService).describe(parsedFields);
    }

    @Test
    void handle_invalidCronExpression_shouldPropagateException() {
        String rawInput = "61 0 * * * echo bad"; // invalid minute
        String[] parsedFields = new String[]{"61", "0", "*", "*", "*", "echo bad"};

        when(stringUtils.cleanAndNormalize(rawInput)).thenReturn(parsedFields);
        doThrow(new MinuteInvalidException("Value 61 out of range")).when(validationService).validate(parsedFields);

        MinuteInvalidException exception = assertThrows(
                MinuteInvalidException.class,
                () -> handler.handle(rawInput)
        );

        assertEquals("Minute field error: Value 61 out of range", exception.getMessage());
        verify(validationService).validate(parsedFields);
        verify(descriptionService, never()).describe(any());
    }

    @Test
    void handle_blankInput_shouldHandleEmptyGracefully() {
        String rawInput = "     ";
        String[] parsed = new String[0];

        when(stringUtils.cleanAndNormalize(rawInput)).thenReturn(parsed);
        doThrow(new IllegalArgumentException("No input provided")).when(validationService).validate(parsed);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> handler.handle(rawInput)
        );

        assertTrue(ex.getMessage().contains("No input"));
        verify(validationService).validate(parsed);
    }
}
