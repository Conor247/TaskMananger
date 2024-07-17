package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.database.TaskRepository;
import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GetTaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public GetTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Mono<Task> getTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return taskRepository.findById(id)
                .doOnNext(task -> log.info("got: " + task.toString()));
    }

    public Flux<Task> getAllTasks() {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return taskRepository.findAll()
                .doOnNext(task -> log.info("Returned All Tasks"))
                .doOnError(e -> log.error("Error occurred while retrieving tasks", e));
    }
}
