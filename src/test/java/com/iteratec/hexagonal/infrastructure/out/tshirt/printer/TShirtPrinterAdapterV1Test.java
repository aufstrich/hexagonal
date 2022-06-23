package com.iteratec.hexagonal.infrastructure.out.tshirt.printer;

import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import com.iteratec.hexagonal.external.tshirt.ExternalTShirtLibV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TShirtPrinterAdapterV1Test {

    @InjectMocks
    TShirtPrinterAdapterV1 tShirtPrinterAdapterV1;
    @Mock
    ExternalTShirtLibV1 externalTShirtLibV1;

    @Test
    void print() {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskState.IN_PROGRESS)
                .build();

        tShirtPrinterAdapterV1.print(task);

        Mockito.verify(externalTShirtLibV1).printTShirt(task.getDescription());

    }
}
