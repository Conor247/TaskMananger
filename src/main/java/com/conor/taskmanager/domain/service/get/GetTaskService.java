package com.conor.taskmanager.domain.service.get;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.AbstractTaskService;
import com.conor.taskmanager.domain.service.create.CreateTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GetTaskService extends AbstractTaskService implements GetTaskInterface {

    public GetTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> getTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return taskRepository.findById(id, Task.class)
                .doOnNext(task -> log.info("Returned a Mono of the Task"))
                .doOnError(e -> log.error("Error occurred while retrieving tasks", e))
                .onErrorResume(e -> Mono.empty());
    }

    public Flux<Task> getAllTasks() {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return taskRepository.findAll(Task.class)
                .doOnNext(task -> log.info("Returned a Flux of all Tasks"))
                .doOnError(e -> log.error("Error occurred while retrieving tasks", e))
                .onErrorResume(e -> Flux.empty());
    }
}