import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/*
 * ============================================================================
 *                 JAVA 17 FEATURES - INTERVIEW PREPARATION GUIDE
 * ============================================================================
 * 
 * Released: September 2021 (LTS - Long Term Support Version)
 * Major milestone with modern language features and performance improvements
 * 
 * KEY REASONS FOR JAVA 17:
 * 1. LTS support for enterprise adoption (after Java 11)
 * 2. Pattern matching and modern language features
 * 3. Sealed classes for better domain modeling
 * 4. Performance improvements and new APIs
 * 5. Enhanced security and tooling
 * 
 * MAJOR FEATURES:
 * 1. Sealed Classes (domain modeling)
 * 2. Pattern Matching for instanceof (cleaner code)
 * 3. Records (data carriers)
 * 4. Text Blocks (multiline strings)
 * 5. Switch Expressions (functional style)
 * 6. New Random Generator API
 * 7. Enhanced Pseudo-Random Number Generators
 * 8. Foreign Function & Memory API (Incubator)
 * 9. Vector API (Incubator)
 * 10. Context-Specific Deserialization Filters
 * 
 * REMOVED/DEPRECATED:
 * - Applet API (removed)
 * - Security Manager (deprecated for removal)
 * - RMI Activation (removed)
 * 
 * ============================================================================
 * 1. SEALED CLASSES
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Control class inheritance hierarchy
 * - Better domain modeling with restricted inheritance
 * - Enable exhaustive pattern matching
 * - Improve API design and maintainability
 * 
 * DEFINITION:
 * - Classes/interfaces that restrict which classes can extend/implement them
 * - Declared with 'sealed' keyword
 * - Must specify permitted subclasses with 'permits' clause
 * - Subclasses must be: final, sealed, or non-sealed
 * 
 * EXAMPLE:
 * public sealed class Shape permits Circle, Rectangle, Triangle {
 *     // base class implementation
 * }
 * 
 * public final class Circle extends Shape { ... }
 * public final class Rectangle extends Shape { ... }
 * public non-sealed class Triangle extends Shape { ... }
 * 
 * INTERVIEW Q&A:
 * Q: Purpose of sealed classes?
 * A: Control inheritance, enable exhaustive pattern matching, better domain modeling
 * 
 * Q: Difference between sealed, final, and non-sealed?
 * A: sealed = can have restricted subclasses, final = no subclasses, 
 *    non-sealed = opens hierarchy again
 * 
 * ============================================================================
 * 2. PATTERN MATCHING FOR INSTANCEOF
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Eliminate redundant casting after instanceof checks
 * - Make code more readable and less error-prone
 * - Foundation for future pattern matching features
 * 
 * BEFORE JAVA 17:
 * if (obj instanceof String) {
 *     String s = (String) obj;
 *     System.out.println(s.length());
 * }
 * 
 * JAVA 17:
 * if (obj instanceof String s) {
 *     System.out.println(s.length());  // s is automatically cast
 * }
 * 
 * BENEFITS:
 * - Eliminates explicit casting
 * - Reduces boilerplate code
 * - Type-safe pattern variable
 * 
 * INTERVIEW Q&A:
 * Q: What is pattern matching for instanceof?
 * A: Combines instanceof check with automatic casting in single expression
 * 
 * ============================================================================
 * 3. RECORDS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Reduce boilerplate for data carrier classes
 * - Immutable data containers by design
 * - Better than traditional POJOs for pure data
 * 
 * DEFINITION:
 * - Special class for immutable data carriers
 * - Automatically generates: constructor, getters, equals(), hashCode(), toString()
 * - Cannot extend other classes (but can implement interfaces)
 * - All fields are final
 * 
 * EXAMPLE:
 * public record Person(String name, int age) {
 *     // Compact constructor for validation
 *     public Person {
 *         if (age < 0) throw new IllegalArgumentException();
 *     }
 * }
 * 
 * USAGE:
 * Person p = new Person("John", 25);
 * System.out.println(p.name());  // auto-generated accessor
 * 
 * INTERVIEW Q&A:
 * Q: Advantages of records over regular classes?
 * A: Less boilerplate, immutable by design, auto-generated methods
 * 
 * Q: Can records have methods?
 * A: Yes, instance methods, static methods, but cannot have instance fields
 * 
 * ============================================================================
 * 4. TEXT BLOCKS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Better support for multiline strings
 * - Improve readability of embedded languages (JSON, SQL, HTML)
 * - Reduce escape sequence complexity
 * 
 * SYNTAX:
 * String json = """
 *     {
 *         "name": "John",
 *         "age": 30
 *     }
 *     """;
 * 
 * FEATURES:
 * - Triple quotes (""") for multiline strings
 * - Automatic indentation management
 * - Escape sequences still work: \n, \t, \"
 * - Incidental whitespace is removed
 * 
 * INTERVIEW Q&A:
 * Q: Benefits of text blocks?
 * A: Better multiline string handling, improved readability, less escaping
 * 
 * ============================================================================
 * 5. SWITCH EXPRESSIONS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Make switch more functional and expression-oriented
 * - Eliminate fall-through issues
 * - Enable pattern matching in future versions
 * 
 * TRADITIONAL SWITCH:
 * String result;
 * switch (day) {
 *     case MONDAY: result = "Start of week"; break;
 *     case FRIDAY: result = "End of week"; break;
 *     default: result = "Midweek";
 * }
 * 
 * SWITCH EXPRESSION:
 * String result = switch (day) {
 *     case MONDAY -> "Start of week";
 *     case FRIDAY -> "End of week";
 *     default -> "Midweek";
 * };
 * 
 * FEATURES:
 * - Arrow syntax (->) prevents fall-through
 * - Can return values directly
 * - Exhaustiveness checking
 * - yield keyword for complex blocks
 * 
 * INTERVIEW Q&A:
 * Q: Difference between switch statement and expression?
 * A: Expression returns value, statement executes code. Expression prevents fall-through
 * 
 * ============================================================================
 * 6. NEW RANDOM GENERATOR API
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Replace legacy java.util.Random limitations
 * - Support multiple algorithms
 * - Better performance and quality
 * - Pluggable architecture
 * 
 * NEW API:
 * - RandomGenerator interface (base)
 * - RandomGeneratorFactory for creating instances
 * - Multiple algorithms: L32X64MixRandom, Xoshiro256PlusPlus, etc.
 * 
 * USAGE:
 * RandomGenerator rng = RandomGeneratorFactory.of("L32X64MixRandom").create();
 * int random = rng.nextInt(100);
 * 
 * BENEFITS:
 * - Better statistical quality
 * - Higher performance
 * - Algorithm flexibility
 * 
 * ============================================================================
 * 7. FOREIGN FUNCTION & MEMORY API (INCUBATOR)
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Replace JNI with safer, easier native code interaction
 * - Direct memory access without GC overhead
 * - Better performance for native libraries
 * 
 * PURPOSE:
 * - Call native functions efficiently
 * - Allocate and manage off-heap memory
 * - Interoperate with native libraries
 * 
 * NOTE: Incubator feature, API may change
 * 
 * ============================================================================
 * 8. VECTOR API (INCUBATOR)
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - SIMD (Single Instruction, Multiple Data) operations
 * - Better performance for mathematical computations
 * - Machine learning and scientific computing support
 * 
 * PURPOSE:
 * - Vectorized operations on arrays
 * - Hardware acceleration
 * - Parallel processing of data
 * 
 * NOTE: Incubator feature for performance-critical applications
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: Why is Java 17 significant?
 * A: LTS version with modern language features, sealed classes, records, 
 *    pattern matching, performance improvements
 * 
 * Q2: Main language features in Java 17?
 * A: Sealed classes, pattern matching for instanceof, records, text blocks,
 *    switch expressions
 * 
 * Q3: What are sealed classes used for?
 * A: Control inheritance hierarchy, enable exhaustive pattern matching,
 *    better domain modeling
 * 
 * Q4: Benefits of records over traditional classes?
 * A: Less boilerplate, immutable by design, auto-generated methods,
 *    better data modeling
 * 
 * Q5: How do switch expressions differ from statements?
 * A: Expressions return values, prevent fall-through, more functional style
 * 
 * Q6: Purpose of text blocks?
 * A: Better multiline string handling, embedded languages support,
 *    improved readability
 * 
 * Q7: What is pattern matching for instanceof?
 * A: Combines type check with automatic casting, reduces boilerplate
 * 
 * Q8: New Random API advantages?
 * A: Multiple algorithms, better performance, pluggable architecture
 * 
 * ============================================================================
 * KEY POINTS FOR INTERVIEWS
 * ============================================================================
 * 
 * MIGRATION BENEFITS:
 * - From Java 11: Modern language features, better APIs
 * - Performance improvements
 * - Enhanced security
 * - Better tooling support
 * 
 * ENTERPRISE ADOPTION:
 * - LTS support crucial
 * - Modern language features improve productivity
 * - Better maintainability with sealed classes and records
 * 
 * PERFORMANCE:
 * - Improved garbage collection
 * - Better startup time
 * - Enhanced JIT compilation
 * - New algorithms and APIs
 * 
 * DEVELOPMENT PRODUCTIVITY:
 * - Less boilerplate with records
 * - Cleaner code with pattern matching
 * - Better string handling with text blocks
 * - More expressive switch expressions
 * 
 * ============================================================================
 */

public class Java17FeaturesInterviewGuide {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA 17 FEATURES - INTERVIEW GUIDE ===");
        System.out.println("LTS Version with modern language features and performance improvements");
        
        // Demonstrate key features with minimal examples
        demonstrateFeatures();
    }
    
    private static void demonstrateFeatures() {
        // 1. Pattern Matching for instanceof
        System.out.println("\n1. Pattern Matching for instanceof:");
        Object obj = "Hello World";
        if (obj instanceof String s) {
            System.out.println("   String length: " + s.length());
        }
        
        // 2. Text Blocks
        System.out.println("\n2. Text Blocks:");
        String json = """
            {
                "name": "Java",
                "version": 17
            }
            """;
        System.out.println("   JSON: " + json.trim());
        
        // 3. Switch Expressions
        System.out.println("\n3. Switch Expressions:");
        String day = "MONDAY";
        String result = switch (day) {
            case "MONDAY" -> "Start of week";
            case "FRIDAY" -> "End of week";
            default -> "Midweek";
        };
        System.out.println("   " + day + " is " + result);
        
        // 4. Records demonstration
        System.out.println("\n4. Records:");
        Person person = new Person("Alice", 30);
        System.out.println("   Person: " + person);
        System.out.println("   Name: " + person.name() + ", Age: " + person.age());
        
        // 5. New Random Generator API
        System.out.println("\n5. New Random Generator API:");
        RandomGenerator rng = RandomGeneratorFactory.getDefault().create();
        System.out.println("   Random number: " + rng.nextInt(100));
        
        // 6. Sealed Classes demonstration
        System.out.println("\n6. Sealed Classes:");
        Shape circle = new Circle(5.0);
        System.out.println("   Shape area: " + calculateArea(circle));
        
        System.out.println("\nAll Java 17 features demonstrated!");
    }
    
    // Record example
    public record Person(String name, int age) {
        // Compact constructor for validation
        public Person {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
        }
    }
    
    // Sealed classes example
    public static sealed class Shape permits Circle, Rectangle {
        // Base class for shapes
    }
    
    public static final class Circle extends Shape {
        private final double radius;
        
        public Circle(double radius) {
            this.radius = radius;
        }
        
        public double getRadius() {
            return radius;
        }
    }
    
    public static final class Rectangle extends Shape {
        private final double width, height;
        
        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        
        public double getWidth() { return width; }
        public double getHeight() { return height; }
    }
    
    // Pattern matching with sealed classes (traditional instanceof)
    private static double calculateArea(Shape shape) {
        if (shape instanceof Circle c) {
            return Math.PI * c.getRadius() * c.getRadius();
        } else if (shape instanceof Rectangle r) {
            return r.getWidth() * r.getHeight();
        }
        throw new IllegalArgumentException("Unknown shape");
    }
}
