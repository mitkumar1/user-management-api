package com.kumar.wipro.api.features;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * ============================================================================
 *                 JAVA 8 FEATURES - COMPREHENSIVE INTERVIEW GUIDE
 * ============================================================================
 * 
 * Author: Java 8 Interview Preparation
 * Version: 2.0 (Standardized)
 * Purpose: Complete Java 8 features guide for technical interviews
 * 
 * FEATURES COVERED:
 * 1. Lambda Expressions
 * 2. Functional Interfaces
 * 3. Method References
 * 4. Default & Static Methods in Interfaces
 * 5. Predefined Functional Interfaces
 * 6. Stream API
 * 7. Date & Time API (JSR 310)
 * 8. Optional Class
 * 9. Practical Interview Examples
 * 10. Common Interview Questions
 * 
 * ============================================================================
 */
public class JavaFeaturesComparison {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA 8 FEATURES - INTERVIEW PREPARATION ===\n");
        
        JavaFeaturesComparison guide = new JavaFeaturesComparison();
        guide.demonstrateAllFeatures();
    }
    
    public void demonstrateAllFeatures() {
        System.out.println("Starting Java 8 Features Demonstration...\n");
        
        // 1. Lambda Expressions
        demonstrateLambdas();
        
        // 2. Functional Interfaces
        demonstrateFunctionalInterfaces();
        
        // 3. Method References
        demonstrateMethodReferences();
        
        // 4. Predefined Functional Interfaces
        demonstrateBuiltInFunctionalInterfaces();
        
        // 5. Stream API
        demonstrateStreams();
        
        // 6. Date Time API
        demonstrateDateTimeAPI();
        
        // 7. Optional Class
        demonstrateOptional();
        
        System.out.println("Java 8 Features demonstration completed!");
    }
    
    // ============================================================================
    // 1. LAMBDA EXPRESSIONS
    // ============================================================================
    
    private void demonstrateLambdas() {
        System.out.println("1. LAMBDA EXPRESSIONS");
        System.out.println("=====================");
        
        // Traditional approach vs Lambda approach
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        
        System.out.println("Traditional forEach:");
        for (String name : names) {
            System.out.println("  " + name);
        }
        
        System.out.println("\nLambda forEach:");
        names.forEach(name -> System.out.println("  " + name));
        
        System.out.println("\nMethod Reference forEach:");
        names.forEach(System.out::println);
        
        // Lambda syntax examples
        System.out.println("\nLambda Syntax Examples:");
        System.out.println("() -> System.out.println(\"No parameters\")");
        System.out.println("x -> x * 2 (Single parameter)");
        System.out.println("(x, y) -> x + y (Multiple parameters)");
        System.out.println("(String s) -> s.length() (With type)");
        System.out.println("x -> { return x * x; } (Block body)");
        
        System.out.println();
    }
    
    // ============================================================================
    // 2. FUNCTIONAL INTERFACES
    // ============================================================================
    
    @FunctionalInterface
    interface MathOperation {
        int operate(int a, int b);
        
        // Default methods allowed
        default void printResult(int result) {
            System.out.println("Result: " + result);
        }
        
        // Static methods allowed
        static void info() {
            System.out.println("Math Operation Interface");
        }
    }
    
    @FunctionalInterface
    interface StringProcessor {
        String process(String input);
    }
    
    private void demonstrateFunctionalInterfaces() {
        System.out.println("2. FUNCTIONAL INTERFACES");
        System.out.println("=========================");
        
        // Custom functional interfaces
        MathOperation addition = (a, b) -> a + b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation subtraction = (a, b) -> a - b;
        
        System.out.println("Custom Functional Interface Examples:");
        addition.printResult(addition.operate(10, 5));
        multiplication.printResult(multiplication.operate(10, 5));
        subtraction.printResult(subtraction.operate(10, 5));
        
        MathOperation.info();
        
        // String processing
        StringProcessor toUpperCase = s -> s.toUpperCase();
        StringProcessor addPrefix = s -> "Processed: " + s;
        
        System.out.println("\nString Processing:");
        System.out.println(toUpperCase.process("hello world"));
        System.out.println(addPrefix.process("test string"));
        
        System.out.println();
    }
    
    // ============================================================================
    // 3. METHOD REFERENCES
    // ============================================================================
    
    private void demonstrateMethodReferences() {
        System.out.println("3. METHOD REFERENCES");
        System.out.println("====================");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
        
        // 1. Static Method Reference
        System.out.println("Static Method Reference (String::valueOf):");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
               .map(String::valueOf)
               .forEach(s -> System.out.println("  " + s));
        
        // 2. Instance Method Reference on specific object
        System.out.println("\nInstance Method Reference:");
        PrintStream out = System.out;
        words.forEach(out::println);
        
        // 3. Constructor Reference
        System.out.println("\nConstructor Reference:");
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> newList = listSupplier.get();
        newList.add("Created using constructor reference");
        System.out.println("  " + newList.get(0));
        
        // 4. Arbitrary Object Method Reference
        System.out.println("\nArbitrary Object Method Reference:");
        words.stream()
             .map(String::toUpperCase)
             .forEach(s -> System.out.println("  " + s));
        
        System.out.println();
    }
    
    // ============================================================================
    // 4. PREDEFINED FUNCTIONAL INTERFACES
    // ============================================================================
    
    private void demonstrateBuiltInFunctionalInterfaces() {
        System.out.println("4. PREDEFINED FUNCTIONAL INTERFACES");
        System.out.println("===================================");
        
        // Predicate<T> - boolean test(T t)
        System.out.println("Predicate<T> Examples:");
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<String> startsWith = s -> s.startsWith("A");
        System.out.println("  Is 4 even? " + isEven.test(4));
        System.out.println("  Does 'Apple' start with 'A'? " + startsWith.test("Apple"));
        
        // Function<T, R> - R apply(T t)
        System.out.println("\nFunction<T, R> Examples:");
        Function<String, Integer> stringLength = String::length;
        Function<Integer, String> numberToString = Object::toString;
        System.out.println("  Length of 'Hello': " + stringLength.apply("Hello"));
        System.out.println("  Number 42 to string: " + numberToString.apply(42));
        
        // Consumer<T> - void accept(T t)
        System.out.println("\nConsumer<T> Examples:");
        Consumer<String> printer = s -> System.out.println("  Consumed: " + s);
        Consumer<List<String>> listCleaner = List::clear;
        printer.accept("Test String");
        
        // Supplier<T> - T get()
        System.out.println("\nSupplier<T> Examples:");
        Supplier<String> randomString = () -> "Random-" + (int)(Math.random() * 100);
        Supplier<LocalDateTime> currentTime = LocalDateTime::now;
        System.out.println("  Generated: " + randomString.get());
        System.out.println("  Current time: " + currentTime.get());
        
        // BiFunction<T, U, R>
        System.out.println("\nBiFunction<T, U, R> Examples:");
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
        System.out.println("  5 + 3 = " + add.apply(5, 3));
        System.out.println("  Concatenated: " + concat.apply("Hello ", "World"));
        
        System.out.println();
    }
    
    // ============================================================================
    // 5. STREAM API
    // ============================================================================
    
    private void demonstrateStreams() {
        System.out.println("5. STREAM API");
        System.out.println("=============");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // Intermediate Operations
        System.out.println("Intermediate Operations:");
        
        // Filter + Map + Collect
        List<Integer> evenSquares = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println("  Even numbers squared: " + evenSquares);
        
        // Sorted + Distinct
        List<String> sortedNames = names.stream()
                .sorted()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("  Sorted names: " + sortedNames);
        
        // Terminal Operations
        System.out.println("\nTerminal Operations:");
        
        // reduce()
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("  Sum of all numbers: " + sum);
        
        // count()
        long count = numbers.stream()
                .filter(n -> n > 5)
                .count();
        System.out.println("  Numbers greater than 5: " + count);
        
        // min() and max()
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        min.ifPresent(value -> System.out.println("  Minimum: " + value));
        max.ifPresent(value -> System.out.println("  Maximum: " + value));
        
        // anyMatch(), allMatch(), noneMatch()
        boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
        System.out.println("  Any even? " + anyEven);
        System.out.println("  All positive? " + allPositive);
        System.out.println("  None negative? " + noneNegative);
        
        // Grouping and Partitioning
        System.out.println("\nGrouping and Partitioning:");
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("  Partitioned by even/odd: " + partitioned);
        
        Map<Integer, List<String>> groupedByLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("  Grouped by length: " + groupedByLength);
        
        System.out.println();
    }
    
    // ============================================================================
    // 6. DATE & TIME API
    // ============================================================================
    
    private void demonstrateDateTimeAPI() {
        System.out.println("6. DATE & TIME API (JSR 310)");
        System.out.println("=============================");
        
        // Current date and time
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        
        System.out.println("Current Date/Time:");
        System.out.println("  Today: " + today);
        System.out.println("  Current time: " + now);
        System.out.println("  Current date-time: " + dateTime);
        System.out.println("  Zoned date-time: " + zonedDateTime);
        
        // Creating specific dates
        LocalDate specificDate = LocalDate.of(2024, Month.DECEMBER, 25);
        LocalTime specificTime = LocalTime.of(14, 30, 45);
        LocalDateTime specificDateTime = LocalDateTime.of(2024, Month.DECEMBER, 25, 14, 30, 45);
        
        System.out.println("\nSpecific Date/Time:");
        System.out.println("  Christmas 2024: " + specificDate);
        System.out.println("  Meeting time: " + specificTime);
        System.out.println("  Specific date-time: " + specificDateTime);
        
        // Date calculations
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate lastMonth = today.minusMonths(1);
        LocalDate nextYear = today.plusYears(1);
        
        System.out.println("\nDate Calculations:");
        System.out.println("  Next week: " + nextWeek);
        System.out.println("  Last month: " + lastMonth);
        System.out.println("  Next year: " + nextYear);
        
        // Formatting and parsing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formatted = dateTime.format(formatter);
        System.out.println("\nFormatting:");
        System.out.println("  Formatted: " + formatted);
        
        // Period and Duration
        LocalDate birthday = LocalDate.of(1990, Month.JANUARY, 15);
        Period age = Period.between(birthday, today);
        Duration duration = Duration.between(LocalTime.of(9, 0), LocalTime.of(17, 30));
        
        System.out.println("\nPeriod and Duration:");
        System.out.println("  Age: " + age.getYears() + " years, " + 
                          age.getMonths() + " months, " + age.getDays() + " days");
        System.out.println("  Work duration: " + duration.toHours() + " hours");
        
        System.out.println();
    }
    
    // ============================================================================
    // 7. OPTIONAL CLASS
    // ============================================================================
    
    private void demonstrateOptional() {
        System.out.println("7. OPTIONAL CLASS");
        System.out.println("=================");
        
        // Creating Optional objects
        Optional<String> name = Optional.of("John Doe");
        Optional<String> empty = Optional.empty();
        Optional<String> nullable = Optional.ofNullable(null);
        Optional<String> nullableWithValue = Optional.ofNullable("Valid Value");
        
        System.out.println("Optional Creation:");
        System.out.println("  Name present: " + name.isPresent());
        System.out.println("  Empty present: " + empty.isPresent());
        System.out.println("  Nullable present: " + nullable.isPresent());
        System.out.println("  Nullable with value present: " + nullableWithValue.isPresent());
        
        // Safe value retrieval
        System.out.println("\nSafe Value Retrieval:");
        String nameValue = name.orElse("Unknown");
        String emptyValue = empty.orElse("Default Value");
        String computedValue = empty.orElseGet(() -> "Computed: " + System.currentTimeMillis());
        
        System.out.println("  Name or default: " + nameValue);
        System.out.println("  Empty or default: " + emptyValue);
        System.out.println("  Empty or computed: " + computedValue);
        
        // Transformation with map()
        System.out.println("\nTransformation:");
        Optional<Integer> nameLength = name.map(String::length);
        Optional<String> upperName = name.map(String::toUpperCase);
        
        nameLength.ifPresent(length -> System.out.println("  Name length: " + length));
        upperName.ifPresent(upper -> System.out.println("  Upper name: " + upper));
        
        // Filtering
        System.out.println("\nFiltering:");
        Optional<String> longName = name.filter(n -> n.length() > 5);
        Optional<String> shortName = name.filter(n -> n.length() <= 5);
        
        System.out.println("  Long name present: " + longName.isPresent());
        System.out.println("  Short name present: " + shortName.isPresent());
        
        // Conditional execution
        System.out.println("\nConditional Execution:");
        name.ifPresent(n -> System.out.println("  Processing name: " + n));
        empty.ifPresent(n -> System.out.println("  This won't print"));
        
        // Exception handling
        try {
            String value = empty.orElseThrow(() -> new RuntimeException("Value not present"));
        } catch (RuntimeException e) {
            System.out.println("  Caught exception: " + e.getMessage());
        }
        
        System.out.println();
    }
}

/*
 * ============================================================================
 *                    INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: What are the key features introduced in Java 8?
 * A: Lambda expressions, Stream API, Functional interfaces, Method references,
 *    Default and static methods in interfaces, Date/Time API (JSR 310),
 *    Optional class, CompletableFuture, Nashorn JavaScript engine
 * 
 * Q2: What is a lambda expression and what are its benefits?
 * A: Lambda expression is an anonymous function that enables functional 
 *    programming. Benefits: concise code, improved readability, functional
 *    programming support, better collection processing, parallel processing
 * 
 * Q3: What is a functional interface?
 * A: Interface with exactly one abstract method (SAM). Can have default and
 *    static methods. Examples: Runnable, Callable, Comparator. Required for
 *    lambda expressions. @FunctionalInterface annotation ensures compile-time safety.
 * 
 * Q4: Explain the difference between map() and flatMap().
 * A: map() transforms each element 1:1 (Stream<T> -> Stream<R>)
 *    flatMap() transforms and flattens nested structures (Stream<T> -> Stream<R>)
 *    Example: map() for simple transformation, flatMap() for nested collections
 * 
 * Q5: What are method references and their types?
 * A: Shorthand notation for lambda expressions using :: operator
 *    Types: 1) Static method (Class::method)
 *           2) Instance method (object::method)
 *           3) Constructor (Class::new)
 *           4) Arbitrary object method (Class::method)
 * 
 * Q6: Difference between intermediate and terminal operations in Stream API?
 * A: Intermediate operations: lazy evaluation, return Stream, chainable
 *    (filter, map, sorted, distinct, limit, skip)
 *    Terminal operations: trigger execution, return result, end pipeline
 *    (collect, forEach, reduce, count, min, max, anyMatch, allMatch)
 * 
 * Q7: When should you use parallel streams?
 * A: Use for: large datasets (>1000 elements), CPU-intensive operations,
 *    stateless operations, when thread safety is ensured
 *    Avoid for: small datasets, I/O operations, stateful operations
 * 
 * Q8: What is the purpose of Optional class?
 * A: Handle potentially null values elegantly, avoid NullPointerException,
 *    explicit null handling, functional programming style, better API design
 * 
 * Q9: Advantages of new Date/Time API over legacy Date/Calendar?
 * A: Immutable objects, thread-safe, clear API design, timezone support,
 *    better performance, comprehensive operations, null-safe, fluent interface
 * 
 * Q10: What are default methods in interfaces?
 * A: Methods with implementation in interfaces using 'default' keyword.
 *     Purpose: backward compatibility, multiple inheritance of behavior,
 *     evolution of interfaces without breaking existing implementations
 * 
 * ============================================================================
 *                         TECHNICAL INTERVIEW TIPS
 * ============================================================================
 * 
 * CODING BEST PRACTICES:
 * 1. Use functional interfaces for lambda expressions
 * 2. Prefer method references over lambda expressions when possible
 * 3. Use Optional for return types, not fields or parameters
 * 4. Keep lambda expressions simple and readable
 * 5. Use new Date/Time API instead of legacy Date/Calendar
 * 6. Be cautious with parallel streams - measure performance
 * 7. Use primitive streams (IntStream, LongStream) to avoid boxing
 * 8. Chain stream operations for readability
 * 9. Use appropriate collectors for stream termination
 * 10. Handle exceptions properly in lambda expressions
 * 
 * PERFORMANCE CONSIDERATIONS:
 * 1. Streams have overhead - traditional loops may be faster for small data
 * 2. Parallel streams have their own overhead - use for large datasets
 * 3. Method references are more efficient than lambda expressions
 * 4. Primitive streams avoid boxing/unboxing costs
 * 5. Stateless operations perform better in parallel streams
 * 6. Consider memory usage with large stream operations
 * 7. Use lazy evaluation benefits of intermediate operations
 * 8. Avoid creating unnecessary objects in lambda expressions
 * 
 * COMMON PITFALLS TO AVOID:
 * 1. Don't use Optional.get() without checking isPresent()
 * 2. Don't use Optional for fields or method parameters
 * 3. Don't modify collections during stream operations
 * 4. Don't use parallel streams for I/O operations
 * 5. Don't ignore exceptions in lambda expressions
 * 6. Don't overuse lambda expressions - readability matters
 * 7. Don't assume parallel streams are always faster
 * 8. Don't use streams for simple operations where loops are clearer
 * 
 * ============================================================================
 */
