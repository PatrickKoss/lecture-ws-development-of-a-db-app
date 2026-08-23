# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/er.svg` nennen Movie, Screening, Hall, Seat, Ticket und Customer. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt Movie zu Screening; ein Film hat mehrere Vorstellungen. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt Screening zu Seat über `tickets` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `tickets.price_cents` und `tickets.sold_at` speichern Preis und Verkaufszeitpunkt. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | `tickets.customer_id` ist optional; Verkäufe an Laufkundschaft brauchen kein Kundenkonto. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `seats` wird durch `hall_id`, `row_label` und `seat_number` identifiziert. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 14 Datenzeilen; V-26001 sowie Film- und Saaldaten wiederholen sich. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Ticket -> Vorstellung -> Film-Nr. -> Film, Laufzeit, FSK`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `FSK-Code -> Mindestalter`; FSK-Code ist kein Superschlüssel, Mindestalter aber Teil des Alternativschlüssels `(Titel, Mindestalter)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 3 und 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfragen 4, 6 und 8 verwenden ihn ebenfalls. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht die Ticketzahl mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 2 vergleicht `starts_at` mit `2026-04-15 00:00:00`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/cinema.db` lief fehlerfrei; die Abfragen ergaben 9, 6, 4, 4, 5, 14, 3 und 5 Zeilen, `foreign_key_check` blieb leer. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/screenings` und `GET /screenings/{id}/tickets`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Wertemengen, Eindeutigkeit und konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
