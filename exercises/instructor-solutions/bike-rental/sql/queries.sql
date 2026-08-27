PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Räder sind verfügbar?
-- erwartet: 10 Zeilen
SELECT id, bike_number, current_station_id
FROM bikes
WHERE status = 'AVAILABLE'
ORDER BY bike_number;

-- Abfrage 2: Welche Ausleihen begannen am oder nach dem 2026-03-01?
-- erwartet: 5 Zeilen
SELECT id, rental_number, customer_id, bike_id, start_time
FROM rentals
WHERE start_time >= '2026-03-01T00:00:00'
ORDER BY start_time, id;

-- Abfrage 3: Welche Räder stehen an der Station Domplatz?
-- erwartet: 2 Zeilen
SELECT b.bike_number, bm.manufacturer, bm.model_name
FROM bikes AS b
INNER JOIN bike_models AS bm ON bm.id = b.model_id
INNER JOIN stations AS s ON s.id = b.current_station_id
WHERE s.station_code = 'DOM'
ORDER BY b.bike_number;

-- Abfrage 4: Welche Ausleihen sind noch offen?
-- erwartet: 3 Zeilen
SELECT
    r.id,
    c.customer_number,
    c.first_name,
    c.last_name,
    b.bike_number,
    s.name AS start_station,
    r.start_time
FROM rentals AS r
INNER JOIN customers AS c ON c.id = r.customer_id
INNER JOIN bikes AS b ON b.id = r.bike_id
INNER JOIN stations AS s ON s.id = r.start_station_id
WHERE r.end_time IS NULL
ORDER BY r.start_time, r.id;

-- Abfrage 5: Welche aktiven Stationen haben aktuell kein verfügbares Rad?
-- erwartet: 1 Zeilen
SELECT s.id, s.station_code, s.name
FROM stations AS s
LEFT JOIN bikes AS b
    ON b.current_station_id = s.id
   AND b.status = 'AVAILABLE'
WHERE s.status = 'ACTIVE'
  AND b.id IS NULL
ORDER BY s.name;

-- Abfrage 6: Wie viele Ausleihen hat jeder Kunde?
-- erwartet: 12 Zeilen
SELECT
    c.customer_number,
    c.first_name,
    c.last_name,
    COUNT(r.id) AS rental_count
FROM customers AS c
LEFT JOIN rentals AS r ON r.customer_id = c.id
GROUP BY c.id, c.customer_number, c.first_name, c.last_name
ORDER BY rental_count DESC, c.customer_number;

-- Abfrage 7: Welche Radmodelle kommen über ihre Räder auf mehr als zwei Ausleihen?
-- erwartet: 2 Zeilen
SELECT
    bm.id,
    bm.manufacturer,
    bm.model_name,
    COUNT(r.id) AS rental_count
FROM bike_models AS bm
INNER JOIN bikes AS b ON b.model_id = bm.id
INNER JOIN rentals AS r ON r.bike_id = b.id
GROUP BY bm.id, bm.manufacturer, bm.model_name
HAVING COUNT(r.id) > 2
ORDER BY rental_count DESC, bm.model_name;

-- Abfrage 8: Welche Räder haben mehr Wartungseinträge als der Durchschnitt?
-- erwartet: 5 Zeilen
SELECT
    b.id,
    b.bike_number,
    COUNT(ml.sequence_number) AS maintenance_count
FROM bikes AS b
INNER JOIN maintenance_logs AS ml ON ml.bike_id = b.id
GROUP BY b.id, b.bike_number
HAVING COUNT(ml.sequence_number) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM bikes)
    FROM maintenance_logs
)
ORDER BY maintenance_count DESC, b.bike_number;
