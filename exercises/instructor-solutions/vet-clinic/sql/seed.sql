PRAGMA foreign_keys = ON;

BEGIN TRANSACTION;

INSERT INTO owners (
    id,
    customer_number,
    first_name,
    last_name,
    email,
    phone,
    city
) VALUES
    (1, 'H-1001', 'Anna', 'Krüger', 'anna.krueger@example.org', '0521 661201', 'Bielefeld'),
    (2, 'H-1002', 'Mehmet', 'Demir', 'mehmet.demir@example.org', '0521 661202', 'Bielefeld'),
    (3, 'H-1003', 'Sophie', 'Lindner', 'sophie.lindner@example.org', '0521 661203', 'Bielefeld'),
    (4, 'H-1004', 'Jonas', 'Becker', 'jonas.becker@example.org', '0521 661204', 'Steinhagen'),
    (5, 'H-1005', 'Aylin', 'Yilmaz', 'aylin.yilmaz@example.org', '0521 661205', 'Bielefeld'),
    (6, 'H-1006', 'Daniel', 'Hoffmann', 'daniel.hoffmann@example.org', '0521 661206', 'Gütersloh'),
    (7, 'H-1007', 'Emilia', 'Rossi', 'emilia.rossi@example.org', '0521 661207', 'Bielefeld'),
    (8, 'H-1008', 'Noah', 'Schneider', 'noah.schneider@example.org', '0521 661208', 'Herford'),
    (9, 'H-1009', 'Fatma', 'Kaya', 'fatma.kaya@example.org', '0521 661209', 'Bielefeld'),
    (10, 'H-1010', 'Leonie', 'Wagner', 'leonie.wagner@example.org', '0521 661210', 'Bielefeld'),
    (11, 'H-1011', 'Cem', 'Aydin', 'cem.aydin@example.org', '0521 661211', 'Oerlinghausen'),
    (12, 'H-1012', 'Mara', 'Klein', 'mara.klein@example.org', '0521 661212', 'Bielefeld');

INSERT INTO pets (
    id,
    owner_id,
    pet_number,
    name,
    species,
    birth_date,
    insurance_policy_number,
    active
) VALUES
    (1, 1, 1, 'Luna', 'KATZE', '2026-01-01', 'AG-832910', 1),
    (2, 2, 1, 'Balu', 'HUND', '2026-01-01', NULL, 1),
    (3, 3, 1, 'Nala', 'KATZE', '2026-01-02', 'UEL-441208', 1),
    (4, 4, 1, 'Rocky', 'HUND', '2026-01-02', NULL, 1),
    (5, 5, 1, 'Coco', 'KANINCHEN', '2026-01-03', NULL, 1),
    (6, 6, 1, 'Milo', 'KATZE', '2026-01-03', 'BARM-71234', 1),
    (7, 7, 1, 'Pepe', 'VOGEL', '2026-01-04', NULL, 1),
    (8, 2, 2, 'Kira', 'HUND', '2026-01-04', 'AG-933104', 1),
    (9, 8, 1, 'Flocke', 'MEERSCHWEINCHEN', '2026-01-05', NULL, 1),
    (10, 9, 1, 'Sammy', 'HUND', '2026-01-05', 'RPV-553120', 1),
    (11, 3, 2, 'Simba', 'KATZE', '2026-01-06', NULL, 1),
    (12, 10, 1, 'Bruno', 'HUND', '2026-01-06', NULL, 1),
    (13, 11, 1, 'Maja', 'KANINCHEN', '2026-01-07', 'UEL-778410', 1),
    (14, 12, 1, 'Oskar', 'KATZE', '2026-01-07', NULL, 1),
    (15, 1, 2, 'Lotti', 'HUND', NULL, NULL, 1);

INSERT INTO vets (
    id,
    license_number,
    first_name,
    last_name,
    specialization,
    consultation_room,
    active
) VALUES
    (1, 'TA-4711', 'Miriam', 'Vogt', 'Innere Medizin', 'B-1', 1),
    (2, 'TA-5822', 'Lena', 'Hartmann', 'Dermatologie', 'B-2', 1),
    (3, 'TA-6033', 'Tobias', 'Fischer', 'Chirurgie', 'OP-1', 0),
    (4, 'TA-6144', 'Nora', 'Schulz', 'Zahnheilkunde', 'B-3', 0),
    (5, 'TA-6255', 'Yusuf', 'Özkan', 'Kardiologie', 'B-4', 0),
    (6, 'TA-6366', 'Clara', 'Jansen', 'Augenheilkunde', 'B-5', 0),
    (7, 'TA-6477', 'Felix', 'Braun', 'Neurologie', 'B-6', 0),
    (8, 'TA-6588', 'Mina', 'Saleh', 'Verhaltensmedizin', 'B-7', 0),
    (9, 'TA-6699', 'Jan', 'Koch', 'Onkologie', 'B-8', 0),
    (10, 'TA-6700', 'Alina', 'Franke', 'Radiologie', 'R-1', 0);

INSERT INTO appointments (
    id,
    pet_id,
    vet_id,
    scheduled_at,
    reason,
    status
) VALUES
    (1, 1, 1, '2026-01-08T09:00', 'Schmerzhaftes Wasserlassen', 'COMPLETED'),
    (2, 2, 2, '2026-01-10T10:30', 'Juckreiz und gerötete Haut', 'COMPLETED'),
    (3, 3, 1, '2026-01-14T14:00', 'Vermehrtes Trinken', 'COMPLETED'),
    (4, 4, 2, '2026-01-17T11:00', 'Kontrolle nach Hautbehandlung', 'NO_SHOW'),
    (5, 5, 1, '2026-01-21T08:30', 'Frisst seit zwei Tagen schlecht', 'COMPLETED'),
    (6, 6, 2, '2026-01-25T15:30', 'Kreisrunde kahle Stellen', 'COMPLETED'),
    (7, 7, 1, '2026-02-02T09:30', 'Atemgeräusche', 'COMPLETED'),
    (8, 8, 2, '2026-02-05T13:00', 'Schüttelt häufig den Kopf', 'COMPLETED'),
    (9, 9, 1, '2026-02-09T16:00', 'Verklebtes Auge', 'COMPLETED'),
    (10, 10, 2, '2026-02-11T10:00', 'Steifer Gang nach dem Aufstehen', 'COMPLETED'),
    (11, 11, 1, '2026-02-14T12:30', 'Entzündete Bisswunde', 'COMPLETED'),
    (12, 12, 2, '2026-02-18T09:00', 'Anhaltender Juckreiz', 'COMPLETED'),
    (13, 2, 1, '2026-02-25T11:30', 'Impfberatung', 'PLANNED'),
    (14, 3, 2, '2026-03-02T14:30', 'Kontrolle der Nierenwerte', 'PLANNED'),
    (15, 1, 1, '2026-03-05T09:00', 'Nachkontrolle der Blase', 'CANCELLED'),
    (16, 13, 2, '2026-02-27T15:00', 'Zahnkontrolle', 'PLANNED');

INSERT INTO treatments (
    id,
    appointment_id,
    treated_on,
    diagnosis,
    notes,
    fee_cents
) VALUES
    (1, 1, '2026-01-08', 'Blasenentzündung', 'Urinprobe im Labor untersucht.', 6890),
    (2, 2, '2026-01-10', 'Allergische Dermatitis', 'Futtertagebuch empfohlen.', 7450),
    (3, 3, '2026-01-14', 'Chronische Niereninsuffizienz', 'Blutwerte in vier Wochen kontrollieren.', 12900),
    (4, 5, '2026-01-21', 'Zahnspitzen', 'Backenzähne unter Sedierung gekürzt.', 11800),
    (5, 6, '2026-01-25', 'Dermatophytose', 'Pilzkultur angelegt.', 9650),
    (6, 7, '2026-02-02', 'Bakterielle Atemwegsinfektion', 'Tier warm und zugfrei halten.', 6320),
    (7, 8, '2026-02-05', 'Otitis externa', 'Gehörgang gereinigt.', 7110),
    (8, 9, '2026-02-09', 'Bakterielle Konjunktivitis', NULL, 5480),
    (9, 10, '2026-02-11', 'Arthrose', 'Gewichtskontrolle in sechs Wochen.', 8240),
    (10, 11, '2026-02-14', 'Wundinfektion', 'Wunde gespült und verbunden.', 7760),
    (11, 12, '2026-02-18', 'Atopische Dermatitis', 'Pfoten nach Spaziergängen abwaschen.', 7350);

INSERT INTO medications (
    id,
    pzn,
    product_name,
    active_ingredient,
    dosage_form,
    prescription_required,
    active
) VALUES
    (1, '09231122', 'Synulox 50 mg', 'Amoxicillin/Clavulansäure', 'Tablette', 1, 1),
    (2, '01472114', 'Metacam 0,5 mg/ml', 'Meloxicam', 'Suspension', 1, 1),
    (3, '10033456', 'Apoquel 5,4 mg', 'Oclacitinib', 'Tablette', 1, 1),
    (4, '06199218', 'Semintra 4 mg/ml', 'Telmisartan', 'Lösung', 1, 1),
    (5, '01377561', 'Itrafungol 10 mg/ml', 'Itraconazol', 'Lösung', 1, 1),
    (6, '06999103', 'Surolan', 'Miconazol/Polymyxin B/Prednisolon', 'Suspension', 1, 1),
    (7, '03464231', 'Floxal Augentropfen', 'Ofloxacin', 'Augentropfen', 1, 1),
    (8, '11358861', 'Prilium 75 mg', 'Imidapril', 'Tablette', 1, 1),
    (9, '16893244', 'Caniviton Forte 30', 'Chondroitinsulfat', 'Pulver', 0, 1),
    (10, '12490017', 'RenalVet Paste', 'Calciumcarbonat/Chitosan', 'Paste', 0, 1),
    (11, '00711052', 'Baytril 15 mg', 'Enrofloxacin', 'Tablette', 1, 0),
    (12, '04932218', 'Prednisolon 5 mg', 'Prednisolon', 'Tablette', 1, 0);

INSERT INTO prescriptions (
    treatment_id,
    medication_id,
    dose,
    duration_days,
    instructions
) VALUES
    (1, 1, '1 Tablette morgens und abends', 7, 'Mit dem Futter geben.'),
    (1, 2, '2 ml einmal täglich', 3, 'Vor Gebrauch schütteln.'),
    (2, 3, '1 Tablette täglich', 14, NULL),
    (3, 4, '1 ml einmal täglich', 30, 'Direkt ins Maul geben.'),
    (3, 10, '2 cm Paste zweimal täglich', 30, 'Unter das Futter mischen.'),
    (4, 2, '0,3 ml einmal täglich', 5, 'Nach dem Fressen geben.'),
    (5, 5, '2 ml einmal täglich', 7, 'Handschuhe bei der Gabe tragen.'),
    (5, 6, '5 Tropfen morgens und abends', 10, 'Auf die betroffenen Stellen geben.'),
    (6, 1, '1/4 Tablette morgens und abends', 7, NULL),
    (7, 6, '5 Tropfen morgens und abends', 10, 'Vorher den Gehörgang reinigen.'),
    (8, 7, '1 Tropfen dreimal täglich', 7, 'Nicht mit der Pipette ans Auge kommen.'),
    (9, 2, '1,2 ml einmal täglich', 14, 'Mit einer Mahlzeit geben.'),
    (9, 9, '1 Messlöffel täglich', 30, 'Unter das Futter mischen.'),
    (10, 1, '1 Tablette morgens und abends', 7, 'Bis zum Ende der Packung geben.'),
    (11, 3, '1 Tablette täglich', 14, NULL);

COMMIT;
