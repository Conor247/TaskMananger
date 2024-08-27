package com.conor.taskmanager.domain.service.delete;

import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTaskServiceTest {

    @Mock
    private ReactiveTemplateInterface reactiveTemplateInterface;
    
    @InjectMocks
    private DeleteTaskService deleteTaskService;

    @Test
    void deleteTaskByIdTest() {

        String id = "ABC123";

        Query query = new Query(Criteria.where("id").is(id));

        DeleteResult deleteResult = DeleteResult.acknowledged(1);

        when(reactiveTemplateInterface.removeTask(any(Query.class))).thenReturn(Mono.just(deleteResult));

        Mono<DeleteResult> result = deleteTaskService.deleteTaskById(id);

        StepVerifier.create(result.map(DeleteResult::getDeletedCount))
                .expectNext(1L)
                .verifyComplete();

        verify(reactiveTemplateInterface, times(1)).removeTask(query);
    }
}