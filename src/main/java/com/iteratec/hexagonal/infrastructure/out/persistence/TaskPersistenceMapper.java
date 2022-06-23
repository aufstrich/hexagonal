package com.iteratec.hexagonal.infrastructure.out.persistence;

import com.iteratec.hexagonal.domain.Task;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TaskPersistenceMapper {
    Task fromEntity(TaskEntity source);

    TaskEntity toEntity(Task source);
}
