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
- **Consumer Offsets:** Any message that's produced into the topic will have a unique ID called offset. Consumers have three options when it comes to reading the messages from the topic.
  - They can read the messages from the beginning using **--from-beginning**
  - They can read the messages from the **latest**. Meaning read only the messages that's going to come after the consumers spun up.
  - They can read the messages from **specific offset**. Meaning, read the messages and the topic by passing a specific offset value from the consumer. This option can only be done programmatically.
  - If for some reason the consumer crashed and while it is down, the producer of the topic produced some more messages. Now the consumer is brought up after some time, how does it know that it needs to read from offset for the consumer? The consumer offset in general are stored in an internal topic called **__consumer_offsets**. In a nutshell, the consumer offsets behave like a bookmark for the consumers to go on check from which point in the topic it needs to read the messages from.
  - There is a handy command that you can run, which is going to list all the topics that you have in your broker: [List the topics in a cluster](./SetUpKafka.md#list-the-topics-in-a-cluster)
- **Group Id:** It plays a major role when it comes to scalable message consumption.
  - Each different application will have a unique consumer group.
  - The consumer groups are fundamentally the basis for a scalable message consumption.
  - The Kafka Broker manages the consumer-groups, it also acts as a group coordinator.
- **Consumer Groups:**
  - Consumer groups are used for scalable message consumption.
  - Different applications will need to have a unique group id.
  - It is the Kafka broker which manages the consumer group.
- **Commit Log:**
  - When the producer sends a message, it first reaches the topic and then the very next thing that happens is that the record gets returned to a file system in the machine. So the file system is where the Kafka Broker is installed, for this example, it is our local machine: /tmp/kafka-logs
  - The log record is always written into the file system as bytes.
  - So when the consumer who is continuously pulling for new records, can only see the records that are committed to the file system, as new records are produced for the topic, then the records get appended to the log file and the process continues.
- **Retention Policy:**
  - Retention policy is one of the key properties that's going to determine how long the log message is going to be retained.
  - Retention policy is configured using the **log.retention.hours** property in the **server.properties** file
  - The default kafka retention policy period is 168 hours (seven days).
- **Apache Kafka as a distributed system:** A distributed system, in general, are a collection of systems work and interact together in order to deliver some functionality or value. Some characteristic of distributed system are:
  - **Availability and Fault Tolerance**.
  - **Reliable Work Distribution**.
  - **Easy Scalable**.
  - **Handling Concurrency is fairly easy**.
  - There are a lot more... for now let's focus on Kafka. 
- **Kafka Cluster:** 
  - Normally you may want to have more than one broker as part of the kafka Cluster, they will be managed by zookeeper.
  - All the kafka brokers send a heartbeat to the zookeeper at regular intervals to ensure that the state of the broker is healthy and active to serve client requests.
  - If one of the kafka brokers goes down, then the cluster manager, which is the zookeeper here, gets notified, then all the client requests will be routed to the other available brokers. By this way, their clients won't have any clue that an issue is going on.
  - It is easy to scale the number of brokers in the cluster without affecting the clients.
  - Kafka retains a record and a file system and each broker will have its own file system in the event of failure.
  - In the event of failure, Kafka handles it using replication.


## Information references
- https://www.conduktor.io/kafka/kafka-topics-cli-tutorial