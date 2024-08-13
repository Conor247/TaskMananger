package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import reactor.core.publisher.Mono;

public interface DeleteTaskInterface {
    Mono<Void> deleteTaskById(String id);
    Mono<Void> deleteSubtaskById(String id, String subtaskId);
}
