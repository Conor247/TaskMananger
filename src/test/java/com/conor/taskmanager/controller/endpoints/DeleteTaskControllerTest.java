package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.service.delete.DeleteTaskInterface;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteTaskControllerTest {

    @MockBean
    private DeleteTaskInterface deleteTaskInterface;

    @Autowired
    private DeleteTaskController deleteTaskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
