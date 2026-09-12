# Musterlösung

> Zeitbox für die Erstellung: 45 Minuten nach Abschluss der Aufgabenblätter

Diese Dateien sind für die gemeinsame Auswertung gedacht. Studierende öffnen sie erst im Debrief.

## Normalisierte Tabellen

Das Referenzmodell trennt Halter, Tiere, Tierärzte, Termine, Behandlungen und Medikamente. `prescriptions` setzt die Beziehung zwischen Behandlung und Medikament als Zuordnungstabelle um.

### `owners`

Attribute sind `id`, `customer_number`, `first_name`, `last_name`, `email`, `phone` und `city`. `id` ist der Primary Key. `customer_number` und `email` sind jeweils eindeutige Alternativschlüssel. Alle Attribute außer `id` sind Pflichtfelder.

Funktionale Abhängigkeiten:

- `id -> customer_number, first_name, last_name, email, phone, city`
- `customer_number -> id, first_name, last_name, email, phone, city`
- `email -> id, customer_number, first_name, last_name, phone, city`

### `pets`

Attribute sind `id`, `owner_id`, `pet_number`, `name`, `species`, `birth_date`, `insurance_policy_number` und `active`. `id` ist der Primary Key. `owner_id` verweist auf `owners.id`. `owner_id` und `pet_number` bilden einen Alternativschlüssel. `insurance_policy_number` ist optional und eindeutig. Tierart und Aktivstatus haben feste Wertemengen.

Funktionale Abhängigkeiten:

- `id -> owner_id, pet_number, name, species, birth_date, insurance_policy_number, active`
- `(owner_id, pet_number) -> id, name, species, birth_date, insurance_policy_number, active`
- `insurance_policy_number -> id, owner_id, pet_number, name, species, birth_date, active`, sofern `insurance_policy_number` nicht `NULL` ist

`pets` ist der Kandidat für eine schwache Entität. Die Tiernummer ist nur zusammen mit dem Halter eindeutig. Das Referenzschema nutzt trotzdem `id` als technischen Primary Key, weil JDBC, JPA und der REST-Pfad dadurch einfacher bleiben.

### `vets`

Attribute sind `id`, `license_number`, `first_name`, `last_name`, `specialization`, `consultation_room` und `active`. `id` ist der Primary Key. `license_number` sowie die Kombination aus Name und `consultation_room` sind Alternativschlüssel. `active` ist auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> license_number, first_name, last_name, specialization, consultation_room, active`
- `license_number -> id, first_name, last_name, specialization, consultation_room, active`
- `(first_name, last_name, consultation_room) -> id, license_number, specialization, active`
- `specialization -> consultation_room`

Die letzte Abhängigkeit ist die BCNF-Falle. `specialization` ist kein Superschlüssel. `consultation_room` ist aber ein Primattribut des Alternativschlüssels `(first_name, last_name, consultation_room)`. Die Tabelle erfüllt daher die 3NF, aber nicht die BCNF. Eine BCNF-Zerlegung würde `specialization_rooms(specialization, consultation_room)` abtrennen. Für das Kursmodell bleibt die 3NF-Fassung bestehen, damit das Schema bei den sechs fachlichen Entitäten bleibt.

### `appointments`

Attribute sind `id`, `pet_id`, `vet_id`, `scheduled_at`, `reason` und `status`. `id` ist der Primary Key. `pet_id` verweist auf `pets.id`, `vet_id` auf `vets.id`. Die Kombinationen aus Tierarzt und Zeitpunkt sowie aus Tier und Zeitpunkt sind eindeutig. Der Status hat eine feste Wertemenge.

Funktionale Abhängigkeiten:

- `id -> pet_id, vet_id, scheduled_at, reason, status`
- `(vet_id, scheduled_at) -> id, pet_id, reason, status`
- `(pet_id, scheduled_at) -> id, vet_id, reason, status`

In der flachen Ausgangstabelle entsteht unter anderem die transitive Abhängigkeit `Termin -> Halternr. -> Halter, E-Mail`. Die Zerlegung trennt Halter, Tiere und Termine.

### `treatments`

Attribute sind `id`, `appointment_id`, `treated_on`, `diagnosis`, `notes` und `fee_cents`. `id` ist der Primary Key. `appointment_id` verweist auf `appointments.id` und ist eindeutig. Dadurch hat ein Termin höchstens eine Behandlung. `notes` ist optional.

Funktionale Abhängigkeiten:

- `id -> appointment_id, treated_on, diagnosis, notes, fee_cents`
- `appointment_id -> id, treated_on, diagnosis, notes, fee_cents`

Die Beziehung vom Termin zur Behandlung ist optional. Ausgefallene Termine und Kontrollen ohne dokumentierte Behandlung haben keinen Datensatz in `treatments`.

### `medications`

Attribute sind `id`, `pzn`, `product_name`, `active_ingredient`, `dosage_form`, `prescription_required` und `active`. `id` ist der Primary Key. `pzn` sowie die Kombination aus Produktname und Darreichungsform sind Alternativschlüssel. Rezeptpflicht und Aktivstatus sind auf 0 oder 1 beschränkt.

Funktionale Abhängigkeiten:

- `id -> pzn, product_name, active_ingredient, dosage_form, prescription_required, active`
- `pzn -> id, product_name, active_ingredient, dosage_form, prescription_required, active`
- `(product_name, dosage_form) -> id, pzn, active_ingredient, prescription_required, active`

### `prescriptions`

Attribute sind `treatment_id`, `medication_id`, `dose`, `duration_days` und `instructions`. Der Primary Key besteht aus `treatment_id` und `medication_id`. `treatment_id` verweist auf `treatments.id`, `medication_id` auf `medications.id`. Dosis und Dauer beschreiben die Verschreibung, nicht das Medikament allein.

Funktionale Abhängigkeiten:

- `(treatment_id, medication_id) -> dose, duration_days, instructions`

`prescriptions` löst die n:m-Beziehung zwischen Behandlung und Medikament auf. `dose` und `duration_days` sind fachliche Attribute dieser Beziehung.

## Annahmen zu den offenen Fragen

Die Referenzlösung vergibt pro Halter fortlaufende Tiernummern. Zwei Tiere desselben Halters dürfen denselben Namen tragen, ihre Nummer unterscheidet sie. Die Datenbank nutzt zusätzlich eine technische ID.

Ein ausgefallener Termin erzeugt keine Behandlung. Bei einer Kontrolle entsteht nur dann ein Behandlungsdatensatz, wenn die Praxis einen Befund, eine Maßnahme oder eine Diagnose dokumentiert. So bleibt die Beziehung vom Termin zur Behandlung optional.

Eine Verschreibung hält die Dosis zum Zeitpunkt der Behandlung fest. Eine spätere Änderung überschreibt sie nicht. Das Kursmodell erfasst die geänderte Verordnung bei einer neuen Behandlung. Eine eigene Änderungshistorie wäre ebenfalls begründbar, gehört aber nicht zum Referenzmodell.

## Dateien

- `er.svg` zeigt das Referenzmodell in Crow's-Foot-Notation.
- `../sql/schema.sql` enthält das SQLite-Schema.
- `../sql/seed.sql` enthält 10 bis 20 Zeilen pro Tabelle.
- `../sql/queries.sql` löst die acht SQL-Aufgaben.
