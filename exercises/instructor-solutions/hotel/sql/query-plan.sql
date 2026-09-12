DROP INDEX IF EXISTS idx_room_types_capacity;
EXPLAIN QUERY PLAN SELECT id FROM room_types WHERE capacity >= 3;
CREATE INDEX idx_room_types_capacity ON room_types(capacity);
EXPLAIN QUERY PLAN SELECT id FROM room_types WHERE capacity >= 3;
-- Vorher: SCAN room_types. Nachher: SEARCH room_types USING COVERING INDEX idx_room_types_capacity.
