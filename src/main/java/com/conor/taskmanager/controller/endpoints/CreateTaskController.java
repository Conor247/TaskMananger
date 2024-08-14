package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.create.CreateTaskInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/create")
public class CreateTaskController {
    private final CreateTaskInterface createTaskInterface;

    public CreateTaskController(CreateTaskInterface createTaskInterface) {
        this.createTaskInterface = createTaskInterface;
    }

    @PostMapping(path = "/task", consumes = {"application/json"})
    public Mono<ResponseEntity<Task>> createTask(@RequestBody Task requestBody) {
        return createTaskInterface.createTask(requestBody)
                .map(createdTask -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(createdTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
