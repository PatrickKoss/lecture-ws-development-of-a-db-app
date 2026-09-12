# Normalisierung

Der Bestellexport hat den Schlüssel `(order_number, position_number, topping_id)` und enthält Kunde, Adresse, Fahrer, Pizza, Beläge und Bestellposition.

```text
order_number -> customer_number, delivery_address_id, driver_number, ordered_on, order_type, status, delivery_fee
customer_number -> customer_name, email, phone
address_id -> customer_number, label, street, house_number, postal_code, city
driver_number -> driver_name, phone, hired_on, active
(order_number, position_number) -> pizza_number, quantity, unit_price
pizza_number -> pizza_name, category, oven_station, base_price, active
topping_id -> topping_name, vegetarian, allergen
(pizza_number, topping_id) -> extra_charge
```

Bestellposition und Belag hängen nur von Teilen des Exportschlüssels ab. Kunde, Adresse und Fahrer hängen transitiv von der Bestellung ab. Ändert sich die Telefonnummer eines Fahrers, müsste jede alte Bestellung im Export geändert werden.

Die Zerlegung trennt `customers(customer_number, ...)` ab. `customer_number` ist die Schnittmenge mit der Restrelation und bestimmt die gesamte Kundenprojektion. Dieser Schritt ist verlustfrei. Adressen, Fahrer, Pizzen, Beläge, Bestellungen und Positionen werden danach jeweils mit dem links genannten Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage gilt für vorhandene Bestellexportzeilen. Eine Pizza ohne Bestellung oder ein Fahrer ohne Tour lässt sich daraus nicht rekonstruieren, bleibt im A2-Modell aber unabhängig speicherbar. Abholungen und noch nicht zugewiesene Lieferungen werden über nullable Foreign Keys und LEFT JOINs dargestellt.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

In `pizzas` gilt als Lehrannahme `category -> oven_station`. `category` ist kein Superschlüssel, `oven_station` ist aber Teil des Alternativschlüssels `(name, oven_station)`. Daher liegt 3NF, aber keine BCNF vor. `category_oven_stations(category, oven_station)` wäre die BCNF-Zerlegung.
