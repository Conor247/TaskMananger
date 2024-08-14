package com.conor.taskmanager.domain.service.create;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.AbstractTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class CreateTaskService extends AbstractTaskService implements CreateTaskInterface {

    public CreateTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> createTask(Task taskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        if (taskRequest.getSubTasks() != null && !taskRequest.getSubTasks().isEmpty()) {
            assignIdsToSubTasks(taskRequest.getSubTasks());
        }
        return taskRepository.insert(taskRequest)
                .doOnNext(task -> log.info("Insert: " + task));
    }
}
