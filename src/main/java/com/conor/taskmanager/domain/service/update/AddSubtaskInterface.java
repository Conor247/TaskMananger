package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface AddSubtaskInterface {
    Mono<Task> addSubTaskById(Task taskRequest, String id);
    Mono<Task> addNestedSubTaskById(Task subtaskRequest, String id, String subTaskId);
}
