package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class UpdateTaskService extends AbstractTaskService {

    public UpdateTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> updateTaskById(String id, Task updatedTask) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();

        update.set("title", updatedTask.getTitle());
        update.set("description", updatedTask.getDescription());

        return taskRepository.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true), // Return the updated document
                Task.class
        );
    }

    //Using a queue
    public Mono<Task> updateSubTaskById(String id, String subtaskId, Task updatedTask) {
        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    boolean updated = updateNestedSubTask(task, subtaskId, updatedTask);
                    if (updated) {
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    //Using a queue to perform a breadth first search on the subtask tree.
    //This is used to avoid recursion which can lead to problems and is considered not safe in many scenarios.
    private boolean updateNestedSubTask(Task task, String subTaskId, Task updatedTask) {

        Queue<Task> queue = new LinkedList<>();
        queue.add(task);

        while (!queue.isEmpty()) {
            Task currentTask = queue.poll();
            if (currentTask.getSubTasks() != null) {
                for (Task subTask : currentTask.getSubTasks()) {
                    if (subTask.getId().equals(subTaskId)) {
                        subTask.setTitle(updatedTask.getTitle());
                        subTask.setDescription(updatedTask.getDescription());
                        return true;
                    }
                    queue.add(subTask);
                }
            }
        }
        return false;
    }
}
