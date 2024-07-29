package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Service
public abstract class AbstractTaskService {

    protected final ReactiveMongoTemplate taskRepository;

    @Autowired
    public AbstractTaskService(ReactiveMongoTemplate taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void assignIdsToSubTasks(Collection<Task> subTasks, String parentId) {
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
}
