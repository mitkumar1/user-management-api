package com.kumar.wipro.api.features;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/*
 * ============================================================================
 *                 JAVA 8 FEATURES - INTERVIEW PREPARATION GUIDE
 * ============================================================================
 * 
 * Version: 3.0 (Clean & Concise for Interview Preparation)
 * 
 * CORE JAVA 8 FEATURES:
 * 1. Lambda Expressions
 * 2. Functional Interfaces
 * 3. Method References
 * 4. Default & Static Methods in Interfaces
 * 5. Predefined Functional Interfaces
 * 6. Stream API
 * 7. Optional Class
 * 8. Date & Time API
 * 
 * PURPOSE:
 * - Enable functional programming
 * - Write concise, readable code
 * - Support parallel processing
 * - Improve API usability
 * 
 * ============================================================================
 * 1. LAMBDA EXPRESSIONS
 * ============================================================================
 * 
 * DEFINITION: Anonymous function without name, modifiers, or return type
 * SYNTAX: (parameters) -> expression or statement block
 * 
 * EXAMPLES:
 * Traditional                        Lambda
 * ===========                        ======
 * () -> System.out.println("Hi")     // No parameters
 * x -> x * 2                         // Single parameter
 * (a, b) -> a + b                    // Multiple parameters
 * x -> { return x * x; }             // Block body
 * 
 * RULES:
 * 1. {} optional for single statements
 * 2. Parameter types optional (type inference)
 * 3. () optional for single parameters
 * 4. 'return' optional for expressions
 * 
 * ============================================================================
 * 2. FUNCTIONAL INTERFACES
 * ============================================================================
 * 
 * DEFINITION: Interface with exactly ONE abstract method (SAM)
 * 
 * FEATURES:
 * - Can have default methods
 * - Can have static methods
 * - @FunctionalInterface annotation (optional but recommended)
 * 
 * EXAMPLES:
 * - Runnable: run()
 * - Callable: call()
 * - Comparator: compare()
 * 
 * CUSTOM EXAMPLE:
 * @FunctionalInterface
 * interface Calculator {
 *     int calculate(int a, int b);  // abstract method
 *     
 *     default void info() {         // default method allowed
 *         System.out.println("Calculator");
 *     }
 * }
 * Calculator add = (a, b) -> a + b;
 * 
 * ============================================================================
 * 3. METHOD REFERENCES
 * ============================================================================
 * 
 * DEFINITION: Shorthand for lambda expressions using :: operator
 * 
 * TYPES:
 * 1. Static:           ClassName::methodName
 * 2. Instance:         object::methodName
 * 3. Constructor:      ClassName::new
 * 4. Arbitrary Object: ClassName::methodName
 * 
 * EXAMPLES:
 * Lambda                          Method Reference
 * ======                          ================
 * s -> Integer.parseInt(s)        Integer::parseInt
 * () -> new ArrayList()           ArrayList::new
 * s -> s.toUpperCase()            String::toUpperCase
 * x -> System.out.println(x)      System.out::println
 * 
 * ============================================================================
 * 4. PREDEFINED FUNCTIONAL INTERFACES (java.util.function)
 * ============================================================================
 * 
 * 1. PREDICATE<T>
 * Method: boolean test(T t)
 * Purpose: Conditional checking
 * Example: Predicate<Integer> isEven = x -> x % 2 == 0;
 * 
 * 2. FUNCTION<T, R>
 * Method: R apply(T t)
 * Purpose: Transform input to output
 * Example: Function<String, Integer> length = String::length;
 * 
 * 3. CONSUMER<T>
 * Method: void accept(T t)
 * Purpose: Accept input, perform operation
 * Example: Consumer<String> printer = System.out::println;
 * 
 * 4. SUPPLIER<T>
 * Method: T get()
 * Purpose: Supply value without input
 * Example: Supplier<LocalDate> today = LocalDate::now;
 * 
 * BI-VARIANTS:
 * - BiPredicate<T, U>: boolean test(T t, U u)
 * - BiFunction<T, U, R>: R apply(T t, U u)
 * - BiConsumer<T, U>: void accept(T t, U u)
 * 
 * ============================================================================
 * 5. STREAM API
 * ============================================================================
 * 
 * DEFINITION: Functional approach to process collections
 * 
 * CHARACTERISTICS:
 * - No storage (wrapper around data)
 * - Functional operations
 * - Lazy evaluation
 * - Consumable (use once)
 * 
 * CREATION:
 * - collection.stream()
 * - Stream.of(values...)
 * - Arrays.stream(array)
 * 
 * INTERMEDIATE OPERATIONS (Return Stream):
 * - filter(Predicate)    - Select elements
 * - map(Function)        - Transform elements
 * - sorted()             - Sort elements
 * - distinct()           - Remove duplicates
 * - limit(n)             - Limit elements
 * - skip(n)              - Skip elements
 * 
 * TERMINAL OPERATIONS (Return Result):
 * - collect(Collector)   - Collect to collection
 * - forEach(Consumer)    - Process each
 * - reduce(BinaryOperator) - Reduce to single value
 * - count()              - Count elements
 * - min()/max()          - Find min/max
 * - anyMatch/allMatch/noneMatch()
 * - findFirst()/findAny()
 * 
 * EXAMPLES:
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
 * 
 * // Filter even numbers
 * List<Integer> evens = numbers.stream()
 *     .filter(n -> n % 2 == 0)
 *     .collect(Collectors.toList());
 * 
 * // Sum of squares of even numbers
 * int sum = numbers.stream()
 *     .filter(n -> n % 2 == 0)
 *     .map(n -> n * n)
 *     .reduce(0, Integer::sum);
 * 
 * ============================================================================
 * 6. OPTIONAL CLASS
 * ============================================================================
 * 
 * PURPOSE: Handle null values elegantly, avoid NullPointerException
 * 
 * CREATION:
 * - Optional.of(value)        - Non-null value
 * - Optional.ofNullable(value) - Possibly null
 * - Optional.empty()          - Empty optional
 * 
 * KEY METHODS:
 * - isPresent()              - Check if value present
 * - get()                    - Get value (unsafe)
 * - orElse(default)          - Get value or default
 * - orElseGet(supplier)      - Get value or from supplier
 * - map(function)            - Transform if present
 * - filter(predicate)        - Filter if present
 * - ifPresent(consumer)      - Execute if present
 * 
 * EXAMPLES:
 * Optional<String> name = Optional.ofNullable(getName());
 * 
 * // Safe retrieval
 * String result = name.orElse("Unknown");
 * 
 * // Conditional execution
 * name.ifPresent(System.out::println);
 * 
 * // Transformation
 * Optional<Integer> length = name.map(String::length);
 * 
 * ============================================================================
 * 7. DATE & TIME API (JSR 310)
 * ============================================================================
 * 
 * KEY CLASSES:
 * - LocalDate      - Date only
 * - LocalTime      - Time only
 * - LocalDateTime  - Date and time
 * - ZonedDateTime  - Date, time with timezone
 * - Period         - Date-based duration
 * - Duration       - Time-based duration
 * 
 * ADVANTAGES:
 * - Immutable objects
 * - Thread-safe
 * - Clear API
 * - Better performance
 * 
 * EXAMPLES:
 * // Current date/time
 * LocalDate today = LocalDate.now();
 * LocalTime now = LocalTime.now();
 * LocalDateTime dateTime = LocalDateTime.now();
 * 
 * // Specific date
 * LocalDate birthday = LocalDate.of(1990, Month.JANUARY, 15);
 * 
 * // Calculations
 * LocalDate nextWeek = today.plusWeeks(1);
 * Period age = Period.between(birthday, today);
 * 
 * // Formatting
 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 * String formatted = today.format(formatter);
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: What are Java 8's main features?
 * A: Lambda expressions, Stream API, Functional interfaces, Method references,
 *    Default methods, Date/Time API, Optional class
 * 
 * Q2: What is a lambda expression?
 * A: Anonymous function enabling functional programming. 
 *    Syntax: (parameters) -> expression
 * 
 * Q3: What is a functional interface?
 * A: Interface with exactly one abstract method. Examples: Runnable, Callable
 * 
 * Q4: Difference between map() and filter()?
 * A: map() transforms elements 1:1, filter() selects based on condition
 * 
 * Q5: What is method reference?
 * A: Shorthand for lambda using :: operator. Four types: static, instance,
 *    constructor, arbitrary object method
 * 
 * Q6: Intermediate vs terminal operations?
 * A: Intermediate: lazy, return Stream (filter, map)
 *    Terminal: eager, return result (collect, forEach)
 * 
 * Q7: When to use parallel streams?
 * A: Large datasets, CPU-intensive tasks, thread-safe operations
 * 
 * Q8: Purpose of Optional?
 * A: Handle null values elegantly, avoid NullPointerException
 * 
 * Q9: Benefits of new Date/Time API?
 * A: Immutable, thread-safe, clear API, timezone support
 * 
 * Q10: What are default methods?
 * A: Interface methods with implementation using 'default' keyword.
 *     Provides backward compatibility
 * 
 * ============================================================================
 * KEY POINTS FOR INTERVIEWS
 * ============================================================================
 * 
 * PERFORMANCE:
 * - Streams overhead for small collections
 * - Parallel streams have their own overhead
 * - Method references more efficient than lambdas
 * - Use primitive streams (IntStream) to avoid boxing
 * 
 * BEST PRACTICES:
 * - Keep lambdas simple and readable
 * - Use Optional for return types, not fields
 * - Prefer method references when possible
 * - Use new Date/Time API over legacy
 * - Be careful with parallel streams
 * 
 * COMMON PITFALLS:
 * - Optional.get() without isPresent() check
 * - Modifying collections during stream operations
 * - Overusing lambdas where traditional code is clearer
 * - Using parallel streams for I/O operations
 * 
 * ============================================================================
 */

public class Java8Features {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA 8 FEATURES - INTERVIEW GUIDE ===");
        System.out.println("Clean, concise documentation for technical interviews");
        
        // Quick examples of each feature
        demonstrateJava8Features();
    }
    
    private static void demonstrateJava8Features() {
        System.out.println("\n1. Lambda Expression:");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(name -> System.out.println("  " + name));
        
        System.out.println("\n2. Method Reference:");
        names.forEach(System.out::println);
        
        System.out.println("\n3. Stream API:");
        List<Integer> evens = Arrays.asList(1, 2, 3, 4, 5, 6)
                .stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("  Even numbers: " + evens);
        
        System.out.println("\n4. Optional:");
        Optional<String> optional = Optional.of("Hello World");
        optional.map(String::toUpperCase)
                .ifPresent(s -> System.out.println("  " + s));
        
        System.out.println("\n5. Functional Interfaces:");
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println("  Is 4 even? " + isEven.test(4));
        
        System.out.println("\n6. Date/Time API:");
        LocalDate today = LocalDate.now();
        System.out.println("  Today: " + today);
        
        System.out.println("\nAll Java 8 features demonstrated!");
    }
}
