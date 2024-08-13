package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface UpdateTaskInterface {
    Mono<Task> updateTaskById(String id, Task updatedTask);
    Mono<Task> updateSubTaskById(String id, String subtaskId, Task updatedTask);
}
