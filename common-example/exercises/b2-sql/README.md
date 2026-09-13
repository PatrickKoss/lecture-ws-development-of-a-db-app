# B2: Vier SQL-Abfragen

Zeitbox: etwa 25 Minuten vor der Gruppenphase.

## Eingang

Die Lösung aus B1 liefert Schema und Seed-Daten. `starter/queries.sql` enthält vier leere Aufgaben.

Der Check scheitert, bis vier ausführbare `SELECT`-Anweisungen vorhanden sind:

```sh
python3 check.py
```

## Gemeinsame Arbeit

1. Projiziert Studierendennummer und Namen. Filtert auf Studienbeginn ab dem 1. April 2025 und sortiert nach Studierendennummer.
2. Verbindet Belegungen, Studierende und Kurse mit `INNER JOIN`. Zeigt alle Belegungen mit Note 1,3.
3. Findet mit `LEFT JOIN` alle Studierenden ohne Kursbelegung.
4. Zählt mit `GROUP BY` die Belegungen je Kurs. Kurse ohne Belegung müssen im Ergebnis bleiben.
5. Schreibt über jede Abfrage einen Satz zur erwarteten Ergebnismenge.

## Checkpoint

`python3 check.py` führt die vier Statements einzeln gegen eine neue Datenbank aus. Es vergleicht Spalten, Zeilen und Reihenfolge mit den Seed-Daten.

## Lösung

[core-queries.sql](../../sql/core-queries.sql) enthält die vier Pflichtabfragen. Prüft sie mit:

```sh
python3 check.py ../../sql/core-queries.sql
```

Die Lösung liefert fünf Studierende mit Studienbeginn ab April 2025 und sechs Belegungen mit Note 1,3. Hannah Maier und Samir Saleh bleiben im `LEFT JOIN` erhalten, weil sie keine Belegung haben. Die kanonischen Seed-Daten verteilen 40 Belegungen über sieben Kurse; `INF-201` hat mit acht die meisten. Der Checker fügt zusätzlich einen leeren Kurs ein. So fällt eine Aggregation mit `INNER JOIN` trotz der kanonischen Seed-Daten durch.

[queries.sql](../../sql/queries.sql) bleibt die erweiterte Demo mit Änderungen, `HAVING`, Unterabfrage und weiteren Joins.

## Transfer zur eigenen Domäne

Bearbeitet vom Repository-Root `exercises/<domain>/b2-sql/`. Verwendet dieselben vier Abfrageformen mit den Tabellen und Fragen eurer Domäne.
