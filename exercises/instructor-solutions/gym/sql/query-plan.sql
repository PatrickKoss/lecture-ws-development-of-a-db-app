DROP INDEX IF EXISTS idx_courses_level;
EXPLAIN QUERY PLAN SELECT id FROM courses WHERE level = 'BEGINNER';
CREATE INDEX idx_courses_level ON courses(level);
EXPLAIN QUERY PLAN SELECT id FROM courses WHERE level = 'BEGINNER';
-- Vorher: SCAN courses. Nachher: SEARCH courses USING COVERING INDEX idx_courses_level.
