-- Fix for internet_bills table schema
-- Run this SQL on your server database

-- Check current table structure
DESCRIBE internet_bills;

-- Add missing columns if they don't exist
ALTER TABLE internet_bills ADD COLUMN IF NOT EXISTS account_no VARCHAR(255);
ALTER TABLE internet_bills ADD COLUMN IF NOT EXISTS payment_date DATE;
ALTER TABLE internet_bills ADD COLUMN IF NOT EXISTS payment_mode VARCHAR(255);
ALTER TABLE internet_bills ADD COLUMN IF NOT EXISTS document_path VARCHAR(500);

-- Update column names if needed
-- ALTER TABLE internet_bills CHANGE accountNo account_no VARCHAR(255);
-- ALTER TABLE internet_bills CHANGE paymentDate payment_date DATE;
-- ALTER TABLE internet_bills CHANGE paymentMode payment_mode VARCHAR(255);
-- ALTER TABLE internet_bills CHANGE documentPath document_path VARCHAR(500);