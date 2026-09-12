DROP INDEX IF EXISTS idx_medications_active;
EXPLAIN QUERY PLAN SELECT id FROM medications WHERE active = 1;
CREATE INDEX idx_medications_active ON medications(active);
EXPLAIN QUERY PLAN SELECT id FROM medications WHERE active = 1;
-- Vorher: SCAN medications. Nachher: SEARCH medications USING COVERING INDEX idx_medications_active.
