package com.conor.taskmanager.config;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoAppConfig {

    //use the mongo uri environment variable in mongo docker container as
    //configuring it explicitly caused local builds and bootRuns to fail
    @Value("${spring.data.mongodb}")
    private String mongoUri;

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(MongoClients.create(mongoUri), "database");
    }
}
