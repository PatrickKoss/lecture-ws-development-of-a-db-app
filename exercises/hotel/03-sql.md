# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Gäste, Zimmertypen, Zimmer, Mitarbeiter, Leistungen und Buchungen sowie Zuordnungen zwischen Buchungen und Zimmern und zwischen Buchungen und Leistungen. Gastnummer, E-Mail, Typ-Code, Mitarbeiterkürzel und Leistungscode müssen eindeutig sein. Ein Zimmer ist durch Etage und Zimmernummer eindeutig. Datumsfolgen, Statuswerte, Preise und Mengen brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle verfügbaren Zimmer nach Etage und Zimmernummer auf.
2. Findet alle bestätigten Buchungen, deren Anreise vor dem `2026-05-01` liegt und deren Abreise an diesem Tag oder später geplant ist. Sortiert sie nach Anreise.
3. Zeigt alle Zimmer zusammen mit Code, Bezeichnung und Standardpreis ihres Zimmertyps.
4. Zeigt jede bestätigte oder eingecheckte Buchung mit Buchungsnummer, Gastnummer, Gastname, Etage, Zimmernummer und geplantem Abreisedatum.
5. Findet alle Leistungen, die noch nie zu einer Buchung erfasst wurden. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jeden Zimmertyp die nicht stornierten Zimmerbelegungen, auch wenn für einen Typ noch keine Belegung vorliegt.
7. Findet Leistungen mit mehr als 100 Euro Umsatz aus nicht stornierten Buchungen. Verwendet `GROUP BY` und `HAVING` und berechnet den Umsatz aus Menge und berechnetem Einzelpreis.
8. Findet Gäste mit mehr Buchungen als der Durchschnitt über alle Gäste. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
