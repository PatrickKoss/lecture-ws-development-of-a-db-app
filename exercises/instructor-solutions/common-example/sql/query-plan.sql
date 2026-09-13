DROP INDEX IF EXISTS idx_students_enrollment_date;
EXPLAIN QUERY PLAN
SELECT id FROM students WHERE enrollment_date >= '2025-04-01';
CREATE INDEX idx_students_enrollment_date ON students(enrollment_date);
EXPLAIN QUERY PLAN
SELECT id FROM students WHERE enrollment_date >= '2025-04-01';
-- Vorher liest SQLite die Tabelle. Danach verwendet es idx_students_enrollment_date.
