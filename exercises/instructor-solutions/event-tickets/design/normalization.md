# Normalisierung

Der Ticketexport verbindet Bestellung, Käufer, Ticket, Kategorie, Veranstaltung, Spielort und Veranstalter. Der Schlüssel lautet `(order_number, ticket_number)`, weil Ticketnummern laut Schema nur innerhalb einer Bestellung eindeutig sind.

```text
(order_number, ticket_number) -> event_number, category_name, seat_label, price_paid, checked_in_at
order_number -> buyer_number, ordered_at, order_status
buyer_number -> buyer_name, email, registered_on
event_number -> title, event_type, admission_code, venue_code, organizer_number, event_on, starts_at
venue_code -> venue_name, address, capacity
organizer_number -> organizer_name, contact_person, email, phone
(event_number, category_name) -> list_price, quota, seating_type
```

Käufer, Veranstaltung und Spielort hängen transitiv von der Ticketnummer ab. Ein neuer Name für einen Spielort müsste in jeder verkauften Ticketzeile geändert werden. Damit entstehen leicht widersprüchliche Stammdaten.

Die Zerlegung trennt zuerst `venues(venue_code, ...)` ab. `venue_code` ist die Schnittmenge mit der Restrelation und bestimmt alle Attribute der Spielortprojektion. Der Schritt ist deshalb verlustfrei. Veranstalter, Käufer, Bestellungen, Veranstaltungen und Kategorien werden nacheinander mit demselben Kriterium abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Der Beweis betrifft vorhandene Ticketexportzeilen. Ein Spielort ohne Veranstaltung oder eine Bestellung ohne Ticket kommt dort nicht vor und kann aus diesem Export nicht rekonstruiert werden. Das A2-Modell speichert solche Zeilen unabhängig. Eigenveranstaltungen erhalten keinen Veranstalter und werden mit nullable `organizer_id` und LEFT JOIN abgebildet.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

`event_type -> admission_code` verletzt BCNF, weil `event_type` kein Superschlüssel von `events` ist. `admission_code` ist Teil des Alternativschlüssels `(title, admission_code)`, daher bleibt 3NF erfüllt. Für BCNF kann `event_type_codes(event_type, admission_code)` abgetrennt werden.
