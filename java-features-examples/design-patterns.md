# Design Patterns & Principles for Banking/Enterprise Interviews

## Why Design Patterns Matter in Interviews
Design patterns and principles show your ability to write scalable, maintainable, and robust code. In banking and enterprise systems, they help solve common problems like security, transaction management, and integration. Interviewers expect you to know patterns, apply them to real-world scenarios, and explain your choices clearly.

---

## Quick Reference Table: Patterns & Banking Use Cases
| Pattern/Principle         | Definition/Principle                        | Banking Example/Use Case                  |
|--------------------------|----------------------------------------------|-------------------------------------------|
| Singleton                | One instance, global access                  | AuditLogger for all transactions          |
| Builder                  | Step-by-step object construction             | Loan application builder                  |
| Prototype                | Clone objects                                | Account template cloning                  |
| Factory Method           | Subclass decides object creation             | NotificationFactory (Email/SMS)           |
| Abstract Factory         | Create families of related objects           | UIFactory for web/mobile portal           |
| Object Pool              | Reuse expensive objects                      | DB ConnectionPool                         |
| Adapter                  | Bridge incompatible interfaces               | SMSAdapter for legacy AlertService        |
| Facade                   | Simplified interface to subsystem            | BankFacade for account/loan/card ops      |
| Decorator                | Add responsibilities dynamically             | Audit logging for transactions            |
| Proxy                    | Surrogate for access control                 | SecurityProxy for fund transfer           |
| Flyweight                | Share objects efficiently                    | AccountType sharing                       |
| Composite                | Tree structure for part-whole                | Branch with sub-branches/accounts         |
| Bridge                   | Separate abstraction from implementation     | Notification abstraction (Email/SMS)      |
| Observer                 | Notify on state changes                      | Compliance/risk team notification         |
| Chain of Responsibility  | Pass request along handlers                  | Loan approval workflow                    |
| Visitor                  | Separate algorithm from object               | ReportVisitor for reporting               |
| Command                  | Encapsulate request as object                | Batch loan approval/rejection             |
| Strategy                 | Select algorithm at runtime                  | File upload validation strategies         |
| State                    | Change behavior by state                     | Account state: Active/Frozen              |
| Iterator                 | Sequential access to collection              | Iterate customer accounts                 |
| Mediator                 | Coordinate object interaction                | ChatMediator for user messaging           |
| Interpreter              | Grammar and sentence interpretation          | Custom query language for reports         |
| Memento                  | Capture/restore object state                 | Account undo/restore                      |
| SOLID Principles         | Core OOP design principles                   | All banking modules                       |
| DRY, KISS, etc.          | General design principles                    | All banking modules                       |

---

# Design Principles

## SOLID
- **Single Responsibility Principle:** One class should have one and only one responsibility.
- **Open Closed Principle:** Software components should be open for extension, but closed for modification.
- **Liskov’s Substitution Principle:** Derived types must be completely substitutable for their base types.
- **Interface Segregation Principle:** Make fine-grained interfaces that are client specific. Don't create fatty interfaces.
- **Dependency Inversion Principle:** High-level modules should not depend on low-level modules. Both should depend on abstractions.

## More Design Principles
- **DRY (Don't Repeat Yourself):** Avoid code duplication.
- **Encapsulate what changes:** Hide implementation details for maintainability.
- **Favor Composition over Inheritance:** Reuse code without inflexibility.
- **Code to Interface:** Improves flexibility and maintenance.
- **Delegation Principle:** Delegate tasks instead of doing everything yourself.
- **KISS (Keep It Simple, Stupid):** Avoid unnecessary complexity.

---

# Design Patterns: Definitions & Banking Examples

## 1. Creational Patterns
*Creational patterns abstract object creation, making systems independent of how objects are created.*

- **Singleton:** Ensure a class has only one instance and provide a global point of access.
  - *Bank Example:* Only one AuditLogger for all transactions
- **Builder:** Construct complex objects step by step.
  - *Bank Example:* Build a loan application
- **Prototype:** Clone existing objects instead of creating new ones.
  - *Bank Example:* Clone a standard account template
- **Factory Method:** Subclass decides which class to instantiate.
  - *Bank Example:* NotificationFactory creates Email/SMS notification
- **Abstract Factory:** Create families of related objects without specifying concrete classes.
  - *Bank Example:* UIFactory creates web/mobile UI components for banking portal
- **Object Pool:** Reuse objects that are expensive to create.
  - *Bank Example:* ConnectionPool for DB connections

## 2. Structural Patterns
*Structural patterns deal with object composition and relationships between objects.*

- **Adapter:** Allow incompatible interfaces to work together.
  - *Bank Example:* SMSAdapter adapts legacy SMS system to new AlertService
- **Facade:** Provide a simplified interface to a complex subsystem.
  - *Bank Example:* BankFacade handles account, loan, and card services
- **Decorator:** Add responsibilities to objects dynamically.
  - *Bank Example:* Add audit logging to transactions
- **Proxy:** Provide a surrogate for another object to control access.
  - *Bank Example:* SecurityProxy checks permissions before fund transfer
- **Flyweight:** Share objects to support large numbers efficiently.
  - *Bank Example:* Share AccountType objects for all accounts of same type
- **Composite:** Compose objects into tree structures to represent part-whole hierarchies.
  - *Bank Example:* Branch contains sub-branches and accounts
- **Bridge:** Separate abstraction from implementation.
  - *Bank Example:* Notification (abstraction) uses Email/SMS (implementation)

## 3. Behavioral Patterns
*Behavioral patterns manage algorithms and responsibilities between objects.*

- **Observer:** Notify multiple objects about state changes.
  - *Bank Example:* Notify compliance and risk teams on large transaction
- **Chain of Responsibility:** Pass requests along a chain of handlers.
  - *Bank Example:* Loan approval passes through clerk, manager, director
- **Visitor:** Separate algorithms from objects on which they operate.
  - *Bank Example:* ReportVisitor visits Account, Loan, Card for reporting
- **Command:** Encapsulate a request as an object.
  - *Bank Example:* Batch process approve/reject loan commands
- **Strategy:** Select an algorithm at runtime.
  - *Bank Example:* Different validation strategies for file upload
- **State:** Change behavior when internal state changes.
  - *Bank Example:* Account state changes from Active to Frozen
- **Iterator:** Access elements of a collection sequentially.
  - *Bank Example:* Iterate over all customer accounts
- **Mediator:** Define an object that coordinates interaction between objects.
  - *Bank Example:* ChatMediator coordinates messages between users
- **Interpreter:** Define a grammar and interpret sentences.
  - *Bank Example:* Parse and execute custom query language for reports
- **Memento:** Capture and restore an object's state.
  - *Bank Example:* Save and restore account state for undo

---

## How to Discuss Design Patterns in Interviews
- **Start with the definition:** Give a one-line summary of the pattern.
- **Explain the banking/enterprise use case:** Relate the pattern to a real-world scenario.
- **Discuss pros and cons:** Mention why you chose the pattern and any trade-offs.
- **Show code snippets if asked:** Be ready to write a simple example.
- **Mention alternatives:** If relevant, discuss other patterns that could be used.

---

> **Tip:** Use the summary table above for quick reference during interviews. Practice explaining patterns with banking examples for maximum impact.
