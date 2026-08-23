# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/README.md` nennen sechs fachliche Entitätstypen; `Ticket` ist die Zuordnung zwischen Bestellung und Kategorie. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt Käufer zu Bestellung und Veranstaltung zu Ticketkategorie. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt Bestellung zu Ticketkategorie über `tickets` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `tickets` speichert `seat_label`, `price_paid` und `checked_in_at`. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | `events.organizer_id` ist optional; intern betreute Veranstaltungen haben keinen externen Veranstalter. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `ticket_categories` wird durch `event_id` und `name` identifiziert. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 14 Datenzeilen; B-26001, B-26002, B-26004 bis B-26006 und Stammdaten wiederholen sich. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Bestellung -> Kundennr. -> Käufer, E-Mail`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `Art -> Einlasscode`; Art ist kein Superschlüssel, Einlasscode aber Teil des Alternativschlüssels `(Titel, Einlasscode)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 2 bis 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfrage 6 nutzt ihn für Kategorien ohne Verkäufe erneut. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht die Ticketzahl mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 1 vergleicht `event_on` mit `2026-06-01`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/event-tickets.db` lief fehlerfrei; die Abfragen ergaben 7, 4, 10, 18, 8, 15, 2 und 7 Zeilen, `foreign_key_check` blieb leer. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/events` und `GET /events/{id}/organizer`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Zeitregeln, Eindeutigkeit und konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
