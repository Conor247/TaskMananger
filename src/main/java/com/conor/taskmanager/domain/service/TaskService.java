package com.conor.taskmanager.domain.service;

import com.conor.taskmanager.domain.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Service
public class TaskService {

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
