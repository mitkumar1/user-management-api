INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (name, username, email, password) VALUES ('Test User', 'testuser', 'testuser@example.com', 'password');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
