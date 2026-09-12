PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche aktiven Medikamente führt die Praxis?
-- erwartet: 10 Zeilen
SELECT id, pzn, product_name, active_ingredient, dosage_form
FROM medications
WHERE active = 1
ORDER BY product_name;

-- Abfrage 2: Welche geplanten Termine liegen vor dem 2026-03-01?
-- erwartet: 2 Zeilen
SELECT id, pet_id, vet_id, scheduled_at, reason
FROM appointments
WHERE status = 'PLANNED'
  AND scheduled_at < '2026-03-01T00:00'
ORDER BY scheduled_at, id;

-- Abfrage 3: Welche Termine hat Dr. Miriam Vogt?
-- erwartet: 8 Zeilen
SELECT
    a.id,
    p.name AS pet_name,
    p.species,
    a.scheduled_at,
    a.status
FROM appointments AS a
INNER JOIN pets AS p ON p.id = a.pet_id
INNER JOIN vets AS v ON v.id = a.vet_id
WHERE v.first_name = 'Miriam'
  AND v.last_name = 'Vogt'
ORDER BY a.scheduled_at;

-- Abfrage 4: Welche Termine sind noch geplant?
-- erwartet: 3 Zeilen
SELECT
    a.id,
    p.name AS pet_name,
    o.customer_number,
    o.first_name AS owner_first_name,
    o.last_name AS owner_last_name,
    v.first_name AS vet_first_name,
    v.last_name AS vet_last_name,
    a.scheduled_at,
    a.reason
FROM appointments AS a
INNER JOIN pets AS p ON p.id = a.pet_id
INNER JOIN owners AS o ON o.id = p.owner_id
INNER JOIN vets AS v ON v.id = a.vet_id
WHERE a.status = 'PLANNED'
ORDER BY a.scheduled_at, a.id;

-- Abfrage 5: Für welche Tiere wurde noch nie ein Termin erfasst?
-- erwartet: 2 Zeilen
SELECT p.id, p.name, p.species, o.customer_number
FROM pets AS p
INNER JOIN owners AS o ON o.id = p.owner_id
LEFT JOIN appointments AS a ON a.pet_id = p.id
WHERE a.id IS NULL
ORDER BY p.name, p.id;

-- Abfrage 6: Wie viele Termine hat jeder Tierarzt?
-- erwartet: 10 Zeilen
SELECT
    v.license_number,
    v.first_name,
    v.last_name,
    COUNT(a.id) AS appointment_count
FROM vets AS v
LEFT JOIN appointments AS a ON a.vet_id = v.id
GROUP BY v.id, v.license_number, v.first_name, v.last_name
ORDER BY appointment_count DESC, v.license_number;

-- Abfrage 7: Welche Medikamente wurden in mehr als zwei Behandlungen verschrieben?
-- erwartet: 2 Zeilen
SELECT
    m.id,
    m.pzn,
    m.product_name,
    COUNT(p.treatment_id) AS prescription_count
FROM medications AS m
INNER JOIN prescriptions AS p ON p.medication_id = m.id
GROUP BY m.id, m.pzn, m.product_name
HAVING COUNT(p.treatment_id) > 2
ORDER BY prescription_count DESC, m.product_name;

-- Abfrage 8: Welche Tiere haben mehr Termine als der Durchschnitt?
-- erwartet: 3 Zeilen
SELECT
    p.id,
    p.name,
    p.species,
    COUNT(a.id) AS appointment_count
FROM pets AS p
INNER JOIN appointments AS a ON a.pet_id = p.id
GROUP BY p.id, p.name, p.species
HAVING COUNT(a.id) > (
    SELECT 1.0 * COUNT(*) / (SELECT COUNT(*) FROM pets)
    FROM appointments
)
ORDER BY appointment_count DESC, p.id;
