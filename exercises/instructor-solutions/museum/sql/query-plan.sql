DROP INDEX IF EXISTS idx_exhibits_insured_value;
EXPLAIN QUERY PLAN SELECT id FROM exhibits WHERE insured_value >= 10000;
CREATE INDEX idx_exhibits_insured_value ON exhibits(insured_value);
EXPLAIN QUERY PLAN SELECT id FROM exhibits WHERE insured_value >= 10000;
-- Vorher: SCAN exhibits. Nachher: SEARCH exhibits USING COVERING INDEX idx_exhibits_insured_value.
