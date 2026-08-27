PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Zimmer sind verfügbar?
-- erwartet: 9 Zeilen
SELECT floor, room_number, room_type_id, accessible
FROM rooms
WHERE status = 'AVAILABLE'
ORDER BY floor, room_number;

-- Abfrage 2: Welche bestätigten Buchungen reichen über den 2026-05-01?
-- erwartet: 1 Zeile
SELECT id, booking_number, guest_id, arrival_on, departure_on
FROM bookings
WHERE status = 'CONFIRMED'
  AND arrival_on < '2026-05-01'
  AND departure_on >= '2026-05-01'
ORDER BY arrival_on, id;

-- Abfrage 3: Welchen Typ und Standardpreis hat jedes Zimmer?
-- erwartet: 16 Zeilen
SELECT
    r.floor,
    r.room_number,
    rt.type_code,
    rt.name,
    rt.standard_price_cents
FROM rooms AS r
INNER JOIN room_types AS rt ON rt.id = r.room_type_id
ORDER BY r.floor, r.room_number;

-- Abfrage 4: Welche bestätigten oder eingecheckten Buchungen haben ein Zimmer?
-- erwartet: 6 Zeilen
SELECT
    b.booking_number,
    g.guest_number,
    g.first_name,
    g.last_name,
    br.floor,
    br.room_number,
    b.departure_on
FROM bookings AS b
INNER JOIN guests AS g ON g.id = b.guest_id
INNER JOIN booking_rooms AS br ON br.booking_id = b.id
WHERE b.status IN ('CONFIRMED', 'CHECKED_IN')
ORDER BY b.arrival_on, b.id, br.floor, br.room_number;

-- Abfrage 5: Welche Leistungen wurden noch nie zu einer Buchung erfasst?
-- erwartet: 2 Zeilen
SELECT s.id, s.service_code, s.name
FROM services AS s
LEFT JOIN booking_services AS bs ON bs.service_id = s.id
WHERE bs.booking_id IS NULL
ORDER BY s.name;

-- Abfrage 6: Wie viele nicht stornierte Zimmerbelegungen gibt es pro Zimmertyp?
-- erwartet: 10 Zeilen
SELECT
    rt.type_code,
    rt.name,
    COUNT(b.id) AS occupancy_count
FROM room_types AS rt
LEFT JOIN rooms AS r ON r.room_type_id = rt.id
LEFT JOIN booking_rooms AS br
    ON br.floor = r.floor
   AND br.room_number = r.room_number
LEFT JOIN bookings AS b
    ON b.id = br.booking_id
   AND b.status <> 'CANCELLED'
GROUP BY rt.id, rt.type_code, rt.name
ORDER BY occupancy_count DESC, rt.type_code;

-- Abfrage 7: Welche Leistungen erzielen aus nicht stornierten Buchungen mehr als 100 Euro Umsatz?
-- erwartet: 3 Zeilen
SELECT
    s.service_code,
    s.name,
    SUM(bs.quantity * bs.unit_price_cents) AS revenue_cents
FROM services AS s
INNER JOIN booking_services AS bs ON bs.service_id = s.id
INNER JOIN bookings AS b ON b.id = bs.booking_id
WHERE b.status <> 'CANCELLED'
GROUP BY s.id, s.service_code, s.name
HAVING SUM(bs.quantity * bs.unit_price_cents) > 10000
ORDER BY revenue_cents DESC, s.service_code;

-- Abfrage 8: Welche Gäste haben mehr Buchungen als der Durchschnitt?
-- erwartet: 2 Zeilen
SELECT
    g.id,
    g.guest_number,
    g.first_name,
    g.last_name,
    COUNT(b.id) AS booking_count
FROM guests AS g
INNER JOIN bookings AS b ON b.guest_id = g.id
GROUP BY g.id, g.guest_number, g.first_name, g.last_name
HAVING COUNT(b.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM guests)
    FROM bookings
)
ORDER BY booking_count DESC, g.guest_number;
