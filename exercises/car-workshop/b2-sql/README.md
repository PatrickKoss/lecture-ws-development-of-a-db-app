# B2: SQL-Abfragen

Zeitbox: etwa 45 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

`../b1-schema/starter/` enthält euer laufendes Schema und Seed-Daten. `starter/queries.sql` benennt vier Pflichtabfragen.

Starttest:

```bash
../b1-schema/check.sh
```

## Kernauftrag

Schreibt vier Abfragen: einen Filter auf `parts`, einen INNER JOIN, einen LEFT JOIN und eine Aggregation mit GROUP BY. Jede Abfrage bekommt einen Satz zur erwarteten Ergebnismenge.

## Vertiefung

Ergänzt HAVING, eine Unterabfrage oder eine zweite Aggregation. Vergleicht den Abfrageplan vor und nach einem Index.

## Vorbereiteter Zwischenstand

Die Lehrperson kann fertige Seed-Daten aus `instructor-solutions/car-workshop/sql/seed.sql` freigeben. Die Abfragen bleiben eure Arbeit.

## Ausgang

`starter/queries.sql` läuft ohne Fehler und enthält vier sichtbare Ergebnismengen.

Prüfbefehl:

```bash
./check.sh
```

## Auswertung

Warum fehlt bei eurem INNER JOIN eine Zeile, die beim LEFT JOIN erhalten bleibt?
