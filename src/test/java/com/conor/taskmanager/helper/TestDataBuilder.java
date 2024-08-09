package com.conor.taskmanager.helper;

import com.conor.taskmanager.domain.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class TestDataBuilder {

    public static Task buildTask() {
        return Task.builder()
                .id("ABC123")
                .title("Task Title")
                .description("Task Description")
                .build();
    }

    public static Task buildTaskWithSubTask() {
        return buildTask().addSubTask(Task.builder()
                .id("1")
                .title("SubTask Title")
                .description("SubTask Description")
                .build());
    }

    public static Task buildTaskWithSubTaskAndNestedSubTask() {
        return buildTaskWithSubTask().addSubTask(Task.builder()
                .id("1.1")
                .title("Nested SubTask Title")
                .description("Nested SubTask Description")
                .build());
    }

    public static Task buildTaskFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/test/resources/task-request.json"), Task.class);
    }
}
