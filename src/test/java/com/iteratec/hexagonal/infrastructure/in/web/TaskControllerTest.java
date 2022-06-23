package com.iteratec.hexagonal.infrastructure.in.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteratec.hexagonal.application.TaskService;
import com.iteratec.hexagonal.domain.Task;
import com.iteratec.hexagonal.domain.TaskState;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(TaskWebMapperImpl.class)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskService taskService;

    @Test
    @SneakyThrows
    void create() {
        TaskDto taskDto = TaskDto.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskStateDto.IN_PROGRESS)
                .build();
        Task task = Task.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskState.IN_PROGRESS)
                .build();
        Task createdTask = Task.builder()
                .id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2"))
                .description("write unit tests results")
                .state(TaskState.CLOSED)
                .build();
        TaskDto expectedTaskDtoResult = TaskDto.builder()
                .id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2"))
                .description("write unit tests results")
                .state(TaskStateDto.CLOSED)
                .build();

        Mockito.when(taskService.create(task)).thenReturn(createdTask);


        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/task/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto));
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();


        TaskDto resultDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);

        Assertions.assertThat(resultDto).isEqualTo(expectedTaskDtoResult);
    }

    @Test
    @SneakyThrows
    void progress() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/task/{id}/progress", id);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(taskService).setState(id, TaskState.IN_PROGRESS);
    }

    @Test
    @SneakyThrows
    void open() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/task/{id}/open", id);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(taskService).setState(id, TaskState.OPEN);
    }

    @Test
    @SneakyThrows
    void close() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/task/{id}/close", id);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(taskService).setState(id, TaskState.CLOSED);
    }

    @Test
    @SneakyThrows
    void cancel() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/task/{id}/cancel", id);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(taskService).setState(id, TaskState.CANCELED);
    }

    @Test
    @SneakyThrows
    void print() {
        UUID id = UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/task/{id}/print", id);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(taskService).printShirt(id);
    }

    @Test
    @SneakyThrows
    void list() {
        Task task1 = Task.builder().id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd")).build();
        Task task2 = Task.builder().id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2")).build();
        TaskDto taskDto1 = TaskDto.builder().id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd")).build();
        TaskDto taskDto2 = TaskDto.builder().id(UUID.fromString("9bceeb48-22e2-4496-95a5-c98b37ed1cd2")).build();

        Mockito.when(taskService.list()).thenReturn(List.of(task1, task2));


        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/task");
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();


        List<TaskDto> resultDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertThat(resultDto).containsExactly(taskDto1, taskDto2);
    }
}
