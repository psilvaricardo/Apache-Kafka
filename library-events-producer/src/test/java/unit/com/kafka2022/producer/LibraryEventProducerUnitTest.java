package com.kafka2022.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka2022.domain.Book;
import com.kafka2022.domain.LibraryEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

/**
 * This class will test the kafkaTemplate.send and onFailure methods as part of the LibraryEventProducer
 * @see com.kafka2022.producer.LibraryEventProducer
 * */
@ExtendWith(MockitoExtension.class)
public class LibraryEventProducerUnitTest {

    @Mock
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    LibraryEventProducer eventProducer;

    @Test
    void sendLibraryEvent_Approach2_onFailure() throws JsonProcessingException, ExecutionException, InterruptedException {
        //given
        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Richard")
                .bookName("Kafka using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        // this is a mock response for the kafkaTemplate.send method call
        SettableListenableFuture future = new SettableListenableFuture();
        // we set an exception, this way we try to simulate/mimic the onFailure scenario
        future.setException(new RuntimeException("Exception Calling Kafka"));
        // then, we have a mock created and every time we call it, it is going to simulate a failure.
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);
        //when

        assertThrows(Exception.class, ()-> eventProducer.sendLibraryEvent_Approach2(libraryEvent).get());

    }

    @Test
    void sendLibraryEvent_Approach2_onSuccess() throws JsonProcessingException, ExecutionException, InterruptedException {
        //given
        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Richard")
                .bookName("Kafka using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        // the producer needs this one
        String record = objectMapper.writeValueAsString(libraryEvent);
        // this is a mock response for the kafkaTemplate.send method call
        SettableListenableFuture future = new SettableListenableFuture();

        // producer record is needed as part of the sendResult param
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord("library-events", libraryEvent.getLibraryEventId(),record );
        // record metadata is needed as part of the sendResult param
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("library-events", 1),
                1,1,System.currentTimeMillis(), 1, 2);

        // we are building the result based on the same object type we have on the Event Producer
        SendResult<Integer, String> sendResult = new SendResult<Integer, String>(producerRecord,recordMetadata);
        // setting the sendResult into the 'future'
        future.set(sendResult);

        //when
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);

        //then
        ListenableFuture<SendResult<Integer,String>> listenableFuture =  eventProducer.sendLibraryEvent_Approach2(libraryEvent);
        SendResult<Integer,String> sendResult1 = listenableFuture.get();
        assert sendResult1.getRecordMetadata().partition()==1;

    }

}