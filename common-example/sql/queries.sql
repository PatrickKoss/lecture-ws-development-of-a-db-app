PRAGMA foreign_keys = ON;

-- Welche Studierenden sind gespeichert?
-- erwartet: 20 Zeilen
SELECT id, first_name, last_name, student_number
FROM students;

-- Welche fünf zuletzt eingeschriebenen Studierenden kommen alphabetisch zuerst?
-- erwartet: 5 Zeilen
SELECT id, first_name, last_name, enrollment_date
FROM students
WHERE enrollment_date >= '2024-10-01'
ORDER BY last_name, first_name
LIMIT 5;

-- Welche Zeile wurde beim Ändern von Lenas E-Mail-Adresse aktualisiert?
-- erwartet: 1 Zeile
UPDATE students
SET email = 'lena.hoffmann@campus.example'
WHERE student_number = 'M2023001'
RETURNING id, email;

-- Welche Zeile wurde beim Löschen der noch kurslosen Testperson entfernt?
-- erwartet: 1 Zeile
DELETE FROM students
WHERE student_number = 'M2025005'
RETURNING id, first_name, last_name;

-- Welche Studierenden haben in welchem Kurs eine Note von 1,7 oder besser?
-- erwartet: 16 Zeilen
SELECT
    s.first_name,
    s.last_name,
    c.title AS course,
    e.grade
FROM enrollments AS e
INNER JOIN students AS s ON s.id = e.student_id
INNER JOIN courses AS c ON c.id = e.course_id
WHERE e.grade <= 1.7
ORDER BY e.grade, s.last_name, c.title;

-- Welche Studierenden sind in keinem Kurs eingeschrieben?
-- erwartet: 1 Zeile
SELECT s.id, s.first_name, s.last_name
FROM students AS s
LEFT JOIN enrollments AS e ON e.student_id = s.id
WHERE e.id IS NULL
ORDER BY s.last_name;

-- Wie viele Einschreibungen hat jeder Kurs?
-- erwartet: 7 Zeilen
SELECT c.course_code, c.title, COUNT(e.id) AS enrollment_count
FROM courses AS c
LEFT JOIN enrollments AS e ON e.course_id = c.id
GROUP BY c.id, c.course_code, c.title
ORDER BY enrollment_count DESC, c.course_code;

-- Welche Durchschnittsnote hat jeder Kurs für bereits vergebene Noten?
-- erwartet: 7 Zeilen
SELECT c.course_code, c.title, ROUND(AVG(e.grade), 2) AS average_grade
FROM courses AS c
LEFT JOIN enrollments AS e ON e.course_id = c.id
GROUP BY c.id, c.course_code, c.title
ORDER BY c.course_code;

-- Welche Kurse haben mindestens sechs Einschreibungen?
-- erwartet: 3 Zeilen
SELECT c.course_code, c.title, COUNT(e.id) AS enrollment_count
FROM courses AS c
INNER JOIN enrollments AS e ON e.course_id = c.id
GROUP BY c.id, c.course_code, c.title
HAVING COUNT(e.id) >= 6
ORDER BY enrollment_count DESC, c.course_code;

-- Welche Studierenden haben eine bessere Durchschnittsnote als der Gesamtdurchschnitt?
-- erwartet: 7 Zeilen
SELECT s.id, s.first_name, s.last_name
FROM students AS s
WHERE s.id IN (
    SELECT e.student_id
    FROM enrollments AS e
    WHERE e.grade IS NOT NULL
    GROUP BY e.student_id
    HAVING AVG(e.grade) < (
        SELECT AVG(grade)
        FROM enrollments
        WHERE grade IS NOT NULL
    )
)
ORDER BY s.last_name, s.first_name;

-- Welche Einschreibungen fanden seit dem 1. April 2025 statt?
-- erwartet: 24 Zeilen
SELECT e.id, s.student_number, c.course_code, e.enrolled_on
FROM enrollments AS e
INNER JOIN students AS s ON s.id = e.student_id
INNER JOIN courses AS c ON c.id = e.course_id
WHERE e.enrolled_on >= '2025-04-01'
ORDER BY e.enrolled_on, s.student_number, c.course_code;

-- Welche Fachbereiche sind über belegte Kurse vertreten?
-- erwartet: 3 Zeilen
SELECT DISTINCT d.code, d.name
FROM departments AS d
INNER JOIN lecturers AS l ON l.department_id = d.id
INNER JOIN courses AS c ON c.lecturer_id = l.id
INNER JOIN enrollments AS e ON e.course_id = c.id
ORDER BY d.code;
