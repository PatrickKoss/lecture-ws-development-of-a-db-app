DROP INDEX IF EXISTS idx_music_courses_fee;
EXPLAIN QUERY PLAN SELECT id FROM music_courses WHERE fee <= 120;
CREATE INDEX idx_music_courses_fee ON music_courses(fee);
EXPLAIN QUERY PLAN SELECT id FROM music_courses WHERE fee <= 120;
-- Vorher: SCAN music_courses. Nachher: SEARCH music_courses USING COVERING INDEX idx_music_courses_fee.
