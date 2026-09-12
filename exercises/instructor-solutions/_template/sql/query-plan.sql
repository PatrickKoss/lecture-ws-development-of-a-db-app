DROP INDEX IF EXISTS idx_resources_measure;
EXPLAIN QUERY PLAN SELECT id FROM resources WHERE measure >= 20;
CREATE INDEX idx_resources_measure ON resources(measure);
EXPLAIN QUERY PLAN SELECT id FROM resources WHERE measure >= 20;
-- Vorher: SCAN resources. Nachher: SEARCH resources USING COVERING INDEX idx_resources_measure.
