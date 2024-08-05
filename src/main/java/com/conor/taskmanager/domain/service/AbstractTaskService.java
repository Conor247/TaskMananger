package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

@Service
public abstract class AbstractTaskService {

    protected final ReactiveMongoTemplate taskRepository;

    @Autowired
    public AbstractTaskService(ReactiveMongoTemplate taskRepository) {
        this.taskRepository = taskRepository;
    }

    //TODO re write this so it doesn't use recursion
    //TODO it currently re does the entire subtask id's, is this desired so sub tasks always start at 1?
    protected void assignIdsToSubTasks(Collection<Task> subTasks, String parentId) {
        if (!CollectionUtils.isEmpty(subTasks)) {
            int index = 1;
            for (Task subTask : subTasks) {
                String newId = (parentId == null || parentId.isEmpty()) ? String.valueOf(index) : parentId + "." + index;
                subTask.setId(newId);
                assignIdsToSubTasks(subTask.getSubTasks(), newId);
                index++;
            }
        }
    }

    //Using a queue to perform a breadth first search on the subtask tree.
    //This is used to avoid recursion which can lead to problems and is considered not safe in many scenarios.
    protected boolean findSubTask(Task task, String subTaskId, Task requestedTask) {
        Queue<Task> queue = new LinkedList<>();
        queue.add(task);

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

    protected boolean performOperation(Task currentTask, Task subTask, Task requestedTask) {
        return false;
    }
}
