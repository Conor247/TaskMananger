package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.create.CreateTaskInterface;
import com.conor.taskmanager.helper.TestDataBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebFluxTest(CreateTaskController.class)
class CreateTaskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreateTaskInterface createTaskServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTaskTest() {

        Task task = TestDataBuilder.buildTask();

        when(createTaskServiceMock.createTask(any(Task.class))).thenReturn(Mono.just(task));

        webTestClient.post()
                .uri("/create/task")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Task created with id: ABC123");
    }

    @Test
    void createSubTaskTest() {
        Task task = TestDataBuilder.buildTaskWithSubTask();

        when(createTaskServiceMock.createSubTaskById(any(Task.class), eq("ABC123"))).thenReturn(Mono.just(task));

        webTestClient.post()
                .uri("/create/subtask")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .header("id","ABC123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("SubTask created under Task with id: ABC123");
    }

    @Test
    void createNestedSubTaskTest() {
        Task task = TestDataBuilder.buildTaskWithSubTaskAndNestedSubTask();

        when(createTaskServiceMock.createNestedSubTaskById(any(Task.class), eq("ABC123"), eq("1.1")))
                .thenReturn(Mono.just(task));

        webTestClient.post()
                .uri("/create/nested-subtask")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .header("id","ABC123")
                .header("subtaskId", "1.1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Nested SubTask created under Task with id: ABC123 and SubTask id: 1.1");
    }
}