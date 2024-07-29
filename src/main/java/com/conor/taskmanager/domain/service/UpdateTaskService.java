package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateTaskService extends AbstractTaskService {

    public UpdateTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> updateTaskById(String id, Task updatedTaskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return taskRepository.findById(id, Task.class)
                .flatMap(existingTask -> {
                    existingTask.setTitle(updatedTaskRequest.getTitle());
                    existingTask.setDescription(updatedTaskRequest.getDescription());
                    existingTask.setSubTasks(updatedTaskRequest.getSubTasks());
                    assignIdsToSubTasks(existingTask.getSubTasks(), "");
                    return taskRepository.save(existingTask);
                })
                .doOnNext(task -> log.info("Updated task: " + task.getId()));
    }
}
