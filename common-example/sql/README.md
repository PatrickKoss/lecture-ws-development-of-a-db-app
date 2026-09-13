# Universitätsdatenbank

Dieses Beispiel führt die Tabellen aus Tag 1 in SQLite zusammen. Die normalisierte Datenbank enthält Studierende, Kurse, Lehrende, Belegungen und Fachbereiche. Die Decks 05 und 06 verwenden dieselben Daten für ihre SQL-Beispiele.

## Datenbank laden

Führe die Befehle in diesem Verzeichnis mit einer neuen Datenbankdatei aus:

```sh
sqlite3 university.db < schema.sql
sqlite3 university.db < seed.sql
sqlite3 -header -column university.db < core-queries.sql
```

`core-queries.sql` enthält die vier read-only Pflichtabfragen aus B2. `queries.sql` ist die erweiterte Demo. Sie enthält weitere Abfragen, ändert Lenas E-Mail-Adresse und löscht eine kurslose Testperson. Lade `seed.sql` für diese Demo deshalb nur in eine neue, leere Datenbank.

Der kanonische Check funktioniert aus jedem Arbeitsverzeichnis:

```sh
python3 common-example/sql/check.py
```

Aus `common-example/sql/` lautet derselbe Aufruf `python3 check.py`. Das Programm lädt eine In-Memory-Datenbank und prüft Tabellenzahlen, Fremdschlüssel, fachliche Constraints sowie die erwarteten Ergebnisse der vier Pflichtabfragen.
