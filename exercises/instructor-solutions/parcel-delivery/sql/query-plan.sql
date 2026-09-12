DROP INDEX IF EXISTS idx_parcels_weight;
EXPLAIN QUERY PLAN SELECT id FROM parcels WHERE weight > 4;
CREATE INDEX idx_parcels_weight ON parcels(weight);
EXPLAIN QUERY PLAN SELECT id FROM parcels WHERE weight > 4;
-- Vorher: SCAN parcels. Nachher: SEARCH parcels USING COVERING INDEX idx_parcels_weight.
