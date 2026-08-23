# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Stationen, Radmodelle, Tarife, Kunden, Räder, Wartungseinträge und Ausleihen. Die Ausleihe setzt die n:m-Beziehung zwischen Kunde und Rad um. Stationscode, Radnummer, Modellcode, Kundennummer, E-Mail und Tarifcode müssen eindeutig sein. Ein Wartungseintrag ist innerhalb seines Rads eindeutig. Datumsfolgen, Zeitfolgen, Statuswerte, Preise und Stationskapazitäten brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein. Bei Stationen umfasst der Beispieldatensatz neben den sechs aktiven Stationen auch geplante und geschlossene Standorte. Für die 80 Räder genügt ein Ausschnitt mit 15 Datensätzen. Verwendet für jedes Datum das ISO-Format `YYYY-MM-DD` und für Zeitpunkte `YYYY-MM-DDTHH:MM:SS`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle verfügbaren Räder alphabetisch nach Radnummer auf.
2. Findet alle Ausleihen, die am oder nach dem `2026-03-01` begonnen wurden. Sortiert sie nach Startzeit.
3. Zeigt alle Räder an der Station Domplatz zusammen mit Hersteller und Modellname.
4. Zeigt jede offene Ausleihe mit Kundennummer, Kundenname, Radnummer, Startstation und Startzeit.
5. Findet alle aktiven Stationen, an denen aktuell kein verfügbares Rad steht. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jeden Kunden die Ausleihen, auch wenn der Kunde noch kein Rad ausgeliehen hat.
7. Findet Radmodelle, deren Räder zusammen mehr als zwei Ausleihen haben. Verwendet `GROUP BY` und `HAVING`.
8. Findet Räder mit mehr Wartungseinträgen als der Durchschnitt über alle Räder. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
