package com.conor.taskmanager.helper;

import com.conor.taskmanager.domain.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class TestDataBuilder {

    public static Task buildTask() {
        return Task.builder()
                .title("Task Title")
                .description("Task Description")
                .build();
    }

    public static Task buildTaskWithSubTask() {
        Task subTask = Task.builder()
                .title("Task Title")
                .description("Task Description")
                .build();

        return Task.builder()
                .title("SubTask Title")
                .description("SubTask Description")
                .subTasks(Collections.singletonList(subTask))
                .build();
    }

    public static Task buildTaskWithSubTaskAndNestedSubTask() {
        Task nestedSubTask = Task.builder()
                .title("Nested SubTask Title")
                .description("Nested SubTask Description")
                .build();

        Task subTask = Task.builder()
                .title("Task Title")
                .description("Task Description")
                .subTasks(Collections.singletonList(nestedSubTask))
                .build();

        return Task.builder()
                .title("Subtask Title")
                .description("Subtask Description")
                .subTasks(Collections.singletonList(subTask))
                .build();
    }

    public static Task buildTaskFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/test/resources/task-request.json"), Task.class);
    }
}
