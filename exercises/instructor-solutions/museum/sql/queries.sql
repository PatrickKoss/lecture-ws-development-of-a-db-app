PRAGMA foreign_keys = ON;

-- 1. Welche Exponate haben einen Versicherungswert ab 10.000 Euro?
SELECT inventory_code, title, insured_value
FROM exhibits
WHERE insured_value >= 10000
ORDER BY insured_value DESC;

-- 2. In welcher Galerie steht jedes zugeordnete Exponat?
SELECT e.inventory_code, e.title, g.name AS gallery_name
FROM exhibits AS e
INNER JOIN galleries AS g ON g.id = e.gallery_id
ORDER BY g.floor, e.title;

-- 3. Welche Exponate sind noch nie verliehen worden?
SELECT e.inventory_code, e.title
FROM exhibits AS e
LEFT JOIN loans AS l ON l.exhibit_id = e.id
WHERE l.id IS NULL
ORDER BY e.inventory_code;

-- 4. Wie viele Exponate stehen in jeder Galerie?
SELECT g.name, COUNT(e.id) AS exhibit_count
FROM galleries AS g
LEFT JOIN exhibits AS e ON e.gallery_id = g.id
GROUP BY g.id, g.name
ORDER BY exhibit_count DESC, g.name;

-- Vertiefung: Welche Galerien enthalten mehr als ein Exponat?
SELECT g.name, COUNT(e.id) AS exhibit_count
FROM galleries AS g
INNER JOIN exhibits AS e ON e.gallery_id = g.id
GROUP BY g.id, g.name
HAVING COUNT(e.id) > 1
ORDER BY g.name;
