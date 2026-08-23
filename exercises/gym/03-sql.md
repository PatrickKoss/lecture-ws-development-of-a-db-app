# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Mitglieder, Tarife, Mitgliedschaften, Trainer, Räume, Kurse, Kurstermine und Buchungen. Mitgliedsnummer, E-Mail, Tarifcode, Trainer-E-Mail, Raumcode und Kurscode müssen eindeutig sein. Ein Mitglied darf einen Kurstermin nur einmal buchen. Datumsfolgen, Beitragswerte, Statuswerte, Niveaus, Dauer und Teilnahme brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle aktiven Mitglieder alphabetisch nach Nachname und Vorname auf.
2. Findet alle nicht abgesagten Kurstermine ab dem `2026-09-15`. Sortiert sie nach Datum und Startzeit.
3. Zeigt alle Termine im Kursraum 1 zusammen mit Kurscode, Kurstitel und Trainername.
4. Zeigt jede Buchung mit Mitgliedsnummer, Mitgliedsname, Kurstitel, Kurstag, Startzeit und Teilnahmeangabe.
5. Zeigt alle Kurse mit ihrem Raum. Online-Kurse ohne Raum müssen ebenfalls erscheinen. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jedes Mitglied die Buchungen, auch wenn das Mitglied noch keinen Kurs gebucht hat.
7. Findet Kurse mit mehr als zwei Buchungen. Verwendet `GROUP BY` und `HAVING`.
8. Findet Mitglieder mit mehr Buchungen als der Durchschnitt über alle Mitglieder. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
