package com.solid.principles;

/**
 * SOLID Design Principles with Real-Life Examples
 * 
 * SOLID is an acronym for five design principles:
 * S - Single Responsibility Principle (SRP)
 * O - Open/Closed Principle (OCP)
 * L - Liskov Substitution Principle (LSP)
 * I - Interface Segregation Principle (ISP)
 * D - Dependency Inversion Principle (DIP)
 */

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 1. SINGLE RESPONSIBILITY PRINCIPLE (SRP)
 * "A class should have only one reason to change"
 * 
 * Real-life analogy: A chef only cooks, a waiter only serves, a cashier only handles payments
 */

// ❌ VIOLATION - This class has multiple responsibilities
class BadUser {
    private String name;
    private String email;
    
    // User data management
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    
    // Email validation (should be separate)
    public boolean isValidEmail(String email) {
        return email.contains("@");
    }
    
    // Database operations (should be separate)
    public void saveToDatabase() {
        System.out.println("Saving user to database...");
    }
    
    // Email sending (should be separate)
    public void sendWelcomeEmail() {
        System.out.println("Sending welcome email...");
    }
}

// ✅ CORRECT - Each class has single responsibility
class User {
    private String name;
    private String email;
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Only responsible for user data
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}

class EmailValidator {
    // Only responsible for email validation
    public boolean isValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}

class UserRepository {
    // Only responsible for database operations
    public void save(User user) {
        System.out.println("Saving user: " + user.getName() + " to database");
    }
    
    public User findByEmail(String email) {
        System.out.println("Finding user by email: " + email);
        return new User("John", email);
    }
}

class EmailService {
    // Only responsible for sending emails
    public void sendWelcomeEmail(User user) {
        System.out.println("Sending welcome email to: " + user.getEmail());
    }
    
    public void sendPasswordResetEmail(User user) {
        System.out.println("Sending password reset email to: " + user.getEmail());
    }
}

/**
 * 2. OPEN/CLOSED PRINCIPLE (OCP)
 * "Classes should be open for extension but closed for modification"
 * 
 * Real-life analogy: A smartphone - you can add apps (extend) without modifying the phone's hardware
 */

// ❌ VIOLATION - Need to modify this class for each new shape
class BadAreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            return rect.getWidth() * rect.getHeight();
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        }
        // Need to add more if-else for new shapes
        return 0;
    }
}

// ✅ CORRECT - Open for extension, closed for modification
interface Shape {
    double calculateArea();
}

class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}

class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    public double getRadius() { return radius; }
}

// Easy to extend without modifying existing code
class Triangle implements Shape {
    private double base;
    private double height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea(); // No modification needed for new shapes
    }
    
    public double calculateTotalArea(List<Shape> shapes) {
        return shapes.stream()
                .mapToDouble(Shape::calculateArea)
                .sum();
    }
}

/**
 * 3. LISKOV SUBSTITUTION PRINCIPLE (LSP)
 * "Subtypes must be substitutable for their base types"
 * 
 * Real-life analogy: Any car should be able to use any parking spot designed for cars
 */

// ❌ VIOLATION - Penguin can't fly, violates LSP
abstract class BadBird {
    public abstract void fly();
}

class BadEagle extends BadBird {
    @Override
    public void fly() {
        System.out.println("Eagle flying high!");
    }
}

class BadPenguin extends BadBird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// ✅ CORRECT - Proper hierarchy respecting LSP
abstract class Bird {
    public abstract void eat();
    public abstract void makeSound();
}

interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Eagle extends Bird implements Flyable {
    @Override
    public void eat() {
        System.out.println("Eagle eating fish");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Eagle: Screech!");
    }
    
    @Override
    public void fly() {
        System.out.println("Eagle flying high!");
    }
}

class Penguin extends Bird implements Swimmable {
    @Override
    public void eat() {
        System.out.println("Penguin eating fish");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Penguin: Squawk!");
    }
    
    @Override
    public void swim() {
        System.out.println("Penguin swimming gracefully!");
    }
}

class Duck extends Bird implements Flyable, Swimmable {
    @Override
    public void eat() {
        System.out.println("Duck eating seeds");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Duck: Quack!");
    }
    
    @Override
    public void fly() {
        System.out.println("Duck flying low!");
    }
    
    @Override
    public void swim() {
        System.out.println("Duck swimming on pond!");
    }
}

/**
 * 4. INTERFACE SEGREGATION PRINCIPLE (ISP)
 * "Clients should not be forced to depend on interfaces they don't use"
 * 
 * Real-life analogy: A printer shouldn't be forced to implement fax functionality if it only prints
 */

// ❌ VIOLATION - Fat interface forces unnecessary implementations
interface BadPrinter {
    void print();
    void scan();
    void fax();
    void copy();
}

class BadSimplePrinter implements BadPrinter {
    @Override
    public void print() {
        System.out.println("Printing document");
    }
    
    @Override
    public void scan() {
        throw new UnsupportedOperationException("This printer can't scan");
    }
    
    @Override
    public void fax() {
        throw new UnsupportedOperationException("This printer can't fax");
    }
    
    @Override
    public void copy() {
        throw new UnsupportedOperationException("This printer can't copy");
    }
}

// ✅ CORRECT - Segregated interfaces
interface Printable {
    void print();
}

interface Scannable {
    void scan();
}

interface Faxable {
    void fax();
}

interface Copyable {
    void copy();
}

class SimplePrinter implements Printable {
    @Override
    public void print() {
        System.out.println("Simple printer: Printing document");
    }
}

class MultiFunctionPrinter implements Printable, Scannable, Faxable, Copyable {
    @Override
    public void print() {
        System.out.println("Multi-function printer: Printing document");
    }
    
    @Override
    public void scan() {
        System.out.println("Multi-function printer: Scanning document");
    }
    
    @Override
    public void fax() {
        System.out.println("Multi-function printer: Faxing document");
    }
    
    @Override
    public void copy() {
        System.out.println("Multi-function printer: Copying document");
    }
}

class Scanner implements Scannable {
    @Override
    public void scan() {
        System.out.println("Scanner: Scanning document");
    }
}

/**
 * 5. DEPENDENCY INVERSION PRINCIPLE (DIP)
 * "High-level modules should not depend on low-level modules. Both should depend on abstractions"
 * 
 * Real-life analogy: A wall switch doesn't care what type of bulb is connected - LED, incandescent, etc.
 */

// ❌ VIOLATION - High-level class depends on low-level implementation
class BadEmailService {
    public void sendEmail(String message) {
        System.out.println("Sending via Gmail: " + message);
    }
}

class BadNotificationService {
    private BadEmailService emailService; // Tightly coupled to concrete implementation
    
    public BadNotificationService() {
        this.emailService = new BadEmailService(); // Hard dependency
    }
    
    public void sendNotification(String message) {
        emailService.sendEmail(message);
    }
}

// ✅ CORRECT - Depend on abstractions
interface MessageSender {
    void sendMessage(String message, String recipient);
}

interface NotificationRepository {
    void saveNotification(String message, String recipient);
}

class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("Email sent to " + recipient + ": " + message);
    }
}

class SMSSender implements MessageSender {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("SMS sent to " + recipient + ": " + message);
    }
}

class PushNotificationSender implements MessageSender {
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("Push notification sent to " + recipient + ": " + message);
    }
}

class DatabaseNotificationRepository implements NotificationRepository {
    @Override
    public void saveNotification(String message, String recipient) {
        System.out.println("Notification saved to database: " + message + " for " + recipient);
    }
}

class NotificationService {
    private final MessageSender messageSender;
    private final NotificationRepository repository;
    
    // Dependency injection through constructor
    public NotificationService(MessageSender messageSender, NotificationRepository repository) {
        this.messageSender = messageSender;
        this.repository = repository;
    }
    
    public void sendNotification(String message, String recipient) {
        messageSender.sendMessage(message, recipient);
        repository.saveNotification(message, recipient);
    }
}

/**
 * REAL-WORLD E-COMMERCE EXAMPLE - Applying All SOLID Principles
 */

// Product entity (SRP - only manages product data)
class Product {
    private String id;
    private String name;
    private BigDecimal price;
    private int stockQuantity;
    
    public Product(String id, String name, BigDecimal price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}

// Order entity (SRP)
class Order {
    private String id;
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    private String customerId;
    
    public Order(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
    }
    
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Getters
    public String getId() { return id; }
    public List<OrderItem> getItems() { return items; }
    public String getCustomerId() { return customerId; }
    public LocalDateTime getOrderDate() { return orderDate; }
}

class OrderItem {
    private Product product;
    private int quantity;
    private BigDecimal price;
    
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }
    
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}

// OCP - Payment processing open for extension
interface PaymentProcessor {
    boolean processPayment(BigDecimal amount, String customerId);
}

class CreditCardProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(BigDecimal amount, String customerId) {
        System.out.println("Processing credit card payment of $" + amount + " for customer " + customerId);
        return true;
    }
}

class PayPalProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(BigDecimal amount, String customerId) {
        System.out.println("Processing PayPal payment of $" + amount + " for customer " + customerId);
        return true;
    }
}

class CryptoPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(BigDecimal amount, String customerId) {
        System.out.println("Processing cryptocurrency payment of $" + amount + " for customer " + customerId);
        return true;
    }
}

// ISP - Segregated interfaces for different shipping capabilities
interface ShippingCalculator {
    BigDecimal calculateShippingCost(Order order, String address);
}

interface TrackingProvider {
    String getTrackingNumber(Order order);
    String getTrackingStatus(String trackingNumber);
}

interface ExpressShipping {
    boolean canProvideExpressShipping(String address);
    BigDecimal getExpressShippingCost(Order order, String address);
}

// Different shipping providers implementing only what they support
class StandardShippingProvider implements ShippingCalculator {
    @Override
    public BigDecimal calculateShippingCost(Order order, String address) {
        return new BigDecimal("5.99");
    }
}

class FedExProvider implements ShippingCalculator, TrackingProvider, ExpressShipping {
    @Override
    public BigDecimal calculateShippingCost(Order order, String address) {
        return new BigDecimal("12.99");
    }
    
    @Override
    public String getTrackingNumber(Order order) {
        return "FDX" + order.getId() + "123";
    }
    
    @Override
    public String getTrackingStatus(String trackingNumber) {
        return "In Transit";
    }
    
    @Override
    public boolean canProvideExpressShipping(String address) {
        return true;
    }
    
    @Override
    public BigDecimal getExpressShippingCost(Order order, String address) {
        return new BigDecimal("24.99");
    }
}

// DIP - Order service depends on abstractions
interface OrderRepository {
    void saveOrder(Order order);
    Order findById(String orderId);
}

interface InventoryService {
    boolean isInStock(Product product, int quantity);
    void reserveStock(Product product, int quantity);
}

class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentProcessor paymentProcessor;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;
    
    // DIP - Constructor injection of dependencies
    public OrderService(OrderRepository orderRepository, 
                       PaymentProcessor paymentProcessor,
                       InventoryService inventoryService,
                       NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.paymentProcessor = paymentProcessor;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
    }
    
    public boolean processOrder(Order order) {
        // Check inventory
        for (OrderItem item : order.getItems()) {
            if (!inventoryService.isInStock(item.getProduct(), item.getQuantity())) {
                System.out.println("Insufficient stock for product: " + item.getProduct().getName());
                return false;
            }
        }
        
        // Process payment
        BigDecimal totalAmount = order.getTotalAmount();
        if (!paymentProcessor.processPayment(totalAmount, order.getCustomerId())) {
            System.out.println("Payment failed for order: " + order.getId());
            return false;
        }
        
        // Reserve inventory
        for (OrderItem item : order.getItems()) {
            inventoryService.reserveStock(item.getProduct(), item.getQuantity());
        }
        
        // Save order
        orderRepository.saveOrder(order);
        
        // Send confirmation
        notificationService.sendNotification(
            "Order confirmed: " + order.getId(), 
            order.getCustomerId()
        );
        
        System.out.println("Order processed successfully: " + order.getId());
        return true;
    }
}

// Implementations
class DatabaseOrderRepository implements OrderRepository {
    private Map<String, Order> orders = new HashMap<>();
    
    @Override
    public void saveOrder(Order order) {
        orders.put(order.getId(), order);
        System.out.println("Order saved to database: " + order.getId());
    }
    
    @Override
    public Order findById(String orderId) {
        return orders.get(orderId);
    }
}

class DatabaseInventoryService implements InventoryService {
    @Override
    public boolean isInStock(Product product, int quantity) {
        return product.getStockQuantity() >= quantity;
    }
    
    @Override
    public void reserveStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        System.out.println("Reserved " + quantity + " units of " + product.getName());
    }
}

public class SOLIDPrinciplesDemo {
    public static void main(String[] args) {
        System.out.println("=== SOLID PRINCIPLES DEMO ===\n");
        
        demonstrateSRP();
        demonstrateOCP();
        demonstrateLSP();
        demonstrateISP();
        demonstrateDIP();
        demonstrateECommerceExample();
    }
    
    private static void demonstrateSRP() {
        System.out.println("1. SINGLE RESPONSIBILITY PRINCIPLE");
        System.out.println("===================================");
        
        User user = new User("John Doe", "john@example.com");
        EmailValidator validator = new EmailValidator();
        UserRepository repository = new UserRepository();
        EmailService emailService = new EmailService();
        
        if (validator.isValid(user.getEmail())) {
            repository.save(user);
            emailService.sendWelcomeEmail(user);
        }
        System.out.println();
    }
    
    private static void demonstrateOCP() {
        System.out.println("2. OPEN/CLOSED PRINCIPLE");
        System.out.println("=========================");
        
        List<Shape> shapes = Arrays.asList(
            new Rectangle(5, 10),
            new Circle(7),
            new Triangle(6, 8)
        );
        
        AreaCalculator calculator = new AreaCalculator();
        double totalArea = calculator.calculateTotalArea(shapes);
        System.out.println("Total area of all shapes: " + totalArea);
        System.out.println();
    }
    
    private static void demonstrateLSP() {
        System.out.println("3. LISKOV SUBSTITUTION PRINCIPLE");
        System.out.println("=================================");
        
        List<Bird> birds = Arrays.asList(new Eagle(), new Penguin(), new Duck());
        
        for (Bird bird : birds) {
            bird.eat();
            bird.makeSound();
            
            if (bird instanceof Flyable) {
                ((Flyable) bird).fly();
            }
            
            if (bird instanceof Swimmable) {
                ((Swimmable) bird).swim();
            }
        }
        System.out.println();
    }
    
    private static void demonstrateISP() {
        System.out.println("4. INTERFACE SEGREGATION PRINCIPLE");
        System.out.println("===================================");
        
        SimplePrinter simplePrinter = new SimplePrinter();
        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        Scanner scanner = new Scanner();
        
        simplePrinter.print();
        
        mfp.print();
        mfp.scan();
        mfp.copy();
        mfp.fax();
        
        scanner.scan();
        System.out.println();
    }
    
    private static void demonstrateDIP() {
        System.out.println("5. DEPENDENCY INVERSION PRINCIPLE");
        System.out.println("==================================");
        
        // Different implementations can be injected
        MessageSender emailSender = new EmailSender();
        MessageSender smsSender = new SMSSender();
        NotificationRepository repository = new DatabaseNotificationRepository();
        
        NotificationService emailNotificationService = 
            new NotificationService(emailSender, repository);
        NotificationService smsNotificationService = 
            new NotificationService(smsSender, repository);
        
        emailNotificationService.sendNotification("Welcome!", "user@example.com");
        smsNotificationService.sendNotification("Urgent update", "+1234567890");
        System.out.println();
    }
    
    private static void demonstrateECommerceExample() {
        System.out.println("6. E-COMMERCE EXAMPLE - ALL PRINCIPLES");
        System.out.println("=======================================");
        
        // Create dependencies (DIP)
        OrderRepository orderRepository = new DatabaseOrderRepository();
        PaymentProcessor paymentProcessor = new CreditCardProcessor();
        InventoryService inventoryService = new DatabaseInventoryService();
        MessageSender messageSender = new EmailSender();
        NotificationRepository notificationRepository = new DatabaseNotificationRepository();
        NotificationService notificationService = 
            new NotificationService(messageSender, notificationRepository);
        
        // Create order service with injected dependencies
        OrderService orderService = new OrderService(
            orderRepository, paymentProcessor, inventoryService, notificationService
        );
        
        // Create products and order
        Product laptop = new Product("1", "Gaming Laptop", new BigDecimal("1299.99"), 10);
        Product mouse = new Product("2", "Wireless Mouse", new BigDecimal("29.99"), 50);
        
        Order order = new Order("ORD-001", "CUST-123");
        order.addItem(new OrderItem(laptop, 1));
        order.addItem(new OrderItem(mouse, 2));
        
        // Process order
        boolean success = orderService.processOrder(order);
        System.out.println("Order processing result: " + (success ? "SUCCESS" : "FAILED"));
    }
}
