#################################################################################
Section 7: Spring Boot, Spring Cloud, Cloud Foundry, Git, and Miscellaneous
#################################################################################

1) What is spring-boot-starter-web?
**Answer:**
It is a starter dependency for building web, including RESTful, applications using Spring MVC. It includes Tomcat as the default embedded container.

2) What is Spring Cloud?
**Answer:**
Spring Cloud provides tools for developers to quickly build distributed systems, including configuration management, service discovery, circuit breakers, intelligent routing, micro-proxy, control bus, one-time tokens, global locks, and more.

3) What problems are solved by Spring Cloud?
**Answer:**
Spring Cloud solves problems like distributed configuration, service discovery, load balancing, circuit breaking, distributed messaging, and monitoring in microservices architectures.

4) What is service registry and discovery?
**Answer:**
Service registry is a database of service instances. Service discovery is the process of finding a network location of a service instance. Tools like Eureka or Kubernetes Service Registry are used.

5) How to achieve server side load balancing using Spring Cloud?
**Answer:**
By using Kubernetes Services, DNS-based service discovery, and Ingress controllers, which distribute requests across multiple service instances and manage external access and load balancing.

6) What is spring-boot-devtools?
**Answer:**
It is a module that provides features for rapid application development, such as automatic restarts, live reload, and configurations for improved developer productivity.

7) How to disable specific auto-configuration in Spring Boot?
**Answer:**
Use the @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) annotation or @EnableAutoConfiguration(exclude = ...).

8) How to implement config server?
**Answer:**
Create a Spring Cloud Config Server application, annotate with @EnableConfigServer, and configure it to serve properties from a Git repository or file system.

9) How can you maintain different versions of config server for the same app with different versions?
**Answer:**
By using different branches or directories in the config repository, each corresponding to a specific application version.

10) Key differences between SOA and Microservices Architecture
**Answer:**
SOA is typically based on ESB and focuses on reusability and integration, while microservices are independently deployable, smaller, and focus on business capabilities with decentralized data management.

11) Performance Tuning of Rest APIs
**Answer:**
Use connection pooling, caching, pagination, async processing, minimize payload, and monitor with tools like JMeter or Prometheus.

12) Difference between PUT and POST HTTP methods
**Answer:**
PUT is idempotent and used to update/replace a resource, while POST is used to create a new resource and is not idempotent.

13) What do you know about Cloud Foundry?
**Answer:**
Cloud Foundry is an open-source cloud application platform for deploying, running, and scaling applications. It supports multiple languages and frameworks.

14) How is monitoring and analyzing of Cloud Foundry done?
**Answer:**
Using tools like Loggregator, Health Manager, and third-party integrations (e.g., Splunk, New Relic) for logs, metrics, and health checks.

15) What is a Buildpack? How can you use your custom buildpack?
**Answer:**
A buildpack provides runtime and framework support for apps. Custom buildpacks can be created and specified during app deployment using the -b flag in cf push.

16) What is Loggregator?
**Answer:**
Loggregator is Cloud Foundry’s component for collecting and streaming logs and metrics from applications and platform components.

17) How does Splunk read logs from apps deployed in PCF?
**Answer:**
Splunk can be integrated with PCF via the Loggregator Firehose, forwarding logs and metrics to Splunk for analysis.

18) What is CI and CD process?
**Answer:**
CI (Continuous Integration) is the practice of merging code changes frequently and running automated tests. CD (Continuous Delivery/Deployment) automates the release of code to production.

19) How to create CUP services and how to use these in APIs?
**Answer:**
CUP (Custom User Provided) services in Cloud Foundry are created using cf create-user-provided-service and bound to apps to inject credentials or endpoints.

20) Basic overview of Jenkins, SonarQube, JMeter, and Jacoco code coverage
**Answer:**
Jenkins: CI/CD automation server. SonarQube: Code quality and security analysis. JMeter: Performance/load testing. Jacoco: Java code coverage tool.

21) What is the difference between git pull and git fetch?
**Answer:**
git fetch downloads new data from a remote repository but does not integrate it, while git pull fetches and merges changes into the current branch.

22) What is the difference between git merge and git rebase?
**Answer:**
git merge combines histories, creating a merge commit. git rebase moves or combines a sequence of commits to a new base commit, creating a linear history.

23) How to stop a PCF component?
**Answer:**
Use the cf stop <app-name> command to stop an application in PCF.

24) How to scale a PCF component?
**Answer:**
Use the cf scale <app-name> -i <instances> command to change the number of running instances.

25) How is config server used in deployment?
**Answer:**
Config server provides externalized configuration to applications at startup, allowing dynamic updates without redeploying the app.

26) How to return a value from a Thread?
**Answer:**
Use Callable and Future interfaces in Java to return a value from a thread.

27) Is it allowed to use Class as a HashMap key? If yes, how to implement?
**Answer:**
Yes, Class objects can be used as HashMap keys because they have proper equals and hashCode implementations. Example: Map<Class<?>, Object> map = new HashMap<>();

28) What is the Collections.synchronizedMap() method?
**Answer:**
It returns a thread-safe (synchronized) map backed by the specified map.

29) Some annotations used in Unit test classes
**Answer:**
@Test, @Before, @After, @BeforeClass, @AfterClass, @Mock, @InjectMocks, @RunWith

30) Some annotations used in Spring Boot
**Answer:**
@SpringBootApplication, @RestController, @Autowired, @Component, @Service, @Repository, @Value, @Configuration

31) How to autowire and how to undo autowire?
**Answer:**
Autowire using @Autowired annotation. To undo, remove the annotation or use @Primary/@Qualifier to change the bean selection.

32) Why do we need to use Spring Boot instead of Spring?
**Answer:**
Spring Boot simplifies setup, configuration, and deployment with embedded servers, auto-configuration, and production-ready features, reducing boilerplate and time to market.

1) What are the 12 factors of a microservice application?
**Detailed Explanation:**
The 12-factor app methodology provides best practices for building scalable, maintainable microservices. The factors include codebase, dependencies, config, backing services, build/release/run, processes, port binding, concurrency, disposability, dev/prod parity, logs, and admin processes.

**Banking Example:**
In a banking microservice, configuration is externalized, logs are streamed, and each service is independently deployable and stateless.

2) How does the DAO layer work in a microservice, and what is the end-to-end request/response flow?
**Detailed Explanation:**
The flow starts at the Controller, which receives the HTTP request, calls the Service layer for business logic, which then interacts with the DAO (Data Access Object) layer to fetch or persist data. The DAO uses JPA/Hibernate or JDBC to access the database, and the response flows back up to the client.

**Banking Example:**
In the Account Service, a REST call to /accounts/123 triggers the controller, which calls the service to fetch account details from the DAO, which queries the accounts table and returns the result.

3) What is pair programming?
**Detailed Explanation:**
Pair programming is an agile practice where two developers work together at one workstation. One writes code (driver), the other reviews each line (observer/navigator), switching roles frequently.

**Banking Example:**
Developers pair to implement and review the Transaction Service, improving code quality and knowledge sharing.

4) How are service discovery, registry, load balancing, and security implemented in your banking microservices project?
**Detailed Explanation:**
Service discovery and registry are managed by Kubernetes Services and DNS. Load balancing is handled by Kubernetes and Ingress. Security is enforced using OAuth2/JWT, HTTPS, and API Gateway.

**Banking Example:**
Account and Transaction Services are registered as Kubernetes Services, discovered via DNS, and secured with JWT tokens. Ingress provides load balancing and external access.

5) How is the circuit breaker pattern used and implemented in your project?
**Detailed Explanation:**
Circuit breaker prevents repeated calls to failing services. In Spring Boot, it's implemented using Resilience4j or Hystrix annotations.

**Banking Example:**
If Notification Service is down, Transaction Service uses @CircuitBreaker from Resilience4j to return a fallback response and avoid blocking transactions.

6) How is application performance monitoring and alerting implemented?
**Detailed Explanation:**
Performance is monitored using Prometheus for metrics and Grafana for dashboards. Alerts are configured for error rates, latency, and resource usage.

**Banking Example:**
Prometheus scrapes metrics from all services. Grafana sends alerts to Slack/email if transaction latency exceeds thresholds.

7) How is log aggregation handled, and how do you troubleshoot issues using SPLUNK/ELK?
**Detailed Explanation:**
Logs from all services are sent to a central system (ELK or Splunk). Kibana/Splunk dashboards help search, visualize, and troubleshoot issues.

**Banking Example:**
If a transaction fails, logs from Account and Transaction Services are searched in Kibana/Splunk to trace the error and debug the root cause.

8) How is Kafka implemented, and how are load and failover managed?
**Detailed Explanation:**
Kafka is used for asynchronous messaging between services. Load is managed by partitioning topics and scaling consumers. Failover is handled by Kafka's replication and consumer group rebalancing.

**Banking Example:**
Transaction Service publishes events to a Kafka topic. Multiple Notification Service instances consume from the topic, ensuring high availability and load distribution.

9) What are Factory and Abstract Factory design patterns, and how are they used?
**Detailed Explanation:**
Factory pattern creates objects without exposing instantiation logic. Abstract Factory provides an interface for creating families of related objects.

**Banking Example:**
Factory pattern is used to create different types of notifications (SMS, Email) in Notification Service. Abstract Factory can be used to create DAOs for different databases (MySQL, Oracle) in the DAO layer.


1) What is microservices architecture?
Microservices architecture is a design approach where an application is structured as a collection of small, independent services. Each service is responsible for a specific business capability, runs in its own process, and communicates with other services via lightweight protocols (typically HTTP/REST or messaging).

**Key Characteristics:**
- Each microservice is independently deployable and scalable.
- Services can be developed using different programming languages and technologies.
- Decentralized data management: each service manages its own database.
- Fault isolation: failure in one service does not affect the whole system.

**Typical Microservices Architecture Diagram:**

```
            +-------------------+
            |   API Gateway     |
            +--------+----------+
                     |
   +-----------------+-----------------+
   |         |           |              |
 +-----+  +-----+    +-------+      +-------+
 |User |  |Order|    |Product|      |Payment|
 |Svc  |  |Svc  |    |Svc    |      |Svc    |
 +-----+  +-----+    +-------+      +-------+
   |        |           |              |
 +---+   +---+      +-----+        +-----+
 |DB1|   |DB2|      |DB3 |        |DB4  |
 +---+   +---+      +-----+        +-----+
```


**Use Case Example:**
Suppose you are building a banking application. Instead of a single monolithic system, you split the application into microservices:
- **Account Service:** Manages customer accounts, balances, and account creation.
- **Transaction Service:** Handles money transfers, deposits, and withdrawals.
- **Customer Service:** Manages customer profiles and KYC information.
- **Notification Service:** Sends SMS/email alerts for transactions and account activity.
Each service can be developed, deployed, and scaled independently. For example, during peak hours, you can scale only the Transaction Service to handle increased transaction volume.


2) What are the main features of microservices?
**Detailed Explanation:**
Microservices are characterized by independent deployment, decentralized data management, technology diversity, scalability, and fault isolation. Each service is small, focused on a single business capability, and can be developed, deployed, and scaled independently.

**Diagram:**
```
 [Service A]   [Service B]   [Service C]
     |             |             |
   [DB A]        [DB B]        [DB C]
```

**Use Case:**
In a banking system, the Account, Transaction, and Customer services can be updated or scaled independently. For example, during salary credit days, only the Transaction Service may need to be scaled up.


3) How do microservices communicate with each other?
**Detailed Explanation:**
Microservices communicate using lightweight protocols such as HTTP/REST, gRPC, or asynchronous messaging (RabbitMQ, Kafka). Synchronous communication uses REST/gRPC, while asynchronous uses message brokers for decoupling.

**Diagram:**
```
Service A --HTTP/REST--> Service B
Service C --Kafka--> Service D
```

**Use Case:**
When a fund transfer is initiated, the Transaction Service sends a message to the Notification Service via RabbitMQ to alert the customer.


4) What is service discovery?
**Detailed Explanation:**
Service discovery enables automatic detection and registration of services in a distributed system. It allows services to find and communicate with each other dynamically, even as instances scale up or down.

**Diagram:**
```
[Service A] <--> [Service Registry] <--> [Service B]
```

**Use Case:**
Using Kubernetes, when a new instance of Account Service starts, it is automatically registered as a Kubernetes Service. Other services (like Transaction Service) discover and communicate with it using Kubernetes DNS. Ingress can be used to expose services externally and manage routing.


5) How do you handle configuration in microservices?
**Detailed Explanation:**
Centralized configuration management allows all services to fetch their configuration from a single source, such as Spring Cloud Config or Consul. This ensures consistency and simplifies updates.

**Diagram:**
```
[Config Server] --> [Service A]
                --> [Service B]
```

**Use Case:**
All banking microservices (Account, Transaction, Customer) fetch their database credentials and configuration from a central Spring Cloud Config server at startup.


6) What is API Gateway and why is it used?
**Detailed Explanation:**
An API Gateway is a server that acts as a single entry point for all client requests. It handles routing, authentication, rate limiting, and can aggregate responses from multiple services.

**Diagram:**
```
Client --> [API Gateway] --> [User Service]
                        --> [Order Service]
```

**Use Case:**
In a banking app, the API Gateway routes login requests to the Customer Service and transaction requests to the Transaction Service, hiding internal service details from clients.


7) How do you ensure data consistency in microservices?
**Detailed Explanation:**
Microservices use eventual consistency, distributed transactions (Saga pattern), and careful design of service boundaries to maintain data integrity. Strong consistency is avoided to reduce coupling.

**Diagram:**
```
[Order Service] --Saga--> [Payment Service]
```

**Use Case:**
When a fund transfer is initiated, the Transaction Service starts a Saga. If the debit from the sender's account fails, the Saga triggers a compensating transaction to reverse any partial changes.


8) How do you secure microservices?
**Detailed Explanation:**
Security is enforced using authentication (OAuth2, JWT), authorization, HTTPS for encrypted communication, and secure service-to-service calls. API Gateway often handles authentication.

**Diagram:**
```
Client --JWT--> [API Gateway] --Token--> [Service]
```

**Use Case:**
Banking app users obtain a JWT token after login. All requests to services (Account, Transaction) must include the token, which is validated by the API Gateway.


9) What is circuit breaker pattern?
**Detailed Explanation:**
The circuit breaker pattern prevents a service from making requests to a failing service, allowing it to recover and improving overall system resilience. Libraries like Hystrix or Resilience4j implement this pattern.

**Diagram:**
```
[Service A] --X--> [Service B (Down)]
   | (fallback)
   v
[Default Response]
```

**Use Case:**
If Notification Service is down, Transaction Service returns a default response or logs the failure, using a circuit breaker, so the transaction can still proceed without blocking.


10) How do you monitor microservices?
**Detailed Explanation:**
Monitoring is achieved using metrics collection (Prometheus), visualization (Grafana), centralized logging (ELK stack), and distributed tracing (Zipkin, Sleuth) to track requests across services.

**Diagram:**
```
[Service] --> [Prometheus] --> [Grafana]
         --> [Logstash] --> [Elasticsearch] --> [Kibana]
```

**Use Case:**
Prometheus scrapes metrics from all banking services (Account, Transaction, Customer), and Grafana displays dashboards for real-time monitoring. ELK stack aggregates and visualizes logs for auditing and compliance.


11) How do you deploy microservices?
**Detailed Explanation:**
Each microservice is packaged (often as a Docker container) and deployed independently. Orchestration tools like Kubernetes manage scaling, networking, and health checks.

**Diagram:**
```
[Docker Image] --> [Kubernetes Cluster]
```

**Use Case:**
Account Service and Transaction Service are built as Docker images and deployed to a Kubernetes cluster, allowing independent scaling and updates in the banking system.


12) What are the benefits of using microservices architecture?
**Detailed Explanation:**
Microservices offer scalability, independent deployment, technology diversity, better fault isolation, and easier maintenance. Teams can work on different services in parallel.

**Diagram:**
```
[Service 1]  [Service 2]  [Service 3]
   |            |            |
 [Team A]    [Team B]    [Team C]
```

**Use Case:**
In a large bank, separate teams manage and deploy different services (Account, Transaction, Notification), enabling faster development and innovation.

13) How do you migrate an application from monolithic to microservices using Spring Boot?
Step-by-step process:
1. Analyze the monolith: List all business functions and modules.
2. Identify microservice boundaries: Group related features/data into logical services.
3. Extract the first microservice: Copy related code to a new Spring Boot project.
4. Set up a separate database for the new service (if possible).
5. Expose REST APIs for the new service.
   Example (Spring Boot Controller):
   ```java
   @RestController
   @RequestMapping("/users")
   public class UserController {
       @GetMapping("/{id}")
       public User getUser(@PathVariable Long id) {
           // fetch user from DB
       }
   }
   ```
6. Update the monolith to call the new service via API instead of direct code/database access.
   Example (RestTemplate call from monolith):
   ```java
   RestTemplate restTemplate = new RestTemplate();
   User user = restTemplate.getForObject("http://user-service/users/1", User.class);
   ```
7. Test end-to-end to ensure the new service works independently.
8. Repeat: Gradually extract more services, one at a time, until the monolith is fully split.
9. Use tools like Eureka (service discovery), Spring Cloud Config, and API Gateway for better management.
10. Deploy and monitor each service independently.

This approach reduces risk and allows gradual migration with minimal downtime.

#############################################################
SECTION: How to Enable WSL (Windows Subsystem for Linux) on Windows 11
#############################################################
Step 1: Open PowerShell as Administrator
- Right-click the Start button and select "Windows Terminal (Admin)" or "PowerShell (Admin)".

Step 2: Install WSL
- Run the following command:
  ```powershell
  wsl --install
  ```
- This installs WSL and the default Ubuntu distribution.

Step 3: Restart Your Computer
- After installation, restart your PC if prompted.

Step 4: Launch Linux
- Open the Start menu, search for "Ubuntu" (or your chosen distro), and launch it.
- Complete the initial setup (username and password).

Step 5: (Optional) Install a Different Linux Distribution
- To see available distros:
  ```powershell
  wsl --list --online
  ```
- To install a specific distro (e.g., Debian):
  ```powershell
  wsl --install -d Debian
  ```

Step 6: Update WSL (Optional)
- To upgrade to WSL 2 (recommended for most users):
  ```powershell
  wsl --set-default-version 2
  ```

Step 7: Check WSL Version
- To check your installed WSL version:
  ```powershell
  wsl --list --verbose
  ```

Step 8: Use Linux Terminal
- You can now use Linux commands and tools directly from your Windows environment.

#############################################################
SECTION: Troubleshooting
#############################################################
- If you get errors, make sure virtualization is enabled in BIOS/UEFI.
- Ensure your Windows 11 is up to date.
- For advanced settings, visit Microsoft's official WSL documentation.
