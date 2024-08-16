package com.conor.taskmanager.domain.service.get;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.common.AbstractTaskService;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import com.conor.taskmanager.domain.service.create.CreateTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GetTaskService extends AbstractTaskService implements GetTaskInterface {

    ReactiveTemplateInterface reactiveTemplateInterface;

    public Mono<Task> getTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return reactiveTemplateInterface.findTaskById(id)
                .doOnNext(task -> log.info("Returned a Mono of the Task"))
                .doOnError(e -> log.error("Error occurred while retrieving tasks", e))
                .onErrorResume(e -> Mono.empty());
    }

    public Flux<Task> getAllTasks() {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return reactiveTemplateInterface.findAllTasks()
                .doOnNext(task -> log.info("Returned a Flux of all Tasks"))
                .doOnError(e -> log.error("Error occurred while retrieving tasks", e))
                .onErrorResume(e -> Flux.empty());
    }
}