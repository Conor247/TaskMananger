package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateTaskService extends AbstractTaskService {

    public CreateTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> createTaskRequest(Task taskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        assignIdsToSubTasks(taskRequest.getSubTasks(), "");
        return taskRepository.insert(taskRequest)
                .doOnNext(task -> log.info("Insert: " + task));
    }

}
