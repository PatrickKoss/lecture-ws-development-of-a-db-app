# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell trennt die fachlichen Entitäten von den Beziehungstabellen. `work_order_parts` setzt die Beziehung zwischen Arbeitsauftrag und Ersatzteil um. `work_order_mechanics` setzt die Beziehung zwischen Arbeitsauftrag und Mechaniker um.

### `customers`

Attribute sind `id`, `customer_number`, `first_name`, `last_name`, `email`, `phone`, `registered_on` und `active`. `id` ist der Primary Key. `customer_number` und `email` sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> customer_number, first_name, last_name, email, phone, registered_on, active`
- `customer_number -> id, first_name, last_name, email, phone, registered_on, active`
- `email -> id, customer_number, first_name, last_name, phone, registered_on, active`

### `vehicles`

Attribute sind `id`, `owner_id`, `previous_owner_id`, `licence_plate`, `vin`, `manufacturer`, `model`, `construction_year` und `mileage`. `id` ist der Primary Key. `owner_id` verweist auf `customers.id`. `previous_owner_id` ist optional und verweist ebenfalls auf `customers.id`. Kennzeichen und Fahrgestellnummer sind Alternativschlüssel. Das Schema sichert zusätzlich die Kombination aus aktuellem Halter und Kennzeichen ab.

Funktionale Abhängigkeiten:

- `id -> owner_id, previous_owner_id, licence_plate, vin, manufacturer, model, construction_year, mileage`
- `licence_plate -> id, owner_id, previous_owner_id, vin, manufacturer, model, construction_year, mileage`
- `vin -> id, owner_id, previous_owner_id, licence_plate, manufacturer, model, construction_year, mileage`

### `mechanics`

Attribute sind `id`, `personnel_number`, `first_name`, `last_name`, `specialization`, `hourly_rate` und `active`. `id` ist der Primary Key. `personnel_number` ist ein Alternativschlüssel. Spezialisierung und Aktivkennzeichen haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `id -> personnel_number, first_name, last_name, specialization, hourly_rate, active`
- `personnel_number -> id, first_name, last_name, specialization, hourly_rate, active`

### `parts`

Attribute sind `id`, `part_number`, `name`, `category`, `shelf_code`, `stock_quantity`, `reorder_level` und `list_price`. `id` ist der Primary Key. `part_number` sowie die Kombination aus `name` und `shelf_code` sind Alternativschlüssel. Bestand, Meldebestand und Listenpreis dürfen nicht negativ sein.

Funktionale Abhängigkeiten:

- `id -> part_number, name, category, shelf_code, stock_quantity, reorder_level, list_price`
- `part_number -> id, name, category, shelf_code, stock_quantity, reorder_level, list_price`
- `(name, shelf_code) -> id, part_number, category, stock_quantity, reorder_level, list_price`
- `category -> shelf_code`

Die letzte Abhängigkeit ist die BCNF-Falle. `category` ist kein Superschlüssel. `shelf_code` ist aber ein Primattribut des Alternativschlüssels `(name, shelf_code)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `category_shelves(category, shelf_code)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Schema bei den sechs fachlichen Entitäten bleibt.

### `work_orders`

Attribute sind `id`, `order_number`, `vehicle_id`, `opened_on`, `closed_on`, `status`, `mileage_in` und `complaint`. `id` ist der Primary Key. `order_number` ist ein Alternativschlüssel. `vehicle_id` verweist auf `vehicles.id`. Abschlussdatum und Status müssen zusammenpassen. Ein partieller eindeutiger Index erlaubt pro Fahrzeug höchstens einen Auftrag mit dem Status `OPEN` oder `IN_PROGRESS`.

Funktionale Abhängigkeiten:

- `id -> order_number, vehicle_id, opened_on, closed_on, status, mileage_in, complaint`
- `order_number -> id, vehicle_id, opened_on, closed_on, status, mileage_in, complaint`

In der Ausgangstabelle gilt außerdem `Auftrag -> Kundennr. -> Kunde, E-Mail`. Das ist die transitive Abhängigkeit für die Zerlegung in 3NF. Der Weg vom Auftrag zum Kunden führt im normalisierten Modell über Fahrzeug und aktuellen Halter.

### `work_order_mechanics`

Attribute sind `work_order_id`, `mechanic_id` und `hours_worked`. Der Primary Key besteht aus `work_order_id` und `mechanic_id`. `work_order_id` verweist auf `work_orders.id`, `mechanic_id` auf `mechanics.id`.

Funktionale Abhängigkeiten:

- `(work_order_id, mechanic_id) -> hours_worked`

### `work_order_parts`

Attribute sind `work_order_id`, `line_number`, `part_id`, `quantity` und `unit_price`. Der Primary Key besteht aus `work_order_id` und `line_number`. `work_order_id` verweist auf `work_orders.id`, `part_id` auf `parts.id`. Die Kombination aus Arbeitsauftrag und Ersatzteil ist eindeutig.

Funktionale Abhängigkeiten:

- `(work_order_id, line_number) -> part_id, quantity, unit_price`
- `(work_order_id, part_id) -> line_number, quantity, unit_price`

`work_order_parts` ist der Kandidat für eine schwache Entität. Die Positionsnummer beginnt bei jedem Arbeitsauftrag wieder bei 1. Menge und Einzelpreis zum Einbauzeitpunkt sind Attribute der n:m-Beziehung zwischen Arbeitsauftrag und Ersatzteil.

### `invoices`

Attribute sind `id`, `invoice_number`, `work_order_id`, `issued_on`, `due_on`, `paid_on`, `net_amount`, `tax_amount` und `status`. `id` ist der Primary Key. `invoice_number` und `work_order_id` sind jeweils Alternativschlüssel. `work_order_id` verweist auf `work_orders.id`. Die Eindeutigkeit des Foreign Keys begrenzt jeden Auftrag auf höchstens eine Rechnung.

Funktionale Abhängigkeiten:

- `id -> invoice_number, work_order_id, issued_on, due_on, paid_on, net_amount, tax_amount, status`
- `invoice_number -> id, work_order_id, issued_on, due_on, paid_on, net_amount, tax_amount, status`
- `work_order_id -> id, invoice_number, issued_on, due_on, paid_on, net_amount, tax_amount, status`

## Annahmen zu den offenen Fragen

Das Referenzmodell behandelt das Kennzeichen als werkstattweit eindeutig. Ein Halterwechsel ändert diese Regel nicht. Die Fahrgestellnummer bleibt der zweite eindeutige Alternativschlüssel. Der unmittelbar vorherige Halter steht als optionale Beziehung am Fahrzeug. Eine vollständige Halterhistorie gehört nicht zum Kursmodell.

Die geleisteten Stunden werden je Mechaniker und Arbeitsauftrag als Summe gespeichert. Eine tageweise Zeiterfassung ist ebenfalls vertretbar, braucht aber eine eigene Zeitbuchung mit Datum.

Die Rechnung entsteht erst, wenn der Arbeitsauftrag abgeschlossen ist. Ein abgeschlossener Auftrag darf kurzzeitig noch keine Rechnung haben. Offene Aufträge haben nie eine Rechnung.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
