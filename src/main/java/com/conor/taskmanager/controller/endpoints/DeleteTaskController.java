package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.service.delete.DeleteTaskInterface;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/delete")
public class DeleteTaskController {
    private final DeleteTaskInterface deleteTaskInterface;

    public DeleteTaskController(DeleteTaskInterface deleteTaskInterface) {
        this.deleteTaskInterface = deleteTaskInterface;
    }

    @DeleteMapping(path = "/task")
    public Mono<Void> deleteTask(
            @RequestHeader("id") String id) {
        return deleteTaskInterface.deleteTaskById(id);
    }

    @DeleteMapping(path = "/subtask")
    public Mono<Void> deleteSubTasks(
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return deleteTaskInterface.deleteSubtaskById(id, subtaskId).then();
    }

}
