-- Guidely Exhibition Service Database Initialization
-- This script will be executed when the MySQL container starts for the first time

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS guidely_exhibition_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE guidely_exhibition_db;

-- Create tables (Spring Boot JPA will handle this automatically, but you can add custom initialization here)
-- The tables will be created by Hibernate with ddl-auto: update

-- Optional: Insert some sample data
-- INSERT INTO exhibitions (title, description, start_date, end_date, location, status, image_url, created_at, updated_at) 
-- VALUES ('샘플 전시회', '샘플 전시회 설명', '2024-01-01 10:00:00', '2024-12-31 18:00:00', '샘플 위치', 'ACTIVE', 'https://example.com/sample.jpg', NOW(), NOW()); 