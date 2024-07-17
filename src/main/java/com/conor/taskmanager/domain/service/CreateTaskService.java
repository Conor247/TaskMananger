package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.database.TaskRepository;
import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateTaskService {
    private final TaskRepository taskRepository;

    private final TaskService taskService;

    @Autowired
    public CreateTaskService(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    public Mono<Task> createTaskRequest(Task taskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        taskService.assignIdsToSubTasks(taskRequest.getSubTasks(), "");
        return taskRepository.insert(taskRequest)
                .doOnNext(task -> log.info("Insert: " + task));
    }

}
