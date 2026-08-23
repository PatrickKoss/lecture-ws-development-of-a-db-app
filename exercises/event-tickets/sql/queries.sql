PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Veranstaltungen finden nach dem 2026-06-01 statt?
-- erwartet: 7 Zeilen
SELECT event_number, title, event_on, starts_at
FROM events
WHERE event_on > '2026-06-01'
ORDER BY event_on, starts_at;

-- Abfrage 2: Welche Veranstaltungen finden in der Zeche Carl statt?
-- erwartet: 4 Zeilen
SELECT e.event_number, e.title, e.event_on, e.doors_open
FROM events AS e
INNER JOIN venues AS v ON v.id = e.venue_id
WHERE v.name = 'Zeche Carl'
ORDER BY e.event_on, e.starts_at;

-- Abfrage 3: Welche Bestellungen sind bezahlt?
-- erwartet: 10 Zeilen
SELECT o.order_number, b.buyer_number, b.first_name, b.last_name
FROM orders AS o
INNER JOIN buyers AS b ON b.id = o.buyer_id
WHERE o.status = 'PAID'
ORDER BY o.ordered_at, o.id;

-- Abfrage 4: Welche Tickets wurden verkauft?
-- erwartet: 18 Zeilen
SELECT
    t.id,
    e.title,
    t.category_name,
    o.order_number,
    b.first_name,
    b.last_name,
    t.price_paid
FROM tickets AS t
INNER JOIN events AS e ON e.id = t.event_id
INNER JOIN orders AS o ON o.id = t.order_id
INNER JOIN buyers AS b ON b.id = o.buyer_id
ORDER BY e.event_on, o.order_number, t.ticket_number;

-- Abfrage 5: Bei welchen Veranstaltungen wurde noch kein Ticket eingecheckt?
-- erwartet: 8 Zeilen
SELECT e.event_number, e.title, e.event_on
FROM events AS e
LEFT JOIN tickets AS t
    ON t.event_id = e.id
   AND t.checked_in_at IS NOT NULL
WHERE t.id IS NULL
ORDER BY e.event_on;

-- Abfrage 6: Wie viele Tickets wurden je Kategorie verkauft und eingecheckt?
-- erwartet: 15 Zeilen
SELECT
    e.event_number,
    tc.name AS category_name,
    COUNT(t.id) AS sold_count,
    SUM(CASE WHEN t.checked_in_at IS NOT NULL THEN 1 ELSE 0 END) AS checked_in_count
FROM ticket_categories AS tc
INNER JOIN events AS e ON e.id = tc.event_id
LEFT JOIN tickets AS t
    ON t.event_id = tc.event_id
   AND t.category_name = tc.name
GROUP BY tc.event_id, e.event_number, tc.name
ORDER BY e.event_number, tc.name;

-- Abfrage 7: Für welche Kategorien wurden mehr als zwei Tickets verkauft?
-- erwartet: 2 Zeilen
SELECT
    e.event_number,
    tc.name AS category_name,
    COUNT(t.id) AS sold_count
FROM ticket_categories AS tc
INNER JOIN events AS e ON e.id = tc.event_id
INNER JOIN tickets AS t
    ON t.event_id = tc.event_id
   AND t.category_name = tc.name
GROUP BY tc.event_id, e.event_number, tc.name
HAVING COUNT(t.id) > 2
ORDER BY sold_count DESC, e.event_number;

-- Abfrage 8: Welche Käufer haben mehr Tickets als der Durchschnitt?
-- erwartet: 7 Zeilen
SELECT
    b.id,
    b.buyer_number,
    b.first_name,
    b.last_name,
    COUNT(t.id) AS ticket_count
FROM buyers AS b
INNER JOIN orders AS o ON o.buyer_id = b.id
INNER JOIN tickets AS t ON t.order_id = o.id
GROUP BY b.id, b.buyer_number, b.first_name, b.last_name
HAVING COUNT(t.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM buyers)
    FROM tickets
)
ORDER BY ticket_count DESC, b.buyer_number;
