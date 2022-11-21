package com.kafka2022.jpa;

import com.kafka2022.config.LibraryEventConsumerConfig;
import com.kafka2022.entity.FailureRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class FailureRecordRepositoryTest {

    @Autowired
    FailureRecordRepository failureRecordRepository;

    @BeforeEach
    public void setUp(){
        var record = "{\"libraryEventId\":1,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot 2.X\",\"bookAuthor\":\"Richard\"}}";

        var failureRecord = new FailureRecord(null,"library-events", 123, record,1,0L, "exception occurred", LibraryEventConsumerConfig.RETRY);
        var failureRecord1= new FailureRecord(null,"library-events", 123, record,1,1L, "exception occurred",LibraryEventConsumerConfig.DEAD);

        failureRecordRepository.saveAll(List.of(failureRecord, failureRecord1));
    }

    @Test
    void findAllByStatus() {

        //when
        var failRecordList = failureRecordRepository.findAllByStatus(LibraryEventConsumerConfig.RETRY);

        //then
        assertEquals(1, failRecordList.size());
    }

}
