package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.get.GetTaskInterface;
import com.conor.taskmanager.helper.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetTaskControllerTest {

    @MockBean
    private GetTaskInterface getTaskInterface;

    @Autowired
    private GetTaskController getTaskController;

    @Test
    void getTask() {

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
    void getAllTasks() {

        Task task = TestDataBuilder.buildTask();

        when(getTaskInterface.getAllTasks()).thenReturn(Flux.just(task));

        Flux<ResponseEntity<Task>> result = getTaskController.getAllTasks();

        verify(getTaskInterface, times(1)).getAllTasks();

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }
}
