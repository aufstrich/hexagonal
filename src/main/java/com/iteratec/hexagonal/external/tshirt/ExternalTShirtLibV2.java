package com.iteratec.hexagonal.external.tshirt;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Profile("tshirt2")
public class ExternalTShirtLibV2 {
    public void printTShirt(TShirt tShirt) {
        log.warn("Printed: {}", tShirt);
    }
}

