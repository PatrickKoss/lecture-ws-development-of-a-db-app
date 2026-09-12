DROP INDEX IF EXISTS idx_restaurants_active;
EXPLAIN QUERY PLAN SELECT id FROM restaurants WHERE active = 1;
CREATE INDEX idx_restaurants_active ON restaurants(active);
EXPLAIN QUERY PLAN SELECT id FROM restaurants WHERE active = 1;
-- Vorher: SCAN restaurants. Nachher: SEARCH restaurants USING COVERING INDEX idx_restaurants_active.
