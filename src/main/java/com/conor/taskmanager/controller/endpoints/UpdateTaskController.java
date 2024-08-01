package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.UpdateTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/update")
public class UpdateTaskController {
    UpdateTaskService updateTaskService;

    public UpdateTaskController(UpdateTaskService updateTaskService) {
        this.updateTaskService = updateTaskService;
    }

    @PutMapping(path = "/task/{id}", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> updateTask(@PathVariable String id, @RequestBody Task requestBody) {
        return updateTaskService.updateTaskById(id, requestBody)
                .map(updatedTask -> ResponseEntity.ok("Task updated with id: " + updatedTask.getId()))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }

    @PutMapping(path = "/task/{id}/subtask/{subtaskId}", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> updateSubTask(@PathVariable String id, @PathVariable String subtaskId, @RequestBody Task requestBody) {
        return updateTaskService.updateSubTaskById(id, subtaskId, requestBody)
                .map(updatedTask -> ResponseEntity.ok("SubTask updated with id: " + updatedTask.getId() + " and SubTask id: " + subtaskId))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error: " + e.getMessage())));
    }
}
