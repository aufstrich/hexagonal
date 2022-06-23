package com.iteratec.hexagonal.infrastructure.out.tshirt.printer;

import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import com.iteratec.hexagonal.external.tshirt.ExternalTShirtLibV2;
import com.iteratec.hexagonal.external.tshirt.TShirt;
import com.iteratec.hexagonal.external.tshirt.TShirtColor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class TShirtPrinterAdapterV2Test {

    @InjectMocks
    TShirtPrinterAdapterV2 tShirtPrinterAdapterV2;
    @Mock
    ExternalTShirtLibV2 externalTShirtLibV2;

    @ParameterizedTest
    @MethodSource
    void print(TaskState state, TShirtColor color) {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(state)
                .build();

        TShirt tShirt = TShirt.builder()
                .text(task.getDescription())
                .color(color)
                .build();

        tShirtPrinterAdapterV2.print(task);

        Mockito.verify(externalTShirtLibV2).printTShirt(tShirt);
    }

    static Stream<Arguments> print() {
        return Stream.of(
                Arguments.of(TaskState.OPEN, TShirtColor.RED),
                Arguments.of(TaskState.IN_PROGRESS, TShirtColor.YELLOW),
                Arguments.of(TaskState.CLOSED, TShirtColor.GREEN),
                Arguments.of(TaskState.CANCELED, TShirtColor.BLUE)
        );
    }

    @Test
    void print_NoState_RuntimeExcepiton() {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .build();

        Assertions.assertThatThrownBy(() -> {
                    tShirtPrinterAdapterV2.print(task);
                })
                .isInstanceOf(RuntimeException.class)
                .hasMessage("invalid TaskState");
    }
}

