package com.iteratec.hexagonal.external.tshirt;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Profile("tshirt1")
public class ExternalTShirtLibV1 {

    public void printTShirt(String text) {
        log.warn("Printed TShirt with Text '{}'", text);
    }
}
