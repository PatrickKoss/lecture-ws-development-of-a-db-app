INSERT INTO guests (
    id,
    guest_number,
    first_name,
    last_name,
    email,
    phone,
    company_name,
    company_account_number,
    registered_on
) VALUES
    (1, 'G-1001', 'Julia', 'Neumann', 'julia.neumann@example.org', '+49 211 5550101', NULL, NULL, '2026-01-04'),
    (2, 'G-1002', 'Cem', 'Aydin', 'cem.aydin@example.org', '+49 221 5550102', 'Rheinwerk AG', 'FA-2001', '2026-01-06'),
    (3, 'G-1003', 'Hannah', 'Berger', 'hannah.berger@example.org', '+49 201 5550103', NULL, NULL, '2026-01-18'),
    (4, 'G-1004', 'David', 'Özdemir', 'david.oezdemir@example.org', '+49 231 5550104', 'Messebau West GmbH', 'FA-2002', '2026-02-02'),
    (5, 'G-1005', 'Lea', 'Hoffmann', 'lea.hoffmann@example.org', '+49 211 5550105', NULL, NULL, '2026-02-11'),
    (6, 'G-1006', 'Murat', 'Yilmaz', 'murat.yilmaz@example.org', '+49 209 5550106', NULL, NULL, '2026-02-19'),
    (7, 'G-1007', 'Sofia', 'Rossi', 'sofia.rossi@example.org', '+39 02 5550107', NULL, NULL, '2026-03-03'),
    (8, 'G-1008', 'Noah', 'Schneider', 'noah.schneider@example.org', '+49 40 5550108', 'Klee Consulting', 'FA-2003', '2026-03-14'),
    (9, 'G-1009', 'Aylin', 'Kaya', 'aylin.kaya@example.org', '+49 211 5550109', NULL, NULL, '2026-04-01'),
    (10, 'G-1010', 'Jonas', 'Klein', 'jonas.klein@example.org', '+49 69 5550110', 'Mainblick Logistik', 'FA-2004', '2026-04-16'),
    (11, 'G-1011', 'Mina', 'Saleh', 'mina.saleh@example.org', '+49 30 5550111', NULL, NULL, '2026-05-09'),
    (12, 'G-1012', 'Felix', 'Braun', 'felix.braun@example.org', '+49 211 5550112', NULL, NULL, '2026-06-12');

INSERT INTO room_types (id, type_code, name, capacity, standard_price_cents) VALUES
    (1, 'EZ', 'Einzelzimmer', 1, 8900),
    (2, 'DZ', 'Doppelzimmer', 2, 12900),
    (3, 'TW', 'Twin-Zimmer', 2, 12500),
    (4, 'FA', 'Familienzimmer', 4, 17900),
    (5, 'JS', 'Junior Suite', 2, 21900),
    (6, 'SU', 'Rhein Suite', 3, 28900),
    (7, 'BF', 'Barrierefreies Zimmer', 2, 13900),
    (8, 'DLX', 'Deluxe Doppelzimmer', 2, 18900),
    (9, 'ECO', 'Economy Einzelzimmer', 1, 7900),
    (10, 'ST', 'Studio', 2, 16900);

-- Die Seed-Daten zeigen 16 der 48 Zimmer des Hotels.
INSERT INTO rooms (
    floor,
    room_number,
    room_type_id,
    cleaning_area,
    status,
    accessible
) VALUES
    (1, 11, 1, 'RHEIN', 'AVAILABLE', 0),
    (1, 12, 2, 'RHEIN', 'OCCUPIED', 0),
    (1, 13, 3, 'RHEIN', 'AVAILABLE', 0),
    (1, 14, 7, 'RHEIN', 'AVAILABLE', 1),
    (1, 15, 2, 'RHEIN', 'MAINTENANCE', 0),
    (2, 11, 1, 'HAFEN', 'AVAILABLE', 0),
    (2, 12, 1, 'HAFEN', 'OCCUPIED', 0),
    (2, 13, 3, 'HAFEN', 'AVAILABLE', 0),
    (2, 14, 4, 'HAFEN', 'AVAILABLE', 0),
    (2, 15, 4, 'HAFEN', 'MAINTENANCE', 0),
    (3, 11, 5, 'MEDIENHAFEN', 'AVAILABLE', 0),
    (3, 12, 5, 'MEDIENHAFEN', 'OCCUPIED', 0),
    (3, 13, 6, 'MEDIENHAFEN', 'AVAILABLE', 0),
    (3, 14, 8, 'MEDIENHAFEN', 'AVAILABLE', 0),
    (4, 11, 6, 'PARK', 'OCCUPIED', 0),
    (4, 12, 8, 'PARK', 'MAINTENANCE', 0);

INSERT INTO employees (
    id,
    employee_code,
    first_name,
    last_name,
    role,
    hired_on,
    active
) VALUES
    (1, 'RB-01', 'Anna', 'Schmitz', 'RECEPTION', '2026-01-02', 1),
    (2, 'RB-02', 'Mehmet', 'Demir', 'RECEPTION', '2026-01-02', 1),
    (3, 'RB-03', 'Clara', 'Vogt', 'NIGHT_AUDIT', '2026-01-03', 1),
    (4, 'RB-04', 'Tobias', 'Kramer', 'RECEPTION', '2026-01-03', 1),
    (5, 'RB-05', 'Nina', 'Lenz', 'GUEST_SERVICE', '2026-01-04', 1),
    (6, 'RB-06', 'Samir', 'Haddad', 'RECEPTION', '2026-01-04', 1),
    (7, 'RB-07', 'Eva', 'Peters', 'MANAGEMENT', '2026-01-05', 1),
    (8, 'RB-08', 'Leon', 'Wolf', 'NIGHT_AUDIT', '2026-01-05', 1),
    (9, 'RB-09', 'Mara', 'Koch', 'GUEST_SERVICE', '2026-01-06', 1),
    (10, 'RB-10', 'Deniz', 'Arslan', 'RECEPTION', '2026-01-06', 0);

INSERT INTO services (id, service_code, name, list_price_cents, active) VALUES
    (1, 'FRUEH', 'Frühstücksbuffet', 1900, 1),
    (2, 'PARK', 'Tiefgaragenplatz', 1700, 1),
    (3, 'SPA', 'Spa-Tageskarte', 3800, 1),
    (4, 'MINI', 'Minibar Klassik', 2400, 1),
    (5, 'LATE', 'Late Check-out', 3000, 1),
    (6, 'WASH', 'Wäscheservice', 1200, 1),
    (7, 'BABY', 'Babybett', 1000, 1),
    (8, 'PET', 'Hundepauschale', 2500, 1),
    (9, 'AIR', 'Flughafentransfer', 6500, 1),
    (10, 'CONF', 'Konferenzpaket Rhein', 4500, 0);

INSERT INTO bookings (
    id,
    booking_number,
    guest_id,
    checked_in_by_employee_id,
    booked_on,
    arrival_on,
    departure_on,
    status
) VALUES
    (1, 'B-26001', 1, 1, '2026-01-05', '2026-01-12', '2026-01-15', 'CHECKED_OUT'),
    (2, 'B-26002', 2, 2, '2026-01-06', '2026-01-20', '2026-01-22', 'CHECKED_OUT'),
    (3, 'B-26003', 3, 4, '2026-01-19', '2026-02-03', '2026-02-06', 'CHECKED_OUT'),
    (4, 'B-26004', 4, NULL, '2026-02-02', '2026-02-21', '2026-02-25', 'CANCELLED'),
    (5, 'B-26005', 5, 6, '2026-02-12', '2026-03-06', '2026-03-09', 'CHECKED_OUT'),
    (6, 'B-26006', 6, 3, '2026-02-20', '2026-03-18', '2026-03-20', 'CHECKED_OUT'),
    (7, 'B-26007', 2, NULL, '2026-03-01', '2026-04-29', '2026-05-02', 'CONFIRMED'),
    (8, 'B-26008', 7, 8, '2026-03-04', '2026-05-04', '2026-05-08', 'CHECKED_IN'),
    (9, 'B-26009', 8, 1, '2026-03-15', '2026-06-15', '2026-06-18', 'CHECKED_OUT'),
    (10, 'B-26010', 9, NULL, '2026-04-02', '2026-08-10', '2026-08-14', 'CONFIRMED'),
    (11, 'B-26011', 10, NULL, '2026-04-17', '2026-09-07', '2026-09-10', 'CONFIRMED'),
    (12, 'B-26012', 2, NULL, '2026-05-01', '2026-10-19', '2026-10-22', 'PENDING'),
    (13, 'B-26013', 1, NULL, '2026-05-10', '2026-11-11', '2026-11-14', 'CONFIRMED'),
    (14, 'B-26014', 11, NULL, '2026-06-01', '2026-12-04', '2026-12-07', 'CANCELLED');

INSERT INTO booking_rooms (
    booking_id,
    floor,
    room_number,
    check_in_on,
    check_out_on,
    nightly_price_cents
) VALUES
    (1, 1, 12, '2026-01-12', '2026-01-15', 12400),
    (2, 2, 12, '2026-01-20', '2026-01-22', 8500),
    (3, 3, 11, '2026-02-03', '2026-02-06', 20900),
    (4, 1, 15, NULL, NULL, 12900),
    (5, 2, 14, '2026-03-06', '2026-03-09', 16900),
    (5, 2, 13, '2026-03-07', '2026-03-09', 11900),
    (6, 3, 13, '2026-03-18', '2026-03-20', 27900),
    (7, 2, 12, NULL, NULL, 8900),
    (8, 1, 14, '2026-05-04', NULL, 13900),
    (9, 3, 12, '2026-06-15', '2026-06-18', 21900),
    (10, 1, 13, NULL, NULL, 12500),
    (11, 3, 14, NULL, NULL, 18900),
    (11, 4, 12, NULL, NULL, 18900),
    (12, 1, 11, NULL, NULL, 8900),
    (13, 4, 11, NULL, NULL, 28900),
    (14, 2, 15, NULL, NULL, 17900);

INSERT INTO booking_services (
    booking_id,
    service_id,
    service_on,
    quantity,
    unit_price_cents
) VALUES
    (1, 1, '2026-01-13', 2, 1800),
    (1, 2, '2026-01-12', 3, 1600),
    (2, 1, '2026-01-21', 2, 1800),
    (3, 3, '2026-02-04', 2, 3500),
    (3, 4, '2026-02-04', 1, 2400),
    (4, 2, '2026-02-21', 4, 1600),
    (5, 1, '2026-03-07', 6, 1800),
    (5, 7, '2026-03-06', 1, 1000),
    (6, 5, '2026-03-20', 1, 3000),
    (7, 1, '2026-04-30', 3, 1900),
    (7, 6, '2026-04-30', 2, 1200),
    (8, 3, '2026-05-05', 1, 3800),
    (9, 2, '2026-06-15', 3, 1700),
    (9, 1, '2026-06-16', 3, 1900),
    (10, 8, '2026-08-10', 1, 2500),
    (11, 1, '2026-09-08', 4, 1900),
    (12, 2, '2026-10-19', 3, 1700),
    (13, 3, '2026-11-12', 2, 3800),
    (13, 5, '2026-11-14', 1, 3000),
    (14, 7, '2026-12-04', 1, 1000);


