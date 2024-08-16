package com.conor.taskmanager.domain.service.common;

import com.conor.taskmanager.domain.model.Task;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveTemplateInterface {

    Mono<Task> insertTask(Task taskRequest);
    Mono<DeleteResult> removeTask(Query query);
    Mono<Task> findTaskById(String id);
    Flux<Task> findAllTasks();
    Mono<Task> findOne(Query query);
    Mono<Task> findAndModify(Query query, Update update);
    Mono<Task> saveTask(Task task);
}
