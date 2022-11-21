package com.kafka2022.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka2022.config.LibraryEventConsumerConfig;
import com.kafka2022.entity.FailureRecord;
import com.kafka2022.jpa.FailureRecordRepository;
import com.kafka2022.service.LibraryEventsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
@Disabled
public class RetrySchedulerIntegrationTest {

    @SpyBean
    LibraryEventsService libraryEventsServiceSpy;

    @Autowired
    RetryScheduler retryScheduler;

    @Autowired
    FailureRecordRepository failureRecordRepository;

    @BeforeEach
    public void setUp(){

        failureRecordRepository.deleteAll();

        var record = "{\"libraryEventId\":1,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot 2.X\",\"bookAuthor\":\"Dilip\"}}";

        var failureRecord = new FailureRecord(null,"library-events", 123, record,1,0L, "exception occurred", LibraryEventConsumerConfig.RETRY);
        var failureRecord1= new FailureRecord(null,"library-events", 123, record,1,1L, "exception occurred",LibraryEventConsumerConfig.DEAD);

        failureRecordRepository.saveAll(List.of(failureRecord, failureRecord1));
    }

    @Test
    @Disabled
    public void retryFailedRecords() throws JsonProcessingException {

        retryScheduler.retryFailedRecords();

        Mockito.verify(libraryEventsServiceSpy, times(1) ).processLibraryEvent(Mockito.isA(ConsumerRecord.class));
    }

}
