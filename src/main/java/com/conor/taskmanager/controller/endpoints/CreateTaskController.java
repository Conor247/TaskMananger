package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.create.CreateTaskInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/create")
public class CreateTaskController {
    private final CreateTaskInterface createTaskInterface;

    public CreateTaskController(CreateTaskInterface createTaskInterface) {
        this.createTaskInterface = createTaskInterface;
    }

    @PostMapping(path = "/task", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createTask(@RequestBody Task requestBody) {
        return createTaskInterface.createTask(requestBody)
                .map(createdTask -> ResponseEntity.ok(String.format("Task created with id: %s", createdTask.getId())))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }

    @PostMapping(path = "/subtask", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createSubTask(
            @RequestBody Task requestBody,
            @RequestHeader("id") String id) {
        return createTaskInterface.createSubTaskById(requestBody, id)
                .map(task -> ResponseEntity.ok(String.format("SubTask created under Task with id: %s", id)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }

    @PostMapping(path = "/nested-subtask", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createNestedSubTask(
            @RequestBody Task requestBody,
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return createTaskInterface.createNestedSubTaskById(requestBody, id, subtaskId)
                .map(task -> ResponseEntity.ok(
                                String.format("Nested SubTask created under Task with id: %s and SubTask id: %s",
                                        id, subtaskId)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }
}
