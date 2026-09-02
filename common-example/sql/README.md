# Universitätsdatenbank

Dieses Beispiel führt die Tabellen aus Tag 1 in SQLite zusammen. Die normalisierte Datenbank enthält Studierende, Kurse, Lehrende, Einschreibungen und Fachbereiche. Die Decks 05 und 06 verwenden dieselben Daten für ihre SQL-Beispiele.

## Datenbank laden

Führe die Befehle in diesem Verzeichnis mit einer neuen Datenbankdatei aus:

```sh
sqlite3 university.db < schema.sql
sqlite3 university.db < seed.sql
sqlite3 -header -column university.db < queries.sql
```

`queries.sql` ändert Lenas E-Mail-Adresse und löscht eine kurslose Testperson. Lade `seed.sql` deshalb nur in eine neue, leere Datenbank.
