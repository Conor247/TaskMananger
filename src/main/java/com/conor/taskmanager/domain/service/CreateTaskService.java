package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class CreateTaskService extends AbstractTaskService implements CreateTaskInterface {

    public CreateTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> createTask(Task taskRequest) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        assignIdsToSubTasks(taskRequest.getSubTasks());
        return taskRepository.insert(taskRequest)
                .doOnNext(task -> log.info("Insert: " + task));
    }

    public Mono<Task> createSubTaskById(Task taskRequest, String id) {

        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    task.getSubTasks().add(taskRequest);
                    assignIdsToSubTasks(task.getSubTasks());
                    return taskRepository.save(task);
                });
    }

    public Mono<Task> createNestedSubTaskById(Task subtaskRequest, String id, String subTaskId) {

        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    if (findSubTaskPerformOperation(task, subTaskId, subtaskRequest)) {
                        assignIdsToSubTasks(task.getSubTasks());
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    protected boolean performOperation(Task currentTask, Task currentSubTask, Task task) {
        if(currentSubTask.getSubTasks() != null) {
            currentSubTask.getSubTasks().add(task);
        } else {
            currentSubTask.setSubTasks(new ArrayList<>(Collections.singletonList(task)));
        }
        return true;
    }

    protected void assignIdsToSubTasks(Collection<Task> subTasks) {
        Queue<Task> queue = new LinkedList<>(subTasks);
        int index = 1;

        while (!queue.isEmpty()) {
            Task currentTask = queue.poll();

            if (currentTask.getId() == null || currentTask.getId().isEmpty()) {
                currentTask.setId(String.valueOf(index));
            }
            index++;

            if (currentTask.getSubTasks() != null) {
                int subIndex = 1;
                for (Task subTask : currentTask.getSubTasks()) {
                    subTask.setId(currentTask.getId() + "." + subIndex);
                    queue.add(subTask);
                    subIndex++;
                }
            }
        }
    }
}
