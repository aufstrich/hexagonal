package com.iteratec.hexagonal.infrastructure.in.web;

import com.iteratec.hexagonal.domain.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskWebMapper {
    Task fromDto(TaskDto source);

    TaskDto toDto(Task source);
}
