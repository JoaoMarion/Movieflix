ALTER TABLE users
ADD COLUMN verification_code INT;

ALTER TABLE users
ADD COLUMN verification_code_expire_at TIMESTAMP;