-- B2.1 Projektion und Filter
-- Erwartet fünf Studierende, sortiert nach Studierendennummer.
SELECT student_number, first_name, last_name
FROM students
WHERE enrollment_date >= '2025-04-01'
ORDER BY student_number;

-- B2.2 INNER JOIN
-- Erwartet alle mit 1,3 bewerteten Belegungen samt Kurscode.
SELECT s.student_number, c.course_code, e.grade
FROM enrollments AS e
INNER JOIN students AS s ON s.id = e.student_id
INNER JOIN courses AS c ON c.id = e.course_id
WHERE e.grade = 1.3
ORDER BY s.student_number, c.course_code;

-- B2.3 LEFT JOIN
-- Erwartet Studierende ohne Kursbelegung.
SELECT s.student_number, s.first_name, s.last_name
FROM students AS s
LEFT JOIN enrollments AS e ON e.student_id = s.id
WHERE e.id IS NULL
ORDER BY s.student_number;

-- B2.4 Aggregation
-- Erwartet jeden Kurs, auch Kurse ohne Belegung.
SELECT c.course_code, COUNT(e.id) AS enrollment_count
FROM courses AS c
LEFT JOIN enrollments AS e ON e.course_id = c.id
GROUP BY c.id, c.course_code
ORDER BY enrollment_count DESC, c.course_code;
