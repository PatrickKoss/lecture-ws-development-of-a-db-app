PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Kurse richten sich an Einsteiger?
-- erwartet: 4 Zeilen
SELECT id, course_code, title, duration_minutes
FROM courses
WHERE level = 'BEGINNER'
ORDER BY title;

-- Abfrage 2: Welche nicht abgesagten Kurstermine finden ab dem 2026-09-15 statt?
-- erwartet: 9 Zeilen
SELECT id, course_id, trainer_id, session_date, start_time
FROM course_sessions
WHERE cancelled = 0
  AND session_date >= '2026-09-15'
ORDER BY session_date, start_time, id;

-- Abfrage 3: Welche Termine finden im Kursraum 1 statt?
-- erwartet: 5 Zeilen
SELECT
    cs.id,
    c.course_code,
    c.title,
    t.first_name,
    t.last_name,
    cs.session_date,
    cs.start_time
FROM course_sessions AS cs
INNER JOIN courses AS c ON c.id = cs.course_id
INNER JOIN rooms AS r ON r.id = c.room_id
INNER JOIN trainers AS t ON t.id = cs.trainer_id
WHERE r.room_code = 'KR-1'
ORDER BY cs.session_date, cs.start_time;

-- Abfrage 4: Welche Buchungen wurden erfasst?
-- erwartet: 20 Zeilen
SELECT
    b.id,
    m.membership_number,
    m.first_name,
    m.last_name,
    c.title,
    cs.session_date,
    cs.start_time,
    b.attended
FROM bookings AS b
INNER JOIN members AS m ON m.id = b.member_id
INNER JOIN course_sessions AS cs ON cs.id = b.course_session_id
INNER JOIN courses AS c ON c.id = cs.course_id
ORDER BY cs.session_date, cs.start_time, b.id;

-- Abfrage 5: Welcher Raum gehört zu jedem Kurs?
-- erwartet: 12 Zeilen
SELECT
    c.id,
    c.course_code,
    c.title,
    r.room_code,
    r.name AS room_name
FROM courses AS c
LEFT JOIN rooms AS r ON r.id = c.room_id
ORDER BY c.course_code;

-- Abfrage 6: Wie viele Buchungen hat jedes Mitglied?
-- erwartet: 12 Zeilen
SELECT
    m.membership_number,
    m.first_name,
    m.last_name,
    COUNT(b.id) AS booking_count
FROM members AS m
LEFT JOIN bookings AS b ON b.member_id = m.id
GROUP BY m.id, m.membership_number, m.first_name, m.last_name
ORDER BY booking_count DESC, m.membership_number;

-- Abfrage 7: Welche Kurse haben mehr als zwei Buchungen?
-- erwartet: 3 Zeilen
SELECT
    c.id,
    c.course_code,
    c.title,
    COUNT(b.id) AS booking_count
FROM courses AS c
INNER JOIN course_sessions AS cs ON cs.course_id = c.id
INNER JOIN bookings AS b ON b.course_session_id = cs.id
GROUP BY c.id, c.course_code, c.title
HAVING COUNT(b.id) > 2
ORDER BY booking_count DESC, c.course_code;

-- Abfrage 8: Welche Mitglieder haben mehr Buchungen als der Durchschnitt?
-- erwartet: 9 Zeilen
SELECT
    m.id,
    m.membership_number,
    m.first_name,
    m.last_name,
    COUNT(b.id) AS booking_count
FROM members AS m
INNER JOIN bookings AS b ON b.member_id = m.id
GROUP BY m.id, m.membership_number, m.first_name, m.last_name
HAVING COUNT(b.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM members)
    FROM bookings
)
ORDER BY booking_count DESC, m.membership_number;
