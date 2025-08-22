-- Database migration script to add missing payment_date column
-- Run this on your server database

-- Add payment_date column to electric_bills table
ALTER TABLE electric_bills ADD COLUMN payment_date DATE;

-- Add payment_date column to internet_bills table  
ALTER TABLE internet_bills ADD COLUMN payment_date DATE;

-- Add payment_date column to sim_bills table
ALTER TABLE sim_bills ADD COLUMN payment_date DATE;

-- Update existing records with a default date (adjust as needed)
UPDATE electric_bills SET payment_date = date WHERE payment_date IS NULL;
UPDATE internet_bills SET payment_date = date WHERE payment_date IS NULL;
UPDATE sim_bills SET payment_date = date WHERE payment_date IS NULL;