# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell hat sechs eigenständige Entitätstypen. `rentals` setzt die attributtragende Beziehung zwischen Kunde und Rad um. `customer_tariffs` hält die ebenfalls attributtragende Tarifzuordnung fest.

### `stations`

Attribute sind `id`, `station_code`, `name`, `address`, `capacity` und `status`. `id` ist der Primary Key. Stationscode, Name und Adresse sind jeweils eindeutige Alternativschlüssel. Kapazität und Status haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> station_code, name, address, capacity, status`
- `station_code -> id, name, address, capacity, status`
- `name -> id, station_code, address, capacity, status`
- `address -> id, station_code, name, capacity, status`

### `bike_models`

Attribute sind `id`, `model_code`, `manufacturer`, `model_name`, `category` und `service_interval_days`. `id` ist der Primary Key. `model_code` sowie die Kombination aus `model_name` und `service_interval_days` sind Alternativschlüssel. Kategorie und Wartungsintervall haben Wertebereichsprüfungen.

Funktionale Abhängigkeiten:

- `id -> model_code, manufacturer, model_name, category, service_interval_days`
- `model_code -> id, manufacturer, model_name, category, service_interval_days`
- `(model_name, service_interval_days) -> id, model_code, manufacturer, category`
- `category -> service_interval_days`

Die letzte Abhängigkeit ist die BCNF-Falle. `category` ist kein Superschlüssel. `service_interval_days` ist aber ein Primattribut des Alternativschlüssels `(model_name, service_interval_days)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `category_intervals(category, service_interval_days)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Modell dieselben Fachbegriffe wie die Aufgabenstellung verwendet.

### `tariffs`

Attribute sind `id`, `tariff_code`, `name`, `base_fee_cents`, `minute_price_cents` und `active`. `id` ist der Primary Key. Tarifcode und Name sind jeweils eindeutige Alternativschlüssel. Gebühren sind nicht negativ, `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> tariff_code, name, base_fee_cents, minute_price_cents, active`
- `tariff_code -> id, name, base_fee_cents, minute_price_cents, active`
- `name -> id, tariff_code, base_fee_cents, minute_price_cents, active`

### `customers`

Attribute sind `id`, `customer_number`, `first_name`, `last_name`, `email`, `registered_on` und `active`. `id` ist der Primary Key. Kundennummer und E-Mail-Adresse sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder.

Funktionale Abhängigkeiten:

- `id -> customer_number, first_name, last_name, email, registered_on, active`
- `customer_number -> id, first_name, last_name, email, registered_on, active`
- `email -> id, customer_number, first_name, last_name, registered_on, active`

### `customer_tariffs`

Attribute sind `customer_id`, `tariff_id` und `valid_from`. Der Primary Key besteht aus `customer_id` und `valid_from`. `customer_id` verweist auf `customers.id`, `tariff_id` auf `tariffs.id`. Fehlt für einen Kunden jede Zeile, fährt er ohne Tarif. Bei mehreren Zeilen gilt zu einem Zeitpunkt die jüngste bereits begonnene Zuordnung.

Funktionale Abhängigkeiten:

- `(customer_id, valid_from) -> tariff_id`

### `bikes`

Attribute sind `id`, `bike_number`, `model_id`, `current_station_id`, `status` und `commissioned_on`. `id` ist der Primary Key. `bike_number` ist ein eindeutiger Alternativschlüssel. `model_id` verweist auf `bike_models.id`, `current_station_id` optional auf `stations.id`. Verfügbare Räder brauchen eine Station, ausgeliehene Räder haben keine aktuelle Station.

Funktionale Abhängigkeiten:

- `id -> bike_number, model_id, current_station_id, status, commissioned_on`
- `bike_number -> id, model_id, current_station_id, status, commissioned_on`

### `maintenance_logs`

Attribute sind `bike_id`, `sequence_number`, `logged_on`, `issue`, `action_taken` und `cost_cents`. `bike_id` und `sequence_number` bilden den Primary Key. `bike_id` verweist auf `bikes.id`. Kosten dürfen nicht negativ sein.

Funktionale Abhängigkeiten:

- `(bike_id, sequence_number) -> logged_on, issue, action_taken, cost_cents`

`maintenance_logs` ist der Kandidat für eine schwache Entität. Die laufende Nummer ist nur innerhalb eines Rads eindeutig.

### `rentals`

Attribute sind `id`, `rental_number`, `customer_id`, `bike_id`, `start_station_id`, `end_station_id`, `start_time`, `end_time` und `price_cents`. `id` ist der Primary Key, `rental_number` ein eindeutiger Alternativschlüssel. Die drei IDs für Kunde, Rad und Startstation sind Pflicht-Foreign-Keys. Endzeit, Endstation und Preis sind gemeinsam optional. Ein partieller eindeutiger Index erlaubt pro Rad höchstens eine offene Ausleihe.

Funktionale Abhängigkeiten:

- `id -> rental_number, customer_id, bike_id, start_station_id, end_station_id, start_time, end_time, price_cents`
- `rental_number -> id, customer_id, bike_id, start_station_id, end_station_id, start_time, end_time, price_cents`

`rentals` löst die n:m-Beziehung zwischen Kunde und Rad auf. Start- und Endzeit, Start- und Endstation sowie Preis sind Attribute dieser Beziehung.

## REST-Endpunkte

| Methode | Pfad | Erfolg | Weitere Statuscodes |
| --- | --- | --- | --- |
| `GET` | `/rentals` | `200 OK` | keine |
| `GET` | `/rentals/{id}` | `200 OK` | `404 Not Found` |
| `POST` | `/rentals` | `201 Created` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `PUT` | `/rentals/{id}` | `200 OK` | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/rentals/{id}` | `204 No Content` | `404 Not Found`, `409 Conflict` |
| `GET` | `/rentals/{id}/tariff` | `200 OK` | `404 Not Found` |

## Annahmen zu den offenen Fragen

Während einer offenen Ausleihe hat das Rad keine aktuelle Station. Die Startstation bleibt an der Ausleihe gespeichert. Bei der Rückgabe erhält das Rad die Endstation als neuen Standort.

Tarifwechsel bleiben in `customer_tariffs` erhalten. Zu einer Ausleihe gilt die Zuordnung mit dem größten `valid_from`, das nicht nach dem Startdatum liegt. Kunden ohne passende Zuordnung fahren ohne Tarif.

Eine abgebrochene Fahrt bleibt offen, bis ein Mitarbeiter das Rad findet und die Ausleihe abschließt. Erst dann werden Endzeit, Endstation und Preis gemeinsam gesetzt. Eine Stornierung wäre eine mögliche Erweiterung mit einem eigenen Status.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
