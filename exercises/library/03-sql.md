# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Mitglieder, Autoren, Bücher, Exemplare, Ausleihen und Vormerkungen sowie eine Zuordnung zwischen Büchern und Autoren. ISBN, Mitgliedsnummer, E-Mail und Barcode müssen eindeutig sein. Ein Exemplar ist innerhalb seines Buchs eindeutig. Datumsfolgen, Statuswerte, Zweigstellencodes und die Autorenreihenfolge brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle aktiven Mitglieder alphabetisch nach Nachname und Vorname auf.
2. Findet alle noch nicht zurückgegebenen Ausleihen, die am `2026-03-01` bereits fällig waren. Sortiert sie nach Fälligkeitsdatum.
3. Zeigt alle Exemplare der Zweigstelle Südstadt zusammen mit ISBN und Buchtitel.
4. Zeigt jede offene Ausleihe mit Mitgliedsnummer, Mitgliedsname, Buchtitel, Exemplarnummer und Fälligkeitsdatum.
5. Findet alle Bücher, für die noch nie eine Vormerkung erfasst wurde. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jedes Mitglied die Ausleihen, auch wenn das Mitglied noch nichts ausgeliehen hat.
7. Findet Autoren, deren Bücher zusammen mehr als zwei Ausleihen haben. Verwendet `GROUP BY` und `HAVING`.
8. Findet Mitglieder mit mehr Ausleihen als der Durchschnitt über alle Mitglieder. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
