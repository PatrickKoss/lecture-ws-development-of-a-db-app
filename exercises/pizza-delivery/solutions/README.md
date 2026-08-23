# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs eigenständige Entitäten. `order_items` setzt die Beziehung zwischen Bestellung und Pizza um und ist zugleich der Kandidat für eine schwache Entität. `pizza_toppings` setzt die Beziehung zwischen Pizza und Belag um.

### `customers`

Attribute sind `id`, `customer_number`, `first_name`, `last_name`, `email`, `phone`, `created_on` und `active`. `id` ist der Primary Key. `customer_number`, `email` und `phone` sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> customer_number, first_name, last_name, email, phone, created_on, active`
- `customer_number -> id, first_name, last_name, email, phone, created_on, active`
- `email -> id, customer_number, first_name, last_name, phone, created_on, active`
- `phone -> id, customer_number, first_name, last_name, email, created_on, active`

### `addresses`

Attribute sind `id`, `customer_id`, `label`, `street`, `house_number`, `postal_code` und `city`. `id` ist der Primary Key. `customer_id` verweist auf `customers.id`. Kunde und Bezeichnung bilden zusammen einen Alternativschlüssel.

Funktionale Abhängigkeiten:

- `id -> customer_id, label, street, house_number, postal_code, city`
- `(customer_id, label) -> id, street, house_number, postal_code, city`

### `drivers`

Attribute sind `id`, `driver_number`, `first_name`, `last_name`, `phone`, `hired_on` und `active`. `id` ist der Primary Key. `driver_number` und `phone` sind jeweils eindeutige Alternativschlüssel. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> driver_number, first_name, last_name, phone, hired_on, active`
- `driver_number -> id, first_name, last_name, phone, hired_on, active`
- `phone -> id, driver_number, first_name, last_name, hired_on, active`

### `pizzas`

Attribute sind `id`, `pizza_number`, `name`, `category`, `oven_station`, `base_price` und `active`. `id` ist der Primary Key. `pizza_number` sowie die Kombination aus `name` und `oven_station` sind Alternativschlüssel. Preis, Textlängen und Aktivstatus haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> pizza_number, name, category, oven_station, base_price, active`
- `pizza_number -> id, name, category, oven_station, base_price, active`
- `(name, oven_station) -> id, pizza_number, category, base_price, active`
- `category -> oven_station`

Die letzte Abhängigkeit ist die BCNF-Falle. `category` ist kein Superschlüssel. `oven_station` ist aber ein Primattribut des Alternativschlüssels `(name, oven_station)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `category_oven_stations(category, oven_station)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit es bei den sechs eigenständigen Entitäten bleibt.

### `toppings`

Attribute sind `id`, `name`, `vegetarian` und `allergen`. `id` ist der Primary Key. `name` ist ein eindeutiger Alternativschlüssel. `vegetarian` ist auf 0 oder 1 beschränkt, `allergen` darf fehlen.

Funktionale Abhängigkeiten:

- `id -> name, vegetarian, allergen`
- `name -> id, vegetarian, allergen`

### `pizza_toppings`

Attribute sind `pizza_id`, `topping_id` und `extra_charge`. Der Primary Key besteht aus `pizza_id` und `topping_id`. `pizza_id` verweist auf `pizzas.id`, `topping_id` auf `toppings.id`. Der Aufpreis darf 0 sein, aber nicht negativ.

Funktionale Abhängigkeiten:

- `(pizza_id, topping_id) -> extra_charge`

### `orders`

Attribute sind `id`, `order_number`, `customer_id`, `delivery_address_id`, `driver_id`, `ordered_on`, `order_type`, `status` und `delivery_fee`. `id` ist der Primary Key. `order_number` ist ein eindeutiger Alternativschlüssel. `customer_id` verweist auf `customers.id`. `delivery_address_id` verweist zusammen mit `customer_id` auf eine Adresse dieses Kunden. `driver_id` ist optional und verweist auf `drivers.id`. Abholungen haben weder Lieferadresse noch Fahrer.

Funktionale Abhängigkeiten:

- `id -> order_number, customer_id, delivery_address_id, driver_id, ordered_on, order_type, status, delivery_fee`
- `order_number -> id, customer_id, delivery_address_id, driver_id, ordered_on, order_type, status, delivery_fee`

### `order_items`

Attribute sind `order_id`, `position_number`, `pizza_id`, `quantity` und `unit_price`. `order_id` und `position_number` bilden den Primary Key. `order_id` verweist auf `orders.id`, `pizza_id` auf `pizzas.id`. Bestellung und Pizza bilden zusammen einen Alternativschlüssel, weil eine Pizzasorte pro Bestellung nur einmal vorkommt.

Funktionale Abhängigkeiten:

- `(order_id, position_number) -> pizza_id, quantity, unit_price`
- `(order_id, pizza_id) -> position_number, quantity, unit_price`

`order_items` ist der Kandidat für eine schwache Entität. Die Positionsnummer beginnt bei jeder Bestellung wieder bei 1. Die Tabelle löst zugleich die n:m-Beziehung zwischen Bestellung und Pizza auf. Menge und Einzelpreis zum Bestellzeitpunkt sind Attribute dieser Beziehung.

## REST-Endpunkte

| Methode | Pfad | Erfolg | Weitere Statuscodes |
| --- | --- | --- | --- |
| `GET` | `/orders` | `200 OK` | keine |
| `GET` | `/orders/{id}` | `200 OK` | `404 Not Found` |
| `POST` | `/orders` | `201 Created` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT` | `/orders/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/orders/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` |
| `GET` | `/orders/{id}/driver` | `200 OK` | `404 Not Found` |

## Annahmen zu den offenen Fragen

Die Referenzlösung erlaubt mehrere Adressen pro Kunde. Eine kurze Bezeichnung wie "Zuhause" oder "Büro" unterscheidet sie. Eine Adresse gehört genau einem Kunden.

Eine Abholbestellung hat weder Lieferadresse noch Fahrer. Lieferbestellungen brauchen eine Adresse, dürfen aber ohne Fahrer angelegt werden, solange der Chef die Tour noch nicht zugeteilt hat.

Jede Bestellposition speichert ihren Einzelpreis. Eine spätere Änderung des Grundpreises oder eines Belagaufpreises verändert alte Bestellungen und deren Umsatz nicht.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
