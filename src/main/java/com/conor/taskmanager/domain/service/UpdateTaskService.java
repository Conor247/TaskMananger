package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.database.TaskRepository;
import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateTaskService {
    private final TaskRepository taskRepository;

    private final TaskService taskService;

    @Autowired
    public UpdateTaskService(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    public Mono<Task> updateTaskById(String id, Task updatedTaskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setTitle(updatedTaskRequest.getTitle());
                    existingTask.setDescription(updatedTaskRequest.getDescription());
                    existingTask.setSubTasks(updatedTaskRequest.getSubTasks());
                    taskService.assignIdsToSubTasks(existingTask.getSubTasks(), "");
                    return taskRepository.save(existingTask);
                })
                .doOnNext(task -> log.info("Updated task: " + task.getId()));
    }
}
