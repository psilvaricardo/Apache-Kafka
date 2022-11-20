package com.kafka2022.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;


@Configuration
@EnableKafka
public class LibraryEventConsumerConfig {

    @Autowired
    private KafkaProperties properties;

    /**
     * This Bean was copy/pasted from KafkaAnnotationDrivenConfiguration.java to override the behavior
     *
     * */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory
                .getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.properties.buildConsumerProperties())));

        // let's override the Acknowledged mode
        // we are manually manage the offset, for this to work, we need a new listener
        // factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // we can leverage this to configure multiple KafKa listeners from the same applications itself
        // if we have our application running in kubernetes/cloud this is not necessary

        // we are going to span 3 threads with the same instance of the Kafka listeners.
        // when the application is up and running we can check that the container is running on a different thread
        // for this example, 3 container threads will be listening to the same partition, in parallel.
        // this option is recommended if you are not running your application in a cloud-like environment
        factory.setConcurrency(3); // because we have 3 partitions

        return factory;
    }
}
