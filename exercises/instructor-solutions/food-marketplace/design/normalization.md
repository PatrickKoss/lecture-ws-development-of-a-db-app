# Normalisierung

Der Bestellexport hat den Schlüssel `(order_number, position_number)` und enthält Kunde, Restaurant, Kurier, Gericht, Position und Bewertung.

```text
order_number -> customer_number, partner_number, courier_number, ordered_at, order_type, order_status
customer_number -> customer_name, email, registered_on
partner_number -> restaurant_name, address, commission_rate
courier_number -> courier_name, phone, vehicle_type
(order_number, position_number) -> dish_id, quantity, unit_price
dish_id -> partner_number, dish_name, category, vat_rate, current_price
order_number -> rating, comment, reviewed_on
```

Bestellkopf und Gerichtsdaten hängen nur von Teilen des zusammengesetzten Schlüssels ab. Restaurant und Kunde hängen transitiv über ihre Nummern ab. Wird der Name eines Restaurants geändert, müsste der Export jede Position jeder Bestellung ändern.

Beim Abtrennen von `customers(customer_number, ...)` ist `customer_number` die Schnittmenge mit der verbleibenden Exportrelation und bestimmt die Kundenprojektion. Der Schritt ist verlustfrei. Das Verfahren wird für Restaurants, Kuriere, Gerichte, Bestellungen, Positionen und Bewertungen mit deren Determinanten wiederholt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage gilt für vorhandene Bestellpositionen. Ein Restaurant ohne Bestellung und ein Gericht ohne verkaufte Position lassen sich aus dem Export nicht rekonstruieren, bleiben im A2-Modell aber eigenständig speicherbar. Eine noch nicht zugewiesene Lieferung wird über das nullable `courier_id` und einen LEFT JOIN dargestellt.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

In `dishes` gilt als Lehrannahme `category -> vat_rate`. `category` ist kein Superschlüssel, `vat_rate` ist aber Teil des Alternativschlüssels `(name, vat_rate)`. Damit erfüllt `dishes` 3NF, aber nicht BCNF. Die BCNF-Fassung trennt `category_vat_rates(category, vat_rate)` ab.
