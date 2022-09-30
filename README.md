# Apache Kafka 2022

Apache Kafka for Developers using Spring Boot[Latest Edition]


# Apache Kafka for Developers using Spring Boot[LatestEdition]

Learn to build enterprise standard Kafka producers/consumers with Kafka Unit/Integration tests using Spring Boot.

## Apache Kafka for Developers using Spring Boot
This repository has the complete code related to kafka producers/consumers using spring boot.

## Environment

By the time of writing this project was set up using the following environment: 
- Linux arch 5.18.3-arch1-1 #1 SMP PREEMPT_DYNAMIC Thu, 09 Jun 2022 16:14:10 +0000 x86_64 GNU/Linux

## Installation for Arch Linux
- $ sudo pacman -S intellij-idea-community-edition jdk-openjdk jre-openjdk
- $ paru -S kafka
- $ sudo systemctl enable kafka.service

## Installation for Linux
- Go to https://kafka.apache.org/downloads
- From the latest release version, check the higher Scala version
- By the time of writing, recommended version 2.13 was downloaded from https://downloads.apache.org/kafka/3.2.1/kafka_2.13-3.2.1.tgz

## Setting Up Kafka
- https://github.com/dilipsundarraj1/kafka-for-developers-using-spring-boot/blob/master/SetUpKafka.md

## Key Concepts, Useful Resources & Links

- **Kafka Topic:** A Topic is an **Entity** in Kafka, and it has a name. (A quick analogy is to think of Topic as a Table in a Data Base). Topics in general live inside the Kafka Broker. Kafka client uses the topic name to produce and consume messages. The Producer will use the topic name to produce a message.
- **Partitions:** Partitions is where actually the message is located inside the topic. Each topic in general can have one or more partitions. Each partition is an ordered, immutable sequence of records. That means once a record is produced, it cannot be changed at all. Each record as a number associated with a number called Offset. An offset is generated when a record is published to the topic. Offsets play an important role when it comes to consumers. Each partition is independent of each other and that's why you will see the offset in both of those partitions. Ordering is guaranteed only at the partition level.
