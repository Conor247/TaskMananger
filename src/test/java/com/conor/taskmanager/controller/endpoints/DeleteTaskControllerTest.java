package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.service.delete.DeleteTaskInterface;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTaskControllerTest {

    @Mock
    private DeleteTaskInterface deleteTaskInterface;
    @InjectMocks
    private DeleteTaskController deleteTaskController;

    @Test
    public void deleteTaskTest() {
        String taskId = "ABC123";
        DeleteResult deleteResult = DeleteResult.acknowledged(1);
        when(deleteTaskInterface.deleteTaskById(taskId)).thenReturn(Mono.just(deleteResult));

        Mono<DeleteResult> result = deleteTaskController.deleteTask(taskId);

        //Verify the Mono is emitted and the stream completes as expected
        StepVerifier.create(result)
                .expectNext(deleteResult)
                .verifyComplete();

        //Verify the interface method is called once inside the Controller
        verify(deleteTaskInterface, times(1)).deleteTaskById(taskId);

        //Verify the deletedCount is 1
        StepVerifier.create(result.map(DeleteResult::getDeletedCount))
                .expectNext(1L)
                .verifyComplete();
    }
}
