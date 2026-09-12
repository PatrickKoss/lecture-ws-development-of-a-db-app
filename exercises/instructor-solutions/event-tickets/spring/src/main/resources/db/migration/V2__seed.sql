INSERT INTO venues (
    id,
    venue_code,
    name,
    street,
    postal_code,
    city,
    capacity
) VALUES
    (1, 'V-01', 'Zeche Carl', 'Wilhelm-Nieswandt-Allee 100', '45326', 'Essen', 1000),
    (2, 'V-02', 'Bahnhof Langendreer', 'Wallbaumweg 108', '44894', 'Bochum', 800),
    (3, 'V-03', 'Ringlokschuppen', 'Am Schloß Broich 38', '45479', 'Mülheim an der Ruhr', 1200),
    (4, 'V-04', 'Druckluft', 'Am Förderturm 27', '46049', 'Oberhausen', 500),
    (5, 'V-05', 'Pact Zollverein', 'Bullmannaue 20A', '45327', 'Essen', 600),
    (6, 'V-06', 'Lindenbrauerei', 'Rio-Reiser-Weg 1', '59423', 'Unna', 700),
    (7, 'V-07', 'Rotunde', 'Konrad-Adenauer-Platz 3', '44787', 'Bochum', 400),
    (8, 'V-08', 'Maschinenhalle Zweckel', 'Frentroper Straße 74', '45966', 'Gladbeck', 900),
    (9, 'V-09', 'Recklinghäuser Ruhrfestspielhaus', 'Otto-Burrmeister-Allee 1', '45657', 'Recklinghausen', 1100),
    (10, 'V-10', 'Alte Kaue', 'Hertenstraße 18', '45699', 'Herten', 650);

INSERT INTO organizers (
    id,
    organizer_number,
    name,
    contact_person,
    email,
    phone
) VALUES
    (1, 'O-2001', 'Ruhrpott Events', 'Mara Küster', 'mara.kuester@ruhrpott-events.de', '+49 201 5550101'),
    (2, 'O-2002', 'Klangwerk West', 'Tobias Kruse', 'tobias.kruse@klangwerk-west.de', '+49 234 5550102'),
    (3, 'O-2003', 'Lesebühne Ruhr', 'Rana Yilmaz', 'rana.yilmaz@lesebuehne-ruhr.de', '+49 208 5550103'),
    (4, 'O-2004', 'Förderturm Kultur', 'Eva Schulte', 'eva.schulte@foerderturm-kultur.de', '+49 2366 5550104'),
    (5, 'O-2005', 'Revierklang GmbH', 'Deniz Acar', 'deniz.acar@revierklang.de', '+49 231 5550105'),
    (6, 'O-2006', 'Wortwechsel West', 'Pauline Roth', 'pauline.roth@wortwechsel-west.de', '+49 209 5550106'),
    (7, 'O-2007', 'Kanallicht Kultur', 'Mehmet Özkan', 'mehmet.oezkan@kanallicht.de', '+49 2043 5550107'),
    (8, 'O-2008', 'Stahlstadt Konzerte', 'Jule Reimann', 'jule.reimann@stahlstadt-konzerte.de', '+49 203 5550108'),
    (9, 'O-2009', 'Kapitel Zwei Veranstaltungen', 'Svenja Koch', 'svenja.koch@kapitel-zwei.de', '+49 2303 5550109'),
    (10, 'O-2010', 'Nordstern Booking', 'Can Erdem', 'can.erdem@nordstern-booking.de', '+49 209 5550110');

INSERT INTO events (
    id,
    event_number,
    title,
    event_type,
    admission_code,
    venue_id,
    organizer_id,
    event_on,
    doors_open,
    starts_at
) VALUES
    (1, 'E-2601', 'Nachtschicht am Kanal', 'KONZERT', 'K', 1, 1, '2026-03-14', '18:30:00', '20:00:00'),
    (2, 'E-2602', 'Mord im Revier', 'LESUNG', 'L', 2, 3, '2026-04-18', '18:00:00', '19:00:00'),
    (3, 'E-2603', 'Seitenwechsel', 'LESUNG', 'L', 3, NULL, '2026-05-09', '17:30:00', '18:30:00'),
    (4, 'E-2604', 'Stromaufwärts', 'KONZERT', 'K', 1, 2, '2026-06-06', '18:30:00', '20:00:00'),
    (5, 'E-2605', 'Geschichten aus Stahl', 'LESUNG', 'L', 2, 4, '2026-06-20', '18:00:00', '19:00:00'),
    (6, 'E-2606', 'Echo der Fördertürme', 'KONZERT', 'K', 3, 5, '2026-07-04', '18:30:00', '20:00:00'),
    (7, 'E-2607', 'Sommerseiten', 'LESUNG', 'L', 1, 6, '2026-08-15', '17:00:00', '18:00:00'),
    (8, 'E-2608', 'Licht über der Ruhr', 'KONZERT', 'K', 2, 7, '2026-09-12', '18:30:00', '20:00:00'),
    (9, 'E-2609', 'Stimmen im Maschinenhaus', 'LESUNG', 'L', 3, 9, '2026-10-03', '18:00:00', '19:00:00'),
    (10, 'E-2610', 'Winterklang im Revier', 'KONZERT', 'K', 1, 8, '2026-11-21', '18:30:00', '20:00:00');

INSERT INTO ticket_categories (
    event_id,
    name,
    list_price,
    quota,
    seating_type
) VALUES
    (1, 'Standard', 34.00, 600, 'STEHPLATZ'),
    (1, 'Premium', 52.00, 120, 'SITZPLATZ'),
    (2, 'Freie Platzwahl', 24.00, 450, 'FREIE_PLATZWAHL'),
    (3, 'Standard', 20.00, 500, 'FREIE_PLATZWAHL'),
    (3, 'Premium', 32.00, 80, 'SITZPLATZ'),
    (4, 'Stehplatz', 29.00, 750, 'STEHPLATZ'),
    (5, 'Standard', 23.00, 400, 'FREIE_PLATZWAHL'),
    (5, 'Loge', 39.00, 60, 'SITZPLATZ'),
    (6, 'Freie Platzwahl', 31.00, 900, 'FREIE_PLATZWAHL'),
    (7, 'Standard', 21.00, 500, 'FREIE_PLATZWAHL'),
    (8, 'Standard', 36.00, 600, 'STEHPLATZ'),
    (8, 'Premium', 58.00, 100, 'SITZPLATZ'),
    (9, 'Freie Platzwahl', 25.00, 650, 'FREIE_PLATZWAHL'),
    (10, 'Standard', 38.00, 700, 'STEHPLATZ'),
    (10, 'Meet & Greet', 79.00, 40, 'SITZPLATZ');

INSERT INTO buyers (
    id,
    buyer_number,
    first_name,
    last_name,
    email,
    registered_on
) VALUES
    (1, 'K-1001', 'Aylin', 'Demir', 'aylin.demir@example.org', '2026-01-04'),
    (2, 'K-1002', 'Jonas', 'Feld', 'jonas.feld@example.org', '2026-01-06'),
    (3, 'K-1003', 'Miriam', 'Scholz', 'miriam.scholz@example.org', '2026-01-09'),
    (4, 'K-1004', 'Cem', 'Karaca', 'cem.karaca@example.org', '2026-01-12'),
    (5, 'K-1005', 'Nele', 'Braun', 'nele.braun@example.org', '2026-01-15'),
    (6, 'K-1006', 'Luca', 'Winter', 'luca.winter@example.org', '2026-01-18'),
    (7, 'K-1007', 'Sofia', 'Nguyen', 'sofia.nguyen@example.org', '2026-01-22'),
    (8, 'K-1008', 'David', 'Mertens', 'david.mertens@example.org', '2026-01-25'),
    (9, 'K-1009', 'Elif', 'Arslan', 'elif.arslan@example.org', '2026-02-02'),
    (10, 'K-1010', 'Tom', 'Reuter', 'tom.reuter@example.org', '2026-02-06'),
    (11, 'K-1011', 'Mina', 'Saleh', 'mina.saleh@example.org', '2026-02-11'),
    (12, 'K-1012', 'Felix', 'Nowak', 'felix.nowak@example.org', '2026-02-15');

INSERT INTO orders (
    id,
    order_number,
    buyer_id,
    ordered_at,
    status
) VALUES
    (1, 'B-26001', 1, '2026-01-10T10:12:00', 'PAID'),
    (2, 'B-26002', 2, '2026-01-11T14:05:00', 'PAID'),
    (3, 'B-26003', 3, '2026-01-19T09:44:00', 'PAID'),
    (4, 'B-26004', 4, '2026-01-21T17:20:00', 'PAID'),
    (5, 'B-26005', 5, '2026-02-01T11:08:00', 'PAID'),
    (6, 'B-26006', 6, '2026-02-05T20:14:00', 'PAID'),
    (7, 'B-26007', 7, '2026-02-12T08:31:00', 'PAID'),
    (8, 'B-26008', 8, '2026-02-18T16:27:00', 'PAID'),
    (9, 'B-26009', 9, '2026-03-02T13:11:00', 'PAID'),
    (10, 'B-26010', 10, '2026-03-08T19:03:00', 'PAID'),
    (11, 'B-26011', 11, '2026-03-12T15:42:00', 'CANCELLED'),
    (12, 'B-26012', 12, '2026-03-18T10:55:00', 'PENDING');

INSERT INTO tickets (
    id,
    order_id,
    event_id,
    category_name,
    ticket_number,
    seat_label,
    price_paid,
    checked_in_at
) VALUES
    (1, 1, 1, 'Standard', 1, NULL, 34.00, '2026-03-14T18:47:00'),
    (2, 1, 1, 'Standard', 2, NULL, 34.00, '2026-03-14T18:51:00'),
    (3, 2, 1, 'Premium', 1, 'A-12', 49.00, '2026-03-14T18:43:00'),
    (4, 2, 1, 'Premium', 2, 'A-13', 52.00, NULL),
    (5, 3, 2, 'Freie Platzwahl', 1, NULL, 24.00, '2026-04-18T18:18:00'),
    (6, 4, 2, 'Freie Platzwahl', 1, NULL, 22.00, '2026-04-18T18:22:00'),
    (7, 4, 2, 'Freie Platzwahl', 2, NULL, 22.00, '2026-04-18T18:24:00'),
    (8, 5, 3, 'Standard', 1, NULL, 20.00, NULL),
    (9, 5, 3, 'Premium', 2, 'B-08', 30.00, NULL),
    (10, 6, 4, 'Stehplatz', 1, NULL, 29.00, NULL),
    (11, 6, 4, 'Stehplatz', 2, NULL, 29.00, NULL),
    (12, 6, 4, 'Stehplatz', 3, NULL, 27.00, NULL),
    (13, 7, 5, 'Standard', 1, NULL, 23.00, NULL),
    (14, 8, 6, 'Freie Platzwahl', 1, NULL, 31.00, NULL),
    (15, 9, 7, 'Standard', 1, NULL, 21.00, NULL),
    (16, 9, 7, 'Standard', 2, NULL, 21.00, NULL),
    (17, 10, 8, 'Standard', 1, NULL, 36.00, NULL),
    (18, 10, 8, 'Premium', 2, 'P-07', 58.00, NULL);


