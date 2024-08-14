package com.conor.taskmanager.domain.service.delete;

import com.conor.taskmanager.domain.model.Task;
import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;

public interface DeleteTaskInterface {
    Mono<DeleteResult> deleteTaskById(String id);
}
