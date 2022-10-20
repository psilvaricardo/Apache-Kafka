package com.kafka2022.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

/**
 * THIS IS JUST FOR TESTING PURPOSES ONLY
 * NOT RECOMMENDED FOR PRODUCTION ENVIRONMENT.
 * */

@Configuration
@Profile("local")
public class AutoCreateConfig {

    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name("library-events")  // this is the name of the topic created programmatically
                .partitions(3)         // the number of partitions
                .replicas(1)            // the number of replicas, which should be equal to the cluster
                .build();
    }

    /**
     * After starting the zookeeper and the broker, this command should give you the list of topics
     * ./zookeeper-server-start.sh ../config/zookeeper.properties
     * ./kafka-server-start.sh ../config/server.properties
     *
     * ./kafka-topics.sh --bootstrap-server localhost:9092 --list
     * library-events
     * */

}
