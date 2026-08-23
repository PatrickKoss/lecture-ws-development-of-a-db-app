# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/README.md` nennen sechs eigenständige Entitäten: Member, Plan, Course, CourseSession, Trainer und Room. Membership und Booking sind Beziehungsentitäten. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt Course zu CourseSession; ein Kurs hat mehrere Termine. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt Member zu CourseSession über `bookings` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `bookings.booked_on` und `bookings.attended` speichern Buchungstag und Teilnahme; `memberships` hat zusätzlich Start- und Enddatum. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | `courses.room_id` ist optional; Online-Kurse haben keinen Raum. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `bookings` hängt fachlich von Member und CourseSession ab; `(member_id, course_session_id)` ist eindeutig. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 14 Datenzeilen; die Termine C-101 bis C-104 sowie Mitglieds-, Tarif- und Kursdaten wiederholen sich. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Buchung -> Mitgliedsnr. -> Mitglied, E-Mail`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `Niveau -> Dauer`; Niveau ist kein Superschlüssel, Dauer aber Teil des Alternativschlüssels `(Kurs, Dauer)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 3 und 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfrage 6 nutzt ihn für Mitglieder ohne Buchung erneut. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht die Buchungszahl mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 2 vergleicht `session_date` mit `2026-09-15`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/gym.db` lief fehlerfrei; die Abfragen ergaben 10, 9, 5, 20, 12, 12, 3 und 9 Zeilen, `foreign_key_check` blieb leer und `integrity_check` meldete `ok`. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/bookings` und `GET /bookings/{id}/course-session`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Datumsregeln, Eindeutigkeit, Kapazität und konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
