package com.iteratec.hexagonal.infrastructure.out.tshirt.printer;

import com.iteratec.hexagonal.application.TShirtPrinter;
import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.external.tshirt.ExternalTShirtLibV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("tshirt1")
@RequiredArgsConstructor
public class TShirtPrinterAdapterV1 implements TShirtPrinter {
    private final ExternalTShirtLibV1 externalTShirtLibV1;

    @Override
    public void print(Task task) {
        externalTShirtLibV1.printTShirt(task.getDescription());
    }
}
