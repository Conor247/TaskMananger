package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.update.UpdateTaskInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/update")
public class UpdateTaskController {
    private final UpdateTaskInterface updateTaskInterface;

    public UpdateTaskController(UpdateTaskInterface updateTaskInterface) {
        this.updateTaskInterface = updateTaskInterface;
    }

    @PutMapping(path = "/task", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> updateTask(
            @RequestBody Task requestBody,
            @RequestHeader("id") String id) {
        return updateTaskInterface.updateTaskById(id, requestBody)
                .map(updatedTask -> ResponseEntity.ok("Task updated with id: " + updatedTask.getId()))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }

    @PutMapping(path = "/subtask", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> updateSubTask(
            @RequestBody Task requestBody,
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return updateTaskInterface.updateSubTaskById(id, subtaskId, requestBody)
                .map(updatedTask -> ResponseEntity.ok("SubTask updated with id: " + updatedTask.getId() + " and SubTask id: " + subtaskId))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }
}
