# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

<!-- TODO(domain): Nenne die erwarteten Tabellen und die fachlich wichtigen Constraints, ohne SQL vorzugeben. Verlange 10 bis 20 Zeilen pro Tabelle mit realistischen Namen und ISO-Datumswerten. -->

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. <!-- TODO(domain): Einfache Auswahl mit WHERE und ORDER BY. -->
2. <!-- TODO(domain): Auswahl mit einem Vergleich gegen ein konkretes Datum im ISO-Format. -->
3. <!-- TODO(domain): Fachliche Frage mit JOIN über zwei Tabellen. -->
4. <!-- TODO(domain): Fachliche Frage mit JOIN über mindestens drei Tabellen. -->
5. <!-- TODO(domain): Frage nach Datensätzen ohne Zuordnung, die einen LEFT JOIN verlangt. -->
6. <!-- TODO(domain): Aggregation mit GROUP BY. -->
7. <!-- TODO(domain): Aggregation mit GROUP BY und HAVING, zum Beispiel nur Gruppen mit mehr als zwei Treffern. -->
8. <!-- TODO(domain): Frage, deren Lösung eine Subquery verlangt. -->

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
