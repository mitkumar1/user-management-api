package com.java.features;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 21 Features with Examples
 * Released: September 2023 (LTS Version)
 */
public class Java21Features {
    
    public static void main(String[] args) {
        Java21Features examples = new Java21Features();
        
        System.out.println("=== JAVA 21 FEATURES ===\n");
        
        examples.patternMatchingSwitch();
        examples.recordPatterns();
        examples.stringTemplates();
        examples.sequencedCollections();
        examples.virtualThreads();
        examples.scopedValues();
        examples.structuredConcurrency();
    }
    
    // 1. PATTERN MATCHING FOR SWITCH (Final)
    public void patternMatchingSwitch() {
        System.out.println("1. PATTERN MATCHING FOR SWITCH");
        System.out.println("-------------------------------");
        
        // Basic pattern matching
        Object obj = "Hello World";
        String result = processObject(obj);
        System.out.println("Result: " + result);
        
        // With guards
        processNumber(42);
        processNumber(-5);
        processNumber(0);
        
        // Nested patterns
        processShape(new Circle(5.0));
        processShape(new Rectangle(4.0, 6.0));
        System.out.println();
    }
    
    public String processObject(Object obj) {
        return switch (obj) {
            case null -> "null value";
            case String s when s.length() > 10 -> "Long string: " + s;
            case String s -> "Short string: " + s;
            case Integer i when i > 0 -> "Positive number: " + i;
            case Integer i -> "Non-positive number: " + i;
            case List<?> list when list.isEmpty() -> "Empty list";
            case List<?> list -> "List with " + list.size() + " elements";
            default -> "Unknown type: " + obj.getClass().getSimpleName();
        };
    }
    
    public void processNumber(Integer num) {
        String result = switch (num) {
            case null -> "null";
            case Integer i when i > 0 -> "positive";
            case Integer i when i < 0 -> "negative";
            case Integer i when i == 0 -> "zero";
            default -> "unexpected";
        };
        System.out.println(num + " is " + result);
    }
    
    public void processShape(Object shape) {
        String description = switch (shape) {
            case Circle(var radius) when radius > 10 -> 
                "Large circle with radius " + radius;
            case Circle(var radius) -> 
                "Small circle with radius " + radius;
            case Rectangle(var width, var height) when width == height -> 
                "Square with side " + width;
            case Rectangle(var width, var height) -> 
                "Rectangle " + width + "x" + height;
            default -> "Unknown shape";
        };
        System.out.println(description);
    }
    
    // 2. RECORD PATTERNS
    public void recordPatterns() {
        System.out.println("2. RECORD PATTERNS");
        System.out.println("------------------");
        
        Point point = new Point(3, 4);
        ColoredPoint coloredPoint = new ColoredPoint(new Point(1, 2), "Red");
        Person person = new Person("Alice", 30);
        
        // Deconstructing records
        processPoint(point);
        processColoredPoint(coloredPoint);
        processPerson(person);
        
        // Nested deconstruction
        Address address = new Address("123 Main St", "Anytown", "12345");
        PersonWithAddress personWithAddress = new PersonWithAddress("Bob", address);
        processPersonWithAddress(personWithAddress);
        System.out.println();
    }
    
    public void processPoint(Point point) {
        switch (point) {
            case Point(var x, var y) when x == 0 && y == 0 -> 
                System.out.println("Origin point");
            case Point(var x, var y) when x == y -> 
                System.out.println("Diagonal point at (" + x + ", " + y + ")");
            case Point(var x, var y) -> 
                System.out.println("Point at (" + x + ", " + y + ")");
        }
    }
    
    public void processColoredPoint(ColoredPoint cp) {
        switch (cp) {
            case ColoredPoint(Point(var x, var y), var color) -> 
                System.out.println(color + " point at (" + x + ", " + y + ")");
        }
    }
    
    public void processPerson(Person person) {
        switch (person) {
            case Person(var name, var age) when age >= 18 -> 
                System.out.println(name + " is an adult (" + age + " years old)");
            case Person(var name, var age) -> 
                System.out.println(name + " is a minor (" + age + " years old)");
        }
    }
    
    public void processPersonWithAddress(PersonWithAddress pwa) {
        switch (pwa) {
            case PersonWithAddress(var name, Address(var street, var city, var zip)) -> 
                System.out.println(name + " lives at " + street + ", " + city + " " + zip);
        }
    }
    
    // 3. STRING TEMPLATES (Preview Feature)
    public void stringTemplates() {
        System.out.println("3. STRING TEMPLATES (Preview)");
        System.out.println("-----------------------------");
        
        String name = "Java";
        int version = 21;
        double price = 99.99;
        
        // Note: String templates are a preview feature in Java 21
        // This is conceptual code showing the intended syntax
        System.out.println("String templates are a preview feature in Java 21");
        System.out.println("They allow embedded expressions in strings");
        System.out.println("Example syntax would be: STR.\"Hello \\{name} \\{version}!\"");
        System.out.println("Current workaround: " + String.format("Hello %s %d!", name, version));
        System.out.println();
    }
    
    // 4. SEQUENCED COLLECTIONS
    public void sequencedCollections() {
        System.out.println("4. SEQUENCED COLLECTIONS");
        System.out.println("-------------------------");
        
        // SequencedList methods
        List<String> list = new ArrayList<>(List.of("first", "second", "third"));
        System.out.println("Original list: " + list);
        
        // New methods in Java 21
        System.out.println("First element: " + list.getFirst());
        System.out.println("Last element: " + list.getLast());
        
        list.addFirst("new-first");
        list.addLast("new-last");
        System.out.println("After adding: " + list);
        
        list.removeFirst();
        list.removeLast();
        System.out.println("After removing: " + list);
        
        // Reversed view
        List<String> reversed = list.reversed();
        System.out.println("Reversed view: " + reversed);
        
        // SequencedSet methods
        LinkedHashSet<String> set = new LinkedHashSet<>(List.of("a", "b", "c"));
        System.out.println("Original set: " + set);
        System.out.println("First: " + set.getFirst());
        System.out.println("Last: " + set.getLast());
        
        // SequencedMap methods
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("first", 1);
        map.put("second", 2);
        map.put("third", 3);
        
        System.out.println("Original map: " + map);
        System.out.println("First entry: " + map.firstEntry());
        System.out.println("Last entry: " + map.lastEntry());
        System.out.println();
    }
    
    // 5. VIRTUAL THREADS (Final)
    public void virtualThreads() {
        System.out.println("5. VIRTUAL THREADS");
        System.out.println("------------------");
        
        // Traditional platform thread
        Thread platformThread = new Thread(() -> {
            System.out.println("Platform thread: " + Thread.currentThread());
        });
        platformThread.start();
        
        try {
            platformThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Virtual thread
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("Virtual thread: " + Thread.currentThread());
        });
        
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Multiple virtual threads
        System.out.println("Creating 1000 virtual threads...");
        long startTime = System.currentTimeMillis();
        
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            final int threadId = i;
            Thread vt = Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(10); // Simulate work
                    if (threadId % 100 == 0) {
                        System.out.println("Virtual thread " + threadId + " completed");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(vt);
        }
        
        // Wait for all virtual threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("1000 virtual threads completed in " + (endTime - startTime) + "ms");
        System.out.println();
    }
    
    // 6. SCOPED VALUES (Preview)
    public void scopedValues() {
        System.out.println("6. SCOPED VALUES (Preview)");
        System.out.println("--------------------------");
        
        System.out.println("Scoped Values are a preview feature in Java 21");
        System.out.println("They provide a way to share immutable data within a thread");
        System.out.println("Similar to ThreadLocal but more efficient and immutable");
        System.out.println("Example usage would be for request context in web applications");
        System.out.println();
    }
    
    // 7. STRUCTURED CONCURRENCY (Preview)
    public void structuredConcurrency() {
        System.out.println("7. STRUCTURED CONCURRENCY (Preview)");
        System.out.println("-----------------------------------");
        
        System.out.println("Structured Concurrency is a preview feature in Java 21");
        System.out.println("It provides a way to manage concurrent operations as a unit");
        System.out.println("Helps with error handling and cancellation in concurrent code");
        System.out.println("Uses StructuredTaskScope for managing related tasks");
        System.out.println();
    }
    
    // Supporting classes for demonstrations
    
    // Records for pattern matching
    public record Point(int x, int y) {}
    
    public record ColoredPoint(Point point, String color) {}
    
    public record Person(String name, int age) {}
    
    public record Address(String street, String city, String zipCode) {}
    
    public record PersonWithAddress(String name, Address address) {}
    
    // Shapes for pattern matching
    public record Circle(double radius) {}
    
    public record Rectangle(double width, double height) {}
    
    public record Triangle(double a, double b, double c) {}
}

// Additional Java 21 Features Summary:
/*
1. Performance Improvements:
   - Generational ZGC
   - Better escape analysis
   - Improved startup performance

2. New APIs:
   - Math.clamp() methods
   - String methods improvements
   - Character.isEmoji() methods

3. JVM Enhancements:
   - Better memory management
   - Improved garbage collection
   - Better observability

4. Security:
   - Key Encapsulation Mechanism API
   - Enhanced security providers

5. Developer Experience:
   - Better error messages
   - Improved debugging support
   - Enhanced tooling integration

6. Preview/Incubator Features:
   - String Templates (Preview)
   - Unnamed Patterns and Variables (Preview)
   - Unnamed Classes and Instance Main Methods (Preview)
   - Scoped Values (Preview)
   - Structured Concurrency (Preview)
   - Vector API (Incubator)
   - Foreign Function & Memory API (Preview)
*/
