package com.conor.taskmanager.domain.service.create;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface CreateTaskInterface {
    Mono<Task> createTask(Task taskRequest);
    Mono<Task> createSubTaskById(Task taskRequest, String id);
    Mono<Task> createNestedSubTaskById(Task subtaskRequest, String id, String subTaskId);
}
