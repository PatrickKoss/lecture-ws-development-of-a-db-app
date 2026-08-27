PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Ersatzteile müssen nachbestellt werden?
-- erwartet: 6 Zeilen
SELECT id, part_number, name, stock_quantity, reorder_level
FROM parts
WHERE stock_quantity <= reorder_level
ORDER BY part_number;

-- Abfrage 2: Welche offenen Arbeitsaufträge gingen vor dem 2026-03-01 ein?
-- erwartet: 2 Zeilen
SELECT id, order_number, vehicle_id, opened_on, status
FROM work_orders
WHERE status IN ('OPEN', 'IN_PROGRESS')
  AND opened_on < '2026-03-01'
ORDER BY opened_on, id;

-- Abfrage 3: Welche Arbeitsaufträge gehören zu Fahrzeugen von Volkswagen?
-- erwartet: 4 Zeilen
SELECT wo.order_number, v.licence_plate, v.model
FROM work_orders AS wo
INNER JOIN vehicles AS v ON v.id = wo.vehicle_id
WHERE v.manufacturer = 'Volkswagen'
ORDER BY wo.opened_on, wo.id;

-- Abfrage 4: Welche Arbeitsaufträge sind noch offen?
-- erwartet: 4 Zeilen
SELECT
    wo.order_number,
    c.customer_number,
    c.first_name,
    c.last_name,
    v.licence_plate,
    v.model,
    wo.opened_on
FROM work_orders AS wo
INNER JOIN vehicles AS v ON v.id = wo.vehicle_id
INNER JOIN customers AS c ON c.id = v.owner_id
WHERE wo.status IN ('OPEN', 'IN_PROGRESS')
ORDER BY wo.opened_on, wo.id;

-- Abfrage 5: Welche Ersatzteile wurden noch nie in einem Auftrag verbaut?
-- erwartet: 2 Zeilen
SELECT p.id, p.part_number, p.name
FROM parts AS p
LEFT JOIN work_order_parts AS wop ON wop.part_id = p.id
WHERE wop.work_order_id IS NULL
ORDER BY p.part_number;

-- Abfrage 6: Wie viele Arbeitsaufträge hat jedes Fahrzeug?
-- erwartet: 12 Zeilen
SELECT
    v.id,
    v.licence_plate,
    v.manufacturer,
    v.model,
    COUNT(wo.id) AS work_order_count
FROM vehicles AS v
LEFT JOIN work_orders AS wo ON wo.vehicle_id = v.id
GROUP BY v.id, v.licence_plate, v.manufacturer, v.model
ORDER BY work_order_count DESC, v.licence_plate;

-- Abfrage 7: Welche Ersatzteile wurden in mehr als zwei Aufträgen verbaut?
-- erwartet: 2 Zeilen
SELECT
    p.id,
    p.part_number,
    p.name,
    COUNT(DISTINCT wop.work_order_id) AS work_order_count
FROM parts AS p
INNER JOIN work_order_parts AS wop ON wop.part_id = p.id
GROUP BY p.id, p.part_number, p.name
HAVING COUNT(DISTINCT wop.work_order_id) > 2
ORDER BY work_order_count DESC, p.part_number;

-- Abfrage 8: Welche Kunden haben mehr Arbeitsaufträge als der Durchschnitt?
-- erwartet: 3 Zeilen
SELECT
    c.id,
    c.customer_number,
    c.first_name,
    c.last_name,
    COUNT(wo.id) AS work_order_count
FROM customers AS c
INNER JOIN vehicles AS v ON v.owner_id = c.id
INNER JOIN work_orders AS wo ON wo.vehicle_id = v.id
GROUP BY c.id, c.customer_number, c.first_name, c.last_name
HAVING COUNT(wo.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM customers)
    FROM work_orders
)
ORDER BY work_order_count DESC, c.customer_number;
