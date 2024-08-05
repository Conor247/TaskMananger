package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.CreateTaskService;
import com.conor.taskmanager.domain.service.GetTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/get/task")
public class GetTaskController {

    private final GetTaskService getTaskService;

    @Autowired
    public GetTaskController(GetTaskService getTaskService) {
        this.getTaskService = getTaskService;
    }

    @GetMapping("/{id}")
    public Mono<Task> getTask(@PathVariable String id) {
        return getTaskService.getTaskById(id)
                .onErrorResume(e -> {
                    final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
                    log.error("Error retrieving tasks", e);
                    return Mono.empty();
                });
    }

    @GetMapping("/all")
        public Flux<Task> getAllTasks() {
        return getTaskService.getAllTasks()
                .onErrorResume(e -> {
                    final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
                    log.error("Error retrieving tasks", e);
                    return Flux.empty();
                });
    }
}
