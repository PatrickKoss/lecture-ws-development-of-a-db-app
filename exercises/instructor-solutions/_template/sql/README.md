# Musterlösung für B1 und B2

`schema.sql` setzt Primary Keys, Foreign Keys, Pflichtfelder, eindeutige Fachschlüssel und fachliche Wertebereiche um. `seed.sql` enthält mindestens drei Datensätze pro Tabelle. `queries.sql` löst Filter, INNER JOIN, LEFT JOIN, Aggregation und die HAVING-Vertiefung.

```bash
sqlite3 ':memory:' ".read schema.sql" ".read seed.sql" ".read queries.sql" "PRAGMA foreign_key_check;"
```

Ein zweiter Insert mit `resource_code = 'R-01'` endet mit `UNIQUE constraint failed: resources.resource_code`. Eine Ausgabe mit einer unbekannten `resource_id` endet bei aktiven Foreign Keys mit `FOREIGN KEY constraint failed`.

`query-plan.sql` vergleicht den Filter auf `resources.measure` vor und nach dem
Index `idx_resources_measure`. SQLite wechselt von `SCAN resources` zu einer
Bereichssuche über den Index.
