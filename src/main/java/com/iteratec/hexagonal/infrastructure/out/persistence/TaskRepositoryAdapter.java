package com.iteratec.hexagonal.infrastructure.out.persistence;

import com.iteratec.hexagonal.application.TaskRepository;
import com.iteratec.hexagonal.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {
    private final JPATaskRepository repository;
    private final TaskPersistenceMapper mapper;

    @Override
    public void save(Task task) {
        TaskEntity taskEntity = mapper.toEntity(task);
        repository.save(taskEntity);
    }

    @Override
    public List<Task> list() {
        return repository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::fromEntity);
    }
}
