-- Flyway migration: increase verification_token size and add verification_token_expiry column
-- Make sure Flyway is configured in the application (or run this SQL manually against your DB)

-- Increase length (safe operation)
ALTER TABLE users ALTER COLUMN verification_token TYPE VARCHAR(512);

-- Add expiry column if it doesn't exist (Postgres supports IF NOT EXISTS)
ALTER TABLE users ADD COLUMN IF NOT EXISTS verification_token_expiry TIMESTAMP;

