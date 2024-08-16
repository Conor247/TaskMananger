package com.conor.taskmanager.domain.service.create;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.common.AbstractTaskService;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateTaskService extends AbstractTaskService implements CreateTaskInterface {

    ReactiveTemplateInterface reactiveTemplateInterface;

    public Mono<Task> createTask(Task taskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        if (taskRequest.getSubTasks() != null && !taskRequest.getSubTasks().isEmpty()) {
            assignIdsToSubTasks(taskRequest.getSubTasks());
        }
        return reactiveTemplateInterface.insertTask(taskRequest)
                .doOnNext(task -> log.info("Created with ID: " + task.getId()));
    }
}
