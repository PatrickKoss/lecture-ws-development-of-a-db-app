# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/er.svg` nennen Guest, Room, RoomType, Booking, Service und Employee als sechs Kernentitäten. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt RoomType zu Room; ein Zimmertyp gehört zu mehreren Zimmern. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt Booking zu Service über `booking_services` und Booking zu Room über `booking_rooms` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `booking_services` speichert Menge, Leistungsdatum und berechneten Einzelpreis; `booking_rooms` speichert Check-in, Check-out und Nachtpreis. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | `bookings.checked_in_by_employee_id` ist optional; Online-Buchungen haben vor dem Check-in keinen Mitarbeiter. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `rooms` wird durch `floor` und `room_number` identifiziert; die Zimmernummer ist nur innerhalb einer Etage eindeutig. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 14 Datenzeilen; B-26001, B-26003, B-26005, B-26007 und B-26009 wiederholen sich. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Buchung -> Gast-Nr. -> Gast, E-Mail, Firma`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `Etage -> Reinigungsbereich`; Etage ist kein Superschlüssel, Reinigungsbereich aber Teil des Alternativschlüssels `(Zimmernummer, Reinigungsbereich)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 3 und 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfrage 6 nutzt ihn für Zimmertypen ohne Belegung erneut. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht die Buchungszahl mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 2 vergleicht `arrival_on` und `departure_on` mit `2026-05-01`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/hotel.db` lief fehlerfrei; die Abfragen ergaben 9, 1, 16, 6, 2, 10, 3 und 2 Zeilen, `foreign_key_check` blieb leer und `integrity_check` ergab `ok`. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/bookings` und `GET /bookings/{id}/check-in-employee`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Datumsregeln, Zimmerüberschneidungen und konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
