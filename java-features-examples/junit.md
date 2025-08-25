#############################################################
SECTION: Integration Testing
#############################################################
What is Integration Testing?
- Integration testing checks how different modules, components, or services work together as a group.
- It verifies that the interactions between parts of your application (e.g., database, APIs, external services) function correctly.

Why use Integration Testing?
- Ensures that modules/components work together as expected.
- Detects issues that unit tests may miss (e.g., configuration, data flow, API contracts).
- Validates real-world scenarios and workflows.
- Helps catch bugs early in the development cycle.

Benefits of Integration Testing:

Step-by-Step Guide: How to Perform Integration Testing (and Why Each Step Matters)

1. Identify Integration Points
	- Why: Focuses your tests on the places where modules interact (e.g., service-to-service, database access, API calls).

2. Set Up the Test Environment
	- Why: Ensures all dependencies (databases, services, configs) are available and configured as in production, so tests are realistic.

3. Prepare Test Data
	- Why: Guarantees consistent, repeatable results and covers both typical and edge-case scenarios.

4. Write Integration Test Cases
	- Why: Documents expected interactions and outcomes, making it easier to spot failures and maintain tests.

5. Execute Tests and Monitor Interactions
	- Why: Validates that modules communicate correctly, data flows as expected, and errors are handled gracefully.

6. Verify Results and Side Effects
	- Why: Checks not only direct outputs but also changes in state, database updates, and external system effects.

7. Clean Up After Tests
	- Why: Removes test data and resets environment to prevent interference with future tests and maintain reliability.

8. Analyze Failures and Debug
	- Why: Helps pinpoint integration issues, configuration errors, or contract mismatches that unit tests may miss.

9. Automate and Integrate with CI/CD
	- Why: Ensures integration tests run regularly, catching issues early and supporting continuous delivery.
/**
 * JUnit & Mockito Learning Exercise
 * #############################################################
 * SECTION: JUnit Overview
 * #############################################################
 * What is JUnit?
 * JUnit is a popular Java framework for writing and running unit and integration tests. It provides annotations and assertions to structure and verify your tests.

 * Common JUnit Annotations:
 * - @Test — Marks a method as a test.
 * - @BeforeEach — Runs before each test method (setup).
 * - @AfterEach — Runs after each test method (cleanup).
 * - @BeforeAll — Runs once before all tests in the class.
 * - @AfterAll — Runs once after all tests in the class.

 * Common JUnit Assertions:
 * - assertEquals(expected, actual) — Checks if two values are equal.
 * - assertTrue(condition) — Checks if a condition is true.
 * - assertFalse(condition) — Checks if a condition is false.
 * - assertNotNull(object) — Checks if an object is not null.
 * - assertThrows(Exception.class, () -> { ... }) — Checks if code throws an exception.

 * #############################################################
 * SECTION: Mockito Overview
 * #############################################################
 * What is Mockito?
 * Mockito is a Java library for creating mock objects. It allows you to simulate dependencies, control their behavior, and verify interactions.

 * Types of Mockito Mocks:
 * - Mock: A basic mock object, created with mock(). Used to stub and verify interactions.
 * - Spy: Wraps a real object, allowing selective stubbing and verification while calling real methods by default.
 * - Partial Mock: Only some methods are mocked, others use real implementation (usually via spy()).
 * - Fake: A lightweight implementation, not created by Mockito, but sometimes used in tests.
 * - Stub: A mock with predefined behavior for certain methods (using when().thenReturn()).

 * Why use Mockito?
 * - Isolate the class under test from its dependencies.
 * - Simulate error conditions and edge cases easily.
 * - Verify interactions (method calls, arguments, call counts).
 * - Control return values and exceptions from dependencies.
 * - Test code that depends on external systems (DB, web services) without needing those systems.
 * - Improve test speed and reliability.

 * Major Mockito Features:
 * - mock(Class<T>): Create a mock object.
 * - spy(Object): Create a spy (partial mock) of a real object.
 * - when(mock.method()).thenReturn(value): Stub a method to return a value.
 * - when(mock.method()).thenThrow(exception): Stub a method to throw an exception.
 * - doReturn(value).when(mock).method(): Alternative stubbing for void methods or spies.
 * - doThrow(exception).when(mock).method(): Make a mock throw an exception for void methods.
 * - verify(mock).method(): Verify that a method was called.
 * - verify(mock, times(n)).method(): Verify method was called n times.
 * - verifyNoMoreInteractions(mock): Ensure no unexpected calls were made.
 * - ArgumentMatchers.any(), eq(), isA(), etc.: Match arguments in stubbing and verification.
 * - InOrder: Verify calls happened in a specific order.
 * - @Mock, @Spy, @InjectMocks: Annotations for cleaner test setup.
 * - reset(mock): Reset a mock to its initial state.
 * - Captor: Capture arguments passed to methods for further assertions.
 * - Answers: Custom behavior for stubbing (using thenAnswer()).

 * Common Mockito Methods:
 * - mock(Class<T> classToMock) — Creates a mock object.
 * - when(mock.method()).thenReturn(value) — Stubs a method to return a value.
 * - verify(mock).method() — Verifies that a method was called.
 * - doThrow(exception).when(mock).method() — Makes a mock throw an exception.
 * - any(Class<T> type) — Matches any argument of a given type.

 * #############################################################
 * SECTION: JUnit vs. Mockito
 * #############################################################
 * - JUnit is for structuring and running tests, and making assertions.
 * - Mockito is for mocking dependencies and verifying interactions.
 * - Use JUnit for all test logic; use Mockito when you need to isolate the class under test from its dependencies.

 * #############################################################
 * SECTION: Test Types (Happy Path, Edge Case, Error Path)
 * #############################################################
 * - Happy Path: Valid input, expected result.
 * - Edge Case: Unusual or boundary input (empty, null, max/min values).
 * - Error Path: Invalid input, exceptions, or failures.

 * #############################################################
 * SECTION: Example - JUnit + Mockito Test Class
 * #############################################################
 * import org.junit.jupiter.api.*;
 * import static org.junit.jupiter.api.Assertions.*;
 * import org.mockito.*;

 * class UserServiceTest {
 *     @Mock
 *     UserRepository userRepository;

 *     @InjectMocks
 *     UserService userService;

 *     @BeforeEach
 *     void setup() {
 *         MockitoAnnotations.openMocks(this);
 *     }

 *     @Test
 *     void testCreateUserHappyPath() {
 *         User user = new User("Alice", "alice@example.com");
 *         Mockito.when(userRepository.save(user)).thenReturn(user);

 *         User result = userService.createUser(user);

 *         assertNotNull(result);
 *         assertEquals("Alice", result.getName());
 *         Mockito.verify(userRepository).save(user);
 *     }

 *     @Test
 *     void testCreateUserWithInvalidEmail() {
 *         User user = new User("Bob", "invalid-email");
 *         Exception ex = assertThrows(IllegalArgumentException.class, () -> {
 *             userService.createUser(user);
 *         });
 *         assertEquals("Invalid email", ex.getMessage());
 *     }

 *     @Test
 *     void testGetUserNotFound() {
 *         Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
 *         Exception ex = assertThrows(UserNotFoundException.class, () -> {
 *             userService.getUser(1L);
 *         });
 *         assertEquals("User not found", ex.getMessage());
 *     }
 * }

 * #############################################################
 * SECTION: Why use JUnit and Mockito?
 * #############################################################
 * - JUnit ensures your code works as intended.
 * - Mockito allows you to test classes in isolation, without relying on real dependencies.
 * - Together, they help you build robust, maintainable, and well-tested applications.
 */
#############################################################
SECTION: Test Driven Development (TDD)
#############################################################
What is TDD?
- TDD (Test Driven Development) is a software development approach where tests are written before the code itself.
- The cycle: Write a test → Run the test (it fails) → Write code → Run the test (it passes) → Refactor.

Types of TDD:
- Classic TDD: Write unit tests before code, focus on small units/classes.
- Acceptance TDD (ATDD): Write acceptance tests from user stories before development.
- Behavior Driven Development (BDD): Write tests in natural language (see Gherkin/Cucumber below).

Why use TDD?
- Ensures code meets requirements from the start.
- Improves code quality and design.
- Reduces bugs and regression issues.
- Provides living documentation.
- Encourages small, focused units of code.
- Makes refactoring safer and easier.
- Increases developer confidence.

Benefits of TDD:
- Faster feedback and bug detection.
- Easier maintenance and refactoring.
- Better test coverage.
- Clear requirements and design.

#############################################################
SECTION: Gherkin & Cucumber
#############################################################
What is Gherkin?
- Gherkin is a language for writing BDD test scenarios in a human-readable format.
- Uses keywords: Feature, Scenario, Given, When, Then, And, But.
- Example:
  Feature: User Login
	Scenario: Successful login
	  Given the user is on the login page
	  When the user enters valid credentials
	  Then the user should be redirected to the dashboard

What is Cucumber?
- Cucumber is a tool for running automated tests written in Gherkin.
- Integrates with Java (and other languages) to map Gherkin steps to code.
- Supports BDD by connecting requirements to implementation.

Benefits:
- Improves communication and shared understanding.
- Tests are readable by non-technical stakeholders.
- Bridges gap between requirements and code.

#############################################################
SECTION: Java Selenium Overview
#############################################################
What is Selenium?
- Selenium is a popular tool for automating web browser interactions.
- Used for UI testing of web applications.

Key Features:
- Supports multiple browsers (Chrome, Firefox, Edge, etc.).
- Can be used with Java, Python, C#, etc.
- Integrates with JUnit, TestNG, and Cucumber for test automation.

Benefits:
- Automates repetitive UI tests.
- Enables regression testing.
- Supports cross-browser and cross-platform testing.
- Can be integrated into CI/CD pipelines.

Example (Java + Selenium + JUnit):
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

class LoginUITest {
	WebDriver driver;

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
	}

	@Test
	void testLoginSuccess() {
		driver.get("https://example.com/login");
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pass");
		driver.findElement(By.id("loginBtn")).click();
		Assertions.assertTrue(driver.getCurrentUrl().contains("dashboard"));
	}

	@AfterEach
	void tearDown() {
		driver.quit();
	}
}
