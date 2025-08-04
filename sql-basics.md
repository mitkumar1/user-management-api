############################################################
# SQL Basics - Complete Guide
############################################################

## Table of Contents
1. [What is Database](#what-is-database)
2. [DDL, DML, DCL](#ddl-dml-dcl)
3. [Keys (Primary, Foreign, Candidate)](#keys)
4. [Subqueries](#subqueries)
5. [Indexes](#indexes)
6. [Views and Schemas](#views-and-schemas)
7. [SQL Injection](#sql-injection)
8. [Set Operations and Grouping](#set-operations-and-grouping)
9. [SQL Clauses](#sql-clauses)
10. [Aggregate Functions](#aggregate-functions)
11. [Joins](#joins)
12. [Functions and Stored Procedures](#functions-and-stored-procedures)
13. [Execution Plans](#execution-plans)
14. [Senior Java FSD Interview Questions](#senior-java-fsd-interview-questions)

---

############################################################
## What is Database
############################################################

A **database** is an organized collection of structured information or data, typically stored electronically in a computer system. It's managed by a Database Management System (DBMS).

### Key Characteristics:
- **Data Integrity**: Ensures accuracy and consistency
- **Data Security**: Controls access and protects data
- **Concurrent Access**: Multiple users can access simultaneously
- **Backup and Recovery**: Data protection mechanisms
- **ACID Properties**: Atomicity, Consistency, Isolation, Durability

### Types of Databases:
1. **Relational Database** (RDBMS) - MySQL, PostgreSQL, Oracle
2. **NoSQL Database** - MongoDB, Cassandra, Redis
3. **In-Memory Database** - Redis, Memcached
4. **Graph Database** - Neo4j, Amazon Neptune

---

############################################################
## DDL, DML, DCL
############################################################

1. ### DDL (Data Definition Language)
Commands that define the database structure.

```sql
-- CREATE: Create database objects
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    department_id INT,
    salary DECIMAL(10,2),
    hire_date DATE
);

-- ALTER: Modify existing structure
ALTER TABLE employees 
ADD COLUMN phone VARCHAR(15);

ALTER TABLE employees 
MODIFY COLUMN salary DECIMAL(12,2);

-- DROP: Delete database objects
DROP TABLE temp_table;

-- TRUNCATE: Remove all data but keep structure
TRUNCATE TABLE employees;
```

2. ### DML (Data Manipulation Language)
Commands that manipulate data within tables.

```sql
-- INSERT: Add new records
INSERT INTO employees (id, name, email, department_id, salary, hire_date)
VALUES (1, 'John Doe', 'john@company.com', 101, 75000.00, '2023-01-15');

-- UPDATE: Modify existing records
UPDATE employees 
SET salary = salary * 1.10 
WHERE department_id = 101;

-- DELETE: Remove records
DELETE FROM employees 
WHERE hire_date < '2020-01-01';

-- SELECT: Retrieve data
SELECT * FROM employees 
WHERE salary > 50000;
```

3. ### DCL (Data Control Language)
Commands that control access to database.

```sql
-- GRANT: Give permissions
GRANT SELECT, INSERT ON employees TO 'hr_user'@'localhost';

-- REVOKE: Remove permissions
REVOKE INSERT ON employees FROM 'hr_user'@'localhost';

-- CREATE USER: Create new database user
CREATE USER 'app_user'@'localhost' IDENTIFIED BY 'secure_password';
```

---

############################################################
## Keys
############################################################

4. ### Primary Key
- Uniquely identifies each record in a table
- Cannot be NULL
- Only one primary key per table

```sql
CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    email VARCHAR(100),
    name VARCHAR(100)
);

5. -- Composite Primary Key
CREATE TABLE order_items (
    order_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (order_id, product_id)
);
```

6. ### Foreign Key
- Links two tables together
- References the primary key of another table
- Maintains referential integrity ("Maintains referential integrity" means that a foreign key in one table always refers to a valid, existing primary key in another table. This ensures relationships between tables remain consistent—so you can't have an order referencing a customer that doesn't exist, for example. The database enforces these rules automatically, preventing orphaned or invalid references.)

```sql
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
```

### Candidate Key
- A column or combination of columns that can uniquely identify records
- Can become primary key
- Multiple candidate keys can exist

```sql
-- Example: Both employee_id and email can be candidate keys
CREATE TABLE employees (
    employee_id INT,
    email VARCHAR(100) UNIQUE,
    ssn VARCHAR(11) UNIQUE,
    name VARCHAR(100),
    PRIMARY KEY (employee_id)
);
-- Here email and ssn are candidate keys
```

---

############################################################
## Subqueries
############################################################

A **subquery** is a query nested inside another query. It can be used in SELECT, INSERT, UPDATE, or DELETE statements.

### Types of Subqueries:

#### 1. Scalar Subquery (returns single value)
```sql
-- Find employees with salary above average
SELECT name, salary 
FROM employees 
WHERE salary > (SELECT AVG(salary) FROM employees);
```

#### 2. Row Subquery (returns single row)
```sql
-- Find employee with same department and salary as John
SELECT * FROM employees 
WHERE (department_id, salary) = (
    SELECT department_id, salary 
    FROM employees 
    WHERE name = 'John Doe'
);
```

#### 3. Table Subquery (returns multiple rows)
```sql
-- Find employees in departments with more than 10 employees
SELECT * FROM employees 
WHERE department_id IN (
    SELECT department_id 
    FROM employees 
    GROUP BY department_id 
    HAVING COUNT(*) > 10
);
```

#### 4. Correlated Subquery
```sql
-- Find employees earning more than department average
SELECT e1.name, e1.salary, e1.department_id
FROM employees e1
WHERE e1.salary > (
    SELECT AVG(e2.salary)
    FROM employees e2
    WHERE e2.department_id = e1.department_id
);
```

#### 5. EXISTS Subquery
```sql
-- Find customers who have placed orders
SELECT c.name 
FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o 
    WHERE o.customer_id = c.customer_id
);
```

---

############################################################
## Indexes
############################################################

An **index** is a database object that improves the speed of data retrieval operations.

### Types of Indexes:

#### 1. Clustered Index
- Physically reorders table data
- Only one per table
- Usually created on primary key

```sql
-- Primary key automatically creates clustered index
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10,2)
);
```

#### 2. Non-Clustered Index
- Creates separate structure pointing to data rows
- Multiple allowed per table

```sql
-- Create index on frequently searched columns
CREATE INDEX idx_employee_email ON employees(email);
CREATE INDEX idx_employee_dept_salary ON employees(department_id, salary);
```

#### 3. Unique Index
- Ensures uniqueness and improves performance

```sql
CREATE UNIQUE INDEX idx_employee_ssn ON employees(ssn);
```

#### 4. Composite Index
- Index on multiple columns

```sql
CREATE INDEX idx_order_date_customer ON orders(order_date, customer_id);
```

#### 5. Partial Index (PostgreSQL)
```sql
-- Index only active employees
CREATE INDEX idx_active_employees ON employees(department_id) 
WHERE status = 'ACTIVE';
```

### When to Use Indexes:
- ✅ Frequently searched columns
- ✅ JOIN conditions
- ✅ ORDER BY columns
- ❌ Small tables
- ❌ Frequently updated columns

---

############################################################
## Views and Schemas
############################################################

### Views
A **view** is a virtual table based on the result of an SQL statement.

```sql
-- Create a view
CREATE VIEW employee_summary AS
SELECT 
    e.name,
    e.salary,
    d.department_name,
    CASE 
        WHEN e.salary > 80000 THEN 'Senior'
        WHEN e.salary > 50000 THEN 'Mid-level'
        ELSE 'Junior'
    END AS level
FROM employees e
JOIN departments d ON e.department_id = d.department_id
WHERE e.status = 'ACTIVE';

-- Use the view
SELECT * FROM employee_summary WHERE level = 'Senior';

-- Update through view (if updatable)
UPDATE employee_summary SET salary = 85000 WHERE name = 'John Doe';
```

#### When to Use Views:
- **Security**: Hide sensitive columns
- **Simplification**: Complex queries
- **Abstraction**: Present data differently
- **Reusability**: Common query patterns

### Schemas
A **schema** is a logical container for database objects.

```sql
-- Create schema
CREATE SCHEMA hr;
CREATE SCHEMA finance;

-- Create tables in schema
CREATE TABLE hr.employees (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE finance.budgets (
    id INT PRIMARY KEY,
    amount DECIMAL(15,2)
);

-- Access tables
SELECT * FROM hr.employees;
SELECT * FROM finance.budgets;
```

#### When to Use Schemas:
- **Organization**: Group related objects
- **Security**: Schema-level permissions
- **Multi-tenancy**: Separate customer data
- **Environment separation**: dev, test, prod

### Views vs Schemas:

| Aspect | Views | Schemas |
|--------|-------|---------|
| Purpose | Virtual tables | Logical containers |
| Data Storage | No physical storage | Contains physical objects |
| Security | Column-level hiding | Object-level access |
| Performance | May impact performance | No performance impact |
| Use Case | Simplify complex queries | Organize database objects |

---

############################################################
## SQL Injection
############################################################

**SQL Injection** is a security vulnerability where malicious SQL code is inserted into application queries.

### Example of Vulnerable Code:
```java
// VULNERABLE - Never do this!
String query = "SELECT * FROM users WHERE username = '" + username + 
               "' AND password = '" + password + "'";
```

### Attack Example:
```sql
-- Malicious input: username = "admin'; --"
-- Results in: SELECT * FROM users WHERE username = 'admin'; --' AND password = '...'
-- The -- comments out the password check!
```

### Prevention Methods:

#### 1. Prepared Statements (Best Practice)
```java
// SECURE - Use prepared statements
String query = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement stmt = connection.prepareStatement(query);
stmt.setString(1, username);
stmt.setString(2, password);
ResultSet rs = stmt.executeQuery();
```

#### 2. Stored Procedures
```sql
DELIMITER //
CREATE PROCEDURE GetUser(IN p_username VARCHAR(100), IN p_password VARCHAR(100))
BEGIN
    SELECT * FROM users 
    WHERE username = p_username AND password = p_password;
END //
DELIMITER ;
```

#### 3. Input Validation
```java
// Validate input
if (!username.matches("^[a-zA-Z0-9_]+$")) {
    throw new IllegalArgumentException("Invalid username format");
}
```

---

############################################################
## Set Operations and Grouping
############################################################

### UNION
Combines results from multiple SELECT statements.

```sql
-- UNION (removes duplicates)
SELECT name FROM employees WHERE department_id = 1
UNION
SELECT name FROM employees WHERE salary > 70000;

-- UNION ALL (keeps duplicates)
SELECT department_id FROM employees
UNION ALL
SELECT department_id FROM contractors;
```

### GROUP BY
Groups rows with same values into summary rows.

```sql
-- Basic grouping
SELECT department_id, COUNT(*) as employee_count, AVG(salary) as avg_salary
FROM employees
GROUP BY department_id;

-- Multiple columns grouping
SELECT department_id, job_title, COUNT(*) as count
FROM employees
GROUP BY department_id, job_title
ORDER BY department_id, job_title;
```

### HAVING
Filters groups (used with GROUP BY).

```sql
-- Find departments with more than 5 employees and average salary > 60000
SELECT department_id, COUNT(*) as emp_count, AVG(salary) as avg_sal
FROM employees
GROUP BY department_id
HAVING COUNT(*) > 5 AND AVG(salary) > 60000;
```

### EXISTS
Tests for the existence of records in a subquery.

```sql
-- Find customers who have placed orders
SELECT customer_id, name
FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o 
    WHERE o.customer_id = c.customer_id
);

-- Find customers who haven't placed orders
SELECT customer_id, name
FROM customers c
WHERE NOT EXISTS (
    SELECT 1 FROM orders o 
    WHERE o.customer_id = c.customer_id
);
```

### ANY/ALL
Compare a value to any or all values in a subquery.

```sql
-- ANY: salary greater than any salary in department 1
SELECT name, salary
FROM employees
WHERE salary > ANY (
    SELECT salary FROM employees WHERE department_id = 1
);

-- ALL: salary greater than all salaries in department 1
SELECT name, salary
FROM employees
WHERE salary > ALL (
    SELECT salary FROM employees WHERE department_id = 1
);
```

---

############################################################
## SQL Clauses
############################################################

### WHERE
Filters records based on specified conditions.

```sql
SELECT * FROM employees 
WHERE salary > 50000 
  AND department_id IN (1, 2, 3)
  AND hire_date >= '2020-01-01';
```

### ORDER BY
Sorts the result set.

```sql
-- Single column
SELECT * FROM employees ORDER BY salary DESC;

-- Multiple columns
SELECT * FROM employees 
ORDER BY department_id ASC, salary DESC;

-- With expressions
SELECT name, salary, salary * 12 as annual_salary
FROM employees
ORDER BY annual_salary DESC;
```

### Logical Operators (AND, OR, NOT)
```sql
-- AND: All conditions must be true
SELECT * FROM employees 
WHERE salary > 50000 AND department_id = 1;

-- OR: At least one condition must be true
SELECT * FROM employees 
WHERE department_id = 1 OR department_id = 2;

-- NOT: Negates a condition
SELECT * FROM employees 
WHERE NOT department_id = 1;

-- Complex combinations
SELECT * FROM employees 
WHERE (department_id = 1 OR department_id = 2) 
  AND salary > 60000 
  AND NOT status = 'INACTIVE';
```

### INSERT INTO
Adds new records to a table.

```sql
-- Insert single record
INSERT INTO employees (name, email, department_id, salary)
VALUES ('John Doe', 'john@company.com', 1, 75000);

-- Insert multiple records
INSERT INTO employees (name, email, department_id, salary)
VALUES 
    ('Jane Smith', 'jane@company.com', 2, 80000),
    ('Bob Johnson', 'bob@company.com', 1, 65000);

-- Insert from SELECT
INSERT INTO employees_backup 
SELECT * FROM employees WHERE hire_date < '2020-01-01';
```

### NULL Handling
```sql
-- IS NULL / IS NOT NULL
SELECT * FROM employees WHERE phone IS NULL;
SELECT * FROM employees WHERE phone IS NOT NULL;

-- COALESCE (returns first non-null value)
SELECT name, COALESCE(phone, email, 'No contact') as contact
FROM employees;

-- NULLIF (returns NULL if values are equal)
SELECT name, NULLIF(salary, 0) as salary
FROM employees;
```

### VALUES
Used with INSERT statements.

```sql
-- Direct values
INSERT INTO departments VALUES (1, 'HR'), (2, 'Engineering');

-- With columns specified
INSERT INTO departments (id, name) 
VALUES (3, 'Marketing'), (4, 'Sales');
```

---

############################################################
## Aggregate Functions
############################################################

### Basic Aggregate Functions

#### COUNT
```sql
-- Count all rows
SELECT COUNT(*) FROM employees;

-- Count non-null values
SELECT COUNT(phone) FROM employees;

-- Count distinct values
SELECT COUNT(DISTINCT department_id) FROM employees;
```

#### SUM
```sql
-- Total salary budget
SELECT SUM(salary) as total_salary FROM employees;

-- Sum by department
SELECT department_id, SUM(salary) as dept_budget
FROM employees
GROUP BY department_id;
```

#### AVG
```sql
-- Average salary
SELECT AVG(salary) as avg_salary FROM employees;

-- Average by department with rounding
SELECT department_id, ROUND(AVG(salary), 2) as avg_salary
FROM employees
GROUP BY department_id;
```

#### MIN/MAX
```sql
-- Salary range
SELECT MIN(salary) as min_sal, MAX(salary) as max_sal FROM employees;

-- Earliest and latest hire dates
SELECT MIN(hire_date) as first_hire, MAX(hire_date) as latest_hire
FROM employees;
```

### String Functions

#### LIKE with Wildcards
```sql
-- % matches any sequence of characters
SELECT * FROM employees WHERE name LIKE 'John%';
SELECT * FROM employees WHERE email LIKE '%@gmail.com';

-- _ matches single character
SELECT * FROM employees WHERE phone LIKE '555-____-1234';

-- Case insensitive search
SELECT * FROM employees WHERE UPPER(name) LIKE 'JOHN%';
```

#### String Functions
```sql
-- Length and substring
SELECT name, LENGTH(name) as name_length,
       SUBSTRING(name, 1, 3) as first_three
FROM employees;

-- Case conversion
SELECT UPPER(name) as upper_name,
       LOWER(email) as lower_email
FROM employees;

-- Concatenation
SELECT CONCAT(name, ' - ', email) as full_info
FROM employees;
```

### Range and Set Functions

#### IN
```sql
-- Check if value is in a list
SELECT * FROM employees 
WHERE department_id IN (1, 2, 3);

-- Using subquery
SELECT * FROM employees 
WHERE department_id IN (
    SELECT id FROM departments WHERE name LIKE '%Engineering%'
);
```

#### BETWEEN
```sql
-- Salary range
SELECT * FROM employees 
WHERE salary BETWEEN 50000 AND 100000;

-- Date range
SELECT * FROM employees 
WHERE hire_date BETWEEN '2020-01-01' AND '2023-12-31';
```

### Aliases
```sql
-- Column aliases
SELECT 
    name AS employee_name,
    salary AS monthly_salary,
    salary * 12 AS annual_salary,
    department_id AS dept
FROM employees;

-- Table aliases
SELECT e.name, d.department_name
FROM employees e
JOIN departments d ON e.department_id = d.id;
```

---

############################################################
## Joins
############################################################

### Types of Joins

#### INNER JOIN
Returns only matching records from both tables.

```sql
SELECT e.name, e.salary, d.department_name
FROM employees e
INNER JOIN departments d ON e.department_id = d.id;
```

#### LEFT JOIN (LEFT OUTER JOIN)
Returns all records from left table and matching records from right table.

```sql
-- All employees, with department info if available
SELECT e.name, e.salary, d.department_name
FROM employees e
LEFT JOIN departments d ON e.department_id = d.id;
```

#### RIGHT JOIN (RIGHT OUTER JOIN)
Returns all records from right table and matching records from left table.

```sql
-- All departments, with employee info if available
SELECT e.name, e.salary, d.department_name
FROM employees e
RIGHT JOIN departments d ON e.department_id = d.id;
```

#### FULL OUTER JOIN
Returns all records when there's a match in either table.

```sql
-- All employees and departments
SELECT e.name, e.salary, d.department_name
FROM employees e
FULL OUTER JOIN departments d ON e.department_id = d.id;
```

#### SELF JOIN
Joins a table with itself.

```sql
-- Find employees and their managers
SELECT 
    e.name AS employee_name,
    m.name AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;

-- Find employees in the same department
SELECT 
    e1.name AS employee1,
    e2.name AS employee2,
    e1.department_id
FROM employees e1
JOIN employees e2 ON e1.department_id = e2.department_id
WHERE e1.id < e2.id;  -- Avoid duplicates
```

#### CROSS JOIN
Cartesian product of two tables.

```sql
-- All possible employee-project combinations
SELECT e.name, p.project_name
FROM employees e
CROSS JOIN projects p;
```

### Complex Join Examples

```sql
-- Multiple joins
SELECT 
    e.name,
    d.department_name,
    p.project_name,
    ep.role
FROM employees e
JOIN departments d ON e.department_id = d.id
JOIN employee_projects ep ON e.id = ep.employee_id
JOIN projects p ON ep.project_id = p.id
WHERE p.status = 'ACTIVE';

-- Join with aggregation
SELECT 
    d.department_name,
    COUNT(e.id) as employee_count,
    AVG(e.salary) as avg_salary
FROM departments d
LEFT JOIN employees e ON d.id = e.department_id
GROUP BY d.id, d.department_name
ORDER BY employee_count DESC;
```

---

############################################################
## Functions and Stored Procedures
############################################################

### Functions
Functions return a single value and can be used in expressions.

#### Built-in Functions
```sql
-- Date functions
SELECT NOW(), CURDATE(), YEAR(hire_date), MONTH(hire_date)
FROM employees;

-- String functions
SELECT CONCAT(UPPER(SUBSTRING(name, 1, 1)), LOWER(SUBSTRING(name, 2))) as proper_name
FROM employees;

-- Math functions
SELECT ROUND(salary/12, 2) as monthly_salary,
       CEILING(salary/1000) as salary_thousands
FROM employees;
```

#### User-Defined Functions
```sql
-- Create a function to calculate annual salary
DELIMITER //
CREATE FUNCTION CalculateAnnualSalary(monthly_salary DECIMAL(10,2))
RETURNS DECIMAL(12,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    RETURN monthly_salary * 12;
END //
DELIMITER ;

-- Use the function
SELECT name, salary, CalculateAnnualSalary(salary) as annual_salary
FROM employees;
```

### Stored Procedures
Procedures can perform complex operations and don't necessarily return values.

```sql
-- Create a stored procedure
DELIMITER //
CREATE PROCEDURE GetEmployeesByDepartment(
    IN dept_id INT,
    IN min_salary DECIMAL(10,2),
    OUT total_count INT
)
BEGIN
    DECLARE exit handler for sqlexception
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    SELECT * FROM employees 
    WHERE department_id = dept_id 
      AND salary >= min_salary
    ORDER BY salary DESC;
    
    SELECT COUNT(*) INTO total_count
    FROM employees 
    WHERE department_id = dept_id 
      AND salary >= min_salary;
    
    COMMIT;
END //
DELIMITER ;

-- Call the procedure
CALL GetEmployeesByDepartment(1, 50000, @count);
SELECT @count as total_employees;
```

#### Advanced Stored Procedure Example
```sql
DELIMITER //
CREATE PROCEDURE ProcessSalaryIncrease(
    IN dept_id INT,
    IN increase_percentage DECIMAL(5,2),
    OUT affected_rows INT,
    OUT total_increase DECIMAL(15,2)
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE emp_id INT;
    DECLARE old_salary DECIMAL(10,2);
    DECLARE new_salary DECIMAL(10,2);
    DECLARE emp_cursor CURSOR FOR 
        SELECT id, salary FROM employees WHERE department_id = dept_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET affected_rows = 0;
    SET total_increase = 0;
    
    START TRANSACTION;
    
    OPEN emp_cursor;
    read_loop: LOOP
        FETCH emp_cursor INTO emp_id, old_salary;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SET new_salary = old_salary * (1 + increase_percentage / 100);
        
        UPDATE employees 
        SET salary = new_salary 
        WHERE id = emp_id;
        
        SET affected_rows = affected_rows + 1;
        SET total_increase = total_increase + (new_salary - old_salary);
    END LOOP;
    
    CLOSE emp_cursor;
    COMMIT;
END //
DELIMITER ;
```

### Functions vs Stored Procedures

| Aspect | Functions | Stored Procedures |
|--------|-----------|-------------------|
| Return Value | Must return a value | Optional return values |
| Usage | In SELECT statements | Called independently |
| Parameters | Input only | Input, Output, InOut |
| Transactions | Limited transaction control | Full transaction control |
| Side Effects | Should be side-effect free | Can modify database state |

---

############################################################
## Execution Plans
############################################################

An **execution plan** shows how the database engine executes a query, including the order of operations and methods used.

### Why Use Execution Plans?
- **Performance Optimization**: Identify bottlenecks
- **Index Usage**: See if indexes are being used effectively
- **Query Tuning**: Understand query behavior
- **Cost Analysis**: Compare different query approaches

### How to Generate Execution Plans

#### MySQL
```sql
-- Show execution plan
EXPLAIN SELECT e.name, d.department_name
FROM employees e
JOIN departments d ON e.department_id = d.id
WHERE e.salary > 50000;

-- Detailed execution plan
EXPLAIN FORMAT=JSON SELECT * FROM employees WHERE salary > 50000;

-- Analyze actual execution
EXPLAIN ANALYZE SELECT * FROM employees WHERE salary > 50000;
```

#### PostgreSQL
```sql
-- Basic execution plan
EXPLAIN SELECT * FROM employees WHERE salary > 50000;

-- Detailed plan with costs
EXPLAIN (ANALYZE, BUFFERS) SELECT * FROM employees WHERE salary > 50000;

-- Visual execution plan
EXPLAIN (FORMAT JSON) SELECT * FROM employees WHERE salary > 50000;
```

#### Oracle
```sql
-- Generate execution plan
EXPLAIN PLAN FOR
SELECT * FROM employees WHERE salary > 50000;

-- Display the plan
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

-- Real-time execution statistics
SELECT /*+ GATHER_PLAN_STATISTICS */ * FROM employees WHERE salary > 50000;
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));
```

### Reading Execution Plans

#### Key Metrics to Look For:
- **Cost**: Estimated resource usage
- **Rows**: Estimated number of rows processed
- **Time**: Execution time (in ANALYZE mode)
- **Operation Type**: Scan, Join, Sort, etc.

#### Common Operations:
```sql
-- Table Scan vs Index Scan
EXPLAIN SELECT * FROM employees WHERE id = 1;        -- Index scan (good)
EXPLAIN SELECT * FROM employees WHERE name LIKE '%John%'; -- Table scan (potentially slow)

-- Join algorithms
EXPLAIN SELECT e.name, d.name 
FROM employees e 
JOIN departments d ON e.department_id = d.id;
-- Possible joins: Nested Loop, Hash Join, Merge Join
```

### Optimization Examples

#### Before Optimization:
```sql
-- Slow query without index
SELECT * FROM employees WHERE email = 'john@company.com';
-- Execution plan shows table scan

-- Query with function in WHERE clause
SELECT * FROM employees WHERE YEAR(hire_date) = 2023;
-- Cannot use index on hire_date
```

#### After Optimization:
```sql
-- Add index
CREATE INDEX idx_employees_email ON employees(email);

-- Rewrite query to be index-friendly
SELECT * FROM employees 
WHERE hire_date >= '2023-01-01' AND hire_date < '2024-01-01';
```

### Advanced Execution Plan Analysis

#### Query with Subquery Optimization:
```sql
-- Original query (potentially slow)
EXPLAIN SELECT * FROM employees 
WHERE department_id IN (
    SELECT id FROM departments WHERE name LIKE '%Engineering%'
);

-- Optimized with JOIN
EXPLAIN SELECT DISTINCT e.* FROM employees e
JOIN departments d ON e.department_id = d.id
WHERE d.name LIKE '%Engineering%';
```

#### Analyzing Complex Queries:
```sql
-- Complex query with multiple joins and aggregations
EXPLAIN ANALYZE
SELECT 
    d.department_name,
    COUNT(e.id) as employee_count,
    AVG(e.salary) as avg_salary,
    SUM(CASE WHEN p.status = 'ACTIVE' THEN 1 ELSE 0 END) as active_projects
FROM departments d
LEFT JOIN employees e ON d.id = e.department_id
LEFT JOIN employee_projects ep ON e.id = ep.employee_id
LEFT JOIN projects p ON ep.project_id = p.id
GROUP BY d.id, d.department_name
HAVING COUNT(e.id) > 5
ORDER BY avg_salary DESC;
```

### When to Use Execution Plans:
- ✅ Query performance issues
- ✅ Before adding indexes
- ✅ Comparing query alternatives
- ✅ Database tuning
- ✅ Code reviews for complex queries

---

############################################################
## Senior Java FSD Interview Questions
############################################################

1. **Find the second highest salary from the employee table.**
   ```sql
   SELECT MAX(salary) AS second_highest_salary
   FROM employees
   WHERE salary < (SELECT MAX(salary) FROM employees);
   ```

2. **Get the count of employees in each department.**
   ```sql
   SELECT department_id, COUNT(*) AS employee_count
   FROM employees
   GROUP BY department_id;
   ```

3. **Retrieve employees who have not been assigned to any project.**
   ```sql
   SELECT e.*
   FROM employees e
   LEFT JOIN employee_projects ep ON e.id = ep.employee_id
   WHERE ep.project_id IS NULL;
   ```

4. **Find duplicate email addresses in the users table.**
   ```sql
   SELECT email, COUNT(*) AS count
   FROM users
   GROUP BY email
   HAVING COUNT(*) > 1;
   ```

5. **List departments with more than 5 employees and average salary above 60,000.**
   ```sql
   SELECT department_id, COUNT(*) AS emp_count, AVG(salary) AS avg_salary
   FROM employees
   GROUP BY department_id
   HAVING COUNT(*) > 5 AND AVG(salary) > 60000;
   ```

6. **Get employees who joined in the last 30 days.**
   ```sql
   SELECT *
   FROM employees
   WHERE hire_date >= CURDATE() - INTERVAL 30 DAY;
   ```

7. **Write a query to swap values between two columns.**
   ```sql
   UPDATE employees
   SET column1 = column2,
       column2 = column1;
   ```

8. **Find all managers (employees who are referenced as manager_id by others).**
   ```sql
   SELECT DISTINCT m.*
   FROM employees e
   JOIN employees m ON e.manager_id = m.id;
   ```

9. **Get the highest paid employee in each department.**
   ```sql
   SELECT e.*
   FROM employees e
   INNER JOIN (
       SELECT department_id, MAX(salary) AS max_salary
       FROM employees
       GROUP BY department_id
   ) d ON e.department_id = d.department_id AND e.salary = d.max_salary;
   ```

10. **Show employees who have the same salary.**
    ```sql
    SELECT salary, COUNT(*) AS count
    FROM employees
    GROUP BY salary
    HAVING COUNT(*) > 1;
    ```
