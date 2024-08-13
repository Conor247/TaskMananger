package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.CreateTaskService;
import com.conor.taskmanager.domain.service.GetTaskInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/get")
public class GetTaskController {

    private final GetTaskInterface getTaskService;

    public GetTaskController(GetTaskInterface getTaskService) {
        this.getTaskService = getTaskService;
    }

    @GetMapping(path = "/task")
    public Mono<Task> getTask(@RequestHeader("id") String id) {
        return getTaskService.getTaskById(id)
                .onErrorResume(e -> {
                    final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
                    log.error("Error retrieving tasks", e);
                    return Mono.empty();
                });
    }

    @GetMapping("/all-tasks")
        public Flux<Task> getAllTasks() {
        return getTaskService.getAllTasks()
                .onErrorResume(e -> {
                    final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
                    log.error("Error retrieving tasks", e);
                    return Flux.empty();
                });
    }
}
