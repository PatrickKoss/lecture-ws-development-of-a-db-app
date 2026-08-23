# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/er.svg` nennen Restaurant, Dish, Customer, Order, Courier und Review; OrderItem setzt die n:m-Beziehung um. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt Restaurant zu Dish; ein Restaurant bietet mehrere Gerichte an. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt Order zu Dish über `order_items` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `order_items` speichert `position_number`, `quantity` und `unit_price`; die Kurierzuordnung hat zwei Zeitpunkte. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | `orders.courier_id` ist optional; `reviews.order_id` bildet die optionale Bewertung ab. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `order_items` wird durch `order_id` und `position_number` identifiziert. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 14 Datenzeilen; O-26001, O-26003, O-26005 und O-26008 erstrecken sich über mehrere Positionen. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Bestellung -> Kundennr. -> Kunde, E-Mail`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `Kategorie -> MwSt.`; Kategorie ist kein Superschlüssel, MwSt. aber Teil des Alternativschlüssels `(Gerichtsname, MwSt.)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 3 und 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfragen 4 und 6 nutzen ihn für optionale Zuordnungen erneut. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht den Provisionsbetrag mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 2 vergleicht `ordered_at` mit `2026-03-01 00:00:00`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/food-marketplace.db` lief fehlerfrei; die Abfragen ergaben 12, 3, 3, 13, 5, 12, 3 und 6 Zeilen, `foreign_key_check` blieb leer. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/orders` und `GET /orders/{id}/review`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Zeit- und Positionsregeln sowie konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
