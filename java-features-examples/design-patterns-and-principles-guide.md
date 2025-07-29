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

### **1. Observer Pattern - Automessaging Module**

**Concept:** Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified automatically.

**When to Use:** When multiple components need to be notified of state changes or events.

**Pseudo Code:**
```
INTERFACE Observer:
    METHOD update(message)

INTERFACE Subject:
    METHOD addObserver(observer)
    METHOD removeObserver(observer)
    METHOD notifyObservers()

CLASS MessageBroadcaster IMPLEMENTS Subject:
    PRIVATE observerList = []
    PRIVATE messageContent
    
    METHOD addObserver(observer):
        observerList.add(observer)
    
    METHOD removeObserver(observer):
        observerList.remove(observer)
    
    METHOD notifyObservers():
        FOR EACH observer IN observerList:
            observer.update(messageContent)
    
    METHOD setMessage(message):
        messageContent = message
        notifyObservers()

CLASS ComplianceNotifier IMPLEMENTS Observer:
    METHOD update(message):
        PRINT "Compliance team notified: " + message
        sendToComplianceSystem(message)

CLASS RiskNotifier IMPLEMENTS Observer:
    METHOD update(message):
        PRINT "Risk team notified: " + message
        updateRiskDashboard(message)

// Usage
messageBroadcaster = NEW MessageBroadcaster()
messageBroadcaster.addObserver(NEW ComplianceNotifier())
messageBroadcaster.addObserver(NEW RiskNotifier())
messageBroadcaster.setMessage("New regulatory update available")
```

**Benefits:**
- Loose coupling between subject and observers
- Dynamic subscription/unsubscription
- Broadcast communication

---

### **2. Command Pattern - Batch Notification Module**

**Concept:** Encapsulates a request as an object, allowing parameterization of clients with queues, requests, and operations.

**When to Use:** For batch processing, queuing operations, undo functionality, and logging.

**Pseudo Code:**
```
INTERFACE Command:
    METHOD execute()
    METHOD undo()

CLASS NotificationCommand IMPLEMENTS Command:
    PRIVATE recipient
    PRIVATE message
    PRIVATE notificationService
    PRIVATE executionResult
    
    CONSTRUCTOR(recipient, message, notificationService):
        this.recipient = recipient
        this.message = message
        this.notificationService = notificationService
    
    METHOD execute():
        executionResult = notificationService.send(recipient, message)
        RETURN executionResult
    
    METHOD undo():
        IF executionResult.isSuccess():
            notificationService.recall(recipient, message)

CLASS BatchProcessor:
    PRIVATE commandQueue = []
    PRIVATE executedCommands = []
    
    METHOD addCommand(command):
        commandQueue.add(command)
    
    METHOD processAll():
        WHILE commandQueue.isNotEmpty():
            command = commandQueue.remove()
            TRY:
                command.execute()
                executedCommands.add(command)
            CATCH exception:
                PRINT "Command failed: " + exception.message
                rollbackExecutedCommands()
    
    METHOD rollbackExecutedCommands():
        FOR EACH command IN executedCommands REVERSE:
            command.undo()

// Usage
batchProcessor = NEW BatchProcessor()
batchProcessor.addCommand(NEW NotificationCommand("user1@bank.com", "Payment due", emailService))
batchProcessor.addCommand(NEW NotificationCommand("user2@bank.com", "Account update", emailService))
batchProcessor.processAll()
```

**Benefits:**
- Queuing and scheduling operations
- Undo/Redo functionality
- Request logging and auditing

---

### **3. Singleton Pattern - Domain Module**

**Concept:** Ensures a class has only one instance and provides global access to it.

**When to Use:** For configuration managers, logging, database connections, and caching.

**Pseudo Code:**
```
CLASS DomainManager:
    PRIVATE STATIC instance = null
    PRIVATE domainConfigurations = {}
    PRIVATE connectionPool
    
    PRIVATE CONSTRUCTOR():
        loadDomainConfigurations()
        initializeConnectionPool()
    
    PUBLIC STATIC METHOD getInstance():
        IF instance IS null:
            SYNCHRONIZED:
                IF instance IS null:
                    instance = NEW DomainManager()
        RETURN instance
    
    METHOD getConfiguration(key):
        RETURN domainConfigurations.get(key)
    
    METHOD updateConfiguration(key, value):
        domainConfigurations.put(key, value)
        persistConfiguration(key, value)
    
    METHOD getConnection():
        RETURN connectionPool.getConnection()
    
    PRIVATE METHOD loadDomainConfigurations():
        // Load from database or configuration files
        domainConfigurations.put("MAX_TRANSACTION_LIMIT", "1000000")
        domainConfigurations.put("REGULATORY_ENDPOINT", "https://api.regulator.gov")

// Usage
domainManager = DomainManager.getInstance()
maxLimit = domainManager.getConfiguration("MAX_TRANSACTION_LIMIT")
connection = domainManager.getConnection()
```

**Benefits:**
- Global access point
- Controlled instantiation
- Resource conservation

---

### **4. Builder Pattern - EIT Bulk Offline Module**

**Concept:** Separates the construction of complex objects from their representation.

**When to Use:** For creating complex objects with many optional parameters.

**Pseudo Code:**
```
CLASS BulkJobBuilder:
    PRIVATE jobType
    PRIVATE inputFile
    PRIVATE outputDirectory
    PRIVATE processingRules = []
    PRIVATE scheduledTime
    PRIVATE retryPolicy
    PRIVATE notificationSettings
    
    METHOD setJobType(type):
        jobType = type
        RETURN this
    
    METHOD setInputFile(file):
        inputFile = file
        RETURN this
    
    METHOD setOutputDirectory(directory):
        outputDirectory = directory
        RETURN this
    
    METHOD addProcessingRule(rule):
        processingRules.add(rule)
        RETURN this
    
    METHOD setScheduledTime(time):
        scheduledTime = time
        RETURN this
    
    METHOD setRetryPolicy(policy):
        retryPolicy = policy
        RETURN this
    
    METHOD setNotificationSettings(settings):
        notificationSettings = settings
        RETURN this
    
    METHOD build():
        IF jobType IS null OR inputFile IS null:
            THROW "Required fields missing"
        
        job = NEW BulkJob()
        job.setJobType(jobType)
        job.setInputFile(inputFile)
        job.setOutputDirectory(outputDirectory)
        job.setProcessingRules(processingRules)
        job.setScheduledTime(scheduledTime)
        job.setRetryPolicy(retryPolicy)
        job.setNotificationSettings(notificationSettings)
        
        RETURN job

// Usage
bulkJob = NEW BulkJobBuilder()
    .setJobType("COMPLIANCE_REPORT")
    .setInputFile("/data/transactions.csv")
    .setOutputDirectory("/reports/")
    .addProcessingRule("VALIDATE_AMOUNTS")
    .addProcessingRule("CHECK_SANCTIONS")
    .setScheduledTime("2024-01-01 02:00:00")
    .setRetryPolicy("EXPONENTIAL_BACKOFF")
    .setNotificationSettings("email:admin@bank.com")
    .build()
```

**Benefits:**
- Flexible object construction
- Readable and maintainable code
- Step-by-step object creation

---

### **5. Strategy Pattern - File Upload Core Module**

**Concept:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable.

**When to Use:** When you have multiple ways to perform a task and want to switch between them.

**Pseudo Code:**
```
INTERFACE FileValidationStrategy:
    METHOD validate(file)

CLASS CsvValidationStrategy IMPLEMENTS FileValidationStrategy:
    METHOD validate(file):
        IF NOT file.extension EQUALS ".csv":
            THROW "Invalid file type. Expected CSV"
        
        IF file.size > MAX_CSV_SIZE:
            THROW "File too large for CSV processing"
        
        validateCsvHeaders(file)
        RETURN true

CLASS XmlValidationStrategy IMPLEMENTS FileValidationStrategy:
    METHOD validate(file):
        IF NOT file.extension EQUALS ".xml":
            THROW "Invalid file type. Expected XML"
        
        validateXmlSchema(file)
        validateXmlStructure(file)
        RETURN true

CLASS FileUploadProcessor:
    PRIVATE validationStrategy
    
    METHOD setValidationStrategy(strategy):
        validationStrategy = strategy
    
    METHOD processFile(file):
        IF validationStrategy IS null:
            THROW "No validation strategy set"
        
        validationStrategy.validate(file)
        parseFile(file)
        storeFile(file)
        notifyCompletion(file)

// Usage
processor = NEW FileUploadProcessor()

IF file.type EQUALS "CSV":
    processor.setValidationStrategy(NEW CsvValidationStrategy())
ELSE IF file.type EQUALS "XML":
    processor.setValidationStrategy(NEW XmlValidationStrategy())

processor.processFile(uploadedFile)
```

**Benefits:**
- Algorithm flexibility
- Easy to add new strategies
- Clean separation of concerns

---

### **6. Adapter Pattern - Messaging Module**

**Concept:** Allows incompatible interfaces to work together by converting one interface to another.

**When to Use:** When integrating with third-party systems or legacy code.

**Pseudo Code:**
```
INTERFACE MessagingService:
    METHOD sendMessage(recipient, message)

CLASS LegacyEmailSystem:
    METHOD transmitEmail(emailAddress, subject, body):
        // Legacy email system implementation
        connectToLegacyServer()
        formatEmailMessage(emailAddress, subject, body)
        sendViaLegacyProtocol()

CLASS EmailAdapterService IMPLEMENTS MessagingService:
    PRIVATE legacyEmailSystem
    
    CONSTRUCTOR(legacyEmailSystem):
        this.legacyEmailSystem = legacyEmailSystem
    
    METHOD sendMessage(recipient, message):
        subject = extractSubject(message)
        body = extractBody(message)
        legacyEmailSystem.transmitEmail(recipient, subject, body)

CLASS ModernSmsApi:
    METHOD sendSms(phoneNumber, text):
        // Modern SMS API implementation
        authenticateWithApi()
        postMessage(phoneNumber, text)

CLASS SmsAdapterService IMPLEMENTS MessagingService:
    PRIVATE modernSmsApi
    
    CONSTRUCTOR(modernSmsApi):
        this.modernSmsApi = modernSmsApi
    
    METHOD sendMessage(recipient, message):
        phoneNumber = formatPhoneNumber(recipient)
        modernSmsApi.sendSms(phoneNumber, message)

// Usage
emailAdapter = NEW EmailAdapterService(NEW LegacyEmailSystem())
smsAdapter = NEW SmsAdapterService(NEW ModernSmsApi())

messagingServices = [emailAdapter, smsAdapter]

FOR EACH service IN messagingServices:
    service.sendMessage("user@bank.com", "Transaction completed")
```

**Benefits:**
- Legacy system integration
- Interface compatibility
- Gradual system migration

---

### **7. Factory Method Pattern - Notifications Module**

**Concept:** Creates objects without specifying their concrete classes.

**When to Use:** When you need to create objects based on certain criteria.

**Pseudo Code:**
```
INTERFACE Notification:
    METHOD send(recipient, message)

CLASS EmailNotification IMPLEMENTS Notification:
    METHOD send(recipient, message):
        emailService.send(recipient, message)
        logNotification("EMAIL", recipient, message)

CLASS SmsNotification IMPLEMENTS Notification:
    METHOD send(recipient, message):
        smsService.send(recipient, message)
        logNotification("SMS", recipient, message)

CLASS PushNotification IMPLEMENTS Notification:
    METHOD send(recipient, message):
        pushService.send(recipient, message)
        logNotification("PUSH", recipient, message)

ABSTRACT CLASS NotificationFactory:
    ABSTRACT METHOD createNotification()
    
    METHOD processNotification(recipient, message):
        notification = createNotification()
        notification.send(recipient, message)

CLASS EmailNotificationFactory EXTENDS NotificationFactory:
    METHOD createNotification():
        RETURN NEW EmailNotification()

CLASS SmsNotificationFactory EXTENDS NotificationFactory:
    METHOD createNotification():
        RETURN NEW SmsNotification()

CLASS NotificationManager:
    METHOD sendNotification(type, recipient, message):
        factory = getFactory(type)
        factory.processNotification(recipient, message)
    
    PRIVATE METHOD getFactory(type):
        SWITCH type:
            CASE "EMAIL":
                RETURN NEW EmailNotificationFactory()
            CASE "SMS":
                RETURN NEW SmsNotificationFactory()
            CASE "PUSH":
                RETURN NEW PushNotificationFactory()
            DEFAULT:
                THROW "Unknown notification type"

// Usage
notificationManager = NEW NotificationManager()
notificationManager.sendNotification("EMAIL", "user@bank.com", "Payment processed")
notificationManager.sendNotification("SMS", "+1234567890", "Login detected")
```

**Benefits:**
- Loose coupling between creation and usage
- Easy to add new notification types
- Centralized object creation logic

---

### **8. Template Method Pattern - Response File Core Module**

**Concept:** Defines the skeleton of an algorithm, letting subclasses override specific steps.

**When to Use:** When you have a common algorithm structure with varying implementations.

**Pseudo Code:**
```
ABSTRACT CLASS ResponseFileGenerator:
    // Template method
    METHOD generateResponseFile(data):
        validateInput(data)
        processedData = processData(data)
        formattedData = formatData(processedData)
        fileName = generateFileName()
        writeToFile(formattedData, fileName)
        notifyCompletion(fileName)
    
    // Common implementation
    METHOD validateInput(data):
        IF data IS null OR data.isEmpty():
            THROW "Input data cannot be empty"
    
    METHOD generateFileName():
        timestamp = getCurrentTimestamp()
        RETURN "response_" + timestamp + getFileExtension()
    
    METHOD notifyCompletion(fileName):
        emailService.send("admin@bank.com", "File generated: " + fileName)
    
    // Abstract methods for subclasses
    ABSTRACT METHOD processData(data)
    ABSTRACT METHOD formatData(processedData)
    ABSTRACT METHOD getFileExtension()
    ABSTRACT METHOD writeToFile(data, fileName)

CLASS CsvResponseFileGenerator EXTENDS ResponseFileGenerator:
    METHOD processData(data):
        // CSV-specific data processing
        cleanedData = removeNullValues(data)
        RETURN sortByTimestamp(cleanedData)
    
    METHOD formatData(processedData):
        csvData = ""
        FOR EACH record IN processedData:
            csvData += record.toCsvRow() + "\n"
        RETURN csvData
    
    METHOD getFileExtension():
        RETURN ".csv"
    
    METHOD writeToFile(data, fileName):
        csvWriter.writeToFile(data, fileName)

CLASS XmlResponseFileGenerator EXTENDS ResponseFileGenerator:
    METHOD processData(data):
        // XML-specific data processing
        RETURN addXmlMetadata(data)
    
    METHOD formatData(processedData):
        xmlData = "<response>"
        FOR EACH record IN processedData:
            xmlData += record.toXmlElement()
        xmlData += "</response>"
        RETURN xmlData
    
    METHOD getFileExtension():
        RETURN ".xml"
    
    METHOD writeToFile(data, fileName):
        xmlWriter.writeToFile(data, fileName)

// Usage
csvGenerator = NEW CsvResponseFileGenerator()
csvGenerator.generateResponseFile(transactionData)

xmlGenerator = NEW XmlResponseFileGenerator()
xmlGenerator.generateResponseFile(complianceData)
```

**Benefits:**
- Code reuse through inheritance
- Consistent algorithm structure
- Customizable behavior in subclasses

---

## SAGA Design Pattern

### **What is SAGA Pattern?**

**Definition:** SAGA is a design pattern for managing distributed transactions across multiple microservices. It ensures data consistency through a sequence of local transactions, where each service performs its work and publishes events. If any step fails, compensating actions are executed to undo previous steps.

### **Types of SAGA Implementation:**

1. **Choreography-based SAGA:** Services communicate through events
2. **Orchestration-based SAGA:** Central coordinator manages the transaction

### **When to Use SAGA Pattern:**

- Distributed transactions across microservices
- Long-running business processes
- When ACID transactions are not feasible
- Need for eventual consistency

### **SAGA Pattern Implementation - Orchestration Approach**

**Scenario:** Payment Processing in Banking System

**Pseudo Code:**
```
// SAGA Coordinator/Orchestrator
CLASS PaymentSagaOrchestrator:
    PRIVATE sagaSteps = []
    PRIVATE compensatingActions = []
    PRIVATE currentStep = 0
    
    METHOD executePaymentSaga(paymentRequest):
        sagaId = generateSagaId()
        
        TRY:
            // Step 1: Validate Account
            validateAccountResult = accountService.validateAccount(paymentRequest.fromAccount)
            IF NOT validateAccountResult.isSuccess():
                THROW "Account validation failed"
            
            compensatingActions.add(() -> accountService.releaseValidation(paymentRequest.fromAccount))
            
            // Step 2: Reserve Funds
            reserveFundsResult = accountService.reserveFunds(paymentRequest.fromAccount, paymentRequest.amount)
            IF NOT reserveFundsResult.isSuccess():
                executeCompensatingActions()
                THROW "Fund reservation failed"
            
            compensatingActions.add(() -> accountService.releaseFunds(paymentRequest.fromAccount, paymentRequest.amount))
            
            // Step 3: Process Payment
            processPaymentResult = paymentService.processPayment(paymentRequest)
            IF NOT processPaymentResult.isSuccess():
                executeCompensatingActions()
                THROW "Payment processing failed"
            
            compensatingActions.add(() -> paymentService.reversePayment(processPaymentResult.transactionId))
            
            // Step 4: Update Account Balance
            updateBalanceResult = accountService.updateBalance(paymentRequest.fromAccount, -paymentRequest.amount)
            IF NOT updateBalanceResult.isSuccess():
                executeCompensatingActions()
                THROW "Balance update failed"
            
            // Step 5: Send Notification
            notificationService.sendPaymentConfirmation(paymentRequest.userId, processPaymentResult.transactionId)
            
            // Step 6: Record Transaction
            auditService.recordTransaction(processPaymentResult.transactionId, paymentRequest)
            
            RETURN SUCCESS("Payment completed successfully")
            
        CATCH exception:
            executeCompensatingActions()
            RETURN FAILURE("Payment failed: " + exception.message)
    
    PRIVATE METHOD executeCompensatingActions():
        FOR EACH action IN compensatingActions REVERSE:
            TRY:
                action.execute()
            CATCH compensationException:
                logCompensationFailure(compensationException)
                // Continue with other compensations

// Individual Services with Compensation Logic
CLASS AccountService:
    METHOD validateAccount(accountId):
        // Validate account exists and is active
        IF accountExists(accountId) AND accountIsActive(accountId):
            markAccountAsBeingProcessed(accountId)
            RETURN SUCCESS("Account validated")
        ELSE:
            RETURN FAILURE("Account validation failed")
    
    METHOD releaseValidation(accountId):
        // Compensating action
        unmarkAccountAsBeingProcessed(accountId)
    
    METHOD reserveFunds(accountId, amount):
        currentBalance = getAccountBalance(accountId)
        IF currentBalance >= amount:
            updateReservedFunds(accountId, amount)
            RETURN SUCCESS("Funds reserved")
        ELSE:
            RETURN FAILURE("Insufficient funds")
    
    METHOD releaseFunds(accountId, amount):
        // Compensating action
        updateReservedFunds(accountId, -amount)

CLASS PaymentService:
    METHOD processPayment(paymentRequest):
        transactionId = generateTransactionId()
        
        // Process payment through external payment gateway
        gatewayResult = paymentGateway.processPayment(paymentRequest)
        
        IF gatewayResult.isSuccess():
            RETURN SUCCESS(transactionId)
        ELSE:
            RETURN FAILURE("Gateway processing failed")
    
    METHOD reversePayment(transactionId):
        // Compensating action
        paymentGateway.reverseTransaction(transactionId)

// Event-based SAGA (Choreography Approach)
CLASS PaymentSagaChoreography:
    METHOD initiatePayment(paymentRequest):
        eventBus.publish(NEW PaymentInitiatedEvent(paymentRequest))

// Event Handlers
CLASS AccountEventHandler:
    METHOD handlePaymentInitiated(event):
        TRY:
            validateAccountResult = accountService.validateAccount(event.fromAccount)
            IF validateAccountResult.isSuccess():
                eventBus.publish(NEW AccountValidatedEvent(event.paymentRequest))
            ELSE:
                eventBus.publish(NEW PaymentFailedEvent(event.paymentRequest, "Account validation failed"))
        CATCH exception:
            eventBus.publish(NEW PaymentFailedEvent(event.paymentRequest, exception.message))

CLASS FundsEventHandler:
    METHOD handleAccountValidated(event):
        TRY:
            reserveResult = accountService.reserveFunds(event.fromAccount, event.amount)
            IF reserveResult.isSuccess():
                eventBus.publish(NEW FundsReservedEvent(event.paymentRequest))
            ELSE:
                eventBus.publish(NEW PaymentFailedEvent(event.paymentRequest, "Fund reservation failed"))
        CATCH exception:
            eventBus.publish(NEW PaymentFailedEvent(event.paymentRequest, exception.message))

// Compensation Event Handler
CLASS CompensationEventHandler:
    METHOD handlePaymentFailed(event):
        // Execute compensating actions based on how far the saga progressed
        IF event.lastSuccessfulStep >= "FUNDS_RESERVED":
            accountService.releaseFunds(event.fromAccount, event.amount)
        
        IF event.lastSuccessfulStep >= "ACCOUNT_VALIDATED":
            accountService.releaseValidation(event.fromAccount)

// SAGA State Management
CLASS SagaStateManager:
    PRIVATE sagaStates = {}
    
    METHOD trackSagaProgress(sagaId, step, status):
        IF NOT sagaStates.containsKey(sagaId):
            sagaStates.put(sagaId, NEW SagaState())
        
        sagaState = sagaStates.get(sagaId)
        sagaState.addStep(step, status)
        
        IF status EQUALS "FAILED":
            triggerCompensation(sagaId, sagaState)
    
    METHOD triggerCompensation(sagaId, sagaState):
        compensationSteps = sagaState.getCompensationSteps()
        FOR EACH step IN compensationSteps REVERSE:
            executeCompensationStep(step)

// Usage Example
paymentOrchestrator = NEW PaymentSagaOrchestrator()
paymentRequest = NEW PaymentRequest(
    fromAccount: "ACC123",
    toAccount: "ACC456",
    amount: 1000.00,
    userId: "USER789"
)

result = paymentOrchestrator.executePaymentSaga(paymentRequest)
IF result.isSuccess():
    PRINT "Payment completed successfully"
ELSE:
    PRINT "Payment failed: " + result.errorMessage
```

### **SAGA Pattern Benefits:**

1. **Consistency:** Ensures eventual consistency across services
2. **Resilience:** Automatic compensation for failures
3. **Scalability:** Each service handles its part independently
4. **Monitoring:** Clear visibility into transaction progress
5. **Fault Tolerance:** Graceful handling of partial failures

### **SAGA Pattern Challenges:**

1. **Complexity:** More complex than traditional transactions
2. **Eventual Consistency:** Not immediately consistent
3. **Compensation Logic:** Must implement reverse operations
4. **Testing:** Complex testing scenarios
5. **Monitoring:** Need sophisticated tracking

---

## SOLID Principles

### **1. Single Responsibility Principle (SRP)**

**Definition:** A class should have only one reason to change, meaning it should have only one responsibility.

**Violation Example:**
```
// VIOLATION - Class has multiple responsibilities
CLASS UserManager:
    METHOD createUser(userData)
    METHOD validateUserEmail(email)
    METHOD sendWelcomeEmail(user)
    METHOD generateUserReport(userId)
    METHOD saveUserToDatabase(user)
    METHOD logUserActivity(user, activity)
```

**Correct Implementation:**
```
// Each class has single responsibility
CLASS User:
    PRIVATE id, name, email, createdDate
    // Only user data and related methods

CLASS UserValidator:
    METHOD validateEmail(email):
        RETURN email.contains("@") AND email.contains(".")
    
    METHOD validatePassword(password):
        RETURN password.length >= 8

CLASS UserRepository:
    METHOD save(user):
        database.insert("users", user)
    
    METHOD findById(id):
        RETURN database.select("users", id)

CLASS EmailService:
    METHOD sendWelcomeEmail(user):
        emailTemplate = loadTemplate("welcome")
        emailContent = populateTemplate(emailTemplate, user)
        sendEmail(user.email, emailContent)

CLASS UserActivityLogger:
    METHOD logActivity(user, activity):
        logEntry = createLogEntry(user, activity, timestamp)
        writeToLog(logEntry)

CLASS UserReportGenerator:
    METHOD generateUserReport(userId):
        user = userRepository.findById(userId)
        activities = activityLogger.getActivities(userId)
        RETURN createReport(user, activities)

// User management orchestration
CLASS UserService:
    PRIVATE userValidator
    PRIVATE userRepository
    PRIVATE emailService
    PRIVATE activityLogger
    
    METHOD createUser(userData):
        IF userValidator.validateEmail(userData.email):
            user = NEW User(userData)
            userRepository.save(user)
            emailService.sendWelcomeEmail(user)
            activityLogger.logActivity(user, "USER_CREATED")
            RETURN user
        ELSE:
            THROW "Invalid email format"
```

**Benefits:**
- Easier to understand and maintain
- Reduced coupling between functionalities
- Easier to test individual components
- Better code organization

---

### **2. Open/Closed Principle (OCP)**

**Definition:** Software entities should be open for extension but closed for modification.

**Violation Example:**
```
// VIOLATION - Must modify class to add new notification types
CLASS NotificationService:
    METHOD sendNotification(type, recipient, message):
        IF type EQUALS "EMAIL":
            sendEmail(recipient, message)
        ELSE IF type EQUALS "SMS":
            sendSms(recipient, message)
        ELSE IF type EQUALS "PUSH":
            sendPushNotification(recipient, message)
        // Adding new type requires modifying this method
```

**Correct Implementation:**
```
// Open for extension, closed for modification
INTERFACE NotificationChannel:
    METHOD send(recipient, message)
    METHOD supports(type)

CLASS EmailNotificationChannel IMPLEMENTS NotificationChannel:
    METHOD send(recipient, message):
        emailService.send(recipient, message)
    
    METHOD supports(type):
        RETURN type EQUALS "EMAIL"

CLASS SmsNotificationChannel IMPLEMENTS NotificationChannel:
    METHOD send(recipient, message):
        smsService.send(recipient, message)
    
    METHOD supports(type):
        RETURN type EQUALS "SMS"

CLASS PushNotificationChannel IMPLEMENTS NotificationChannel:
    METHOD send(recipient, message):
        pushService.send(recipient, message)
    
    METHOD supports(type):
        RETURN type EQUALS "PUSH"

// New channel can be added without modifying existing code
CLASS SlackNotificationChannel IMPLEMENTS NotificationChannel:
    METHOD send(recipient, message):
        slackService.send(recipient, message)
    
    METHOD supports(type):
        RETURN type EQUALS "SLACK"

CLASS NotificationService:
    PRIVATE channels = []
    
    METHOD addChannel(channel):
        channels.add(channel)
    
    METHOD sendNotification(type, recipient, message):
        FOR EACH channel IN channels:
            IF channel.supports(type):
                channel.send(recipient, message)
                RETURN
        THROW "Unsupported notification type: " + type

// Usage - Adding new channels without modifying existing code
notificationService = NEW NotificationService()
notificationService.addChannel(NEW EmailNotificationChannel())
notificationService.addChannel(NEW SmsNotificationChannel())
notificationService.addChannel(NEW SlackNotificationChannel()) // New addition
```

**Benefits:**
- Easy to add new functionality
- Existing code remains unchanged
- Reduced risk of breaking existing features
- Better maintainability

---

### **3. Liskov Substitution Principle (LSP)**

**Definition:** Objects of a superclass should be replaceable with objects of a subclass without affecting program correctness.

**Violation Example:**
```
// VIOLATION - Subclass changes expected behavior
CLASS Bird:
    METHOD fly():
        PRINT "Flying in the sky"

CLASS Penguin EXTENDS Bird:
    METHOD fly():
        THROW "Penguins cannot fly" // Violates LSP
```

**Correct Implementation:**
```
// Correct design respecting LSP
INTERFACE Bird:
    METHOD eat()
    METHOD move()

INTERFACE FlyingBird EXTENDS Bird:
    METHOD fly()

INTERFACE SwimmingBird EXTENDS Bird:
    METHOD swim()

CLASS Eagle IMPLEMENTS FlyingBird:
    METHOD eat():
        PRINT "Eagle hunting for prey"
    
    METHOD move():
        fly() // Uses flying for movement
    
    METHOD fly():
        PRINT "Eagle soaring high"

CLASS Penguin IMPLEMENTS SwimmingBird:
    METHOD eat():
        PRINT "Penguin catching fish"
    
    METHOD move():
        swim() // Uses swimming for movement
    
    METHOD swim():
        PRINT "Penguin swimming underwater"

// Business logic example
CLASS PaymentProcessor:
    METHOD processPayment(amount)

CLASS CreditCardProcessor EXTENDS PaymentProcessor:
    METHOD processPayment(amount):
        IF amount <= creditLimit:
            chargeCreditCard(amount)
            RETURN SUCCESS("Payment processed")
        ELSE:
            RETURN FAILURE("Credit limit exceeded")

CLASS DebitCardProcessor EXTENDS PaymentProcessor:
    METHOD processPayment(amount):
        IF amount <= accountBalance:
            debitAccount(amount)
            RETURN SUCCESS("Payment processed")
        ELSE:
            RETURN FAILURE("Insufficient funds")

// Both processors can be used interchangeably
CLASS PaymentService:
    METHOD makePayment(processor, amount):
        result = processor.processPayment(amount)
        IF result.isSuccess():
            sendConfirmation()
        ELSE:
            handleFailure(result.errorMessage)

// Usage - Both processors work correctly
paymentService = NEW PaymentService()
paymentService.makePayment(NEW CreditCardProcessor(), 100.00)
paymentService.makePayment(NEW DebitCardProcessor(), 100.00)
```

**Benefits:**
- Consistent behavior across inheritance hierarchy
- Reliable polymorphism
- Predictable code behavior
- Better software design

---

### **4. Interface Segregation Principle (ISP)**

**Definition:** Clients should not be forced to depend on interfaces they don't use.

**Violation Example:**
```
// VIOLATION - Fat interface forcing unnecessary dependencies
INTERFACE BankingOperations:
    METHOD depositMoney(amount)
    METHOD withdrawMoney(amount)
    METHOD transferMoney(toAccount, amount)
    METHOD generateStatement()
    METHOD calculateInterest()
    METHOD approveLoan(loanAmount)
    METHOD processInsurance()

// Customer service forced to implement loan and insurance methods
CLASS CustomerService IMPLEMENTS BankingOperations:
    METHOD depositMoney(amount): // Needed
    METHOD withdrawMoney(amount): // Needed
    METHOD transferMoney(toAccount, amount): // Needed
    METHOD generateStatement(): // Needed
    METHOD calculateInterest(): // NOT NEEDED
    METHOD approveLoan(loanAmount): // NOT NEEDED
    METHOD processInsurance(): // NOT NEEDED
```

**Correct Implementation:**
```
// Segregated interfaces based on client needs
INTERFACE AccountOperations:
    METHOD depositMoney(amount)
    METHOD withdrawMoney(amount)
    METHOD transferMoney(toAccount, amount)

INTERFACE StatementOperations:
    METHOD generateStatement()
    METHOD getTransactionHistory()

INTERFACE InterestOperations:
    METHOD calculateInterest()
    METHOD compoundInterest()

INTERFACE LoanOperations:
    METHOD approveLoan(loanAmount)
    METHOD calculateLoanEmi(loanAmount, tenure)

INTERFACE InsuranceOperations:
    METHOD processInsurance()
    METHOD calculatePremium()

// Classes implement only what they need
CLASS CustomerService IMPLEMENTS AccountOperations, StatementOperations:
    METHOD depositMoney(amount):
        account.deposit(amount)
        logTransaction("DEPOSIT", amount)
    
    METHOD withdrawMoney(amount):
        IF account.balance >= amount:
            account.withdraw(amount)
            logTransaction("WITHDRAWAL", amount)
    
    METHOD transferMoney(toAccount, amount):
        withdrawMoney(amount)
        toAccount.depositMoney(amount)
    
    METHOD generateStatement():
        transactions = getTransactionHistory()
        RETURN formatStatement(transactions)
    
    METHOD getTransactionHistory():
        RETURN database.getTransactions(account.id)

CLASS LoanOfficer IMPLEMENTS LoanOperations:
    METHOD approveLoan(loanAmount):
        creditScore = getCreditScore(applicant)
        IF creditScore > 700:
            RETURN approveLoanApplication(loanAmount)
        ELSE:
            RETURN rejectLoanApplication()
    
    METHOD calculateLoanEmi(loanAmount, tenure):
        interestRate = getCurrentInterestRate()
        RETURN calculateEmi(loanAmount, interestRate, tenure)

CLASS InterestCalculator IMPLEMENTS InterestOperations:
    METHOD calculateInterest():
        RETURN principal * rate * time / 100
    
    METHOD compoundInterest():
        RETURN principal * Math.pow(1 + rate/100, time) - principal

// Mixed responsibilities when needed
CLASS PremiumBankingService IMPLEMENTS AccountOperations, LoanOperations, InsuranceOperations:
    // Implements only the interfaces it actually uses
```

**Benefits:**
- Clients depend only on methods they use
- Reduced coupling between components
- Better testability
- More focused interfaces

---

### **5. Dependency Inversion Principle (DIP)**

**Definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions.

**Violation Example:**
```
// VIOLATION - High-level class depends on concrete low-level classes
CLASS PaymentProcessor:
    PRIVATE emailService = NEW EmailService() // Direct dependency
    PRIVATE smsService = NEW SmsService()     // Direct dependency
    PRIVATE database = NEW MySqlDatabase()    // Direct dependency
    
    METHOD processPayment(payment):
        result = database.savePayment(payment)
        IF result.isSuccess():
            emailService.send(payment.email, "Payment successful")
            smsService.send(payment.phone, "Payment confirmed")
```

**Correct Implementation:**
```
// Depend on abstractions, not concretions
INTERFACE NotificationService:
    METHOD sendNotification(recipient, message)

INTERFACE PaymentRepository:
    METHOD savePayment(payment)
    METHOD findPaymentById(id)

INTERFACE AuditLogger:
    METHOD logPaymentEvent(payment, event)

// High-level module depends on abstractions
CLASS PaymentProcessor:
    PRIVATE notificationService // Interface dependency
    PRIVATE paymentRepository   // Interface dependency
    PRIVATE auditLogger        // Interface dependency
    
    // Dependency injection through constructor
    CONSTRUCTOR(notificationService, paymentRepository, auditLogger):
        this.notificationService = notificationService
        this.paymentRepository = paymentRepository
        this.auditLogger = auditLogger
    
    METHOD processPayment(payment):
        TRY:
            // Use abstraction, not concrete implementation
            result = paymentRepository.savePayment(payment)
            
            IF result.isSuccess():
                notificationService.sendNotification(payment.email, "Payment successful")
                auditLogger.logPaymentEvent(payment, "PAYMENT_COMPLETED")
                RETURN SUCCESS("Payment processed")
            ELSE:
                auditLogger.logPaymentEvent(payment, "PAYMENT_FAILED")
                RETURN FAILURE("Payment processing failed")
                
        CATCH exception:
            auditLogger.logPaymentEvent(payment, "PAYMENT_ERROR: " + exception.message)
            RETURN FAILURE("Payment error: " + exception.message)

// Low-level modules implement abstractions
CLASS EmailNotificationService IMPLEMENTS NotificationService:
    METHOD sendNotification(recipient, message):
        emailClient.send(recipient, "Payment Notification", message)

CLASS SmsNotificationService IMPLEMENTS NotificationService:
    METHOD sendNotification(recipient, message):
        smsClient.send(recipient, message)

CLASS DatabasePaymentRepository IMPLEMENTS PaymentRepository:
    METHOD savePayment(payment):
        RETURN database.insert("payments", payment)
    
    METHOD findPaymentById(id):
        RETURN database.select("payments", id)

CLASS FileAuditLogger IMPLEMENTS AuditLogger:
    METHOD logPaymentEvent(payment, event):
        logEntry = formatLogEntry(payment, event, getCurrentTimestamp())
        fileWriter.appendToFile("audit.log", logEntry)

// Dependency injection configuration
CLASS PaymentProcessorFactory:
    METHOD createPaymentProcessor():
        notificationService = NEW EmailNotificationService()
        paymentRepository = NEW DatabasePaymentRepository()
        auditLogger = NEW FileAuditLogger()
        
        RETURN NEW PaymentProcessor(notificationService, paymentRepository, auditLogger)

// Usage with dependency injection
paymentProcessor = PaymentProcessorFactory.createPaymentProcessor()
result = paymentProcessor.processPayment(paymentData)

// Easy to swap implementations for testing
CLASS MockNotificationService IMPLEMENTS NotificationService:
    METHOD sendNotification(recipient, message):
        testLogger.log("Notification sent to: " + recipient)

// Test with mocked dependencies
testPaymentProcessor = NEW PaymentProcessor(
    NEW MockNotificationService(),
    NEW MockPaymentRepository(),
    NEW MockAuditLogger()
)
```

**Benefits:**
- Reduced coupling between modules
- Easy to test with mock implementations
- Flexible architecture
- Easy to change implementations
- Better maintainability

---

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
