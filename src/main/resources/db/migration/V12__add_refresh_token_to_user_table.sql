ALTER TABLE users
ADD COLUMN refresh_token VARCHAR(255),
ADD COLUMN refresh_token_expire_at TIMESTAMP;