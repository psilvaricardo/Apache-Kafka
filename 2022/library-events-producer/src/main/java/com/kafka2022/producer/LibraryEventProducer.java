package com.kafka2022.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka2022.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The Controller is calling this class.
 * @see com.kafka2022.controller.LibraryEventsController
 * */
@Component
@Slf4j
public class LibraryEventProducer {

    /**
     * This is the KafKa template that is created for us by SpringBoot.
     * For the key-serializer as part of the yml file, we have IntegerSerializer
     * For the value-serializer as part of the yml file, we have StringSerializer
     *
     * That's the reason why we have <Integer, String>
     * */
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private String topic = "library-events";

    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * */
    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {

        // getting the key and value
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent); // convert the LibraryObject to String

        // using sendDefault we don't have to specify the topic value, it is reading is from the yml file
        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.sendDefault(key,value);
        // this callback
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

            /**
             * onFailure callback will be called in case of error
             **/
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            /**
             * onSuccess callback will be called if the publishing is successful
             **/
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public ListenableFuture<SendResult<Integer,String>> sendLibraryEvent_Approach2(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        // with sendDefault, you depend on whatever you have on the config yml file
        // but when you use send, you have the freedom to pass the topic Id as parameter,
        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key, value, topic);
        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });

        return listenableFuture;
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
        // the headers are additional information, metadata about the message
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }


    public SendResult<Integer, String> sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        SendResult<Integer,String> sendResult=null;
        try {
            sendResult = kafkaTemplate.sendDefault(key,value).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }

        return sendResult;
    }


}
