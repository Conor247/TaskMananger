package com.conor.taskmanager.domain.service.delete;

import com.conor.taskmanager.domain.service.common.AbstractTaskService;
import com.conor.taskmanager.domain.service.common.ReactiveTemplateInterface;
import com.conor.taskmanager.domain.service.create.CreateTaskService;
import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteTaskService extends AbstractTaskService implements DeleteTaskInterface {

    ReactiveTemplateInterface reactiveTemplateInterface;

    public Mono<DeleteResult> deleteTaskById(String id) {
        final Logger log = LoggerFactory.getLogger(CreateTaskService.class);
        Query query = new Query(Criteria.where("id").is(id));
        return reactiveTemplateInterface.removeTask(query)
                .doOnError(e -> log.error("Error occurred while deleting task with id: " + id, e));
    }
}
