# Design Patterns & Architectural Concepts - Interview Guide
## Quick Reference for Technical Interviews

---

## Categories of Design Patterns

### **Creational Patterns:**
These patterns deal with object creation mechanisms, aiming to create objects in a manner suitable for a particular situation.

- **Singleton:** Ensures a class has only one instance and provides a global access point to it.
```java
class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}
```

- **Factory Method:** Defines an interface for creating objects, but lets subclasses decide which class to instantiate.
```java
interface Product { void operation(); }
class ProductA implements Product { public void operation() { /* ... */ } }
class ProductB implements Product { public void operation() { /* ... */ } }
class Factory {
    public Product createProduct(String type) {
        if ("A".equals(type)) return new ProductA();
        if ("B".equals(type)) return new ProductB();
        return null;
    }
}
```

- **Abstract Factory:** Provides an interface for creating families of related or dependent objects without specifying their concrete classes.
```java
interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}
class ConcreteFactory1 implements AbstractFactory {
    public ProductA createProductA() { return new ProductA1(); }
    public ProductB createProductB() { return new ProductB1(); }
}

- **Builder:** Separates the construction of a complex object from its representation, allowing the same construction process to create different representations.
```java
class Product {
    String prop1;
    String prop2;
}
class Builder {
    private Product product = new Product();
    public Builder setProp1(String val) { product.prop1 = val; return this; }
    public Builder setProp2(String val) { product.prop2 = val; return this; }
    public Product build() { return product; }
}
```

- **Prototype:** Creates new objects by copying an existing object (the prototype).
```java
interface Prototype { Prototype clone(); }
class ConcretePrototype implements Prototype {
    String data;
    public ConcretePrototype(String data) { this.data = data; }
    public Prototype clone() { return new ConcretePrototype(this.data); }
}
```

### **Structural Patterns:**
These patterns focus on how classes and objects can be composed to form larger structures, promoting flexibility and efficiency.

- **Adapter:** Allows objects with incompatible interfaces to collaborate.
```java
interface Target { void operation(); }
class Adaptee { void specificOperation() { /* ... */ } }
class Adapter implements Target {
    private Adaptee adaptee;
    public Adapter(Adaptee adaptee) { this.adaptee = adaptee; }
    public void operation() { adaptee.specificOperation(); }
}
```

- **Bridge:** Decouples an abstraction from its implementation, allowing them to vary independently.
```java
interface Implementation { void operationImpl(); }
class ConcreteImplementationA implements Implementation {
    public void operationImpl() { /* ... */ }
}
class Abstraction {
    protected Implementation implementation;
    public Abstraction(Implementation implementation) { this.implementation = implementation; }
    public void operation() { implementation.operationImpl(); }
}
```

- **Composite:** Composes objects into tree structures to represent part-whole hierarchies.
```java
interface Component { void operation(); }
class Leaf implements Component {
    public void operation() { /* ... */ }
}
class Composite implements Component {
    private List<Component> children = new ArrayList<>();
    public void add(Component c) { children.add(c); }
    public void operation() { for (Component c : children) c.operation(); }
}
```

- **Decorator:** Attaches new responsibilities to an object dynamically.
```java
interface Component { void operation(); }
class ConcreteComponent implements Component {
    public void operation() { /* ... */ }
}
class Decorator implements Component {
    private Component component;
    public Decorator(Component component) { this.component = component; }
    public void operation() {
        component.operation();
        additionalBehavior();
    }
    private void additionalBehavior() { /* ... */ }
}
```

- **Facade:** Provides a simplified interface to a complex subsystem.
```java
class Subsystem1 { void complexOperation1() { /* ... */ } }
class Subsystem2 { void complexOperation2() { /* ... */ } }
class Facade {
    private Subsystem1 s1 = new Subsystem1();
    private Subsystem2 s2 = new Subsystem2();
    public void simpleOperation() {
        s1.complexOperation1();
        s2.complexOperation2();
    }
}
```

- **Flyweight:** Reduces memory usage by sharing common parts of state among multiple objects.
```java
class Flyweight {
    private String intrinsicState;
    public Flyweight(String state) { this.intrinsicState = state; }
    public void operation(String extrinsicState) {
        // Use both intrinsic and extrinsic state
    }
}
class FlyweightFactory {
    private Map<String, Flyweight> pool = new HashMap<>();
    public Flyweight getFlyweight(String key) {
        return pool.computeIfAbsent(key, Flyweight::new);
    }
}
```

- **Proxy:** Provides a surrogate or placeholder for another object to control access to it.
```java
interface Subject { void operation(); }
class RealSubject implements Subject {
    public void operation() { /* ... */ }
}
class Proxy implements Subject {
    private RealSubject realObject = new RealSubject();
    public void operation() {
        if (hasAccess()) realObject.operation();
    }
    private boolean hasAccess() { return true; }
}
```

### **Behavioral Patterns:**
These patterns focus on communication and interaction between objects, defining how objects interact and distribute responsibilities.

- **Chain of Responsibility:** Passes requests along a chain of handlers.
```java
abstract class Handler {
    protected Handler nextHandler;
    public void setNext(Handler next) { this.nextHandler = next; }
    public void handle(Request request) {
        if (canHandle(request)) process(request);
        else if (nextHandler != null) nextHandler.handle(request);
    }
    protected abstract boolean canHandle(Request request);
    protected abstract void process(Request request);
}
```

- **Command:** Encapsulates a request as an object, thereby allowing for parameterization of clients with different requests, queuing or
logging of requests, and support for undoable operations.
```java
interface Command { void execute(); }
class ConcreteCommand implements Command {
    private Receiver receiver;
    public ConcreteCommand(Receiver receiver) { this.receiver = receiver; }
    public void execute() { receiver.action(); }
}
class Invoker {
    private Command command;
    public void setCommand(Command cmd) { this.command = cmd; }
    public void invoke() { command.execute(); }
}

- **Interpreter:** Given a language, defines a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.
```java
interface Expression { boolean interpret(Context context); }
class TerminalExpression implements Expression {
    public boolean interpret(Context context) { /* ... */ return true; }
}
```

- **Iterator:** Provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.
```java
interface Iterator<T> {
    boolean hasNext();
    T next();
}
class ConcreteIterator<T> implements Iterator<T> {
    private List<T> collection;
    private int position = 0;
    public ConcreteIterator(List<T> collection) { this.collection = collection; }
    public boolean hasNext() { return position < collection.size(); }
    public T next() { return collection.get(position++); }
}
```

- **Mediator:** Defines an object that encapsulates how a set of objects interact.
```java
interface Mediator { void notify(Component sender, String event); }
class ConcreteMediator implements Mediator {
    public void notify(Component sender, String event) {
        // Coordinate between components
    }
}
```

- **Memento:** Captures and externalizes an object's internal state so that the object can be restored to this state later.
```java
class Memento {
    private String state;
    public Memento(String state) { this.state = state; }
    public String getState() { return state; }
}
class Originator {
    private String state;
    public Memento createMemento() { return new Memento(state); }
    public void restore(Memento memento) { state = memento.getState(); }
}
```

- **Observer:** Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
```java
interface Observer { void update(String data); }
class Subject {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer obs) { observers.add(obs); }
    public void notifyObservers(String data) {
        for (Observer obs : observers) obs.update(data);
    }
}
```

- **State:** Allows an object to alter its behavior when its internal state changes.
```java
interface State { void handle(Context context); }
class Context {
    private State state;
    public void setState(State state) { this.state = state; }
    public void request() { state.handle(this); }
}
```

- **Strategy:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
```java
interface Strategy { void execute(); }
class ConcreteStrategyA implements Strategy {
    public void execute() { /* ... */ }
}
class Context {
    private Strategy strategy;
    public void setStrategy(Strategy strategy) { this.strategy = strategy; }
    public void doWork() { strategy.execute(); }
}
```

- **Template Method:** Defines the skeleton of an algorithm in an operation, deferring some steps to subclasses.
```java
abstract class AbstractClass {
    public final void templateMethod() {
        step1();
        step2(); // Abstract - subclass implements
        step3();
    }
    protected abstract void step2();
    protected void step1() { /* ... */ }
    protected void step3() { /* ... */ }
}
```

- **Visitor:** Represents an operation to be performed on the elements of an object structure. It lets you define a new operation without changing the classes of the elements on which it operates.
```java
interface Visitor { void visit(Element element); }
interface Element { void accept(Visitor visitor); }
class ConcreteElement implements Element {
    public void accept(Visitor visitor) { visitor.visit(this); }
}
```

---

## Common Design Patterns - Quick Implementation

### **1. Observer Pattern**
**Concept:** One-to-many dependency notification

```
INTERFACE Observer:
    METHOD update(data)

CLASS Subject:
    observers = []
    METHOD addObserver(obs) → observers.add(obs)
    METHOD notify() → FOR EACH obs: obs.update(data)

// Usage: Subject notifies all observers automatically
```

### **2. Command Pattern**
**Concept:** Encapsulate requests as objects

```
INTERFACE Command:
    METHOD execute()

CLASS ConcreteCommand:
    METHOD execute() → receiver.action()

CLASS Invoker:
    METHOD setCommand(cmd) → this.command = cmd
    METHOD invoke() → command.execute()

// Usage: Queuing, undo/redo, batch processing
```

### **3. Singleton Pattern**
**Concept:** Single instance per application

```
CLASS Singleton:
    STATIC instance = null
    PRIVATE constructor()

    STATIC METHOD getInstance():
        IF instance == null: instance = NEW Singleton()
        RETURN instance

// Usage: Database connections, loggers, configuration
```

### **4. Strategy Pattern**
**Concept:** Interchangeable algorithms

```
INTERFACE Strategy:
    METHOD execute(data)

CLASS Context:
    strategy
    METHOD setStrategy(s) → strategy = s
    METHOD doWork() → strategy.execute(data)

// Usage: Payment methods, file formats, sorting algorithms
```

### **5. Factory Pattern**
**Concept:** Create objects without specifying exact class

```
INTERFACE Product:
    METHOD operation()

CLASS Factory:
    METHOD createProduct(type):
        IF type == "A": RETURN NEW ProductA()
        IF type == "B": RETURN NEW ProductB()

// Usage: Creating objects based on conditions
```

### **6. Builder Pattern**
**Concept:** Step-by-step object construction

```
CLASS Builder:
    product
    METHOD setProperty1(val) → product.prop1 = val; RETURN this
    METHOD setProperty2(val) → product.prop2 = val; RETURN this
    METHOD build() → RETURN product

// Usage: Complex objects with optional parameters
```

---

## SAGA Pattern

### **What is SAGA Design Pattern?**
SAGA is a design pattern for managing **distributed transactions** across multiple microservices.
It ensures data consistency through a sequence of local transactions, where each service performs its work and publishes events.
If any step fails, compensating actions are executed to undo previous steps.

### **How SAGA Manages Distributed Transactions:**
- **Problem:** Traditional ACID transactions don't work across microservices
- **Solution:** Break large transaction into smaller, local transactions
- **Consistency:** Eventual consistency through compensation
- **Reliability:** Each step can be undone if later steps fail

### **Orchestration vs Choreography**

**Orchestration (Centralized Control):**
- **What:** Central coordinator manages the entire transaction flow
- **How:** Orchestrator calls each service and handles compensation
```
CLASS SagaOrchestrator:
    METHOD executeTransaction():
        TRY:
            step1Result = service1.action()
            step2Result = service2.action()
            step3Result = service3.action()
        CATCH failure:
            compensate() // Reverse order: service2.undo(), service1.undo()
```

**Choreography (Event-driven Decentralized):**
- **What:** Services coordinate themselves through events
- **How:** Each service knows what to do when specific events occur
```
SERVICE A: complete → publish(A_COMPLETED)
SERVICE B: listen(A_COMPLETED) → execute → publish(B_COMPLETED)
SERVICE C: listen(B_COMPLETED) → execute → publish(C_COMPLETED)

// On failure: publish(COMPENSATE) → all services undo
```

### **How They Work Together:**

**Orchestration Flow:**
1. **Client** → calls **Orchestrator**
2. **Orchestrator** → calls **Service A** → gets result
3. **Orchestrator** → calls **Service B** → gets result
4. **Orchestrator** → calls **Service C** → gets result
5. **Success:** Transaction complete
6. **Failure:** Orchestrator calls compensation in reverse order

**Choreography Flow:**
1. **Service A** → completes work → publishes **A_COMPLETED** event
2. **Service B** → listens **A_COMPLETED** → executes → publishes **B_COMPLETED**
3. **Service C** → listens **B_COMPLETED** → executes → publishes **C_COMPLETED**
4. **Success:** All services completed
5. **Failure:** Failed service publishes **COMPENSATE** → all previous services undo

### **Benefits Comparison:**

**Orchestration Benefits:**
- ✅ **Central control** - Easy to understand transaction flow
- ✅ **Easier debugging** - Single point to track transaction state
- ✅ **Simpler testing** - Test orchestrator logic in one place
- ✅ **Clear responsibility** - Orchestrator owns the transaction
- ✅ **Better monitoring** - Centralized logging and metrics

**Choreography Benefits:**
- ✅ **High autonomy** - Services are independent and loosely coupled
- ✅ **Better scalability** - No central bottleneck
- ✅ **Fault tolerance** - No single point of failure
- ✅ **Event-driven** - Natural fit for event-based architectures
- ✅ **Service independence** - Each service manages its own logic

### **When to Use Which:**

**Use Orchestration When:**
- Complex business logic requiring tight control
- Need clear transaction visibility
- Team prefers centralized coordination
- Easier debugging is priority

**Use Choreography When:**
- High scalability requirements
- Event-driven architecture already in place
- Services need maximum autonomy
- Avoiding single points of failure

**Key Points:**
- **Use case:** Distributed transactions across microservices
- **Benefit:** Eventual consistency without 2PC (Two-Phase Commit)
- **Challenge:** Compensating transactions must be idempotent
- **Pattern Choice:** Orchestration for control, Choreography for autonomy

---

## SOLID Principles

### **What are SOLID Principles?**
SOLID is an acronym for five design principles that make software designs more understandable, flexible, and maintainable. These principles guide object-oriented programming and design to create robust, scalable applications.

**Why SOLID Principles Matter:**
- **Maintainability:** Easier to modify and extend code
- **Testability:** Code becomes more testable and mockable
- **Flexibility:** Easy to adapt to changing requirements
- **Reusability:** Components can be reused across applications
- **Reduced Coupling:** Classes depend less on each other

---

### **1. Single Responsibility Principle (SRP)**
**Definition:** A class should have only one reason to change, meaning it should have only one responsibility or job.

**Why Important:**
- **Focused Purpose:** Each class has a clear, single purpose
- **Easier Testing:** Smaller scope means simpler unit tests
- **Reduced Impact:** Changes affect fewer parts of the system
- **Better Organization:** Code is logically grouped by responsibility

**Example:**
```
// WRONG: Multiple responsibilities (violates SRP)
CLASS UserManager:
    validateUser(), saveUser(), sendEmail(), generateReport()
    // This class does too many different things!

// CORRECT: Single responsibility per class
CLASS UserValidator: validateUser()     // Only validation
CLASS UserRepository: saveUser()        // Only data access
CLASS EmailService: sendEmail()         // Only email sending
CLASS ReportGenerator: generateReport() // Only report creation
```

**Benefits:**
- ✅ Easier to understand and maintain
- ✅ Simpler testing (test one responsibility at a time)
- ✅ Better code reuse
- ✅ Reduced risk when making changes

---

### **2. Open/Closed Principle (OCP)**
**Definition:** Software entities should be open for extension but closed for modification. You should be able to add new functionality without changing existing code.

**Why Important:**
- **Stability:** Existing code remains unchanged and stable
- **Extensibility:** New features can be added without risk
- **Backward Compatibility:** Old functionality continues to work
- **Reduced Regression:** Less chance of breaking existing features

**Example:**
```
// WRONG: Must modify existing code for new features
METHOD calculate(type):
    IF type == "A": logic_A()
    IF type == "B": logic_B()
    // Adding type "C" requires modifying this method!

// CORRECT: Extend without modifying existing code
INTERFACE Calculator: calculate()
CLASS CalculatorA: calculate() // Implementation A
CLASS CalculatorB: calculate() // Implementation B  
CLASS CalculatorC: calculate() // New implementation - no modification needed!

CLASS CalculatorFactory:
    METHOD getCalculator(type): RETURN appropriate calculator
```

**Benefits:**
- ✅ Easy to add new functionality
- ✅ Existing code remains untouched
- ✅ Reduced risk of introducing bugs
- ✅ Better maintainability over time

---

### **3. Liskov Substitution Principle (LSP)**
**Definition:** Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.

**Why Important:**
- **Polymorphism Reliability:** Subclasses work correctly in place of parent
- **Contract Preservation:** Subclasses honor parent class contracts
- **Predictable Behavior:** Code behaves consistently across inheritance
- **Interface Integrity:** APIs remain stable across implementations

**Example:**
```
// WRONG: Subclass changes expected behavior
CLASS Bird: 
    METHOD fly(): // All birds should fly
CLASS Penguin EXTENDS Bird:
    METHOD fly(): THROW "Penguins cannot fly" // Violates LSP!

// CORRECT: Consistent behavior across hierarchy
INTERFACE Bird: eat(), move()
INTERFACE FlyingBird EXTENDS Bird: fly()
INTERFACE SwimmingBird EXTENDS Bird: swim()

CLASS Eagle IMPLEMENTS FlyingBird:
    METHOD fly(): // Eagles can fly
    METHOD move(): fly() // Uses flying for movement

CLASS Penguin IMPLEMENTS SwimmingBird:
    METHOD swim(): // Penguins can swim  
    METHOD move(): swim() // Uses swimming for movement
```

**Benefits:**
- ✅ Reliable polymorphism
- ✅ Predictable inheritance behavior
- ✅ Better API design
- ✅ Stronger type safety

---

### **4. Interface Segregation Principle (ISP)**
**Definition:** Clients should not be forced to depend on interfaces they don't use. Create multiple specific interfaces rather than one general-purpose interface.

**Why Important:**
- **Focused Dependencies:** Classes depend only on what they need
- **Reduced Coupling:** Smaller interfaces mean fewer dependencies
- **Better Flexibility:** Changes to unused methods don't affect clients
- **Cleaner Architecture:** Interfaces are more focused and cohesive

**Example:**
```
// WRONG: Fat interface forces unnecessary dependencies
INTERFACE Worker: 
    work(), eat(), sleep(), manage(), code(), design()
    // Not all workers need all these methods!

CLASS Developer IMPLEMENTS Worker:
    work(), eat(), sleep() // Needed
    manage(), code(), design() // Some not needed - forced dependency!

// CORRECT: Segregated interfaces based on actual needs
INTERFACE Workable: work()
INTERFACE Eatable: eat()  
INTERFACE Sleepable: sleep()
INTERFACE Manageable: manage()
INTERFACE Codeable: code()
INTERFACE Designable: design()

CLASS Developer IMPLEMENTS Workable, Eatable, Sleepable, Codeable:
    // Only implements what it actually needs
CLASS Manager IMPLEMENTS Workable, Eatable, Sleepable, Manageable:
    // Different combination based on role
```

**Benefits:**
- ✅ Clients depend only on methods they use
- ✅ Reduced coupling between components
- ✅ Better testability with focused interfaces
- ✅ More flexible architecture

---

### **5. Dependency Inversion Principle (DIP)**
**Definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

**Why Important:**
- **Loose Coupling:** High-level logic independent of implementation details
- **Testability:** Easy to inject mock dependencies for testing
- **Flexibility:** Easy to swap implementations
- **Maintainability:** Changes to low-level modules don't affect high-level logic

**Example:**
```
// WRONG: High-level depends on concrete low-level class
CLASS OrderService:
    database = NEW MySQLDatabase() // Direct dependency on concrete class
    emailService = NEW GmailService() // Hard to test or change
    
    METHOD processOrder(order):
        database.save(order) // Tightly coupled to MySQL
        emailService.send() // Tightly coupled to Gmail

// CORRECT: Depend on abstractions, inject dependencies
INTERFACE Database: save(), find()
INTERFACE EmailService: send()

CLASS OrderService:
    database // Interface dependency
    emailService // Interface dependency
    
    CONSTRUCTOR(database, emailService): // Dependency injection
        this.database = database
        this.emailService = emailService
    
    METHOD processOrder(order):
        database.save(order) // Works with any database implementation
        emailService.send() // Works with any email service

// Implementations depend on abstractions
CLASS MySQLDatabase IMPLEMENTS Database: save(), find()
CLASS PostgreSQLDatabase IMPLEMENTS Database: save(), find()
CLASS GmailService IMPLEMENTS EmailService: send()
CLASS OutlookService IMPLEMENTS EmailService: send()
```

**Benefits:**
- ✅ Easy testing with mock implementations
- ✅ Flexible architecture - easy to swap implementations
- ✅ Reduced coupling between layers
- ✅ Better maintainability and extensibility

---

### **How SOLID Principles Work Together:**

**In Practice:**
1. **SRP:** Each class has one job (User, UserValidator, UserRepository)
2. **OCP:** Add new user types without modifying existing classes
3. **LSP:** All user types work consistently in the same contexts
4. **ISP:** Separate interfaces for different user capabilities
5. **DIP:** Inject dependencies rather than hard-coding them

**Real-World Benefits:**
- **Development Speed:** Faster feature development
- **Code Quality:** Higher quality, more maintainable code
- **Team Collaboration:** Easier for teams to work on different parts
- **System Evolution:** Applications adapt better to changing requirements



## Quick Interview Answers

**Q: When to use Observer vs Strategy?**
**A:** Observer for notifications (1-to-many), Strategy for algorithms (1-to-1)

**Q: SAGA vs 2PC?**
**A:** SAGA for long-running transactions, 2PC for short atomic transactions

**Q: Which SOLID principle for testability?**
**A:** DIP - inject mock dependencies

**Q: Singleton drawbacks?**
**A:** Global state, hard to test, violates SRP

**Q: Builder vs Factory?**
**A:** Builder for complex construction, Factory for simple object creation

---

## Banking Domain Examples

**Observer:** Account balance changes notify compliance, risk, notifications
**Command:** Batch payment processing with undo capability
**Strategy:** Different payment methods (card, transfer, wallet)
**SAGA:** Multi-service payment flow (validate → reserve → transfer → notify)
**Factory:** Create different account types (savings, current, loan)

This guide covers essential concepts for technical interviews without implementation details.

---

## GoF (Gang of Four) Design Patterns

**What is it?**
The GoF Design Patterns are 23 classic solutions to common software design problems, introduced in the book "Design Patterns: Elements of Reusable Object-Oriented Software" by Gamma, Helm, Johnson, and Vlissides. They are grouped into Creational, Structural, and Behavioral patterns.

**Why use it?**
- Provides proven, reusable solutions for recurring design challenges
- Improves code maintainability, flexibility, and scalability
- Facilitates communication among developers using a common vocabulary

**Real-World Scenario & Code Example:**

### Example: Strategy Pattern (Behavioral)
*Scenario: Payment processing system that supports multiple payment methods (Credit Card, PayPal, UPI)*

```java
// Strategy interface
public interface PaymentStrategy {
    void pay(double amount);
}

// Concrete strategies
public class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " by Credit Card");
    }
}
public class PaypalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " by PayPal");
    }
}

// Context
public class PaymentContext {
    private PaymentStrategy strategy;
    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    public void executePayment(double amount) {
        strategy.pay(amount);
    }
}

// Usage
PaymentContext context = new PaymentContext(new CreditCardPayment());
context.executePayment(100.0);
context = new PaymentContext(new PaypalPayment());
context.executePayment(200.0);
```

### Example: Singleton Pattern (Creational)
*Scenario: Application configuration manager that should have only one instance*

```java
public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private ConfigManager() {}
    public static ConfigManager getInstance() {
        return INSTANCE;
    }
    // config methods...
}
```

### Example: Observer Pattern (Behavioral)
*Scenario: Notification system where multiple listeners react to an event*

```java
public interface Observer {
    void update(String message);
}
public class EmailObserver implements Observer {
    public void update(String message) {
        System.out.println("Email: " + message);
    }
}
public class SmsObserver implements Observer {
    public void update(String message) {
        System.out.println("SMS: " + message);
    }
}
public class NotificationService {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer o) { observers.add(o); }
    public void notifyAll(String msg) {
        for (Observer o : observers) o.update(msg);
    }
}
// Usage
NotificationService service = new NotificationService();
service.addObserver(new EmailObserver());
service.addObserver(new SmsObserver());
service.notifyAll("Order shipped!");
```

**Summary:**
GoF patterns help solve design problems in a standardized way. For interviews, be ready to explain the pattern, its intent, and show a real-world code example.
