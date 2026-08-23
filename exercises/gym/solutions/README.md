# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs eigenständige Entitäten: Mitglied, Tarif, Kurs, Kurstermin, Trainer und Raum. `memberships` und `bookings` setzen die beiden Beziehungen mit eigenen Attributen als Beziehungsentitäten um.

### `members`

Attribute sind `id`, `membership_number`, `first_name`, `last_name`, `email`, `joined_on` und `active`. `id` ist der Primary Key. `membership_number` und `email` sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> membership_number, first_name, last_name, email, joined_on, active`
- `membership_number -> id, first_name, last_name, email, joined_on, active`
- `email -> id, membership_number, first_name, last_name, joined_on, active`

### `plans`

Attribute sind `id`, `plan_code`, `name`, `monthly_fee_cents`, `minimum_term_months` und `active`. `id` ist der Primary Key. Tarifcode und Name sind jeweils eindeutige Alternativschlüssel. Beitrag und Mindestlaufzeit haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> plan_code, name, monthly_fee_cents, minimum_term_months, active`
- `plan_code -> id, name, monthly_fee_cents, minimum_term_months, active`
- `name -> id, plan_code, monthly_fee_cents, minimum_term_months, active`

### `memberships`

Attribute sind `member_id`, `plan_id`, `starts_on` und `ends_on`. `member_id` und `starts_on` bilden den Primary Key. `member_id` verweist auf `members.id`, `plan_id` auf `plans.id`. Das Enddatum ist optional und darf nicht vor dem Startdatum liegen.

Funktionale Abhängigkeiten:

- `(member_id, starts_on) -> plan_id, ends_on`

`memberships` löst die Beziehung zwischen Mitglied und Tarif auf. Start- und Enddatum sind Attribute dieser Beziehung.

### `trainers`

Attribute sind `id`, `trainer_number`, `first_name`, `last_name`, `email`, `specialty`, `hired_on` und `active`. `id` ist der Primary Key. Trainernummer und E-Mail sind eindeutige Alternativschlüssel.

Funktionale Abhängigkeiten:

- `id -> trainer_number, first_name, last_name, email, specialty, hired_on, active`
- `trainer_number -> id, first_name, last_name, email, specialty, hired_on, active`
- `email -> id, trainer_number, first_name, last_name, specialty, hired_on, active`

### `rooms`

Attribute sind `id`, `room_code`, `name`, `capacity` und `floor`. `id` ist der Primary Key. Raumcode und Name sind eindeutige Alternativschlüssel. Kapazität und Etage haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> room_code, name, capacity, floor`
- `room_code -> id, name, capacity, floor`
- `name -> id, room_code, capacity, floor`

### `courses`

Attribute sind `id`, `course_code`, `title`, `level`, `duration_minutes` und `room_id`. `id` ist der Primary Key. `course_code` sowie die Kombination aus `title` und `duration_minutes` sind Alternativschlüssel. `room_id` ist optional und verweist auf `rooms.id`.

Funktionale Abhängigkeiten:

- `id -> course_code, title, level, duration_minutes, room_id`
- `course_code -> id, title, level, duration_minutes, room_id`
- `(title, duration_minutes) -> id, course_code, level, room_id`
- `level -> duration_minutes`

Die letzte Abhängigkeit ist die BCNF-Falle. `level` ist kein Superschlüssel. `duration_minutes` ist aber ein Primattribut des Alternativschlüssels `(title, duration_minutes)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `course_levels(level, duration_minutes)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Niveau direkt im Kurskatalog lesbar bleibt.

### `course_sessions`

Attribute sind `id`, `course_id`, `trainer_id`, `session_date`, `start_time`, `maximum_participants` und `cancelled`. `id` ist der Primary Key. `course_id` verweist auf `courses.id`, `trainer_id` auf `trainers.id`. Kurs, Datum und Startzeit bilden einen Alternativschlüssel. Trainer, Datum und Startzeit sind ebenfalls eindeutig, damit ein Trainer nicht zwei Termine zur gleichen Zeit übernimmt.

Funktionale Abhängigkeiten:

- `id -> course_id, trainer_id, session_date, start_time, maximum_participants, cancelled`
- `(course_id, session_date, start_time) -> id, trainer_id, maximum_participants, cancelled`
- `(trainer_id, session_date, start_time) -> id, course_id, maximum_participants, cancelled`

### `bookings`

Attribute sind `id`, `member_id`, `course_session_id`, `booked_on` und `attended`. `id` ist der technische Primary Key für die REST API. `member_id` verweist auf `members.id`, `course_session_id` auf `course_sessions.id`. Mitglied und Kurstermin bilden zusammen einen eindeutigen fachlichen Alternativschlüssel. Die Teilnahme ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> member_id, course_session_id, booked_on, attended`
- `(member_id, course_session_id) -> id, booked_on, attended`

`bookings` ist der Kandidat für eine schwache Entität. Eine Buchung hat ohne Mitglied und Kurstermin keine fachliche Identität. `booked_on` und `attended` sind Attribute der n:m-Beziehung.

## REST-Endpunkte

| Methode | Pfad | Erfolg | Weitere Statuscodes |
| --- | --- | --- | --- |
| `GET` | `/bookings` | `200 OK` | keine |
| `GET` | `/bookings/{id}` | `200 OK` | `404 Not Found` |
| `POST` | `/bookings` | `201 Created` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT` | `/bookings/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/bookings/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` |
| `GET` | `/bookings/{id}/course-session` | `200 OK` | `404 Not Found` |

## Annahmen zu den offenen Fragen

Die Referenzlösung versteht eine doppelte Buchung als zweite Anmeldung desselben Mitglieds für denselben Kurstermin. Der eindeutige Schlüssel aus `member_id` und `course_session_id` verhindert diesen Fall. Zeitliche Überschneidungen verschiedener Kurse müssten über Startzeit und Dauer geprüft werden und bleiben eine mögliche Erweiterung.

Ein Mitglied hat zu einem Zeitpunkt höchstens eine Mitgliedschaft. Tarifwechsel schließen den alten Zeitraum ab und beginnen am Folgetag. Das Schema prüft die Reihenfolge der beiden Datumswerte; eine lückenlose Überschneidungsprüfung gehört in den Service oder in einen Datenbank-Trigger.

Ein Kurs hat einen festen Raum, einzelne Termine übernehmen diese Zuordnung. Bei Online-Kursen ist `courses.room_id` leer. Die Auslastung berechnet sich aus Buchungen im Verhältnis zu `maximum_participants`; `attended` dient einer getrennten Auswertung der tatsächlichen Teilnahme.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
