package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface RemoveSubtaskInterface {

    Mono<Task> removeSubtaskById(String id, String subtaskId);
}
