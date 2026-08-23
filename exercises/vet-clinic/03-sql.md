# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Halter, Tiere, Tierärzte, Termine, Behandlungen und Medikamente sowie eine Zuordnung zwischen Behandlungen und Medikamenten. Kundennummer, E-Mail, Approbationsnummer, PZN und Versicherungsnummer müssen eindeutig sein. Eine Tiernummer ist nur innerhalb eines Halters eindeutig. Datumswerte, Terminstatus, Tierarten, Dosis und Behandlungsdauer brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD` oder für Zeitpunkte `YYYY-MM-DD HH:MM`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle aktiven Medikamente alphabetisch nach Produktname auf.
2. Findet alle noch geplanten Termine, die vor dem `2026-03-01` liegen. Sortiert sie nach Terminzeitpunkt.
3. Zeigt alle Termine von Dr. Miriam Vogt zusammen mit Tiername, Tierart und Terminzeitpunkt.
4. Zeigt jeden geplanten Termin mit Tiername, Halternummer, Haltername, Tierarzt und Anlass.
5. Findet alle Tiere, für die noch nie ein Termin erfasst wurde. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jeden Tierarzt die Termine, auch wenn der Tierarzt noch keinen Termin hatte.
7. Findet Medikamente, die in mehr als zwei Behandlungen verschrieben wurden. Verwendet `GROUP BY` und `HAVING`.
8. Findet Tiere mit mehr Terminen als der Durchschnitt über alle Tiere. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
