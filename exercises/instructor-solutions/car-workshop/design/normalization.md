# Normalisierung

Der Werkstattexport hat den Schlüssel `(order_number, personnel_number, line_number)` und mischt Auftrag, Fahrzeug, Kunde, Mechaniker, Teileposition und Rechnung.

```text
order_number -> vehicle_id, opened_on, closed_on, status, mileage_in, complaint
vehicle_id -> owner_id, previous_owner_id, licence_plate, vin, manufacturer, model
customer_number -> customer_name, email, phone
personnel_number -> mechanic_name, specialization, hourly_rate
(order_number, personnel_number) -> hours_worked
(order_number, line_number) -> part_number, quantity, unit_price
part_number -> part_name, category, shelf_code, stock_quantity, reorder_level, list_price
invoice_number -> order_number, issued_on, due_on, paid_on, net_amount, tax_amount, invoice_status
```

Mechaniker- und Teiledaten hängen nur von Teilen des Schlüssels ab. Fahrzeug und Kunde hängen transitiv vom Auftrag ab. Wird der Lagerort eines Ersatzteils geändert, müsste jede frühere Auftragszeile angepasst werden. Das wäre fachlich falsch und leicht widersprüchlich.

Die Zerlegung trennt beispielsweise `parts(part_number, ...)` ab. `part_number` ist die Schnittmenge mit der verbleibenden Exportrelation und bestimmt die gesamte Teileprojektion. Dieser binäre Schritt ist deshalb verlustfrei. Für Mechaniker, Fahrzeuge, Kunden, Aufträge und Rechnungen wird derselbe Schritt mit dem jeweiligen Determinanten wiederholt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Verlustfreiheitsaussage gilt für die vorhandenen Auftragszeilen. Ein Ersatzteil ohne Verwendung oder ein Mechaniker ohne Auftrag steht nicht im Export. Solche Stammdaten bleiben im A2-Modell unabhängig speicherbar. Der optionale frühere Halter und die optionale Rechnung werden über nullable Foreign Keys beziehungsweise LEFT JOINs rekonstruiert.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

Für die Zusatzprüfung gilt `category -> shelf_code` in `parts`. `category` ist kein Superschlüssel. Weil `shelf_code` Teil des Alternativschlüssels `(name, shelf_code)` ist, erfüllt die Tabelle 3NF, aber nicht BCNF. `category_shelves(category, shelf_code)` wäre die BCNF-Zerlegung.
