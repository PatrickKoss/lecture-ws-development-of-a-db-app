# Normalisierung

Der Buchungsexport hat den Schlüssel `(booking_number, floor, room_number, service_code, service_on)` und mischt Gast, Zimmer, Zimmertyp, Mitarbeiter und Leistungen.

```text
booking_number -> guest_number, checked_in_by_employee_code, booked_on, arrival_on, departure_on, status
guest_number -> guest_name, email, phone, company_account_number
(floor, room_number) -> type_code, cleaning_area, room_status, accessible
type_code -> type_name, capacity, standard_price_cents
employee_code -> employee_name, role, hired_on
(booking_number, floor, room_number) -> check_in_on, check_out_on, nightly_price_cents
service_code -> service_name, list_price_cents, active
(booking_number, service_code, service_on) -> quantity, unit_price_cents
```

Zimmer und Leistung hängen nur von Teilen des Exportschlüssels ab. Gast und Zimmertyp hängen transitiv. Eine Änderung des Standardpreises müsste sonst jede alte Buchungszeile ändern und würde historische Preise verfälschen.

Beim Abtrennen von `guests(guest_number, ...)` ist `guest_number` die Schnittmenge mit der verbleibenden Relation und bestimmt die gesamte Gästeprojektion. Dieser Schritt ist verlustfrei. Zimmertypen, Zimmer, Mitarbeiter, Buchungen und Leistungen werden danach jeweils über den links genannten Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage gilt für vorhandene Buchungsexportzeilen. Ein Zimmer ohne Buchung und eine Leistung ohne Verwendung sind daraus nicht ableitbar, bleiben im A2-Modell aber eigenständige Stammdaten. Ein noch nicht erfolgter Check-in wird mit nullable `checked_in_by_employee_id` und einem LEFT JOIN rekonstruiert.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

Für die Zusatzprüfung gilt `floor -> cleaning_area` in `rooms`. `floor` ist kein Superschlüssel, `cleaning_area` ist aber Teil des Alternativschlüssels `(room_number, cleaning_area)`. Die Tabelle erfüllt 3NF, aber nicht BCNF. Eine BCNF-Fassung trennt `floor_cleaning_areas(floor, cleaning_area)` ab.
