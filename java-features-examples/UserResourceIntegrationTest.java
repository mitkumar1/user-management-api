/**
 * Cucumber BDD Testing: Theory and Benefits
 *
 * Cucumber is a tool for Behavior-Driven Development (BDD) that allows you to write tests in plain language
 * (Gherkin syntax) that non-technical stakeholders can understand. Cucumber scenarios describe application behavior
 * from the user's perspective, making requirements and tests easy to read and validate.
 *
 * Benefits of Cucumber:
 * - Bridges the gap between business and technical teams by using human-readable language.
 * - Ensures requirements are clear, testable, and traceable to code.
 * - Encourages collaboration and shared understanding of features.
 * - Automates acceptance criteria, reducing manual testing effort.
 *
 * How Cucumber Differs from JUnit Integration Tests:
 * - Cucumber focuses on end-to-end user scenarios, written in Gherkin (Given-When-Then), while JUnit integration tests
 *   are written in Java and focus on technical validation of system components.
 * - Cucumber tests validate business flows and acceptance criteria; JUnit integration tests validate system wiring and data flow.
 * - Cucumber is ideal for acceptance and regression testing; JUnit is ideal for verifying code correctness and integration.
 *
 * In summary: Use Cucumber for business-facing, scenario-driven tests; use JUnit for technical, code-level integration tests.
 */
package com.kumar.wipro.api.integration;

import com.kumar.wipro.api.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserResourceIntegrationTest {

    private static final String BASE_URL = "http://localhost:8080/api";
    private Client client = ClientBuilder.newClient();

    @Test
    public void testCreateAndRetrieveUser() {
        User newUser = new User(0, "Integration test user", "testuser@example.com");
        WebTarget target = client.target(BASE_URL).path("users");

        Response createResponse = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON));
        assertEquals(201, createResponse.getStatus());

        User createdUser = createResponse.readEntity(User.class);
        assertNotNull(createdUser);
        assertTrue(createdUser.getId() > 0);

        WebTarget getTarget = target.path(String.valueOf(createdUser.getId()));
        Response getResponse = getTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, getResponse.getStatus());

        User retrievedUser = getResponse.readEntity(User.class);
        assertEquals(createdUser.getId(), retrievedUser.getId());
        assertEquals(newUser.getName(), retrievedUser.getName());
        assertEquals(newUser.getEmail(), retrievedUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User(0, "Update Test User", "updateuser@example.com");
        WebTarget target = client.target(BASE_URL).path("users");

        Response createResponse = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON));
        assertEquals(201, createResponse.getStatus());

        User createdUser = createResponse.readEntity(User.class);
        assertNotNull(createdUser);

        // Update user details
        createdUser.setName("Updated Name");
        createdUser.setEmail("updatedemail@example.com");

        WebTarget putTarget = target.path(String.valueOf(createdUser.getId()));
        Response putResponse = putTarget.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(createdUser, MediaType.APPLICATION_JSON));
        assertEquals(200, putResponse.getStatus());

        User updatedUser = putResponse.readEntity(User.class);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("updatedemail@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User newUser = new User(0, "Delete Test User", "deleteuser@example.com");
        WebTarget target = client.target(BASE_URL).path("users");

        Response createResponse = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON));
        assertEquals(201, createResponse.getStatus());

        User createdUser = createResponse.readEntity(User.class);
        assertNotNull(createdUser);

        WebTarget deleteTarget = target.path(String.valueOf(createdUser.getId()));
        Response deleteResponse = deleteTarget.request().delete();
        assertEquals(204, deleteResponse.getStatus());

        // Try to retrieve deleted user
        Response getResponse = deleteTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, getResponse.getStatus());
    }

    @Test
    public void testGetUserNotFound() {
        WebTarget target = client.target(BASE_URL).path("users/999999");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testCreateUserWithInvalidData() {
        // Missing name and email
        User invalidUser = new User(0, "", "");
        WebTarget target = client.target(BASE_URL).path("users");

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(invalidUser, MediaType.APPLICATION_JSON));
        assertEquals(400, response.getStatus());
    }
}

/**
 * Jersey Client and REST API End-to-End Testing:
 *
 * Jersey client is a Java library for building and consuming RESTful web services. In integration tests,
 * it allows you to send real HTTP requests to your API endpoints, simulating actual client behavior.
 * This approach tests the full stack: controller, service, repository, and database, ensuring that all layers
 * work together as expected.
 *
 * Advantages of End-to-End REST API Testing:
 * - Validates the entire request/response lifecycle, including serialization, validation, and error handling.
 * - Detects issues that may not appear in isolated unit tests (e.g., misconfigured routes, security, data mapping).
 * - Ensures the API contract is honored for real clients and consumers.
 * - Provides confidence that the deployed application will work as intended.
 *
 * Common HTTP Methods in REST APIs:
 * - GET: Retrieve data from the server (e.g., fetch a user or list of users).
 * - POST: Create new resources (e.g., add a new user).
 * - PUT: Update an existing resource completely (e.g., replace user details).
 * - PATCH: Partially update an existing resource (e.g., change only the user's email).
 * - DELETE: Remove a resource (e.g., delete a user).
 * - HEAD: Retrieve only the headers for a resource (useful for checking existence or metadata).
 * - OPTIONS: Discover supported HTTP methods for a resource (useful for CORS and API clients).
 *
 * Using these methods in integration tests ensures your API behaves correctly for all standard operations and
 * follows RESTful principles.
 */
