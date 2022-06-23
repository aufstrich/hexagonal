package com.iteratec.hexagonal.application;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {
    UUID generate() {
        return UUID.randomUUID();
    }
}
