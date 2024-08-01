package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class DeleteTaskService extends AbstractTaskService {

    public DeleteTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Void> deleteTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        Query query = new Query(Criteria.where("id").is(id));
        return taskRepository.remove(query, Task.class)
                .doOnError(e -> log.error("Error occurred while deleting task with id: " + id, e))
                .then();
    }

    public Mono<Task> deleteSubtaskById(String id, String subtaskId) {
        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    if (removeSubtaskById(task, subtaskId)) {
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    private boolean removeSubtaskById(Task task, String subTaskId) {

        Queue<Task> queue = new LinkedList<>();
        queue.add(task);

        while (!queue.isEmpty()) {
            Task currentTask = queue.poll();
            if (currentTask.getSubTasks() != null) {
                for (Task subTask : currentTask.getSubTasks()) {
                    if (subTask.getId().equals(subTaskId)) {
                        currentTask.getSubTasks().remove(subTask);
                        return true;
                    }
                    queue.add(subTask);
                }
            }
        }
        return false;
    }
}
