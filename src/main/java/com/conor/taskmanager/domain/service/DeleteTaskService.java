package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;

@Service
public class DeleteTaskService extends AbstractTaskService {

    public DeleteTaskService(ReactiveMongoTemplate taskRepository) {
        super(taskRepository);
    }

    public Mono<Void> deleteTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        Query query = new Query(Criteria.where("id").is(id));
        return taskRepository.remove(query, Task.class)
                .doOnError(e -> log.error("Error occurred while deleting task with id: " + id, e))
                .then();
    }

    public Mono<Task> deleteSubtaskById(String taskId, String subtaskId) {
        //Retrieve the task by its ID
        return taskRepository.findById(taskId, Task.class)
                .flatMap(task -> {
                    //Look at the subtasks and attempt to find the subtask with matching id
                    removeSubtaskById(task, subtaskId);
                    //Save the updated task document
                    return taskRepository.save(task);
                });
    }

    private void removeSubtaskById(Task task, String subtaskId) {
        removeSubtaskFromList(task.getSubTasks(), subtaskId);
    }

    private boolean removeSubtaskFromList(Collection<Task> subTasks, String subtaskId) {
        if (subTasks == null) return false;

        boolean removed = false;
        Iterator<Task> iterator = subTasks.iterator();

        while (iterator.hasNext()) {
            Task subtask = iterator.next();
            if (subtask.getId().equals(subtaskId)) {
                iterator.remove(); // Remove the subtask if it matches the subtaskId
                removed = true;
            } else {
                // Otherwise, iterate through nested subtasks and remove if found
                if (subtask.getSubTasks() != null) {
                    //recursively call removeSubtaskFromList() again to go another level deeper
                    boolean nestedRemoved = removeSubtaskFromList(subtask.getSubTasks(), subtaskId);
                    if (nestedRemoved) {
                        // Clean up empty subTasks collections as they were just being emptied
                        if (subtask.getSubTasks().isEmpty()) {
                            subtask.setSubTasks(null);
                        }
                        removed = true;
                    }
                }
            }
        }
        return removed;
        //could probably do this in a way that it doesn't return useless boolean...
    }
}
