package com.kafka2022.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventsConsumerManualOffset implements AcknowledgingMessageListener<Integer,String> {

    @Override
    @KafkaListener(topics ={"library-events"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) {
        log.info("com.kafka2022.consumer.LibraryEventsConsumerManualOffset.ConsumerRecord : {}", consumerRecord);

        // after the record is processed/read, it is up to the developer to manually call the ack method
        // here you are letting/telling the message listener know that you have successfully processed the message.
        acknowledgment.acknowledge();
    }

}
