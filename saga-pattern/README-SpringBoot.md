# Saga Pattern with Spring Boot Microservices

This project demonstrates a **complete implementation** of the **Saga Pattern** using **Spring Boot**, **JPA**, **REST APIs**, and **event-driven architecture** for managing **distributed transactions** in microservices.

## 🎯 What is the Saga Pattern?

The **Saga Pattern** is a design pattern for managing **distributed transactions** across multiple microservices. Instead of using traditional ACID transactions (which don't work well across service boundaries), Saga breaks down a business transaction into a sequence of smaller, local transactions.

### Key Characteristics:
- **Each step** is a local transaction in a specific microservice
- **No distributed locks** - each service manages its own data
- **Compensation logic** for handling failures
- **Eventual consistency** instead of strong consistency

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Order Service │    │ Payment Service │    │Inventory Service│
│                 │    │                 │    │                 │
│ 1. Create Order │───▶│ 2. Process Pay  │───▶│ 3. Reserve Items│
│                 │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Shipping Service│    │      Events     │    │   Saga Manager  │
│                 │    │                 │    │                 │
│ 4. Create Ship  │◀───│ Choreography    │───▶│  Orchestration  │
│                 │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🎪 Two Implementation Approaches

### 1. **Orchestration** (Centralized Control)
- **Saga Manager** controls the entire workflow
- **Central coordination** of all steps
- **Easier to monitor** and debug
- **Single point of failure** risk

### 2. **Choreography** (Distributed Control)  
- **Each service** knows what to do next
- **Event-driven** communication
- **More resilient** - no single point of failure
- **Harder to monitor** overall flow

## 🚀 Quick Start

### Prerequisites
- **Java 11+**
- **Maven 3.6+**
- **Spring Boot 2.7+**

### Running the Application

```bash
# Clone the repository
git clone <your-repo-url>
cd saga-pattern-demo

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# The application will start on http://localhost:8080
```

### Access Points
- **REST API**: `http://localhost:8080/api/orders`
- **H2 Console**: `http://localhost:8080/h2-console`
- **Actuator**: `http://localhost:8080/actuator`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## 📋 API Examples

### 1. Create a Successful Order

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-123",
    "items": [
      {
        "productId": "PROD-001",
        "productName": "Gaming Laptop",
        "quantity": 1,
        "price": 799.99
      },
      {
        "productId": "PROD-002",
        "productName": "Wireless Mouse", 
        "quantity": 2,
        "price": 29.99
      }
    ],
    "shippingAddress": "123 Main St, Anytown, USA"
  }'
```

**Response:**
```json
{
  "sagaId": "SAGA-a1b2c3d4",
  "message": "Order saga started successfully"
}
```

### 2. Create an Order That Will Fail (High Amount)

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-456",
    "items": [
      {
        "productId": "PROD-001",
        "productName": "Gaming Laptop",
        "quantity": 2,
        "price": 799.99
      }
    ],
    "shippingAddress": "456 Oak Ave, Another City, USA"
  }'
```

This will trigger **payment failure** and **compensation flow**.

### 3. Create an Order with Insufficient Inventory

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-789",
    "items": [
      {
        "productId": "PROD-003",
        "productName": "Limited Edition Item",
        "quantity": 10,
        "price": 199.99
      }
    ],
    "shippingAddress": "789 Pine St, Somewhere, USA"
  }'
```

This will trigger **inventory reservation failure** and **compensation flow**.

### 4. Check Saga Status

```bash
# Get specific saga status
curl http://localhost:8080/api/orders/saga/SAGA-a1b2c3d4

# Get all sagas
curl http://localhost:8080/api/orders/sagas
```

**Response:**
```json
{
  "sagaId": "SAGA-a1b2c3d4",
  "orderId": "ORD-x1y2z3w4",
  "status": "COMPLETED",
  "completedSteps": [
    "CREATE_ORDER",
    "PROCESS_PAYMENT", 
    "RESERVE_INVENTORY",
    "CREATE_SHIPMENT",
    "CONFIRM_ORDER"
  ],
  "currentStep": null,
  "errorMessage": null,
  "startTime": "2024-01-15T10:30:00",
  "endTime": "2024-01-15T10:30:05"
}
```

## 🔄 Saga Flow Diagram

```
Happy Path (Success):
┌──────────────┐    ┌─────────────┐    ┌──────────────┐    ┌─────────────┐    ┌──────────────┐
│ CREATE_ORDER │───▶│PROCESS_PAY  │───▶│RESERVE_ITEMS │───▶│CREATE_SHIP  │───▶│CONFIRM_ORDER │
│   ✅ Success │    │  ✅ Success │    │  ✅ Success  │    │ ✅ Success  │    │  ✅ Success  │
└──────────────┘    └─────────────┘    └──────────────┘    └─────────────┘    └──────────────┘

Failure Path with Compensation:
┌──────────────┐    ┌─────────────┐    ┌──────────────┐
│ CREATE_ORDER │───▶│PROCESS_PAY  │───▶│RESERVE_ITEMS │
│   ✅ Success │    │  ❌ Failed  │    │   🔄 Skip    │
└──────────────┘    └─────────────┘    └──────────────┘
        │                  │
        ▼                  ▼
┌──────────────┐    ┌─────────────┐
│ CANCEL_ORDER │◀───│ REFUND_PAY  │
│ 🔄 Compensate│    │🔄 Compensate│ 
└──────────────┘    └─────────────┘
```

## 🛠️ Implementation Details

### Core Components

#### 1. **Saga Manager** (`OrderSagaService`)
```java
@Service
@Transactional
public class OrderSagaService {
    // Orchestrates the entire saga workflow
    // Handles event publishing and compensation
}
```

#### 2. **Domain Entities**
- **OrderEntity**: Order information and items
- **SagaInstanceEntity**: Saga state tracking
- **OrderItemEntity**: Individual order items

#### 3. **Spring Events**
- **OrderCreatedEvent**: Triggers payment processing
- **PaymentProcessedEvent**: Triggers inventory reservation
- **InventoryReservedEvent**: Triggers shipment creation
- **Various Failure Events**: Trigger compensation

#### 4. **Service Layer**
- **PaymentService**: Handles payment processing and refunds
- **InventoryService**: Manages inventory reservation and release
- **ShippingService**: Creates shipments
- **NotificationService**: Sends customer notifications

### Event-Driven Flow

```java
@Async
@EventListener
public void handleOrderCreated(OrderCreatedEvent event) {
    // Process payment
    boolean success = paymentService.processPayment(...);
    if (success) {
        eventPublisher.publishEvent(new PaymentProcessedEvent(...));
    } else {
        eventPublisher.publishEvent(new PaymentFailedEvent(...));
    }
}
```

### Compensation Logic

```java
private void compensatePaymentAndOrder(String sagaId, String orderId, String reason) {
    // 1. Refund payment
    paymentService.refundPayment(orderId);
    
    // 2. Cancel order
    OrderEntity order = orderRepository.findById(orderId).orElse(null);
    if (order != null) {
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
    
    // 3. Update saga status
    SagaInstanceEntity saga = sagaRepository.findById(sagaId)
            .orElseThrow(() -> new RuntimeException("Saga not found"));
    saga.markFailed(reason);
    sagaRepository.save(saga);
}
```

## 🌟 Why Use Saga Pattern in Microservices?

### ✅ **Benefits**

1. **Distributed Transaction Management**
   - No need for distributed locks
   - Each service maintains its own data consistency
   - Works across service boundaries

2. **Fault Tolerance**
   - Automatic compensation on failures
   - No partial states left in the system
   - Graceful degradation

3. **Scalability**
   - No global transaction coordinator bottleneck
   - Services can scale independently
   - Async processing improves performance

4. **Business Logic Visibility**
   - Clear representation of business processes
   - Easy to understand and modify workflows
   - Audit trail of all saga executions

### ⚠️ **Considerations**

1. **Eventual Consistency**
   - Data may be temporarily inconsistent
   - Need to design UI/UX accordingly
   - Users might see intermediate states

2. **Complexity**
   - More complex than local transactions
   - Need to handle partial failures
   - Compensation logic must be carefully designed

3. **Monitoring**
   - Need good observability
   - Distributed tracing essential
   - Error handling and alerting important

## 📊 Real-World Examples

### E-commerce Order Processing
```
Order Saga Flow:
1. Create Order → 2. Process Payment → 3. Reserve Inventory → 
4. Schedule Delivery → 5. Send Confirmation

Compensation Flow (if step 3 fails):
1. ✅ Order Created
2. ✅ Payment Processed  
3. ❌ Inventory Failed
4. 🔄 Refund Payment
5. 🔄 Cancel Order
```

### Travel Booking System
```
Booking Saga Flow:
1. Reserve Flight → 2. Book Hotel → 3. Rent Car → 
4. Process Payment → 5. Send Itinerary

Compensation Flow (if payment fails):
1. ✅ Flight Reserved
2. ✅ Hotel Booked
3. ✅ Car Rented
4. ❌ Payment Failed
5. 🔄 Cancel Car Rental
6. 🔄 Cancel Hotel Booking  
7. 🔄 Cancel Flight Reservation
```

### Banking Transfer System
```
Transfer Saga Flow:
1. Validate Accounts → 2. Debit Source → 3. Credit Target → 
4. Update Balances → 5. Send Notifications

Compensation Flow (if credit fails):
1. ✅ Accounts Validated
2. ✅ Source Debited
3. ❌ Target Credit Failed
4. 🔄 Credit Back to Source
5. 🔄 Revert Balance Updates
```

## 🔧 Configuration

### Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sagadb
    username: saga_user
    password: saga_password
    
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
```

### Saga-Specific Configuration
```yaml
saga:
  timeout:
    payment: 30s
    inventory: 15s  
    shipping: 45s
  retry:
    max-attempts: 3
    delay: 2s
  compensation:
    enabled: true
    timeout: 60s
```

## 📈 Monitoring and Observability

### Metrics
- Saga success/failure rates
- Average saga duration
- Step-specific performance metrics
- Compensation frequency

### Health Checks
```bash
# Application health
curl http://localhost:8080/actuator/health

# Detailed saga metrics
curl http://localhost:8080/actuator/metrics/saga.completed

# Prometheus metrics
curl http://localhost:8080/actuator/prometheus
```

### Logging
- Structured logging with correlation IDs
- Saga lifecycle events
- Compensation activities
- Error details with context

## 🧪 Testing

### Unit Tests
```java
@Test
public void shouldCompleteOrderSagaSuccessfully() {
    // Given
    OrderRequest request = createValidOrderRequest();
    
    // When
    String sagaId = sagaService.startOrderSaga(request);
    
    // Then
    SagaResponse saga = sagaService.getSagaStatus(sagaId);
    assertThat(saga.getStatus()).isEqualTo(SagaStatus.COMPLETED);
}
```

### Integration Tests
```java
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class SagaIntegrationTest {
    
    @Test
    @Order(1)
    void shouldProcessSuccessfulOrder() { /* ... */ }
    
    @Test
    @Order(2) 
    void shouldHandlePaymentFailure() { /* ... */ }
    
    @Test
    @Order(3)
    void shouldHandleInventoryFailure() { /* ... */ }
}
```

## 🚀 Production Deployment

### Kubernetes Configuration
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: saga-pattern-demo
spec:
  replicas: 3
  selector:
    matchLabels:
      app: saga-pattern-demo
  template:
    metadata:
      labels:
        app: saga-pattern-demo
    spec:
      containers:
      - name: saga-app
        image: saga-pattern-demo:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "production"
        - name: DB_USERNAME
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: username
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: password
```

### Docker Configuration
```dockerfile
FROM openjdk:11-jre-slim

COPY target/saga-pattern-demo-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📚 Additional Resources

### Books
- "Microservices Patterns" by Chris Richardson
- "Building Microservices" by Sam Newman
- "Distributed Systems Patterns" by Unmesh Joshi

### Articles
- [Saga Pattern Implementation Guide](https://microservices.io/patterns/data/saga.html)
- [Spring Boot Microservices with Saga](https://spring.io/blog/2021/07/12/spring-cloud-dataflow-composed-tasks)

### Tools
- **Axon Framework**: Event sourcing and CQRS
- **Eventuate**: Saga orchestration platform
- **Apache Camel**: Integration patterns

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🎉 Conclusion

The **Saga Pattern** is essential for building **resilient microservices** that need to maintain **data consistency** across service boundaries. This implementation provides:

- ✅ **Complete working example** with Spring Boot
- ✅ **Both orchestration and choreography** approaches  
- ✅ **Comprehensive error handling** and compensation
- ✅ **Production-ready features** (monitoring, logging, testing)
- ✅ **Real-world scenarios** and failure cases

**Start building distributed systems** that can handle failures gracefully and maintain business consistency! 🚀
