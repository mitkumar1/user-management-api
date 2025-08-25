# Kafka in Microservices & Spring Boot

## What is Kafka?
Apache Kafka is a distributed, fault-tolerant event streaming platform used to build real-time data pipelines and streaming applications. It is designed for high-throughput, low-latency, and scalable message processing.

## Why Use Kafka in Microservices?
- **Decoupling:** Services communicate via events, not direct calls.
- **Scalability:** Handles high volumes of data and traffic.
- **Reliability:** Guarantees message delivery (at least once, exactly once, etc.).
- **Event Sourcing:** Enables event-driven architecture and audit trails.
- **Asynchronous Communication:** Improves performance and resilience.

## Kafka Internal Working Structure

**Producer:** Sends messages (events) to Kafka topics.
**Topic:** Logical channel to which messages are sent; topics are partitioned for scalability.
**Partition:** Subset of a topic; enables parallelism and ordering within a partition.
**Broker:** Kafka server that stores and serves messages.
**Consumer:** Reads messages from topics (can be grouped for load balancing).

**Flow:**
1. Producer sends message to a topic.
2. Kafka broker stores message in a partition.
3. Consumer subscribes to topic and reads messages.

## Kafka Internal Workflow & Diagram

Below is a simplified diagram showing how Kafka works internally:

```
   +-----------+         +---------+         +---------+         +-----------+
   | Producer  |  --->   | Broker  |  --->   | Topic   |  --->   | Consumer  |
   +-----------+         +---------+         +---------+         +-----------+
       |                   |                   |                    |
       |                   |                   |                    |
       |                   |                   |                    |
       |                   |                   |                    |
```

**Detailed Workflow:**
1. **Producer** creates and sends messages to a specific Kafka topic.
2. **Broker** receives the message and stores it in the appropriate topic partition.
3. **Topic** organizes messages; partitions allow parallel processing and ordering.
4. **Consumer** subscribes to the topic and reads messages from one or more partitions.

## Kafka Benefits
- **High Throughput:** Handles large volumes of data efficiently.
- **Scalability:** Easily scales horizontally by adding brokers and partitions.
- **Fault Tolerance:** Replicates data across brokers for reliability.
- **Durability:** Persists messages on disk until consumed.
- **Decoupling:** Producers and consumers are independent, enabling flexible architectures.
- **Real-Time Processing:** Supports event-driven and streaming use cases.


## How to Use Kafka in Spring Boot (Step by Step)

### 1. Add Dependencies
Add to `pom.xml`:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka</artifactId>
</dependency>
```

### 2. Configure Kafka Properties
Add to `application.properties`:
```
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

### 3. Create a Producer
```java
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public MyProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
```

### 4. Create a Consumer
```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyConsumer {
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received: " + message);
    }
}
```

### 5. Run Kafka Locally (for testing)
- Download Kafka from https://kafka.apache.org/downloads
- Start Kafka: `bin/kafka-server-start.sh config/server.properties`
- Create topic: `bin/kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1`

### 6. Send and Receive Messages
- Inject `MyProducer` in your controller/service and call `sendMessage("my-topic", "Hello Kafka!")`.
- The consumer will print received messages.

## Summary Table
| Component  | Role in Kafka Ecosystem           |
|------------|-----------------------------------|
| Producer   | Sends messages to topics          |
| Topic      | Logical channel for messages      |
| Partition  | Subset of topic for scalability   |
| Broker     | Kafka server                      |
| Consumer   | Reads messages from topics        |

## Best Practices
- Use separate topics for different event types.
- Use consumer groups for scaling consumers.
- Handle errors and retries in consumers.
- Monitor lag and broker health.

#############################################################
SECTION: Kafka Internals & Advanced Features
#############################################################
What happens inside Kafka?
- **Replication:** Each partition is replicated across multiple brokers for fault tolerance. One broker acts as the leader, others as followers.
- **Leader Election:** Kafka automatically elects a leader for each partition. Producers/consumers interact with the leader; followers sync data.
- **Log Compaction & Retention:** Kafka can retain messages for a set time or size, and compact logs to keep only the latest value for a key.
- **Consumer Offsets:** Kafka tracks the last read message for each consumer group, allowing reliable processing and recovery.
- **Exactly-Once Semantics:** Kafka supports exactly-once delivery for both producers and consumers (with configuration).
- **Transactions:** Kafka allows atomic writes to multiple partitions/topics, ensuring data consistency.

#############################################################
SECTION: Advanced Kafka Ecosystem
#############################################################
- **Kafka Streams:** Library for building real-time, stateful stream processing applications directly on Kafka.
- **Kafka Connect:** Framework for integrating Kafka with external systems (DBs, files, cloud, etc.) using connectors.
- **Schema Registry:** Manages message schemas for topics, enabling compatibility and evolution.
- **Security:** Supports SSL/TLS encryption, SASL authentication, and ACL-based authorization.

#############################################################
SECTION: Real-World Use Cases & Design Patterns
#############################################################
- **Event Sourcing:** Store all changes as events in Kafka for audit and replay.
- **CQRS (Command Query Responsibility Segregation):** Separate write and read models using Kafka topics.
- **Data Lake Integration:** Use Kafka to stream data into data lakes or analytics platforms.
- **Microservices Choreography:** Services communicate via Kafka events, enabling loose coupling and scalability.

#############################################################
SECTION: Failure Handling & Monitoring
#############################################################
- **Broker Failure:** Kafka automatically reassigns partition leaders if a broker fails.
- **Consumer Recovery:** Consumers can restart and resume from last committed offset.
- **Partition Reassignment:** Kafka supports moving partitions between brokers for load balancing or recovery.
- **Monitoring:** Use tools like Kafka Manager, Confluent Control Center, or Prometheus/Grafana to monitor health, lag, throughput, and errors.

#############################################################
SECTION: Interview Tips for Senior FSD Roles
#############################################################
- Be ready to discuss Kafka internals, advanced features, and real-world design patterns.
- Know how to handle failures, tune performance, and secure Kafka clusters.
- Understand integration with other systems (Spark, Flink, Hadoop, etc.).
- Be able to explain event-driven architecture and why Kafka is chosen over other messaging systems.
- Share practical experiences: scaling, troubleshooting, and optimizing Kafka in production.

---
This guide provides a practical overview and step-by-step implementation for using Kafka in Spring Boot microservices.
