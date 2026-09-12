-- Lesbare Relationsnotation, kein ausführbares SQL
owners(id PK, customer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, phone NOT NULL, city NOT NULL)
pets(id PK, owner_id FK NOT NULL -> owners.id, pet_number NOT NULL, name NOT NULL, species NOT NULL, birth_date NULL, insurance_policy_number UK NULL, active NOT NULL, UK(owner_id, pet_number))
vets(id PK, license_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, specialization NOT NULL, consultation_room NOT NULL, active NOT NULL, UK(first_name, last_name, consultation_room))
appointments(id PK, pet_id FK NOT NULL -> pets.id, vet_id FK NOT NULL -> vets.id, scheduled_at NOT NULL, reason NOT NULL, status NOT NULL, UK(vet_id, scheduled_at), UK(pet_id, scheduled_at))
treatments(id PK, appointment_id FK UK NOT NULL -> appointments.id, treated_on NOT NULL, diagnosis NOT NULL, notes NULL, fee_cents NOT NULL)
medications(id PK, pzn UK NOT NULL, product_name NOT NULL, active_ingredient NOT NULL, dosage_form NOT NULL, prescription_required NOT NULL, active NOT NULL, UK(product_name, dosage_form))
prescriptions(treatment_id PK FK NOT NULL -> treatments.id, medication_id PK FK NOT NULL -> medications.id, dose NOT NULL, duration_days NOT NULL, instructions NULL)

-- Behandlung und Medikament identifizieren eine Verschreibung vollständig.
-- NULL bei birth_date bedeutet ein unbekanntes Geburtsdatum.
