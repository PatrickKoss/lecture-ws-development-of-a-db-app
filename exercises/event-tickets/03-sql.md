# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Spielorte, Veranstalter, Veranstaltungen, Ticketkategorien, Käufer und Bestellungen sowie Tickets als Zuordnung zwischen Bestellungen und Kategorien. Veranstaltungsnummer, Spielortcode, Veranstalternummer, Kundennummer, Bestellnummer und E-Mail-Adressen müssen eindeutig sein. Ein Kategoriename ist innerhalb einer Veranstaltung eindeutig, eine Ticketnummer innerhalb einer Bestellung. Datums- und Zeitwerte, Status, Veranstaltungsarten, Platzarten, Preise und Kontingente brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle Veranstaltungen nach dem `2026-06-01` aufsteigend nach Datum und Startzeit auf.
2. Zeigt alle Veranstaltungen in der Zeche Carl mit Veranstaltungsnummer, Titel, Datum und Einlasszeit.
3. Zeigt alle bezahlten Bestellungen mit Bestellnummer, Kundennummer und Namen des Käufers.
4. Zeigt jedes verkaufte Ticket mit Veranstaltungstitel, Kategoriename, Bestellnummer, Käufername und bezahltem Preis.
5. Findet alle Veranstaltungen, bei denen noch kein Ticket eingecheckt wurde. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jede Ticketkategorie die verkauften und die eingecheckten Tickets, auch wenn noch keine Karte verkauft wurde.
7. Findet Ticketkategorien, für die mehr als zwei Karten verkauft wurden. Verwendet `GROUP BY` und `HAVING`.
8. Findet Käufer mit mehr Tickets als der Durchschnitt über alle Käufer. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
