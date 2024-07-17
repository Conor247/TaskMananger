package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.CreateTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/create/task")
public class CreateTaskController {
    CreateTaskService createTaskService;

    public CreateTaskController(CreateTaskService createTaskService) {
        this.createTaskService = createTaskService;
    }

    @PostMapping(consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createTask(@RequestBody Task requestBody) {
        return createTaskService.createTaskRequest(requestBody)
                .map(createdTask -> ResponseEntity.ok("Task created with id: " + createdTask.getId()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }
}
