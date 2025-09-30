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

**Java 17 & 21 Exception Handling Improvements:**

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
    
    METHOD getLimit(): RETURN limit
    METHOD getAttemptedAmount(): RETURN attemptedAmount
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

---

## Senior Java Developer Enhancements

## Real-World Use Cases & Best Practices
- Use Streams for batch processing, filtering, and transformation of large datasets.
- Apply Optional for safe null handling in service and DAO layers.
- Use method references for cleaner event handling and callbacks.
- Prefer immutable collections for thread safety and predictability.
- Leverage functional interfaces for strategy patterns and event listeners.

## Performance Considerations & Pitfalls
- Avoid excessive chaining of streams for very large collections; consider parallel streams or batch processing.
- Be cautious with parallel streams: only use when operations are stateless and thread-safe.
- Minimize boxing/unboxing in streams by using primitive streams (IntStream, LongStream).
- Avoid using Optional in fields or method parameters; use only for return types.
- Profile memory usage when using lambdas and streams in high-throughput systems.

## Advanced Code Examples
### Custom Collector Example
```java
// Group employees by department and count
Map<String, Long> deptCounts = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

// Custom collector for string concatenation
Collector<String, StringBuilder, String> customConcat = Collector.of(
    StringBuilder::new,
    StringBuilder::append,
    StringBuilder::append,
    StringBuilder::toString
);
List<String> words = Arrays.asList("Java", "is", "awesome");
String result = words.stream().collect(customConcat); // "Javaisawesome"
```

### Advanced CompletableFuture Usage
```java
// Combine multiple async tasks and handle exceptions
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> fetchData1());
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> fetchData2());
CompletableFuture<String> combined = future1.thenCombine(future2, (a, b) -> a + b)
    .exceptionally(ex -> "Fallback");
String value = combined.get();
```

## Concurrency, Memory Management & JVM Internals
- Use ExecutorService for controlled thread pools and resource management.
- Prefer ForkJoinPool for recursive parallel tasks (divide-and-conquer algorithms).
- Monitor JVM memory with tools like VisualVM, JMC, and Flight Recorder.
- Understand garbage collection algorithms (G1, ZGC, Shenandoah) and their impact on latency.
- Use synchronized blocks, locks, and concurrent collections for thread safety.
- Avoid shared mutable state; prefer stateless or immutable objects.

## Design Patterns & Architectural Decisions
- Singleton: Use enum for thread-safe singletons.
- Factory: Use functional interfaces for dynamic object creation.
- Strategy: Pass lambdas or method references for runtime behavior changes.
- Observer: Use event listeners and streams for reactive programming.
- Builder: Use builder pattern for complex object construction, especially with immutable objects.
- Layered architecture: Separate concerns (Controller, Service, DAO, Model).
- Dependency Injection: Use frameworks (Spring, CDI) for loose coupling and testability.

## Example: Strategy Pattern with Lambdas
```java
interface PaymentStrategy { void pay(double amount); }
class CreditCardPayment implements PaymentStrategy { public void pay(double amount) { /* ... */ } }
class PaypalPayment implements PaymentStrategy { public void pay(double amount) { /* ... */ } }
void processPayment(double amount, PaymentStrategy strategy) { strategy.pay(amount); }
// Usage:
processPayment(100.0, amount -> System.out.println("Paid " + amount + " via Lambda"));
```

## Example: Thread-Safe Singleton with Enum
```java
public enum ConfigManager {
    INSTANCE;
    private Properties config = new Properties();
    public Properties getConfig() { return config; }
}
```

## Example: JVM Profiling & Monitoring
- Use `jcmd`, `jstat`, `jmap`, and `jstack` for JVM diagnostics.
- Enable Flight Recorder: `java -XX:StartFlightRecording=duration=60s,filename=recording.jfr -jar app.jar`
- Analyze heap dumps for memory leaks and GC tuning.

############################################################
# Summary
############################################################
These enhancements make corejava.md a robust resource for senior Java developer interviews, covering advanced use cases, performance, concurrency, JVM internals, and design patterns.

############################################################
# Thread & Concurrency Interview Questions (with Sample Code)
############################################################

1. What is a thread in Java?
A: A thread is a lightweight process for concurrent execution within a program. Java threads share memory and resources, enabling parallelism.
```java
// Creating and starting a simple thread
Thread t = new Thread(() -> System.out.println("Hello from thread!"));
t.start();
```

2. How do you create a thread in Java?
A: By extending the Thread class or implementing the Runnable/Callable interface and passing it to a Thread or ExecutorService.
```java
// Extending Thread
class MyThread extends Thread {
    public void run() { System.out.println("Thread via extends"); }
}
new MyThread().start();

// Implementing Runnable
Runnable r = () -> System.out.println("Thread via Runnable");
new Thread(r).start();

// Using Callable with ExecutorService
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<String> future = executor.submit(() -> "Result from Callable");
System.out.println(future.get());
executor.shutdown();
```

3. What is the difference between Runnable and Callable?
A: Runnable's run() returns void and cannot throw checked exceptions; Callable's call() returns a value and can throw checked exceptions.
```java
// Runnable
Runnable r = () -> System.out.println("Runnable running");
new Thread(r).start();

// Callable
Callable<Integer> c = () -> 42;
ExecutorService ex = Executors.newSingleThreadExecutor();
Future<Integer> f = ex.submit(c);
System.out.println(f.get());
ex.shutdown();
```

4. What is thread safety?
A: Thread safety means code can be safely executed by multiple threads at the same time without causing data corruption or unexpected behavior.
```java
// Thread-safe increment using synchronized
class Counter {
    private int count = 0;
    public synchronized void increment() { count++; }
    public int get() { return count; }
}
```

5. What is synchronization?
A: Synchronization is a mechanism to control access to shared resources by multiple threads, preventing race conditions.
```java
class SyncExample {
    private final Object lock = new Object();
    public void safeMethod() {
        synchronized(lock) {
            // critical section
        }
    }
}
```

6. What is a deadlock?
A: Deadlock occurs when two or more threads are blocked forever, each waiting for the other to release a resource.
```java
// Deadlock example
Object lock1 = new Object();
Object lock2 = new Object();
Thread t1 = new Thread(() -> {
    synchronized(lock1) {
        try { Thread.sleep(100); } catch (Exception e) {}
        synchronized(lock2) {}
    }
});
Thread t2 = new Thread(() -> {
    synchronized(lock2) {
        try { Thread.sleep(100); } catch (Exception e) {}
        synchronized(lock1) {}
    }
});
t1.start(); t2.start();
```

7. What is a race condition?
A: A race condition happens when the outcome depends on the timing or sequence of threads accessing shared data.
```java
// Race condition example
class UnsafeCounter {
    private int count = 0;
    public void increment() { count++; }
}
// Multiple threads calling increment() can cause incorrect results.
```

8. What is the difference between wait(), notify(), and notifyAll()?
A: wait() causes a thread to release the lock and wait; notify() wakes up one waiting thread; notifyAll() wakes up all waiting threads.
```java
class WaitNotifyExample {
    private final Object lock = new Object();
    public void waitMethod() throws InterruptedException {
        synchronized(lock) { lock.wait(); }
    }
    public void notifyMethod() {
        synchronized(lock) { lock.notify(); }
    }
}
```

9. What is the Executor framework?
A: The Executor framework provides a high-level API for managing threads, thread pools, and asynchronous task execution.
```java
ExecutorService pool = Executors.newFixedThreadPool(4);
pool.submit(() -> System.out.println("Task executed"));
pool.shutdown();
```

10. What is the difference between synchronized block and synchronized method?
A: Synchronized block locks only a specific section of code; synchronized method locks the entire method.
```java
class SyncDemo {
    public synchronized void syncMethod() { /* locked on this */ }
    public void syncBlock() {
        synchronized(this) { /* locked on this */ }
    }
}
```

11. What is volatile keyword?
A: volatile ensures visibility of changes to variables across threads, preventing caching of variable values.
```java
class VolatileExample {
    private volatile boolean running = true;
    public void stop() { running = false; }
    public void run() { while (running) { /* ... */ } }
}
```

12. What is the difference between Thread.sleep() and Object.wait()?
A: Thread.sleep() pauses the thread for a specified time; Object.wait() releases the lock and waits for notify/notifyAll.
```java
Thread.sleep(1000); // Pauses thread for 1 second
synchronized(lock) { lock.wait(); } // Releases lock and waits
```

13. What is a daemon thread?
A: Daemon threads run in the background and do not prevent JVM from exiting when all user threads finish.
```java
Thread daemon = new Thread(() -> {/* ... */});
daemon.setDaemon(true);
daemon.start();
```

14. How do you handle thread exceptions?
A: Use UncaughtExceptionHandler or handle exceptions within the thread's run/call method.
```java
Thread t = new Thread(() -> { throw new RuntimeException("Error"); });
t.setUncaughtExceptionHandler((th, ex) -> System.out.println("Caught: " + ex));
t.start();
```

15. What is the difference between start() and run() in Thread?
A: start() creates a new thread and calls run(); run() executes in the current thread if called directly.
```java
Thread t = new Thread(() -> System.out.println("run"));
t.start(); // New thread
// t.run(); // Runs in current thread
```

16. What is ScheduledExecutorService and how is it used?
A: ScheduledExecutorService allows scheduling tasks to run after a delay or periodically.
```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
scheduler.schedule(() -> System.out.println("Delayed"), 1, TimeUnit.SECONDS);
scheduler.scheduleAtFixedRate(() -> System.out.println("Periodic"), 0, 1, TimeUnit.SECONDS);
```

17. What is CompletionService and when should you use it?
A: CompletionService combines Executor and BlockingQueue to process completed tasks as soon as they finish.
```java
CompletionService<String> service = new ExecutorCompletionService<>(executor);
service.submit(() -> "task1");
Future<String> completed = service.take(); // blocks until a task completes
```

18. How do you gracefully shut down an ExecutorService?
A: Use shutdown() to stop accepting new tasks and awaitTermination() to wait for completion. Use shutdownNow() to attempt to stop all running tasks.
```java
executor.shutdown();
if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
    executor.shutdownNow();
}
```

19. What are virtual threads in Java 21+ and how do they differ from platform threads?
A: Virtual threads are lightweight, managed by the JVM, and allow millions of concurrent tasks. Platform threads are mapped to OS threads.
```java
ExecutorService vExecutor = Executors.newVirtualThreadPerTaskExecutor();
vExecutor.submit(() -> System.out.println("Virtual thread running"));
vExecutor.shutdown();
```

20. How do you handle exceptions in virtual threads?
A: Exceptions in virtual threads are propagated to the Future or can be handled with an UncaughtExceptionHandler.
```java
Future<?> f = vExecutor.submit(() -> { throw new RuntimeException("Error"); });
try { f.get(); } catch (ExecutionException e) { /* handle */ }
```

21. What is structured concurrency and how is it supported in Java 21?
A: Structured concurrency scopes multiple concurrent tasks, ensuring they complete or fail together.
```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Future<String> t1 = scope.fork(() -> fetchUserData())
    Future<String> t2 = scope.fork(() -> fetchPreferences())
    Future<String> t3 = scope.fork(() -> fetchHistory())
    
    scope.join()
    scope.throwIfFailed()
    
    System.out.println(t1.resultNow() + t2.resultNow() + t3.resultNow())
}
```

22. What are best practices for using ExecutorService and virtual threads?
A:
- Use fixed thread pools for predictable workloads.
- Use virtual threads for high concurrency and blocking I/O.
- Always shut down ExecutorService to avoid resource leaks.
- Avoid blocking on native calls in virtual threads.
- Use non-blocking I/O and be cautious with thread-local variables.
````markdown
############################################################
# Collections Framework (Java) - Senior Developer Guide
############################################################

## Collections Overview & Diagram
The Java Collections Framework provides unified architecture for storing, retrieving, and manipulating groups of objects. It includes interfaces (Collection, List, Set, Map, Queue) and concrete implementations.

```
+-------------------+
| Collection        |
+-------------------+
        ^
        |
+-------------------+        +-------------------+
| List              |        | Set               |
+-------------------+        +-------------------+
        ^                        ^
        |                        |
+-------------------+    +-------------------+    +-------------------+
| ArrayList         |    | HashSet           |    | SortedSet         |
| LinkedList        |    | LinkedHashSet     |    | TreeSet           |
| Vector            |    +-------------------+    +-------------------+
+-------------------+

+-------------------+
| Map               |
+-------------------+
        ^
        |
+-------------------+    +-------------------+    +-------------------+
| HashMap           |    | LinkedHashMap     |    | TreeMap           |
+-------------------+    +-------------------+    +-------------------+
```

## Key Interfaces & Implementations
- **Collection**: Root interface for List, Set, and Queue.
- **List**: Ordered, allows duplicates. (ArrayList, LinkedList, Vector)
- **Set**: Unordered, no duplicates. (HashSet, LinkedHashSet, TreeSet)
- **Map**: Key-value pairs, keys unique. (HashMap, LinkedHashMap, TreeMap)
- **Queue**: FIFO structure. (PriorityQueue, ArrayDeque)

## Sample Code
```java
List<String> list = new ArrayList<>();
Set<Integer> set = new HashSet<>();
Map<String, Integer> map = new HashMap<>();
Queue<String> queue = new LinkedList<>();
```

## Internal Working Structures
### HashMap
- Uses array of buckets, each bucket is a linked list or tree (Java 8+).
- Hash function computes index; collisions handled by chaining or tree.
- Keys must implement hashCode() and equals().
```
+-------------------+
|  HashMap Table    |
+-------------------+
| [0] -> Entry -> Entry -> ...
| [1] -> Entry
| [2] -> Entry -> ...
| ...
+-------------------+
```
```java
Map<String, Integer> map = new HashMap<>();
map.put("A", 1); // hash("A") -> index
```

### HashSet
- Backed by HashMap; stores elements as keys, dummy value as value.
- Unordered, no duplicates.
```java
Set<String> set = new HashSet<>();
set.add("A"); // Internally: map.put("A", PRESENT)
```

### TreeSet
- Backed by TreeMap; elements sorted by natural order or Comparator.
- Red-black tree for O(log n) operations.
```java
Set<Integer> treeSet = new TreeSet<>();
treeSet.add(5); treeSet.add(1); treeSet.add(3); // Sorted order
```

### LinkedHashMap
- HashMap with doubly-linked list for predictable iteration order.
- Maintains insertion order.
```java
Map<String, Integer> lhm = new LinkedHashMap<>();
lhm.put("A", 1); lhm.put("B", 2); // Iteration order: A, B
```

## Fail-Fast vs Fail-Safe Iterators
- **Fail-Fast**: Throws ConcurrentModificationException if collection is modified during iteration (e.g., ArrayList, HashMap).
- **Fail-Safe**: Iterators work on a copy, no exception (e.g., ConcurrentHashMap, CopyOnWriteArrayList).
```java
List<String> list = new ArrayList<>();
for (String s : list) { list.add("X"); } // Fail-fast: throws exception

ConcurrentHashMap<String, Integer> cmap = new ConcurrentHashMap<>();
for (String k : cmap.keySet()) { cmap.put("Y", 2); } // Fail-safe: no exception
```

## Advanced Collection Concepts
- **Concurrent Collections**: Thread-safe (ConcurrentHashMap, CopyOnWriteArrayList).
- **Immutability**: Use Collections.unmodifiableList(), List.of(), Set.of() for read-only collections.
- **Custom Comparators**: For sorting TreeSet/TreeMap.
- **Performance Tips**: Prefer ArrayList for random access, LinkedList for frequent insert/delete, HashMap for fast lookup.
- **Thread Safety**: Use synchronized collections or concurrent collections for multi-threaded code.

## Example: Custom Comparator
```java
Set<String> sorted = new TreeSet<>(Comparator.comparingInt(String::length));
sorted.add("Java"); sorted.add("Spring"); // Sorted by length
```

## Example: Unmodifiable Collection
```java
List<String> immutable = Collections.unmodifiableList(new ArrayList<>(List.of("A", "B")));
```

## Example: Concurrent Collection
```java
ConcurrentMap<String, Integer> cmap = new ConcurrentHashMap<>();
cmap.put("A", 1);
```

## Best Practices
- Always choose the right collection for your use case.
- Prefer interfaces (List, Set, Map) for variable types.
- Use generics for type safety.
- Avoid modifying collections during iteration unless using fail-safe.
- For thread safety, use concurrent collections or proper synchronization.

############################################################
# End of Collections Section
############################################################
