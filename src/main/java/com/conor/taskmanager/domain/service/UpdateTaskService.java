package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
                    boolean updated = findSubTaskPerformOperation(task, subtaskId, updatedTask);
                    if (updated) {
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    protected boolean performOperation(Task currentTask, Task subTask, Task updatedTask) {
        subTask.setTitle(updatedTask.getTitle());
        subTask.setDescription(updatedTask.getDescription());
        return true;
    }
}
