/**
 * Cucumber BDD Testing: Theory, Usage, and HTTP Methods
 *
 * What is Cucumber?
 * Cucumber is a tool for Behavior-Driven Development (BDD) that lets you write tests in plain language (Gherkin syntax).
 * These tests describe application behavior from the user's perspective, making requirements and test cases easy for
 * both technical and non-technical stakeholders to read and validate.
 *
 * Gherkin Syntax:
 * - Scenarios are written in Given-When-Then format.
 *   Example:
 *     Given a user exists
 *     When the client sends a GET request to /api/users/{id}
 *     Then the response status should be 200
 *     And the response should contain the user's details
 *
 * How Cucumber Works:
 * - Feature files contain scenarios written in Gherkin.
 * - Step definitions (Java code) implement the actions described in each scenario.
 * - Cucumber executes the steps, interacting with your application (e.g., sending HTTP requests to your API).
 *
 * Benefits of Cucumber:
 * - Bridges the gap between business and technical teams using human-readable language.
 * - Ensures requirements are clear, testable, and traceable to code.
 * - Encourages collaboration and shared understanding of features.
 * - Automates acceptance criteria, reducing manual testing effort.
 * - Validates business flows end-to-end, not just technical correctness.
 *
 * HTTP Methods in Cucumber API Testing:
 * - GET: Used in scenarios to verify data retrieval.
 * - POST: Used to test resource creation.
 * - PUT: Used to test full resource updates.
 * - PATCH: Used to test partial updates.
 * - DELETE: Used to test resource removal.
 * - HEAD/OPTIONS: Used to test metadata and supported operations.
 *
 * Why Use Cucumber for API Testing?
 * - Validates that the API meets business requirements and acceptance criteria.
 * - Ensures the API contract is honored for real-world scenarios.
 * - Provides living documentation for your API.
 * - Complements JUnit integration tests by focusing on user-facing behavior and flows.
 *
 * In summary: Use Cucumber for business-facing, scenario-driven tests that validate end-to-end behavior and requirements.
 */

package com.kumar.wipro.api.steps;

import com.kumar.wipro.api.model.User;
import io.cucumber.java.en.*;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static org.junit.Assert.*;

public class UserApiSteps {

    // Jersey client for making HTTP requests
    private Client client = ClientBuilder.newClient();

    // Base URL for the API, set in the background step
    private String baseUrl;

    // User object used for requests
    private User user;

    // Response object to store API responses
    private Response response;

    // Step: Set the base URL for the API
    @Given("the user API is available at {string}")
    public void the_user_api_is_available_at(String url) {
        this.baseUrl = url;
    }

    // Step: Create a user object with name and email
    @Given("I have a user with name {string} and email {string}")
    public void i_have_a_user_with_name_and_email(String name, String email) {
        this.user = new User(0, name, email);
    }

    // Step: Send a POST request to create the user
    @When("I send a POST request to create the user")
    public void i_send_a_post_request_to_create_the_user() {
        WebTarget target = client.target(baseUrl).path("users");
        this.response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
    }

    // Step: Send a GET request to retrieve the user by ID
    @When("I send a GET request to retrieve the user with ID {int}")
    public void i_send_a_get_request_to_retrieve_the_user_with_id(Integer id) {
        WebTarget target = client.target(baseUrl).path("users").path(String.valueOf(id));
        this.response = target.request(MediaType.APPLICATION_JSON).get();
    }

    // Step: Send a PUT request to update the user
    @When("I send a PUT request to update the user with ID {int} and name {string} and email {string}")
    public void i_send_a_put_request_to_update_the_user_with_id_and_name_and_email(Integer id, String name, String email) {
        User updatedUser = new User(id, name, email);
        WebTarget target = client.target(baseUrl).path("users").path(String.valueOf(id));
        this.response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedUser, MediaType.APPLICATION_JSON));
    }

    // Step: Send a DELETE request to remove the user
    @When("I send a DELETE request to remove the user with ID {int}")
    public void i_send_a_delete_request_to_remove_the_user_with_id(Integer id) {
        WebTarget target = client.target(baseUrl).path("users").path(String.valueOf(id));
        this.response = target.request().delete();
    }

    // Step: Assert the response status code
    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatus());
    }

    // Step: Assert the response contains the created user with an ID
    @Then("the response should contain the created user with an ID")
    public void the_response_should_contain_the_created_user_with_an_id() {
        User createdUser = response.readEntity(User.class);
        assertNotNull(createdUser);
        assertTrue(createdUser.getId() > 0);
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    // Step: Assert the response contains the updated user details
    @Then("the response should contain the updated user with name {string} and email {string}")
    public void the_response_should_contain_the_updated_user_with_name_and_email(String name, String email) {
        User updatedUser = response.readEntity(User.class);
        assertNotNull(updatedUser);
        assertEquals(name, updatedUser.getName());
        assertEquals(email, updatedUser.getEmail());
    }

    // Step: Assert the response is empty (for DELETE)
    @Then("the response should be empty")
    public void the_response_should_be_empty() {
        assertTrue(response.readEntity(String.class).isEmpty());
    }
}
