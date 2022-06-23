package com.iteratec.hexagonal.external.tshirt;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TShirt {
    TShirtColor color;
    String text;
}
