package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.create.CreateTaskService;
import com.conor.taskmanager.domain.service.get.GetTaskInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/get")
public class GetTaskController {

    private final GetTaskInterface getTaskInterface;

    public GetTaskController(GetTaskInterface getTaskInterface) {
        this.getTaskInterface = getTaskInterface;
    }

    @GetMapping(path = "/task")
    public Mono<Task> getTask(@RequestHeader("id") String id) {
        return getTaskInterface.getTaskById(id)
                .onErrorResume(e -> {
                    final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
                    log.error("Error retrieving tasks", e);
                    return Mono.empty();
                });
    }

    @GetMapping("/all-tasks")
        public Flux<Task> getAllTasks() {
        return getTaskInterface.getAllTasks()
                .onErrorResume(e -> {
                    final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
                    log.error("Error retrieving tasks", e);
                    return Flux.empty();
                });
    }
}
