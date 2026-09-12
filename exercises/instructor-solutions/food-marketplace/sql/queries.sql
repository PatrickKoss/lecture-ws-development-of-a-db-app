PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Restaurants sind aktiv?
-- erwartet: 9 Zeilen
SELECT id, partner_number, name, city, commission_rate
FROM restaurants
WHERE active = 1
ORDER BY partner_number;

-- Abfrage 2: Welche noch nicht abgeschlossenen Bestellungen wurden vor dem 2026-03-01 aufgegeben?
-- erwartet: 3 Zeilen
SELECT id, order_number, ordered_at, status
FROM orders
WHERE status NOT IN ('DELIVERED', 'CANCELLED')
  AND NOT (order_type = 'PICKUP' AND status = 'PICKED_UP')
  AND ordered_at < '2026-03-01 00:00:00'
ORDER BY ordered_at, id;

-- Abfrage 3: Welche Gerichte bieten Restaurants aus Essen an?
-- erwartet: 3 Zeilen
SELECT d.id, d.name, r.partner_number, r.name AS restaurant_name
FROM dishes AS d
INNER JOIN restaurants AS r ON r.id = d.restaurant_id
WHERE r.city = 'Essen'
ORDER BY r.name, d.name;

-- Abfrage 4: Welche Lieferbestellungen gibt es und welcher Kurier ist zugeordnet?
-- erwartet: 13 Zeilen
SELECT
    o.order_number,
    c.first_name,
    c.last_name,
    r.name AS restaurant_name,
    k.first_name AS courier_first_name,
    k.last_name AS courier_last_name,
    o.delivered_at
FROM orders AS o
INNER JOIN customers AS c ON c.id = o.customer_id
INNER JOIN restaurants AS r ON r.id = o.restaurant_id
LEFT JOIN couriers AS k ON k.id = o.courier_id
WHERE o.order_type = 'DELIVERY'
ORDER BY o.ordered_at, o.id;

-- Abfrage 5: Für welche Bestellungen wurde noch nie eine Bewertung erfasst?
-- erwartet: 5 Zeilen
SELECT o.id, o.order_number, o.ordered_at
FROM orders AS o
LEFT JOIN reviews AS rv ON rv.order_id = o.id
WHERE rv.id IS NULL
ORDER BY o.ordered_at, o.id;

-- Abfrage 6: Wie viele Bestellungen hat jeder Kunde?
-- erwartet: 12 Zeilen
SELECT
    c.customer_number,
    c.first_name,
    c.last_name,
    COUNT(o.id) AS order_count
FROM customers AS c
LEFT JOIN orders AS o ON o.customer_id = c.id
GROUP BY c.id, c.customer_number, c.first_name, c.last_name
ORDER BY order_count DESC, c.customer_number;

-- Abfrage 7: Welche Restaurants kommen auf mehr als zwei bestellte Positionen?
-- erwartet: 3 Zeilen
SELECT
    r.id,
    r.partner_number,
    r.name,
    COUNT(oi.order_id) AS item_count
FROM restaurants AS r
INNER JOIN orders AS o ON o.restaurant_id = r.id
INNER JOIN order_items AS oi ON oi.order_id = o.id
GROUP BY r.id, r.partner_number, r.name
HAVING COUNT(oi.order_id) > 2
ORDER BY item_count DESC, r.partner_number;

-- Abfrage 8: Welche Restaurants liegen mit ihrem Provisionsbetrag über dem Durchschnitt aller Restaurants?
-- erwartet: 6 Zeilen
SELECT
    r.id,
    r.partner_number,
    r.name,
    ROUND(SUM(oi.quantity * oi.unit_price) * r.commission_rate / 100, 2) AS commission_amount
FROM restaurants AS r
INNER JOIN orders AS o ON o.restaurant_id = r.id
INNER JOIN order_items AS oi ON oi.order_id = o.id
WHERE o.status <> 'CANCELLED'
GROUP BY r.id, r.partner_number, r.name, r.commission_rate
HAVING SUM(oi.quantity * oi.unit_price) * r.commission_rate / 100 > (
    SELECT AVG(commission_amount)
    FROM (
        SELECT
            SUM(oi2.quantity * oi2.unit_price) * r2.commission_rate / 100 AS commission_amount
        FROM restaurants AS r2
        INNER JOIN orders AS o2 ON o2.restaurant_id = r2.id
        INNER JOIN order_items AS oi2 ON oi2.order_id = o2.id
        WHERE o2.status <> 'CANCELLED'
        GROUP BY r2.id, r2.commission_rate
    ) AS commissions
)
ORDER BY commission_amount DESC, r.partner_number;
