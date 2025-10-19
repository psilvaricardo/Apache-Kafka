package com.kafka2022;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LibraryEventsProducerApplication {

	public static void main(String[] args) {
		log.info("Library-Events-Producer Application Started...");
		SpringApplication.run(LibraryEventsProducerApplication.class, args);
		log.info("Library-Events-Producer Application Terminated...");
	}

}
