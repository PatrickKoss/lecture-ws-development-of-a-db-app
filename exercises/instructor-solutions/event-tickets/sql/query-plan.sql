DROP INDEX IF EXISTS idx_venues_capacity;
EXPLAIN QUERY PLAN SELECT id FROM venues WHERE capacity >= 1000;
CREATE INDEX idx_venues_capacity ON venues(capacity);
EXPLAIN QUERY PLAN SELECT id FROM venues WHERE capacity >= 1000;
-- Vorher: SCAN venues. Nachher: SEARCH venues USING COVERING INDEX idx_venues_capacity.
