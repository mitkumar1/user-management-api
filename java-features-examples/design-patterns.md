
# Design Principles

## SOLID

**S - Single Responsibility Principle**

“One class should have one and only one responsibility”

**O - Open Closed Principle**

“Software components should be open for extension, but closed for modification”

**L - Liskov’s Substitution Principle**

“Derived types must be completely substitutable for their base types”

**I - Interface Segregation Principle**

Make fine grained interfaces that are client specific.

Don't create fatty interface, Implementation classes require to implement unnecessary methods

**D - Dependency Inversion Principle**

Dependency Inversion principle states that:

High-level modules should not depend on low-level modules. Both should depend on abstractions.
Abstractions should not depend on details. Details should depend on abstractions.

## Few More Design Principles

1. **DRY (Don't Repeat Yourself)**
   - Avoid code duplication

2. **Encapsulate what changes**
   - Hides implementation details, helps in maintenance

3. **Favor Composition over Inheritance**
   - Code re-usability without cost of inflexibility

4. **Code to Interface**
   - Helps in maintenance, improves flexibility

5. **Delegation Principle**
   - Do not do all the stuff by yourself, delegate it

6. **KISS (Keep It Simple, Stupid)**
   - Keep solutions simple and avoid unnecessary complexity

---

# Design Patterns: Definitions & Banking Examples
## 1. Creational Patterns
*Creational patterns deal with object creation mechanisms, trying to create objects in a manner suitable to the situation. They abstract the instantiation process and make a system independent of how its objects are created.*

**Singleton:** Ensure a class has only one instance and provide a global point of access.
// Bank Example: Only one AuditLogger for all transactions
class AuditLogger { private static AuditLogger inst; private AuditLogger() {} public static AuditLogger get() { if (inst==null) inst=new AuditLogger(); return inst; } }

**Builder:** Construct complex objects step by step.
// Bank Example: Build a loan application
LoanBuilder b = new LoanBuilder().setAmount(10000).setType("Home").build();

**Prototype:** Clone existing objects instead of creating new ones.
// Bank Example: Clone a standard account template
Account template = new Account(); Account copy = template.clone();

**Factory Method:** Define an interface for creating objects, but let subclasses decide which class to instantiate.
// Bank Example: NotificationFactory creates Email/SMS notification
Notification n = new EmailFactory().create();

**Abstract Factory:** Create families of related objects without specifying their concrete classes.
// Bank Example: UIFactory creates web/mobile UI components for banking portal
UIFactory f = new WebUIFactory(); Button b = f.createButton();

**Object Pool:** Reuse objects that are expensive to create.
// Bank Example: ConnectionPool for DB connections
Connection c = pool.getConnection(); pool.release(c);
## 2. Structural Patterns
*Structural patterns deal with object composition, creating relationships between objects to form larger structures and ensure that if one part changes, the entire structure does not need to.*

**Adapter:** Allow incompatible interfaces to work together.
// Bank Example: SMSAdapter adapts legacy SMS system to new AlertService
AlertService s = new SMSAdapter(new LegacySMS());

**Facade:** Provide a simplified interface to a complex subsystem.
// Bank Example: BankFacade handles account, loan, and card services
facade.openAccountAndIssueCard();

**Decorator:** Add responsibilities to objects dynamically.
// Bank Example: Add audit logging to transactions
Transaction t = new AuditDecorator(new FundTransfer());

**Proxy:** Provide a surrogate for another object to control access.
// Bank Example: SecurityProxy checks permissions before fund transfer
TransferService s = new SecurityProxy(new RealTransferService());

**Flyweight:** Share objects to support large numbers efficiently.
// Bank Example: Share AccountType objects for all accounts of same type
AccountType t = AccountTypeFactory.get("SAVINGS");

**Composite:** Compose objects into tree structures to represent part-whole hierarchies.
// Bank Example: Branch contains sub-branches and accounts
Branch main = new Branch(); main.add(new Branch()); main.add(new Account());

**Bridge:** Separate abstraction from implementation.
// Bank Example: Notification (abstraction) uses Email/SMS (implementation)
Notification n = new Notification(new EmailSender());
## 3. Behavioral Patterns
*Behavioral patterns are concerned with algorithms and the assignment of responsibilities between objects. They help manage how objects interact and communicate with each other.*

**Observer:** Notify multiple objects about state changes.
// Bank Example: Notify compliance and risk teams on large transaction
notifier.add(new ComplianceTeam()); notifier.notifyAll("Large txn");

**Chain of Responsibility:** Pass requests along a chain of handlers.
// Bank Example: Loan approval passes through clerk, manager, director
clerk.setNext(manager).setNext(director); clerk.handle(request);

**Visitor:** Separate algorithms from objects on which they operate.
// Bank Example: ReportVisitor visits Account, Loan, Card for reporting
account.accept(new ReportVisitor());

**Command:** Encapsulate a request as an object.
// Bank Example: Batch process approve/reject loan commands
processor.add(new ApproveLoanCommand()); processor.processAll();

**Strategy:** Select an algorithm at runtime.
// Bank Example: Different validation strategies for file upload
processor.setStrategy(new CSVValidation());

**State:** Change behavior when internal state changes.
// Bank Example: Account state changes from Active to Frozen
account.setState(new FrozenState());

**Iterator:** Access elements of a collection sequentially.
// Bank Example: Iterate over all customer accounts
for(Account a : bank) { ... }

**Mediator:** Define an object that coordinates interaction between objects.
// Bank Example: ChatMediator coordinates messages between users
mediator.send("msg", user1, user2);

**Interpreter:** Define a grammar and interpret sentences.
// Bank Example: Parse and execute custom query language for reports
interpreter.interpret("SELECT * FROM accounts WHERE balance > 1000");

**Memento:** Capture and restore an object's state.
// Bank Example: Save and restore account state for undo
memento = account.saveState(); account.restoreState(memento);
