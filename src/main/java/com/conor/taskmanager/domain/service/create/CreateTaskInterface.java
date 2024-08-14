package com.conor.taskmanager.domain.service.create;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface CreateTaskInterface {
    Mono<Task> createTask(Task taskRequest);
}
