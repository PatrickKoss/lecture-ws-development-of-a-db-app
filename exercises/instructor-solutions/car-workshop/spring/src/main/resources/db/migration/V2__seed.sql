INSERT INTO customers (
    id,
    customer_number,
    first_name,
    last_name,
    email,
    phone,
    registered_on,
    active
) VALUES
    (1, 'K-1001', 'Julia', 'Neumann', 'julia.neumann@example.org', '0209 5550101', '2026-01-03', 1),
    (2, 'K-1002', 'Cem', 'Aydin', 'cem.aydin@example.org', '0209 5550102', '2026-01-04', 1),
    (3, 'K-1003', 'Hannah', 'Berger', 'hannah.berger@example.org', '0209 5550103', '2026-01-05', 1),
    (4, 'K-1004', 'David', 'Özdemir', 'david.oezdemir@example.org', '0209 5550104', '2026-01-06', 1),
    (5, 'K-1005', 'Lea', 'Hoffmann', 'lea.hoffmann@example.org', '0209 5550105', '2026-01-07', 1),
    (6, 'K-1006', 'Murat', 'Yilmaz', 'murat.yilmaz@example.org', '0209 5550106', '2026-01-09', 1),
    (7, 'K-1007', 'Sofia', 'Rossi', 'sofia.rossi@example.org', '0209 5550107', '2026-01-11', 1),
    (8, 'K-1008', 'Noah', 'Schneider', 'noah.schneider@example.org', '0209 5550108', '2026-01-12', 1),
    (9, 'K-1009', 'Aylin', 'Kaya', 'aylin.kaya@example.org', '0209 5550109', '2026-01-13', 1),
    (10, 'K-1010', 'Jonas', 'Klein', 'jonas.klein@example.org', '0209 5550110', '2026-01-14', 1),
    (11, 'K-1011', 'Mina', 'Saleh', 'mina.saleh@example.org', '0209 5550111', '2026-01-15', 0),
    (12, 'K-1012', 'Felix', 'Braun', 'felix.braun@example.org', '0209 5550112', '2026-01-16', 1);

INSERT INTO vehicles (
    id,
    owner_id,
    previous_owner_id,
    licence_plate,
    vin,
    manufacturer,
    model,
    construction_year,
    mileage
) VALUES
    (1, 1, NULL, 'GE-AB 123', 'WVWZZZAUZJW000101', 'Volkswagen', 'Golf VII', 2018, 78420),
    (2, 2, 11, 'GE-CD 456', 'W0L0XEP68G4000202', 'Opel', 'Corsa E', 2016, 92510),
    (3, 3, NULL, 'GE-EF 789', 'WF05XXGCC5GB00303', 'Ford', 'Focus', 2016, 110340),
    (4, 4, NULL, 'GE-GH 321', 'WBA8C91070K000404', 'BMW', '320d', 2019, 68120),
    (5, 5, 12, 'GE-JK 654', 'WDD1760421J000505', 'Mercedes-Benz', 'A 180', 2017, 85300),
    (6, 6, NULL, 'GE-LM 987', 'TMBJG7NE7J0000606', 'Skoda', 'Octavia', 2018, 124800),
    (7, 7, NULL, 'GE-NP 147', 'VF15R040H55000707', 'Renault', 'Clio', 2015, 99640),
    (8, 8, NULL, 'GE-QR 258', 'WAUZZZ8V7JA000808', 'Audi', 'A3 Sportback', 2018, 73550),
    (9, 9, NULL, 'GE-ST 369', 'VSSZZZ5FZKR000909', 'Seat', 'Leon', 2019, 61220),
    (10, 10, NULL, 'GE-UV 741', 'WVWZZZAWZLY001010', 'Volkswagen', 'Polo VI', 2020, 43800),
    (11, 11, NULL, 'GE-WX 852', 'UU1HSDAC6L0011111', 'Dacia', 'Duster', 2020, 51200),
    (12, 12, NULL, 'GE-YZ 963', 'KMHCT51CABU012121', 'Hyundai', 'i20', 2021, 38950);

INSERT INTO mechanics (
    id,
    personnel_number,
    first_name,
    last_name,
    specialization,
    hourly_rate,
    active
) VALUES
    (1, 'ME-01', 'Thomas', 'Becker', 'ALLGEMEIN', 72.00, 1),
    (2, 'ME-02', 'Nadine', 'Scholz', 'MOTOR', 78.00, 1),
    (3, 'ME-03', 'Mehmet', 'Yilmaz', 'DIAGNOSE', 82.00, 1),
    (4, 'ME-04', 'Stefan', 'Roth', 'KAROSSERIE', 76.00, 1),
    (5, 'ME-05', 'Aylin', 'Demir', 'ELEKTRIK', 84.00, 1),
    (6, 'ME-06', 'Frank', 'Nowak', 'ALLGEMEIN', 70.00, 1),
    (7, 'ME-07', 'Laura', 'König', 'MOTOR', 80.00, 1),
    (8, 'ME-08', 'Daniel', 'Krüger', 'DIAGNOSE', 83.00, 1),
    (9, 'ME-09', 'Sarah', 'Winkler', 'ALLGEMEIN', 71.00, 1),
    (10, 'ME-10', 'Marco', 'Peters', 'KAROSSERIE', 77.00, 0);

INSERT INTO parts (
    id,
    part_number,
    name,
    category,
    shelf_code,
    stock_quantity,
    reorder_level,
    list_price
) VALUES
    (1, 'P-1001', 'Ölfilter MANN W 712/95', 'Filter', 'F-03', 3, 5, 12.90),
    (2, 'P-1002', 'Bremsbelagsatz Bosch 0 986 494 596', 'Bremse', 'BR-02', 2, 2, 69.90),
    (3, 'P-1003', 'Motoröl Castrol EDGE 5W-30 1 l', 'Betriebsstoff', 'B-01', 18, 10, 14.50),
    (4, 'P-1004', 'Zündkerze NGK 97153', 'Zündung', 'Z-04', 6, 8, 13.20),
    (5, 'P-1005', 'Luftfilter MAHLE LX 2046', 'Filter', 'F-03', 7, 4, 24.90),
    (6, 'P-1006', 'Starterbatterie Varta E11', 'Elektrik', 'E-02', 1, 2, 139.00),
    (7, 'P-1007', 'Innenraumfilter Bosch 1 987 432 543', 'Filter', 'F-03', 9, 5, 19.90),
    (8, 'P-1008', 'Keilrippenriemen CONTITECH 6PK1054', 'Antrieb', 'A-05', 4, 3, 26.50),
    (9, 'P-1009', 'Wischerblatt Bosch Aerotwin A863S', 'Sicht', 'S-01', 5, 3, 34.90),
    (10, 'P-1010', 'H7-Lampe OSRAM Night Breaker 200', 'Elektrik', 'E-02', 12, 6, 22.90),
    (11, 'P-1011', 'Kraftstofffilter MAHLE KL 756', 'Filter', 'F-03', 2, 3, 31.80),
    (12, 'P-1012', 'Kühlmittel Glysantin G40 1,5 l', 'Betriebsstoff', 'B-01', 8, 8, 16.40);

INSERT INTO work_orders (
    id,
    order_number,
    vehicle_id,
    opened_on,
    closed_on,
    status,
    mileage_in,
    complaint
) VALUES
    (1, 'A-26001', 1, '2026-01-08', '2026-01-08', 'INVOICED', 74120, 'Inspektion und Ölwechsel nach Serviceanzeige'),
    (2, 'A-26002', 2, '2026-01-10', '2026-01-10', 'INVOICED', 88740, 'Bremsen vorne quietschen bei niedriger Geschwindigkeit'),
    (3, 'A-26003', 1, '2026-01-20', '2026-01-20', 'INVOICED', 74630, 'Motor läuft unruhig und Kontrollleuchte ist an'),
    (4, 'A-26004', 3, '2026-02-01', '2026-02-02', 'INVOICED', 108910, 'Jahresinspektion und Luftfilter prüfen'),
    (5, 'A-26005', 4, '2026-02-05', NULL, 'IN_PROGRESS', 67400, 'Fahrzeug startet morgens nur nach mehreren Versuchen'),
    (6, 'A-26006', 5, '2026-02-09', '2026-02-09', 'INVOICED', 83100, 'Motor ruckelt beim Beschleunigen im zweiten Gang'),
    (7, 'A-26007', 1, '2026-02-15', '2026-02-16', 'INVOICED', 75880, 'Bremswirkung vorne hat deutlich nachgelassen'),
    (8, 'A-26008', 6, '2026-02-18', '2026-02-18', 'INVOICED', 121500, 'Keilriemen pfeift nach dem Kaltstart'),
    (9, 'A-26009', 7, '2026-02-21', '2026-02-21', 'INVOICED', 98210, 'Scheibenwischer ziehen Schlieren auf der Frontscheibe'),
    (10, 'A-26010', 8, '2026-02-25', NULL, 'OPEN', 72400, 'Batteriewarnleuchte leuchtet während der Fahrt'),
    (11, 'A-26011', 2, '2026-03-05', '2026-03-06', 'INVOICED', 90220, 'Abblendlicht rechts fällt wiederholt aus'),
    (12, 'A-26012', 9, '2026-03-10', NULL, 'OPEN', 60310, 'Kühlmittelstand sinkt ohne sichtbare Pfütze'),
    (13, 'A-26013', 10, '2026-03-12', '2026-03-12', 'INVOICED', 42610, 'Bremsbeläge hinten laut Verschleißanzeige prüfen'),
    (14, 'A-26014', 3, '2026-03-15', NULL, 'IN_PROGRESS', 110340, 'Motor verliert Leistung bei höherer Drehzahl');

INSERT INTO work_order_mechanics (work_order_id, mechanic_id, hours_worked) VALUES
    (1, 1, 2.5),
    (2, 2, 1.5),
    (3, 1, 0.8),
    (3, 3, 1.0),
    (4, 4, 3.0),
    (5, 5, 2.0),
    (5, 2, 0.6),
    (6, 6, 2.2),
    (7, 1, 1.3),
    (7, 7, 4.0),
    (8, 8, 2.8),
    (9, 9, 0.7),
    (10, 5, 1.1),
    (11, 2, 1.4),
    (12, 10, 1.8),
    (13, 4, 2.1),
    (14, 6, 2.6);

INSERT INTO work_order_parts (
    work_order_id,
    line_number,
    part_id,
    quantity,
    unit_price
) VALUES
    (1, 1, 1, 1, 11.90),
    (1, 2, 3, 5, 13.50),
    (2, 1, 2, 1, 64.90),
    (3, 1, 3, 5, 13.50),
    (3, 2, 4, 4, 12.40),
    (4, 1, 1, 1, 11.90),
    (4, 2, 5, 1, 22.80),
    (5, 1, 6, 1, 129.00),
    (6, 1, 4, 4, 12.40),
    (6, 2, 7, 1, 18.70),
    (7, 1, 2, 1, 64.90),
    (8, 1, 1, 1, 11.90),
    (8, 2, 8, 1, 24.60),
    (9, 1, 9, 1, 31.50),
    (10, 1, 6, 1, 139.00),
    (11, 1, 1, 1, 12.90),
    (11, 2, 10, 1, 22.90),
    (13, 1, 2, 1, 69.90);

INSERT INTO invoices (
    id,
    invoice_number,
    work_order_id,
    issued_on,
    due_on,
    paid_on,
    net_amount,
    tax_amount,
    status
) VALUES
    (1, 'R-26001', 1, '2026-01-08', '2026-01-22', '2026-01-12', 144.40, 27.44, 'PAID'),
    (2, 'R-26002', 2, '2026-01-10', '2026-01-24', '2026-01-18', 181.90, 34.56, 'PAID'),
    (3, 'R-26003', 3, '2026-01-20', '2026-02-03', '2026-01-29', 195.60, 37.16, 'PAID'),
    (4, 'R-26004', 4, '2026-02-02', '2026-02-16', '2026-02-14', 262.70, 49.91, 'PAID'),
    (5, 'R-26006', 6, '2026-02-09', '2026-02-23', '2026-02-19', 222.10, 42.20, 'PAID'),
    (6, 'R-26007', 7, '2026-02-16', '2026-03-02', '2026-02-28', 468.50, 89.02, 'PAID'),
    (7, 'R-26008', 8, '2026-02-18', '2026-03-04', '2026-03-01', 268.90, 51.09, 'PAID'),
    (8, 'R-26009', 9, '2026-02-21', '2026-03-07', '2026-03-05', 81.20, 15.43, 'PAID'),
    (9, 'R-26011', 11, '2026-03-06', '2026-03-20', NULL, 135.40, 25.73, 'OPEN'),
    (10, 'R-26013', 13, '2026-03-12', '2026-03-26', NULL, 231.60, 44.00, 'OPEN');


