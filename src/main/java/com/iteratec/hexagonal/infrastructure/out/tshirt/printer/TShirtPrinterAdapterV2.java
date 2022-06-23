package com.iteratec.hexagonal.infrastructure.out.tshirt.printer;

import com.iteratec.hexagonal.application.TShirtPrinter;
import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import com.iteratec.hexagonal.external.tshirt.ExternalTShirtLibV2;
import com.iteratec.hexagonal.external.tshirt.TShirt;
import com.iteratec.hexagonal.external.tshirt.TShirtColor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("tshirt2")
@RequiredArgsConstructor
public class TShirtPrinterAdapterV2 implements TShirtPrinter {

    private final ExternalTShirtLibV2 externalTShirtLibV2;

    @Override
    public void print(Task task) {
        TShirt shirt = TShirt.builder()
                .text(task.getDescription())
                .color(mapColorForState(task.getState()))
                .build();
        externalTShirtLibV2.printTShirt(shirt);
    }

    private TShirtColor mapColorForState(TaskState state) {
        if (TaskState.OPEN.equals(state)) {
            return TShirtColor.RED;
        } else if (TaskState.IN_PROGRESS.equals(state)) {
            return TShirtColor.YELLOW;
        } else if (TaskState.CLOSED.equals(state)) {
            return TShirtColor.GREEN;
        } else if (TaskState.CANCELED.equals(state)) {
            return TShirtColor.BLUE;
        }
        throw new RuntimeException("invalid TaskState");
    }
}
