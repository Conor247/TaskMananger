package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.update.AddSubtaskInterface;
import com.conor.taskmanager.domain.service.update.RemoveSubtaskInterface;
import com.conor.taskmanager.domain.service.update.UpdateTaskInterface;
import com.conor.taskmanager.helper.TestDataBuilder;
import org.junit.jupiter.api.Test;
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
public class UpdateTaskControllerTest {

    @MockBean
    private UpdateTaskInterface updateTaskInterface;
    @MockBean
    private RemoveSubtaskInterface removeSubtaskInterface;
    @MockBean
    private AddSubtaskInterface addSubtaskInterface;

    @Autowired
    private UpdateTaskController updateTaskController;

    @Test
    void updateTask() {
        Task task = TestDataBuilder.buildTask();

        when(updateTaskInterface.updateTaskById(anyString(), any(Task.class))).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = updateTaskController.updateTask(task.getId(), task);

        verify(updateTaskInterface, times(1)).updateTaskById(task.getId(), task);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }

    @Test
    void updateSubTask() {
        Task task = TestDataBuilder.buildTaskWithSubTask();

        when(updateTaskInterface.updateSubTaskById(anyString(), anyString(), any(Task.class))).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = updateTaskController.updateSubTask(task.getId(), "1", task);

        verify(updateTaskInterface, times(1)).updateSubTaskById(task.getId(), "1", task);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }

    @Test
    void removeSubTask() {

        Task task = TestDataBuilder.buildTask();

        when(removeSubtaskInterface.removeSubtaskById(anyString(), anyString())).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = updateTaskController.removeSubTask(task.getId(), "1");

        verify(removeSubtaskInterface, times(1)).removeSubtaskById(task.getId(), "1");

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }

    @Test
    void addSubTask() {

        Task task = TestDataBuilder.buildTask();

        when(addSubtaskInterface.addSubTaskById(anyString(), any(Task.class))).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = updateTaskController.addSubTask("1", task);

        verify(addSubtaskInterface, times(1)).addSubTaskById( "1", task);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }

    @Test
    void addNestedSubTask() {

        Task task = TestDataBuilder.buildTask();

        when(addSubtaskInterface.addNestedSubTaskById(anyString(), anyString(), any(Task.class))).thenReturn(Mono.just(task));

        Mono<ResponseEntity<Task>> result = updateTaskController.addNestedSubTask(task.getId(),"1", task);

        verify(addSubtaskInterface, times(1)).addNestedSubTaskById( task.getId(), "1", task);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK)
                        .body(task))
                .verifyComplete();
    }
}
