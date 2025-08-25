# Design Patterns, SAGA Pattern & SOLID Principles Guide
## Enterprise Application Design - Interview Preparation

---

## Table of Contents
1. [Design Patterns in Banking Applications](#design-patterns-in-banking-applications)
2. [SAGA Design Pattern](#saga-design-pattern)
3. [SOLID Principles](#solid-principles)
4. [Interview Questions & Concepts](#interview-questions--concepts)

---

## Design Patterns in Banking Applications
batchProcessor.processAll()
domainManager = DomainManager.getInstance()
maxLimit = domainManager.getConfiguration("MAX_TRANSACTION_LIMIT")
connection = domainManager.getConnection()
messagingServices = [emailAdapter, smsAdapter]
csvGenerator.generateResponseFile(transactionData)

### Observer Pattern
// Notify compliance and risk teams on large transaction
interface Observer { update(msg); }
class ComplianceTeam implements Observer { update(msg) { alertCompliance(msg); } }
class RiskTeam implements Observer { update(msg) { alertRisk(msg); } }
class TransactionNotifier {
  List<Observer> observers;
  void add(Observer o) { observers.add(o); }
  void notifyAll(String msg) { for (o : observers) o.update(msg); }
}
// Usage: notifier.add(new ComplianceTeam()); notifier.add(new RiskTeam()); notifier.notifyAll("Large txn");

### Singleton Pattern
// Only one instance of AuditLogger for all transactions
class AuditLogger {
  private static AuditLogger instance;
  private AuditLogger() {}
  public static AuditLogger getInstance() {
    if (instance == null) instance = new AuditLogger();
    return instance;
  }
}
// Usage: AuditLogger.getInstance().log(txn);

### Builder Pattern
// Build a complex fund transfer request
class FundTransferBuilder {
  String from, to; double amount; String note;
  FundTransferBuilder setFrom(String f) { from = f; return this; }
  FundTransferBuilder setTo(String t) { to = t; return this; }
  FundTransferBuilder setAmount(double a) { amount = a; return this; }
  FundTransferBuilder setNote(String n) { note = n; return this; }
  FundTransfer build() { return new FundTransfer(from, to, amount, note); }
}
// Usage: new FundTransferBuilder().setFrom("A").setTo("B").setAmount(1000).build();

### Strategy Pattern
// Validate different payment file formats
interface ValidationStrategy { validate(File f); }
class CSVValidation implements ValidationStrategy { validate(f) { /*csv rules*/ } }
class XMLValidation implements ValidationStrategy { validate(f) { /*xml rules*/ } }
class FileProcessor {
  ValidationStrategy strategy;
  void setStrategy(ValidationStrategy s) { strategy = s; }
  void process(File f) { strategy.validate(f); }
}
// Usage: processor.setStrategy(new CSVValidation()); processor.process(file);

### Adapter Pattern
// Integrate legacy SMS and new email APIs for alerts
interface AlertService { send(String to, String msg); }
class LegacySMS { void sendSMS(String num, String msg) { ... } }
class SMSAdapter implements AlertService {
  LegacySMS sms;
  void send(String to, String msg) { sms.sendSMS(to, msg); }
}
// Usage: new SMSAdapter(new LegacySMS()).send("123", "Alert");

### Factory Method Pattern
// Create notification objects for different channels
interface Notification { send(String to, String msg); }
class EmailNotification implements Notification { send(to, msg) { ... } }
class SmsNotification implements Notification { send(to, msg) { ... } }
abstract class NotificationFactory { abstract Notification create(); }
class EmailFactory extends NotificationFactory { Notification create() { return new EmailNotification(); } }
// Usage: new EmailFactory().create().send("user@bank.com", "msg");

### Template Method Pattern
// Generate daily transaction report files
abstract class ReportGenerator {
  void generate() {
    fetchData();
    process();
    format();
    export();
  }
  abstract void fetchData();
  abstract void process();
  abstract void format();
  abstract void export();
}
// Usage: new CsvReportGenerator().generate();


## SAGA Design Pattern
paymentRequest = NEW PaymentRequest(
    fromAccount: "ACC123",
    toAccount: "ACC456",
    amount: 1000.00,
    userId: "USER789"
)
IF result.isSuccess():
    PRINT "Payment completed successfully"
ELSE:
    PRINT "Payment failed: " + result.errorMessage

### What is SAGA Pattern?
SAGA manages distributed transactions across microservices using a series of local transactions and compensating actions.

**Banking Example (Orchestration):**
// SAGA: Payment transfer with compensation
class PaymentSaga {
  void execute(PaymentRequest req) {
    try {
      accountService.debit(req.from, req.amount);
      paymentService.transfer(req);
      accountService.credit(req.to, req.amount);
    } catch (Exception e) {
      // Compensation: refund if any step fails
      accountService.refund(req.from, req.amount);
    }
  }
}

**Banking Example (Choreography):**
// Each service listens for events and triggers next step
eventBus.publish(new PaymentInitiated(req));
// AccountService listens, debits, then publishes AccountDebited event, etc.

// Key: Each step is a local transaction; failures trigger compensating events.

**When to Use:** Distributed transactions, long-running business processes, eventual consistency.

**Benefits:**
- Ensures consistency across services
- Automatic compensation for failures
- Scalable and resilient

**Challenges:**
- More complex than single DB transactions
- Requires compensation logic and event tracking


## SOLID Principles

### 1. Single Responsibility Principle (SRP)
// Banking Example: Each class has one responsibility
class AccountCreator { void create(Account a); }
class AccountEmailSender { void sendWelcome(Account a); }
class AccountLogger { void log(Account a); }

### 2. Open/Closed Principle (OCP)
// Banking Example: Add new notification channel without modifying existing code
interface NotificationChannel { void send(String to, String msg); }
class EmailChannel implements NotificationChannel { void send(String to, String msg) { /* send email */ } }
class SmsChannel implements NotificationChannel { void send(String to, String msg) { /* send sms */ } }
// Add WhatsAppChannel by extending, not editing logic

### 3. Liskov Substitution Principle (LSP)
// Banking Example: All payment processors can be used interchangeably
abstract class PaymentProcessor { abstract boolean process(double amt); }
class CreditCardProcessor extends PaymentProcessor { boolean process(double amt) { /*...*/ return true; } }
class UPIProcessor extends PaymentProcessor { boolean process(double amt) { /*...*/ return true; } }
// Usage: PaymentProcessor p = new CreditCardProcessor(); p.process(1000);

### 4. Interface Segregation Principle (ISP)
// Banking Example: Split interfaces for teller and loan officer
interface DepositOps { void deposit(double amt); }
interface LoanOps { void approveLoan(double amt); }
class Teller implements DepositOps { public void deposit(double amt) { /*...*/ } }
class LoanOfficer implements LoanOps { public void approveLoan(double amt) { /*...*/ } }

### 5. Dependency Inversion Principle (DIP)
// Banking Example: High-level module depends on abstraction
interface Notifier { void notifyUser(String msg); }
class EmailNotifier implements Notifier { public void notifyUser(String msg) { /*...*/ } }
class TransactionService {
  Notifier notifier;
  TransactionService(Notifier n) { notifier = n; }
  void processTxn() { /*...*/ notifier.notifyUser("Txn done"); }
}



## Interview Questions & Concepts

### **Design Pattern Questions:**

**Q1: When would you use Observer Pattern over Command Pattern?**
**A:** Use Observer for event-driven scenarios where multiple objects need real-time notifications. Use Command for queuing operations, undo functionality, and batch processing.

**Q2: Explain the difference between Strategy and State patterns.**
**A:** Strategy pattern changes algorithm behavior, while State pattern changes object behavior based on internal state. Strategy is about choosing different ways to do something; State is about doing different things based on current condition.

**Q3: How does SAGA pattern handle distributed transaction failures?**
**A:** SAGA executes compensating transactions in reverse order to undo completed steps. Each service must implement both forward and compensating operations.

### **SOLID Principles Questions:**

**Q4: How does Dependency Inversion Principle improve testability?**
**A:** By depending on interfaces rather than concrete classes, you can easily inject mock implementations during testing, making unit tests isolated and reliable.

**Q5: Can you violate one SOLID principle to satisfy another?**
**A:** Generally no, but sometimes there are trade-offs. For example, adding methods to an interface (ISP violation) might be acceptable to maintain LSP compliance.

### **Practical Application:**

**Q6: How would you refactor a monolithic payment system using these patterns?**
**A:** 
1. Use SAGA for distributed transactions
2. Apply SRP to separate concerns (validation, processing, notification)
3. Use Strategy for different payment methods
4. Implement Factory for creating payment processors
5. Apply DIP for loose coupling between services

This comprehensive guide provides the conceptual understanding and pseudo code examples needed for technical interviews focusing on design patterns and architectural principles in enterprise banking applications.
