package com.conor.taskmanager.domain.service.update;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.common.AbstractTaskService;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RemoveSubtaskService extends AbstractTaskService implements RemoveSubtaskInterface {

    private final ReactiveTemplateInterface reactiveTemplateInterface;

    public RemoveSubtaskService(ReactiveTemplateInterface reactiveTemplateInterface) {
        this.reactiveTemplateInterface = reactiveTemplateInterface;
    }

    public Mono<Task> removeSubtaskById(String id, String subtaskId) {
        Query query = new Query(Criteria.where("id").is(id));

        return reactiveTemplateInterface.findOne(query)
                .flatMap(task -> {
                    if (findSubTaskPerformOperation(task, subtaskId, null)) {
                        return reactiveTemplateInterface.saveTask(task);
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
