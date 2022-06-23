package com.iteratec.hexagonal.application;

import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TShirtPrinter printer;
    private final UUIDGenerator uuidGenerator;

    public Task create(Task task) {
        if (task.getId() == null) {
            task.setId(uuidGenerator.generate());
        }
        if (task.getState() == null) {
            task.setState(TaskState.OPEN);
        }

        repository.save(task);

        return task;
    }

    public List<Task> list() {
        return repository.list();
    }

    @SneakyThrows
    public void setState(UUID id, TaskState state) {
        Task task = repository.findById(id).orElseThrow(TaskNotFoundException::new);
        task.setState(state);
        repository.save(task);
    }

    @SneakyThrows
    public void printShirt(UUID id) {
        Task task = repository.findById(id).orElseThrow(TaskNotFoundException::new);
        this.printer.print(task);
    }
}

