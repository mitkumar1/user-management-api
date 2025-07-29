# Core Java Concepts - Interview Preparation Guide
## Object-Oriented Programming, Multithreading & Advanced Features

---

## Table of Contents
1. [Object-Oriented Programming (OOP)](#object-oriented-programming-oop)
2. [Class Loader](#class-loader)
3. [Method Overloading & Overriding](#method-overloading--overriding)
4. [Abstract Classes vs Interfaces](#abstract-classes-vs-interfaces)
5. [Object Class in Java](#object-class-in-java)
6. [Cloning and Types](#cloning-and-types)
7. [Exception Handling](#exception-handling)
8. [Threading Fundamentals](#threading-fundamentals)
9. [Thread Lifecycle](#thread-lifecycle)
10. [Thread Implementation](#thread-implementation)
11. [Synchronization](#synchronization)
12. [Semaphore and Deadlock](#semaphore-and-deadlock)
13. [Multithreading and Concurrency](#multithreading-and-concurrency)
14. [Executor Services](#executor-services)
15. [Virtual Threads (Java 21)](#virtual-threads-java-21)
16. [Collections Framework](#collections-framework)

---

## Object-Oriented Programming (OOP)

### **What is OOP?**
Object-Oriented Programming is a programming paradigm based on the concept of objects that contain data (attributes) and code (methods).

### **Types of OOP Concepts:**

**1. Encapsulation:**
- **What:** Bundling data and methods together, hiding internal details
- **How:** Using private fields with public getter/setter methods
```
CLASS BankAccount:
    PRIVATE balance
    PRIVATE accountNumber
    
    PUBLIC METHOD getBalance(): RETURN balance
    PUBLIC METHOD deposit(amount): 
        IF amount > 0: balance += amount
    PUBLIC METHOD withdraw(amount):
        IF amount <= balance: balance -= amount
```

**2. Inheritance:**
- **What:** Creating new classes based on existing classes
- **How:** Child class inherits properties and methods from parent
```
CLASS Animal:
    METHOD eat(): PRINT "Animal eating"
    METHOD sleep(): PRINT "Animal sleeping"

CLASS Dog EXTENDS Animal:
    METHOD bark(): PRINT "Dog barking"
    METHOD eat(): PRINT "Dog eating" // Override parent method
```

**3. Polymorphism:**
- **What:** Same interface, different implementations
- **Types:** Compile-time (overloading) and Runtime (overriding)
```
INTERFACE Shape:
    METHOD calculateArea()

CLASS Circle IMPLEMENTS Shape:
    radius
    METHOD calculateArea(): RETURN 3.14 * radius * radius

CLASS Rectangle IMPLEMENTS Shape:
    length, width
    METHOD calculateArea(): RETURN length * width

// Usage - same method, different behavior
shapes = [NEW Circle(5), NEW Rectangle(4, 6)]
FOR EACH shape IN shapes:
    PRINT shape.calculateArea() // Different implementations called
```

**4. Abstraction:**
- **What:** Hiding complex implementation details, showing only essential features
- **How:** Using abstract classes and interfaces
```
ABSTRACT CLASS PaymentProcessor:
    ABSTRACT METHOD processPayment(amount)
    
    METHOD validatePayment(amount): // Common implementation
        IF amount <= 0: THROW "Invalid amount"

CLASS CreditCardProcessor EXTENDS PaymentProcessor:
    METHOD processPayment(amount):
        validatePayment(amount)
        // Credit card specific processing
```

---

## Class Loader

### **What is Class Loader?**
Class Loader is a part of JVM that loads class files into memory. It's responsible for loading, linking, and initializing classes.

### **Types of Class Loaders:**

**1. Bootstrap Class Loader:**
- **Purpose:** Loads core Java classes (java.lang, java.util, etc.)
- **Location:** Part of JVM, written in native code
```
PSEUDO_PROCESS BootstrapClassLoader:
    METHOD loadClass(className):
        IF className.startsWith("java."):
            LOAD from JVM core libraries
            RETURN loaded class
```

**2. Extension Class Loader:**
- **Purpose:** Loads classes from extension directories
- **Location:** $JAVA_HOME/lib/ext
```
PSEUDO_PROCESS ExtensionClassLoader:
    METHOD loadClass(className):
        IF className NOT found in Bootstrap:
            SEARCH in extension directories
            RETURN loaded class OR delegate to parent
```

**3. Application Class Loader:**
- **Purpose:** Loads classes from application classpath
- **Location:** Application's classpath
```
PSEUDO_PROCESS ApplicationClassLoader:
    METHOD loadClass(className):
        IF className NOT found in parent loaders:
            SEARCH in application classpath
            RETURN loaded class OR throw ClassNotFoundException
```

**Class Loading Process:**
1. **Loading:** Find and load class bytecode
2. **Linking:** Verify, prepare, and resolve
3. **Initialization:** Execute static initializers

---

## Method Overloading & Overriding

### **Method Overloading (Compile-time Polymorphism):**
- **What:** Multiple methods with same name but different parameters
- **When:** Compile time decision based on method signature

```
CLASS Calculator:
    METHOD add(a, b): RETURN a + b                    // 2 int parameters
    METHOD add(a, b, c): RETURN a + b + c             // 3 int parameters  
    METHOD add(a, b): RETURN a + b                    // 2 double parameters
    METHOD add(numbers[]): 
        sum = 0
        FOR EACH num IN numbers: sum += num
        RETURN sum                                     // Array parameter

// Usage - compiler decides which method to call
calculator.add(5, 10)        // Calls first method
calculator.add(5, 10, 15)    // Calls second method
calculator.add([1,2,3,4])    // Calls fourth method
```

### **Method Overriding (Runtime Polymorphism):**
- **What:** Child class provides specific implementation of parent class method
- **When:** Runtime decision based on actual object type

```
CLASS Animal:
    METHOD makeSound(): PRINT "Animal makes sound"

CLASS Dog EXTENDS Animal:
    METHOD makeSound(): PRINT "Dog barks"        // Override

CLASS Cat EXTENDS Animal:
    METHOD makeSound(): PRINT "Cat meows"        // Override

// Usage - runtime decides which method to call
Animal animal1 = NEW Dog()
Animal animal2 = NEW Cat()
animal1.makeSound()  // Prints "Dog barks" - runtime decision
animal2.makeSound()  // Prints "Cat meows" - runtime decision
```

**Key Differences:**
- **Overloading:** Same class, different parameters, compile-time
- **Overriding:** Different classes (inheritance), same signature, runtime

---

## Abstract Classes vs Interfaces

### **Abstract Classes:**
- **What:** Classes that cannot be instantiated, may have abstract and concrete methods
- **When:** When you want to share code among related classes

```
ABSTRACT CLASS Vehicle:
    PRIVATE String brand          // Concrete field
    
    CONSTRUCTOR(brand):          // Constructor allowed
        this.brand = brand
    
    METHOD getBrand():           // Concrete method
        RETURN brand
    
    ABSTRACT METHOD start()      // Abstract method - must implement
    ABSTRACT METHOD stop()       // Abstract method - must implement

CLASS Car EXTENDS Vehicle:
    CONSTRUCTOR(brand):
        SUPER(brand)
    
    METHOD start(): PRINT "Car engine started"
    METHOD stop(): PRINT "Car engine stopped"
```

### **Interfaces:**
- **What:** Contract that defines what a class must do, not how
- **When:** When you want to achieve multiple inheritance of type

```
INTERFACE Flyable:
    METHOD fly()                 // Abstract method (implicit)
    
    DEFAULT METHOD glide():      // Default method (Java 8+)
        PRINT "Gliding through air"
    
    STATIC METHOD getMaxHeight(): // Static method (Java 8+)
        RETURN 10000

INTERFACE Swimmable:
    METHOD swim()

CLASS Duck IMPLEMENTS Flyable, Swimmable:  // Multiple inheritance
    METHOD fly(): PRINT "Duck flying"
    METHOD swim(): PRINT "Duck swimming"
```

**Key Differences:**
| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Instantiation | Cannot instantiate | Cannot instantiate |
| Methods | Abstract + Concrete | Abstract + Default + Static |
| Fields | Any type | public static final only |
| Constructor | Can have | Cannot have |
| Inheritance | Single inheritance | Multiple inheritance |
| Access Modifiers | Any | public (implicit) |

---

## Object Class in Java

### **What is Object Class?**
The root class of all Java classes. Every class inherits from Object class directly or indirectly.

### **Key Methods in Object Class:**

```
CLASS Object:
    
    METHOD toString():
        RETURN className + "@" + hashCode()
    
    METHOD equals(obj):
        RETURN this == obj                    // Reference equality
    
    METHOD hashCode():
        RETURN memory address hash            // Native implementation
    
    METHOD clone():
        RETURN shallow copy of object         // Must implement Cloneable
    
    METHOD getClass():
        RETURN Class object                   // Runtime type information
    
    METHOD finalize():
        // Called by garbage collector before object destruction
    
    METHOD wait():
        // Thread waits until notify() called
    
    METHOD notify():
        // Wakes up single waiting thread
    
    METHOD notifyAll():
        // Wakes up all waiting threads

// Every class automatically inherits these methods
CLASS Student:
    name, age
    
    METHOD toString():                        // Override Object's toString
        RETURN "Student{name=" + name + ", age=" + age + "}"
    
    METHOD equals(obj):                       // Override Object's equals
        IF obj IS NOT Student: RETURN false
        Student other = (Student) obj
        RETURN this.name.equals(other.name) AND this.age == other.age
```

---

## Cloning and Types

### **What is Cloning?**
Creating an exact copy of an object. The original and cloned objects are separate instances.

### **Types of Cloning:**

**1. Shallow Cloning:**
- **What:** Copies object's primitive fields and references (not referenced objects)
- **How:** Default clone() method behavior

```
CLASS Student IMPLEMENTS Cloneable:
    name                    // String reference
    age                     // primitive
    address                 // Address object reference
    
    METHOD clone():
        RETURN SUPER.clone()  // Shallow clone
        
CLASS Address:
    street, city

// Usage
originalStudent = NEW Student("John", 20, NEW Address("Main St", "NYC"))
clonedStudent = originalStudent.clone()

// Result: 
// - clonedStudent.age is independent copy
// - clonedStudent.name points to same String (immutable, so safe)
// - clonedStudent.address points to SAME Address object (shared!)

clonedStudent.address.city = "Boston"  // Changes both students' address!
```

**2. Deep Cloning:**
- **What:** Copies object and all referenced objects recursively
- **How:** Manual implementation or serialization

```
CLASS Student IMPLEMENTS Cloneable:
    name, age, address
    
    METHOD clone():                           // Deep clone implementation
        clonedStudent = (Student) SUPER.clone()
        clonedStudent.address = this.address.clone()  // Clone referenced object
        RETURN clonedStudent

CLASS Address IMPLEMENTS Cloneable:
    street, city
    
    METHOD clone():
        RETURN NEW Address(this.street, this.city)

// Alternative: Using Serialization for Deep Clone
METHOD deepClone(object):
    // Serialize object to bytes
    bytes = serialize(object)
    // Deserialize bytes to new object
    RETURN deserialize(bytes)
```

**3. Copy Constructor Pattern:**
- **What:** Constructor that creates copy of existing object
- **How:** Preferred approach over clone()

```
CLASS Student:
    name, age, address
    
    CONSTRUCTOR(other):                       // Copy constructor
        this.name = other.name
        this.age = other.age
        this.address = NEW Address(other.address)  // Deep copy

// Usage
originalStudent = NEW Student("John", 20, address)
copiedStudent = NEW Student(originalStudent)      // Clean, explicit copying
```

---

## Exception Handling

### **What are Exceptions?**
Exceptions are runtime errors that disrupt normal program flow. Java provides mechanism to handle these errors gracefully.

### **Types of Exceptions:**

**1. Checked Exceptions:**
- **What:** Compile-time checked exceptions that must be handled
- **Examples:** IOException, SQLException, ClassNotFoundException

```
METHOD readFile(fileName):
    TRY:
        file = openFile(fileName)        // May throw IOException
        content = file.read()
        RETURN content
    CATCH IOException e:
        PRINT "Error reading file: " + e.message
        RETURN null
    FINALLY:
        IF file IS NOT null:
            file.close()                 // Always executed
```

**2. Unchecked Exceptions (Runtime Exceptions):**
- **What:** Runtime exceptions that don't need explicit handling
- **Examples:** NullPointerException, ArrayIndexOutOfBoundsException

```
METHOD divide(a, b):
    IF b == 0:
        THROW NEW ArithmeticException("Division by zero")
    RETURN a / b

METHOD processArray(array, index):
    TRY:
        RETURN array[index]              // May throw ArrayIndexOutOfBoundsException
    CATCH ArrayIndexOutOfBoundsException e:
        PRINT "Invalid index: " + index
        RETURN null
```

**3. Errors:**
- **What:** Serious problems that applications shouldn't handle
- **Examples:** OutOfMemoryError, StackOverflowError

```
// Errors typically shouldn't be caught
METHOD recursiveMethod():
    recursiveMethod()                    // Eventually throws StackOverflowError
```

### **Exception Hierarchy:**
```
Throwable
├── Error (OutOfMemoryError, StackOverflowError)
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   └── IllegalArgumentException
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        └── ClassNotFoundException
```

### **Exception Handling Keywords:**

**What are Exception Handling Keywords?**
Java provides five keywords for exception handling: try, catch, throw, throws, and finally. These keywords work together to handle exceptional situations gracefully.

**1. try:**
- **What:** Block of code that might throw an exception
- **Purpose:** Contains risky code that needs monitoring
```
METHOD riskyOperation():
    TRY:
        // Code that might throw exception
        file = openFile("data.txt")
        data = file.read()
        number = Integer.parseInt(data)     // May throw NumberFormatException
        result = 100 / number               // May throw ArithmeticException
```

**2. catch:**
- **What:** Block that handles specific exception types
- **Purpose:** Provides recovery mechanism for exceptions
```
METHOD handleExceptions():
    TRY:
        performRiskyOperation()
    CATCH NumberFormatException e:
        PRINT "Invalid number format: " + e.getMessage()
        RETURN defaultValue()
    CATCH ArithmeticException e:
        PRINT "Math error: " + e.getMessage()
        RETURN 0
    CATCH Exception e:                      // Generic catch - should be last
        PRINT "Unexpected error: " + e.getMessage()
        RETURN null
```

**3. throw:**
- **What:** Keyword to manually throw an exception
- **Purpose:** Create and throw custom exceptions
```
METHOD validateAge(age):
    IF age < 0:
        THROW NEW IllegalArgumentException("Age cannot be negative: " + age)
    IF age > 150:
        THROW NEW IllegalArgumentException("Age too high: " + age)

METHOD withdraw(amount):
    IF amount <= 0:
        THROW NEW IllegalArgumentException("Amount must be positive")
    IF amount > balance:
        THROW NEW InsufficientFundsException("Insufficient balance: " + balance)
    balance -= amount
```

**4. throws:**
- **What:** Keyword in method signature declaring exceptions method might throw
- **Purpose:** Propagate exceptions to calling method
```
METHOD readFile(fileName) THROWS IOException, FileNotFoundException:
    FileReader file = NEW FileReader(fileName)  // May throw FileNotFoundException
    BufferedReader reader = NEW BufferedReader(file)
    content = reader.readLine()                 // May throw IOException
    RETURN content
    // Caller must handle these exceptions

METHOD processFile(fileName):
    TRY:
        content = readFile(fileName)            // Must handle declared exceptions
        processContent(content)
    CATCH FileNotFoundException e:
        PRINT "File not found: " + fileName
    CATCH IOException e:
        PRINT "Error reading file: " + e.getMessage()
```

**5. finally:**
- **What:** Block that always executes regardless of exception occurrence
- **Purpose:** Cleanup code (close files, connections, etc.)
```
METHOD processFileWithCleanup(fileName):
    FileReader file = null
    TRY:
        file = NEW FileReader(fileName)
        data = file.read()
        processData(data)
    CATCH IOException e:
        PRINT "Error: " + e.getMessage()
    FINALLY:
        // Always executes - even if exception occurs
        IF file != null:
            TRY:
                file.close()                   // Cleanup
            CATCH IOException e:
                PRINT "Error closing file: " + e.getMessage()
```

**Exception Handling Flow:**
```
TRY Block Execution:
    IF no exception: Execute try → finally → continue
    IF exception thrown: Execute try (until exception) → matching catch → finally → continue
    IF exception not caught: Execute try → finally → propagate exception

FINALLY Block:
    - Always executes (except System.exit() or JVM crash)
    - Executes even if return statement in try/catch
    - Executes even if another exception thrown in catch
```

### **Try-With-Resources (Java 7+):**

**What is Try-With-Resources?**
Automatic resource management that ensures resources are closed automatically, even if exceptions occur.

**Why Try-With-Resources was Introduced?**

**1. Problem with Traditional Approach:**
```
// Traditional approach - verbose and error-prone
METHOD readFileTraditional(fileName):
    FileReader file = null
    BufferedReader reader = null
    TRY:
        file = NEW FileReader(fileName)
        reader = NEW BufferedReader(file)
        content = reader.readLine()
        RETURN content
    CATCH IOException e:
        PRINT "Error: " + e.getMessage()
        RETURN null
    FINALLY:
        // Nested try-catch for cleanup - messy!
        IF reader != null:
            TRY:
                reader.close()
            CATCH IOException e:
                PRINT "Error closing reader"
        IF file != null:
            TRY:
                file.close()
            CATCH IOException e:
                PRINT "Error closing file"
```

**Problems with Traditional Approach:**
- **Verbose Code:** Lots of boilerplate for cleanup
- **Error Prone:** Easy to forget resource cleanup
- **Exception Suppression:** Cleanup exceptions hide original exceptions
- **Resource Leaks:** If cleanup code has bugs, resources leak

**2. Try-With-Resources Solution:**
```
// Modern approach - clean and automatic
METHOD readFileModern(fileName):
    TRY WITH RESOURCES(
        FileReader file = NEW FileReader(fileName);
        BufferedReader reader = NEW BufferedReader(file)
    ):
        content = reader.readLine()
        RETURN content
    CATCH IOException e:
        PRINT "Error: " + e.getMessage()
        RETURN null
    // Resources automatically closed - no finally needed!
```

**Benefits of Try-With-Resources:**

**1. Automatic Resource Management:**
```
// Resources automatically closed in reverse order
TRY WITH RESOURCES(
    Connection conn = getConnection();           // Closed second
    PreparedStatement stmt = conn.prepareStatement(sql);  // Closed first
    ResultSet rs = stmt.executeQuery()          // Closed third
):
    WHILE rs.next():
        processRow(rs)
// All resources automatically closed here
```

**2. Exception Suppression Handling:**
```
// If both try block and close() throw exceptions
TRY WITH RESOURCES(FileWriter writer = NEW FileWriter("output.txt")):
    writer.write("data")
    THROW NEW RuntimeException("Main exception")    // Primary exception
CATCH RuntimeException e:
    PRINT "Primary: " + e.getMessage()              // Main exception
    Throwable[] suppressed = e.getSuppressed()      // Close() exceptions
    FOR EACH s IN suppressed:
        PRINT "Suppressed: " + s.getMessage()
```

**3. Cleaner Code:**
```
// Multiple resources - clean syntax
METHOD transferData(sourceFile, targetFile):
    TRY WITH RESOURCES(
        FileInputStream input = NEW FileInputStream(sourceFile);
        FileOutputStream output = NEW FileOutputStream(targetFile);
        BufferedInputStream bufferedInput = NEW BufferedInputStream(input);
        BufferedOutputStream bufferedOutput = NEW BufferedOutputStream(output)
    ):
        byte[] buffer = NEW byte[1024]
        int bytesRead
        WHILE (bytesRead = bufferedInput.read(buffer)) != -1:
            bufferedOutput.write(buffer, 0, bytesRead)
    CATCH IOException e:
        PRINT "Transfer failed: " + e.getMessage()
    // All 4 resources automatically closed in reverse order
```

**4. Custom Resources (AutoCloseable Interface):**
```
CLASS DatabaseConnection IMPLEMENTS AutoCloseable:
    PRIVATE connection
    
    CONSTRUCTOR(url):
        connection = DriverManager.getConnection(url)
        PRINT "Connection opened"
    
    METHOD executeQuery(sql):
        RETURN connection.createStatement().executeQuery(sql)
    
    METHOD close():                               // Required by AutoCloseable
        IF connection != null:
            connection.close()
            PRINT "Connection closed"

// Usage with try-with-resources
METHOD queryDatabase():
    TRY WITH RESOURCES(DatabaseConnection db = NEW DatabaseConnection(url)):
        ResultSet results = db.executeQuery("SELECT * FROM users")
        processResults(results)
    CATCH SQLException e:
        PRINT "Database error: " + e.getMessage()
    // DatabaseConnection.close() automatically called
```

**Requirements for Try-With-Resources:**
- Resource must implement `AutoCloseable` interface
- Resource must have `close()` method
- Resources declared in try-with-resources are final

**Benefits Summary:**

| Traditional Approach | Try-With-Resources |
|---------------------|-------------------|
| Manual resource cleanup | Automatic cleanup |
| Verbose finally blocks | No finally needed |
| Exception suppression issues | Proper exception handling |
| Resource leak potential | Guaranteed resource closure |
| Complex nested try-catch | Clean, readable code |
| Error-prone | Less error-prone |

**Real-World Example:**
```
// File processing with multiple resources
METHOD processLargeFile(inputFile, outputFile):
    TRY WITH RESOURCES(
        FileInputStream fis = NEW FileInputStream(inputFile);
        GZIPInputStream gis = NEW GZIPInputStream(fis);
        BufferedReader reader = NEW BufferedReader(NEW InputStreamReader(gis));
        FileOutputStream fos = NEW FileOutputStream(outputFile);
        PrintWriter writer = NEW PrintWriter(fos)
    ):
        String line
        WHILE (line = reader.readLine()) != null:
            processedLine = processLine(line)
            writer.println(processedLine)
    CATCH IOException e:
        PRINT "File processing failed: " + e.getMessage()
        THROW NEW ProcessingException("Unable to process file", e)
    // All 5 resources automatically closed in reverse order
```

### **Try-With-Resources Improvements After Java 7:**

**Java 9 Enhancements - Effectively Final Variables:**

**Problem in Java 7/8:**
- Resources had to be declared within try-with-resources statement
- Could not use existing variables declared outside

```
// Java 7/8 - Resource must be declared in try statement
METHOD processFileJava7(fileName):
    TRY WITH RESOURCES(
        FileReader reader = NEW FileReader(fileName);    // Must declare here
        BufferedReader buffered = NEW BufferedReader(reader)
    ):
        processData(buffered)
    // reader and buffered automatically closed
```

**Java 9 Solution - Effectively Final Resources:**
```
// Java 9+ - Can use effectively final variables
METHOD processFileJava9(fileName):
    FileReader reader = NEW FileReader(fileName)         // Declare outside
    BufferedReader buffered = NEW BufferedReader(reader) // Effectively final
    
    TRY WITH RESOURCES(reader; buffered):               // Use existing variables
        processData(buffered)
    // reader and buffered automatically closed
    
// Effectively final means variables are not reassigned after initialization
METHOD databaseOperationJava9():
    Connection conn = getConnection()                    // Effectively final
    PreparedStatement stmt = conn.prepareStatement(sql)  // Effectively final
    
    TRY WITH RESOURCES(conn; stmt):                     // Reuse existing variables
        ResultSet rs = stmt.executeQuery()
        processResults(rs)
    // conn and stmt automatically closed
```

**Benefits of Java 9 Enhancement:**
- **Code Reuse:** Use same resource variables in multiple try-with-resources
- **Better Readability:** Resources can be initialized with meaningful context
- **Reduced Duplication:** No need to redeclare resources in try statement

```
// Java 9 - Complex Resource Setup
METHOD complexDatabaseOperation():
    // Setup with validation and configuration
    Connection conn = createConfiguredConnection()
    validateConnection(conn)
    PreparedStatement stmt = prepareOptimizedStatement(conn)
    
    TRY WITH RESOURCES(conn; stmt):                     // Reuse configured resources
        executeComplexQuery(stmt)
    
    // Later in same method - reuse same connection for different operation
    PreparedStatement stmt2 = conn.prepareStatement(differentSql)
    TRY WITH RESOURCES(stmt2):                          // conn already closed above
        executeAnotherQuery(stmt2)
```

**Java 10+ - var Keyword with Try-With-Resources:**
```
// Java 10+ - Type inference with var
METHOD processFileJava10(fileName):
    TRY WITH RESOURCES(
        var reader = NEW FileReader(fileName);           // Type inferred
        var buffered = NEW BufferedReader(reader);       // Type inferred
        var scanner = NEW Scanner(buffered)              // Type inferred
    ):
        processWithScanner(scanner)

// Java 10+ with effectively final
METHOD processFileJava10Enhanced(fileName):
    var reader = NEW FileReader(fileName)                // Type inferred, effectively final
    var buffered = NEW BufferedReader(reader)            // Type inferred, effectively final
    
    TRY WITH RESOURCES(reader; buffered):
        processData(buffered)
```

**Java 14+ - Pattern Matching (Preview/Incubator Features):**
```
// Future enhancements - Pattern matching in resource handling
METHOD advancedResourceHandling(resource):
    TRY WITH RESOURCES(resource):
        SWITCH resource:
            CASE FileInputStream fis -> processFile(fis)
            CASE DatabaseConnection db -> processDatabase(db)
            CASE NetworkConnection net -> processNetwork(net)
            DEFAULT -> THROW NEW UnsupportedOperationException()
```

**Enhanced AutoCloseable Interface (Java 8+):**
```
// Java 8+ - Default methods in AutoCloseable implementations
INTERFACE EnhancedCloseable EXTENDS AutoCloseable:
    
    DEFAULT METHOD closeQuietly():                       // Java 8+ default method
        TRY:
            close()
        CATCH Exception e:
            // Log but don't propagate - quiet close
            logger.warn("Error during close: " + e.getMessage())
    
    DEFAULT METHOD closeWithTimeout(seconds):            // Custom close with timeout
        CompletableFuture.runAsync(() -> {
            TRY:
                close()
            CATCH Exception e:
                THROW NEW RuntimeException(e)
        }).get(seconds, TimeUnit.SECONDS)

// Usage with enhanced interface
CLASS SmartFileHandler IMPLEMENTS EnhancedCloseable:
    PRIVATE file
    
    CONSTRUCTOR(fileName):
        file = NEW FileWriter(fileName)
    
    METHOD writeData(data):
        file.write(data)
    
    METHOD close():
        IF file != null:
            file.close()

// Enhanced usage
METHOD smartProcessing():
    handler = NEW SmartFileHandler("output.txt")
    TRY WITH RESOURCES(handler):
        handler.writeData("important data")
    // Can also use handler.closeQuietly() elsewhere if needed
```

**Custom Resource Management Patterns (Java 11+):**
```
// Java 11+ - Resource management with HTTP Client
METHOD modernHttpProcessing():
    httpClient = HttpClient.newHttpClient()
    
    TRY WITH RESOURCES(
        // Custom resource wrapper for HTTP connections
        var connection = NEW HttpConnectionResource(httpClient, url)
    ):
        response = connection.sendRequest(request)
        processResponse(response)

CLASS HttpConnectionResource IMPLEMENTS AutoCloseable:
    PRIVATE httpClient
    PRIVATE connection
    
    CONSTRUCTOR(client, url):
        this.httpClient = client
        this.connection = client.newBuilder().build()
    
    METHOD sendRequest(request):
        RETURN httpClient.send(request, BodyHandlers.ofString())
    
    METHOD close():
        // Cleanup HTTP resources
        IF connection != null:
            connection.shutdown()
```

**Exception Handling Improvements:**
```
// Enhanced exception suppression handling (Java 8+)
METHOD advancedExceptionHandling():
    TRY WITH RESOURCES(
        resource1 = NEW Resource1();
        resource2 = NEW Resource2();
        resource3 = NEW Resource3()
    ):
        processResources(resource1, resource2, resource3)
    CATCH ProcessingException e:
        PRINT "Primary exception: " + e.getMessage()
        
        // Enhanced suppressed exception handling
        Throwable[] suppressed = e.getSuppressed()
        IF suppressed.length > 0:
            PRINT "Suppressed exceptions during resource closure:"
            FOR EACH suppressedException IN suppressed:
                PRINT "  - " + suppressedException.getClass().getSimpleName() + 
                      ": " + suppressedException.getMessage()
                
                // Log detailed stack trace for debugging
                logger.debug("Suppressed exception details", suppressedException)
```

**Performance Improvements (Java 9+):**
```
// Java 9+ - Optimized resource management
CLASS OptimizedResourceManager:
    PRIVATE resourcePool = NEW ConcurrentLinkedQueue()
    
    METHOD getResource():
        resource = resourcePool.poll()
        IF resource == null:
            resource = createNewResource()
        RETURN NEW PooledResource(resource, this)
    
    METHOD returnResource(resource):
        IF isValid(resource):
            resourcePool.offer(resource)
        ELSE:
            resource.destroy()

CLASS PooledResource IMPLEMENTS AutoCloseable:
    PRIVATE actualResource
    PRIVATE manager
    
    CONSTRUCTOR(resource, manager):
        this.actualResource = resource
        this.manager = manager
    
    METHOD close():
        // Return to pool instead of destroying
        manager.returnResource(actualResource)

// Usage with pooled resources
METHOD efficientProcessing():
    manager = NEW OptimizedResourceManager()
    
    TRY WITH RESOURCES(
        resource = manager.getResource()     // Gets from pool
    ):
        processWithResource(resource)
    // Resource returned to pool, not destroyed
```

**Integration with Modern Java Features:**
```
// Java 17+ - Records with try-with-resources
RECORD DatabaseConfig(String url, String username, String password) {}

RECORD ConnectionResource(Connection connection) IMPLEMENTS AutoCloseable:
    METHOD close():
        TRY:
            connection.close()
        CATCH SQLException e:
            THROW NEW RuntimeException("Failed to close connection", e)

METHOD modernDatabaseAccess(DatabaseConfig config):
    TRY WITH RESOURCES(
        var resource = NEW ConnectionResource(
            DriverManager.getConnection(config.url(), config.username(), config.password())
        )
    ):
        processDatabase(resource.connection())

// Java 21+ - Pattern matching with sealed classes
SEALED INTERFACE Resource PERMITS FileResource, DatabaseResource, NetworkResource {}

RECORD FileResource(FileInputStream stream) IMPLEMENTS Resource, AutoCloseable:
    METHOD close(): stream.close()

RECORD DatabaseResource(Connection connection) IMPLEMENTS Resource, AutoCloseable:
    METHOD close(): connection.close()

METHOD processResource(Resource resource):
    TRY WITH RESOURCES(resource):
        SWITCH resource:
            CASE FileResource(var stream) -> processFile(stream)
            CASE DatabaseResource(var conn) -> processDatabase(conn)
            CASE NetworkResource(var socket) -> processNetwork(socket)
```

**Summary of Try-With-Resources Evolution:**

| Java Version | Enhancement | Benefit |
|--------------|-------------|---------|
| Java 7 | Original try-with-resources | Automatic resource management |
| Java 8 | Enhanced AutoCloseable with default methods | More flexible resource handling |
| Java 9 | Effectively final variables | Reuse existing resource variables |
| Java 10 | var keyword support | Type inference for cleaner code |
| Java 11+ | Integration with modern APIs | Better HTTP client, etc. |
| Java 14+ | Pattern matching (preview) | More expressive resource handling |
| Java 17+ | Records integration | Immutable resource wrappers |
| Java 21+ | Sealed classes integration | Type-safe resource management |

**Best Practices for Modern Try-With-Resources:**

1. **Use Effectively Final (Java 9+)** when resources need setup before try block
2. **Combine with var (Java 10+)** for cleaner type inference
3. **Create Resource Wrappers** for complex cleanup logic
4. **Use Suppressed Exception Handling** for better debugging
5. **Consider Resource Pooling** for performance-critical applications
6. **Integrate with Records (Java 17+)** for immutable resource configurations

### **Java 17 & 21 Exception Handling Improvements:**

**Java 17 Enhancements:**

**1. Enhanced NullPointerException Messages:**
```
// Java 17 - More descriptive NPE messages
CLASS User:
    name, address
    
    METHOD getCity():
        RETURN address.city.toUpperCase()    // If address is null
        
// Java 17 NPE Message:
// "Cannot invoke 'String.toUpperCase()' because 'this.address.city' is null"
// Instead of generic: "null"

// Helps identify exact cause of NPE
METHOD processUser(user):
    TRY:
        city = user.getAddress().getCity().trim()
    CATCH NullPointerException e:
        // Java 17 shows: "Cannot invoke 'String.trim()' because 
        // the return value of 'getCity()' is null"
        PRINT "Detailed NPE: " + e.getMessage()
```

**2. Sealed Classes for Exception Hierarchies:**
```
// Java 17 - Sealed exception hierarchies for better type safety
SEALED CLASS PaymentException EXTENDS Exception 
    PERMITS CreditCardException, DebitCardException, WalletException {}

FINAL CLASS CreditCardException EXTENDS PaymentException:
    PRIVATE cardType
    PRIVATE issuer
    
    CONSTRUCTOR(cardType, issuer, message):
        SUPER(message)
        this.cardType = cardType
        this.issuer = issuer

FINAL CLASS DebitCardException EXTENDS PaymentException:
    PRIVATE bankCode
    PRIVATE insufficientFunds
    
    CONSTRUCTOR(bankCode, insufficientFunds, message):
        SUPER(message)
        this.bankCode = bankCode
        this.insufficientFunds = insufficientFunds

NON-SEALED CLASS WalletException EXTENDS PaymentException:
    // Can be extended further

// Exhaustive exception handling with pattern matching
METHOD handlePaymentError(PaymentException e):
    SWITCH e:
        CASE CreditCardException cc -> {
            PRINT "Credit card error: " + cc.getCardType()
            notifyCreditCardIssuer(cc.getIssuer())
        }
        CASE DebitCardException dc -> {
            PRINT "Debit card error: " + dc.getBankCode()
            IF dc.isInsufficientFunds():
                suggestTopUp()
        }
        CASE WalletException we -> {
            PRINT "Wallet error: " + we.getMessage()
            // WalletException can have subtypes
        }
        // Compiler ensures all cases handled
```

**3. Records with Exception Context:**
```
// Java 17 - Records for immutable exception context
RECORD ErrorContext(
    String operationId,
    String userId,
    LocalDateTime timestamp,
    Map<String, Object> metadata
) {}

CLASS BusinessException EXTENDS Exception:
    PRIVATE ErrorContext context
    
    CONSTRUCTOR(message, ErrorContext context):
        SUPER(message)
        this.context = context
    
    METHOD getContext(): RETURN context

// Usage with Records
METHOD processTransaction(transaction):
    errorContext = NEW ErrorContext(
        transaction.getId(),
        transaction.getUserId(),
        LocalDateTime.now(),
        Map.of("amount", transaction.getAmount(), "type", transaction.getType())
    )
    
    TRY:
        validateTransaction(transaction)
        executeTransaction(transaction)
    CATCH ValidationException e:
        THROW NEW BusinessException("Transaction validation failed", errorContext)
    CATCH ExecutionException e:
        THROW NEW BusinessException("Transaction execution failed", errorContext)
```

**Java 21 Enhancements:**

**1. Pattern Matching in Exception Handling:**
```
// Java 21 - Pattern matching with instanceof in catch blocks
METHOD advancedExceptionHandling():
    TRY:
        performComplexOperation()
    CATCH Exception e:
        SWITCH e:
            CASE IOException io WHEN io.getMessage().contains("timeout") -> {
                PRINT "Network timeout: " + io.getMessage()
                scheduleRetry()
            }
            CASE IOException io -> {
                PRINT "General I/O error: " + io.getMessage()
                logError(io)
            }
            CASE SQLException sql WHEN sql.getErrorCode() == 1062 -> {
                PRINT "Duplicate key error"
                handleDuplicateKey(sql)
            }
            CASE SQLException sql -> {
                PRINT "Database error: " + sql.getErrorCode()
                rollbackTransaction()
            }
            CASE RuntimeException rt -> {
                PRINT "Runtime error: " + rt.getClass().getSimpleName()
                alertSupport(rt)
            }
            DEFAULT -> {
                PRINT "Unexpected error: " + e.getClass().getName()
                logUnknownError(e)
            }
```

**2. Enhanced Switch Expressions with Exceptions:**
```
// Java 21 - Switch expressions for exception categorization
METHOD categorizeException(Exception e):
    ErrorSeverity severity = SWITCH e:
        CASE NullPointerException, IllegalArgumentException -> ErrorSeverity.LOW
        CASE IOException, SQLException -> ErrorSeverity.MEDIUM
        CASE OutOfMemoryError, StackOverflowError -> ErrorSeverity.CRITICAL
        CASE SecurityException, AccessControlException -> ErrorSeverity.HIGH
        DEFAULT -> ErrorSeverity.UNKNOWN
    
    processErrorBySeverity(e, severity)

ENUM ErrorSeverity:
    LOW, MEDIUM, HIGH, CRITICAL, UNKNOWN
```

**3. Virtual Threads Exception Handling:**
```
// Java 21 - Exception handling in virtual threads
METHOD virtualThreadExceptionHandling():
    TRY WITH RESOURCES(
        executor = Executors.newVirtualThreadPerTaskExecutor()
    ):
        List<Future<String>> futures = NEW ArrayList()
        
        FOR i = 1 TO 1000000:
            future = executor.submit(() -> {
                TRY:
                    RETURN performIOOperation(i)
                CATCH IOException e:
                    // Exception in virtual thread
                    THROW NEW CompletionException("Virtual thread IO error", e)
            })
            futures.add(future)
        
        // Collect results and handle exceptions
        FOR future IN futures:
            TRY:
                result = future.get()
                processResult(result)
            CATCH ExecutionException e:
                Throwable cause = e.getCause()
                IF cause INSTANCEOF CompletionException:
                    handleVirtualThreadException(cause)
```

**4. Structured Concurrency Exception Propagation:**
```
// Java 21 - Structured concurrency with exception handling
METHOD structuredConcurrencyExceptions():
    TRY WITH RESOURCES(
        scope = NEW StructuredTaskScope.ShutdownOnFailure()
    ):
        // Fork multiple tasks
        Future<String> task1 = scope.fork(() -> fetchUserData())
        Future<String> task2 = scope.fork(() -> fetchPreferences())
        Future<String> task3 = scope.fork(() -> fetchHistory())
        
        // Wait for all to complete or first failure
        scope.join()
        
        // Check for failures and propagate
        scope.throwIfFailed(e -> NEW ServiceException("Concurrent operation failed", e))
        
        // All tasks succeeded
        userData = task1.resultNow()
        preferences = task2.resultNow()
        history = task3.resultNow()
        
        RETURN combineResults(userData, preferences, history)
```

**5. String Templates with Exception Messages (Preview):**
```
// Java 21 - String templates for better exception messages
METHOD enhancedExceptionMessages(userId, operation, amount):
    TRY:
        performOperation(userId, operation, amount)
    CATCH InsufficientFundsException e:
        // String template for dynamic exception messages
        message = STR."""
            Operation failed for user: \{userId}
            Attempted operation: \{operation}
            Requested amount: \{amount}
            Available balance: \{e.getAvailableBalance()}
            Timestamp: \{LocalDateTime.now()}
            """
        THROW NEW DetailedException(message, e)
```

**Improved Exception Stack Traces:**
```
// Java 17/21 - Better stack trace filtering
METHOD improvedStackTraces():
    TRY:
        deepNestedCall()
    CATCH Exception e:
        // Filter stack trace to show only application code
        StackTraceElement[] filtered = Arrays.stream(e.getStackTrace())
            .filter(element -> element.getClassName().startsWith("com.myapp"))
            .toArray(StackTraceElement[]::new)
        
        e.setStackTrace(filtered)
        
        // Enhanced logging with context
        logger.error("""
            Exception occurred:
            Type: {}
            Message: {}
            Application Stack:
            {}
            """, 
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(filtered)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"))
        )
```

**Modern Exception Handling Patterns:**
```
// Java 17/21 - Modern exception handling with Result pattern
SEALED INTERFACE Result<T> {}
RECORD Success<T>(T value) IMPLEMENTS Result<T> {}
RECORD Failure<T>(Exception exception) IMPLEMENTS Result<T> {}

METHOD safeOperation(input):
    TRY:
        result = riskyOperation(input)
        RETURN NEW Success(result)
    CATCH Exception e:
        RETURN NEW Failure(e)

METHOD processWithResult():
    Result<String> result = safeOperation("input")
    
    RETURN SWITCH result:
        CASE Success(var value) -> processSuccess(value)
        CASE Failure(var exception) -> handleFailure(exception)
```

**Summary of Java 17/21 Exception Improvements:**

| Java Version | Exception Enhancement | Benefit |
|--------------|----------------------|---------|
| Java 17 | Enhanced NPE messages | Precise null pointer identification |
| Java 17 | Sealed exception hierarchies | Type-safe exception handling |
| Java 17 | Records for exception context | Immutable error information |
| Java 21 | Pattern matching in catch | More expressive exception handling |
| Java 21 | Switch expressions for exceptions | Functional exception categorization |
| Java 21 | Virtual threads exception handling | Scalable concurrent error management |
| Java 21 | Structured concurrency exceptions | Coordinated failure handling |
| Java 21 | String templates (Preview) | Dynamic exception messages |

These improvements make exception handling more precise, type-safe, and expressive while integrating well with modern Java features like pattern matching, virtual threads, and structured concurrency.

### **Custom Exceptions:**

**What are Custom Exceptions?**
Custom exceptions are user-defined exception classes that extend existing exception classes to create application-specific error conditions. They provide meaningful error messages and handle business logic specific errors.

**Why Create Custom Exceptions?**

**1. Business Logic Specific Errors:**
- **Problem:** Standard exceptions don't represent business rules
- **Solution:** Create meaningful exception names that reflect business context

```
// Without Custom Exceptions - Generic and unclear
METHOD withdraw(amount):
    IF amount <= 0:
        THROW NEW IllegalArgumentException("Invalid amount")  // Generic
    IF amount > balance:
        THROW NEW RuntimeException("Not enough money")        // Unclear

// With Custom Exceptions - Clear and specific
METHOD withdraw(amount):
    IF amount <= 0:
        THROW NEW InvalidAmountException("Amount must be positive: " + amount)
    IF amount > balance:
        THROW NEW InsufficientFundsException("Cannot withdraw " + amount + 
                                            ", available balance: " + balance)
    IF account.isBlocked():
        THROW NEW AccountBlockedException("Account " + accountNumber + " is blocked")
```

**2. Better Error Handling:**
- **Problem:** Generic exceptions make it hard to handle specific scenarios
- **Solution:** Different catch blocks for different business scenarios

```
// Generic Exception Handling - Limited control
TRY:
    bankService.withdraw(amount)
CATCH Exception e:
    PRINT "Error: " + e.getMessage()      // Same handling for all errors

// Custom Exception Handling - Specific control
TRY:
    bankService.withdraw(amount)
CATCH InsufficientFundsException e:
    PRINT "Insufficient funds. Available: " + e.getAvailableBalance()
    suggestLowerAmount(e.getAvailableBalance())
CATCH AccountBlockedException e:
    PRINT "Account blocked. Contact support."
    redirectToSupport()
CATCH InvalidAmountException e:
    PRINT "Invalid amount. Please enter positive amount."
    showAmountValidationError()
```

**Types of Custom Exceptions:**

**1. Checked Custom Exceptions:**
```
// Extend Exception class for checked exceptions
CLASS BankingException EXTENDS Exception:
    PRIVATE errorCode
    PRIVATE timestamp
    
    CONSTRUCTOR(message):
        SUPER(message)
        this.timestamp = getCurrentTime()
    
    CONSTRUCTOR(message, cause):
        SUPER(message, cause)
        this.timestamp = getCurrentTime()
    
    CONSTRUCTOR(message, errorCode):
        SUPER(message)
        this.errorCode = errorCode
        this.timestamp = getCurrentTime()
    
    METHOD getErrorCode(): RETURN errorCode
    METHOD getTimestamp(): RETURN timestamp

// Specific checked exceptions
CLASS AccountNotFoundException EXTENDS BankingException:
    PRIVATE accountNumber
    
    CONSTRUCTOR(accountNumber):
        SUPER("Account not found: " + accountNumber)
        this.accountNumber = accountNumber
    
    METHOD getAccountNumber(): RETURN accountNumber

CLASS TransactionLimitExceededException EXTENDS BankingException:
    PRIVATE limit
    PRIVATE attemptedAmount
    
    CONSTRUCTOR(limit, attemptedAmount):
        SUPER("Transaction limit exceeded. Limit: " + limit + 
              ", Attempted: " + attemptedAmount)
        this.limit = limit
        this.attemptedAmount = attemptedAmount
    
    METHOD getLimit(): RETURN limit
    METHOD getAttemptedAmount(): RETURN attemptedAmount
```

**2. Unchecked Custom Exceptions:**
```
// Extend RuntimeException for unchecked exceptions
CLASS BusinessRuleException EXTENDS RuntimeException:
    PRIVATE ruleViolated
    
    CONSTRUCTOR(ruleViolated, message):
        SUPER(message)
        this.ruleViolated = ruleViolated
    
    METHOD getRuleViolated(): RETURN ruleViolated

CLASS InsufficientFundsException EXTENDS BusinessRuleException:
    PRIVATE availableBalance
    PRIVATE requestedAmount
    
    CONSTRUCTOR(availableBalance, requestedAmount):
        SUPER("INSUFFICIENT_FUNDS", 
              "Insufficient funds. Available: " + availableBalance + 
              ", Requested: " + requestedAmount)
        this.availableBalance = availableBalance
        this.requestedAmount = requestedAmount
    
    METHOD getAvailableBalance(): RETURN availableBalance
    METHOD getRequestedAmount(): RETURN requestedAmount

CLASS InvalidAccountStateException EXTENDS BusinessRuleException:
    PRIVATE accountState
    PRIVATE operation
    
    CONSTRUCTOR(accountState, operation):
        SUPER("INVALID_ACCOUNT_STATE",
              "Cannot perform " + operation + " on account in " + accountState + " state")
        this.accountState = accountState
        this.operation = operation
```

**Benefits of Custom Exceptions:**

**1. Better Code Readability:**
```
// Without Custom Exceptions - Unclear intent
METHOD processPayment(payment):
    TRY:
        validatePayment(payment)
        executePayment(payment)
    CATCH Exception e:
        // What kind of error? Hard to tell
        handleError(e)

// With Custom Exceptions - Clear intent
METHOD processPayment(payment):
    TRY:
        validatePayment(payment)
        executePayment(payment)
    CATCH PaymentValidationException e:
        // Clear: validation failed
        handleValidationError(e)
    CATCH PaymentProcessingException e:
        // Clear: processing failed
        handleProcessingError(e)
    CATCH NetworkTimeoutException e:
        // Clear: network issue
        retryPayment(payment)
```

**2. Rich Error Information:**
```
CLASS OrderProcessingException EXTENDS Exception:
    PRIVATE orderId
    PRIVATE customerId
    PRIVATE orderStatus
    PRIVATE failureReason
    PRIVATE retryable
    
    CONSTRUCTOR(orderId, customerId, orderStatus, failureReason, retryable):
        SUPER("Order processing failed: " + failureReason)
        this.orderId = orderId
        this.customerId = customerId
        this.orderStatus = orderStatus
        this.failureReason = failureReason
        this.retryable = retryable
    
    // Rich getters for error handling
    METHOD getOrderId(): RETURN orderId
    METHOD getCustomerId(): RETURN customerId
    METHOD getOrderStatus(): RETURN orderStatus
    METHOD getFailureReason(): RETURN failureReason
    METHOD isRetryable(): RETURN retryable

// Usage with rich error information
TRY:
    orderService.processOrder(order)
CATCH OrderProcessingException e:
    PRINT "Order " + e.getOrderId() + " failed: " + e.getFailureReason()
    
    IF e.isRetryable():
        scheduleRetry(e.getOrderId())
    ELSE:
        notifyCustomer(e.getCustomerId(), e.getFailureReason())
        logCriticalError(e)
```

**3. Exception Hierarchy for Better Organization:**
```
// Base exception for all application exceptions
ABSTRACT CLASS ApplicationException EXTENDS Exception:
    PRIVATE errorCode
    PRIVATE severity
    PRIVATE timestamp
    
    CONSTRUCTOR(errorCode, message, severity):
        SUPER(message)
        this.errorCode = errorCode
        this.severity = severity
        this.timestamp = getCurrentTime()

// Domain-specific exception categories
CLASS UserManagementException EXTENDS ApplicationException:
    CONSTRUCTOR(message):
        SUPER("USER_ERROR", message, "MEDIUM")

CLASS PaymentException EXTENDS ApplicationException:
    CONSTRUCTOR(message):
        SUPER("PAYMENT_ERROR", message, "HIGH")

CLASS InventoryException EXTENDS ApplicationException:
    CONSTRUCTOR(message):
        SUPER("INVENTORY_ERROR", message, "LOW")

// Specific exceptions
CLASS UserNotFoundException EXTENDS UserManagementException:
    CONSTRUCTOR(userId):
        SUPER("User not found with ID: " + userId)

CLASS PaymentGatewayException EXTENDS PaymentException:
    CONSTRUCTOR(gatewayError):
        SUPER("Payment gateway error: " + gatewayError)

CLASS OutOfStockException EXTENDS InventoryException:
    PRIVATE productId
    PRIVATE requestedQuantity
    PRIVATE availableQuantity
    
    CONSTRUCTOR(productId, requestedQuantity, availableQuantity):
        SUPER("Product " + productId + " out of stock. Requested: " + 
              requestedQuantity + ", Available: " + availableQuantity)
        this.productId = productId
        this.requestedQuantity = requestedQuantity
        this.availableQuantity = availableQuantity
```

**4. Exception Chaining and Root Cause Analysis:**
```
CLASS ServiceException EXTENDS Exception:
    CONSTRUCTOR(message, cause):
        SUPER(message, cause)               // Chain the original exception
    
    METHOD getRootCause():
        cause = this
        WHILE cause.getCause() != null:
            cause = cause.getCause()
        RETURN cause

// Usage with exception chaining
METHOD transferFunds(fromAccount, toAccount, amount):
    TRY:
        validateTransfer(fromAccount, toAccount, amount)
        executeTransfer(fromAccount, toAccount, amount)
    CATCH DatabaseException e:
        // Chain the database exception with business context
        THROW NEW TransferException("Fund transfer failed for accounts " + 
                                   fromAccount + " -> " + toAccount, e)
    CATCH NetworkException e:
        // Chain the network exception with business context
        THROW NEW TransferException("Network error during fund transfer", e)

// Exception handling with root cause
TRY:
    bankService.transferFunds(account1, account2, 1000)
CATCH TransferException e:
    PRINT "Transfer failed: " + e.getMessage()
    rootCause = e.getRootCause()
    PRINT "Root cause: " + rootCause.getClass().getSimpleName()
    
    IF rootCause INSTANCEOF NetworkException:
        scheduleRetry()
    ELSE IF rootCause INSTANCEOF DatabaseException:
        alertSystemAdmin()
```

**5. Exception Factory Pattern:**
```
CLASS ExceptionFactory:
    STATIC METHOD createUserException(errorType, details):
        SWITCH errorType:
            CASE "NOT_FOUND":
                RETURN NEW UserNotFoundException(details.userId)
            CASE "DUPLICATE_EMAIL":
                RETURN NEW DuplicateEmailException(details.email)
            CASE "INVALID_PASSWORD":
                RETURN NEW InvalidPasswordException(details.requirements)
            DEFAULT:
                RETURN NEW UserManagementException("Unknown error: " + errorType)
    
    STATIC METHOD createPaymentException(errorType, details):
        SWITCH errorType:
            CASE "INSUFFICIENT_FUNDS":
                RETURN NEW InsufficientFundsException(details.available, details.requested)
            CASE "CARD_EXPIRED":
                RETURN NEW CardExpiredException(details.cardNumber, details.expiryDate)
            CASE "GATEWAY_ERROR":
                RETURN NEW PaymentGatewayException(details.gatewayMessage)
            DEFAULT:
                RETURN NEW PaymentException("Payment error: " + errorType)

// Usage
METHOD processUser(userData):
    IF userExists(userData.email):
        exception = ExceptionFactory.createUserException("DUPLICATE_EMAIL", 
                                                        userData)
        THROW exception
```

**Best Practices for Custom Exceptions:**

**1. Naming Convention:**
- End with "Exception" (e.g., UserNotFoundException)
- Use descriptive names that indicate the problem
- Follow camelCase convention

**2. Exception Design:**
- Extend appropriate base class (Exception vs RuntimeException)
- Include multiple constructors for flexibility
- Add relevant fields and getters for error context
- Override toString() for better logging

**3. Documentation:**
- Document when and why the exception is thrown
- Provide examples of how to handle it
- Document any special fields or methods

```
/**
 * Thrown when a user attempts to withdraw more money than available in account.
 * This is a business rule exception that should be handled by displaying
 * appropriate message to user and suggesting available balance.
 * 
 * Example usage:
 * try {
 *     account.withdraw(amount);
 * } catch (InsufficientFundsException e) {
 *     displayError("Insufficient funds. Available: " + e.getAvailableBalance());
 * }
 */
CLASS InsufficientFundsException EXTENDS BusinessRuleException:
    // Implementation
```

**4. When to Use Checked vs Unchecked:**
- **Checked:** When caller must handle the exception (recoverable errors)
- **Unchecked:** When it indicates programming error or business rule violation

**Benefits Summary:**

| Aspect | Standard Exceptions | Custom Exceptions |
|--------|-------------------|------------------|
| Meaning | Generic, unclear | Business-specific, clear |
| Error Handling | Limited options | Specific handling strategies |
| Debugging | Basic information | Rich contextual information |
| Code Readability | Poor | Excellent |
| Maintainability | Difficult | Easy |
| Error Recovery | Generic approaches | Tailored recovery strategies |

**try-catch-finally:**
```
METHOD performDatabaseOperation():
    Connection connection = null
    TRY:
        connection = getConnection()
        executeQuery(connection)
    CATCH SQLException e:
        PRINT "Database error: " + e.message
        // Handle database specific errors
    CATCH Exception e:
        PRINT "General error: " + e.message
        // Handle any other exceptions
    FINALLY:
        IF connection IS NOT null:
            connection.close()           // Always cleanup
```

**throw and throws:**
```
METHOD validateAge(age) THROWS IllegalArgumentException:
    IF age < 0 OR age > 150:
        THROW NEW IllegalArgumentException("Invalid age: " + age)

METHOD processUser(user) THROWS SQLException, ValidationException:
    validateAge(user.age)               // May throw IllegalArgumentException
    saveToDatabase(user)                // May throw SQLException
```

**try-with-resources:**
```
METHOD readFileContent(fileName):
    TRY WITH RESOURCES(FileReader file = NEW FileReader(fileName)):
        // File automatically closed after try block
        RETURN file.readAll()
    CATCH IOException e:
        PRINT "Error reading file: " + e.message
        RETURN null
    // No finally needed - resources auto-closed
```

---

## Threading Fundamentals

### **What is a Thread?**
A thread is a lightweight subprocess that can run concurrently with other threads. It's the smallest unit of processing that can be scheduled by the operating system.

### **Why Use Threads?**
- **Concurrency:** Multiple tasks can run simultaneously
- **Responsiveness:** UI remains responsive during background tasks
- **Resource Utilization:** Better CPU utilization
- **Parallelism:** Take advantage of multiple CPU cores

```
// Sequential vs Concurrent Processing
SEQUENTIAL_PROCESSING:
    task1()     // Takes 5 seconds
    task2()     // Takes 3 seconds
    task3()     // Takes 4 seconds
    // Total: 12 seconds

CONCURRENT_PROCESSING:
    THREAD1: task1()    // 5 seconds
    THREAD2: task2()    // 3 seconds  
    THREAD3: task3()    // 4 seconds
    // Total: 5 seconds (max of all tasks)
```

---

## Thread Lifecycle

### **Thread States:**

**1. NEW:**
- **What:** Thread object created but not started
- **Transition:** Created → start() called → RUNNABLE

**2. RUNNABLE:**
- **What:** Thread is ready to run or currently running
- **Sub-states:** Ready (waiting for CPU) and Running (executing)

**3. BLOCKED:**
- **What:** Thread waiting for monitor lock to enter synchronized block/method
- **Transition:** Trying to enter synchronized code

**4. WAITING:**
- **What:** Thread waiting indefinitely for another thread's action
- **Causes:** wait(), join(), park()

**5. TIMED_WAITING:**
- **What:** Thread waiting for specified time period
- **Causes:** sleep(), wait(timeout), join(timeout)

**6. TERMINATED:**
- **What:** Thread execution completed or terminated

```
THREAD_LIFECYCLE:
    NEW
     ↓ start()
    RUNNABLE ←→ RUNNING
     ↓         ↓
    BLOCKED   WAITING/TIMED_WAITING
     ↓         ↓
    TERMINATED ←

// State Transitions Example
CLASS ThreadLifecycleDemo:
    METHOD demonstrateStates():
        thread = NEW Thread(runnable)
        PRINT thread.getState()           // NEW
        
        thread.start()
        PRINT thread.getState()           // RUNNABLE
        
        // Inside run method:
        Thread.sleep(1000)                // TIMED_WAITING
        synchronizedMethod()              // BLOCKED (if lock unavailable)
        object.wait()                     // WAITING
        
        // After run method completes:
        PRINT thread.getState()           // TERMINATED
```

---

## Thread Implementation

### **Types of Thread Creation:**

**1. Extending Thread Class:**
```
CLASS MyThread EXTENDS Thread:
    PRIVATE taskName
    
    CONSTRUCTOR(taskName):
        this.taskName = taskName
    
    METHOD run():                         // Override run method
        FOR i = 1 TO 5:
            PRINT taskName + " - Step " + i
            Thread.sleep(1000)            // Simulate work
        PRINT taskName + " completed"

// Usage
thread1 = NEW MyThread("Task-1")
thread2 = NEW MyThread("Task-2")
thread1.start()                          // Starts new thread
thread2.start()                          // Starts new thread
```

**2. Implementing Runnable Interface:**
```
CLASS MyTask IMPLEMENTS Runnable:
    PRIVATE taskName
    
    CONSTRUCTOR(taskName):
        this.taskName = taskName
    
    METHOD run():                         // Implement run method
        FOR i = 1 TO 5:
            PRINT taskName + " - Step " + i
            Thread.sleep(1000)
        PRINT taskName + " completed"

// Usage
task1 = NEW MyTask("Task-1")
task2 = NEW MyTask("Task-2")
thread1 = NEW Thread(task1)             // Pass Runnable to Thread
thread2 = NEW Thread(task2)
thread1.start()
thread2.start()
```

**3. Using Lambda Expressions (Functional Interface):**
```
// Since Runnable is functional interface
Runnable task = () -> {
    FOR i = 1 TO 5:
        PRINT "Lambda Task - Step " + i
        Thread.sleep(1000)
}

Thread thread = NEW Thread(task)
thread.start()

// Or directly
Thread thread = NEW Thread(() -> {
    PRINT "Direct lambda task"
})
thread.start()
```

**Runnable vs Thread:**
| Aspect | Extending Thread | Implementing Runnable |
|--------|------------------|----------------------|
| Inheritance | Single inheritance used | Class can extend other classes |
| Reusability | Thread tied to task | Task can be reused |
| Separation | Task and thread coupled | Task and thread separated |
| Best Practice | Not recommended | Recommended approach |

---

## Synchronization

### **What is Synchronization?**
Synchronization is the capability to control access to shared resources by multiple threads to prevent data corruption and ensure thread safety.

### **Why Synchronization Needed?**
- **Race Conditions:** Multiple threads accessing shared data simultaneously
- **Data Corruption:** Inconsistent state due to concurrent modifications
- **Thread Safety:** Ensure operations are atomic and consistent

### **Types of Synchronization:**

**1. Synchronized Methods:**
```
CLASS BankAccount:
    PRIVATE balance = 1000
    
    SYNCHRONIZED METHOD withdraw(amount):    // Only one thread at a time
        IF balance >= amount:
            PRINT "Withdrawing " + amount
            Thread.sleep(100)                // Simulate processing time
            balance -= amount
            PRINT "New balance: " + balance
        ELSE:
            PRINT "Insufficient funds"
    
    SYNCHRONIZED METHOD deposit(amount):     // Only one thread at a time
        PRINT "Depositing " + amount
        Thread.sleep(100)
        balance += amount
        PRINT "New balance: " + balance

// Usage - Thread Safe
account = NEW BankAccount()
THREAD1: account.withdraw(500)              // Thread safe operation
THREAD2: account.deposit(200)               // Thread safe operation
```

**2. Synchronized Blocks:**
```
CLASS Counter:
    PRIVATE count = 0
    PRIVATE lock = NEW Object()
    
    METHOD increment():
        SYNCHRONIZED(lock):                  // Synchronized block
            count++                          // Critical section
            PRINT "Count: " + count
    
    METHOD decrement():
        SYNCHRONIZED(this):                  // Using 'this' as lock
            count--
            PRINT "Count: " + count

// Fine-grained synchronization
CLASS BankAccount:
    PRIVATE balance = 1000
    PRIVATE accountLock = NEW Object()
    PRIVATE historyLock = NEW Object()
    PRIVATE transactionHistory = []
    
    METHOD withdraw(amount):
        SYNCHRONIZED(accountLock):           // Lock only balance operations
            IF balance >= amount:
                balance -= amount
        
        SYNCHRONIZED(historyLock):           // Separate lock for history
            transactionHistory.add("Withdraw: " + amount)
```

**3. Static Synchronization:**
```
CLASS DatabaseConnection:
    PRIVATE STATIC connectionCount = 0
    PRIVATE STATIC maxConnections = 10
    
    SYNCHRONIZED STATIC METHOD getConnection():  // Class-level lock
        IF connectionCount < maxConnections:
            connectionCount++
            RETURN NEW Connection()
        ELSE:
            THROW "No available connections"
    
    SYNCHRONIZED STATIC METHOD releaseConnection():
        connectionCount--
```

**4. Using Locks (java.util.concurrent.locks):**
```
CLASS AdvancedCounter:
    PRIVATE count = 0
    PRIVATE lock = NEW ReentrantLock()       // Explicit lock
    
    METHOD increment():
        lock.lock()                          // Acquire lock
        TRY:
            count++                          // Critical section
            PRINT "Count: " + count
        FINALLY:
            lock.unlock()                    // Always release lock
    
    METHOD tryIncrement():
        IF lock.tryLock():                   // Non-blocking lock attempt
            TRY:
                count++
                RETURN true
            FINALLY:
                lock.unlock()
        ELSE:
            RETURN false                     // Could not acquire lock
```

**5. Read-Write Locks:**
```
CLASS Cache:
    PRIVATE data = {}
    PRIVATE rwLock = NEW ReentrantReadWriteLock()
    PRIVATE readLock = rwLock.readLock()
    PRIVATE writeLock = rwLock.writeLock()
    
    METHOD getValue(key):
        readLock.lock()                      // Multiple readers allowed
        TRY:
            RETURN data.get(key)
        FINALLY:
            readLock.unlock()
    
    METHOD setValue(key, value):
        writeLock.lock()                     // Exclusive writer access
        TRY:
            data.put(key, value)
        FINALLY:
            writeLock.unlock()
```

---

## Semaphore and Deadlock

### **Semaphore:**
- **What:** Counting semaphore that maintains a set of permits
- **Use:** Control access to shared resource with limited capacity

```
CLASS DatabaseConnectionPool:
    PRIVATE semaphore = NEW Semaphore(5)     // Max 5 connections
    PRIVATE connections = []                  // Pool of connections
    
    METHOD getConnection():
        semaphore.acquire()                   // Wait for permit
        TRY:
            connection = connections.remove()  // Get connection from pool
            RETURN connection
        CATCH:
            RETURN createNewConnection()
    
    METHOD releaseConnection(connection):
        connections.add(connection)           // Return to pool
        semaphore.release()                   // Release permit

// Binary Semaphore (similar to mutex)
CLASS CriticalSection:
    PRIVATE binarySemaphore = NEW Semaphore(1)  // Only 1 permit
    
    METHOD executeTask():
        binarySemaphore.acquire()             // Acquire exclusive access
        TRY:
            // Critical section - only one thread at a time
            performCriticalOperation()
        FINALLY:
            binarySemaphore.release()         // Release access
```

### **Deadlock:**
- **What:** Situation where two or more threads block forever, waiting for each other
- **Conditions:** Mutual exclusion, hold and wait, no preemption, circular wait

```
// Deadlock Example
CLASS DeadlockExample:
    PRIVATE lock1 = NEW Object()
    PRIVATE lock2 = NEW Object()
    
    METHOD method1():
        SYNCHRONIZED(lock1):                  // Thread A acquires lock1
            PRINT "Thread A: Holding lock1"
            Thread.sleep(100)
            
            SYNCHRONIZED(lock2):              // Thread A waits for lock2
                PRINT "Thread A: Holding both locks"
    
    METHOD method2():
        SYNCHRONIZED(lock2):                  // Thread B acquires lock2
            PRINT "Thread B: Holding lock2"
            Thread.sleep(100)
            
            SYNCHRONIZED(lock1):              // Thread B waits for lock1
                PRINT "Thread B: Holding both locks"

// DEADLOCK: Thread A holds lock1, waits for lock2
//           Thread B holds lock2, waits for lock1

// Deadlock Prevention - Ordered Lock Acquisition
CLASS DeadlockPrevention:
    PRIVATE lock1 = NEW Object()
    PRIVATE lock2 = NEW Object()
    
    METHOD method1():
        SYNCHRONIZED(lock1):                  // Always acquire locks in same order
            SYNCHRONIZED(lock2):
                // Critical section
    
    METHOD method2():
        SYNCHRONIZED(lock1):                  // Same order prevents deadlock
            SYNCHRONIZED(lock2):
                // Critical section
```

**Deadlock Detection and Prevention:**
```
// Using tryLock with timeout
CLASS DeadlockAvoidance:
    PRIVATE lock1 = NEW ReentrantLock()
    PRIVATE lock2 = NEW ReentrantLock()
    
    METHOD safeMethod():
        IF lock1.tryLock(1, SECONDS):        // Try to acquire with timeout
            TRY:
                IF lock2.tryLock(1, SECONDS):
                    TRY:
                        // Both locks acquired safely
                        performOperation()
                    FINALLY:
                        lock2.unlock()
                ELSE:
                    PRINT "Could not acquire lock2, avoiding deadlock"
            FINALLY:
                lock1.unlock()
        ELSE:
            PRINT "Could not acquire lock1"
```

---

## Multithreading and Concurrency

### **Multithreading:**
- **What:** Multiple threads executing concurrently within a single process
- **Benefits:** Better resource utilization, improved responsiveness, parallel processing

### **Concurrency vs Parallelism:**

**Concurrency:**
- **Definition:** Multiple tasks making progress, but not necessarily simultaneously
- **Example:** Single core CPU switching between tasks

**Parallelism:**
- **Definition:** Multiple tasks executing simultaneously
- **Example:** Multi-core CPU executing tasks at the same time

```
// Concurrency Example - Task Switching
SINGLE_CORE_CONCURRENCY:
    Time 1: Task A executes
    Time 2: Context switch to Task B
    Time 3: Task B executes  
    Time 4: Context switch to Task A
    // Tasks appear to run simultaneously but actually alternate

// Parallelism Example - Simultaneous Execution
MULTI_CORE_PARALLELISM:
    Core 1: Task A executes continuously
    Core 2: Task B executes continuously
    Core 3: Task C executes continuously
    // Tasks actually run at the same time
```

### **Thread Communication Mechanisms:**

**1. wait(), notify(), notifyAll():**
```
CLASS ProducerConsumer:
    PRIVATE queue = NEW LinkedList()
    PRIVATE capacity = 5
    
    SYNCHRONIZED METHOD produce():
        WHILE queue.size() == capacity:
            wait()                            // Wait until space available
        
        item = NEW Item()
        queue.add(item)
        PRINT "Produced: " + item
        notifyAll()                          // Notify waiting consumers
    
    SYNCHRONIZED METHOD consume():
        WHILE queue.isEmpty():
            wait()                            // Wait until item available
        
        item = queue.remove()
        PRINT "Consumed: " + item
        notifyAll()                          // Notify waiting producers
```

**2. BlockingQueue:**
```
CLASS ModernProducerConsumer:
    PRIVATE queue = NEW ArrayBlockingQueue(5)
    
    METHOD produce():
        item = NEW Item()
        queue.put(item)                      // Blocks if queue full
        PRINT "Produced: " + item
    
    METHOD consume():
        item = queue.take()                  // Blocks if queue empty
        PRINT "Consumed: " + item
```

**3. CountDownLatch:**
```
CLASS ServiceInitializer:
    PRIVATE latch = NEW CountDownLatch(3)    // Wait for 3 services
    
    METHOD initializeService(serviceName):
        // Initialize service
        PRINT serviceName + " initialized"
        latch.countDown()                    // Signal completion
    
    METHOD waitForAllServices():
        latch.await()                        // Wait for all services
        PRINT "All services initialized"
```

---

## Executor Services

### **Why Executor Services?**
- **Thread Management:** Automatic thread creation, pooling, and cleanup
- **Resource Control:** Limit number of concurrent threads
- **Task Scheduling:** Schedule tasks for future execution
- **Better Performance:** Reuse threads instead of creating new ones

### **Problems with Manual Thread Management:**
```
// Problems without Executor Service
FOR i = 1 TO 1000:
    thread = NEW Thread(task)               // Create 1000 threads!
    thread.start()                          // Resource expensive
    // No control over thread lifecycle
    // No way to limit concurrent threads
    // Memory overhead for many threads
```

### **Types of Executor Services:**

**1. Fixed Thread Pool:**
```
EXECUTOR_SERVICE executorService = Executors.newFixedThreadPool(5)

FOR i = 1 TO 100:
    executorService.submit(() -> {
        PRINT "Task " + i + " executed by " + Thread.currentThread().getName()
        Thread.sleep(1000)
    })

executorService.shutdown()                   // Graceful shutdown
```

**2. Cached Thread Pool:**
```
// Creates threads as needed, reuses idle threads
EXECUTOR_SERVICE executorService = Executors.newCachedThreadPool()

FOR i = 1 TO 10:
    executorService.submit(task)            // Reuses threads efficiently

// Good for short-lived tasks with unpredictable load
```

**3. Single Thread Executor:**
```
// Executes tasks sequentially using single thread
EXECUTOR_SERVICE executorService = Executors.newSingleThreadExecutor()

executorService.submit(task1)               // Executes first
executorService.submit(task2)               // Executes after task1
executorService.submit(task3)               // Executes after task2
```

**4. Scheduled Thread Pool:**
```
SCHEDULED_EXECUTOR_SERVICE scheduler = Executors.newScheduledThreadPool(2)

// Execute once after delay
scheduler.schedule(task, 5, SECONDS)

// Execute repeatedly with fixed delay
scheduler.scheduleWithFixedDelay(task, 0, 10, SECONDS)

// Execute repeatedly at fixed rate
scheduler.scheduleAtFixedRate(task, 0, 5, SECONDS)
```

**5. Custom Thread Pool:**
```
EXECUTOR_SERVICE executorService = NEW ThreadPoolExecutor(
    5,                                      // Core pool size
    10,                                     // Maximum pool size
    60,                                     // Keep alive time
    SECONDS,                                // Time unit
    NEW LinkedBlockingQueue(100),           // Work queue
    NEW ThreadFactory() {                   // Custom thread factory
        METHOD newThread(runnable):
            thread = NEW Thread(runnable)
            thread.setName("CustomWorker-" + counter++)
            RETURN thread
    },
    NEW RejectedExecutionHandler() {        // Rejection policy
        METHOD rejectedExecution(task, executor):
            PRINT "Task rejected: " + task
    }
)
```

### **Future and CompletableFuture:**

**Future - Getting Results:**
```
EXECUTOR_SERVICE executor = Executors.newFixedThreadPool(3)

Future<Integer> future = executor.submit(() -> {
    Thread.sleep(2000)                      // Simulate long task
    RETURN 42
})

PRINT "Task submitted, doing other work..."
Integer result = future.get()              // Blocks until result available
PRINT "Result: " + result
```

**CompletableFuture - Asynchronous Programming:**
```
// Asynchronous task chain
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        RETURN "Hello"                      // First async task
    })
    .thenApply(result -> {
        RETURN result + " World"            // Transform result
    })
    .thenApply(String::toUpperCase)         // Another transformation
    .thenCompose(result -> {
        RETURN CompletableFuture.supplyAsync(() -> result + "!")
    })

String finalResult = future.get()          // "HELLO WORLD!"
```

---

## Virtual Threads (Java 21)

### **What are Virtual Threads?**
Virtual threads are lightweight threads managed by the JVM rather than the operating system. They enable massive concurrency with minimal resource overhead.

### **Traditional Threads vs Virtual Threads:**

**Platform Threads (Traditional):**
- **Mapping:** 1:1 with OS threads
- **Cost:** ~2MB stack space per thread
- **Limit:** Few thousand threads max
- **Context Switching:** Expensive OS-level operation

**Virtual Threads:**
- **Mapping:** Many virtual threads to few platform threads
- **Cost:** Few KB per virtual thread
- **Limit:** Millions of virtual threads possible
- **Context Switching:** Cheap JVM-level operation

```
// Traditional Thread Limitations
FOR i = 1 TO 10000:
    thread = NEW Thread(task)               // Creates 10,000 OS threads
    thread.start()                          // ~20GB memory usage!

// Virtual Thread Solution
FOR i = 1 TO 1000000:
    virtualThread = Thread.startVirtualThread(task)  // Creates 1M virtual threads
    // Uses minimal memory, managed by JVM
```

### **Benefits of Virtual Threads:**

**1. Massive Concurrency:**
```
// Handle millions of concurrent requests
CLASS WebServer:
    METHOD handleRequest(request):
        Thread.startVirtualThread(() -> {
            // Each request gets its own virtual thread
            processRequest(request)         // I/O operations
            database.query(request)         // Blocks virtual thread, not platform thread
            sendResponse(response)
        })

// Can handle 1M+ concurrent connections with minimal resources
```

**2. Simplified Concurrency Model:**
```
// Before Virtual Threads - Complex async code
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> fetchUserData(userId))
    .thenCompose(user -> fetchUserPreferences(user.id))
    .thenApply(prefs -> generateRecommendations(prefs))

// With Virtual Threads - Simple sequential code
Thread.startVirtualThread(() -> {
    user = fetchUserData(userId)           // Simple blocking call
    prefs = fetchUserPreferences(user.id)  // Simple blocking call
    recommendations = generateRecommendations(prefs)
    sendRecommendations(recommendations)
})
```

**3. Better Resource Utilization:**
```
// Virtual threads automatically yield when blocking
Thread.startVirtualThread(() -> {
    data = database.query(sql)             // Virtual thread parks
    // Platform thread available for other virtual threads
    result = processData(data)             // Virtual thread resumes
    file.write(result)                     // Virtual thread parks again
})
```

### **Types of Virtual Thread Creation:**

**1. Thread.startVirtualThread():**
```
// Create and start virtual thread
Thread virtualThread = Thread.startVirtualThread(() -> {
    PRINT "Running in virtual thread: " + Thread.currentThread()
    performTask()
})
```

**2. Thread.ofVirtual():**
```
// More control over virtual thread creation
Thread.Builder.OfVirtual builder = Thread.ofVirtual()
    .name("virtual-worker")

Thread virtualThread = builder.start(() -> {
    performTask()
})
```

**3. Virtual Thread Executor:**
```
// Executor service with virtual threads
EXECUTOR_SERVICE executor = Executors.newVirtualThreadPerTaskExecutor()

FOR i = 1 TO 1000000:
    executor.submit(() -> {
        performIOTask()                     // Each task gets virtual thread
    })
```

**4. Structured Concurrency:**
```
// Java 19+ Preview Feature - Structured Concurrency
METHOD fetchUserProfile(userId):
    WITH StructuredTaskScope.ShutdownOnFailure() AS scope:
        Future<User> userFuture = scope.fork(() -> fetchUser(userId))
        Future<Preferences> prefsFuture = scope.fork(() -> fetchPreferences(userId))
        Future<History> historyFuture = scope.fork(() -> fetchHistory(userId))
        
        scope.join()                        // Wait for all tasks
        scope.throwIfFailed()              // Propagate any failures
        
        RETURN NEW UserProfile(
            userFuture.resultNow(),
            prefsFuture.resultNow(),
            historyFuture.resultNow()
        )
```

### **When to Use Virtual Threads:**

**Ideal Use Cases:**
- **I/O Heavy Applications:** Web servers, database applications
- **Microservices:** High concurrency with blocking I/O
- **Request Processing:** Each request in own thread
- **Pipeline Processing:** Many concurrent pipeline stages

**Not Ideal For:**
- **CPU Intensive Tasks:** No benefit over platform threads
- **Few Long-Running Tasks:** Overhead not justified
- **Native Code Interop:** Virtual threads don't help with JNI calls

```
// Good Use Case - Web Request Handler
CLASS WebRequestHandler:
    METHOD handleRequest(request):
        Thread.startVirtualThread(() -> {
            // I/O operations that benefit from virtual threads
            user = userService.getUser(request.userId)          // Database call
            permissions = authService.checkPermissions(user)    // Auth service call
            data = dataService.fetchData(request.params)        // External API call
            response = processData(data, permissions)
            sendResponse(response)
        })

// Poor Use Case - CPU Intensive Task
CLASS CPUIntensiveTask:
    METHOD performCalculation():
        // Virtual threads provide no benefit here
        FOR i = 1 TO 1000000:
            result = complexMathOperation(i)   // Pure CPU work
        RETURN result
```

### **Virtual Thread Best Practices:**

1. **Use for I/O-bound tasks**
2. **Avoid pooling virtual threads** - create them as needed
3. **Don't use ThreadLocal extensively** - can cause memory bloat
4. **Monitor pinning** - avoid long-running synchronized blocks
5. **Use structured concurrency** when available

---

## Collections Framework

### **What is Collections Framework?**
The Collections Framework is a unified architecture for representing and manipulating collections of objects. It provides interfaces, implementations, and algorithms to work with groups of objects.

### **Collection vs Collections:**

**Collection (Interface):**
- **What:** Root interface of the collection hierarchy
- **Purpose:** Defines basic operations for groups of objects
- **Example:** List, Set, Queue extend Collection

**Collections (Class):**
- **What:** Utility class with static methods
- **Purpose:** Provides algorithms for collections (sort, search, etc.)
- **Example:** Collections.sort(), Collections.reverse()

```
// Collection Interface Usage
Collection<String> names = NEW ArrayList()
names.add("Alice")
names.add("Bob")

// Collections Class Usage
Collections.sort(names)                    // Static utility method
Collections.reverse(names)                 // Static utility method
Collections.shuffle(names)                 // Static utility method
```

### **Collections Hierarchy Diagram:**

```
                        Iterable<E>
                             |
                      Collection<E>
                    /        |        \
                   /         |         \
               List<E>    Set<E>     Queue<E>
              /     \    /     \         |
         ArrayList  LinkedList  HashSet  PriorityQueue
         Vector     Deque      TreeSet   ArrayDeque
                              LinkedHashSet
                                 |
                              SortedSet<E>
                                 |
                              NavigableSet<E>

        Map<E> (Not part of Collection hierarchy)
       /     |      \
   HashMap  TreeMap  LinkedHashMap
   Hashtable        WeakHashMap
      |             ConcurrentHashMap
   Properties
```

### **Core Interfaces:**

**1. Collection Interface:**
```
INTERFACE Collection<E>:
    METHOD add(element): boolean
    METHOD remove(element): boolean
    METHOD contains(element): boolean
    METHOD size(): int
    METHOD isEmpty(): boolean
    METHOD clear(): void
    METHOD iterator(): Iterator<E>
    METHOD toArray(): Object[]
```

**2. List Interface:**
```
INTERFACE List<E> EXTENDS Collection<E>:
    METHOD get(index): E
    METHOD set(index, element): E
    METHOD add(index, element): void
    METHOD remove(index): E
    METHOD indexOf(element): int
    METHOD subList(fromIndex, toIndex): List<E>
```

**3. Set Interface:**
```
INTERFACE Set<E> EXTENDS Collection<E>:
    // Same methods as Collection but no duplicates allowed
    // No additional methods - just contract difference
```

**4. Map Interface:**
```
INTERFACE Map<K, V>:
    METHOD put(key, value): V
    METHOD get(key): V
    METHOD remove(key): V
    METHOD containsKey(key): boolean
    METHOD containsValue(value): boolean
    METHOD keySet(): Set<K>
    METHOD values(): Collection<V>
    METHOD entrySet(): Set<Entry<K,V>>
```

---

## Array vs ArrayList vs LinkedList

### **Array:**
- **What:** Fixed-size data structure storing elements of same type
- **Memory:** Contiguous memory allocation
- **Access:** Direct index-based access O(1)

```
// Array Declaration and Usage
DECLARE array[5]                          // Fixed size
array[0] = "First"                        // Direct access
array[1] = "Second"
element = array[0]                        // O(1) access

// Limitations
array.length                              // Cannot be changed
// Cannot add/remove elements dynamically
```

### **ArrayList:**
- **What:** Resizable array implementation of List interface
- **Memory:** Dynamic array with automatic resizing
- **Access:** Index-based access O(1)

```
CLASS ArrayList<E>:
    PRIVATE elementData[]                 // Internal array
    PRIVATE size                          // Current number of elements
    PRIVATE capacity                      // Array length
    
    METHOD add(element):
        IF size >= capacity:
            resize()                      // Double the capacity
        elementData[size] = element
        size++
    
    METHOD get(index):
        IF index >= size: THROW IndexOutOfBounds
        RETURN elementData[index]         // O(1) access
    
    METHOD remove(index):
        IF index >= size: THROW IndexOutOfBounds
        FOR i = index TO size-2:
            elementData[i] = elementData[i+1]  // Shift elements O(n)
        size--
    
    PRIVATE METHOD resize():
        newCapacity = capacity * 2        // Usually doubles
        newArray = NEW Array[newCapacity]
        COPY elementData TO newArray      // O(n) operation
        elementData = newArray
        capacity = newCapacity

// Usage
list = NEW ArrayList()
list.add("A")                            // O(1) amortized
list.add("B")
element = list.get(0)                    // O(1) access
list.remove(0)                           // O(n) - shifts elements
```

### **LinkedList:**
- **What:** Doubly-linked list implementation
- **Memory:** Non-contiguous memory, nodes linked via pointers
- **Access:** Sequential access O(n)

```
CLASS LinkedList<E>:
    PRIVATE first                         // Reference to first node
    PRIVATE last                          // Reference to last node
    PRIVATE size
    
    CLASS Node:
        element
        next                              // Reference to next node
        prev                              // Reference to previous node
    
    METHOD add(element):
        newNode = NEW Node(element)
        IF list is empty:
            first = last = newNode
        ELSE:
            last.next = newNode
            newNode.prev = last
            last = newNode
        size++                            // O(1) at end
    
    METHOD get(index):
        IF index >= size: THROW IndexOutOfBounds
        IF index < size/2:
            node = first
            FOR i = 0 TO index-1:
                node = node.next          // Traverse from beginning
        ELSE:
            node = last
            FOR i = size-1 DOWNTO index+1:
                node = node.prev          // Traverse from end
        RETURN node.element               // O(n) access
    
    METHOD remove(index):
        node = getNode(index)             // O(n) to find
        IF node.prev != null:
            node.prev.next = node.next
        IF node.next != null:
            node.next.prev = node.prev
        size--                            // O(1) once found

// Usage
list = NEW LinkedList()
list.add("A")                            // O(1) at end
list.addFirst("B")                       // O(1) at beginning
element = list.get(0)                    // O(n) access
list.removeFirst()                       // O(1) at beginning
```

### **Comparison Table:**

| Feature | Array | ArrayList | LinkedList |
|---------|-------|-----------|------------|
| Size | Fixed | Dynamic | Dynamic |
| Memory | Contiguous | Contiguous | Non-contiguous |
| Access Time | O(1) | O(1) | O(n) |
| Insert at End | N/A | O(1) amortized | O(1) |
| Insert at Beginning | N/A | O(n) | O(1) |
| Insert at Middle | N/A | O(n) | O(n) |
| Delete at End | N/A | O(1) | O(1) |
| Delete at Beginning | N/A | O(n) | O(1) |
| Delete at Middle | N/A | O(n) | O(n) |
| Memory Overhead | Low | Low | High (pointers) |
| Cache Performance | Excellent | Good | Poor |

### **When to Use What:**

**Use Array When:**
- Fixed size is acceptable
- Memory is critical
- Need maximum performance

**Use ArrayList When:**
- Need dynamic sizing
- Frequent random access
- More reads than writes

**Use LinkedList When:**
- Frequent insertion/deletion at beginning/middle
- Size varies significantly
- Don't need random access

---

## Set and HashSet

### **What is Set?**
Set is a collection that contains no duplicate elements. It models the mathematical set abstraction.

### **HashSet Internal Working:**

**Structure:**
- **Backing Store:** HashMap (HashSet is built on top of HashMap)
- **Storage:** Elements stored as keys, dummy value as values
- **Hash Function:** Uses hashCode() to determine bucket location

```
CLASS HashSet<E>:
    PRIVATE map = NEW HashMap<E, Object>() // Internal HashMap
    PRIVATE PRESENT = NEW Object()         // Dummy value
    
    METHOD add(element):
        RETURN map.put(element, PRESENT) == null  // Returns true if new
    
    METHOD contains(element):
        RETURN map.containsKey(element)    // Delegates to HashMap
    
    METHOD remove(element):
        RETURN map.remove(element) == PRESENT
    
    METHOD size():
        RETURN map.size()

// Internal HashMap Structure for HashSet
CLASS HashMap_Internal:
    PRIVATE buckets[]                      // Array of buckets
    PRIVATE size                           // Number of elements
    PRIVATE threshold                      // Resize threshold
    PRIVATE loadFactor = 0.75             // Load factor
    
    CLASS Node:
        hash                               // Cached hash code
        key                                // The element
        value                              // Dummy PRESENT object
        next                               // Next node in bucket (chaining)
    
    METHOD put(key, value):
        hash = key.hashCode()
        index = hash % buckets.length      // Simple hash function
        
        // Check if key already exists
        node = buckets[index]
        WHILE node != null:
            IF node.hash == hash AND node.key.equals(key):
                oldValue = node.value
                node.value = value         // Update existing
                RETURN oldValue
            node = node.next
        
        // Add new node
        newNode = NEW Node(hash, key, value, buckets[index])
        buckets[index] = newNode           // Insert at beginning
        size++
        
        // Resize if needed
        IF size > threshold:
            resize()
        
        RETURN null                        // New key added
    
    METHOD get(key):
        hash = key.hashCode()
        index = hash % buckets.length
        node = buckets[index]
        
        WHILE node != null:
            IF node.hash == hash AND node.key.equals(key):
                RETURN node.value          // Found
            node = node.next
        
        RETURN null                        // Not found
    
    PRIVATE METHOD resize():
        oldBuckets = buckets
        buckets = NEW Array[buckets.length * 2]  // Double size
        size = 0
        
        FOR EACH bucket IN oldBuckets:
            node = bucket
            WHILE node != null:
                put(node.key, node.value)  // Rehash all elements
                node = node.next
```

### **HashSet Characteristics:**

**1. No Duplicates:**
```
set = NEW HashSet()
set.add("A")                              // true - added
set.add("B")                              // true - added  
set.add("A")                              // false - duplicate, not added
PRINT set.size()                          // Output: 2
```

**2. Hash-based Storage:**
```
// Elements stored based on hashCode()
CLASS Person:
    name, age
    
    METHOD hashCode():
        RETURN name.hashCode() + age      // Custom hash function
    
    METHOD equals(obj):
        IF obj IS NOT Person: RETURN false
        Person other = (Person) obj
        RETURN this.name.equals(other.name) AND this.age == other.age

set = NEW HashSet()
set.add(NEW Person("John", 25))
set.add(NEW Person("John", 25))           // Duplicate based on equals()
PRINT set.size()                          // Output: 1
```

**3. Performance:**
- **Add:** O(1) average, O(n) worst case
- **Contains:** O(1) average, O(n) worst case  
- **Remove:** O(1) average, O(n) worst case

### **Types of Sets:**

**1. HashSet:**
- No ordering guarantee
- Best performance O(1)
- Allows null element

**2. LinkedHashSet:**
- Maintains insertion order
- Slightly slower than HashSet
- Ordered iteration

**3. TreeSet:**
- Sorted order (natural or comparator)
- O(log n) operations
- Implements NavigableSet

```
// Different Set behaviors
hashSet = NEW HashSet()
hashSet.add("C")
hashSet.add("A")  
hashSet.add("B")
PRINT hashSet                             // Random order: [B, A, C]

linkedHashSet = NEW LinkedHashSet()
linkedHashSet.add("C")
linkedHashSet.add("A")
linkedHashSet.add("B")
PRINT linkedHashSet                       // Insertion order: [C, A, B]

treeSet = NEW TreeSet()
treeSet.add("C")
treeSet.add("A")
treeSet.add("B")
PRINT treeSet                             // Sorted order: [A, B, C]
```

---

## HashMap Internal Working

### **HashMap Structure:**

**Components:**
- **Buckets:** Array of linked lists (or trees in Java 8+)
- **Hash Function:** Converts keys to array indices
- **Collision Handling:** Chaining with linked lists/trees
- **Load Factor:** Controls when to resize (default 0.75)

```
CLASS HashMap<K, V>:
    PRIVATE buckets[]                     // Array of Node references
    PRIVATE size                          // Number of key-value pairs
    PRIVATE threshold                     // size * loadFactor
    PRIVATE loadFactor = 0.75
    PRIVATE DEFAULT_CAPACITY = 16
    
    CLASS Node:
        hash                              // Cached hash code
        key
        value
        next                              // Next node in chain
    
    CONSTRUCTOR():
        buckets = NEW Node[DEFAULT_CAPACITY]
        threshold = DEFAULT_CAPACITY * loadFactor
    
    METHOD put(key, value):
        // Step 1: Calculate hash
        hash = hash(key)
        
        // Step 2: Find bucket index
        index = (buckets.length - 1) & hash  // Efficient modulo
        
        // Step 3: Handle collision or insert
        IF buckets[index] == null:
            buckets[index] = NEW Node(hash, key, value, null)
        ELSE:
            node = buckets[index]
            WHILE true:
                // Check if key already exists
                IF node.hash == hash AND 
                   (node.key == key OR node.key.equals(key)):
                    oldValue = node.value
                    node.value = value    // Update existing
                    RETURN oldValue
                
                // Reached end of chain
                IF node.next == null:
                    node.next = NEW Node(hash, key, value, null)
                    BREAK
                
                node = node.next
        
        size++
        
        // Step 4: Resize if needed
        IF size > threshold:
            resize()
        
        RETURN null                       // New key added
    
    METHOD get(key):
        hash = hash(key)
        index = (buckets.length - 1) & hash
        node = buckets[index]
        
        WHILE node != null:
            IF node.hash == hash AND
               (node.key == key OR node.key.equals(key)):
                RETURN node.value         // Found
            node = node.next
        
        RETURN null                       // Not found
    
    PRIVATE METHOD hash(key):
        IF key == null: RETURN 0
        h = key.hashCode()
        RETURN h ^ (h >>> 16)            // Mix high and low bits
    
    PRIVATE METHOD resize():
        oldBuckets = buckets
        buckets = NEW Node[oldBuckets.length * 2]  // Double capacity
        threshold = buckets.length * loadFactor
        oldSize = size
        size = 0
        
        // Rehash all existing entries
        FOR EACH bucket IN oldBuckets:
            node = bucket
            WHILE node != null:
                nextNode = node.next
                put(node.key, node.value) // Rehash
                node = nextNode
```

### **HashMap vs WeakHashMap:**

**HashMap:**
- **References:** Strong references to keys and values
- **Garbage Collection:** Keys won't be GC'd while in map
- **Memory:** Can cause memory leaks if keys accumulate

**WeakHashMap:**
- **References:** Weak references to keys
- **Garbage Collection:** Keys can be GC'd even if in map
- **Memory:** Automatically removes entries when keys are GC'd

```
// HashMap - Strong References
map = NEW HashMap()
key = NEW Object()
map.put(key, "value")
key = null                               // Remove reference
// Object still reachable through map - won't be GC'd

// WeakHashMap - Weak References  
weakMap = NEW WeakHashMap()
key = NEW Object()
weakMap.put(key, "value")
key = null                               // Remove reference
System.gc()                              // Force garbage collection
// Object may be GC'd and removed from map automatically

CLASS WeakHashMap<K, V>:
    PRIVATE map = NEW HashMap<WeakReference<K>, V>()
    PRIVATE referenceQueue = NEW ReferenceQueue()
    
    METHOD put(key, value):
        expungeStaleEntries()            // Remove GC'd entries
        weakKey = NEW WeakReference(key, referenceQueue)
        RETURN map.put(weakKey, value)
    
    METHOD get(key):
        expungeStaleEntries()
        weakKey = findWeakKey(key)
        RETURN map.get(weakKey)
    
    PRIVATE METHOD expungeStaleEntries():
        // Remove entries whose keys have been GC'd
        WHILE true:
            ref = referenceQueue.poll()
            IF ref == null: BREAK
            map.remove(ref)              // Remove stale entry
```

**Use Cases:**
- **HashMap:** General purpose key-value storage
- **WeakHashMap:** Caches, listeners, observers where keys might become unused

---

## ConcurrentHashMap

### **What is ConcurrentHashMap?**
Thread-safe implementation of HashMap that allows concurrent access without synchronizing the entire map.

### **Evolution of Thread-Safe Maps:**

**1. Hashtable (Legacy):**
```
CLASS Hashtable<K, V>:
    SYNCHRONIZED METHOD put(key, value):
        // Entire method synchronized - poor concurrency
        // Only one thread can access at a time
    
    SYNCHRONIZED METHOD get(key):
        // Even reads are synchronized
```

**2. Collections.synchronizedMap():**
```
map = Collections.synchronizedMap(NEW HashMap())
// Wrapper that synchronizes every method call
// Still poor concurrency - locks entire map
```

**3. ConcurrentHashMap (Modern):**
```
CLASS ConcurrentHashMap<K, V>:
    PRIVATE segments[]                   // Array of segments (Java 7)
    PRIVATE buckets[]                    // Direct buckets (Java 8+)
    
    // Java 8+ Implementation
    METHOD put(key, value):
        hash = hash(key)
        
        WHILE true:
            buckets = this.buckets
            IF buckets == null OR buckets.length == 0:
                initTable()              // Initialize if needed
                CONTINUE
            
            index = (buckets.length - 1) & hash
            head = buckets[index]
            
            IF head == null:
                // Try to insert directly using CAS
                IF compareAndSwap(buckets, index, null, NEW Node(key, value)):
                    BREAK                // Success
                // Retry if CAS failed
            ELSE:
                SYNCHRONIZED(head):      // Lock only this bucket
                    // Insert in chain or tree
                    insertInBucket(head, key, value)
                BREAK
        
        incrementSize()                  // Thread-safe size increment
    
    METHOD get(key):
        // No locking needed for reads in most cases
        hash = hash(key)
        index = (buckets.length - 1) & hash
        head = buckets[index]
        
        WHILE head != null:
            IF head.hash == hash AND head.key.equals(key):
                RETURN head.value        // Found
            head = head.next
        
        RETURN null                      // Not found
```

### **Key Features:**

**1. Segment-based Locking (Java 7):**
```
// Divided into segments, each with its own lock
CLASS Segment:
    PRIVATE lock = NEW ReentrantLock()
    PRIVATE buckets[]
    
    METHOD put(key, value):
        lock.lock()
        TRY:
            // Only this segment is locked
            // Other segments can be accessed concurrently
        FINALLY:
            lock.unlock()
```

**2. CAS Operations (Java 8+):**
```
// Compare-And-Swap for lock-free operations
METHOD compareAndSwap(array, index, expected, newValue):
    // Atomic operation - no locks needed
    IF array[index] == expected:
        array[index] = newValue
        RETURN true
    RETURN false
```

**3. Lock-free Reads:**
```
// Reads don't require locks in most cases
METHOD get(key):
    // Uses volatile reads and careful ordering
    // No synchronization needed for typical reads
```

### **Benefits:**

1. **High Concurrency:** Multiple threads can read/write simultaneously
2. **Lock-free Reads:** Get operations don't block
3. **Scalability:** Performance scales with number of processors
4. **Safety:** Thread-safe without external synchronization

### **Performance Comparison:**

```
// Single-threaded performance
HashMap:           100%     (baseline)
ConcurrentHashMap: 95%      (slight overhead)

// Multi-threaded performance (4 threads)
HashMap + sync:    25%      (serialized access)
ConcurrentHashMap: 350%     (parallel access)
```

---

## Fail-Safe vs Fail-Fast

### **Fail-Fast:**
- **What:** Immediately throws exception when concurrent modification detected
- **When:** During iteration, if collection is modified
- **How:** Uses modCount to detect changes

```
CLASS ArrayList_FailFast:
    PRIVATE modCount = 0                 // Modification counter
    
    METHOD add(element):
        modCount++                       // Increment on every change
        // Add element logic
    
    METHOD remove(element):
        modCount++                       // Increment on every change
        // Remove element logic
    
    CLASS Iterator:
        PRIVATE expectedModCount = modCount  // Snapshot at creation
        
        METHOD next():
            IF modCount != expectedModCount:
                THROW ConcurrentModificationException
            // Return next element

// Fail-Fast Example
list = NEW ArrayList()
list.add("A")
list.add("B")
list.add("C")

iterator = list.iterator()
WHILE iterator.hasNext():
    element = iterator.next()
    list.add("D")                        // Modifies during iteration
    // ConcurrentModificationException thrown!
```

### **Fail-Safe:**
- **What:** Continues iteration even if collection is modified
- **When:** Works on copy of collection or uses different approach
- **How:** Iterator works on snapshot or uses weak consistency

```
CLASS CopyOnWriteArrayList_FailSafe:
    PRIVATE volatile array[]
    
    METHOD add(element):
        SYNCHRONIZED:
            oldArray = array
            newArray = copyOf(oldArray, oldArray.length + 1)
            newArray[oldArray.length] = element
            array = newArray             // Atomic replacement
    
    CLASS Iterator:
        PRIVATE snapshot                 // Snapshot of array at creation
        
        CONSTRUCTOR():
            snapshot = array             // Takes current reference
        
        METHOD next():
            // Always works on snapshot - no fail-fast behavior
            RETURN snapshot[index++]

// Fail-Safe Example
list = NEW CopyOnWriteArrayList()
list.add("A")
list.add("B")
list.add("C")

iterator = list.iterator()
WHILE iterator.hasNext():
    element = iterator.next()
    list.add("D")                        // Safe to modify during iteration
    // Iterator continues with original snapshot
```

### **Comparison:**

| Aspect | Fail-Fast | Fail-Safe |
|--------|-----------|-----------|
| Exception | ConcurrentModificationException | No exception |
| Memory | Original collection | Copy or snapshot |
| Performance | Better | Slower (copying overhead) |
| Consistency | Strong | Weak (may see stale data) |
| Examples | ArrayList, HashMap | CopyOnWriteArrayList, ConcurrentHashMap |

### **Benefits:**

**Fail-Fast Benefits:**
- **Early Error Detection:** Catches concurrency bugs quickly
- **Memory Efficient:** No copying required
- **Strong Consistency:** Always sees current state
- **Performance:** No overhead for copying

**Fail-Safe Benefits:**
- **No Exceptions:** Safe concurrent access
- **Predictable Behavior:** Iteration always completes
- **Thread Safety:** Built-in concurrent access support
- **Stability:** Won't crash due to concurrent modifications

---

## Queue and Priority Queue

### **What is Queue?**
Queue is a FIFO (First-In-First-Out) data structure that provides ordered processing of elements.

### **Queue Interface:**
```
INTERFACE Queue<E> EXTENDS Collection<E>:
    METHOD offer(element): boolean       // Add to rear
    METHOD poll(): E                     // Remove from front
    METHOD peek(): E                     // Look at front without removing
    METHOD add(element): boolean         // Throws exception if fails
    METHOD remove(): E                   // Throws exception if empty
    METHOD element(): E                  // Throws exception if empty
```

### **Basic Queue Implementation:**
```
CLASS ArrayQueue<E>:
    PRIVATE elements[]
    PRIVATE front = 0                    // Index of front element
    PRIVATE rear = 0                     // Index of next insertion point
    PRIVATE size = 0
    
    METHOD offer(element):
        IF size == elements.length:
            RETURN false                 // Queue full
        elements[rear] = element
        rear = (rear + 1) % elements.length  // Circular wrap
        size++
        RETURN true
    
    METHOD poll():
        IF size == 0:
            RETURN null                  // Queue empty
        element = elements[front]
        elements[front] = null           // Help GC
        front = (front + 1) % elements.length
        size--
        RETURN element
    
    METHOD peek():
        IF size == 0:
            RETURN null
        RETURN elements[front]

// Usage
queue = NEW ArrayQueue()
queue.offer("First")                     // Add to rear
queue.offer("Second")
queue.offer("Third")

element = queue.poll()                   // Remove from front -> "First"
next = queue.peek()                      // Look at front -> "Second"
```

### **Priority Queue:**
- **What:** Queue where elements are served based on priority, not insertion order
- **Order:** Natural ordering or custom Comparator
- **Implementation:** Usually heap data structure

```
CLASS PriorityQueue<E>:
    PRIVATE heap[]                       // Min-heap by default
    PRIVATE size = 0
    PRIVATE comparator                   // Optional custom comparator
    
    METHOD offer(element):
        IF size == heap.length:
            resize()
        
        // Insert at end and bubble up
        heap[size] = element
        bubbleUp(size)
        size++
    
    METHOD poll():
        IF size == 0:
            RETURN null
        
        result = heap[0]                 // Root is minimum
        heap[0] = heap[size - 1]         // Move last to root
        heap[size - 1] = null
        size--
        
        IF size > 0:
            bubbleDown(0)                // Restore heap property
        
        RETURN result
    
    METHOD peek():
        IF size == 0:
            RETURN null
        RETURN heap[0]                   // Root element
    
    PRIVATE METHOD bubbleUp(index):
        WHILE index > 0:
            parentIndex = (index - 1) / 2
            IF compare(heap[index], heap[parentIndex]) >= 0:
                BREAK                    // Heap property satisfied
            
            swap(heap, index, parentIndex)
            index = parentIndex
    
    PRIVATE METHOD bubbleDown(index):
        WHILE true:
            leftChild = 2 * index + 1
            rightChild = 2 * index + 2
            smallest = index
            
            IF leftChild < size AND 
               compare(heap[leftChild], heap[smallest]) < 0:
                smallest = leftChild
            
            IF rightChild < size AND
               compare(heap[rightChild], heap[smallest]) < 0:
                smallest = rightChild
            
            IF smallest == index:
                BREAK                    // Heap property satisfied
            
            swap(heap, index, smallest)
            index = smallest
    
    PRIVATE METHOD compare(a, b):
        IF comparator != null:
            RETURN comparator.compare(a, b)
        ELSE:
            RETURN a.compareTo(b)        // Natural ordering

// Usage Examples

// 1. Natural Ordering (Min-Heap)
pq = NEW PriorityQueue()
pq.offer(30)
pq.offer(10)
pq.offer(20)
PRINT pq.poll()                          // Output: 10 (minimum)
PRINT pq.poll()                          // Output: 20
PRINT pq.poll()                          // Output: 30

// 2. Custom Comparator (Max-Heap)
maxHeap = NEW PriorityQueue((a, b) -> b.compareTo(a))
maxHeap.offer(30)
maxHeap.offer(10)
maxHeap.offer(20)
PRINT maxHeap.poll()                     // Output: 30 (maximum)

// 3. Custom Objects
CLASS Task:
    name, priority
    
    METHOD compareTo(other):
        RETURN this.priority - other.priority  // Lower number = higher priority

taskQueue = NEW PriorityQueue()
taskQueue.offer(NEW Task("Low Priority", 3))
taskQueue.offer(NEW Task("High Priority", 1))
taskQueue.offer(NEW Task("Medium Priority", 2))

task = taskQueue.poll()                  // Returns "High Priority" task
```

### **Queue vs Priority Queue:**

| Feature | Queue (FIFO) | Priority Queue |
|---------|--------------|----------------|
| Ordering | Insertion order | Priority order |
| poll() | First inserted | Highest priority |
| offer() | O(1) | O(log n) |
| poll() | O(1) | O(log n) |
| peek() | O(1) | O(1) |
| Use Case | Fair processing | Important-first processing |

### **Real-World Applications:**

**Regular Queue:**
- **Print Queue:** Documents printed in order
- **Task Scheduling:** Process tasks in FIFO order
- **BFS Algorithm:** Level-order traversal

**Priority Queue:**
- **Operating System:** Process scheduling by priority
- **Dijkstra's Algorithm:** Shortest path finding
- **A* Search:** Pathfinding in games
- **Event Simulation:** Process events by time
- **Hospital Emergency:** Treat patients by severity

```
// Emergency Room Example
CLASS Patient:
    name, severity  // 1=Critical, 2=Urgent, 3=Non-urgent
    
    METHOD compareTo(other):
        RETURN this.severity - other.severity  // Lower = higher priority

emergencyQueue = NEW PriorityQueue()
emergencyQueue.offer(NEW Patient("John", 3))      // Non-urgent
emergencyQueue.offer(NEW Patient("Alice", 1))     // Critical
emergencyQueue.offer(NEW Patient("Bob", 2))       // Urgent

// Patients treated by priority
nextPatient = emergencyQueue.poll()               // Alice (Critical)
nextPatient = emergencyQueue.poll()               // Bob (Urgent)  
nextPatient = emergencyQueue.poll()               // John (Non-urgent)
```

This comprehensive guide covers all major Core Java concepts with pseudo code examples perfect for interview preparation!
