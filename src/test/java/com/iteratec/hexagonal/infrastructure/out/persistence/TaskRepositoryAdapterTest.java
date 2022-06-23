package com.iteratec.hexagonal.infrastructure.out.persistence;

import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryAdapterTest {
    TaskRepositoryAdapter taskRepositoryAdapter;

    @Mock
    JPATaskRepository repository;

    @BeforeEach
    void before() {
        TaskPersistenceMapper mapper = new TaskPersistenceMapperImpl();
        taskRepositoryAdapter = new TaskRepositoryAdapter(repository, mapper);
    }

    @Test
    void save() {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskState.IN_PROGRESS)
                .build();

        TaskEntity taskEntity = TaskEntity.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskStateEntity.IN_PROGRESS)
                .build();

        taskRepositoryAdapter.save(task);

        Mockito.verify(repository).save(taskEntity);
    }

    @Test
    void list() {
        Task task1 = Task.builder().id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd")).build();
        Task task2 = Task.builder().id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2")).build();
        TaskEntity taskEntity1 = TaskEntity.builder().id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd")).build();
        TaskEntity taskEntity2 = TaskEntity.builder().id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2")).build();

        Mockito.when(repository.findAll()).thenReturn(List.of(taskEntity1, taskEntity2));


        List<Task> result = taskRepositoryAdapter.list();


        Assertions.assertThat(result).containsExactly(task1, task2);
    }

    @Test
    void findById() {
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskState.IN_PROGRESS)
                .build();

        TaskEntity taskEntity = TaskEntity.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskStateEntity.IN_PROGRESS)
                .build();


        Mockito.when(repository.findById(taskEntity.getId())).thenReturn(Optional.of(taskEntity));


        Optional<Task> result = taskRepositoryAdapter.findById(taskEntity.getId());

        Assertions.assertThat(result).contains(task);
    }
}
