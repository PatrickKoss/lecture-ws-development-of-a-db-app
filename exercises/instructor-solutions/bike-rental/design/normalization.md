# Normalisierung

Ausgangspunkt ist ein Export mit dem Schlüssel `(rental_number, maintenance_sequence, tariff_valid_from)`. Er enthält Ausleihe, Kunde, Tarif, Rad, Radmodell, Station und Wartung in einer Zeile.

```text
rental_number -> customer_number, bike_number, start_station_code, end_station_code, start_time, end_time, price_cents
customer_number -> customer_name, email
bike_number -> model_code, current_station_code, bike_status
model_code -> manufacturer, model_name, category, service_interval_days
station_code -> station_name, address, capacity, station_status
(customer_number, tariff_valid_from) -> tariff_code
tariff_code -> tariff_name, base_fee_cents, minute_price_cents
(bike_number, maintenance_sequence) -> logged_on, issue, action_taken, cost_cents
```

Die Rad- und Kundendaten hängen nur von Teilen des Exportschlüssels ab. Stations- und Tarifdaten hängen transitiv über ihre Codes ab. Ändert sich die Kapazität der Station `DOM`, müsste der Export jede Ausleihe dieser Station ändern. Eine ausgelassene Zeile ergäbe zwei Kapazitäten.

Die Zerlegung trennt zuerst `stations(station_code, ...)` ab. `station_code` ist die Schnittmenge mit der Restrelation und bestimmt alle Attribute der Stationsprojektion. Nach dem Kriterium für binäre Zerlegungen ist dieser Schritt verlustfrei. Dasselbe gilt nacheinander für Radmodelle, Tarife, Kunden, Räder, Wartungen und Ausleihen, jeweils mit dem links in den Abhängigkeiten genannten Determinanten.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage gilt für vorhandene Exportzeilen. Ein Rad ohne Ausleihe oder ein Kunde ohne Tarifzuordnung ist im Export nicht ableitbar, bleibt aber als eigenständige Zeile im A2-Modell erhalten. Optionale Endstationen und Tarifzuordnungen werden mit nullable Foreign Keys beziehungsweise fehlenden Zuordnungszeilen und LEFT JOINs dargestellt.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

`bike_models` verletzt unter der Kursannahme `category -> service_interval_days` die BCNF. `category` ist kein Superschlüssel, `service_interval_days` ist aber Teil des Alternativschlüssels `(model_name, service_interval_days)`. Die 3NF-Fassung bleibt bestehen. Eine BCNF-Variante trennt `category_intervals(category, service_interval_days)` ab.
