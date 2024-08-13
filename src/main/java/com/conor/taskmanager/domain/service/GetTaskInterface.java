package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetTaskInterface {
    Mono<Task> getTaskById(String id);
    Flux<Task> getAllTasks();
}
