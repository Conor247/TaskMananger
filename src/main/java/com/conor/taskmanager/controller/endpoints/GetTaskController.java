package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.get.GetTaskInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/get")
public class GetTaskController {

    private final GetTaskInterface getTaskInterface;

    public GetTaskController(GetTaskInterface getTaskInterface) {
        this.getTaskInterface = getTaskInterface;
    }

    @GetMapping(path = "/task")
    public Mono<ResponseEntity<Task>> getTask(@RequestHeader("id") String id) {
        return getTaskInterface.getTaskById(id)
                .map(retrievedTask -> ResponseEntity.status(HttpStatus.OK)
                        .body(retrievedTask))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/all-tasks")
        public Mono<ResponseEntity<Flux<Task>>> getAllTasks() {
        return getTaskInterface.getAllTasks()
                .collectList()
                .flatMap(tasks -> {
                    if (tasks.isEmpty()) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        Flux<Task> taskFlux = Flux.fromIterable(tasks);
                        return Mono.just(ResponseEntity.ok(taskFlux));
                    }
                });
    }
}
