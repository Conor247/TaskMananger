package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.DeleteTaskService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/delete/task")
public class DeleteTaskController {
    DeleteTaskService deleteTaskService;

    public DeleteTaskController(DeleteTaskService deleteTaskService) {
        this.deleteTaskService = deleteTaskService;
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Void> deleteTask(@PathVariable String id) {
        return deleteTaskService.deleteTaskById(id);
    }

    /*
    @DeleteMapping(path = "/{id}/subtask/{subtaskId}")
    public Mono<Void> deleteSubTasks(@PathVariable String id, @PathVariable String subtaskId) {
        return deleteTaskService.deleteSubTaskById(id, subtaskId);
    }
    */
}
