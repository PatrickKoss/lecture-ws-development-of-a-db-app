DROP INDEX IF EXISTS idx_pizzas_active;
EXPLAIN QUERY PLAN SELECT id FROM pizzas WHERE active = 1;
CREATE INDEX idx_pizzas_active ON pizzas(active);
EXPLAIN QUERY PLAN SELECT id FROM pizzas WHERE active = 1;
-- Vorher: SCAN pizzas. Nachher: SEARCH pizzas USING COVERING INDEX idx_pizzas_active.
