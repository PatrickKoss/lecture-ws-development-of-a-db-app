# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs fachliche Entitätstypen. `tickets` setzt die Beziehung zwischen Bestellung und Ticketkategorie als Zuordnungstabelle mit eigenen Attributen um.

### `venues`

Attribute sind `id`, `venue_code`, `name`, `street`, `postal_code`, `city` und `capacity`. `id` ist der Primary Key. `venue_code` und `name` sind jeweils eindeutige Alternativschlüssel. Adresse und Kapazität sind Pflichtfelder, die Kapazität muss positiv sein.

Funktionale Abhängigkeiten:

- `id -> venue_code, name, street, postal_code, city, capacity`
- `venue_code -> id, name, street, postal_code, city, capacity`
- `name -> id, venue_code, street, postal_code, city, capacity`

### `organizers`

Attribute sind `id`, `organizer_number`, `name`, `contact_person`, `email` und `phone`. `id` ist der Primary Key. Veranstalternummer, Name und E-Mail-Adresse sind jeweils eindeutig. Das Kursmodell speichert genau eine Kontaktperson pro externem Veranstalter.

Funktionale Abhängigkeiten:

- `id -> organizer_number, name, contact_person, email, phone`
- `organizer_number -> id, name, contact_person, email, phone`
- `name -> id, organizer_number, contact_person, email, phone`
- `email -> id, organizer_number, name, contact_person, phone`

### `events`

Attribute sind `id`, `event_number`, `title`, `event_type`, `admission_code`, `venue_id`, `organizer_id`, `event_on`, `doors_open` und `starts_at`. `id` ist der Primary Key. `event_number`, die Kombination aus `title` und `admission_code` sowie die Kombination aus `venue_id`, `event_on` und `starts_at` sind Alternativschlüssel. `venue_id` verweist auf `venues.id`. `organizer_id` darf `NULL` sein und verweist sonst auf `organizers.id`. Die Startzeit darf nicht vor der Einlasszeit liegen.

Funktionale Abhängigkeiten:

- `id -> event_number, title, event_type, admission_code, venue_id, organizer_id, event_on, doors_open, starts_at`
- `event_number -> id, title, event_type, admission_code, venue_id, organizer_id, event_on, doors_open, starts_at`
- `(title, admission_code) -> id, event_number, event_type, venue_id, organizer_id, event_on, doors_open, starts_at`
- `(venue_id, event_on, starts_at) -> id, event_number, title, event_type, admission_code, organizer_id, doors_open`
- `event_type -> admission_code`

Die letzte Abhängigkeit ist die BCNF-Falle. `event_type` ist kein Superschlüssel. `admission_code` ist aber ein Primattribut des Alternativschlüssels `(title, admission_code)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `event_type_codes(event_type, admission_code)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen.

### `ticket_categories`

Attribute sind `event_id`, `name`, `list_price`, `quota` und `seating_type`. `event_id` und `name` bilden den Primary Key. `event_id` verweist auf `events.id`. Der Preis darf nicht negativ sein, das Kontingent muss positiv sein. Die Platzart hat eine feste Wertemenge.

Funktionale Abhängigkeiten:

- `(event_id, name) -> list_price, quota, seating_type`

`ticket_categories` ist der Kandidat für eine schwache Entität. Der Kategoriename ist nur innerhalb einer Veranstaltung eindeutig.

### `buyers`

Attribute sind `id`, `buyer_number`, `first_name`, `last_name`, `email` und `registered_on`. `id` ist der Primary Key. Kundennummer und E-Mail-Adresse sind jeweils eindeutige Alternativschlüssel. Das Registrierungsdatum wird als ISO-Datum gespeichert.

Funktionale Abhängigkeiten:

- `id -> buyer_number, first_name, last_name, email, registered_on`
- `buyer_number -> id, first_name, last_name, email, registered_on`
- `email -> id, buyer_number, first_name, last_name, registered_on`

### `orders`

Attribute sind `id`, `order_number`, `buyer_id`, `ordered_at` und `status`. `id` ist der Primary Key. `order_number` ist ein eindeutiger Alternativschlüssel. `buyer_id` verweist auf `buyers.id`. Bestellzeitpunkt und Status sind Pflichtfelder.

Funktionale Abhängigkeiten:

- `id -> order_number, buyer_id, ordered_at, status`
- `order_number -> id, buyer_id, ordered_at, status`

### `tickets`

Attribute sind `id`, `order_id`, `event_id`, `category_name`, `ticket_number`, `seat_label`, `price_paid` und `checked_in_at`. `id` ist der Primary Key. `order_id` verweist auf `orders.id`. `event_id` und `category_name` verweisen gemeinsam auf `ticket_categories`. `order_id` und `ticket_number` bilden einen Alternativschlüssel. Ein Sitzplatz darf fehlen, ist innerhalb einer Veranstaltung aber eindeutig. `checked_in_at` ist optional.

Funktionale Abhängigkeiten:

- `id -> order_id, event_id, category_name, ticket_number, seat_label, price_paid, checked_in_at`
- `(order_id, ticket_number) -> id, event_id, category_name, seat_label, price_paid, checked_in_at`
- `(event_id, seat_label) -> id, order_id, category_name, ticket_number, price_paid, checked_in_at`, sofern `seat_label` nicht `NULL` ist

`tickets` löst die n:m-Beziehung zwischen Bestellung und Ticketkategorie auf. Sitzplatz, bezahlter Preis und Check-in-Zeitpunkt sind Attribute dieser Beziehung.

## REST-Endpunkte

| Methode  | Pfad                     | Erfolg           | Weitere Statuscodes                                |
| -------- | ------------------------ | ---------------- | -------------------------------------------------- |
| `GET`    | `/events`                | `200 OK`         | keine                                              |
| `GET`    | `/events/{id}`           | `200 OK`         | `404 Not Found`                                    |
| `POST`   | `/events`                | `201 Created`    | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT`    | `/events/{id}`           | `200 OK`         | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/events/{id}`           | `204 No Content` | `404 Not Found`, `409 Conflict`                    |
| `GET`    | `/events/{id}/organizer` | `200 OK`         | `404 Not Found`                                    |

## Annahmen zu den offenen Fragen

Die Referenzlösung behandelt den Kategorienamen als innerhalb einer Veranstaltung eindeutig. Dadurch kann "Standard" bei mehreren Veranstaltungen vorkommen. Eine globale Kategorie braucht stattdessen eine eigene ID und eine weitere Zuordnung zur Veranstaltung.

Nur ausgegebene Tickets einer bezahlten Bestellung verbrauchen Kontingent. Unbezahlte Bestellungen erzeugen im Kursmodell noch kein Ticket. Reservierungen mit Ablaufzeit wären eine sinnvolle Erweiterung, gehören aber nicht zu dieser Aufgabe.

Eine Veranstaltung hat höchstens einen externen Veranstalter. Dessen Stammdatensatz nennt genau eine Kontaktperson. `organizer_id` bleibt leer, wenn "Ruhrpott Events" die Veranstaltung selbst betreut.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
