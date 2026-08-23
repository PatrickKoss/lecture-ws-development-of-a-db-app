# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Kunden, Adressen, Fahrer, Pizzen, Beläge und Bestellungen sowie Zuordnungen für Pizzabeläge und Bestellpositionen. Kundennummer, E-Mail, Fahrernummer, Pizzanummer und Belagname müssen eindeutig sein. Eine Position ist innerhalb ihrer Bestellung eindeutig. Bestellart, Statuswerte, Mengen, Preise und die Zuordnung von Lieferadressen brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle aktiven Pizzen alphabetisch nach Name auf.
2. Findet alle noch nicht abgeschlossenen oder stornierten Bestellungen, die vor dem `2026-03-01` eingegangen sind. Sortiert sie nach Bestelldatum.
3. Zeigt alle Lieferbestellungen zusammen mit Bestellnummer, Kundenname, Straße, Hausnummer und Postleitzahl.
4. Zeigt jede Bestellposition mit Bestellnummer, Pizzaname, Menge, Einzelpreis und berechnetem Positionsumsatz.
5. Findet alle Bestellungen, denen kein Fahrer zugeordnet ist. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jeden Kunden die Bestellungen, auch wenn der Kunde noch nichts bestellt hat.
7. Findet Pizzen, von denen insgesamt mehr als drei Stück bestellt wurden. Verwendet `GROUP BY` und `HAVING`.
8. Findet Pizzen, deren Umsatz über dem durchschnittlichen Umsatz pro Pizza liegt. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
