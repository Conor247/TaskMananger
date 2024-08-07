package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.CreateTaskService;
import com.conor.taskmanager.helper.TestDataBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@WebFluxTest(CreateTaskController.class)
class CreateTaskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreateTaskService createTaskServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTaskTest() throws IOException {

        Task task = TestDataBuilder.buildTask();
        task.setId("ABC123");

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
    }

    @Test
    void createNestedSubTaskTest() {
    }
}