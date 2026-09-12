DROP INDEX IF EXISTS idx_work_orders_status_opened;
EXPLAIN QUERY PLAN SELECT id FROM work_orders WHERE status = 'OPEN' AND opened_on < '2026-03-01';
CREATE INDEX idx_work_orders_status_opened ON work_orders(status, opened_on);
EXPLAIN QUERY PLAN SELECT id FROM work_orders WHERE status = 'OPEN' AND opened_on < '2026-03-01';
-- Vorher: SCAN work_orders. Nachher: SEARCH work_orders USING COVERING INDEX idx_work_orders_status_opened.
