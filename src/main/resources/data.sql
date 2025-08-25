
INSERT INTO roles (name)
SELECT 'ROLE_USER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER');
INSERT INTO roles (name)
SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');


INSERT INTO users (name, username, email, password)
SELECT 'Test User', 'testuser', 'testuser@example.com', 'password'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'testuser');


INSERT INTO users_roles (user_id, role_id)
SELECT 1, 1 WHERE NOT EXISTS (SELECT 1 FROM users_roles WHERE user_id = 1 AND role_id = 1);
