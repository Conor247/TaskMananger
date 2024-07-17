package com.conor.taskmanager.database;

import com.conor.taskmanager.domain.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task,String> {
}
