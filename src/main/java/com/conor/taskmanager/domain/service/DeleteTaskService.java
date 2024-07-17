package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;

@Service
public class DeleteTaskService {
    private final ReactiveMongoTemplate taskRepository;

    @Autowired
    public DeleteTaskService(ReactiveMongoTemplate taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Mono<Void> deleteTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        Query query = new Query(Criteria.where("id").is(id));
        return taskRepository.remove(query, Task.class)
                .doOnError(e -> log.error("Error occurred while deleting task with id: " + id, e))
                .then();
    }

    public Mono<Void> deleteSubTaskById(String taskId, String subTaskId) {
        return taskRepository.findById(taskId, Task.class)
                .flatMap(task -> {
                    deleteSubTaskRecursive(task.getSubTasks(), subTaskId);
                    return taskRepository.save(task);
                })
                .then();
    }

    //need to think about this more as it doesn't seem to work with subtasks with multiple tasks
    private void deleteSubTaskRecursive(Collection<Task> subTasks, String subTaskId) {
        Iterator<Task> iterator = subTasks.iterator();
        while (iterator.hasNext()) {
            Task subTask = iterator.next();
            if (subTask.getId().equals(subTaskId)) {
                iterator.remove();
            } else {
                deleteSubTaskRecursive(subTask.getSubTasks(), subTaskId); // Recursively check nested subtasks
            }
        }
    }
}
