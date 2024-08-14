package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface AddSubtaskInterface {
    Mono<Task> addSubTaskById(String id, Task taskRequest);
    Mono<Task> addNestedSubTaskById(String id, String subTaskId, Task subtaskRequest);
}
