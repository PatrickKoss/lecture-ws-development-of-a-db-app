PRAGMA foreign_keys = ON;

-- 1. Welche Pakete wiegen mehr als vier Kilogramm?
-- erwartet: 2 Zeilen
SELECT tracking_code, recipient, weight
FROM parcels
WHERE weight > 4
ORDER BY weight DESC;

-- 2. Zwischen welchen Depots läuft jedes Paket?
-- erwartet: 4 Zeilen
SELECT p.tracking_code, origin.city AS origin_city, destination.city AS destination_city
FROM parcels AS p
INNER JOIN depots AS origin ON origin.id = p.origin_depot_id
INNER JOIN depots AS destination ON destination.id = p.destination_depot_id
ORDER BY p.tracking_code;

-- 3. Für welche Pakete gab es noch keinen Zustellversuch?
-- erwartet: 2 Zeilen
SELECT p.tracking_code, p.recipient
FROM parcels AS p
LEFT JOIN delivery_attempts AS a ON a.parcel_id = p.id
WHERE a.id IS NULL
ORDER BY p.tracking_code;

-- 4. Wie viele Pakete gehen an jedes Zieldepot?
-- erwartet: 3 Zeilen
SELECT d.code, d.city, COUNT(p.id) AS parcel_count
FROM depots AS d
LEFT JOIN parcels AS p ON p.destination_depot_id = d.id
GROUP BY d.id, d.code, d.city
ORDER BY parcel_count DESC, d.code;

-- Vertiefung: Welche Pakete haben mehr als eine Statusmeldung?
-- erwartet: 1 Zeile
SELECT p.tracking_code, COUNT(e.event_number) AS event_count
FROM parcels AS p
INNER JOIN status_events AS e ON e.parcel_id = p.id
GROUP BY p.id, p.tracking_code
HAVING COUNT(e.event_number) > 1
ORDER BY p.tracking_code;
