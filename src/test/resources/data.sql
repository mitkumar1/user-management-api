-- Test data for integration tests
-- This file can be used to insert test data for integration tests

-- Insert test roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Insert test users (passwords are encrypted versions of 'password123')
-- Password: password123 -> encrypted using BCrypt
INSERT INTO users (name, username, email, password) VALUES 
('Test User', 'testuser', 'test@example.com', '$2a$10$4eqIF5s/UwGMOuKbVVNzUuHCW0LuR7yt6sN7L9P.pGzwUWf.LHNHm'),
('Admin User', 'admin', 'admin@example.com', '$2a$10$4eqIF5s/UwGMOuKbVVNzUuHCW0LuR7yt6sN7L9P.pGzwUWf.LHNHm');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- testuser has ROLE_USER
(2, 1), -- admin has ROLE_USER  
(2, 2); -- admin has ROLE_ADMIN
