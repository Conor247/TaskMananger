package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.update.RemoveSubtaskInterface;
import com.conor.taskmanager.domain.service.update.UpdateTaskInterface;
import com.mongodb.client.result.DeleteResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/update")
public class UpdateTaskController {
    private final UpdateTaskInterface updateTaskInterface;
    private final RemoveSubtaskInterface removeSubtaskInterface;

    public UpdateTaskController(UpdateTaskInterface updateTaskInterface,
                                RemoveSubtaskInterface removeSubtaskInterface) {
        this.updateTaskInterface = updateTaskInterface;
        this.removeSubtaskInterface = removeSubtaskInterface;
    }

    @PutMapping(path = "/task", consumes = {"application/json"})
    public Mono<Task> updateTask(
            @RequestBody Task requestBody,
            @RequestHeader("id") String id) {
        return updateTaskInterface.updateTaskById(id, requestBody);
    }

    @PutMapping(path = "/subtask", consumes = {"application/json"})
    public Mono<Task> updateSubTask(
            @RequestBody Task requestBody,
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return updateTaskInterface.updateSubTaskById(id, subtaskId, requestBody);
    }

    @PutMapping(path = "/remove-subtask")
    public Mono<Task> removeSubTask(
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return removeSubtaskInterface.removeSubtaskById(id, subtaskId);
    }
}
