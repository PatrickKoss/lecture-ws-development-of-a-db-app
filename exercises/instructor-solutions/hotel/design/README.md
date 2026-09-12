# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs Kernentitäten. `booking_rooms` und `booking_services` setzen die beiden n:m-Beziehungen als Zuordnungstabellen mit eigenen Attributen um.

### `guests`

Attribute sind `id`, `guest_number`, `first_name`, `last_name`, `email`, `phone`, `company_name`, `company_account_number` und `registered_on`. `id` ist der Primary Key. `guest_number`, `email` und die optionale `company_account_number` sind eindeutige Alternativschlüssel. Firmenname und Firmenkundennummer sind entweder gemeinsam gesetzt oder beide `NULL`.

Funktionale Abhängigkeiten:

- `id -> guest_number, first_name, last_name, email, phone, company_name, company_account_number, registered_on`
- `guest_number -> id, first_name, last_name, email, phone, company_name, company_account_number, registered_on`
- `email -> id, guest_number, first_name, last_name, phone, company_name, company_account_number, registered_on`
- `company_account_number -> id, guest_number, first_name, last_name, email, phone, company_name, registered_on`, sofern die Nummer nicht `NULL` ist

### `room_types`

Attribute sind `id`, `type_code`, `name`, `capacity` und `standard_price_cents`. `id` ist der Primary Key. `type_code` und `name` sind jeweils eindeutige Alternativschlüssel. Kapazität und Standardpreis haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> type_code, name, capacity, standard_price_cents`
- `type_code -> id, name, capacity, standard_price_cents`
- `name -> id, type_code, capacity, standard_price_cents`

### `rooms`

Attribute sind `floor`, `room_number`, `room_type_id`, `cleaning_area`, `status` und `accessible`. `floor` und `room_number` bilden den Primary Key. `room_type_id` verweist auf `room_types.id`. Die Kombination aus `room_number` und `cleaning_area` ist ein Alternativschlüssel. Status und Reinigungsbereich haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `(floor, room_number) -> room_type_id, cleaning_area, status, accessible`
- `(room_number, cleaning_area) -> floor, room_type_id, status, accessible`
- `floor -> cleaning_area`

Die letzte Abhängigkeit ist die BCNF-Falle. `floor` ist kein Superschlüssel. `cleaning_area` ist aber ein Primattribut des Alternativschlüssels `(room_number, cleaning_area)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `floor_cleaning_areas(floor, cleaning_area)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Modell bei sechs Kernentitäten bleibt.

`rooms` ist der Kandidat für eine schwache Entität. Eine Zimmernummer ist nur zusammen mit der Etage eindeutig. Ein Modell mit einer zusätzlichen Entität `HotelBuilding` könnte das Zimmer über Gebäude, Etage und Zimmernummer identifizieren.

### `employees`

Attribute sind `id`, `employee_code`, `first_name`, `last_name`, `role`, `hired_on` und `active`. `id` ist der Primary Key, `employee_code` ein eindeutiger Alternativschlüssel. Rolle und Aktivstatus haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `id -> employee_code, first_name, last_name, role, hired_on, active`
- `employee_code -> id, first_name, last_name, role, hired_on, active`

### `services`

Attribute sind `id`, `service_code`, `name`, `list_price_cents` und `active`. `id` ist der Primary Key. `service_code` und `name` sind jeweils eindeutige Alternativschlüssel. Der Listenpreis darf null, aber nicht negativ sein.

Funktionale Abhängigkeiten:

- `id -> service_code, name, list_price_cents, active`
- `service_code -> id, name, list_price_cents, active`
- `name -> id, service_code, list_price_cents, active`

### `bookings`

Attribute sind `id`, `booking_number`, `guest_id`, `checked_in_by_employee_id`, `booked_on`, `arrival_on`, `departure_on` und `status`. `id` ist der Primary Key, `booking_number` ein eindeutiger Alternativschlüssel. `guest_id` verweist auf `guests.id`. `checked_in_by_employee_id` ist optional und verweist auf `employees.id`. Buchungsstatus und Datumsfolge werden geprüft.

Funktionale Abhängigkeiten:

- `id -> booking_number, guest_id, checked_in_by_employee_id, booked_on, arrival_on, departure_on, status`
- `booking_number -> id, guest_id, checked_in_by_employee_id, booked_on, arrival_on, departure_on, status`

### `booking_rooms`

Attribute sind `booking_id`, `floor`, `room_number`, `check_in_on`, `check_out_on` und `nightly_price_cents`. Der Primary Key besteht aus `booking_id`, `floor` und `room_number`. `booking_id` verweist auf `bookings.id`. `floor` und `room_number` verweisen gemeinsam auf `rooms`. Der gespeicherte Nachtpreis bleibt erhalten, wenn sich der Standardpreis später ändert.

Funktionale Abhängigkeit:

- `(booking_id, floor, room_number) -> check_in_on, check_out_on, nightly_price_cents`

### `booking_services`

Attribute sind `booking_id`, `service_id`, `service_on`, `quantity` und `unit_price_cents`. Der Primary Key besteht aus `booking_id`, `service_id` und `service_on`. `booking_id` verweist auf `bookings.id`, `service_id` auf `services.id`. Menge, Leistungsdatum und berechneter Einzelpreis gehören zur Beziehung.

Funktionale Abhängigkeit:

- `(booking_id, service_id, service_on) -> quantity, unit_price_cents`

## REST-Endpunkte

| Methode  | Pfad                               | Erfolg           | Weitere Statuscodes                                |
| -------- | ---------------------------------- | ---------------- | -------------------------------------------------- |
| `GET`    | `/bookings`                        | `200 OK`         | keine                                              |
| `GET`    | `/bookings/{id}`                   | `200 OK`         | `404 Not Found`                                    |
| `POST`   | `/bookings`                        | `201 Created`    | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT`    | `/bookings/{id}`                   | `200 OK`         | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/bookings/{id}`                   | `204 No Content` | `404 Not Found`, `409 Conflict`                    |
| `GET`    | `/bookings/{id}/check-in-employee` | `200 OK`         | `404 Not Found`                                    |

## Annahmen zu den offenen Fragen

Die geplante Anreise und Abreise gehören zur Buchung. Tatsächlicher Check-in und Check-out werden pro Zimmer in `booking_rooms` gespeichert, weil Zimmer derselben Buchung an verschiedenen Tagen bezogen werden können.

Das Referenzmodell identifiziert ein Zimmer mit Etage und Zimmernummer. Eine nackte Zimmernummer aus dem alten Rezeptionsbuch reicht nicht aus. Bei mehreren Gebäuden müsste zusätzlich ein Gebäudeschlüssel in den zusammengesetzten Schlüssel aufgenommen werden.

Firmenname und Firmenkundennummer bleiben im Kursmodell optionale Gastattribute. Der Text nennt keine eigenen Ansprechpartner oder Abrechnungsregeln, die eine Entität für Firmenkonten rechtfertigen würden. Ein begründetes Modell mit eigener Entität ist ebenfalls möglich.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
