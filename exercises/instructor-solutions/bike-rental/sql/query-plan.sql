DROP INDEX IF EXISTS idx_stations_status;
EXPLAIN QUERY PLAN SELECT id FROM stations WHERE status = 'ACTIVE';
CREATE INDEX idx_stations_status ON stations(status);
EXPLAIN QUERY PLAN SELECT id FROM stations WHERE status = 'ACTIVE';
-- Vorher: SCAN stations. Nachher: SEARCH stations USING COVERING INDEX idx_stations_status.
