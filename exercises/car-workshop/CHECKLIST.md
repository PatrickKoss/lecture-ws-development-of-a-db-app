# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Für jeden Punkt ist `Ja` oder `Nein` angekreuzt. Der Nachweis nennt die genaue Stelle in den Dateien.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [x] | [ ] | `README.md` und `solutions/er.svg` nennen Customer, Vehicle, WorkOrder, Mechanic, Part und Invoice als sechs fachliche Entitäten. |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [x] | [ ] | `solutions/er.svg` zeigt Vehicle zu WorkOrder; ein Fahrzeug hat mehrere Arbeitsaufträge. |
| Enthält das Modell mindestens eine n:m-Beziehung? | [x] | [ ] | `schema.sql` setzt WorkOrder zu Part über `work_order_parts` und WorkOrder zu Mechanic über `work_order_mechanics` um. |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [x] | [ ] | `work_order_parts` speichert `quantity` und `unit_price`; `work_order_mechanics` speichert `hours_worked`. |
| Enthält das Modell mindestens eine optionale Beziehung? | [x] | [ ] | Eine Invoice gehört optional zu einem WorkOrder; `vehicles.previous_owner_id` ist ebenfalls optional. |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [x] | [ ] | `work_order_parts` wird durch `work_order_id` und die nur auftragsintern eindeutige `line_number` identifiziert. |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [x] | [ ] | `02-normalization.md` hat 14 Datenzeilen; A-26001, A-26003, A-26004, A-26006 und A-26008 kommen je Teileposition mehrfach vor. |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [x] | [ ] | In `02-normalization.md` gilt `Auftrag -> Kundennr. -> Kunde, E-Mail`. |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [x] | [ ] | `Kategorie -> Lagerfach`; Kategorie ist kein Superschlüssel, Lagerfach aber Teil des Alternativschlüssels `(Ersatzteil, Lagerfach)`. |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [x] | [ ] | Abfragen 3 und 4 in `03-sql.md` und `sql/queries.sql`. |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [x] | [ ] | Abfrage 5 verwendet `LEFT JOIN`; Abfrage 6 nutzt ihn für Fahrzeuge ohne Auftrag erneut. |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [x] | [ ] | Abfragen 6, 7 und 8 gruppieren ihre Ergebnisse. |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [x] | [ ] | Abfragen 7 und 8 verwenden `HAVING`. |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [x] | [ ] | Abfrage 8 vergleicht die Auftragszahl mit einem per Subquery berechneten Durchschnitt. |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [x] | [ ] | Abfrage 2 vergleicht `opened_on` mit `2026-03-01`. |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [x] | [ ] | Laden in `/tmp/car-workshop.db` lief fehlerfrei; die Abfragen ergaben 6, 2, 4, 4, 2, 12, 2 und 3 Zeilen, `foreign_key_check` blieb leer und `integrity_check` ergab `ok`. |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [x] | [ ] | `06-rest.md` definiert `/work-orders` und `GET /work-orders/{id}/invoice`. |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [x] | [ ] | `06-rest.md` nennt Pflichtfelder, Datums- und Statusregeln, Eindeutigkeit und konkrete Fälle für alle drei Codes. |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kein offener Punkt
