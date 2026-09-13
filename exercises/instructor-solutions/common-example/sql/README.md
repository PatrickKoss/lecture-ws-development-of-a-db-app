# Musterlösung für B1 und B2

`schema.sql` und `seed.sql` bilden die Hochschulverwaltung in SQLite ab. `queries.sql` enthält die vier Pflichtabfragen aus B2. `query-plan.sql` zeigt den Plan vor und nach einem Index auf dem Studienbeginn.

Die Übungschecker können diese Dateien direkt prüfen:

```bash
python3 ../../../../common-example/exercises/b1-schema/check.py schema.sql seed.sql
python3 ../../../../common-example/exercises/b2-sql/check.py queries.sql
```
