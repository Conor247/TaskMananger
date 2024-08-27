package com.conor.taskmanager.domain.service.create;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import com.conor.taskmanager.helper.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskServiceTest {

    @Mock
    private ReactiveTemplateInterface reactiveTemplateInterface;
    @InjectMocks
    private CreateTaskService createTaskService;

    @Test
    void createTask_assignIdsToSubtasksTest() {

        Task task = TestDataBuilder.buildTaskWithSubTaskNoId();

        when(reactiveTemplateInterface.insertTask(any(Task.class))).thenReturn(Mono.just(task));

        Mono<Task> result = createTaskService.createTask(task);

        StepVerifier.create(result)
                .assertNext(resultTask -> {
                    resultTask.getSubTasks().forEach(subTask -> {
                        assertNotNull(subTask.getId(), "Subtask IDs should not be null");
                    });
                })
                .verifyComplete();

        verify(reactiveTemplateInterface, times(1)).insertTask(task);
    }
}