package com.java.features;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java 17 Features with Examples
 * Released: September 2021 (LTS Version)
 */
public class Java17Features {
    
    public static void main(String[] args) {
        Java17Features examples = new Java17Features();
        
        System.out.println("=== JAVA 17 FEATURES ===\n");
        
        examples.sealedClasses();
        examples.patternMatchingInstanceof();
        examples.recordsEnhanced();
        examples.textBlocks();
        examples.switchExpressions();
        examples.helpfulNullPointers();
        examples.newStreamMethods();
    }
    
    // 1. SEALED CLASSES
    public void sealedClasses() {
        System.out.println("1. SEALED CLASSES");
        System.out.println("-----------------");
        
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 4.0, 5.0);
        
        System.out.println("Circle area: " + calculateArea(circle));
        System.out.println("Rectangle area: " + calculateArea(rectangle));
        System.out.println("Triangle area: " + calculateArea(triangle));
        System.out.println();
    }
    
    // Pattern matching with sealed classes
    public double calculateArea(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t -> {
                // Heron's formula
                double s = (t.a() + t.b() + t.c()) / 2;
                yield Math.sqrt(s * (s - t.a()) * (s - t.b()) * (s - t.c()));
            }
        };
    }
    
    // 2. PATTERN MATCHING FOR INSTANCEOF
    public void patternMatchingInstanceof() {
        System.out.println("2. PATTERN MATCHING FOR INSTANCEOF");
        System.out.println("----------------------------------");
        
        Object obj = "Hello World";
        
        // Before Java 17
        if (obj instanceof String) {
            String str = (String) obj;
            System.out.println("Old way - Length: " + str.length());
        }
        
        // Java 17 - Pattern matching
        if (obj instanceof String str) {
            System.out.println("New way - Length: " + str.length());
            System.out.println("Uppercase: " + str.toUpperCase());
        }
        
        // More complex example
        processObject("Java");
        processObject(42);
        processObject(List.of(1, 2, 3));
        System.out.println();
    }
    
    public void processObject(Object obj) {
        if (obj instanceof String str && str.length() > 3) {
            System.out.println("Long string: " + str);
        } else if (obj instanceof Integer num && num > 0) {
            System.out.println("Positive number: " + num);
        } else if (obj instanceof List<?> list && !list.isEmpty()) {
            System.out.println("Non-empty list with " + list.size() + " elements");
        } else {
            System.out.println("Other type: " + obj.getClass().getSimpleName());
        }
    }
    
    // 3. ENHANCED RECORDS
    public void recordsEnhanced() {
        System.out.println("3. ENHANCED RECORDS");
        System.out.println("-------------------");
        
        // Creating records
        Person person = new Person("Alice", 30, "alice@example.com");
        Employee employee = new Employee("Bob", 25, "Developer", 75000);
        
        System.out.println("Person: " + person);
        System.out.println("Employee: " + employee);
        System.out.println("Employee details: " + employee.getDetails());
        
        // Records with validation
        try {
            Person invalidPerson = new Person("", -5, "invalid-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        
        // Nested records
        Address address = new Address("123 Main St", "Anytown", "12345");
        PersonWithAddress personWithAddress = new PersonWithAddress("Charlie", address);
        System.out.println("Person with address: " + personWithAddress);
        System.out.println();
    }
    
    // 4. TEXT BLOCKS (from Java 15, stable in 17)
    public void textBlocks() {
        System.out.println("4. TEXT BLOCKS");
        System.out.println("--------------");
        
        // Before text blocks
        String jsonOld = "{\n" +
                "  \"name\": \"John\",\n" +
                "  \"age\": 30,\n" +
                "  \"city\": \"New York\"\n" +
                "}";
        
        // With text blocks
        String jsonNew = """
                {
                  "name": "John",
                  "age": 30,
                  "city": "New York"
                }
                """;
        
        System.out.println("JSON with text blocks:");
        System.out.println(jsonNew);
        
        // SQL example
        String sql = """
                SELECT p.name, p.age, a.city
                FROM person p
                JOIN address a ON p.address_id = a.id
                WHERE p.age > 18
                ORDER BY p.name
                """;
        
        System.out.println("SQL query:");
        System.out.println(sql);
        
        // HTML example
        String html = """
                <html>
                    <body>
                        <h1>Welcome to Java 17</h1>
                        <p>Text blocks make multi-line strings easy!</p>
                    </body>
                </html>
                """;
        
        System.out.println("HTML:");
        System.out.println(html);
        System.out.println();
    }
    
    // 5. SWITCH EXPRESSIONS (from Java 14, stable in 17)
    public void switchExpressions() {
        System.out.println("5. SWITCH EXPRESSIONS");
        System.out.println("---------------------");
        
        // Traditional switch
        String dayType1 = getDayTypeOld(DayOfWeek.MONDAY);
        System.out.println("Monday (old): " + dayType1);
        
        // Switch expression
        String dayType2 = getDayTypeNew(DayOfWeek.FRIDAY);
        System.out.println("Friday (new): " + dayType2);
        
        // Switch with yield
        String season = getSeason(6);
        System.out.println("Month 6 season: " + season);
        System.out.println();
    }
    
    // Old way
    public String getDayTypeOld(DayOfWeek day) {
        String result;
        switch (day) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                result = "Weekday";
                break;
            case SATURDAY:
            case SUNDAY:
                result = "Weekend";
                break;
            default:
                result = "Unknown";
        }
        return result;
    }
    
    // New way - switch expression
    public String getDayTypeNew(DayOfWeek day) {
        return switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
        };
    }
    
    // Switch with yield
    public String getSeason(int month) {
        return switch (month) {
            case 12, 1, 2 -> "Winter";
            case 3, 4, 5 -> "Spring";
            case 6, 7, 8 -> "Summer";
            case 9, 10, 11 -> "Fall";
            default -> {
                System.out.println("Invalid month: " + month);
                yield "Unknown";
            }
        };
    }
    
    // 6. HELPFUL NULL POINTER EXCEPTIONS
    public void helpfulNullPointers() {
        System.out.println("6. HELPFUL NULL POINTER EXCEPTIONS");
        System.out.println("----------------------------------");
        
        try {
            PersonWithAddress person = new PersonWithAddress("John", null);
            String city = person.address().city(); // This will throw NPE
        } catch (NullPointerException e) {
            System.out.println("NPE with helpful message:");
            System.out.println(e.getMessage());
        }
        System.out.println();
    }
    
    // 7. NEW STREAM METHODS
    public void newStreamMethods() {
        System.out.println("7. NEW STREAM METHODS");
        System.out.println("---------------------");
        
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Stream.toList() - returns immutable list
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .toList(); // New in Java 16
        
        System.out.println("Even numbers: " + evenNumbers);
        
        // Try to modify (will throw exception)
        try {
            evenNumbers.add(12);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify immutable list from toList()");
        }
        
        System.out.println();
    }
    
    // Supporting classes and enums
    
    // Sealed class hierarchy
    public sealed abstract class Shape permits Circle, Rectangle, Triangle {
        public abstract double area();
    }
    
    public final class Circle extends Shape {
        private final double radius;
        
        public Circle(double radius) {
            this.radius = radius;
        }
        
        public double radius() { return radius; }
        
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }
    
    public final class Rectangle extends Shape {
        private final double width, height;
        
        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        
        public double width() { return width; }
        public double height() { return height; }
        
        @Override
        public double area() {
            return width * height;
        }
    }
    
    public final class Triangle extends Shape {
        private final double a, b, c;
        
        public Triangle(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        public double a() { return a; }
        public double b() { return b; }
        public double c() { return c; }
        
        @Override
        public double area() {
            double s = (a + b + c) / 2;
            return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }
    }
    
    // Records
    public record Person(String name, int age, String email) {
        // Compact constructor for validation
        public Person {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email");
            }
        }
    }
    
    public record Employee(String name, int age, String position, double salary) {
        public String getDetails() {
            return String.format("%s (%d) - %s: $%.2f", name, age, position, salary);
        }
    }
    
    public record Address(String street, String city, String zipCode) {}
    
    public record PersonWithAddress(String name, Address address) {}
    
    // Enum for demonstration
    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}

// Additional Java 17 Features Summary:
/*
1. JVM Improvements:
   - Better performance
   - New garbage collectors (ZGC and Shenandoah production ready)
   - Improved startup time

2. Security Enhancements:
   - Strong encapsulation of JDK internals
   - Deprecation of Security Manager
   - New cryptographic algorithms

3. API Enhancements:
   - New methods in various classes
   - Better integration with other technologies

4. Removed Features:
   - Experimental AOT and JIT compiler
   - Deprecated applet API

5. Preview Features (in Java 17):
   - Foreign Function & Memory API
   - Vector API
*/
