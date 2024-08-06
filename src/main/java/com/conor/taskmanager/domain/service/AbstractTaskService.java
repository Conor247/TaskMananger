package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public abstract class AbstractTaskService {

    protected final ReactiveMongoTemplate taskRepository;

    @Autowired
    public AbstractTaskService(ReactiveMongoTemplate taskRepository) {
        this.taskRepository = taskRepository;
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

    //Using a queue to perform a breadth first search on the subtask tree
    protected boolean findSubTask(Task task, String subTaskId, Task requestedTask) {
        Queue<Task> queue = new LinkedList<>(Collections.singletonList(task));

        while (!queue.isEmpty()) {
            Task currentTask = queue.poll();
            if (currentTask.getSubTasks() != null) {
                for (Task subTask : currentTask.getSubTasks()) {
                    if (subTask.getId().equals(subTaskId)) {
                        return performOperation(currentTask, subTask, requestedTask);
                    }
                    queue.add(subTask);
                }
            }
        }
        return false;
    }

    //This method should be overridden to define the operation to perform when the subtask is found.
    protected boolean performOperation(Task currentTask, Task subTask, Task requestedTask) {
        return false;
    }
}
