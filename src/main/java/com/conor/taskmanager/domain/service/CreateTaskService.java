package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class CreateTaskService extends AbstractTaskService {

    public CreateTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> createTask(Task taskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        assignIdsToSubTasks(taskRequest.getSubTasks());
        return taskRepository.insert(taskRequest)
                .doOnNext(task -> log.info("Insert: " + task));
    }

    public Mono<Task> createSubTaskById(Task taskRequest, String id) {

        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    task.getSubTasks().add(taskRequest);
                    assignIdsToSubTasks(task.getSubTasks());
                    return taskRepository.save(task);
                });
    }

    public Mono<Task> createNestedSubTaskById(Task subtaskRequest, String id, String subTaskId) {

        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    if (findSubTaskPerformOperation(task, subTaskId, subtaskRequest)) {
                        assignIdsToSubTasks(task.getSubTasks());
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    protected boolean performOperation(Task currentTask, Task currentSubTask, Task task) {
        if(currentSubTask.getSubTasks() != null) {
            currentSubTask.getSubTasks().add(task);
        } else {
            currentSubTask.setSubTasks(new ArrayList<>(Collections.singletonList(task)));
        }
        return true;
    }
}
