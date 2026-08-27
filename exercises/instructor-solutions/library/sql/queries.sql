PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche aktiven Mitglieder gibt es?
-- erwartet: 10 Zeilen
SELECT id, membership_number, first_name, last_name
FROM members
WHERE active = 1
ORDER BY last_name, first_name;

-- Abfrage 2: Welche offenen Ausleihen waren am 2026-03-01 bereits fällig?
-- erwartet: 2 Zeilen
SELECT id, member_id, book_id, copy_number, due_on
FROM loans
WHERE returned_on IS NULL
  AND due_on < '2026-03-01'
ORDER BY due_on, id;

-- Abfrage 3: Welche Exemplare stehen in der Zweigstelle Südstadt?
-- erwartet: 8 Zeilen
SELECT c.barcode, c.copy_number, b.isbn, b.title
FROM copies AS c
INNER JOIN books AS b ON b.id = c.book_id
WHERE c.branch_code = 'SUED'
ORDER BY b.title, c.copy_number;

-- Abfrage 4: Welche Ausleihen sind noch offen?
-- erwartet: 10 Zeilen
SELECT
    l.id,
    m.membership_number,
    m.first_name,
    m.last_name,
    b.title,
    l.copy_number,
    l.due_on
FROM loans AS l
INNER JOIN members AS m ON m.id = l.member_id
INNER JOIN books AS b ON b.id = l.book_id
INNER JOIN copies AS c
    ON c.book_id = l.book_id
   AND c.copy_number = l.copy_number
WHERE l.returned_on IS NULL
ORDER BY l.due_on, l.id;

-- Abfrage 5: Für welche Bücher wurde noch nie eine Vormerkung erfasst?
-- erwartet: 2 Zeilen
SELECT b.id, b.isbn, b.title
FROM books AS b
LEFT JOIN reservations AS r ON r.book_id = b.id
WHERE r.id IS NULL
ORDER BY b.title;

-- Abfrage 6: Wie viele Ausleihen hat jedes Mitglied?
-- erwartet: 12 Zeilen
SELECT
    m.membership_number,
    m.first_name,
    m.last_name,
    COUNT(l.id) AS loan_count
FROM members AS m
LEFT JOIN loans AS l ON l.member_id = m.id
GROUP BY m.id, m.membership_number, m.first_name, m.last_name
ORDER BY loan_count DESC, m.membership_number;

-- Abfrage 7: Welche Autoren kommen über ihre Bücher auf mehr als zwei Ausleihen?
-- erwartet: 2 Zeilen
SELECT
    a.id,
    a.first_name,
    a.last_name,
    COUNT(l.id) AS loan_count
FROM authors AS a
INNER JOIN book_authors AS ba ON ba.author_id = a.id
INNER JOIN loans AS l ON l.book_id = ba.book_id
GROUP BY a.id, a.first_name, a.last_name
HAVING COUNT(l.id) > 2
ORDER BY loan_count DESC, a.last_name;

-- Abfrage 8: Welche Mitglieder haben mehr Ausleihen als der Durchschnitt?
-- erwartet: 6 Zeilen
SELECT
    m.id,
    m.membership_number,
    m.first_name,
    m.last_name,
    COUNT(l.id) AS loan_count
FROM members AS m
INNER JOIN loans AS l ON l.member_id = m.id
GROUP BY m.id, m.membership_number, m.first_name, m.last_name
HAVING COUNT(l.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM members)
    FROM loans
)
ORDER BY loan_count DESC, m.membership_number;
