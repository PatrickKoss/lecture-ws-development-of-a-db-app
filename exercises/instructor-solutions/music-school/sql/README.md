# Musterlösung für B1 und B2

## B1: Schema und Seed-Daten

`schema.sql` setzt Primär-, Fremd- und Eindeutigkeitsschlüssel direkt in
SQLite um. Die fachlichen `CHECK`-Constraints begrenzen die Gebühr auf 0 bis
500 Euro, erlauben drei Schwierigkeitsstufen, verbieten nicht positive
Raumkapazitäten und verlangen mindestens ein Instrument pro Anforderung.

`ON DELETE CASCADE` steht nur an den abhängigen Zuordnungen. Wird ein
Kursangebot gelöscht, dürfen seine Anmeldungen und Instrumentanforderungen
verschwinden. Eine Lehrkraft oder ein Raum mit vorhandenem Kursangebot kann
dagegen nicht versehentlich gelöscht werden.

Die Haupttabelle hat Standardwerte für `level`, `teacher_id` und `room_id`.
`room_id` darf für einen Online-Kurs NULL sein. Durch die Standardwerte kann die
flache POST-Aufgabe aus C3 bei ihren drei Eingabefeldern bleiben. In einer
echten Anwendung sollte der Request die fachliche Auswahl von Lehrkraft und
Raum enthalten.

## B2: Abfragen

Die vier Pflichtabfragen in `queries.sql` decken Filter, `INNER JOIN`,
`LEFT JOIN` und Aggregation ab. Mit den Seed-Daten entstehen diese wesentlichen
Ergebnisse:

- Der Filter liefert drei Kurse mit einer Gebühr bis 120 Euro.
- Der `INNER JOIN` ordnet drei Kursen Lehrkraft und Raum zu. `MU-04` fehlt,
  weil der Online-Kurs keine `room_id` hat.
- Der `LEFT JOIN` findet `MU-04`, weil dieser Kurs keine Anmeldung hat.
- Die Aggregation zählt für `LK-02` zwei Kurse und für die anderen Lehrkräfte
  je einen Kurs.

Ein `INNER JOIN` mit `enrollments` würde `MU-04` entfernen. Der `LEFT JOIN`
behält die Kurszeile und setzt die Spalten der fehlenden Anmeldung auf NULL.
Darum zählt die Abfrage `e.student_id` und nicht `COUNT(*)`.

Ausführen:

```bash
db=$(mktemp)
sqlite3 "$db" < schema.sql
sqlite3 "$db" < seed.sql
sqlite3 -header -column "$db" < queries.sql
rm "$db"
```
