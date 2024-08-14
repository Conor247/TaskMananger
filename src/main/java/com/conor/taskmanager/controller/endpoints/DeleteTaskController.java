package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.service.delete.DeleteTaskInterface;
import com.mongodb.client.result.DeleteResult;
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
    public Mono<DeleteResult> deleteTask(
            @RequestHeader("id") String id) {
        return deleteTaskInterface.deleteTaskById(id);
    }
}
