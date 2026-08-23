# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Restaurants, Gerichte, Kunden, Kuriere, Bestellungen und Bewertungen sowie Bestellpositionen als Zuordnung zwischen Bestellungen und Gerichten. Partnernummer, Kundennummer, E-Mail, Kuriernummer und Telefonnummer müssen eindeutig sein. Ein Gericht ist innerhalb seines Restaurants eindeutig, eine Position innerhalb ihrer Bestellung. Datumsfolgen, Bestellarten, Statuswerte, Mengen, Preise, Bewertungen und Provisionssätze brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD` oder für Zeitpunkte `YYYY-MM-DD HH:MM:SS`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle aktiven Gerichte alphabetisch nach Name auf.
2. Findet alle noch nicht abgeschlossenen Bestellungen, die vor dem `2026-03-01` aufgegeben wurden. Sortiert sie nach Bestellzeitpunkt.
3. Zeigt alle Gerichte der Restaurants aus Essen zusammen mit Partnernummer und Restaurantname.
4. Zeigt jede Lieferbestellung mit Bestellnummer, Kundenname, Restaurant, Kuriername und Lieferzeit. Bestellungen ohne zugewiesenen Kurier sollen ebenfalls erscheinen.
5. Findet alle Bestellungen, für die noch nie eine Bewertung erfasst wurde. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jeden Kunden die Bestellungen, auch wenn der Kunde noch nichts bestellt hat.
7. Findet Restaurants mit mehr als zwei bestellten Positionen. Verwendet `GROUP BY` und `HAVING`.
8. Findet Restaurants, deren Provisionsbetrag aus nicht stornierten Bestellungen über dem Durchschnitt aller Restaurants liegt. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
