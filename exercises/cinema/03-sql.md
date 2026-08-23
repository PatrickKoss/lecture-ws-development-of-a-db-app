# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Filme, Säle, Sitze, Vorstellungen, Kunden und Tickets. Ein Ticket löst die Beziehung zwischen Vorstellung und Sitz auf. Filmnummer, Saalnummer, Kundennummer, Kunden-E-Mail und Ticketnummer müssen eindeutig sein. Ein Sitz ist innerhalb seines Saals eindeutig. Datumswerte, FSK-Freigaben, Mindestalter, Preise, Sprachfassungen, Vorführformate und Sitzkategorien brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle Filme ab Erscheinungsjahr 2024 alphabetisch nach Titel auf.
2. Findet alle Vorstellungen, die am oder nach dem `2026-04-15` beginnen. Sortiert sie nach Startzeit.
3. Zeigt alle Vorstellungen im Saal Gloria zusammen mit Filmnummer, Filmtitel und Startzeit.
4. Zeigt alle Tickets der Vorstellung V-26001 mit Reihe, Sitznummer, Preis und dem Namen des Kunden. Laufkundschaft soll ebenfalls erscheinen.
5. Findet alle Vorstellungen, für die noch kein Ticket verkauft wurde. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jede Vorstellung die verkauften Tickets, auch wenn noch kein Ticket verkauft wurde.
7. Findet Filme, für die zusammen mehr als zwei Tickets verkauft wurden. Verwendet `GROUP BY` und `HAVING`.
8. Findet Säle mit mehr verkauften Tickets als der Durchschnitt über alle Säle. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
