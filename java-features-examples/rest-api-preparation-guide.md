# REST API Development Guide - Interview Preparation
## Complete Guide for Building RESTful Web Services with JAX-RS and Jersey

---

## Table of Contents
1. [REST API Development Checklist](#rest-api-development-checklist)
2. [Project Setup](#project-setup)
3. [Core REST Concepts](#core-rest-concepts)
4. [JAX-RS Implementation](#jax-rs-implementation)
5. [Complete Example Implementation](#complete-example-implementation)
6. [Testing Strategy](#testing-strategy)
7. [Interview Questions & Answers](#interview-questions--answers)

---

## REST API Development Checklist

### **Essential Components for New REST Web Service (Producer)**

✅ **Service Layer**
- [ ] Write new service class with business logic
- [ ] Implement CRUD operations
- [ ] Add transaction management

✅ **Data Access Layer**
- [ ] Write new DAO implementation class
- [ ] Database connectivity and queries
- [ ] Connection pooling configuration

✅ **Model Layer**
- [ ] Create new POJO/Entity classes
- [ ] Add validation annotations
- [ ] Write converters/parsers if needed

✅ **Exception Handling**
- [ ] Write new exception classes
- [ ] Connect exceptions using AOP
- [ ] Define specific error codes for error scenarios
- [ ] Implement global exception handling

✅ **Testing Strategy**
- [ ] Write JUnit test cases for all layers
- [ ] Create Cucumber test suite for BDD
- [ ] Integration testing
- [ ] API documentation testing

---

## Project Setup

### **Maven Archetype for JAX-RS Jersey Project**

```xml
<!-- Maven Coordinates -->
<groupId>org.glassfish.jersey.archetypes</groupId>
<artifactId>jersey-quickstart-webapp</artifactId>
<version>2.34</version>

<!-- Alternative Web App Archetype -->
<groupId>org.apache.maven.archetypes</groupId>
<artifactId>maven-archetype-webapp</artifactId>
<version>1.4</version>
```

### **Essential Dependencies (pom.xml)**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.nish.rest</groupId>
    <artifactId>messenger</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>war</packaging>
    
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <jersey.version>2.34</jersey.version>
        <jackson.version>2.13.0</jackson.version>
    </properties>
    
    <dependencies>
        <!-- JAX-RS Jersey Implementation -->
        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-servlet-core</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        
        <!-- JSON Support -->
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-json-jackson</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        
        <!-- XML Support -->
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-jaxb</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        
        <!-- Dependency Injection -->
        <dependency>
            <groupId>org.glassfish.jersey.inject</groupId>
            <artifactId>jersey-hk2</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        
        <!-- Bean Validation -->
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>6.2.0.Final</version>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
        
        <!-- Connection Pooling -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>5.0.0</version>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>4.2.0</version>
            <scope>test</scope>
        </dependency>
        
        <!-- Cucumber BDD Testing -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>7.2.3</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>7.2.3</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

### **Web.xml Configuration**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    
    <display-name>REST API Application</display-name>
    
    <!-- Jersey Servlet Configuration -->
    <servlet>
        <servlet-name>Jersey REST Service</servlet-name>
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
        <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>com.nish.rest</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>Jersey REST Service</servlet-name>
        <url-pattern>/webapi/*</url-pattern>
    </servlet-mapping>
</web-app>
```

---

## Core REST Concepts

### **HTTP Methods & Idempotence**

| Method | Purpose | Idempotent | Safe | Cacheable |
|--------|---------|------------|------|-----------|
| **GET** | Retrieve resource | ✅ Yes | ✅ Yes | ✅ Yes |
| **POST** | Create resource | ❌ No | ❌ No | ❌ No |
| **PUT** | Update/Replace resource | ✅ Yes | ❌ No | ❌ No |
| **PATCH** | Partial update | ❌ No | ❌ No | ❌ No |
| **DELETE** | Remove resource | ✅ Yes | ❌ No | ❌ No |
| **HEAD** | Get headers only | ✅ Yes | ✅ Yes | ✅ Yes |
| **OPTIONS** | Get allowed methods | ✅ Yes | ✅ Yes | ❌ No |

### **HTTP Status Codes**

```java
// Success Codes (2xx)
200 OK                  // General success
201 Created            // Resource created successfully
202 Accepted           // Request accepted, processing
204 No Content         // Success with no response body

// Client Error Codes (4xx)
400 Bad Request        // Invalid request format
401 Unauthorized       // Authentication required
403 Forbidden          // Access denied
404 Not Found          // Resource doesn't exist
405 Method Not Allowed // HTTP method not supported
409 Conflict           // Resource conflict
422 Unprocessable Entity // Validation failed

// Server Error Codes (5xx)
500 Internal Server Error // General server error
502 Bad Gateway          // Upstream server error
503 Service Unavailable  // Server temporarily unavailable
```

### **Richardson Maturity Model**

```
Level 0: Plain Old XML (POX) - SOAP-like services
├── Single URI for all operations
├── All operations use POST method
└── Operations defined in request body

Level 1: Resources
├── Multiple URIs for different resources
├── Still operations in request body
└── Better resource identification

Level 2: HTTP Verbs
├── Proper use of HTTP methods (GET, POST, PUT, DELETE)
├── HTTP status codes for responses
└── Resource URIs + HTTP methods

Level 3: Hypermedia Controls (HATEOAS)
├── Links to related resources in response
├── Self-discoverable API
└── Hypermedia as the Engine of Application State
```

---

## JAX-RS Implementation

### **1. Model Layer (POJO)**

```java
package com.nish.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@XmlRootElement
public class Message {
    
    private long id;
    
    @NotNull(message = "Message content cannot be null")
    @Size(min = 1, max = 500, message = "Message must be between 1 and 500 characters")
    private String message;
    
    @NotNull(message = "Author cannot be null")
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
    private String author;
    
    private Date created;
    
    // Default constructor required for JAX-B
    public Message() {}
    
    public Message(long id, String message, String author) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.created = new Date();
    }
    
    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }
    
    @Override
    public String toString() {
        return "Message{id=" + id + ", message='" + message + 
               "', author='" + author + "', created=" + created + '}';
    }
}
```

### **2. Custom Exception Classes**

```java
package com.nish.rest.exception;

// Custom Business Exception
public class DataNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public DataNotFoundException(String message) {
        super(message);
    }
    
    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Validation Exception
public class ValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ValidationException(String message) {
        super(message);
    }
}

// Service Exception
public class ServiceException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ServiceException(String message) {
        super(message);
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### **3. Error Response Model**

```java
package com.nish.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
    
    private String errorMessage;
    private int errorCode;
    private String documentation;
    private long timestamp;
    
    public ErrorMessage() {}
    
    public ErrorMessage(String errorMessage, int errorCode, String documentation) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.documentation = documentation;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public int getErrorCode() { return errorCode; }
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }
    
    public String getDocumentation() { return documentation; }
    public void setDocumentation(String documentation) { this.documentation = documentation; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
```

### **4. Global Exception Mapper**

```java
package com.nish.rest.exception;

import com.nish.rest.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    
    @Override
    public Response toResponse(DataNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
            ex.getMessage(),
            404,
            "http://localhost:8080/messengers/docs/errors/404"
        );
        
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
    }
}

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    
    @Override
    public Response toResponse(Throwable ex) {
        ErrorMessage errorMessage = new ErrorMessage(
            "Internal server error occurred",
            500,
            "http://localhost:8080/messengers/docs/errors/500"
        );
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    
    @Override
    public Response toResponse(ValidationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
            ex.getMessage(),
            400,
            "http://localhost:8080/messengers/docs/errors/400"
        );
        
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}
```

### **5. DAO Implementation**

```java
package com.nish.rest.dao;

import com.nish.rest.model.Message;
import com.nish.rest.exception.DataNotFoundException;
import com.nish.rest.exception.ServiceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/messenger_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    // SQL Queries
    private static final String SELECT_ALL_MESSAGES = 
        "SELECT id, message, author, created FROM messages ORDER BY created DESC";
    
    private static final String SELECT_MESSAGE_BY_ID = 
        "SELECT id, message, author, created FROM messages WHERE id = ?";
    
    private static final String INSERT_MESSAGE = 
        "INSERT INTO messages (message, author, created) VALUES (?, ?, ?)";
    
    private static final String UPDATE_MESSAGE = 
        "UPDATE messages SET message = ?, author = ? WHERE id = ?";
    
    private static final String DELETE_MESSAGE = 
        "DELETE FROM messages WHERE id = ?";
    
    private static final String SELECT_MESSAGES_BY_YEAR = 
        "SELECT id, message, author, created FROM messages WHERE YEAR(created) = ? ORDER BY created DESC";
    
    // Get database connection
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new ServiceException("Database driver not found", e);
        }
    }
    
    // Get all messages
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_MESSAGES);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Message message = mapResultSetToMessage(rs);
                messages.add(message);
            }
            
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving messages", e);
        }
        
        return messages;
    }
    
    // Get message by ID
    public Message getMessageById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_MESSAGE_BY_ID)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMessage(rs);
                } else {
                    throw new DataNotFoundException("Message with id " + id + " not found");
                }
            }
            
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving message with id " + id, e);
        }
    }
    
    // Create new message
    public Message createMessage(Message message) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, message.getMessage());
            stmt.setString(2, message.getAuthor());
            stmt.setTimestamp(3, new Timestamp(message.getCreated().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new ServiceException("Creating message failed, no rows affected");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                } else {
                    throw new ServiceException("Creating message failed, no ID obtained");
                }
            }
            
            return message;
            
        } catch (SQLException e) {
            throw new ServiceException("Error creating message", e);
        }
    }
    
    // Update message
    public Message updateMessage(Message message) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_MESSAGE)) {
            
            stmt.setString(1, message.getMessage());
            stmt.setString(2, message.getAuthor());
            stmt.setLong(3, message.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DataNotFoundException("Message with id " + message.getId() + " not found");
            }
            
            return getMessageById(message.getId());
            
        } catch (SQLException e) {
            throw new ServiceException("Error updating message with id " + message.getId(), e);
        }
    }
    
    // Delete message
    public boolean deleteMessage(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_MESSAGE)) {
            
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DataNotFoundException("Message with id " + id + " not found");
            }
            
            return true;
            
        } catch (SQLException e) {
            throw new ServiceException("Error deleting message with id " + id, e);
        }
    }
    
    // Get messages by year
    public List<Message> getMessagesByYear(int year) {
        List<Message> messages = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_MESSAGES_BY_YEAR)) {
            
            stmt.setInt(1, year);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Message message = mapResultSetToMessage(rs);
                    messages.add(message);
                }
            }
            
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving messages for year " + year, e);
        }
        
        return messages;
    }
    
    // Helper method to map ResultSet to Message object
    private Message mapResultSetToMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getLong("id"));
        message.setMessage(rs.getString("message"));
        message.setAuthor(rs.getString("author"));
        message.setCreated(rs.getTimestamp("created"));
        return message;
    }
}
```

### **6. Service Layer**

```java
package com.nish.rest.service;

import com.nish.rest.dao.MessageDao;
import com.nish.rest.model.Message;
import com.nish.rest.exception.ValidationException;

import java.util.Date;
import java.util.List;

public class MessageService {
    
    private MessageDao messageDao = new MessageDao();
    
    // Get all messages
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }
    
    // Get messages with filtering and pagination
    public List<Message> getMessages(int year, int start, int size) {
        List<Message> messages;
        
        if (year > 0) {
            messages = messageDao.getMessagesByYear(year);
        } else {
            messages = messageDao.getAllMessages();
        }
        
        // Apply pagination
        if (start >= 0 && size > 0) {
            int endIndex = Math.min(start + size, messages.size());
            if (start < messages.size()) {
                return messages.subList(start, endIndex);
            } else {
                return messages.subList(0, 0); // Empty list
            }
        }
        
        return messages;
    }
    
    // Get message by ID
    public Message getMessageById(long id) {
        if (id <= 0) {
            throw new ValidationException("Message ID must be positive");
        }
        return messageDao.getMessageById(id);
    }
    
    // Create new message
    public Message createMessage(Message message) {
        validateMessage(message);
        message.setCreated(new Date());
        return messageDao.createMessage(message);
    }
    
    // Update message
    public Message updateMessage(Message message) {
        validateMessage(message);
        if (message.getId() <= 0) {
            throw new ValidationException("Message ID must be positive");
        }
        return messageDao.updateMessage(message);
    }
    
    // Delete message
    public boolean deleteMessage(long id) {
        if (id <= 0) {
            throw new ValidationException("Message ID must be positive");
        }
        return messageDao.deleteMessage(id);
    }
    
    // Validate message
    private void validateMessage(Message message) {
        if (message == null) {
            throw new ValidationException("Message cannot be null");
        }
        
        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
            throw new ValidationException("Message content cannot be empty");
        }
        
        if (message.getMessage().length() > 500) {
            throw new ValidationException("Message content cannot exceed 500 characters");
        }
        
        if (message.getAuthor() == null || message.getAuthor().trim().isEmpty()) {
            throw new ValidationException("Author cannot be empty");
        }
        
        if (message.getAuthor().length() > 50) {
            throw new ValidationException("Author name cannot exceed 50 characters");
        }
    }
}
```

### **7. Resource Layer (Controller)**

```java
package com.nish.rest.resources;

import com.nish.rest.model.Message;
import com.nish.rest.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
    
    private MessageService messageService = new MessageService();
    
    // GET /messages - Get all messages with optional filtering and pagination
    @GET
    public List<Message> getMessages(@QueryParam("year") int year,
                                   @QueryParam("start") int start,
                                   @QueryParam("size") int size) {
        return messageService.getMessages(year, start, size);
    }
    
    // GET /messages/{id} - Get specific message
    @GET
    @Path("/{messageId}")
    public Message getMessageById(@PathParam("messageId") long id) {
        return messageService.getMessageById(id);
    }
    
    // POST /messages - Create new message
    @POST
    public Response createMessage(Message message, @Context UriInfo uriInfo) {
        Message createdMessage = messageService.createMessage(message);
        
        // Build URI for created resource
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(Long.toString(createdMessage.getId()))
                .build();
        
        return Response.created(uri)
                .entity(createdMessage)
                .build();
    }
    
    // PUT /messages/{id} - Update message
    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long id, Message message) {
        message.setId(id);
        return messageService.updateMessage(message);
    }
    
    // DELETE /messages/{id} - Delete message
    @DELETE
    @Path("/{messageId}")
    public Response deleteMessage(@PathParam("messageId") long id) {
        messageService.deleteMessage(id);
        return Response.noContent().build();
    }
    
    // Content Negotiation Example - XML Support
    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getMessagesXml() {
        return messageService.getAllMessages();
    }
    
    // HATEOAS Example
    @GET
    @Path("/{messageId}/hateoas")
    public Response getMessageWithLinks(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
        Message message = messageService.getMessageById(id);
        
        // Add self link
        String selfUri = uriInfo.getAbsolutePathBuilder()
                .path("../" + id)
                .build()
                .toString();
        
        // Add collection link
        String collectionUri = uriInfo.getAbsolutePathBuilder()
                .path("../../messages")
                .build()
                .toString();
        
        return Response.ok(message)
                .header("Link", "<" + selfUri + ">; rel=\"self\"")
                .header("Link", "<" + collectionUri + ">; rel=\"collection\"")
                .build();
    }
}
```

### **8. Parameter Handling Examples**

```java
package com.nish.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/params")
public class ParameterExamples {
    
    // Query Parameters
    @GET
    @Path("/query")
    public Response queryParams(@QueryParam("name") String name,
                              @QueryParam("age") @DefaultValue("0") int age,
                              @QueryParam("active") @DefaultValue("true") boolean active) {
        
        String response = String.format("Name: %s, Age: %d, Active: %s", name, age, active);
        return Response.ok(response).build();
    }
    
    // Path Parameters
    @GET
    @Path("/path/{userId}/profile/{profileId}")
    public Response pathParams(@PathParam("userId") long userId,
                             @PathParam("profileId") long profileId) {
        
        String response = String.format("User ID: %d, Profile ID: %d", userId, profileId);
        return Response.ok(response).build();
    }
    
    // Matrix Parameters
    @GET
    @Path("/matrix")
    public Response matrixParams(@MatrixParam("color") String color,
                               @MatrixParam("size") String size) {
        
        String response = String.format("Color: %s, Size: %s", color, size);
        return Response.ok(response).build();
    }
    
    // Header Parameters
    @GET
    @Path("/headers")
    public Response headerParams(@HeaderParam("User-Agent") String userAgent,
                               @HeaderParam("Authorization") String authToken,
                               @HeaderParam("Accept") String accept) {
        
        String response = String.format("User-Agent: %s, Auth: %s, Accept: %s", 
                                      userAgent, authToken, accept);
        return Response.ok(response).build();
    }
    
    // Cookie Parameters
    @GET
    @Path("/cookies")
    public Response cookieParams(@CookieParam("sessionId") String sessionId,
                               @CookieParam("userId") String userId) {
        
        String response = String.format("Session: %s, User: %s", sessionId, userId);
        return Response.ok(response).build();
    }
    
    // Form Parameters
    @POST
    @Path("/form")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response formParams(@FormParam("username") String username,
                             @FormParam("password") String password,
                             @FormParam("email") String email) {
        
        String response = String.format("Username: %s, Email: %s", username, email);
        return Response.ok(response).build();
    }
    
    // Bean Parameters
    @GET
    @Path("/bean")
    public Response beanParams(@BeanParam MessageFilterBean filterBean) {
        String response = String.format("Year: %d, Start: %d, Size: %d", 
                                      filterBean.getYear(), 
                                      filterBean.getStart(), 
                                      filterBean.getSize());
        return Response.ok(response).build();
    }
    
    // Context Parameters
    @GET
    @Path("/context")
    public Response contextParams(@Context UriInfo uriInfo,
                                @Context HttpHeaders headers,
                                @Context SecurityContext securityContext,
                                @Context Request request) {
        
        String requestUri = uriInfo.getRequestUri().toString();
        String userAgent = headers.getHeaderString("User-Agent");
        
        String response = String.format("URI: %s, User-Agent: %s", requestUri, userAgent);
        return Response.ok(response).build();
    }
}

// Bean Parameter Class
public class MessageFilterBean {
    
    @QueryParam("year")
    @DefaultValue("0")
    private int year;
    
    @QueryParam("start")
    @DefaultValue("0")
    private int start;
    
    @QueryParam("size")
    @DefaultValue("10")
    private int size;
    
    @HeaderParam("Accept")
    private String accept;
    
    @MatrixParam("format")
    private String format;
    
    // Getters and Setters
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public int getStart() { return start; }
    public void setStart(int start) { this.start = start; }
    
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    
    public String getAccept() { return accept; }
    public void setAccept(String accept) { this.accept = accept; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
}
```

---

## Testing Strategy

### **1. JUnit Test Cases**

```java
package com.nish.rest.service;

import com.nish.rest.exception.DataNotFoundException;
import com.nish.rest.exception.ValidationException;
import com.nish.rest.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {
    
    @Mock
    private MessageDao messageDao;
    
    @InjectMocks
    private MessageService messageService;
    
    private Message testMessage;
    
    @Before
    public void setUp() {
        testMessage = new Message(1L, "Test message", "Test Author");
    }
    
    @Test
    public void testGetAllMessages_Success() {
        // Arrange
        List<Message> expectedMessages = Arrays.asList(testMessage);
        when(messageDao.getAllMessages()).thenReturn(expectedMessages);
        
        // Act
        List<Message> actualMessages = messageService.getAllMessages();
        
        // Assert
        assertEquals(1, actualMessages.size());
        assertEquals(testMessage.getMessage(), actualMessages.get(0).getMessage());
        verify(messageDao, times(1)).getAllMessages();
    }
    
    @Test
    public void testGetMessageById_Success() {
        // Arrange
        when(messageDao.getMessageById(1L)).thenReturn(testMessage);
        
        // Act
        Message actualMessage = messageService.getMessageById(1L);
        
        // Assert
        assertNotNull(actualMessage);
        assertEquals(testMessage.getId(), actualMessage.getId());
        assertEquals(testMessage.getMessage(), actualMessage.getMessage());
        verify(messageDao, times(1)).getMessageById(1L);
    }
    
    @Test(expected = ValidationException.class)
    public void testGetMessageById_InvalidId() {
        // Act
        messageService.getMessageById(0L);
    }
    
    @Test(expected = DataNotFoundException.class)
    public void testGetMessageById_NotFound() {
        // Arrange
        when(messageDao.getMessageById(999L)).thenThrow(new DataNotFoundException("Message not found"));
        
        // Act
        messageService.getMessageById(999L);
    }
    
    @Test
    public void testCreateMessage_Success() {
        // Arrange
        when(messageDao.createMessage(any(Message.class))).thenReturn(testMessage);
        
        // Act
        Message createdMessage = messageService.createMessage(testMessage);
        
        // Assert
        assertNotNull(createdMessage);
        assertEquals(testMessage.getMessage(), createdMessage.getMessage());
        verify(messageDao, times(1)).createMessage(any(Message.class));
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateMessage_NullMessage() {
        // Act
        messageService.createMessage(null);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateMessage_EmptyContent() {
        // Arrange
        testMessage.setMessage("");
        
        // Act
        messageService.createMessage(testMessage);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateMessage_TooLongContent() {
        // Arrange
        String longMessage = "a".repeat(501); // 501 characters
        testMessage.setMessage(longMessage);
        
        // Act
        messageService.createMessage(testMessage);
    }
    
    @Test
    public void testUpdateMessage_Success() {
        // Arrange
        when(messageDao.updateMessage(any(Message.class))).thenReturn(testMessage);
        
        // Act
        Message updatedMessage = messageService.updateMessage(testMessage);
        
        // Assert
        assertNotNull(updatedMessage);
        assertEquals(testMessage.getMessage(), updatedMessage.getMessage());
        verify(messageDao, times(1)).updateMessage(any(Message.class));
    }
    
    @Test
    public void testDeleteMessage_Success() {
        // Arrange
        when(messageDao.deleteMessage(1L)).thenReturn(true);
        
        // Act
        boolean result = messageService.deleteMessage(1L);
        
        // Assert
        assertTrue(result);
        verify(messageDao, times(1)).deleteMessage(1L);
    }
    
    @Test
    public void testGetMessagesWithPagination() {
        // Arrange
        List<Message> allMessages = Arrays.asList(
            new Message(1L, "Message 1", "Author 1"),
            new Message(2L, "Message 2", "Author 2"),
            new Message(3L, "Message 3", "Author 3")
        );
        when(messageDao.getAllMessages()).thenReturn(allMessages);
        
        // Act
        List<Message> paginatedMessages = messageService.getMessages(0, 0, 2);
        
        // Assert
        assertEquals(2, paginatedMessages.size());
        assertEquals("Message 1", paginatedMessages.get(0).getMessage());
        assertEquals("Message 2", paginatedMessages.get(1).getMessage());
    }
}
```

### **2. Integration Test**

```java
package com.nish.rest.integration;

import com.nish.rest.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MessageResourceIntegrationTest {
    
    private static final String BASE_URL = "http://localhost:8080/messengers/webapi";
    private Client client = ClientBuilder.newClient();
    
    @Test
    public void testCreateAndRetrieveMessage() {
        // Create message
        Message newMessage = new Message(0, "Integration test message", "Test Author");
        
        WebTarget target = client.target(BASE_URL).path("messages");
        Response createResponse = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newMessage, MediaType.APPLICATION_JSON));
        
        assertEquals(201, createResponse.getStatus());
        
        Message createdMessage = createResponse.readEntity(Message.class);
        assertNotNull(createdMessage);
        assertTrue(createdMessage.getId() > 0);
        
        // Retrieve message
        WebTarget getTarget = target.path(String.valueOf(createdMessage.getId()));
        Response getResponse = getTarget.request(MediaType.APPLICATION_JSON).get();
        
        assertEquals(200, getResponse.getStatus());
        
        Message retrievedMessage = getResponse.readEntity(Message.class);
        assertEquals(createdMessage.getId(), retrievedMessage.getId());
        assertEquals(newMessage.getMessage(), retrievedMessage.getMessage());
    }
    
    @Test
    public void testGetNonExistentMessage() {
        WebTarget target = client.target(BASE_URL).path("messages").path("99999");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        
        assertEquals(404, response.getStatus());
    }
    
    @Test
    public void testUpdateMessage() {
        // First create a message
        Message newMessage = new Message(0, "Original message", "Test Author");
        
        WebTarget target = client.target(BASE_URL).path("messages");
        Response createResponse = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newMessage, MediaType.APPLICATION_JSON));
        
        Message createdMessage = createResponse.readEntity(Message.class);
        
        // Update the message
        createdMessage.setMessage("Updated message");
        
        WebTarget updateTarget = target.path(String.valueOf(createdMessage.getId()));
        Response updateResponse = updateTarget.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(createdMessage, MediaType.APPLICATION_JSON));
        
        assertEquals(200, updateResponse.getStatus());
        
        Message updatedMessage = updateResponse.readEntity(Message.class);
        assertEquals("Updated message", updatedMessage.getMessage());
    }
}
```

### **3. Cucumber BDD Tests**

```gherkin
# src/test/resources/features/message_api.feature
Feature: Message API
  As a client application
  I want to manage messages through REST API
  So that I can create, read, update and delete messages

  Background:
    Given the message API is available at "http://localhost:8080/messengers/webapi"

  Scenario: Create a new message
    Given I have a message with content "Hello World" and author "John Doe"
    When I send a POST request to create the message
    Then the response status should be 201
    And the response should contain the created message with an ID

  Scenario: Retrieve all messages
    Given there are existing messages in the system
    When I send a GET request to retrieve all messages
    Then the response status should be 200
    And the response should contain a list of messages

  Scenario: Retrieve a specific message
    Given there is a message with ID 1 in the system
    When I send a GET request to retrieve message with ID 1
    Then the response status should be 200
    And the response should contain the message details

  Scenario: Update an existing message
    Given there is a message with ID 1 in the system
    When I send a PUT request to update the message with new content "Updated message"
    Then the response status should be 200
    And the message should be updated with the new content

  Scenario: Delete a message
    Given there is a message with ID 1 in the system
    When I send a DELETE request to remove the message
    Then the response status should be 204
    And the message should be removed from the system

  Scenario: Handle non-existent message
    When I send a GET request to retrieve message with ID 99999
    Then the response status should be 404
    And the response should contain an error message

  Scenario: Validate message creation
    Given I have a message with empty content and author "John Doe"
    When I send a POST request to create the message
    Then the response status should be 400
    And the response should contain a validation error message

  Scenario: Pagination support
    Given there are 15 messages in the system
    When I send a GET request with parameters "start=0&size=10"
    Then the response status should be 200
    And the response should contain 10 messages

  Scenario: Filter by year
    Given there are messages from different years in the system
    When I send a GET request with parameter "year=2024"
    Then the response status should be 200
    And the response should contain only messages from 2024
```

```java
// src/test/java/com/nish/rest/steps/MessageApiSteps.java
package com.nish.rest.steps;

import com.nish.rest.model.Message;
import io.cucumber.java.en.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class MessageApiSteps {
    
    private Client client = ClientBuilder.newClient();
    private String baseUrl;
    private Message message;
    private Response response;
    
    @Given("the message API is available at {string}")
    public void the_message_api_is_available_at(String url) {
        this.baseUrl = url;
    }
    
    @Given("I have a message with content {string} and author {string}")
    public void i_have_a_message_with_content_and_author(String content, String author) {
        this.message = new Message(0, content, author);
    }
    
    @When("I send a POST request to create the message")
    public void i_send_a_post_request_to_create_the_message() {
        WebTarget target = client.target(baseUrl).path("messages");
        this.response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(message, MediaType.APPLICATION_JSON));
    }
    
    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatus());
    }
    
    @Then("the response should contain the created message with an ID")
    public void the_response_should_contain_the_created_message_with_an_id() {
        Message createdMessage = response.readEntity(Message.class);
        assertNotNull(createdMessage);
        assertTrue(createdMessage.getId() > 0);
        assertEquals(message.getMessage(), createdMessage.getMessage());
        assertEquals(message.getAuthor(), createdMessage.getAuthor());
    }
    
    @Given("there are existing messages in the system")
    public void there_are_existing_messages_in_the_system() {
        // Assume messages exist or create test data
    }
    
    @When("I send a GET request to retrieve all messages")
    public void i_send_a_get_request_to_retrieve_all_messages() {
        WebTarget target = client.target(baseUrl).path("messages");
        this.response = target.request(MediaType.APPLICATION_JSON).get();
    }
    
    @Then("the response should contain a list of messages")
    public void the_response_should_contain_a_list_of_messages() {
        String responseBody = response.readEntity(String.class);
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("[") && responseBody.contains("]"));
    }
    
    // Additional step implementations...
}
```

---

## Interview Questions & Answers

### **1. REST Fundamentals**

**Q: What is REST and what are its key principles?**

**A:** REST (Representational State Transfer) is an architectural style for designing networked applications. Key principles include:

- **Stateless**: Each request contains all information needed to process it
- **Client-Server**: Separation of concerns between client and server
- **Cacheable**: Responses should be cacheable when appropriate
- **Uniform Interface**: Consistent way to interact with resources
- **Layered System**: Architecture can be composed of hierarchical layers
- **Code on Demand** (optional): Server can send executable code to client

**Q: Explain the difference between PUT and PATCH methods.**

**A:** 
- **PUT**: Complete replacement of a resource. Idempotent. Requires full payload.
- **PATCH**: Partial update of a resource. Non-idempotent. Requires only changed fields.

```java
// PUT - Full update
PUT /messages/1
{
    "id": 1,
    "message": "Updated complete message",
    "author": "New Author",
    "created": "2024-01-01T10:00:00Z"
}

// PATCH - Partial update
PATCH /messages/1
{
    "message": "Updated message only"
}
```

### **2. JAX-RS Specific**

**Q: What is JAX-RS and how does it differ from Spring REST?**

**A:** JAX-RS is a Java specification for RESTful web services, while Spring REST is an implementation:

| Aspect | JAX-RS | Spring REST |
|--------|--------|-------------|
| **Type** | Specification | Framework Implementation |
| **Annotations** | `@Path`, `@GET`, `@POST` | `@RequestMapping`, `@GetMapping` |
| **Dependency Injection** | CDI, HK2 | Spring IoC |
| **Implementations** | Jersey, RESTEasy, CXF | Spring Web MVC |

**Q: Explain different parameter types in JAX-RS.**

**A:**
```java
@Path("/example")
public class ParameterExample {
    
    @GET
    @Path("/{id}")
    public Response getAllParams(
        @PathParam("id") Long id,                    // URL path
        @QueryParam("filter") String filter,        // Query string
        @HeaderParam("Authorization") String auth,   // HTTP header
        @CookieParam("sessionId") String sessionId,  // Cookie
        @MatrixParam("version") String version,      // Matrix params
        @FormParam("username") String username,      // Form data
        @BeanParam UserFilter userFilter,            // Bean parameter
        @Context UriInfo uriInfo                     // Context injection
    ) {
        // Implementation
    }
}
```

### **3. Error Handling**

**Q: How do you implement global exception handling in JAX-RS?**

**A:**
```java
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    
    @Override
    public Response toResponse(Exception exception) {
        ErrorResponse error = new ErrorResponse(
            exception.getMessage(),
            500,
            "Internal Server Error"
        );
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}
```

### **4. Performance & Best Practices**

**Q: How do you handle large data responses in REST APIs?**

**A:** Several strategies:

1. **Pagination**:
```java
@GET
public Response getMessages(@QueryParam("page") @DefaultValue("0") int page,
                          @QueryParam("size") @DefaultValue("20") int size) {
    // Implementation with pagination
}
```

2. **Filtering**:
```java
@GET
public Response getMessages(@QueryParam("filter") String filter,
                          @QueryParam("fields") String fields) {
    // Implementation with filtering and field selection
}
```

3. **Streaming**:
```java
@GET
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public StreamingOutput getLargeData() {
    return output -> {
        // Stream data directly to output
    };
}
```

**Q: What are the best practices for REST API versioning?**

**A:** Four main approaches:

1. **URI Versioning**: `/api/v1/messages`
2. **Query Parameter**: `/api/messages?version=1`
3. **Header Versioning**: `X-API-Version: 1`
4. **Content Negotiation**: `Accept: application/vnd.api-v1+json`

### **5. Security**

**Q: How do you implement authentication and authorization in JAX-RS?**

**A:**
```java
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Access denied")
                        .build()
            );
        }
        
        // Validate token and set security context
        String token = authHeader.substring("Bearer ".length());
        if (!validateToken(token)) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid token")
                        .build()
            );
        }
    }
    
    private boolean validateToken(String token) {
        // Token validation logic
        return true;
    }
}
```

---

## Summary

This comprehensive guide covers:

✅ **Complete REST API Development Workflow**
- Project setup with Maven and Jersey
- Layered architecture (Resource → Service → DAO)
- Error handling and validation
- Testing strategies (Unit, Integration, BDD)

✅ **Production-Ready Features**
- Global exception handling
- Parameter validation
- Content negotiation
- HATEOAS implementation
- Security considerations

✅ **Interview Preparation**
- Core REST concepts and principles
- JAX-RS vs Spring REST comparison
- Best practices and design patterns
- Common questions with detailed answers

This guide provides everything needed to build robust REST APIs and excel in technical interviews for REST API development positions.
