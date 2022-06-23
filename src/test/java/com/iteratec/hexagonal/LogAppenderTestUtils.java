package com.iteratec.hexagonal;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.experimental.UtilityClass;
import org.slf4j.LoggerFactory;

@UtilityClass
public class LogAppenderTestUtils {
    public ListAppender<ILoggingEvent> getLogAppenderFor(Object o) {
        return getLogAppenderFor(o.getClass());
    }

    public ListAppender<ILoggingEvent> getLogAppenderFor(Class<?> clazz) {
        ListAppender<ILoggingEvent> loggerListAppender = new ListAppender<>();
        loggerListAppender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        logger.addAppender(loggerListAppender);

        return loggerListAppender;
    }
}
