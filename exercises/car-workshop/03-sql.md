# 3. SQL

> Zeitbox: 90 Minuten, davon 40 Minuten für Schema und Daten sowie 50 Minuten für Abfragen

## Vorbereitung

Erwartet werden Tabellen für Kunden, Fahrzeuge, Arbeitsaufträge, Mechaniker, Ersatzteile und Rechnungen sowie Zuordnungen für verbaute Teile und eingesetzte Mechaniker. Kundennummer, E-Mail, Kennzeichen, Fahrgestellnummer, Personalnummer, Teilenummer, Auftragsnummer und Rechnungsnummer müssen eindeutig sein. Eine Teileposition ist innerhalb ihres Arbeitsauftrags eindeutig. Datumsfolgen, Statuswerte, Mengen, Preise, Arbeitsstunden und Lagerbestände brauchen passende Constraints. Tragt pro Tabelle 10 bis 20 realistische Datensätze ein und verwendet für jedes Datum das ISO-Format `YYYY-MM-DD`.

## Aufgabe

### Schema und Testdaten

1. Übertragt eure normalisierten Tabellen nach `sql/schema.sql`.
2. Setzt Primary Keys, Foreign Keys, `NOT NULL` und fachlich nötige `UNIQUE`-Constraints.
3. Tragt in `sql/seed.sql` pro Tabelle 10 bis 20 Datensätze ein.
4. Führt beide Dateien mit SQLite 3 in einer leeren Datenbank aus.

### Acht Abfragen

Formuliert die acht Abfragen in `sql/queries.sql`. Schreibt über jede Abfrage einen Kommentar mit ihrer Nummer und dem erwarteten Ergebnis.

1. Listet alle Ersatzteile auf, deren Lagerbestand höchstens dem Meldebestand entspricht. Sortiert sie nach Teilenummer.
2. Findet alle noch offenen Arbeitsaufträge, die vor dem `2026-03-01` eingegangen sind. Sortiert sie nach Eingangsdatum.
3. Zeigt alle Arbeitsaufträge für Fahrzeuge der Marke Volkswagen zusammen mit Auftragsnummer, Kennzeichen und Modell.
4. Zeigt jeden offenen Arbeitsauftrag mit Kundennummer, Kundenname, Kennzeichen, Fahrzeugmodell und Eingangsdatum.
5. Findet alle Ersatzteile, die noch nie in einem Arbeitsauftrag verbaut wurden. Die Lösung soll einen `LEFT JOIN` verwenden.
6. Zählt für jedes Fahrzeug die Arbeitsaufträge, auch wenn das Fahrzeug noch nie in der Werkstatt war.
7. Findet Ersatzteile, die in mehr als zwei verschiedenen Arbeitsaufträgen verbaut wurden. Verwendet `GROUP BY` und `HAVING`.
8. Findet Kunden mit mehr Arbeitsaufträgen als der Durchschnitt über alle Kunden. Verwendet eine Subquery sowie `GROUP BY` und `HAVING`.

## Abgabe

- `sql/schema.sql`
- `sql/seed.sql`
- `sql/queries.sql` mit acht ausführbaren Abfragen
