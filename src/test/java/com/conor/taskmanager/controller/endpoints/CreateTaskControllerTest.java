package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.create.CreateTaskInterface;
import com.conor.taskmanager.helper.TestDataBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class CreateTaskControllerTest {

    @MockBean
    private CreateTaskInterface createTaskInterface;

    @Autowired
    private CreateTaskController createTaskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTaskTest() {

        Task task = TestDataBuilder.buildTask();

        when(createTaskInterface.createTask(any(Task.class))).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = createTaskController.createTask(task);

        verify(createTaskInterface, times(1)).createTask(task);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.CREATED)
                        .body(task))
                .verifyComplete();
    }
}