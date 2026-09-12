PRAGMA foreign_keys = ON;

-- Abfrage 1: Welche Ressourcen haben einen Messwert von mindestens 20?
-- erwartet: 2 Zeilen
SELECT resource_code, name, measure
FROM resources
WHERE measure >= 20
ORDER BY measure DESC;

-- Abfrage 2: Zu welcher Kategorie gehört jede klassifizierte Ressource?
-- erwartet: 4 Zeilen
SELECT r.resource_code, r.name, i.name AS category_name
FROM resources AS r
INNER JOIN reference_items AS i ON i.id = r.reference_item_id
ORDER BY r.resource_code;

-- Abfrage 3: Welchen aktuellen Standort hat jede Ressource, auch eine ausgegebene?
-- erwartet: 4 Zeilen
SELECT r.resource_code, r.name, l.name AS location_name
FROM resources AS r
LEFT JOIN locations AS l ON l.id = r.current_location_id
ORDER BY r.resource_code;

-- Abfrage 4: Wie viele Ressourcen liegen an jedem Standort?
-- erwartet: 3 Zeilen
SELECT l.location_code, l.name, COUNT(r.id) AS resource_count
FROM locations AS l
LEFT JOIN resources AS r ON r.current_location_id = l.id
GROUP BY l.id, l.location_code, l.name
ORDER BY resource_count DESC, l.location_code;

-- Vertiefung: Welche Kategorien haben mehr als eine Ressource?
-- erwartet: 1 Zeile
SELECT i.name, COUNT(r.id) AS resource_count
FROM reference_items AS i
INNER JOIN resources AS r ON r.reference_item_id = i.id
GROUP BY i.id, i.name
HAVING COUNT(r.id) > 1
ORDER BY i.name;
