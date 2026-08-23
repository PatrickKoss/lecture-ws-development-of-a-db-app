# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/README.md` nennen sechs eigenständige Entitäten; `OrderItem` ist die fachlich benannte Zuordnung zwischen Bestellung und Pizza. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt Customer zu Address; ein Kunde kann mehrere Adressen haben. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt Order zu Pizza über `order_items` und Pizza zu Topping über `pizza_toppings` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `order_items` speichert `quantity` und `unit_price`; `pizza_toppings` speichert `extra_charge`. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | `orders.driver_id` ist optional; Abholbestellungen haben keinen Fahrer. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `order_items` wird durch `order_id` und die pro Bestellung neu beginnende `position_number` identifiziert. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 15 Datenzeilen; B-26001, B-26003 und B-26008 sowie Kunden- und Pizzadaten wiederholen sich. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Bestellung -> Kundennr. -> Kunde, Telefon`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `Kategorie -> Ofen`; Kategorie ist kein Superschlüssel, Ofen aber Teil des Alternativschlüssels `(Pizza, Ofen)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 3 und 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfrage 6 nutzt ihn für Kunden ohne Bestellung erneut. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht den Pizzaumsatz mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 2 vergleicht `ordered_on` mit `2026-03-01`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/pizza-delivery.db` lief fehlerfrei; die Abfragen ergaben 10, 4, 12, 20, 6, 12, 3 und 5 Zeilen, `foreign_key_check` blieb leer. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/orders` und `GET /orders/{id}/driver`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Regeln für Abholung und Lieferung sowie konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
