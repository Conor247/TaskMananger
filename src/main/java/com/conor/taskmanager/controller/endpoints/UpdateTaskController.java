package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.update.AddSubtaskInterface;
import com.conor.taskmanager.domain.service.update.RemoveSubtaskInterface;
import com.conor.taskmanager.domain.service.update.UpdateTaskInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/update")
public class UpdateTaskController {
    private final UpdateTaskInterface updateTaskInterface;
    private final RemoveSubtaskInterface removeSubtaskInterface;
    private final AddSubtaskInterface addSubtaskInterface;

    public UpdateTaskController(UpdateTaskInterface updateTaskInterface,
                                RemoveSubtaskInterface removeSubtaskInterface, AddSubtaskInterface addSubtaskInterface) {
        this.updateTaskInterface = updateTaskInterface;
        this.removeSubtaskInterface = removeSubtaskInterface;
        this.addSubtaskInterface = addSubtaskInterface;
    }

    @PutMapping(path = "/task", consumes = {"application/json"})
    public Mono<ResponseEntity<Task>> updateTask(
            @RequestHeader("id") String id,
            @RequestBody Task requestBody) {
        return updateTaskInterface.updateTaskById(id, requestBody)
                .map(updatedTask -> ResponseEntity.status(HttpStatus.OK)
                        .body(updatedTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping(path = "/subtask", consumes = {"application/json"})
    public Mono<ResponseEntity<Task>> updateSubTask(
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId,
            @RequestBody Task requestBody) {
        return updateTaskInterface.updateSubTaskById(id, subtaskId, requestBody)
                .map(updatedTask -> ResponseEntity.status(HttpStatus.OK)
                        .body(updatedTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping(path = "/remove-subtask")
    public Mono<ResponseEntity<Task>> removeSubTask(
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return removeSubtaskInterface.removeSubtaskById(id, subtaskId)
                .map(updatedTask -> ResponseEntity.status(HttpStatus.OK)
                        .body(updatedTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping(path = "/add-subtask", consumes = {"application/json"})
    public Mono<ResponseEntity<Task>> addSubTask(
            @RequestHeader("id") String id,
            @RequestBody Task requestBody) {
        return addSubtaskInterface.addSubTaskById(id, requestBody)
                .map(updatedTask -> ResponseEntity.status(HttpStatus.OK)
                        .body(updatedTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping(path = "/add-nested-subtask", consumes = {"application/json"})
    public Mono<ResponseEntity<Task>> addNestedSubTask(
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId,
            @RequestBody Task requestBody) {
        return addSubtaskInterface.addNestedSubTaskById(id, subtaskId, requestBody)
                .map(updatedTask -> ResponseEntity.status(HttpStatus.OK)
                        .body(updatedTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
