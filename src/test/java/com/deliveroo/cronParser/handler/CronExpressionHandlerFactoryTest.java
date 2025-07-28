package com.deliveroo.cronParser.handler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CronExpressionHandlerFactoryTest {

    @Test
    void createDefault_returnsValidCronExpressionHandler() {
        CronExpressionHandler handler = CronExpressionHandlerFactory.createDefault();
        assertNotNull(handler);
    }
}