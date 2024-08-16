package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.get.GetTaskInterface;
import com.conor.taskmanager.helper.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetTaskControllerTest {

    @Mock
    private GetTaskInterface getTaskInterface;
    @InjectMocks
    private GetTaskController getTaskController;

    @Test
    void getTaskTest() {

        Task task = TestDataBuilder.buildTask();

        when(getTaskInterface.getTaskById(anyString())).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = getTaskController.getTask(task.getId());

        verify(getTaskInterface, times(1)).getTaskById(task.getId());

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }

    @Test
    void getAllTasksTest() {

        Task task = TestDataBuilder.buildTask();

        when(getTaskInterface.getAllTasks()).thenReturn(Flux.just(task));

        Mono<ResponseEntity<Flux<Task>>> result = getTaskController.getAllTasks();

        verify(getTaskInterface, times(1)).getAllTasks();

        StepVerifier.create(result)
                .assertNext(responseEntity -> {
                    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                    StepVerifier.create(Objects.requireNonNull(responseEntity.getBody()))
                            .expectNext(task)
                            .verifyComplete();
                })
                .verifyComplete();
    }
}
