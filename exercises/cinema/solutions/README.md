# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs Entitäten. `tickets` setzt die Beziehung zwischen Vorstellung und Sitz um.

### `movies`

Attribute sind `id`, `movie_code`, `title`, `release_year`, `duration_minutes`, `fsk_code` und `minimum_age`. `id` ist der Primary Key. `movie_code` sowie die Kombination aus `title` und `minimum_age` sind Alternativschlüssel. Erscheinungsjahr, Laufzeit, FSK-Code und Mindestalter haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> movie_code, title, release_year, duration_minutes, fsk_code, minimum_age`
- `movie_code -> id, title, release_year, duration_minutes, fsk_code, minimum_age`
- `(title, minimum_age) -> id, movie_code, release_year, duration_minutes, fsk_code`
- `fsk_code -> minimum_age`

Die letzte Abhängigkeit ist die BCNF-Falle. `fsk_code` ist kein Superschlüssel. `minimum_age` ist aber ein Primattribut des Alternativschlüssels `(title, minimum_age)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `fsk_ratings(fsk_code, minimum_age)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Schema bei den sechs fachlichen Entitäten bleibt.

### `halls`

Attribute sind `id`, `hall_number`, `name` und `capacity`. `id` ist der Primary Key. `hall_number` und `name` sind jeweils eindeutige Alternativschlüssel. Die Kapazität ist auf 1 bis 500 Plätze beschränkt.

Funktionale Abhängigkeiten:

- `id -> hall_number, name, capacity`
- `hall_number -> id, name, capacity`
- `name -> id, hall_number, capacity`

### `seats`

Attribute sind `hall_id`, `row_label`, `seat_number`, `category` und `accessible`. `hall_id`, `row_label` und `seat_number` bilden den Primary Key. `hall_id` verweist auf `halls.id`. Kategorie und Kennzeichnung für barrierearme Plätze haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `(hall_id, row_label, seat_number) -> category, accessible`

`seats` ist der Kandidat für eine schwache Entität. Reihe und Sitznummer sind nur innerhalb eines Saals eindeutig.

### `screenings`

Attribute sind `id`, `screening_code`, `movie_id`, `hall_id`, `starts_at`, `language` und `projection_format`. `id` ist der Primary Key. `movie_id` verweist auf `movies.id`, `hall_id` auf `halls.id`. `screening_code` und die Kombination aus `hall_id` und `starts_at` sind Alternativschlüssel. Sprachfassung und Vorführformat haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `id -> screening_code, movie_id, hall_id, starts_at, language, projection_format`
- `screening_code -> id, movie_id, hall_id, starts_at, language, projection_format`
- `(hall_id, starts_at) -> id, screening_code, movie_id, language, projection_format`

### `customers`

Attribute sind `id`, `customer_number`, `first_name`, `last_name`, `email`, `registered_on` und `active`. `id` ist der Primary Key. `customer_number` und `email` sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> customer_number, first_name, last_name, email, registered_on, active`
- `customer_number -> id, first_name, last_name, email, registered_on, active`
- `email -> id, customer_number, first_name, last_name, registered_on, active`

### `tickets`

Attribute sind `id`, `ticket_number`, `screening_id`, `hall_id`, `row_label`, `seat_number`, `customer_id`, `price_cents` und `sold_at`. `id` ist der Primary Key. `screening_id` und `hall_id` verweisen gemeinsam auf `screenings`. `hall_id`, `row_label` und `seat_number` verweisen auf `seats`. `customer_id` verweist optional auf `customers.id`. `ticket_number` und die Kombination aus Vorstellung und Sitz sind Alternativschlüssel.

Funktionale Abhängigkeiten:

- `id -> ticket_number, screening_id, hall_id, row_label, seat_number, customer_id, price_cents, sold_at`
- `ticket_number -> id, screening_id, hall_id, row_label, seat_number, customer_id, price_cents, sold_at`
- `(screening_id, hall_id, row_label, seat_number) -> id, ticket_number, customer_id, price_cents, sold_at`

`tickets` löst die Beziehung zwischen Vorstellung und Sitz auf. Preis und Verkaufszeitpunkt sind Attribute dieser Beziehung. Der Alternativschlüssel aus Vorstellung und Sitz verhindert einen Doppelverkauf. `customer_id` darf `NULL` sein, wenn die Kasse an Laufkundschaft verkauft.

## REST-Endpunkte

| Methode | Pfad | Erfolg | Weitere Statuscodes |
| --- | --- | --- | --- |
| `GET` | `/screenings` | `200 OK` | keine |
| `GET` | `/screenings/{id}` | `200 OK` | `404 Not Found` |
| `POST` | `/screenings` | `201 Created` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT` | `/screenings/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/screenings/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` |
| `GET` | `/screenings/{id}/tickets` | `200 OK` | `404 Not Found` |

## Annahmen zu den offenen Fragen

Die Referenzlösung behandelt die Sitzkategorie als feste Eigenschaft eines Sitzes. Wer Preise oder Kategorien pro Vorstellung ändern möchte, kann die Kategorie stattdessen an die Beziehung zwischen Vorstellung und Sitz hängen.

Eine Startzeit ist nur zusammen mit dem Saal eindeutig. Mehrere Säle dürfen gleichzeitig Vorstellungen zeigen. `UNIQUE (hall_id, starts_at)` bildet diese Entscheidung ab.

Das Kursmodell enthält nur gültige Verkäufe. Eine Stornierung löscht das Ticket und gibt den Sitz wieder frei. Ein Produktivsystem sollte stattdessen Status und Stornierungszeitpunkt speichern und die Eindeutigkeitsregel auf aktive Tickets beschränken.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
