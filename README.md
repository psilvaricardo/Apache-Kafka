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
- **Kafka Message:** Every message is sent from the producer and has two values:
  - Key (Optional)
  - Value (Mandatory)
- **Partitioner:** When the producer is invoked for sending a message, it goes through a lot of layers behind the scenes before the message is sent to Kafka, one of the layers is the **Partitioner.** The partitioner first checks, whether a key is present as part of the message or not. 
  - When you are not sending any Key, the partitioner will use the Round-Robin approach to send a message across all the existing partitions, meaning all your messages could end up distributed across all the partitions. In this approach, there is no guarantee the consumer will be able to read all the messages in the same order because consumer pulls the messages from all the partitions at the same time.
  - When you are using a Key, (the Key can be of any type, the most common example is a String Key), the Producer is going to apply some hashing technique to determine the partition value. When the same key is sent for two or multiple messages, it's going to resolve the messages to the same partition. And the same will be applied fot all other messages with different keys. Keep in mind, **same key always results to the same partition**.
- **Consumer Offsets:** Any message that's produced into the topic will have a unique ID call offset. Consumers have three options when it comes to reading the messages from the topic.
  - They can read the messages from the beginning using **--from-beginning**
  - They can read the messages from the **latest**. Meaning read only the messages that's going to come after the consumers spun up.
  - They can read the messages from **specific offset**. Meaning, read the messages and the topic by passing a specific offset value from the consumer. This option can only be done programmatically.


## Information references
- https://www.conduktor.io/kafka/kafka-topics-cli-tutorial