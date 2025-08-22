-- Fix for rent table - Add missing payment_date column
-- Run this SQL script in your MySQL database

USE store_management;

-- Add payment_date column if it doesn't exist
ALTER TABLE rent ADD COLUMN payment_date DATE;

-- Update existing records to have a default payment_date (same as date field)
UPDATE rent SET payment_date = date WHERE payment_date IS NULL;

-- Make the column NOT NULL after setting default values
ALTER TABLE rent MODIFY COLUMN payment_date DATE NOT NULL;