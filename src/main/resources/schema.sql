-- Add missing payment_date column to rent table if it doesn't exist
ALTER TABLE rent ADD COLUMN payment_date DATE;
UPDATE rent SET payment_date = date WHERE payment_date IS NULL;
ALTER TABLE rent MODIFY COLUMN payment_date DATE NOT NULL;