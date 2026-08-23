PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Pizzen sind aktiv?
-- erwartet: 10 Zeilen
SELECT id, pizza_number, name, base_price
FROM pizzas
WHERE active = 1
ORDER BY name;

-- Abfrage 2: Welche offenen Bestellungen gingen vor dem 2026-03-01 ein?
-- erwartet: 4 Zeilen
SELECT id, order_number, customer_id, ordered_on, status
FROM orders
WHERE status NOT IN ('COMPLETED', 'CANCELLED')
  AND ordered_on < '2026-03-01'
ORDER BY ordered_on, id;

-- Abfrage 3: An welche Adressen gehen die Lieferbestellungen?
-- erwartet: 12 Zeilen
SELECT
    o.order_number,
    c.first_name,
    c.last_name,
    a.street,
    a.house_number,
    a.postal_code
FROM orders AS o
INNER JOIN customers AS c ON c.id = o.customer_id
INNER JOIN addresses AS a
    ON a.id = o.delivery_address_id
   AND a.customer_id = o.customer_id
WHERE o.order_type = 'DELIVERY'
ORDER BY o.ordered_on, o.id;

-- Abfrage 4: Welchen Umsatz hat jede Bestellposition?
-- erwartet: 20 Zeilen
SELECT
    o.order_number,
    oi.position_number,
    p.name AS pizza_name,
    oi.quantity,
    oi.unit_price,
    oi.quantity * oi.unit_price AS item_revenue
FROM order_items AS oi
INNER JOIN orders AS o ON o.id = oi.order_id
INNER JOIN pizzas AS p ON p.id = oi.pizza_id
ORDER BY o.id, oi.position_number;

-- Abfrage 5: Welche Bestellungen haben keinen Fahrer?
-- erwartet: 6 Zeilen
SELECT o.id, o.order_number, o.order_type, o.status
FROM orders AS o
LEFT JOIN drivers AS d ON d.id = o.driver_id
WHERE d.id IS NULL
ORDER BY o.ordered_on, o.id;

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

-- Abfrage 7: Von welchen Pizzen wurden insgesamt mehr als drei Stück bestellt?
-- erwartet: 3 Zeilen
SELECT
    p.id,
    p.pizza_number,
    p.name,
    SUM(oi.quantity) AS sold_quantity
FROM pizzas AS p
INNER JOIN order_items AS oi ON oi.pizza_id = p.id
GROUP BY p.id, p.pizza_number, p.name
HAVING SUM(oi.quantity) > 3
ORDER BY sold_quantity DESC, p.name;

-- Abfrage 8: Welche Pizzen erzielen mehr als den durchschnittlichen Pizzaumsatz?
-- erwartet: 5 Zeilen
SELECT
    p.id,
    p.pizza_number,
    p.name,
    SUM(oi.quantity * oi.unit_price) AS pizza_revenue
FROM pizzas AS p
INNER JOIN order_items AS oi ON oi.pizza_id = p.id
GROUP BY p.id, p.pizza_number, p.name
HAVING SUM(oi.quantity * oi.unit_price) > (
    SELECT AVG(revenue_by_pizza.pizza_revenue)
    FROM (
        SELECT
            p2.id,
            COALESCE(SUM(oi2.quantity * oi2.unit_price), 0) AS pizza_revenue
        FROM pizzas AS p2
        LEFT JOIN order_items AS oi2 ON oi2.pizza_id = p2.id
        GROUP BY p2.id
    ) AS revenue_by_pizza
)
ORDER BY pizza_revenue DESC, p.name;
