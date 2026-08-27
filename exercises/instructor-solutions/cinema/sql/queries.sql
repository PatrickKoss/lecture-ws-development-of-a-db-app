PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Filme sind 2024 oder später erschienen?
-- erwartet: 9 Zeilen
SELECT id, movie_code, title, release_year
FROM movies
WHERE release_year >= 2024
ORDER BY title;

-- Abfrage 2: Welche Vorstellungen beginnen am oder nach dem 2026-04-15?
-- erwartet: 6 Zeilen
SELECT id, screening_code, movie_id, hall_id, starts_at
FROM screenings
WHERE starts_at >= '2026-04-15 00:00:00'
ORDER BY starts_at, id;

-- Abfrage 3: Welche Vorstellungen laufen im Saal Gloria?
-- erwartet: 4 Zeilen
SELECT
    s.screening_code,
    m.movie_code,
    m.title,
    s.starts_at
FROM screenings AS s
INNER JOIN movies AS m ON m.id = s.movie_id
INNER JOIN halls AS h ON h.id = s.hall_id
WHERE h.name = 'Gloria'
ORDER BY s.starts_at;

-- Abfrage 4: Welche Tickets wurden für V-26001 verkauft?
-- erwartet: 4 Zeilen
SELECT
    t.ticket_number,
    t.row_label,
    t.seat_number,
    t.price_cents,
    c.first_name,
    c.last_name
FROM tickets AS t
INNER JOIN screenings AS s ON s.id = t.screening_id
LEFT JOIN customers AS c ON c.id = t.customer_id
WHERE s.screening_code = 'V-26001'
ORDER BY t.row_label, t.seat_number;

-- Abfrage 5: Für welche Vorstellungen wurde noch kein Ticket verkauft?
-- erwartet: 5 Zeilen
SELECT s.id, s.screening_code, s.starts_at
FROM screenings AS s
LEFT JOIN tickets AS t ON t.screening_id = s.id
WHERE t.id IS NULL
ORDER BY s.starts_at;

-- Abfrage 6: Wie viele Tickets wurden für jede Vorstellung verkauft?
-- erwartet: 14 Zeilen
SELECT
    s.screening_code,
    s.starts_at,
    COUNT(t.id) AS ticket_count
FROM screenings AS s
LEFT JOIN tickets AS t ON t.screening_id = s.id
GROUP BY s.id, s.screening_code, s.starts_at
ORDER BY ticket_count DESC, s.screening_code;

-- Abfrage 7: Für welche Filme wurden mehr als zwei Tickets verkauft?
-- erwartet: 3 Zeilen
SELECT
    m.id,
    m.movie_code,
    m.title,
    COUNT(t.id) AS ticket_count
FROM movies AS m
INNER JOIN screenings AS s ON s.movie_id = m.id
INNER JOIN tickets AS t ON t.screening_id = s.id
GROUP BY m.id, m.movie_code, m.title
HAVING COUNT(t.id) > 2
ORDER BY ticket_count DESC, m.title;

-- Abfrage 8: Welche Säle liegen über dem Durchschnitt aller Ticketverkäufe?
-- erwartet: 5 Zeilen
SELECT
    h.id,
    h.hall_number,
    h.name,
    COUNT(t.id) AS ticket_count
FROM halls AS h
LEFT JOIN screenings AS s ON s.hall_id = h.id
LEFT JOIN tickets AS t ON t.screening_id = s.id
GROUP BY h.id, h.hall_number, h.name
HAVING COUNT(t.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM halls)
    FROM tickets
)
ORDER BY ticket_count DESC, h.hall_number;
