package com.iteratec.hexagonal.application;

import com.iteratec.hexagonal.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    void save(Task task);

    List<Task> list();

    Optional<Task> findById(UUID id);
}
