-- Database schema fix for server deployment
-- Run these commands on your server database

-- Fix Electric Bills table
ALTER TABLE electric_bills ADD COLUMN IF NOT EXISTS payment_date DATE;

-- Fix Internet Bills table  
ALTER TABLE internet_bills ADD COLUMN IF NOT EXISTS payment_date DATE;

-- Fix SIM Bills table
ALTER TABLE sim_bills ADD COLUMN IF NOT EXISTS payment_date DATE;

-- Check and fix Travel Expenses table if needed
-- DESCRIBE travel_expenses;
-- ALTER TABLE travel_expenses ADD COLUMN IF NOT EXISTS payment_date DATE;
-- ALTER TABLE travel_expenses ADD COLUMN IF NOT EXISTS payment_mode VARCHAR(255);