package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.database.TaskRepository;
import com.conor.taskmanager.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;

@Service
public class DeleteTaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public DeleteTaskService(TaskRepository taskRepository, GetTaskService getTaskService, TaskService taskService) {
        this.taskRepository = taskRepository;
    }

    public Mono<Void> deleteTaskById(String id) {
        return taskRepository.deleteById(id);
    }

    public Mono<Void> deleteSubTaskById(String taskId, String subTaskId) {
        return taskRepository.findById(taskId)
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
