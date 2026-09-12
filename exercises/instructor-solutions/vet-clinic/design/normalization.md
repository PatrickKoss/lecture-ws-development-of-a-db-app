# Normalisierung

Der Behandlungs- und Rezeptauszug hat den Schlüssel `(appointment_id, medication_id)` und enthält Halter, Tier, Tierarzt, Termin, Behandlung und Verschreibung.

```text
appointment_id -> pet_id, vet_id, scheduled_at, reason, appointment_status
pet_id -> owner_id, pet_number, pet_name, species, birth_date
owner_id -> customer_number, owner_name, email, phone, city
vet_id -> license_number, vet_name, specialization, consultation_room
appointment_id -> treatment_id, treated_on, diagnosis, notes, fee_cents
medication_id -> pzn, product_name, active_ingredient, dosage_form, prescription_required
(treatment_id, medication_id) -> dose, duration_days, instructions
```

Termin und Medikament hängen jeweils nur von einem Teil des Exportschlüssels ab. Tier, Halter und Tierarzt hängen transitiv. Eine neue Telefonnummer eines Halters müsste sonst in jeder Behandlung und Verschreibung geändert werden.

Die Zerlegung trennt zuerst `owners(owner_id, ...)` ab. `owner_id` ist die Schnittmenge mit der Restrelation und bestimmt die gesamte Halterprojektion. Der Schritt ist verlustfrei. Tiere, Tierärzte, Termine, Behandlungen, Medikamente und Verschreibungen werden danach über ihre aufgeführten Determinanten abgetrennt.

Der Export ist als vollständiger Verbund der genannten Projektionen definiert. Treffen zu einem Elternsatz mehrere unabhängige Kindmengen zusammen, enthält der Export jede zugehörige Kombination. Diese Join-Abhängigkeit ist für das verlustfreie Zurückverbinden der Kindprojektionen nötig und folgt nicht allein aus den funktionalen Abhängigkeiten.

Der Beweis gilt für vorhandene Behandlungs- und Rezeptzeilen. Ein Tier ohne Termin oder ein Medikament ohne Verschreibung ist daraus nicht ableitbar, bleibt im A2-Modell aber eigenständig speicherbar. Termine ohne Behandlung werden über die fehlende 1:0-Beziehung und einen LEFT JOIN dargestellt.

Für jede nicht triviale Abhängigkeit `X -> A` verlangt 3NF: `X` ist ein Superschlüssel oder `A` ist ein Primattribut. Bei allen Abhängigkeiten außer der folgenden BCNF-Falle ist der Determinant ein Kandidaten- oder Superschlüssel. In der Ausnahme ist die rechte Seite prim. Damit erfüllt das Ergebnis in `relations.sql` die formale 3NF-Bedingung.

Unter der Kursannahme gilt `specialization -> consultation_room`. `specialization` ist kein Superschlüssel, `consultation_room` ist aber Teil des Alternativschlüssels `(first_name, last_name, consultation_room)`. `vets` erfüllt 3NF, aber nicht BCNF. Für BCNF wird `specialization_rooms(specialization, consultation_room)` abgetrennt.
