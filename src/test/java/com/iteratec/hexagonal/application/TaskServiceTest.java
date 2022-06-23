package com.iteratec.hexagonal.application;

import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith({MockitoExtension.class})
class TaskServiceTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository repository;
    @Mock
    TShirtPrinter printer;
    @Mock
    UUIDGenerator uuidGenerator;

    @Test
    void create() {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskState.IN_PROGRESS)
                .build();

        taskService.create(task);

        Mockito.verify(repository).save(task);
    }

    @Test
    void create_defaults() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");
        Task task = Task.builder()
                .description("write unit tests")
                .build();

        Mockito.when(uuidGenerator.generate()).thenReturn(id);


        taskService.create(task);


        Task expectedTask = Task.builder()
                .id(id)
                .description("write unit tests")
                .state(TaskState.OPEN)
                .build();
        Mockito.verify(repository).save(expectedTask);
    }


    @Test
    void list() {
        Task task1 = Task.builder().id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd")).build();
        Task task2 = Task.builder().id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2")).build();


        Mockito.when(repository.list()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.list();


        Assertions.assertThat(result).containsExactly(task1, task2);
    }

    @ParameterizedTest
    @MethodSource
    void setState(TaskState state) {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .build();

        Mockito.when(repository.findById(task.getId())).thenReturn(Optional.of(task));


        taskService.setState(task.getId(), state);


        Task expectedTask = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(state)
                .build();
        Mockito.verify(repository).save(expectedTask);
    }

    static Stream<TaskState> setState() {
        return Stream.of(TaskState.values());
    }

    @Test
    void setState_TaskNotFoundException() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            taskService.setState(id, TaskState.IN_PROGRESS);
        }).isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void printShirt() {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskState.IN_PROGRESS)
                .build();

        Mockito.when(repository.findById(task.getId())).thenReturn(Optional.of(task));


        taskService.printShirt(task.getId());


        Mockito.verify(printer).print(task);
    }

    @Test
    void print_TaskNotFoundException() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            taskService.printShirt(id);
        }).isInstanceOf(TaskNotFoundException.class);
    }
}
