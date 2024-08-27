package com.conor.taskmanager.domain.service.get;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import com.conor.taskmanager.helper.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTaskServiceTest {

    @Mock
    private ReactiveTemplateInterface reactiveTemplateInterface;

    @InjectMocks
    private GetTaskService getTaskService;

    @Test
    void getTaskByIdTest() {

        Task task = TestDataBuilder.buildTask();

        when(reactiveTemplateInterface.findTaskById(anyString())).thenReturn(Mono.just(task));

        StepVerifier.create(getTaskService.getTaskById("ABC123"))
                .expectNext(task) //Expect task to be emitted
                .verifyComplete();
    }

    @Test
    void getAllTasksTest() {

        Flux<Task> taskFlux = TestDataBuilder.buildTaskFlux();

        when(reactiveTemplateInterface.findAllTasks()).thenReturn(taskFlux);

        StepVerifier.create(getTaskService.getAllTasks())
                .expectNextSequence(Objects.requireNonNull(taskFlux.collectList().block()))
                .verifyComplete();
    }
}