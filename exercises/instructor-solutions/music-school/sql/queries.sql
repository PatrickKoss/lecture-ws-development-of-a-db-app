PRAGMA foreign_keys = ON;

-- 1. Welche Kursangebote kosten höchstens 120 Euro?
-- Erwartet werden MU-02, MU-03 und MU-01, nach Gebühr aufsteigend.
SELECT course_code, title, fee
FROM music_courses
WHERE fee <= 120
ORDER BY fee, course_code;

-- 2. Welche Lehrkraft unterrichtet welches Kursangebot und in welchem Raum?
-- MU-04 erscheint nicht, weil der Online-Kurs keinen Raum hat.
SELECT c.course_code, c.title, t.name AS teacher_name, r.room_code
FROM music_courses AS c
INNER JOIN teachers AS t ON t.id = c.teacher_id
INNER JOIN rooms AS r ON r.id = c.room_id
ORDER BY c.course_code;

-- 3. Welche Kursangebote haben noch keine Anmeldung?
-- MU-04 bleibt durch den LEFT JOIN erhalten und hat enrollment_count 0.
SELECT c.course_code, c.title, COUNT(e.student_id) AS enrollment_count
FROM music_courses AS c
LEFT JOIN enrollments AS e ON e.course_id = c.id
GROUP BY c.id, c.course_code, c.title
HAVING COUNT(e.student_id) = 0
ORDER BY c.course_code;

-- 4. Wie viele Kursangebote verantwortet jede Lehrkraft?
-- Der LEFT JOIN würde auch eine Lehrkraft ohne Kurs mit course_count 0 anzeigen.
SELECT t.staff_code, t.name, COUNT(c.id) AS course_count
FROM teachers AS t
LEFT JOIN music_courses AS c ON c.teacher_id = t.id
GROUP BY t.id, t.staff_code, t.name
ORDER BY course_count DESC, t.staff_code;

-- Vertiefung: Welche Kurse benötigen mehr als ein Instrument insgesamt?
SELECT c.course_code, c.title, SUM(ci.quantity_required) AS instrument_count
FROM music_courses AS c
INNER JOIN course_instruments AS ci ON ci.course_id = c.id
GROUP BY c.id, c.course_code, c.title
HAVING SUM(ci.quantity_required) > 1
ORDER BY c.course_code;
