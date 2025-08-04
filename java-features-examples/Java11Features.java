import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * ============================================================================
 *                 JAVA 11 FEATURES - INTERVIEW PREPARATION GUIDE
 * ============================================================================
 * 
 * Released: September 2018 (LTS - Long Term Support Version)
 * Oracle JDK becomes commercial, OpenJDK becomes primary reference
 * 
 * KEY REASONS FOR JAVA 11:
 * 1. LTS support for enterprise adoption
 * 2. Modularization cleanup (removed deprecated modules)
 * 3. Performance improvements and new APIs
 * 4. Developer productivity enhancements
 * 
 * MAJOR FEATURES:
 * 1. New String Methods (developer productivity)
 * 2. HTTP Client API (standardized HTTP/2 support)
 * 3. Files API enhancements (simplified file operations)
 * 4. Optional improvements (better null handling)
 * 5. Predicate.not() (readable code)
 * 6. Local Variable Syntax for Lambda (consistency)
 * 7. Nest-based Access Control (performance)
 * 8. Dynamic Class-File Constants (framework optimization)
 * 
 * REMOVED/DEPRECATED:
 * - Java EE modules (javax.xml.bind, etc.)
 * - CORBA modules
 * - Nashorn JavaScript Engine (deprecated)
 * 
 * ============================================================================
 * 1. NEW STRING METHODS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - trim() doesn't handle Unicode whitespace properly
 * - Need better text processing for modern applications
 * - Improve string manipulation productivity
 * 
 * NEW METHODS:
 * - isBlank(): Checks if string is empty or only whitespace
 * - strip(): Better than trim(), handles Unicode whitespace
 * - stripLeading(): Remove leading whitespace
 * - stripTrailing(): Remove trailing whitespace
 * - lines(): Returns Stream<String> of lines
 * - repeat(int): Repeat string n times
 * 
 * INTERVIEW Q&A:
 * Q: Difference between trim() and strip()?
 * A: strip() handles Unicode whitespace, trim() only handles ASCII
 * 
 * Q: When to use isBlank() vs isEmpty()?
 * A: isEmpty() checks length=0, isBlank() checks empty OR only whitespace
 * 
 * ============================================================================
 * 2. HTTP CLIENT API (Standard)
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Replace legacy HttpURLConnection (complex, HTTP/1.1 only)
 * - Built-in HTTP/2 support
 * - Async programming support
 * - Modern, fluent API design
 * 
 * KEY FEATURES:
 * - HTTP/2 support by default
 * - Synchronous and asynchronous operations
 * - WebSocket support
 * - Request/Response builders
 * - CompletableFuture integration
 * 
 * BASIC USAGE:
 * HttpClient client = HttpClient.newHttpClient();
 * HttpRequest request = HttpRequest.newBuilder(URI.create("url")).build();
 * HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
 * 
 * INTERVIEW Q&A:
 * Q: Advantages over HttpURLConnection?
 * A: HTTP/2, async support, fluent API, better performance
 * 
 * ============================================================================
 * 3. FILES API ENHANCEMENTS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Simplify common file operations
 * - Reduce boilerplate code for reading/writing strings
 * - Better integration with NIO.2
 * 
 * NEW METHODS:
 * - Files.readString(Path): Read entire file as String
 * - Files.writeString(Path, String): Write String to file
 * 
 * BEFORE JAVA 11:
 * String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
 * 
 * JAVA 11:
 * String content = Files.readString(path);
 * 
 * ============================================================================
 * 4. OPTIONAL IMPROVEMENTS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Make Optional more convenient
 * - Reduce boilerplate code
 * - Better API consistency
 * 
 * NEW METHODS:
 * - isEmpty(): Opposite of isPresent() (more readable)
 * - orElseThrow(): No parameter version (throws NoSuchElementException)
 * 
 * USAGE:
 * if (optional.isEmpty()) { ... }  // More readable than !isPresent()
 * String value = optional.orElseThrow();  // Cleaner than orElseThrow(supplier)
 * 
 * ============================================================================
 * 5. PREDICATE.NOT() METHOD
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Improve readability of negated predicates
 * - Better method reference usage
 * - Functional programming convenience
 * 
 * BEFORE:
 * list.stream().filter(s -> !s.isBlank())
 * 
 * JAVA 11:
 * list.stream().filter(Predicate.not(String::isBlank))
 * 
 * BENEFIT: More readable with method references
 * 
 * ============================================================================
 * 6. LOCAL VARIABLE SYNTAX FOR LAMBDA (var in lambda)
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Consistency with local variable type inference (var)
 * - Enable annotations on lambda parameters
 * - Uniform syntax across language
 * 
 * USAGE:
 * list.stream().map((var s) -> s.toUpperCase())  // Consistent with var usage
 * list.stream().map((@NonNull var s) -> s.trim())  // Enables annotations
 * 
 * NOTE: Mainly useful when you need annotations on parameters
 * 
 * ============================================================================
 * 7. NEST-BASED ACCESS CONTROL
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Improve performance by eliminating synthetic bridge methods
 * - Better JVM-level access control
 * - Reduce class file size
 * 
 * DEFINITION:
 * - Classes in same nest can access each other's private members
 * - JVM-level feature, transparent to developers
 * - Replaces synthetic accessor methods
 * 
 * BENEFIT: Better performance, smaller bytecode
 * 
 * ============================================================================
 * 8. DYNAMIC CLASS-FILE CONSTANTS
 * ============================================================================
 * 
 * WHY INTRODUCED:
 * - Enable more efficient constant pool usage
 * - Support for framework optimizations
 * - Better JVM performance for dynamic languages
 * 
 * DEFINITION:
 * - JVM feature allowing constants to be computed at link time
 * - Used by frameworks, not directly by developers
 * - Enables lambda metafactory optimizations
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: Why is Java 11 important?
 * A: LTS version, modularization cleanup, performance improvements, new APIs
 * 
 * Q2: Main difference between Java 8 and Java 11?
 * A: Java 11 focuses on API enhancements, performance, modularization.
 *    Java 8 introduced fundamental changes (lambdas, streams)
 *    Java 17 introduced simplicity, safety, and expressiveness with sealed classes, pattern matching, and switch expressions.
 * Q3: What was removed in Java 11?
 * A: Java EE modules, CORBA, Nashorn JavaScript engine (deprecated)
 * 
 * Q4: Advantage of new HTTP Client over HttpURLConnection?
 * A: HTTP/2 support, async operations, fluent API, better performance
 * 
 * Q5: When to use strip() vs trim()?
 * A: strip() for Unicode whitespace, trim() for ASCII only
 * 
 * Q6: Purpose of Predicate.not()?
 * A: Improve readability when negating method references
 * 
 * Q7: What is nest-based access control?
 * A: JVM feature eliminating synthetic bridge methods for better performance
 * 
 * Q8: Why var in lambda parameters?
 * A: Consistency with type inference and enable parameter annotations
 * 
 * ============================================================================
 * KEY POINTS FOR INTERVIEWS
 * ============================================================================
 * 
 * MIGRATION CONSIDERATIONS:
 * - Java EE modules removed (need external dependencies)
 * - Oracle JDK becomes commercial
 * - Module system more mature
 * 
 * PERFORMANCE IMPROVEMENTS:
 * - Better startup time
 * - Improved G1GC
 * - ZGC and Epsilon GC (experimental)
 * 
 * ENTERPRISE ADOPTION:
 * - LTS support crucial for enterprise
 * - OpenJDK becomes primary reference
 * - Better tooling and profiling
 * 
 * ============================================================================
 */

public class Java11Features {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA 11 FEATURES - INTERVIEW GUIDE ===");
        System.out.println("LTS Version with API enhancements and performance improvements");
        
        // Demonstrate key features with minimal examples
        demonstrateFeatures();
    }
    
    private static void demonstrateFeatures() {
        // 1. String Methods
        System.out.println("\n1. String Methods:");
        System.out.println("   '   '.isBlank() = " + "   ".isBlank());
        System.out.println("   'Hello'.repeat(2) = " + "Hello".repeat(2));
        
        // 2. Optional improvements
        System.out.println("\n2. Optional Methods:");
        Optional<String> empty = Optional.empty();
        System.out.println("   empty.isEmpty() = " + empty.isEmpty());
        
        // 3. Predicate.not()
        System.out.println("\n3. Predicate.not():");
        List<String> words = List.of("hello", "", "world", "   ");
        List<String> nonBlank = words.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());
        System.out.println("   Non-blank words: " + nonBlank);
        
        // 4. Var in lambda
        System.out.println("\n4. Var in Lambda:");
        List<String> names = List.of("Alice", "Bob");
        names.stream()
                .map((var name) -> name.toUpperCase())
                .forEach(name -> System.out.println("   " + name));
        
        // 5. Files API (demonstration without actual file operations)
        System.out.println("\n5. Files API:");
        System.out.println("   Files.readString(path) - reads entire file");
        System.out.println("   Files.writeString(path, content) - writes string to file");
        
        // 6. HTTP Client API (demonstration without network call)
        System.out.println("\n6. HTTP Client API:");
        System.out.println("   HttpClient.newHttpClient() - creates HTTP/2 client");
        System.out.println("   Supports sync/async operations");
        
        System.out.println("\nAll Java 11 features demonstrated!");
    }
}
