package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.service.DeleteTaskInterface;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/delete")
public class DeleteTaskController {
    private final DeleteTaskInterface deleteTaskService;

    public DeleteTaskController(DeleteTaskInterface deleteTaskService) {
        this.deleteTaskService = deleteTaskService;
    }

    @DeleteMapping(path = "/task")
    public Mono<Void> deleteTask(
            @RequestHeader("id") String id) {
        return deleteTaskService.deleteTaskById(id);
    }

    @DeleteMapping(path = "/subtask")
    public Mono<Void> deleteSubTasks(
            @RequestHeader("id") String id,
            @RequestHeader("subtaskId") String subtaskId) {
        return deleteTaskService.deleteSubtaskById(id, subtaskId).then();
    }

}
