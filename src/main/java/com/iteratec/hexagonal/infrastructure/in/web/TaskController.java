package com.iteratec.hexagonal.infrastructure.in.web;

import com.iteratec.hexagonal.application.TaskService;
import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskWebMapper taskWebMapper;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody TaskDto taskDto) {
        Task newTask = taskWebMapper.fromDto(taskDto);
        Task createdTask = taskService.create(newTask);
        return taskWebMapper.toDto(createdTask);
    }

    @PostMapping("{id}/progress")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void progress(@PathVariable("id") UUID id) {
        taskService.setState(id, TaskState.IN_PROGRESS);
    }

    @PostMapping("{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable("id") UUID id) {
        taskService.setState(id, TaskState.OPEN);
    }

    @PostMapping("{id}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable("id") UUID id) {
        taskService.setState(id, TaskState.CLOSED);
    }

    @PostMapping("{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable("id") UUID id) {
        taskService.setState(id, TaskState.CANCELED);
    }

    @PostMapping("{id}/print")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void print(@PathVariable("id") UUID id) {
        taskService.printShirt(id);
    }

    @GetMapping
    public List<TaskDto> list() {
        return taskService.list().stream()
                .map(taskWebMapper::toDto)
                .toList();
    }
}

