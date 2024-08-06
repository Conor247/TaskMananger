package com.conor.taskmanager.controller.endpoints;

import com.conor.taskmanager.domain.model.Task;
import com.conor.taskmanager.domain.service.CreateTaskService;
import com.conor.taskmanager.helper.TestDataBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.mock;


class CreateTaskControllerTest {

    @Mock
    private CreateTaskService createTaskServiceMock = mock(CreateTaskService.class);

    @InjectMocks
    private CreateTaskController sut;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask() throws IOException {
        Task task = TestDataBuilder.buildTaskFromJson();
        assert task.getTitle().equals("Create Task Manager App");
    }

    @Test
    void createSubTask() {
    }

    @Test
    void createNestedSubTask() {
    }
}