# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs eigenständige Entitäten. `order_items` setzt die Beziehung zwischen Bestellung und Gericht als Zuordnungstabelle um und ist zugleich der Kandidat für eine schwache Entität.

### `restaurants`

Attribute sind `id`, `partner_number`, `name`, `street`, `postal_code`, `city`, `commission_rate` und `active`. `id` ist der Primary Key. `partner_number` ist ein eindeutiger Alternativschlüssel. Provisionssatz, Postleitzahl und Textlängen haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> partner_number, name, street, postal_code, city, commission_rate, active`
- `partner_number -> id, name, street, postal_code, city, commission_rate, active`

### `dishes`

Attribute sind `id`, `restaurant_id`, `name`, `category`, `vat_rate`, `current_price` und `active`. `id` ist der Primary Key. `restaurant_id` verweist auf `restaurants.id`. Restaurant und Gerichtsname sowie Gerichtsname und Mehrwertsteuersatz bilden jeweils einen Alternativschlüssel. Kategorie, Mehrwertsteuersatz und Preis haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> restaurant_id, name, category, vat_rate, current_price, active`
- `(restaurant_id, name) -> id, category, vat_rate, current_price, active`
- `(name, vat_rate) -> id, restaurant_id, category, current_price, active`
- `category -> vat_rate`

Die letzte Abhängigkeit ist die BCNF-Falle. `category` ist kein Superschlüssel. `vat_rate` ist aber ein Primattribut des Alternativschlüssels `(name, vat_rate)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `category_vat_rates(category, vat_rate)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Schema bei den sechs fachlichen Entitäten bleibt.

### `customers`

Attribute sind `id`, `customer_number`, `first_name`, `last_name`, `email`, `registered_on` und `active`. `id` ist der Primary Key. `customer_number` und `email` sind jeweils eindeutige Alternativschlüssel. Das Registrierungsdatum ist als ISO-Datum gespeichert.

Funktionale Abhängigkeiten:

- `id -> customer_number, first_name, last_name, email, registered_on, active`
- `customer_number -> id, first_name, last_name, email, registered_on, active`
- `email -> id, customer_number, first_name, last_name, registered_on, active`

### `couriers`

Attribute sind `id`, `courier_number`, `first_name`, `last_name`, `phone`, `vehicle_type` und `active`. `id` ist der Primary Key. `courier_number` und `phone` sind jeweils eindeutige Alternativschlüssel. Fahrzeugart und Aktivstatus haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `id -> courier_number, first_name, last_name, phone, vehicle_type, active`
- `courier_number -> id, first_name, last_name, phone, vehicle_type, active`
- `phone -> id, courier_number, first_name, last_name, vehicle_type, active`

### `orders`

Attribute sind `id`, `order_number`, `customer_id`, `restaurant_id`, `courier_id`, `ordered_at`, `order_type`, `status`, `picked_up_at` und `delivered_at`. `id` ist der Primary Key. `order_number` ist ein eindeutiger Alternativschlüssel. `customer_id`, `restaurant_id` und das optionale `courier_id` sind Foreign Keys. Abhol- und Lieferzeit sind Attribute der optionalen Kurierzuordnung. Constraints verhindern Kurierzeiten ohne Kurier, Kurierdaten bei Abholbestellungen und eine Lieferzeit vor der Abholzeit.

Funktionale Abhängigkeiten:

- `id -> order_number, customer_id, restaurant_id, courier_id, ordered_at, order_type, status, picked_up_at, delivered_at`
- `order_number -> id, customer_id, restaurant_id, courier_id, ordered_at, order_type, status, picked_up_at, delivered_at`

### `order_items`

Attribute sind `order_id`, `position_number`, `dish_id`, `quantity` und `unit_price`. `order_id` und `position_number` bilden den Primary Key. `order_id` verweist auf `orders.id`, `dish_id` auf `dishes.id`. Bestellung und Gericht bilden einen Alternativschlüssel. Menge und Stückpreis speichern den Zustand zum Bestellzeitpunkt.

Funktionale Abhängigkeiten:

- `(order_id, position_number) -> dish_id, quantity, unit_price`
- `(order_id, dish_id) -> position_number, quantity, unit_price`

`order_items` ist der Kandidat für eine schwache Entität. Die Positionsnummer ist nur innerhalb einer Bestellung eindeutig. Die Tabelle löst zugleich die n:m-Beziehung zwischen Bestellung und Gericht auf.

### `reviews`

Attribute sind `id`, `order_id`, `rating`, `comment` und `reviewed_on`. `id` ist der Primary Key. `order_id` ist ein eindeutiger Foreign Key auf `orders.id`. Dadurch kann eine Bestellung höchstens eine Bewertung haben. Der Kommentar ist optional, die Bewertung liegt zwischen 1 und 5.

Funktionale Abhängigkeiten:

- `id -> order_id, rating, comment, reviewed_on`
- `order_id -> id, rating, comment, reviewed_on`

## REST-Endpunkte

| Methode | Pfad | Erfolg | Weitere Statuscodes |
| --- | --- | --- | --- |
| `GET` | `/orders` | `200 OK` | keine |
| `GET` | `/orders/{id}` | `200 OK` | `404 Not Found` |
| `POST` | `/orders` | `201 Created` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT` | `/orders/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/orders/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` |
| `GET` | `/orders/{id}/review` | `200 OK` | `404 Not Found` |

## Annahmen zu den offenen Fragen

Die Referenzlösung beschränkt jede Bestellung auf genau ein Restaurant. Ein gemeinsamer Warenkorb kann vor dem Speichern in mehrere Bestellungen geteilt werden. Eine Bestellung über mehrere Restaurants wäre ebenfalls möglich, würde aber die direkte Zuordnung `orders.restaurant_id` und die Provisionsberechnung verändern.

Abholbestellungen haben keinen Kurier. Bei Lieferbestellungen ist die Kurierzuordnung optional, weil die Disposition sie erst nach Bestelleingang vornehmen kann. Abhol- und Lieferzeit bleiben an der Bestellung, weil in diesem Kursmodell höchstens ein Kurier eine Bestellung übernimmt.

Eine Bewertung gilt für die gesamte Bestellung und damit für das Restaurant. Das Kursmodell speichert keine getrennte Gericht- oder Kurierbewertung. Eine solche Lösung ist fachlich vertretbar, braucht aber weitere Beziehungen.

Bestellpositionen speichern den Stückpreis zum Bestellzeitpunkt. Der Provisionssatz liegt im Kursmodell am Restaurant. Wer historische Provisionsänderungen abrechnungssicher aufbewahren will, muss den Satz zusätzlich an der Bestellung speichern oder eine zeitlich gültige Provisionshistorie modellieren.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
