# Normalisierung

Der Kartenexport hat den Schlüssel `ticket_number` und enthält Film, Vorstellung, Saal, Sitz und optional den Kunden.

```text
ticket_number -> screening_code, hall_id, row_label, seat_number, customer_number, price_cents, sold_at
screening_code -> movie_code, hall_id, starts_at, language, projection_format
movie_code -> title, release_year, duration_minutes, fsk_code, minimum_age
hall_id -> hall_number, hall_name, capacity
(hall_id, row_label, seat_number) -> category, accessible
customer_number -> customer_name, email, registered_on, active
```

Film-, Saal- und Kundendaten hängen transitiv vom Ticket ab. Ändert sich ein Saalname, müsste jede Ticketzeile dieses Saals geändert werden. Eine vergessene Zeile würde zwei Namen für denselben Saal enthalten.

Beim Abtrennen von `halls(hall_id, ...)` ist `hall_id` die Schnittmenge mit der verbleibenden Exportrelation und bestimmt die Hallenprojektion. Der Schritt ist nach dem Kriterium für binäre Zerlegungen verlustfrei. Das Verfahren wird mit `movie_code`, `screening_code`, dem zusammengesetzten Sitzschlüssel und `customer_number` wiederholt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Die Aussage gilt für vorhandene Ticketzeilen. Filme ohne Vorstellung und Vorstellungen ohne Ticket lassen sich aus dem Ticketexport nicht gewinnen, bleiben im A2-Modell aber unabhängig erhalten. Ein anonymer Kartenkauf wird über das nullable `customer_id` und einen LEFT JOIN rekonstruiert.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

Unter der Kursannahme gilt in `movies` außerdem `fsk_code -> minimum_age`. `fsk_code` ist kein Superschlüssel, `minimum_age` gehört aber zum Alternativschlüssel `(title, minimum_age)`. Deshalb liegt 3NF, aber keine BCNF vor. Für BCNF wird `fsk_ratings(fsk_code, minimum_age)` abgetrennt.
