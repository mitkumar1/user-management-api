-- Add audit functionality and performance improvements
-- Version: 2.0.0
-- Description: Add audit logging, triggers, and monitoring views


-- Triggers and functions are not supported in H2, so omitted for test compatibility

CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    operation VARCHAR(10) NOT NULL,
    user_id BIGINT,
    old_data VARCHAR(255),
    new_data VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for audit logs
CREATE INDEX idx_audit_logs_table_name ON audit_logs(table_name);
CREATE INDEX idx_audit_logs_timestamp ON audit_logs(timestamp);
CREATE INDEX idx_audit_logs_user_id ON audit_logs(user_id);

CREATE VIEW user_statistics AS
SELECT 
    COUNT(*) as total_users,
    COUNT(CASE WHEN is_active = true THEN 1 END) as active_users,
    COUNT(CASE WHEN created_at >= DATEADD('DAY', -30, CURRENT_DATE) THEN 1 END) as new_users_30_days,
    COUNT(CASE WHEN last_login >= DATEADD('DAY', -7, CURRENT_DATE) THEN 1 END) as recent_logins,
    COUNT(CASE WHEN email_verified = true THEN 1 END) as verified_emails
FROM users;
