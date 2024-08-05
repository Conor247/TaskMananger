package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.CreateTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/create")
public class CreateTaskController {
    CreateTaskService createTaskService;

    public CreateTaskController(CreateTaskService createTaskService) {
        this.createTaskService = createTaskService;
    }

    @PostMapping(path = "/task", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createTask(@RequestBody Task requestBody) {
        return createTaskService.createTask(requestBody)
                .map(createdTask -> ResponseEntity.ok("Task created with id: " + createdTask.getId()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }

    @PostMapping(path= "/subtask/{id}", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createSubTask(@RequestBody Task requestBody, @PathVariable String id) {
        return createTaskService.createSubTaskById(requestBody, id)
                .map(createdTask -> ResponseEntity.ok("SubTask created with id: " + createdTask.getId()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }

    //TODO update these path variables to use headers instead
    @PostMapping(path = "/task/{id}/subtask/{subtaskId}", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createNestedSubTask(@RequestBody Task requestBody, @PathVariable String id, @PathVariable String subtaskId) {
        return createTaskService.createNestedSubTaskById(requestBody, id, subtaskId)
                .map(createdTask -> ResponseEntity.ok("Nested SubTask created with id: " + createdTask.getId()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }
}
