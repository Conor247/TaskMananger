package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.create.CreateTaskInterface;
import com.conor.taskmanager.helper.TestDataBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskControllerTest {

    @Mock
    private CreateTaskInterface createTaskInterface;
    @InjectMocks
    private CreateTaskController createTaskController;

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