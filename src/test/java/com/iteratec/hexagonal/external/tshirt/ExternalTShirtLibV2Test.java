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
class ExternalTShirtLibV2Test {

    @InjectMocks
    ExternalTShirtLibV2 externalTShirtLibV2;

    @Test
    void printTShirt() {
        ListAppender<ILoggingEvent> loggerListAppender = LogAppenderTestUtils.getLogAppenderFor(ExternalTShirtLibV2.class);

        TShirt tShirt = TShirt.builder()
                .text("nice shirt")
                .color(TShirtColor.BLUE)
                .build();

        externalTShirtLibV2.printTShirt(tShirt);

        List<ILoggingEvent> logsList = loggerListAppender.list;
        assertThat(logsList).hasSize(1);
        assertThat(logsList.get(0).getLevel()).isEqualTo(Level.WARN);
        assertThat(logsList.get(0).getMessage()).isEqualTo("Printed: TShirt(color=BLUE, text=nice shirt)");


    }
}
