# ORCIT-EIT Application Architecture
## Bank of America - Risk & Compliance Team
### Senior Developer Interview Guide

---

## Table of Contents
1. [Application Overview](#application-overview)
2. [Module Architecture](#module-architecture)
3. [High-Level Architecture Diagram](#high-level-architecture-diagram)
4. [Microservices Features Overview](#microservices-features-overview)
5. [Frontend to Backend Flow](#frontend-to-backend-flow)
6. [API Security & Gateway](#api-security--gateway)
7. [Microservices Communication](#microservices-communication)
8. [Failure Handling & Resilience](#failure-handling--resilience)
9. [CI/CD Pipeline](#cicd-pipeline)
10. [Database Connectivity](#database-connectivity)
11. [Interview Talking Points](#interview-talking-points)

---

## Application Overview

**ORCIT-EIT** is a comprehensive risk and compliance application at Bank of America designed to handle regulatory reporting, compliance monitoring, and risk assessment workflows.

### What is ORCIT-EIT?
- **ORCIT**: Operational Risk Compliance Intelligence Tool
- **EIT**: Enterprise Intelligence Technology
- **Purpose**: Automate compliance processes, regulatory reporting, and risk monitoring for financial regulations

### Business Context
```
Risk & Compliance Team Responsibilities:
├── Regulatory Reporting (CCAR, DFAST, Basel III)
├── AML (Anti-Money Laundering) Monitoring
├── OFAC Sanctions Screening
├── Credit Risk Assessment
├── Operational Risk Management
└── Compliance Audit Trail
```

---

## Microservices Features Overview

### **Complete Spring Cloud Ecosystem Implementation**

```
ORCIT-EIT Microservices Architecture Components:
├── Service Discovery & Registration
│   ├── Netflix Eureka Server (Service Registry)
│   ├── Service Health Checks & Monitoring
│   └── Dynamic Service Instance Management
│
├── API Gateway & Routing
│   ├── Spring Cloud Gateway
│   ├── Dynamic Route Configuration
│   ├── Request/Response Filtering
│   └── Protocol Translation (HTTP/HTTPS)
│
├── Load Balancing Strategies
│   ├── Client-Side: Spring Cloud LoadBalancer
│   ├── Server-Side: Ribbon (Legacy Support)
│   ├── Algorithms: Round Robin, Weighted, Random
│   └── Health-Check Based Routing
│
├── Circuit Breakers & Resilience
│   ├── Resilience4j (Primary)
│   │   ├── Circuit Breaker Pattern
│   │   ├── Retry Mechanisms
│   │   ├── Rate Limiting
│   │   ├── Time Limiters
│   │   └── Bulkhead Isolation
│   └── Netflix Hystrix (Legacy Support)
│       ├── Command Pattern Implementation
│       ├── Thread Pool Isolation
│       ├── Dashboard & Monitoring
│       └── Turbine Aggregation
│
├── Message Communication
│   ├── Apache Kafka
│   │   ├── Event-Driven Architecture
│   │   ├── Topic-Based Messaging
│   │   ├── Consumer Groups
│   │   └── Exactly-Once Delivery
│   └── Spring Cloud Stream
│       ├── Declarative Messaging
│       ├── Binder Abstraction
│       └── Message Routing
│
├── Configuration Management
│   ├── Spring Cloud Config Server
│   ├── Git-Based Configuration
│   ├── Environment-Specific Properties
│   └── Dynamic Configuration Refresh
│
├── Distributed Tracing & Monitoring
│   ├── AppDynamics
│   │   ├── Application Performance Monitoring
│   │   ├── Business Transaction Monitoring
│   │   ├── Database Performance Monitoring
│   │   └── Infrastructure Monitoring
│   ├── Dynatrace
│   │   ├── AI-Powered Monitoring
│   │   ├── Full-Stack Observability
│   │   ├── Automatic Root Cause Analysis
│   │   └── Real User Monitoring
│   ├── Spring Cloud Sleuth
│   │   ├── Distributed Request Tracing
│   │   ├── Span & Trace Management
│   │   └── Zipkin Integration
│   └── Micrometer Metrics
│       ├── Prometheus Integration
│       ├── Custom Metrics Collection
│       └── Performance Counters
│
├── Security & Authentication
│   ├── OAuth2 + JWT Tokens
│   ├── Okta Integration
│   ├── Method-Level Security
│   └── Inter-Service Authentication
│
└── Database & Persistence
    ├── Oracle Database Connectivity
    ├── Connection Pool Management (HikariCP)
    ├── JPA/Hibernate ORM
    └── Transaction Management
```

### **Technology Stack Summary**

#### **Core Spring Cloud Components**
```yaml
Spring Boot: 2.7.x / 3.x
Spring Cloud: 2022.0.x (Kilburn)
├── Spring Cloud Gateway: API Gateway & Routing
├── Spring Cloud Netflix Eureka: Service Discovery
├── Spring Cloud OpenFeign: Declarative REST Clients
├── Spring Cloud LoadBalancer: Client-Side Load Balancing
├── Spring Cloud Circuit Breaker: Resilience Patterns
├── Spring Cloud Config: Centralized Configuration
├── Spring Cloud Sleuth: Distributed Tracing
└── Spring Cloud Stream: Message-Driven Microservices
```

#### **Resilience & Fault Tolerance**
```yaml
Primary: Resilience4j
├── Circuit Breaker: Prevent cascading failures
├── Retry: Automatic retry with exponential backoff
├── Rate Limiter: Control request rates
├── Time Limiter: Timeout management
├── Bulkhead: Resource isolation
└── Cache: Response caching

Legacy: Netflix Hystrix
├── Command Pattern: Request wrapping
├── Thread Pool Isolation: Resource separation
├── Dashboard: Real-time monitoring
└── Turbine: Metrics aggregation
```

#### **Load Balancing Strategies**
```yaml
Client-Side Load Balancing:
├── Spring Cloud LoadBalancer (Preferred)
│   ├── Round Robin (Default)
│   ├── Random
│   ├── Weighted Response Time
│   └── Health Check Integration
└── Netflix Ribbon (Legacy)
    ├── Zone-Aware Load Balancing
    ├── Server List Filtering
    └── Ping-Based Health Checks

Server-Side Load Balancing:
├── API Gateway Level
├── Kubernetes Service Mesh
└── External Load Balancers (F5, NGINX)
```

#### **Monitoring & Observability**
```yaml
Application Performance Monitoring:
├── AppDynamics
│   ├── Real-time performance monitoring
│   ├── Business transaction tracking
│   ├── Database performance analysis
│   ├── Infrastructure monitoring
│   └── Alert management
└── Dynatrace
    ├── AI-powered anomaly detection
    ├── Full-stack observability
    ├── Automatic root cause analysis
    ├── Real user monitoring
    └── Cloud infrastructure monitoring

Metrics & Tracing:
├── Micrometer: Metrics collection
├── Prometheus: Metrics storage
├── Grafana: Metrics visualization
├── Zipkin: Distributed tracing
└── Spring Cloud Sleuth: Trace correlation
```

#### **Message Communication**
```yaml
Apache Kafka:
├── High-throughput event streaming
├── Distributed commit log
├── Fault-tolerant messaging
├── Real-time data processing
└── Event sourcing support

Topics & Use Cases:
├── compliance-violations: AML/OFAC violations
├── risk-assessments: Credit/market risk updates
├── audit-events: Regulatory audit trails
├── notifications: User alerts & messages
└── file-processing: Large file processing events
```

### **Interview Talking Points for Microservices**

#### **1. Service Discovery & Registration**
> "We use Netflix Eureka as our service registry where all microservices register themselves upon startup. This eliminates the need for hard-coded service locations and enables dynamic scaling. Services can discover each other using logical names, and Eureka handles instance health checks and automatic de-registration of failed services."

#### **2. Load Balancing Strategy**
> "We implement both client-side and server-side load balancing. Spring Cloud LoadBalancer handles client-side load balancing with various algorithms like round-robin and weighted response time. At the API Gateway level, we use Spring Cloud Gateway for server-side load balancing with health-check integration."

#### **3. Circuit Breaker Implementation**
> "We primarily use Resilience4j for implementing circuit breaker patterns, with Hystrix as legacy support. This prevents cascading failures by monitoring service health and automatically failing fast when downstream services are unavailable. We also implement bulkhead patterns to isolate resources and prevent resource exhaustion."

#### **4. Message-Driven Architecture**
> "For asynchronous communication, we use Apache Kafka for event streaming. This enables loose coupling between services and supports event-driven architecture. For example, when a compliance violation occurs, we publish an event that triggers risk score updates, notifications, and audit logging across multiple services."

#### **5. Monitoring & Observability**
> "We have comprehensive monitoring with AppDynamics and Dynatrace providing real-time application performance monitoring. Spring Cloud Sleuth enables distributed tracing across service calls, and we use Micrometer with Prometheus for custom metrics collection. This gives us full observability into our microservices ecosystem."

---

## Module Architecture

### 1. **automessaging**
```
Purpose: Automated notification and messaging system
├── Regulatory deadline alerts
├── Compliance violation notifications
├── Risk threshold breach alerts
├── Executive reporting notifications
└── Integration with bank's messaging infrastructure
```

### 2. **batchnotification**
```
Purpose: Batch processing for large-scale notifications
├── End-of-day regulatory reports
├── Monthly compliance summaries
├── Quarterly risk assessments
├── Annual audit notifications
└── Bulk customer communications
```

### 3. **domain**
```
Purpose: Core business domain models and entities
├── Risk Models (Credit, Market, Operational)
├── Compliance Entities (Regulations, Policies)
├── Customer/Account Models
├── Transaction Models
└── Regulatory Framework Objects
```

### 4. **EIT_bulk-offline**
```
Purpose: Offline bulk data processing for compliance
├── Large dataset analysis (millions of transactions)
├── Historical compliance data processing
├── Regulatory data warehouse operations
├── Batch risk calculations
└── Data archival and retrieval
```

### 5. **file upload core**
```
Purpose: Secure file handling for regulatory submissions
├── Regulatory filing uploads (XML, CSV, Excel)
├── Document management for compliance
├── Encrypted file processing
├── File validation and format checking
└── Audit trail for file operations
```

### 6. **messaging**
```
Purpose: Real-time messaging and event-driven communication
├── Real-time risk alerts
├── Inter-service communication
├── Event streaming for compliance events
├── Message queuing for reliability
└── Integration with enterprise service bus
```

### 7. **notifications**
```
Purpose: User interface notifications and alerts
├── Dashboard alerts for compliance officers
├── Real-time risk notifications
├── User-specific compliance tasks
├── Mobile push notifications
└── Email/SMS integration
```

### 8. **response file core**
```
Purpose: Managing regulatory response files
├── Regulatory submission responses
├── Compliance audit responses
├── Risk assessment reports
├── File format standardization
└── Response validation and tracking
```

### 9. **response file generation testing**
```
Purpose: Testing framework for response file generation
├── Automated testing of regulatory formats
├── Compliance data validation testing
├── Load testing for bulk operations
├── Integration testing with regulatory systems
└── Performance testing for SLA compliance
```

---

## High-Level Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        FRONTEND LAYER                          │
├─────────────────────────────────────────────────────────────────┤
│  Angular 11/16 SPA                                             │
│  ├── Compliance Dashboard                                      │
│  ├── Risk Monitoring UI                                        │
│  ├── Regulatory Reporting Interface                            │
│  ├── Document Management UI                                    │
│  └── Admin Configuration                                       │
└─────────────────────────────────────────────────────────────────┘
                                  │
                                  │ HTTPS/JWT
                                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API GATEWAY LAYER                         │
├─────────────────────────────────────────────────────────────────┤
│  Spring Cloud Gateway                                          │
│  ├── Okta OAuth2 Authentication                               │
│  ├── JWT Token Validation                                     │
│  ├── Rate Limiting & Throttling                               │
│  ├── Request/Response Logging                                 │
│  ├── Circuit Breaker (Resilience4j)                           │
│  ├── Load Balancing (Spring Cloud LoadBalancer)               │
│  └── Service Discovery Integration (Eureka)                   │
└─────────────────────────────────────────────────────────────────┘
                                  │
                                  │ Load Balanced via Spring Cloud LoadBalancer
                                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                   SERVICE DISCOVERY LAYER                      │
├─────────────────────────────────────────────────────────────────┤
│  Netflix Eureka Server                                         │
│  ├── Service Registration & Discovery                          │
│  ├── Health Check Monitoring                                  │
│  ├── Instance Management                                       │
│  └── Load Balancer Integration                                │
└─────────────────────────────────────────────────────────────────┘
                                  │
                                  │ Service Discovery & Load Balancing
                                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                    MICROSERVICES LAYER                         │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │AutoMessaging│  │BatchNotify  │  │   Domain    │            │
│  │   Service   │  │   Service   │  │   Service   │            │
│  │+ Resilience4j│  │+ Hystrix    │  │+ Eureka     │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │EIT-Bulk     │  │FileUpload   │  │ Messaging   │            │
│  │Offline Svc  │  │Core Service │  │   Service   │            │
│  │+ Circuit    │  │+ Load       │  │+ Kafka      │            │
│  │  Breaker    │  │  Balancer   │  │  Events     │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │Notification │  │ResponseFile │  │ResponseFile │            │
│  │   Service   │  │Core Service │  │Test Service │            │
│  │+ AppDynamics│  │+ Dynatrace  │  │+ Monitoring │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│                                                                 │
│  Inter-Service Communication:                                   │
│  ├── Synchronous: OpenFeign + Ribbon Load Balancer            │
│  ├── Asynchronous: Apache Kafka Event Streaming               │
│  ├── Service Discovery: Netflix Eureka                        │
│  ├── Circuit Breakers: Resilience4j + Hystrix                 │
│  └── Monitoring: AppDynamics + Dynatrace + Micrometer         │
└─────────────────────────────────────────────────────────────────┘
                                  │
                                  │ JDBC/JPA
                                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                      DATABASE LAYER                            │
├─────────────────────────────────────────────────────────────────┤
│  Oracle Database 19c                                           │
│  ├── Compliance Schema                                         │
│  ├── Risk Management Schema                                    │
│  ├── Audit Trail Schema                                        │
│  ├── Configuration Schema                                      │
│  └── Archive Schema                                            │
└─────────────────────────────────────────────────────────────────┘
```

---

## Frontend to Backend Flow

### Step 1: User Authentication Flow
```
Angular Frontend → API Gateway → Okta OAuth2 → JWT Token → Frontend
```

**Detailed Process:**
1. **User Login**: User enters credentials in Angular login component
2. **OAuth2 Redirect**: Frontend redirects to Okta authorization server
3. **Authentication**: Okta validates credentials against AD/LDAP
4. **Authorization Code**: Okta returns authorization code to frontend
5. **Token Exchange**: Frontend exchanges code for JWT access token
6. **Token Storage**: JWT stored securely in browser (httpOnly cookie)
7. **API Calls**: All subsequent API calls include JWT in Authorization header

### Step 2: API Request Flow
```
Angular Service → HTTP Interceptor → API Gateway → Microservice → Database
```

**Example: Compliance Report Generation**
```typescript
// Angular Service
@Injectable()
export class ComplianceService {
  generateReport(reportRequest: ReportRequest): Observable<ReportResponse> {
    return this.http.post<ReportResponse>('/api/compliance/reports', reportRequest);
  }
}

// HTTP Interceptor adds JWT token
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next.handle(authReq);
  }
}
```

### Step 3: Response Handling
```
Database → Microservice → API Gateway → Angular Component → UI Update
```

---

## API Security & Gateway

### Okta OAuth2 Implementation

#### 1. **Authentication Flow**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                    .jwtDecoder(jwtDecoder())
                )
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/compliance/**").hasRole("COMPLIANCE_OFFICER")
                .requestMatchers("/api/risk/**").hasRole("RISK_ANALYST")
                .anyRequest().authenticated()
            )
            .build();
    }
}
```

#### 2. **JWT Token Validation**
```java
@Component
public class JwtTokenValidator {
    
    @Value("${okta.oauth2.issuer}")
    private String issuer;
    
    public boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder().decode(token);
            
            // Validate issuer
            if (!issuer.equals(jwt.getIssuer().toString())) {
                return false;
            }
            
            // Validate expiration
            if (jwt.getExpiresAt().isBefore(Instant.now())) {
                return false;
            }
            
            // Validate audience (Bank of America specific)
            List<String> audience = jwt.getAudience();
            if (!audience.contains("bofa-orcit-eit")) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

#### 3. **Role-Based Access Control**
```java
@RestController
@RequestMapping("/api/compliance")
@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
public class ComplianceController {
    
    @GetMapping("/reports/{reportId}")
    @PreAuthorize("hasPermission(#reportId, 'COMPLIANCE_REPORT', 'READ')")
    public ResponseEntity<ComplianceReport> getReport(@PathVariable String reportId) {
        // Method implementation
    }
    
    @PostMapping("/reports")
    @PreAuthorize("hasRole('SENIOR_COMPLIANCE_OFFICER')")
    public ResponseEntity<String> createReport(@RequestBody ReportRequest request) {
        // Method implementation
    }
}
```

---

## Microservices Communication

### 1. **Service Discovery with Netflix Eureka**
```yaml
# eureka-server/application.yml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:8761/eureka/
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 5000

spring:
  application:
    name: eureka-server
```

```yaml
# microservice application.yml
eureka:
  client:
    service-url:
      defaultZone: http://eureka-server:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
  instance:
    instance-id: ${spring.application.name}:${server.port}
    prefer-ip-address: true
    lease-renewal-interval-in-seconds: 10
    lease-expiration-duration-in-seconds: 30

spring:
  application:
    name: compliance-service
```

### 2. **Load Balancing with Spring Cloud LoadBalancer**
```java
@Configuration
@LoadBalancerClient(name = "risk-service", configuration = RiskServiceLoadBalancerConfig.class)
public class LoadBalancerConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

// Custom Load Balancer Configuration
@Configuration
public class RiskServiceLoadBalancerConfig {
    
    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        
        // Round Robin Load Balancing Strategy
        return new RoundRobinLoadBalancer(
            loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
            name
        );
    }
}
```

### 3. **OpenFeign with Ribbon Load Balancer**
```java
@FeignClient(name = "risk-service", fallback = RiskServiceFallback.class)
public interface RiskServiceClient {
    
    @GetMapping("/api/risk/assessment/{customerId}")
    RiskAssessment getRiskAssessment(@PathVariable("customerId") String customerId);
    
    @PostMapping("/api/risk/calculate")
    RiskCalculationResult calculateRisk(@RequestBody RiskCalculationRequest request);
}

// Ribbon Configuration for Load Balancing
@RibbonClient(name = "risk-service", configuration = RibbonConfig.class)
@Configuration
public class RibbonConfig {
    
    @Bean
    public IRule ribbonRule() {
        // Weighted Response Time Rule for better performance
        return new WeightedResponseTimeRule();
    }
    
    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }
    
    @Bean
    public ServerListSubsetFilter serverListFilter() {
        ServerListSubsetFilter filter = new ServerListSubsetFilter();
        filter.setSize(3); // Limit to 3 servers per request
        return filter;
    }
}

# application.yml for Ribbon configuration
risk-service:
  ribbon:
    NIWSServerListClassName: com.netflix.loadbalancer.ConfigurationBasedServerList
    listOfServers: localhost:8081,localhost:8082,localhost:8083
    ConnectTimeout: 3000
    ReadTimeout: 60000
    MaxAutoRetries: 1
    MaxAutoRetriesNextServer: 1
    OkToRetryOnAllOperations: true
```

### 4. **Circuit Breakers with Resilience4j**
```java
@Service
public class ResilientComplianceService {
    
    @CircuitBreaker(name = "risk-service", fallbackMethod = "fallbackRiskAssessment")
    @Retry(name = "risk-service")
    @TimeLimiter(name = "risk-service")
    @Bulkhead(name = "risk-service")
    public CompletableFuture<RiskAssessment> getRiskAssessment(String customerId) {
        return CompletableFuture.supplyAsync(() -> 
            riskServiceClient.getRiskAssessment(customerId)
        );
    }
    
    public CompletableFuture<RiskAssessment> fallbackRiskAssessment(String customerId, Exception ex) {
        log.warn("Risk service unavailable for customer: {}, using cached data", customerId);
        return CompletableFuture.supplyAsync(() -> 
            riskCacheService.getCachedRiskAssessment(customerId)
        );
    }
}

# application.yml - Resilience4j Configuration
resilience4j:
  circuitbreaker:
    instances:
      risk-service:
        sliding-window-size: 20
        minimum-number-of-calls: 5
        wait-duration-in-open-state: 30s
        failure-rate-threshold: 50
        slow-call-rate-threshold: 50
        slow-call-duration-threshold: 2s
        permitted-number-of-calls-in-half-open-state: 3
        automatic-transition-from-open-to-half-open-enabled: true
        
  retry:
    instances:
      risk-service:
        max-attempts: 3
        wait-duration: 1s
        exponential-backoff-multiplier: 2
        
  bulkhead:
    instances:
      risk-service:
        max-concurrent-calls: 10
        max-wait-duration: 2s
        
  timelimiter:
    instances:
      risk-service:
        timeout-duration: 5s
```

### 5. **Hystrix Circuit Breaker (Legacy Support)**
```java
@Component
public class ComplianceHystrixService {
    
    @HystrixCommand(
        commandKey = "getComplianceReport",
        groupKey = "compliance-service",
        threadPoolKey = "compliance-pool",
        fallbackMethod = "getComplianceReportFallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
        },
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "10"),
            @HystrixProperty(name = "maximumSize", value = "20"),
            @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2")
        }
    )
    public ComplianceReport getComplianceReport(String reportId) {
        return complianceRepository.findById(reportId)
            .orElseThrow(() -> new ComplianceReportNotFoundException(reportId));
    }
    
    public ComplianceReport getComplianceReportFallback(String reportId, Throwable ex) {
        log.warn("Fallback triggered for report: {}, reason: {}", reportId, ex.getMessage());
        return ComplianceReport.builder()
            .reportId(reportId)
            .status(ReportStatus.UNAVAILABLE)
            .message("Report temporarily unavailable, please try again later")
            .build();
    }
}

# Hystrix Dashboard Configuration
@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class HystrixDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}
```

### 6. **Apache Kafka for Asynchronous Communication**
```java
// Kafka Configuration
@Configuration
@EnableKafka
public class KafkaConfig {
    
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-cluster:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(props);
    }
    
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-cluster:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "orcit-compliance-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.bofa.orcit.events");
        return new DefaultKafkaConsumerFactory<>(props);
    }
}

// Event Publisher
@Service
@Slf4j
public class ComplianceEventPublisher {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topics.compliance-violations}")
    private String complianceViolationsTopic;
    
    @Value("${kafka.topics.risk-assessments}")
    private String riskAssessmentsTopic;
    
    public void publishComplianceViolation(ComplianceViolationEvent event) {
        try {
            kafkaTemplate.send(complianceViolationsTopic, event.getCustomerId(), event)
                .addCallback(
                    result -> log.info("Compliance violation event sent: {}", event.getEventId()),
                    failure -> log.error("Failed to send compliance violation event: {}", event.getEventId(), failure)
                );
        } catch (Exception e) {
            log.error("Error publishing compliance violation event", e);
        }
    }
    
    public void publishRiskAssessment(RiskAssessmentEvent event) {
        kafkaTemplate.send(riskAssessmentsTopic, event.getCustomerId(), event);
    }
}

// Event Listener
@Component
@Slf4j
public class ComplianceEventListener {
    
    @Autowired
    private RiskService riskService;
    
    @Autowired
    private NotificationService notificationService;
    
    @KafkaListener(topics = "${kafka.topics.compliance-violations}", 
                   groupId = "risk-assessment-group",
                   containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
        attempts = "3",
        backoff = @Backoff(delay = 1000, multiplier = 2.0),
        dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    public void handleComplianceViolation(ComplianceViolationEvent event) {
        try {
            log.info("Processing compliance violation event: {}", event.getEventId());
            
            // Update risk scores based on violation
            riskService.updateRiskScore(event.getCustomerId(), event.getViolationType());
            
            // Trigger alerts for serious violations
            if (event.getSeverity() == ViolationSeverity.HIGH) {
                notificationService.sendImmediateAlert(event);
            }
            
            log.info("Compliance violation event processed successfully: {}", event.getEventId());
            
        } catch (Exception e) {
            log.error("Error processing compliance violation event: {}", event.getEventId(), e);
            throw e; // Rethrow to trigger retry
        }
    }
}

# Kafka Topics Configuration
kafka:
  topics:
    compliance-violations: "compliance-violations-v1"
    risk-assessments: "risk-assessments-v1"
    audit-events: "audit-events-v1"
    notifications: "notifications-v1"
  consumer:
    group-id: "orcit-compliance-group"
  producer:
    client-id: "orcit-producer"
```

### 7. **API Gateway with Spring Cloud Gateway**
```java
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Compliance Service Routes
            .route("compliance-service", r -> r
                .path("/api/compliance/**")
                .filters(f -> f
                    .circuitBreaker(config -> config
                        .setName("compliance-circuit-breaker")
                        .setFallbackUri("forward:/fallback/compliance"))
                    .retry(config -> config
                        .setRetries(3)
                        .setBackoff(Duration.ofSeconds(1), Duration.ofSeconds(5), 2, true))
                    .requestRateLimiter(config -> config
                        .setRateLimiter(redisRateLimiter())
                        .setKeyResolver(ipKeyResolver())))
                .uri("lb://compliance-service"))
                
            // Risk Service Routes
            .route("risk-service", r -> r
                .path("/api/risk/**")
                .filters(f -> f
                    .circuitBreaker(config -> config
                        .setName("risk-circuit-breaker")
                        .setFallbackUri("forward:/fallback/risk"))
                    .addRequestHeader("X-Service", "risk")
                    .addResponseHeader("X-Response-Time", "#{T(System).currentTimeMillis()}"))
                .uri("lb://risk-service"))
                
            // File Upload Service Routes  
            .route("file-upload-service", r -> r
                .path("/api/files/**")
                .filters(f -> f
                    .circuitBreaker(config -> config
                        .setName("file-upload-circuit-breaker"))
                    .requestSize(100000000L)) // 100MB limit
                .uri("lb://file-upload-service"))
            .build();
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20, 1); // 10 requests per second, burst 20
    }
    
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }
}

# Gateway Application Properties
spring:
  cloud:
    gateway:
      routes:
        - id: compliance-service
          uri: lb://compliance-service
          predicates:
            - Path=/api/compliance/**
          filters:
            - name: CircuitBreaker
              args:
                name: compliance-cb
                fallbackUri: forward:/fallback/compliance
            - name: Retry
              args:
                retries: 3
                statuses: BAD_GATEWAY,SERVICE_UNAVAILABLE
                backoff:
                  firstBackoff: 50ms
                  maxBackoff: 500ms
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
```

---

## Failure Handling & Resilience

### 1. **Advanced Circuit Breaker Implementation**
```java
@Service
@Slf4j
public class AdvancedResilientComplianceService {
    
    // Resilience4j Circuit Breaker with comprehensive configuration
    @CircuitBreaker(name = "risk-service", fallbackMethod = "fallbackRiskAssessment")
    @Retry(name = "risk-service", fallbackMethod = "retryExhaustedFallback")
    @TimeLimiter(name = "risk-service")
    @Bulkhead(name = "risk-service", type = Bulkhead.Type.THREADPOOL)
    @RateLimiter(name = "risk-service")
    public CompletableFuture<RiskAssessment> getRiskAssessment(String customerId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Calling risk service for customer: {}", customerId);
            return riskServiceClient.getRiskAssessment(customerId);
        });
    }
    
    // Primary fallback method
    public CompletableFuture<RiskAssessment> fallbackRiskAssessment(String customerId, Exception ex) {
        log.warn("Circuit breaker activated for risk service, using cache for customer: {}", customerId);
        return CompletableFuture.supplyAsync(() -> 
            riskCacheService.getCachedRiskAssessment(customerId)
        );
    }
    
    // Fallback when retries are exhausted
    public CompletableFuture<RiskAssessment> retryExhaustedFallback(String customerId, Exception ex) {
        log.error("All retries exhausted for customer: {}, returning default assessment", customerId);
        return CompletableFuture.supplyAsync(() -> 
            RiskAssessment.defaultAssessment(customerId)
        );
    }
}

# Enhanced Resilience4j Configuration
resilience4j:
  circuitbreaker:
    instances:
      risk-service:
        sliding-window-type: COUNT_BASED
        sliding-window-size: 20
        minimum-number-of-calls: 10
        wait-duration-in-open-state: PT30S
        failure-rate-threshold: 60
        slow-call-rate-threshold: 50
        slow-call-duration-threshold: PT3S
        permitted-number-of-calls-in-half-open-state: 5
        automatic-transition-from-open-to-half-open-enabled: true
        record-exceptions:
          - java.net.ConnectException
          - java.util.concurrent.TimeoutException
          - org.springframework.web.client.ResourceAccessException
        ignore-exceptions:
          - com.bofa.orcit.exceptions.BusinessValidationException
          
  retry:
    instances:
      risk-service:
        max-attempts: 4
        wait-duration: PT1S
        exponential-backoff-multiplier: 2
        retry-exceptions:
          - java.net.ConnectException
          - java.util.concurrent.TimeoutException
        ignore-exceptions:
          - com.bofa.orcit.exceptions.ValidationException
          
  bulkhead:
    instances:
      risk-service:
        max-concurrent-calls: 15
        max-wait-duration: PT3S
        
  thread-pool-bulkhead:
    instances:
      risk-service:
        core-thread-pool-size: 10
        max-thread-pool-size: 20
        queue-capacity: 50
        keep-alive-duration: PT2M
        
  timelimiter:
    instances:
      risk-service:
        timeout-duration: PT5S
        cancel-running-future: true
        
  ratelimiter:
    instances:
      risk-service:
        limit-for-period: 100
        limit-refresh-period: PT1S
        timeout-duration: PT3S
```

### 2. **Hystrix Dashboard & Monitoring**
```java
@Component
@Slf4j
public class HystrixComplianceService {
    
    @HystrixCommand(
        commandKey = "compliance-report-generation",
        groupKey = "compliance-group",
        threadPoolKey = "compliance-thread-pool",
        fallbackMethod = "generateReportFallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "60000"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10")
        },
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "15"),
            @HystrixProperty(name = "maximumSize", value = "25"),
            @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "3"),
            @HystrixProperty(name = "maxQueueSize", value = "100"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "80")
        }
    )
    public ComplianceReport generateComplianceReport(ReportRequest request) {
        log.info("Generating compliance report for: {}", request.getReportType());
        
        // Simulate complex report generation
        return complianceReportGenerator.generateReport(request);
    }
    
    public ComplianceReport generateReportFallback(ReportRequest request, Throwable ex) {
        log.warn("Report generation fallback triggered for: {}, reason: {}", 
                 request.getReportType(), ex.getMessage());
        
        return ComplianceReport.builder()
            .reportId(UUID.randomUUID().toString())
            .reportType(request.getReportType())
            .status(ReportStatus.QUEUED)
            .message("Report queued for generation when service is available")
            .queuedAt(LocalDateTime.now())
            .build();
    }
}

// Hystrix Dashboard Configuration
@Configuration
@EnableHystrixDashboard
@EnableTurbine
public class HystrixDashboardConfig {
    
    @Bean
    public TurbineConfigurationProperties turbineConfigurationProperties() {
        TurbineConfigurationProperties properties = new TurbineConfigurationProperties();
        properties.setAggregatorClusters("COMPLIANCE-SERVICE,RISK-SERVICE,FILE-UPLOAD-SERVICE");
        properties.setAppConfig("compliance-service,risk-service,file-upload-service");
        properties.setClusterNameExpression("new String('default')");
        properties.setCombineHostPort(true);
        return properties;
    }
}
```

### 3. **Server-Side Load Balancing with Spring Cloud**
```java
@Configuration
@EnableLoadBalancerProxyingConfiguration
public class LoadBalancerConfiguration {
    
    // Custom Load Balancer for Risk Service
    @Bean
    @Primary
    public ReactorServiceInstanceLoadBalancer riskServiceLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RoundRobinLoadBalancer(
            loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
            name
        );
    }
    
    // Weighted Load Balancer for File Upload Service
    @Bean
    public ReactorServiceInstanceLoadBalancer fileUploadLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        
        String name = "file-upload-service";
        return new WeightedRandomLoadBalancer(
            loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
            name
        );
    }
    
    // Health Check Configuration
    @Bean
    public ServiceInstanceListSupplier healthCheckServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder()
            .withDiscoveryClient()
            .withHealthChecks()
            .withCaching(Duration.ofSeconds(30))
            .build(context);
    }
}

# Load Balancer Configuration Properties
spring:
  cloud:
    loadbalancer:
      health-check:
        interval: PT30S
        path:
          risk-service: /actuator/health
          compliance-service: /actuator/health
          file-upload-service: /actuator/health
      cache:
        enabled: true
        ttl: PT30S
        capacity: 256
      retry:
        enabled: true
        max-retries-on-same-service-instance: 1
        max-retries-on-next-service-instance: 2
        retry-on-all-operations: false
```

### 4. **Comprehensive Monitoring with AppDynamics & Dynatrace**
```java
@Configuration
public class MonitoringConfiguration {
    
    // AppDynamics Custom Metrics
    @Bean
    public MeterRegistry appDynamicsMeterRegistry() {
        return new AppDynamicsMeterRegistry(AppDynamicsConfig.DEFAULT, Clock.SYSTEM);
    }
    
    // Custom Metrics for Compliance Operations
    @Component
    @Slf4j
    public class ComplianceMetrics {
        
        private final Counter reportGenerationCounter;
        private final Timer reportGenerationTimer;
        private final Gauge activeReportsGauge;
        private final DistributionSummary reportSizeDistribution;
        
        public ComplianceMetrics(MeterRegistry meterRegistry) {
            this.reportGenerationCounter = Counter.builder("compliance.reports.generated")
                .description("Number of compliance reports generated")
                .tag("service", "compliance")
                .register(meterRegistry);
                
            this.reportGenerationTimer = Timer.builder("compliance.reports.generation.time")
                .description("Time taken to generate compliance reports")
                .tag("service", "compliance")
                .register(meterRegistry);
                
            this.activeReportsGauge = Gauge.builder("compliance.reports.active")
                .description("Number of active report generation processes")
                .tag("service", "compliance")
                .register(meterRegistry, this, ComplianceMetrics::getActiveReportsCount);
                
            this.reportSizeDistribution = DistributionSummary.builder("compliance.reports.size")
                .description("Distribution of compliance report sizes")
                .baseUnit("bytes")
                .register(meterRegistry);
        }
        
        public void recordReportGenerated(String reportType, long sizeInBytes, Duration duration) {
            reportGenerationCounter.increment(
                Tags.of(
                    Tag.of("report_type", reportType),
                    Tag.of("status", "success")
                )
            );
            reportGenerationTimer.record(duration);
            reportSizeDistribution.record(sizeInBytes);
        }
        
        private double getActiveReportsCount() {
            return reportGenerationService.getActiveReportsCount();
        }
    }
}

// Dynatrace Integration
@Configuration
public class DynatraceConfiguration {
    
    @Bean
    @ConditionalOnProperty(value = "dynatrace.enabled", havingValue = "true")
    public DynatraceMeterRegistry dynatraceMeterRegistry() {
        DynatraceConfig config = new DynatraceConfig() {
            @Override
            public String apiToken() {
                return System.getenv("DYNATRACE_API_TOKEN");
            }
            
            @Override
            public String uri() {
                return System.getenv("DYNATRACE_URI");
            }
            
            @Override
            public String get(String key) {
                return null;
            }
        };
        
        return DynatraceMeterRegistry.builder(config)
            .clock(Clock.SYSTEM)
            .build();
    }
    
    // Custom Dynatrace Annotations for Distributed Tracing
    @NewRelic.Trace(dispatcher = true)
    @Component
    public class TracedComplianceService {
        
        @Timed(value = "compliance.risk.assessment", description = "Risk assessment operation")
        @Counted(value = "compliance.risk.assessment.calls", description = "Risk assessment calls")
        public RiskAssessment assessRisk(String customerId) {
            Span span = Tracing.current().tracer().nextSpan()
                .name("risk-assessment")
                .tag("customer.id", customerId)
                .tag("service", "compliance")
                .start();
                
            try (Tracer.SpanInScope ws = Tracing.current().tracer().withSpanInScope(span)) {
                return riskAssessmentService.assess(customerId);
            } finally {
                span.end();
            }
        }
    }
}

# Monitoring Configuration Properties
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus,hystrix.stream,circuitbreakers,retries,bulkheads
  endpoint:
    health:
      show-details: always
      show-components: always
    metrics:
      enabled: true
  metrics:
    export:
      appoptics:
        enabled: true
        api-token: ${APPOPTICS_TOKEN}
      dynatrace:
        enabled: true
        api-token: ${DYNATRACE_TOKEN}
        uri: ${DYNATRACE_URI}
      prometheus:
        enabled: true
    distribution:
      percentiles-histogram:
        http.server.requests: true
        compliance.reports.generation.time: true
      percentiles:
        http.server.requests: 0.5,0.9,0.95,0.99
        compliance.reports.generation.time: 0.5,0.9,0.95,0.99

# AppDynamics Configuration
appoptics:
  api-token: ${APPOPTICS_API_TOKEN}
  enabled: true
  step: PT1M
  batch-size: 10000
  read-timeout: PT10S
  connect-timeout: PT5S

# Dynatrace Configuration  
dynatrace:
  enabled: true
  api-token: ${DYNATRACE_API_TOKEN}
  uri: ${DYNATRACE_URI}
  device-id: orcit-eit-compliance
  group: compliance-services
```

### 2. **Bulkhead Pattern**
```java
@Configuration
public class ThreadPoolConfig {
    
    @Bean("complianceExecutor")
    public Executor complianceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("compliance-");
        executor.initialize();
        return executor;
    }
    
    @Bean("riskExecutor") 
    public Executor riskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("risk-");
        executor.initialize();
        return executor;
    }
}
```

### 3. **Saga Pattern for Distributed Transactions**
```java
@Service
public class ComplianceReportSaga {
    
    public void generateComplianceReport(ReportRequest request) {
        String sagaId = UUID.randomUUID().toString();
        
        try {
            // Step 1: Validate request
            validateReportRequest(request);
            
            // Step 2: Gather compliance data
            ComplianceData data = gatherComplianceData(request);
            
            // Step 3: Generate report
            Report report = generateReport(data);
            
            // Step 4: Store report
            storeReport(report);
            
            // Step 5: Send notifications
            sendReportNotifications(report);
            
        } catch (Exception e) {
            // Compensate in reverse order
            compensateReport(sagaId, e);
        }
    }
    
    private void compensateReport(String sagaId, Exception cause) {
        // Implement compensation logic
        deleteGeneratedFiles(sagaId);
        sendFailureNotifications(sagaId, cause.getMessage());
    }
}
```

---

## CI/CD Pipeline

### 1. **Git Workflow**
```
Feature Branch → Pull Request → Code Review → Merge to Develop → QA Testing → Merge to Master → Production
```

### 2. **Jenkins Pipeline Configuration**
```groovy
pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'artifactory.bofa.com'
        SONAR_HOST = 'sonarqube.bofa.com'
        PCF_API = 'api.pcf.bofa.com'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', 
                    url: 'https://github.bofa.com/risk-compliance/orcit-eit.git'
            }
        }
        
        stage('Build & Test') {
            steps {
                sh '''
                    mvn clean compile
                    mvn test
                    mvn jacoco:report
                '''
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    def image = docker.build("${DOCKER_REGISTRY}/orcit-eit:${BUILD_NUMBER}")
                    docker.withRegistry("https://${DOCKER_REGISTRY}", 'artifactory-creds') {
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }
        
        stage('Security Scan') {
            steps {
                sh '''
                    # Vulnerability scanning with Twistlock/Prisma
                    twistcli images scan ${DOCKER_REGISTRY}/orcit-eit:${BUILD_NUMBER}
                '''
            }
        }
        
        stage('Deploy to QA') {
            steps {
                sh '''
                    cf login -a ${PCF_API} -u ${PCF_USER} -p ${PCF_PASS}
                    cf push orcit-eit-qa -f manifest-qa.yml
                '''
            }
        }
        
        stage('Integration Tests') {
            steps {
                sh '''
                    mvn test -Dtest=IntegrationTest
                '''
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'master'
            }
            steps {
                input message: 'Deploy to Production?', ok: 'Deploy'
                sh '''
                    cf push orcit-eit-prod -f manifest-prod.yml
                '''
            }
        }
    }
    
    post {
        always {
            publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'Coverage Report'
            ])
        }
        failure {
            emailext (
                subject: "Pipeline Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build failed. Check console output at ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}
```

### 3. **Docker Configuration**
```dockerfile
# Dockerfile
FROM openjdk:11-jre-slim

LABEL maintainer="Risk-Compliance-Team <risk-compliance@bofa.com>"
LABEL application="ORCIT-EIT"
LABEL version="1.0"

# Create application user
RUN groupadd -r orcit && useradd -r -g orcit orcit

# Create application directory
WORKDIR /app

# Copy application jar
COPY target/orcit-eit-*.jar app.jar

# Change ownership
RUN chown -R orcit:orcit /app

# Switch to non-root user
USER orcit

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
```

### 4. **Helm Charts for Kubernetes**
```yaml
# values.yaml
replicaCount: 3

image:
  repository: artifactory.bofa.com/orcit-eit
  tag: "latest"
  pullPolicy: Always

service:
  type: LoadBalancer
  port: 80
  targetPort: 8080

ingress:
  enabled: true
  annotations:
    kubernetes.io/ingress.class: nginx
    cert-manager.io/cluster-issuer: letsencrypt-prod
  hosts:
    - host: orcit-eit.bofa.com
      paths:
        - path: /
          pathType: Prefix
  tls:
    - secretName: orcit-eit-tls
      hosts:
        - orcit-eit.bofa.com

resources:
  limits:
    cpu: 1000m
    memory: 2Gi
  requests:
    cpu: 500m
    memory: 1Gi

autoscaling:
  enabled: true
  minReplicas: 3
  maxReplicas: 10
  targetCPUUtilizationPercentage: 70
  targetMemoryUtilizationPercentage: 80

database:
  host: oracle-db.bofa.com
  port: 1521
  serviceName: ORCITDB
  username: orcit_user
  passwordSecret: oracle-db-secret
```

### 5. **PCF Deployment Manifest**
```yaml
# manifest-prod.yml
applications:
- name: orcit-eit-prod
  instances: 3
  memory: 2G
  disk_quota: 1G
  buildpacks:
    - java_buildpack
  path: target/orcit-eit-*.jar
  env:
    SPRING_PROFILES_ACTIVE: production
    JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 11.+ } }'
    JAVA_OPTS: '-XX:MaxMetaspaceSize=512m -Xmx1536m'
  services:
    - orcit-oracle-db
    - orcit-redis-cache
    - orcit-kafka-messaging
  routes:
    - route: orcit-eit-prod.apps.pcf.bofa.com
  health-check-type: http
  health-check-http-endpoint: /actuator/health
  timeout: 180
```

---

## Database Connectivity

### 1. **Oracle Database Configuration**
```yaml
# application-production.yml
spring:
  datasource:
    url: jdbc:oracle:thin:@//oracle-db.bofa.com:1521/ORCITDB
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: oracle.jdbc.OracleDriver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 20000
      leak-detection-threshold: 60000
      
  jpa:
    database-platform: org.hibernate.dialect.Oracle12cDialect
    hibernate:
      ddl-auto: validate
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    properties:
      hibernate:
        format_sql: false
        show_sql: false
        jdbc:
          batch_size: 50
        cache:
          use_second_level_cache: true
          region:
            factory_class: org.hibernate.cache.ehcache.EhCacheRegionFactory
```

### 2. **Data Access Layer**
```java
// Repository Layer
@Repository
public interface ComplianceReportRepository extends JpaRepository<ComplianceReport, String> {
    
    @Query("""
        SELECT cr FROM ComplianceReport cr 
        WHERE cr.reportDate BETWEEN :startDate AND :endDate 
        AND cr.regulationType = :regulationType
        """)
    List<ComplianceReport> findByDateRangeAndType(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("regulationType") RegulationType regulationType
    );
    
    @Modifying
    @Query("UPDATE ComplianceReport cr SET cr.status = :status WHERE cr.reportId = :reportId")
    int updateReportStatus(@Param("reportId") String reportId, @Param("status") ReportStatus status);
}

// Service Layer with Transaction Management
@Service
@Transactional
public class ComplianceReportService {
    
    @Autowired
    private ComplianceReportRepository reportRepository;
    
    @Transactional(readOnly = true)
    public List<ComplianceReport> getReportsByPeriod(LocalDate startDate, LocalDate endDate) {
        return reportRepository.findByDateRangeAndType(startDate, endDate, RegulationType.REGULATORY);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ComplianceReport createReport(ComplianceReportRequest request) {
        ComplianceReport report = new ComplianceReport();
        report.setReportId(generateReportId());
        report.setReportType(request.getReportType());
        report.setStatus(ReportStatus.PENDING);
        report.setCreatedDate(LocalDateTime.now());
        
        return reportRepository.save(report);
    }
}
```

### 3. **Database Entity Models**
```java
@Entity
@Table(name = "COMPLIANCE_REPORTS", schema = "ORCIT_COMPLIANCE")
public class ComplianceReport {
    
    @Id
    @Column(name = "REPORT_ID", length = 50)
    private String reportId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "REPORT_TYPE", nullable = false)
    private ReportType reportType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ReportStatus status;
    
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "REPORT_DATA", columnDefinition = "CLOB")
    @Lob
    private String reportData;
    
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ComplianceReportItem> reportItems = new HashSet<>();
    
    // Constructors, getters, setters
}

@Entity
@Table(name = "RISK_ASSESSMENTS", schema = "ORCIT_RISK")
public class RiskAssessment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "risk_seq")
    @SequenceGenerator(name = "risk_seq", sequenceName = "RISK_ASSESSMENT_SEQ", allocationSize = 1)
    private Long assessmentId;
    
    @Column(name = "CUSTOMER_ID", nullable = false)
    private String customerId;
    
    @Column(name = "RISK_SCORE", precision = 5, scale = 2)
    private BigDecimal riskScore;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "RISK_CATEGORY")
    private RiskCategory riskCategory;
    
    @Column(name = "ASSESSMENT_DATE", nullable = false)
    private LocalDateTime assessmentDate;
    
    // Additional fields and methods
}
```

---

## Interview Talking Points

### As a Senior Developer, Here's How to Present the Architecture:

#### 1. **Start with Business Context**
> "At Bank of America, I work on ORCIT-EIT, which is a critical compliance and risk management platform. We handle regulatory reporting for CCAR, DFAST, and Basel III requirements, processing millions of transactions daily for risk assessment and compliance monitoring."

#### 2. **Explain the Technical Challenge**
> "The main challenges we face are: real-time risk monitoring, handling massive data volumes, ensuring regulatory compliance, maintaining 99.9% uptime, and meeting strict security requirements in a highly regulated environment."

#### 3. **Describe the Architecture Decision**
> "We adopted a microservices architecture using Spring Boot because it allows us to scale individual services based on demand, deploy independently, and maintain service isolation for regulatory compliance. Each service handles a specific business domain like compliance reporting, risk assessment, or notification management."

#### 4. **Highlight Security Implementation**
> "Security is paramount in banking. We use Okta for enterprise SSO with OAuth2/JWT tokens, implement role-based access control, and ensure all inter-service communication is encrypted. Every API call is logged and audited for compliance purposes."

#### 5. **Emphasize Resilience Patterns**
> "Given the critical nature of our systems, we implement circuit breakers, bulkhead patterns, and saga patterns for distributed transactions. For example, when generating compliance reports, if one service fails, we have compensation mechanisms to ensure data consistency."

#### 6. **Discuss DevOps Excellence**
> "We maintain a robust CI/CD pipeline with automated testing, security scanning, and blue-green deployments. Our code goes through multiple environments - dev, QA, UAT, and production - with automated quality gates at each stage."

#### 7. **Mention Performance and Monitoring**
> "We use Kubernetes for orchestration, implement horizontal pod autoscaling based on CPU/memory metrics, and have comprehensive monitoring with Prometheus and Grafana. Our SLAs require 99.9% uptime with response times under 200ms for critical operations."

### Key Technical Strengths to Highlight:

1. **Regulatory Compliance**: Deep understanding of banking regulations
2. **Microservices Expertise**: Service decomposition and inter-service communication
3. **Security Focus**: Enterprise-grade security implementation
4. **Resilience Engineering**: Fault tolerance and disaster recovery
5. **DevOps Culture**: Automated deployment and monitoring
6. **Performance Optimization**: Handling high-volume financial data
7. **Team Leadership**: Mentoring junior developers and architecture decisions

### Sample Questions You Can Answer:

**Q: "How do you handle failures in a distributed system?"**
**A:** "We implement multiple resilience patterns. Circuit breakers prevent cascading failures, bulkhead patterns isolate resources, and we use saga patterns for distributed transactions. For example, in our compliance reporting workflow, if payment validation fails, we automatically compensate by rolling back the report generation and notifying relevant stakeholders."

**Q: "How do you ensure data consistency across microservices?"**
**A:** "We use the saga pattern for distributed transactions and event sourcing for audit trails. Critical operations like compliance report generation follow a choreographed saga where each service publishes events, and compensating actions are triggered if any step fails."

**Q: "How do you handle security in a microservices architecture?"**
**A:** "We implement defense in depth: OAuth2/JWT at the gateway, mTLS for inter-service communication, role-based access control, and comprehensive audit logging. Every service validates tokens and implements proper authorization checks."

---

This comprehensive documentation provides you with a complete technical narrative for your interview. Focus on the business impact, technical depth, and your role in architecting solutions for one of the world's largest financial institutions.