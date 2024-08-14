package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.AbstractTaskService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RemoveSubtaskService extends AbstractTaskService implements RemoveSubtaskInterface {

    public RemoveSubtaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Task> removeSubtaskById(String id, String subtaskId) {
        Query query = new Query(Criteria.where("id").is(id));

        return taskRepository.findOne(query, Task.class)
                .flatMap(task -> {
                    if (findSubTaskPerformOperation(task, subtaskId, null)) {
                        return taskRepository.save(task);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    protected boolean performOperation(Task currentTask, Task subTask, Task updatedTask) {
        currentTask.getSubTasks().remove(subTask);
        return true;
    }
}
