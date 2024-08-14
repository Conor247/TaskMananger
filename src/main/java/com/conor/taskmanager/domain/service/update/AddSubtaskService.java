package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.AbstractTaskService;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class AddSubtaskService extends AbstractTaskService implements AddSubtaskInterface {

    public AddSubtaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> addSubTaskById(String id, Task taskRequest) {

        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    task.addSubTask(taskRequest);
                    if (task.getSubTasks() != null && !task.getSubTasks().isEmpty()) {
                        assignIdsToSubTasks(task.getSubTasks());
                    }
                    return taskRepository.save(task);
                });
    }

    public Mono<Task> addNestedSubTaskById(String id, String subTaskId, Task subtaskRequest) {

        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    if (findSubTaskPerformOperation(task, subTaskId, subtaskRequest)) {
                        if (task.getSubTasks() != null && !task.getSubTasks().isEmpty()) {
                            assignIdsToSubTasks(task.getSubTasks());
                        }
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    protected boolean performOperation(Task currentTask, Task currentSubTask, Task task) {
        if(currentSubTask.getSubTasks() != null) {
            currentSubTask.addSubTask(task);
        } else {
            currentSubTask.setSubTasks(new ArrayList<>(Collections.singletonList(task)));
        }
        return true;
    }
}
