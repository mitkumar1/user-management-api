package com.saga.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * SPRING BOOT SAGA PATTERN IMPLEMENTATION
 * 
 * This demonstrates a complete Saga pattern implementation using:
 * - Spring Boot REST APIs
 * - JPA for persistence
 * - Spring Events for choreography
 * - Async processing
 * - Error handling and compensation
 */

@SpringBootApplication
@EnableAsync
public class SagaSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SagaSpringBootApplication.class, args);
    }
}

// ================================
// JPA ENTITIES
// ================================

@Entity
@Table(name = "orders")
class OrderEntity {
    @Id
    private String orderId;
    
    @NotNull
    private String customerId;
    
    @NotNull
    @Positive
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> items = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public OrderEntity() {}
    
    public OrderEntity(String orderId, String customerId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<OrderItemEntity> getItems() { return items; }
    public void setItems(List<OrderItemEntity> items) { this.items = items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

@Entity
@Table(name = "order_items")
class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    
    @NotNull
    private String productId;
    
    @NotNull
    private String productName;
    
    @NotNull
    @Positive
    private Integer quantity;
    
    @NotNull
    @Positive
    private BigDecimal price;
    
    public OrderItemEntity() {}
    
    public OrderItemEntity(OrderEntity order, String productId, String productName, Integer quantity, BigDecimal price) {
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}

@Entity
@Table(name = "saga_instances")
class SagaInstanceEntity {
    @Id
    private String sagaId;
    
    @NotNull
    private String orderId;
    
    @Enumerated(EnumType.STRING)
    private SagaStatus status;
    
    @ElementCollection
    @CollectionTable(name = "saga_steps", joinColumns = @JoinColumn(name = "saga_id"))
    @Column(name = "step_name")
    private List<String> completedSteps = new ArrayList<>();
    
    private String currentStep;
    private String errorMessage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public SagaInstanceEntity() {}
    
    public SagaInstanceEntity(String sagaId, String orderId) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.status = SagaStatus.STARTED;
        this.startTime = LocalDateTime.now();
    }
    
    public void addCompletedStep(String step) {
        if (completedSteps == null) {
            completedSteps = new ArrayList<>();
        }
        completedSteps.add(step);
    }
    
    public void markCompleted() {
        this.status = SagaStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
    }
    
    public void markFailed(String errorMessage) {
        this.status = SagaStatus.FAILED;
        this.errorMessage = errorMessage;
        this.endTime = LocalDateTime.now();
    }
    
    public void markCompensated() {
        this.status = SagaStatus.COMPENSATED;
        this.endTime = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public SagaStatus getStatus() { return status; }
    public void setStatus(SagaStatus status) { this.status = status; }
    public List<String> getCompletedSteps() { return completedSteps; }
    public void setCompletedSteps(List<String> completedSteps) { this.completedSteps = completedSteps; }
    public String getCurrentStep() { return currentStep; }
    public void setCurrentStep(String currentStep) { this.currentStep = currentStep; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
}

// Enums
enum OrderStatus {
    PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, FAILED
}

enum SagaStatus {
    STARTED, IN_PROGRESS, COMPLETED, FAILED, COMPENSATED
}

// ================================
// REPOSITORIES
// ================================

@Repository
interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByCustomerId(String customerId);
    
    @Query("SELECT o FROM OrderEntity o WHERE o.status = :status")
    List<OrderEntity> findByStatus(OrderStatus status);
}

@Repository
interface SagaInstanceRepository extends JpaRepository<SagaInstanceEntity, String> {
    List<SagaInstanceEntity> findByStatus(SagaStatus status);
    
    @Query("SELECT s FROM SagaInstanceEntity s WHERE s.status IN :statuses")
    List<SagaInstanceEntity> findByStatusIn(List<SagaStatus> statuses);
}

// ================================
// DTOs
// ================================

class OrderRequest {
    @NotNull
    private String customerId;
    
    @NotNull
    private List<OrderItemRequest> items;
    
    private String shippingAddress;
    
    public OrderRequest() {}
    
    public OrderRequest(String customerId, List<OrderItemRequest> items, String shippingAddress) {
        this.customerId = customerId;
        this.items = items;
        this.shippingAddress = shippingAddress;
    }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}

class OrderItemRequest {
    @NotNull
    private String productId;
    
    @NotNull
    private String productName;
    
    @NotNull
    @Positive
    private Integer quantity;
    
    @NotNull
    @Positive
    private BigDecimal price;
    
    public OrderItemRequest() {}
    
    public OrderItemRequest(String productId, String productName, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}

class SagaResponse {
    private String sagaId;
    private String orderId;
    private SagaStatus status;
    private List<String> completedSteps;
    private String currentStep;
    private String errorMessage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public SagaResponse() {}
    
    public SagaResponse(SagaInstanceEntity sagaInstance) {
        this.sagaId = sagaInstance.getSagaId();
        this.orderId = sagaInstance.getOrderId();
        this.status = sagaInstance.getStatus();
        this.completedSteps = sagaInstance.getCompletedSteps();
        this.currentStep = sagaInstance.getCurrentStep();
        this.errorMessage = sagaInstance.getErrorMessage();
        this.startTime = sagaInstance.getStartTime();
        this.endTime = sagaInstance.getEndTime();
    }
    
    // Getters and setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public SagaStatus getStatus() { return status; }
    public void setStatus(SagaStatus status) { this.status = status; }
    public List<String> getCompletedSteps() { return completedSteps; }
    public void setCompletedSteps(List<String> completedSteps) { this.completedSteps = completedSteps; }
    public String getCurrentStep() { return currentStep; }
    public void setCurrentStep(String currentStep) { this.currentStep = currentStep; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
}

// ================================
// SPRING EVENTS
// ================================

abstract class SagaEvent {
    private final String sagaId;
    private final String orderId;
    private final LocalDateTime timestamp;
    
    public SagaEvent(String sagaId, String orderId) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getSagaId() { return sagaId; }
    public String getOrderId() { return orderId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

class OrderCreatedEvent extends SagaEvent {
    private final OrderEntity order;
    
    public OrderCreatedEvent(String sagaId, OrderEntity order) {
        super(sagaId, order.getOrderId());
        this.order = order;
    }
    
    public OrderEntity getOrder() { return order; }
}

class PaymentProcessedEvent extends SagaEvent {
    public PaymentProcessedEvent(String sagaId, String orderId) {
        super(sagaId, orderId);
    }
}

class PaymentFailedEvent extends SagaEvent {
    private final String reason;
    
    public PaymentFailedEvent(String sagaId, String orderId, String reason) {
        super(sagaId, orderId);
        this.reason = reason;
    }
    
    public String getReason() { return reason; }
}

class InventoryReservedEvent extends SagaEvent {
    public InventoryReservedEvent(String sagaId, String orderId) {
        super(sagaId, orderId);
    }
}

class InventoryReservationFailedEvent extends SagaEvent {
    private final String reason;
    
    public InventoryReservationFailedEvent(String sagaId, String orderId, String reason) {
        super(sagaId, orderId);
        this.reason = reason;
    }
    
    public String getReason() { return reason; }
}

class ShipmentCreatedEvent extends SagaEvent {
    public ShipmentCreatedEvent(String sagaId, String orderId) {
        super(sagaId, orderId);
    }
}

class SagaCompletedEvent extends SagaEvent {
    public SagaCompletedEvent(String sagaId, String orderId) {
        super(sagaId, orderId);
    }
}

class SagaFailedEvent extends SagaEvent {
    private final String reason;
    
    public SagaFailedEvent(String sagaId, String orderId, String reason) {
        super(sagaId, orderId);
        this.reason = reason;
    }
    
    public String getReason() { return reason; }
}

// ================================
// SERVICES
// ================================

@Service
@Transactional
class OrderSagaService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private SagaInstanceRepository sagaRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private ShippingService shippingService;
    
    @Autowired
    private NotificationService notificationService;
    
    public String startOrderSaga(OrderRequest request) {
        // Generate IDs
        String sagaId = "SAGA-" + UUID.randomUUID().toString().substring(0, 8);
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        
        // Create saga instance
        SagaInstanceEntity sagaInstance = new SagaInstanceEntity(sagaId, orderId);
        sagaInstance.setCurrentStep("CREATE_ORDER");
        sagaRepository.save(sagaInstance);
        
        // Calculate total amount
        BigDecimal totalAmount = request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Create order
        OrderEntity order = new OrderEntity(orderId, request.getCustomerId(), totalAmount);
        
        // Add order items
        for (OrderItemRequest itemRequest : request.getItems()) {
            OrderItemEntity item = new OrderItemEntity(
                order, 
                itemRequest.getProductId(), 
                itemRequest.getProductName(),
                itemRequest.getQuantity(), 
                itemRequest.getPrice()
            );
            order.getItems().add(item);
        }
        
        orderRepository.save(order);
        
        // Update saga
        sagaInstance.addCompletedStep("CREATE_ORDER");
        sagaInstance.setCurrentStep("PROCESS_PAYMENT");
        sagaRepository.save(sagaInstance);
        
        // Publish event to start the saga flow
        eventPublisher.publishEvent(new OrderCreatedEvent(sagaId, order));
        
        return sagaId;
    }
    
    public SagaResponse getSagaStatus(String sagaId) {
        Optional<SagaInstanceEntity> sagaOpt = sagaRepository.findById(sagaId);
        if (sagaOpt.isPresent()) {
            return new SagaResponse(sagaOpt.get());
        }
        throw new RuntimeException("Saga not found: " + sagaId);
    }
    
    public List<SagaResponse> getAllSagas() {
        return sagaRepository.findAll().stream()
                .map(SagaResponse::new)
                .toList();
    }
    
    @Async
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        try {
            // Process payment
            boolean paymentSuccess = paymentService.processPayment(
                event.getOrderId(), 
                event.getOrder().getCustomerId(), 
                event.getOrder().getTotalAmount()
            );
            
            if (paymentSuccess) {
                updateSagaStep(event.getSagaId(), "PROCESS_PAYMENT", "RESERVE_INVENTORY");
                eventPublisher.publishEvent(new PaymentProcessedEvent(event.getSagaId(), event.getOrderId()));
            } else {
                eventPublisher.publishEvent(new PaymentFailedEvent(
                    event.getSagaId(), 
                    event.getOrderId(), 
                    "Payment processing failed"
                ));
            }
        } catch (Exception e) {
            eventPublisher.publishEvent(new PaymentFailedEvent(
                event.getSagaId(), 
                event.getOrderId(), 
                "Payment service error: " + e.getMessage()
            ));
        }
    }
    
    @Async
    @EventListener
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        try {
            OrderEntity order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            
            // Reserve inventory
            boolean inventorySuccess = inventoryService.reserveInventory(order.getItems());
            
            if (inventorySuccess) {
                updateSagaStep(event.getSagaId(), "RESERVE_INVENTORY", "CREATE_SHIPMENT");
                eventPublisher.publishEvent(new InventoryReservedEvent(event.getSagaId(), event.getOrderId()));
            } else {
                eventPublisher.publishEvent(new InventoryReservationFailedEvent(
                    event.getSagaId(), 
                    event.getOrderId(), 
                    "Insufficient inventory"
                ));
            }
        } catch (Exception e) {
            eventPublisher.publishEvent(new InventoryReservationFailedEvent(
                event.getSagaId(), 
                event.getOrderId(), 
                "Inventory service error: " + e.getMessage()
            ));
        }
    }
    
    @Async
    @EventListener
    public void handleInventoryReserved(InventoryReservedEvent event) {
        try {
            // Create shipment
            boolean shipmentSuccess = shippingService.createShipment(event.getOrderId(), "Default Address");
            
            if (shipmentSuccess) {
                updateSagaStep(event.getSagaId(), "CREATE_SHIPMENT", "CONFIRM_ORDER");
                
                // Confirm order
                OrderEntity order = orderRepository.findById(event.getOrderId())
                        .orElseThrow(() -> new RuntimeException("Order not found"));
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);
                
                // Complete saga
                completeSaga(event.getSagaId());
                
                // Send notification
                notificationService.sendOrderConfirmation(order.getCustomerId(), event.getOrderId());
                
                eventPublisher.publishEvent(new SagaCompletedEvent(event.getSagaId(), event.getOrderId()));
            } else {
                throw new RuntimeException("Shipment creation failed");
            }
        } catch (Exception e) {
            eventPublisher.publishEvent(new SagaFailedEvent(
                event.getSagaId(), 
                event.getOrderId(), 
                "Shipment service error: " + e.getMessage()
            ));
        }
    }
    
    @Async
    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        compensateOrderCreation(event.getSagaId(), event.getOrderId(), event.getReason());
    }
    
    @Async
    @EventListener
    public void handleInventoryReservationFailed(InventoryReservationFailedEvent event) {
        compensatePaymentAndOrder(event.getSagaId(), event.getOrderId(), event.getReason());
    }
    
    @Async
    @EventListener
    public void handleSagaFailed(SagaFailedEvent event) {
        compensateAll(event.getSagaId(), event.getOrderId(), event.getReason());
    }
    
    private void updateSagaStep(String sagaId, String completedStep, String nextStep) {
        SagaInstanceEntity saga = sagaRepository.findById(sagaId)
                .orElseThrow(() -> new RuntimeException("Saga not found"));
        saga.addCompletedStep(completedStep);
        saga.setCurrentStep(nextStep);
        saga.setStatus(SagaStatus.IN_PROGRESS);
        sagaRepository.save(saga);
    }
    
    private void completeSaga(String sagaId) {
        SagaInstanceEntity saga = sagaRepository.findById(sagaId)
                .orElseThrow(() -> new RuntimeException("Saga not found"));
        saga.addCompletedStep("CONFIRM_ORDER");
        saga.markCompleted();
        sagaRepository.save(saga);
    }
    
    private void compensateOrderCreation(String sagaId, String orderId, String reason) {
        // Cancel order
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
        
        // Mark saga as compensated
        SagaInstanceEntity saga = sagaRepository.findById(sagaId)
                .orElseThrow(() -> new RuntimeException("Saga not found"));
        saga.markFailed(reason);
        sagaRepository.save(saga);
    }
    
    private void compensatePaymentAndOrder(String sagaId, String orderId, String reason) {
        // Refund payment
        paymentService.refundPayment(orderId);
        
        // Cancel order
        compensateOrderCreation(sagaId, orderId, reason);
    }
    
    private void compensateAll(String sagaId, String orderId, String reason) {
        // Release inventory
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            inventoryService.releaseReservation(order.getItems());
        }
        
        // Refund payment and cancel order
        compensatePaymentAndOrder(sagaId, orderId, reason);
    }
}

@Service
class PaymentService {
    
    public boolean processPayment(String orderId, String customerId, BigDecimal amount) {
        // Simulate payment processing
        System.out.println("💳 Processing payment for order " + orderId + ", amount: $" + amount);
        
        // Simulate failure for high amounts
        if (amount.compareTo(new BigDecimal("1000")) > 0) {
            System.out.println("❌ Payment failed: Amount too high");
            return false;
        }
        
        // Simulate processing delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Payment processed successfully");
        return true;
    }
    
    public void refundPayment(String orderId) {
        System.out.println("💰 Refunding payment for order " + orderId);
    }
}

@Service
class InventoryService {
    
    // Simulate inventory database
    private Map<String, Integer> inventory = new HashMap<String, Integer>() {{
        put("PROD-001", 100);
        put("PROD-002", 50);
        put("PROD-003", 5); // Low stock for testing
    }};
    
    public boolean reserveInventory(List<OrderItemEntity> items) {
        System.out.println("📦 Reserving inventory...");
        
        // Check availability first
        for (OrderItemEntity item : items) {
            Integer available = inventory.getOrDefault(item.getProductId(), 0);
            if (available < item.getQuantity()) {
                System.out.println("❌ Insufficient inventory for " + item.getProductName());
                return false;
            }
        }
        
        // Reserve items
        for (OrderItemEntity item : items) {
            Integer available = inventory.get(item.getProductId());
            inventory.put(item.getProductId(), available - item.getQuantity());
            System.out.println("✅ Reserved " + item.getQuantity() + " units of " + item.getProductName());
        }
        
        return true;
    }
    
    public void releaseReservation(List<OrderItemEntity> items) {
        System.out.println("🔄 Releasing inventory reservation...");
        
        for (OrderItemEntity item : items) {
            Integer current = inventory.getOrDefault(item.getProductId(), 0);
            inventory.put(item.getProductId(), current + item.getQuantity());
            System.out.println("✅ Released " + item.getQuantity() + " units of " + item.getProductName());
        }
    }
}

@Service
class ShippingService {
    
    public boolean createShipment(String orderId, String address) {
        System.out.println("🚚 Creating shipment for order " + orderId);
        
        // Simulate shipment creation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ Shipment created successfully");
        return true;
    }
}

@Service
class NotificationService {
    
    public void sendOrderConfirmation(String customerId, String orderId) {
        System.out.println("📧 Sending order confirmation to customer " + customerId + " for order " + orderId);
    }
    
    public void sendOrderCancellation(String customerId, String orderId, String reason) {
        System.out.println("📧 Sending order cancellation to customer " + customerId + 
                          " for order " + orderId + ". Reason: " + reason);
    }
}

// ================================
// REST CONTROLLERS
// ================================

@RestController
@RequestMapping("/api/orders")
class OrderController {
    
    @Autowired
    private OrderSagaService sagaService;
    
    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequest request) {
        try {
            String sagaId = sagaService.startOrderSaga(request);
            Map<String, String> response = new HashMap<>();
            response.put("sagaId", sagaId);
            response.put("message", "Order saga started successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Order creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    @GetMapping("/saga/{sagaId}")
    public ResponseEntity<SagaResponse> getSagaStatus(@PathVariable String sagaId) {
        try {
            SagaResponse saga = sagaService.getSagaStatus(sagaId);
            return ResponseEntity.ok(saga);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/sagas")
    public ResponseEntity<List<SagaResponse>> getAllSagas() {
        List<SagaResponse> sagas = sagaService.getAllSagas();
        return ResponseEntity.ok(sagas);
    }
}

// ================================
// CONFIGURATION
// ================================

@Configuration
class SagaConfiguration {
    
    @Bean
    public ApplicationEventPublisher applicationEventPublisher() {
        return new ApplicationEventPublisher() {
            @Override
            public void publishEvent(Object event) {
                // Spring will automatically inject the real ApplicationEventPublisher
            }
        };
    }
}

/*
================================================================================
                           APPLICATION PROPERTIES
================================================================================

# application.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    
  h2:
    console:
      enabled: true
      
server:
  port: 8080

logging:
  level:
    com.saga: DEBUG
    org.springframework.context.event: DEBUG

================================================================================
                              TESTING ENDPOINTS
================================================================================

# 1. Create a successful order
POST http://localhost:8080/api/orders
Content-Type: application/json

{
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
}

# 2. Create an order that will fail (high amount)
POST http://localhost:8080/api/orders
Content-Type: application/json

{
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
}

# 3. Create an order with insufficient inventory
POST http://localhost:8080/api/orders
Content-Type: application/json

{
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
}

# 4. Get saga status
GET http://localhost:8080/api/orders/saga/{sagaId}

# 5. Get all sagas
GET http://localhost:8080/api/orders/sagas

================================================================================
                               MONITORING & OBSERVABILITY
================================================================================

# Add dependencies for monitoring
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

# Actuator endpoints in application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

# Custom metrics for saga monitoring
@Component
public class SagaMetrics {
    private final MeterRegistry meterRegistry;
    private final Counter sagaStartedCounter;
    private final Counter sagaCompletedCounter;
    private final Counter sagaFailedCounter;
    
    public SagaMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.sagaStartedCounter = Counter.builder("saga.started").register(meterRegistry);
        this.sagaCompletedCounter = Counter.builder("saga.completed").register(meterRegistry);
        this.sagaFailedCounter = Counter.builder("saga.failed").register(meterRegistry);
    }
    
    @EventListener
    public void handleSagaStarted(OrderCreatedEvent event) {
        sagaStartedCounter.increment();
    }
    
    @EventListener  
    public void handleSagaCompleted(SagaCompletedEvent event) {
        sagaCompletedCounter.increment();
    }
    
    @EventListener
    public void handleSagaFailed(SagaFailedEvent event) {
        sagaFailedCounter.increment(Tags.of("reason", event.getReason()));
    }
}

*/
