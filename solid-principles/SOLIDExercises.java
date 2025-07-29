package com.solid.exercises;

import java.util.*;
import java.math.BigDecimal;

/**
 * SOLID Principles Exercises
 * 
 * This file contains examples that VIOLATE SOLID principles.
 * Your task is to refactor them to follow SOLID principles.
 * 
 * Solutions are provided at the bottom.
 */

/**
 * EXERCISE 1: Single Responsibility Principle (SRP)
 * 
 * Problem: The Employee class below has multiple responsibilities.
 * Task: Refactor to follow SRP by separating concerns.
 */

// ❌ VIOLATES SRP - Fix this!
class Employee {
    private String name;
    private String email;
    private BigDecimal salary;
    private String department;
    
    public Employee(String name, String email, BigDecimal salary, String department) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }
    
    // Employee data management
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getSalary() { return salary; }
    public String getDepartment() { return department; }
    
    // Email validation (different responsibility!)
    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    // Salary calculation (different responsibility!)
    public BigDecimal calculateTax() {
        return salary.multiply(new BigDecimal("0.25"));
    }
    
    public BigDecimal calculateBonus() {
        if (department.equals("Sales")) {
            return salary.multiply(new BigDecimal("0.10"));
        } else if (department.equals("Engineering")) {
            return salary.multiply(new BigDecimal("0.15"));
        }
        return BigDecimal.ZERO;
    }
    
    // Database operations (different responsibility!)
    public void saveToDatabase() {
        System.out.println("Saving employee " + name + " to database");
        // Database logic here
    }
    
    public void deleteFromDatabase() {
        System.out.println("Deleting employee " + name + " from database");
        // Database logic here
    }
    
    // Email operations (different responsibility!)
    public void sendWelcomeEmail() {
        System.out.println("Sending welcome email to " + email);
        // Email sending logic here
    }
    
    public void sendPayrollEmail() {
        System.out.println("Sending payroll email to " + email);
        // Email sending logic here
    }
    
    // Report generation (different responsibility!)
    public String generateEmployeeReport() {
        return "Employee Report:\n" +
               "Name: " + name + "\n" +
               "Email: " + email + "\n" +
               "Salary: $" + salary + "\n" +
               "Department: " + department + "\n" +
               "Tax: $" + calculateTax() + "\n" +
               "Bonus: $" + calculateBonus();
    }
}

/**
 * EXERCISE 2: Open/Closed Principle (OCP)
 * 
 * Problem: The DiscountCalculator requires modification for each new discount type.
 * Task: Refactor to be open for extension but closed for modification.
 */

// ❌ VIOLATES OCP - Fix this!
class DiscountCalculator {
    public BigDecimal calculateDiscount(String customerType, BigDecimal amount) {
        if (customerType.equals("Regular")) {
            return amount.multiply(new BigDecimal("0.05")); // 5% discount
        } else if (customerType.equals("Premium")) {
            return amount.multiply(new BigDecimal("0.10")); // 10% discount
        } else if (customerType.equals("VIP")) {
            return amount.multiply(new BigDecimal("0.20")); // 20% discount
        } else if (customerType.equals("Student")) {
            return amount.multiply(new BigDecimal("0.15")); // 15% discount
        }
        // Need to modify this method for each new customer type!
        return BigDecimal.ZERO;
    }
}

/**
 * EXERCISE 3: Liskov Substitution Principle (LSP)
 * 
 * Problem: The Square class violates LSP when substituted for Rectangle.
 * Task: Refactor to follow LSP properly.
 */

// ❌ VIOLATES LSP - Fix this!
class Rectangle {
    protected int width;
    protected int height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getArea() {
        return width * height;
    }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // Forces height to equal width
    }
    
    @Override
    public void setHeight(int height) {
        this.width = height;  // Forces width to equal height
        this.height = height;
    }
}

// This method expects Rectangle behavior but fails with Square
class GeometryTester {
    public void testRectangle(Rectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(10);
        
        // This assertion fails when rectangle is actually a Square!
        // Expected: 50, Actual: 100 (when Square)
        assert rectangle.getArea() == 50 : "Expected area to be 50";
    }
}

/**
 * EXERCISE 4: Interface Segregation Principle (ISP)
 * 
 * Problem: The Worker interface forces classes to implement methods they don't use.
 * Task: Segregate the interface into smaller, focused interfaces.
 */

// ❌ VIOLATES ISP - Fix this!
interface Worker {
    void work();
    void eat();
    void sleep();
    void code();
    void attendMeeting();
    void writeDocumentation();
}

class Programmer implements Worker {
    @Override
    public void work() {
        System.out.println("Programmer working");
    }
    
    @Override
    public void eat() {
        System.out.println("Programmer eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Programmer sleeping");
    }
    
    @Override
    public void code() {
        System.out.println("Programmer coding");
    }
    
    @Override
    public void attendMeeting() {
        System.out.println("Programmer attending meeting");
    }
    
    @Override
    public void writeDocumentation() {
        System.out.println("Programmer writing documentation");
    }
}

class Robot implements Worker {
    @Override
    public void work() {
        System.out.println("Robot working");
    }
    
    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat!"); // ❌ Forced to implement
    }
    
    @Override
    public void sleep() {
        throw new UnsupportedOperationException("Robots don't sleep!"); // ❌ Forced to implement
    }
    
    @Override
    public void code() {
        throw new UnsupportedOperationException("This robot doesn't code!"); // ❌ Forced to implement
    }
    
    @Override
    public void attendMeeting() {
        throw new UnsupportedOperationException("Robots don't attend meetings!"); // ❌ Forced to implement
    }
    
    @Override
    public void writeDocumentation() {
        throw new UnsupportedOperationException("Robots don't write documentation!"); // ❌ Forced to implement
    }
}

/**
 * EXERCISE 5: Dependency Inversion Principle (DIP)
 * 
 * Problem: OrderProcessor directly depends on concrete classes.
 * Task: Refactor to depend on abstractions instead.
 */

// ❌ VIOLATES DIP - Fix this!
class EmailNotifier {
    public void sendEmail(String message, String recipient) {
        System.out.println("Email sent to " + recipient + ": " + message);
    }
}

class DatabaseLogger {
    public void logToDatabase(String message) {
        System.out.println("Logged to database: " + message);
    }
}

class OrderProcessor {
    private EmailNotifier emailNotifier; // ❌ Direct dependency on concrete class
    private DatabaseLogger logger;       // ❌ Direct dependency on concrete class
    
    public OrderProcessor() {
        this.emailNotifier = new EmailNotifier(); // ❌ Hard-coded instantiation
        this.logger = new DatabaseLogger();       // ❌ Hard-coded instantiation
    }
    
    public void processOrder(String orderId, String customerEmail) {
        System.out.println("Processing order: " + orderId);
        
        // Process order logic here...
        
        emailNotifier.sendEmail("Order " + orderId + " confirmed", customerEmail);
        logger.logToDatabase("Order " + orderId + " processed");
    }
}

/*
================================================================================
                               SOLUTIONS
================================================================================
*/

/**
 * SOLUTION 1: Single Responsibility Principle (SRP)
 * 
 * Separate each responsibility into its own class.
 */

// ✅ SOLUTION - Each class has single responsibility
class EmployeeSolution {
    private String name;
    private String email;
    private BigDecimal salary;
    private String department;
    
    public EmployeeSolution(String name, String email, BigDecimal salary, String department) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }
    
    // Only responsible for employee data
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getSalary() { return salary; }
    public String getDepartment() { return department; }
}

class EmailValidatorSolution {
    public boolean isValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}

class SalaryCalculatorSolution {
    public BigDecimal calculateTax(BigDecimal salary) {
        return salary.multiply(new BigDecimal("0.25"));
    }
    
    public BigDecimal calculateBonus(BigDecimal salary, String department) {
        switch (department) {
            case "Sales":
                return salary.multiply(new BigDecimal("0.10"));
            case "Engineering":
                return salary.multiply(new BigDecimal("0.15"));
            default:
                return BigDecimal.ZERO;
        }
    }
}

class EmployeeRepositorySolution {
    public void save(EmployeeSolution employee) {
        System.out.println("Saving employee " + employee.getName() + " to database");
    }
    
    public void delete(EmployeeSolution employee) {
        System.out.println("Deleting employee " + employee.getName() + " from database");
    }
}

class EmployeeEmailServiceSolution {
    public void sendWelcomeEmail(EmployeeSolution employee) {
        System.out.println("Sending welcome email to " + employee.getEmail());
    }
    
    public void sendPayrollEmail(EmployeeSolution employee) {
        System.out.println("Sending payroll email to " + employee.getEmail());
    }
}

class EmployeeReportGeneratorSolution {
    private SalaryCalculatorSolution salaryCalculator;
    
    public EmployeeReportGeneratorSolution(SalaryCalculatorSolution salaryCalculator) {
        this.salaryCalculator = salaryCalculator;
    }
    
    public String generateReport(EmployeeSolution employee) {
        BigDecimal tax = salaryCalculator.calculateTax(employee.getSalary());
        BigDecimal bonus = salaryCalculator.calculateBonus(employee.getSalary(), employee.getDepartment());
        
        return "Employee Report:\n" +
               "Name: " + employee.getName() + "\n" +
               "Email: " + employee.getEmail() + "\n" +
               "Salary: $" + employee.getSalary() + "\n" +
               "Department: " + employee.getDepartment() + "\n" +
               "Tax: $" + tax + "\n" +
               "Bonus: $" + bonus;
    }
}

/**
 * SOLUTION 2: Open/Closed Principle (OCP)
 * 
 * Use strategy pattern to be open for extension.
 */

// ✅ SOLUTION - Open for extension, closed for modification
interface DiscountStrategy {
    BigDecimal calculateDiscount(BigDecimal amount);
}

class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.05"));
    }
}

class PremiumCustomerDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.10"));
    }
}

class VIPCustomerDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.20"));
    }
}

class StudentDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.15"));
    }
}

// Easy to add new discount types without modifying existing code
class EmployeeDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.25"));
    }
}

class DiscountCalculatorSolution {
    public BigDecimal calculateDiscount(DiscountStrategy strategy, BigDecimal amount) {
        return strategy.calculateDiscount(amount);
    }
}

/**
 * SOLUTION 3: Liskov Substitution Principle (LSP)
 * 
 * Use proper abstraction that all shapes can follow.
 */

// ✅ SOLUTION - Proper abstraction
interface ShapeSolution {
    int getArea();
    int getPerimeter();
}

class RectangleSolution implements ShapeSolution {
    private final int width;
    private final int height;
    
    public RectangleSolution(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    @Override
    public int getArea() {
        return width * height;
    }
    
    @Override
    public int getPerimeter() {
        return 2 * (width + height);
    }
}

class SquareSolution implements ShapeSolution {
    private final int side;
    
    public SquareSolution(int side) {
        this.side = side;
    }
    
    public int getSide() { return side; }
    
    @Override
    public int getArea() {
        return side * side;
    }
    
    @Override
    public int getPerimeter() {
        return 4 * side;
    }
}

class GeometryTesterSolution {
    public void testShape(ShapeSolution shape) {
        int area = shape.getArea();
        int perimeter = shape.getPerimeter();
        System.out.println("Shape area: " + area + ", perimeter: " + perimeter);
        // This works for any shape implementation!
    }
}

/**
 * SOLUTION 4: Interface Segregation Principle (ISP)
 * 
 * Segregate into smaller, focused interfaces.
 */

// ✅ SOLUTION - Segregated interfaces
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

interface Programmable {
    void code();
    void writeDocumentation();
}

interface MeetingAttendable {
    void attendMeeting();
}

class ProgrammerSolution implements Workable, Eatable, Sleepable, Programmable, MeetingAttendable {
    @Override
    public void work() {
        System.out.println("Programmer working");
    }
    
    @Override
    public void eat() {
        System.out.println("Programmer eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Programmer sleeping");
    }
    
    @Override
    public void code() {
        System.out.println("Programmer coding");
    }
    
    @Override
    public void writeDocumentation() {
        System.out.println("Programmer writing documentation");
    }
    
    @Override
    public void attendMeeting() {
        System.out.println("Programmer attending meeting");
    }
}

class RobotSolution implements Workable {
    @Override
    public void work() {
        System.out.println("Robot working");
    }
    // Robot only implements what it can do!
}

class CodingRobotSolution implements Workable, Programmable {
    @Override
    public void work() {
        System.out.println("Coding robot working");
    }
    
    @Override
    public void code() {
        System.out.println("Coding robot programming");
    }
    
    @Override
    public void writeDocumentation() {
        System.out.println("Coding robot writing documentation");
    }
}

/**
 * SOLUTION 5: Dependency Inversion Principle (DIP)
 * 
 * Depend on abstractions, not concretions.
 */

// ✅ SOLUTION - Depend on abstractions
interface NotificationSender {
    void sendNotification(String message, String recipient);
}

interface Logger {
    void log(String message);
}

class EmailNotifierSolution implements NotificationSender {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("Email sent to " + recipient + ": " + message);
    }
}

class SMSNotifierSolution implements NotificationSender {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("SMS sent to " + recipient + ": " + message);
    }
}

class DatabaseLoggerSolution implements Logger {
    @Override
    public void log(String message) {
        System.out.println("Logged to database: " + message);
    }
}

class FileLoggerSolution implements Logger {
    @Override
    public void log(String message) {
        System.out.println("Logged to file: " + message);
    }
}

class OrderProcessorSolution {
    private final NotificationSender notificationSender; // ✅ Depends on abstraction
    private final Logger logger;                          // ✅ Depends on abstraction
    
    // ✅ Dependency injection through constructor
    public OrderProcessorSolution(NotificationSender notificationSender, Logger logger) {
        this.notificationSender = notificationSender;
        this.logger = logger;
    }
    
    public void processOrder(String orderId, String customerEmail) {
        System.out.println("Processing order: " + orderId);
        
        // Process order logic here...
        
        notificationSender.sendNotification("Order " + orderId + " confirmed", customerEmail);
        logger.log("Order " + orderId + " processed");
    }
}

/**
 * DEMO CLASS - Shows how to use all solutions
 */
public class SOLIDExercises {
    public static void main(String[] args) {
        System.out.println("=== SOLID PRINCIPLES SOLUTIONS ===\n");
        
        demonstrateSRPSolution();
        demonstrateOCPSolution();
        demonstrateLSPSolution();
        demonstrateISPSolution();
        demonstrateDIPSolution();
    }
    
    private static void demonstrateSRPSolution() {
        System.out.println("1. SRP SOLUTION - Separated Responsibilities");
        System.out.println("============================================");
        
        EmployeeSolution employee = new EmployeeSolution("John Doe", "john@example.com", 
                                                        new BigDecimal("50000"), "Engineering");
        
        EmailValidatorSolution validator = new EmailValidatorSolution();
        SalaryCalculatorSolution calculator = new SalaryCalculatorSolution();
        EmployeeRepositorySolution repository = new EmployeeRepositorySolution();
        EmployeeEmailServiceSolution emailService = new EmployeeEmailServiceSolution();
        EmployeeReportGeneratorSolution reportGenerator = new EmployeeReportGeneratorSolution(calculator);
        
        if (validator.isValid(employee.getEmail())) {
            repository.save(employee);
            emailService.sendWelcomeEmail(employee);
            System.out.println(reportGenerator.generateReport(employee));
        }
        System.out.println();
    }
    
    private static void demonstrateOCPSolution() {
        System.out.println("2. OCP SOLUTION - Extensible Design");
        System.out.println("===================================");
        
        DiscountCalculatorSolution calculator = new DiscountCalculatorSolution();
        BigDecimal amount = new BigDecimal("100.00");
        
        DiscountStrategy regular = new RegularCustomerDiscount();
        DiscountStrategy vip = new VIPCustomerDiscount();
        DiscountStrategy employee = new EmployeeDiscount(); // Easy to add new types!
        
        System.out.println("Regular discount: $" + calculator.calculateDiscount(regular, amount));
        System.out.println("VIP discount: $" + calculator.calculateDiscount(vip, amount));
        System.out.println("Employee discount: $" + calculator.calculateDiscount(employee, amount));
        System.out.println();
    }
    
    private static void demonstrateLSPSolution() {
        System.out.println("3. LSP SOLUTION - Proper Substitution");
        System.out.println("=====================================");
        
        GeometryTesterSolution tester = new GeometryTesterSolution();
        
        ShapeSolution rectangle = new RectangleSolution(5, 10);
        ShapeSolution square = new SquareSolution(7);
        
        tester.testShape(rectangle); // Works perfectly
        tester.testShape(square);    // Also works perfectly
        System.out.println();
    }
    
    private static void demonstrateISPSolution() {
        System.out.println("4. ISP SOLUTION - Segregated Interfaces");
        System.out.println("=======================================");
        
        ProgrammerSolution programmer = new ProgrammerSolution();
        RobotSolution robot = new RobotSolution();
        CodingRobotSolution codingRobot = new CodingRobotSolution();
        
        // Each implements only what they can do
        programmer.work();
        programmer.code();
        programmer.eat();
        
        robot.work(); // Only implements Workable
        
        codingRobot.work();
        codingRobot.code(); // Implements Workable and Programmable
        System.out.println();
    }
    
    private static void demonstrateDIPSolution() {
        System.out.println("5. DIP SOLUTION - Dependency Injection");
        System.out.println("======================================");
        
        // Different implementations can be injected
        NotificationSender emailSender = new EmailNotifierSolution();
        NotificationSender smsSender = new SMSNotifierSolution();
        Logger dbLogger = new DatabaseLoggerSolution();
        Logger fileLogger = new FileLoggerSolution();
        
        OrderProcessorSolution emailProcessor = new OrderProcessorSolution(emailSender, dbLogger);
        OrderProcessorSolution smsProcessor = new OrderProcessorSolution(smsSender, fileLogger);
        
        emailProcessor.processOrder("ORD-001", "customer@example.com");
        smsProcessor.processOrder("ORD-002", "+1234567890");
        System.out.println();
    }
}
