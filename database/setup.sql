-- MySQL Database Setup for JMeter Test Application
-- Run this script to create the database and user

-- Create database
CREATE DATABASE IF NOT EXISTS jmeter CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user (optional - you can use root user)
-- CREATE USER 'jmeter_user'@'localhost' IDENTIFIED BY 'jmeter_password';
-- GRANT ALL PRIVILEGES ON jmeter.* TO 'jmeter_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Use the database
USE jmeter;

-- The tables will be created automatically by Hibernate
-- when the application starts with ddl-auto: update

-- Optional: Create indexes for better performance
-- These will be created automatically by the @Index annotations in entities

-- Verify database creation
SHOW DATABASES LIKE 'jmeter';
