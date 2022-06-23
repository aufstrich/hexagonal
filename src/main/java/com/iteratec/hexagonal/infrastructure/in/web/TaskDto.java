package com.iteratec.hexagonal.infrastructure.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    UUID id;
    String description;
    TaskStateDto state;
}

