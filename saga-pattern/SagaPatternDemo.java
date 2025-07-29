package com.saga.pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * SAGA DESIGN PATTERN
 * 
 * What is Saga Pattern?
 * The Saga pattern is a design pattern used to manage distributed transactions 
 * across multiple microservices. It ensures data consistency in a distributed 
 * system without using traditional ACID transactions.
 * 
 * Why Use Saga Pattern?
 * 1. Distributed Transaction Management - Handle transactions across multiple services
 * 2. Data Consistency - Maintain consistency without distributed locks
 * 3. Fault Tolerance - Handle failures gracefully with compensation
 * 4. Scalability - Better performance than 2PC (Two-Phase Commit)
 * 5. Resilience - Continue processing even if some services are temporarily down
 * 
 * Types of Saga:
 * 1. Choreography-based Saga - Services communicate through events
 * 2. Orchestration-based Saga - Central coordinator manages the workflow
 */

/**
 * REAL-WORLD E-COMMERCE EXAMPLE
 * 
 * Scenario: Customer places an order
 * Steps:
 * 1. Create Order
 * 2. Process Payment
 * 3. Reserve Inventory
 * 4. Create Shipment
 * 5. Send Notification
 * 
 * If any step fails, we need to compensate (undo) previous steps
 */

// ================================
// DOMAIN MODELS
// ================================

class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    
    public Order(String orderId, String customerId, List<OrderItem> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = calculateTotal(items);
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
    
    private BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Getters and setters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    @Override
    public String toString() {
        return "Order{orderId='" + orderId + "', customerId='" + customerId + 
               "', totalAmount=" + totalAmount + ", status=" + status + "}";
    }
}

class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    
    public OrderItem(String productId, String productName, int quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}

enum OrderStatus {
    PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, FAILED
}

class Payment {
    private String paymentId;
    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private PaymentStatus status;
    
    public Payment(String paymentId, String orderId, String customerId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }
    
    public String getPaymentId() { return paymentId; }
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}

enum PaymentStatus {
    PENDING, PROCESSED, FAILED, REFUNDED
}

class Inventory {
    private String productId;
    private int availableQuantity;
    private int reservedQuantity;
    
    public Inventory(String productId, int availableQuantity) {
        this.productId = productId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = 0;
    }
    
    public String getProductId() { return productId; }
    public int getAvailableQuantity() { return availableQuantity; }
    public int getReservedQuantity() { return reservedQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
    public void setReservedQuantity(int reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}

class Shipment {
    private String shipmentId;
    private String orderId;
    private String address;
    private ShipmentStatus status;
    
    public Shipment(String shipmentId, String orderId, String address) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.address = address;
        this.status = ShipmentStatus.PENDING;
    }
    
    public String getShipmentId() { return shipmentId; }
    public String getOrderId() { return orderId; }
    public String getAddress() { return address; }
    public ShipmentStatus getStatus() { return status; }
    public void setStatus(ShipmentStatus status) { this.status = status; }
}

enum ShipmentStatus {
    PENDING, CREATED, IN_TRANSIT, DELIVERED, CANCELLED
}

// ================================
// SAGA EVENTS
// ================================

abstract class SagaEvent {
    private String sagaId;
    private String eventId;
    private LocalDateTime timestamp;
    
    public SagaEvent(String sagaId, String eventId) {
        this.sagaId = sagaId;
        this.eventId = eventId;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getSagaId() { return sagaId; }
    public String getEventId() { return eventId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

class OrderCreatedEvent extends SagaEvent {
    private Order order;
    
    public OrderCreatedEvent(String sagaId, Order order) {
        super(sagaId, "ORDER_CREATED_" + UUID.randomUUID().toString());
        this.order = order;
    }
    
    public Order getOrder() { return order; }
}

class PaymentProcessedEvent extends SagaEvent {
    private Payment payment;
    
    public PaymentProcessedEvent(String sagaId, Payment payment) {
        super(sagaId, "PAYMENT_PROCESSED_" + UUID.randomUUID().toString());
        this.payment = payment;
    }
    
    public Payment getPayment() { return payment; }
}

class PaymentFailedEvent extends SagaEvent {
    private String orderId;
    private String reason;
    
    public PaymentFailedEvent(String sagaId, String orderId, String reason) {
        super(sagaId, "PAYMENT_FAILED_" + UUID.randomUUID().toString());
        this.orderId = orderId;
        this.reason = reason;
    }
    
    public String getOrderId() { return orderId; }
    public String getReason() { return reason; }
}

class InventoryReservedEvent extends SagaEvent {
    private String orderId;
    private List<OrderItem> items;
    
    public InventoryReservedEvent(String sagaId, String orderId, List<OrderItem> items) {
        super(sagaId, "INVENTORY_RESERVED_" + UUID.randomUUID().toString());
        this.orderId = orderId;
        this.items = items;
    }
    
    public String getOrderId() { return orderId; }
    public List<OrderItem> getItems() { return items; }
}

class InventoryReservationFailedEvent extends SagaEvent {
    private String orderId;
    private String reason;
    
    public InventoryReservationFailedEvent(String sagaId, String orderId, String reason) {
        super(sagaId, "INVENTORY_FAILED_" + UUID.randomUUID().toString());
        this.orderId = orderId;
        this.reason = reason;
    }
    
    public String getOrderId() { return orderId; }
    public String getReason() { return reason; }
}

class ShipmentCreatedEvent extends SagaEvent {
    private Shipment shipment;
    
    public ShipmentCreatedEvent(String sagaId, Shipment shipment) {
        super(sagaId, "SHIPMENT_CREATED_" + UUID.randomUUID().toString());
        this.shipment = shipment;
    }
    
    public Shipment getShipment() { return shipment; }
}

class NotificationSentEvent extends SagaEvent {
    private String orderId;
    private String customerId;
    
    public NotificationSentEvent(String sagaId, String orderId, String customerId) {
        super(sagaId, "NOTIFICATION_SENT_" + UUID.randomUUID().toString());
        this.orderId = orderId;
        this.customerId = customerId;
    }
    
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
}

// ================================
// MICROSERVICES (Business Logic)
// ================================

// Order Service
class OrderService {
    private Map<String, Order> orders = new HashMap<>();
    
    public Order createOrder(String customerId, List<OrderItem> items) {
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        Order order = new Order(orderId, customerId, items);
        orders.put(orderId, order);
        System.out.println("✅ Order created: " + order);
        return order;
    }
    
    public void confirmOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.CONFIRMED);
            System.out.println("✅ Order confirmed: " + orderId);
        }
    }
    
    public void cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            System.out.println("❌ Order cancelled: " + orderId);
        }
    }
    
    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
}

// Payment Service
class PaymentService {
    private Map<String, Payment> payments = new HashMap<>();
    
    public Payment processPayment(String orderId, String customerId, BigDecimal amount) {
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);
        Payment payment = new Payment(paymentId, orderId, customerId, amount);
        
        // Simulate payment processing (could fail)
        if (amount.compareTo(new BigDecimal("1000")) > 0) {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("❌ Payment failed: Amount too high - " + payment.getPaymentId());
            return payment;
        }
        
        payment.setStatus(PaymentStatus.PROCESSED);
        payments.put(paymentId, payment);
        System.out.println("✅ Payment processed: " + payment.getPaymentId() + " for $" + amount);
        return payment;
    }
    
    public void refundPayment(String paymentId) {
        Payment payment = payments.get(paymentId);
        if (payment != null && payment.getStatus() == PaymentStatus.PROCESSED) {
            payment.setStatus(PaymentStatus.REFUNDED);
            System.out.println("💰 Payment refunded: " + paymentId);
        }
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Inventory> inventories = new HashMap<>();
    
    public InventoryService() {
        // Initialize some inventory
        inventories.put("PROD-001", new Inventory("PROD-001", 100));
        inventories.put("PROD-002", new Inventory("PROD-002", 50));
        inventories.put("PROD-003", new Inventory("PROD-003", 5)); // Low stock for testing
    }
    
    public boolean reserveInventory(List<OrderItem> items) {
        // Check if all items are available
        for (OrderItem item : items) {
            Inventory inventory = inventories.get(item.getProductId());
            if (inventory == null || inventory.getAvailableQuantity() < item.getQuantity()) {
                System.out.println("❌ Insufficient inventory for: " + item.getProductName());
                return false;
            }
        }
        
        // Reserve all items
        for (OrderItem item : items) {
            Inventory inventory = inventories.get(item.getProductId());
            inventory.setAvailableQuantity(inventory.getAvailableQuantity() - item.getQuantity());
            inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());
            System.out.println("✅ Reserved " + item.getQuantity() + " units of " + item.getProductName());
        }
        
        return true;
    }
    
    public void releaseReservation(List<OrderItem> items) {
        for (OrderItem item : items) {
            Inventory inventory = inventories.get(item.getProductId());
            if (inventory != null) {
                inventory.setAvailableQuantity(inventory.getAvailableQuantity() + item.getQuantity());
                inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
                System.out.println("🔄 Released reservation for " + item.getQuantity() + " units of " + item.getProductName());
            }
        }
    }
}

// Shipping Service
class ShippingService {
    private Map<String, Shipment> shipments = new HashMap<>();
    
    public Shipment createShipment(String orderId, String address) {
        String shipmentId = "SHIP-" + UUID.randomUUID().toString().substring(0, 8);
        Shipment shipment = new Shipment(shipmentId, orderId, address);
        shipment.setStatus(ShipmentStatus.CREATED);
        shipments.put(shipmentId, shipment);
        System.out.println("📦 Shipment created: " + shipmentId + " for order " + orderId);
        return shipment;
    }
    
    public void cancelShipment(String shipmentId) {
        Shipment shipment = shipments.get(shipmentId);
        if (shipment != null) {
            shipment.setStatus(ShipmentStatus.CANCELLED);
            System.out.println("📦❌ Shipment cancelled: " + shipmentId);
        }
    }
}

// Notification Service
class NotificationService {
    public void sendOrderConfirmation(String customerId, String orderId) {
        System.out.println("📧 Order confirmation sent to customer " + customerId + " for order " + orderId);
    }
    
    public void sendOrderCancellation(String customerId, String orderId, String reason) {
        System.out.println("📧 Order cancellation notification sent to customer " + customerId + 
                          " for order " + orderId + ". Reason: " + reason);
    }
}

// ================================
// ORCHESTRATION-BASED SAGA
// ================================

class OrderSagaOrchestrator {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final ShippingService shippingService;
    private final NotificationService notificationService;
    
    // Saga state tracking
    private Map<String, SagaState> sagaStates = new HashMap<>();
    
    public OrderSagaOrchestrator(OrderService orderService, PaymentService paymentService,
                                InventoryService inventoryService, ShippingService shippingService,
                                NotificationService notificationService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.shippingService = shippingService;
        this.notificationService = notificationService;
    }
    
    public void processOrderSaga(String customerId, List<OrderItem> items, String shippingAddress) {
        String sagaId = "SAGA-" + UUID.randomUUID().toString().substring(0, 8);
        SagaState sagaState = new SagaState(sagaId);
        sagaStates.put(sagaId, sagaState);
        
        System.out.println("\n🚀 Starting Order Saga: " + sagaId);
        System.out.println("==================================================");
        
        try {
            // Step 1: Create Order
            Order order = orderService.createOrder(customerId, items);
            sagaState.setOrder(order);
            sagaState.addCompletedStep("CREATE_ORDER");
            
            // Step 2: Process Payment
            Payment payment = paymentService.processPayment(order.getOrderId(), customerId, order.getTotalAmount());
            if (payment.getStatus() == PaymentStatus.FAILED) {
                throw new SagaException("Payment failed");
            }
            sagaState.setPayment(payment);
            sagaState.addCompletedStep("PROCESS_PAYMENT");
            
            // Step 3: Reserve Inventory
            boolean inventoryReserved = inventoryService.reserveInventory(items);
            if (!inventoryReserved) {
                throw new SagaException("Inventory reservation failed");
            }
            sagaState.addCompletedStep("RESERVE_INVENTORY");
            
            // Step 4: Create Shipment
            Shipment shipment = shippingService.createShipment(order.getOrderId(), shippingAddress);
            sagaState.setShipment(shipment);
            sagaState.addCompletedStep("CREATE_SHIPMENT");
            
            // Step 5: Confirm Order
            orderService.confirmOrder(order.getOrderId());
            sagaState.addCompletedStep("CONFIRM_ORDER");
            
            // Step 6: Send Notification
            notificationService.sendOrderConfirmation(customerId, order.getOrderId());
            sagaState.addCompletedStep("SEND_NOTIFICATION");
            
            sagaState.setStatus(SagaStatus.COMPLETED);
            System.out.println("\n🎉 Saga completed successfully: " + sagaId);
            
        } catch (SagaException e) {
            System.out.println("\n❌ Saga failed: " + e.getMessage());
            compensate(sagaState, e.getMessage());
        }
    }
    
    private void compensate(SagaState sagaState, String reason) {
        System.out.println("\n🔄 Starting compensation for saga: " + sagaState.getSagaId());
        System.out.println("Reason: " + reason);
        System.out.println("==================================================");
        
        List<String> completedSteps = sagaState.getCompletedSteps();
        
        // Compensate in reverse order
        Collections.reverse(completedSteps);
        
        for (String step : completedSteps) {
            switch (step) {
                case "SEND_NOTIFICATION":
                    if (sagaState.getOrder() != null) {
                        notificationService.sendOrderCancellation(
                            sagaState.getOrder().getCustomerId(),
                            sagaState.getOrder().getOrderId(),
                            reason
                        );
                    }
                    break;
                    
                case "CONFIRM_ORDER":
                case "CREATE_ORDER":
                    if (sagaState.getOrder() != null) {
                        orderService.cancelOrder(sagaState.getOrder().getOrderId());
                    }
                    break;
                    
                case "CREATE_SHIPMENT":
                    if (sagaState.getShipment() != null) {
                        shippingService.cancelShipment(sagaState.getShipment().getShipmentId());
                    }
                    break;
                    
                case "RESERVE_INVENTORY":
                    if (sagaState.getOrder() != null) {
                        inventoryService.releaseReservation(sagaState.getOrder().getItems());
                    }
                    break;
                    
                case "PROCESS_PAYMENT":
                    if (sagaState.getPayment() != null) {
                        paymentService.refundPayment(sagaState.getPayment().getPaymentId());
                    }
                    break;
            }
        }
        
        sagaState.setStatus(SagaStatus.COMPENSATED);
        System.out.println("✅ Compensation completed for saga: " + sagaState.getSagaId());
    }
}

// ================================
// SAGA STATE MANAGEMENT
// ================================

class SagaState {
    private String sagaId;
    private SagaStatus status;
    private List<String> completedSteps;
    private Order order;
    private Payment payment;
    private Shipment shipment;
    private LocalDateTime startTime;
    
    public SagaState(String sagaId) {
        this.sagaId = sagaId;
        this.status = SagaStatus.STARTED;
        this.completedSteps = new ArrayList<>();
        this.startTime = LocalDateTime.now();
    }
    
    public void addCompletedStep(String step) {
        completedSteps.add(step);
    }
    
    // Getters and setters
    public String getSagaId() { return sagaId; }
    public SagaStatus getStatus() { return status; }
    public void setStatus(SagaStatus status) { this.status = status; }
    public List<String> getCompletedSteps() { return new ArrayList<>(completedSteps); }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public Shipment getShipment() { return shipment; }
    public void setShipment(Shipment shipment) { this.shipment = shipment; }
    public LocalDateTime getStartTime() { return startTime; }
}

enum SagaStatus {
    STARTED, IN_PROGRESS, COMPLETED, FAILED, COMPENSATED
}

class SagaException extends Exception {
    public SagaException(String message) {
        super(message);
    }
}

// ================================
// CHOREOGRAPHY-BASED SAGA
// ================================

interface EventBus {
    void publish(SagaEvent event);
    void subscribe(String eventType, EventHandler handler);
}

interface EventHandler {
    void handle(SagaEvent event);
}

class SimpleEventBus implements EventBus {
    private Map<String, List<EventHandler>> handlers = new HashMap<>();
    
    @Override
    public void publish(SagaEvent event) {
        String eventType = event.getClass().getSimpleName();
        List<EventHandler> eventHandlers = handlers.getOrDefault(eventType, new ArrayList<>());
        
        System.out.println("📢 Publishing event: " + eventType + " for saga: " + event.getSagaId());
        
        for (EventHandler handler : eventHandlers) {
            try {
                handler.handle(event);
            } catch (Exception e) {
                System.out.println("❌ Error handling event: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void subscribe(String eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }
}

// Choreography-based services
class ChoreographyOrderService {
    private OrderService orderService;
    private EventBus eventBus;
    
    public ChoreographyOrderService(OrderService orderService, EventBus eventBus) {
        this.orderService = orderService;
        this.eventBus = eventBus;
    }
    
    public void createOrder(String sagaId, String customerId, List<OrderItem> items) {
        Order order = orderService.createOrder(customerId, items);
        eventBus.publish(new OrderCreatedEvent(sagaId, order));
    }
}

// ================================
// DEMO AND TESTING
// ================================

public class SagaPatternDemo {
    public static void main(String[] args) {
        System.out.println("=== SAGA PATTERN DEMONSTRATION ===\n");
        
        demonstrateOrchestrationSaga();
        System.out.println("\n" + "=".repeat(80) + "\n");
        demonstrateFailureScenarios();
    }
    
    private static void demonstrateOrchestrationSaga() {
        System.out.println("ORCHESTRATION-BASED SAGA");
        System.out.println("=========================");
        
        // Initialize services
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        InventoryService inventoryService = new InventoryService();
        ShippingService shippingService = new ShippingService();
        NotificationService notificationService = new NotificationService();
        
        // Create saga orchestrator
        OrderSagaOrchestrator orchestrator = new OrderSagaOrchestrator(
            orderService, paymentService, inventoryService, shippingService, notificationService
        );
        
        // Test successful saga
        List<OrderItem> items = Arrays.asList(
            new OrderItem("PROD-001", "Gaming Laptop", 1, new BigDecimal("799.99")),
            new OrderItem("PROD-002", "Wireless Mouse", 2, new BigDecimal("29.99"))
        );
        
        orchestrator.processOrderSaga("CUST-123", items, "123 Main St, Anytown, USA");
    }
    
    private static void demonstrateFailureScenarios() {
        System.out.println("FAILURE SCENARIOS WITH COMPENSATION");
        System.out.println("===================================");
        
        // Initialize services
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        InventoryService inventoryService = new InventoryService();
        ShippingService shippingService = new ShippingService();
        NotificationService notificationService = new NotificationService();
        
        OrderSagaOrchestrator orchestrator = new OrderSagaOrchestrator(
            orderService, paymentService, inventoryService, shippingService, notificationService
        );
        
        System.out.println("\n--- Scenario 1: Payment Failure (High Amount) ---");
        List<OrderItem> expensiveItems = Arrays.asList(
            new OrderItem("PROD-001", "Gaming Laptop", 2, new BigDecimal("799.99")), // Total > $1000
            new OrderItem("PROD-002", "Wireless Mouse", 1, new BigDecimal("29.99"))
        );
        orchestrator.processOrderSaga("CUST-456", expensiveItems, "456 Oak Ave, Another City, USA");
        
        System.out.println("\n--- Scenario 2: Inventory Shortage ---");
        List<OrderItem> outOfStockItems = Arrays.asList(
            new OrderItem("PROD-003", "Limited Edition Item", 10, new BigDecimal("199.99")) // Only 5 in stock
        );
        orchestrator.processOrderSaga("CUST-789", outOfStockItems, "789 Pine St, Somewhere, USA");
    }
}

/*
================================================================================
                    SPRING BOOT IMPLEMENTATION GUIDE
================================================================================

// 1. Add dependencies to pom.xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
</dependencies>

// 2. Create Saga entities with JPA
@Entity
@Table(name = "saga_instances")
public class SagaInstance {
    @Id
    private String sagaId;
    
    @Enumerated(EnumType.STRING)
    private SagaStatus status;
    
    @ElementCollection
    @CollectionTable(name = "saga_steps")
    private List<String> completedSteps;
    
    // ... other fields and methods
}

// 3. Create REST controllers
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderSagaOrchestrator orchestrator;
    
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        try {
            String sagaId = orchestrator.startOrderSaga(request);
            return ResponseEntity.ok(sagaId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order creation failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/{sagaId}/status")
    public ResponseEntity<SagaStatus> getSagaStatus(@PathVariable String sagaId) {
        SagaStatus status = orchestrator.getSagaStatus(sagaId);
        return ResponseEntity.ok(status);
    }
}

// 4. Use Spring Events for Choreography
@Component
public class PaymentEventHandler {
    
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Process payment when order is created
        paymentService.processPayment(event.getOrder());
    }
    
    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        // Compensate when payment fails
        orderService.cancelOrder(event.getOrderId());
    }
}

// 5. Use Kafka for distributed events
@KafkaListener(topics = "order-events")
public void handleOrderEvent(OrderCreatedEvent event) {
    paymentService.processPayment(event.getOrder());
}

@Service
public class EventPublisher {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishEvent(SagaEvent event) {
        kafkaTemplate.send("order-events", event);
    }
}

// 6. Add circuit breaker for resilience
@Service
public class ResilientPaymentService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallbackPayment")
    @Retry(name = "payment-service")
    public Payment processPayment(Order order) {
        // Call external payment service
        return paymentServiceClient.processPayment(order);
    }
    
    public Payment fallbackPayment(Order order, Exception ex) {
        // Return failed payment for saga compensation
        return new Payment(order.getOrderId(), PaymentStatus.FAILED);
    }
}

================================================================================
                               BENEFITS & BEST PRACTICES
================================================================================

BENEFITS:
1. ✅ Data Consistency - Maintains consistency across microservices
2. ✅ Fault Tolerance - Handles failures gracefully with compensation
3. ✅ Scalability - Better performance than distributed transactions
4. ✅ Flexibility - Easy to add new steps or modify workflow
5. ✅ Resilience - Can handle temporary service outages

BEST PRACTICES:
1. 🎯 Keep compensations idempotent - Safe to retry
2. 🎯 Store saga state persistently - Survive service restarts  
3. 🎯 Use timeouts - Don't wait forever for responses
4. 🎯 Monitor saga progress - Track completion and failures
5. 🎯 Design for eventual consistency - Accept temporary inconsistency
6. 🎯 Test compensation flows - Ensure rollbacks work correctly
7. 🎯 Use correlation IDs - Track requests across services

WHEN TO USE SAGA:
✅ Multi-service transactions
✅ Long-running business processes  
✅ When 2PC is too slow/complex
✅ Microservices architecture
✅ Need for fault tolerance

WHEN NOT TO USE SAGA:
❌ Simple single-service operations
❌ When strong consistency is critical
❌ Short-lived transactions
❌ When compensation is impossible
❌ Overly complex compensation logic

*/
