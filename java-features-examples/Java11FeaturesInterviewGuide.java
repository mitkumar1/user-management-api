/*
 * ============================================================================
 *                 JAVA 11 FEATURES - INTERVIEW PREPARATION GUIDE
 * ============================================================================
 *
 * Version: 1.0 (Clean & Concise for Interview Preparation)
 *
 * CORE JAVA 11 FEATURES (including Java 9 & 10):
 * 1. Local-Variable Type Inference (var)
 * 2. New String, Collection, and Optional methods
 * 3. Files API enhancements
 * 4. HTTP Client API
 * 5. Lambda improvements (var in lambda)
 * 6. Flight Recorder & low-overhead profiling
 * 7. Removal of deprecated APIs (Applets, Java EE modules)
 * 8. Unicode 10, TLS 1.3, platform updates
 *
 * PURPOSE:
 * - Modernize Java syntax
 * - Improve developer productivity
 * - Enhance performance and security
 * - Support cloud-native and reactive programming
 *
 * ============================================================================
 * 1. LOCAL-VARIABLE TYPE INFERENCE (var)
 * ============================================================================
 *
 * Introduced in Java 10, improved in Java 11 (lambda parameters)
 *
 * Example:
 * var list = new ArrayList<String>(); // infers ArrayList<String>
 * var stream = list.stream();         // infers Stream<String>
 *
 * // var in lambda
 * BiFunction<Integer, Integer, Integer> add = (var a, var b) -> a + b;
 *
 * Diagram:
 * [var keyword] -> [Compiler infers type] -> [Type safety preserved]
 *
 * ============================================================================
 * 2. STRING, COLLECTION, OPTIONAL, FILES API ENHANCEMENTS
 * ============================================================================
 *
 * String methods:
 * - isBlank(), lines(), strip(), repeat(), stripLeading(), stripTrailing()
 * Example:
 * "  hello  ".strip(); // "hello"
 * "abc\nxyz".lines().count(); // 2
 * "ha".repeat(3); // "hahaha"
 *
 * Collection/Optional:
 * - List.of(), Set.of(), Map.of() (immutable collections)
 * - Optional.isEmpty(), Optional.orElseThrow()
 * Example:
 * Optional<String> opt = Optional.empty();
 * opt.isEmpty(); // true
 *
 * Files API:
 * - Files.readString(), Files.writeString()
 * Example:
 * String content = Files.readString(Path.of("file.txt"));
 * Files.writeString(Path.of("out.txt"), content);
 *
 * ============================================================================
 * 3. HTTP CLIENT API (java.net.http)
 * ============================================================================
 *
 * Modern, non-blocking HTTP/2 client
 *
 * Example:
 * HttpClient client = HttpClient.newHttpClient();
 * HttpRequest request = HttpRequest.newBuilder()
 *     .uri(URI.create("https://api.example.com"))
 *     .build();
 * HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
 * System.out.println(response.body());
 *
 * Diagram:
 * [HttpClient] -> [HttpRequest] -> [send()/sendAsync()] -> [HttpResponse]
 *
 * ============================================================================
 * 4. FLIGHT RECORDER & PROFILING
 * ============================================================================
 *
 * Java Flight Recorder (JFR) is a low-overhead profiling tool built into the JVM.
 * Enables production monitoring and diagnostics.
 *
 * Example:
 * java -XX:StartFlightRecording=duration=60s,filename=recording.jfr
 *
 * ============================================================================
 * 5. REMOVAL OF DEPRECATED APIS
 * ============================================================================
 *
 * Java 11 removed Applets, Java EE modules (jaxb, jws, etc.), CORBA, and other legacy APIs.
 * Use external libraries if needed.
 *
 * ============================================================================
 * 6. PLATFORM UPDATES
 * ============================================================================
 *
 * - Unicode 10 support
 * - TLS 1.3 enabled by default
 * - Enhanced garbage collection (ZGC, Epsilon)
 * - Improved startup and performance
 *
 * ============================================================================
 * ADVANCED INTERVIEW SCENARIOS & Q&A
 * ============================================================================
 *
 * Q: How does var improve code readability and maintainability?
 * A: Reduces boilerplate, but use with care for clarity. Avoid for ambiguous types.
 *
 * Q: How do you use the new String methods for data cleaning?
 * A: Use strip(), isBlank(), and lines() for whitespace and line processing.
 *
 * Q: How do you use the HTTP Client for async calls?
 * A: client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body)
 *
 * Q: How do you migrate code using removed Java EE modules?
 * A: Add dependencies via Maven/Gradle (e.g., javax.xml.bind:jaxb-api)
 *
 * Q: How do you use Optional.isEmpty() in stream pipelines?
 * A: Stream<Optional<String>>.filter(Optional::isEmpty)
 *
 * Q: How do you use Files API for efficient file I/O?
 * A: Files.readString()/writeString() for simple text file operations
 *
 * Q: How do you profile a Java 11 app in production?
 * A: Use Java Flight Recorder (JFR) for low-overhead monitoring
 *
 * ============================================================================
 * COMMON PITFALLS & BEST PRACTICES
 * ============================================================================
 * - Use var only when type is obvious
 * - Prefer immutable collections (List.of, Set.of)
 * - Use new String/Optional methods for cleaner code
 * - Use HTTP Client for modern REST calls
 * - Profile with JFR, avoid legacy APIs
 * - Document migration from removed modules
 *
 * ============================================================================
 */
public class Java11FeaturesInterviewGuide {
    public static void main(String[] args) throws Exception {
        // Quick demo of Java 11 features
        var list = List.of("Java", "11", "Features");
        list.forEach(System.out::println);

        var text = "  Java 11  \nNew Features  ";
        System.out.println(text.strip());
        System.out.println(text.lines().count());
        System.out.println("ha".repeat(3));

        Optional<String> opt = Optional.empty();
        System.out.println(opt.isEmpty());

        // HTTP Client demo
        var client = java.net.http.HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder()
            .uri(java.net.URI.create("https://www.example.com"))
            .build();
        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }
}

