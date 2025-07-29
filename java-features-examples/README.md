# Java Version Features Guide

This repository contains comprehensive examples of features introduced in Java 8, 11, 17, and 21.

## Java Version Overview

| Version | Release Date | Type | Key Features |
|---------|--------------|------|--------------|
| Java 8  | March 2014   | LTS  | Lambda Expressions, Streams, Optional |
| Java 11 | Sept 2018    | LTS  | HTTP Client, String methods, Files API |
| Java 17 | Sept 2021    | LTS  | Sealed Classes, Pattern Matching, Records |
| Java 21 | Sept 2023    | LTS  | Virtual Threads, Pattern Matching for Switch |

## Running the Examples

Each Java file can be run independently to see the features in action:

```bash
# Compile and run Java 8 features
javac Java8Features.java
java Java8Features

# Compile and run Java 11 features  
javac Java11Features.java
java Java11Features

# Compile and run Java 17 features
javac Java17Features.java
java Java17Features

# Compile and run Java 21 features
javac Java21Features.java
java Java21Features
```

## Java 8 Features

### 1. Lambda Expressions
- Functional programming support
- Concise syntax for anonymous functions
- Enables better API design

### 2. Stream API
- Functional-style operations on collections
- Parallel processing support
- Lazy evaluation

### 3. Optional
- Better null handling
- Reduces NullPointerException
- Functional programming patterns

### 4. Date-Time API
- Immutable date-time classes
- Better timezone handling
- Thread-safe operations

## Java 11 Features

### 1. HTTP Client
- Native HTTP/2 support
- Asynchronous operations
- WebSocket support

### 2. String Methods
- `isBlank()`, `strip()`, `lines()`
- Better text processing
- Unicode-aware operations

### 3. Files API Enhancement
- `readString()`, `writeString()`
- Simplified file operations
- Better error handling

## Java 17 Features

### 1. Sealed Classes
- Restricted class hierarchies
- Better pattern matching
- Improved API design

### 2. Records
- Immutable data carriers
- Automatic methods generation
- Concise syntax

### 3. Pattern Matching for instanceof
- Type checking with casting
- Cleaner code
- Better readability

## Java 21 Features

### 1. Virtual Threads
- Lightweight threads
- Better scalability
- Simplified concurrent programming

### 2. Pattern Matching for Switch
- More powerful switch statements
- Pattern destructuring
- Guards support

### 3. Sequenced Collections
- First/last element access
- Ordered collections API
- Reverse iteration

## Migration Guide

### From Java 8 to 11
- Update HTTP client usage
- Use new String methods
- Leverage Files API improvements

### From Java 11 to 17
- Consider sealed classes for restricted hierarchies
- Use records for data classes
- Apply pattern matching for instanceof

### From Java 17 to 21
- Adopt virtual threads for I/O intensive applications
- Use pattern matching for switch where applicable
- Leverage sequenced collections for ordered data

## Best Practices

1. **Use appropriate Java version for your project**
   - LTS versions for production
   - Latest for new features

2. **Gradual migration**
   - Update one feature at a time
   - Test thoroughly
   - Consider backward compatibility

3. **Performance considerations**
   - Virtual threads for I/O intensive apps
   - Parallel streams for CPU intensive tasks
   - Use appropriate garbage collector

4. **Code quality**
   - Use Optional to avoid nulls
   - Prefer immutable objects (records)
   - Apply functional programming where appropriate

## Resources

- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [OpenJDK](https://openjdk.java.net/)
- [Java Feature Timeline](https://en.wikipedia.org/wiki/Java_version_history)
