# Musterlösung für B1 und B2

`schema.sql` enthält Primary Keys, Foreign Keys, Pflichtfelder, eindeutige Fachschlüssel und fachliche Wertebereiche. `seed.sql` legt mindestens drei Zeilen pro Tabelle an. `queries.sql` löst Filter, INNER JOIN, LEFT JOIN, Aggregation und mindestens eine Vertiefung.

```bash
sqlite3 ':memory:' ".read schema.sql" ".read seed.sql" ".read queries.sql" "PRAGMA foreign_key_check;"
```

Die UNIQUE-Vertiefung lässt sich nach Schema und Seed mit diesem Update prüfen:

```sql
UPDATE movies
SET movie_code = (SELECT movie_code FROM movies WHERE id = 1)
WHERE id = 2;
-- UNIQUE constraint failed: movies.movie_code
```

Der Foreign-Key-Test verwendet einen sicher unbekannten Schlüssel:

```sql
PRAGMA foreign_keys = ON;
UPDATE tickets SET customer_id = 999999 WHERE id = (SELECT id FROM tickets LIMIT 1);
-- FOREIGN KEY constraint failed
```

`query-plan.sql` zeigt den Plan derselben Filterabfrage vor und nach einem passenden Index. SQLite wechselt dabei von einem vollständigen Scan zu einer Indexsuche.
