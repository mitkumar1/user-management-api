Feature: User API
  As a client application
  I want to manage users through REST API
  So that I can create, read, update and delete users

  Background:
    Given the user API is available at "http://localhost:8080/api"

  Scenario: Create a new user
    Given I have a user with name "John Doe" and email "john@example.com"
    When I send a POST request to create the user
    Then the response status should be 201
    And the response should contain the created user with an ID

  Scenario: Retrieve all users
    When I send a GET request to retrieve all users
    Then the response status should be 200
    And the response should contain a list of users

  Scenario: Update an existing user
    Given there is a user with ID 1 in the system
    When I send a PUT request to update the user with new name "Jane Doe"
    Then the response status should be 200
    And the user should be updated with the new name

  Scenario: Delete a user
    Given there is a user with ID 1 in the system
    When I send a DELETE request to remove the user
    Then the response status should be 204
    And the user should be removed from the system
