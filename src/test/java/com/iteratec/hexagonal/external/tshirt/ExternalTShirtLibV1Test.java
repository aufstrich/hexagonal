package com.iteratec.hexagonal.external.tshirt;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.iteratec.hexagonal.LogAppenderTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExternalTShirtLibV1Test {

    @InjectMocks
    ExternalTShirtLibV1 externalTShirtLibV1;

    @Test
    void printTShirt() {
        ListAppender<ILoggingEvent> loggerListAppender = LogAppenderTestUtils.getLogAppenderFor(ExternalTShirtLibV1.class);

        externalTShirtLibV1.printTShirt("test log messages");

        List<ILoggingEvent> logsList = loggerListAppender.list;
        assertThat(logsList).hasSize(1);
        assertThat(logsList.get(0).getLevel()).isEqualTo(Level.WARN);
        assertThat(logsList.get(0).getMessage()).isEqualTo("Printed TShirt with Text 'test log messages'");
    }
}
