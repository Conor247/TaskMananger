package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.common.AbstractTaskService;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateTaskService extends AbstractTaskService implements UpdateTaskInterface {

    ReactiveTemplateInterface reactiveTemplateInterface;

    public Mono<Task> updateTaskById(String id, Task updatedTask) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();

        update.set("title", updatedTask.getTitle());
        update.set("description", updatedTask.getDescription());

        return reactiveTemplateInterface.findAndModify(query, update);
    }

    public Mono<Task> updateSubTaskById(String id, String subtaskId, Task updatedTask) {
        Query query = new Query(Criteria.where("id").is(id));

        return reactiveTemplateInterface.findOne(query)
                .flatMap(task -> {
                    if (findSubTaskPerformOperation(task, subtaskId, updatedTask)) {
                        return reactiveTemplateInterface.saveTask(task);
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
