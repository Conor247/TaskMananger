package com.conor.taskmanager.domain.service.common;

import com.conor.taskmanager.domain.model.Task;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveTemplateService implements ReactiveTemplateInterface {

    public ReactiveMongoTemplate taskRepository;

    public Mono<Task> insertTask(Task taskRequest) {
        return taskRepository.insert(taskRequest);
    }

    public Mono<DeleteResult> removeTask(Query query) {
        return taskRepository.remove(query, Task.class);
    }

    public Mono<Task> findTaskById(String id) {
        return taskRepository.findById(id, Task.class);
    }

    public Flux<Task> findAllTasks() {
        return taskRepository.findAll(Task.class);
    }

    public Mono<Task> findOne(Query query) {
        return taskRepository.findOne(query, Task.class);
    }

    public Mono<Task> findAndModify(Query query, Update update) {
        return taskRepository.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true), // Return the updated document
                Task.class);
    }

    public Mono<Task> saveTask(Task task) {
        return taskRepository.save(task);
    }

}
