PRAGMA foreign_keys = ON;

BEGIN TRANSACTION;

INSERT INTO movies (
    id,
    movie_code,
    title,
    release_year,
    duration_minutes,
    fsk_code,
    minimum_age
) VALUES
    (1, 'F-101', 'In die Sonne schauen', 2024, 149, 'FSK_16', 16),
    (2, 'F-102', 'Flow', 2024, 85, 'FSK_6', 6),
    (3, 'F-103', 'Perfect Days', 2023, 123, 'FSK_0', 0),
    (4, 'F-104', 'Konklave', 2024, 120, 'FSK_6', 6),
    (5, 'F-105', 'Anatomie eines Falls', 2023, 152, 'FSK_12', 12),
    (6, 'F-106', 'Die leisen und die großen Töne', 2024, 104, 'FSK_0', 0),
    (7, 'F-107', 'The Zone of Interest', 2023, 105, 'FSK_12', 12),
    (8, 'F-108', 'September 5', 2024, 95, 'FSK_12', 12),
    (9, 'F-109', 'Heldin', 2025, 92, 'FSK_6', 6),
    (10, 'F-110', 'No Other Land', 2024, 95, 'FSK_16', 16),
    (11, 'F-111', 'Emilia Pérez', 2024, 132, 'FSK_12', 12),
    (12, 'F-112', 'Der Buchspazierer', 2024, 98, 'FSK_6', 6);

INSERT INTO halls (id, hall_number, name, capacity) VALUES
    (1, 'S-01', 'Gloria', 148),
    (2, 'S-02', 'Atelier', 96),
    (3, 'S-03', 'Panorama', 120),
    (4, 'S-04', 'Studio', 64),
    (5, 'S-05', 'Luna', 72),
    (6, 'S-06', 'Scala', 88),
    (7, 'S-07', 'Capitol', 110),
    (8, 'S-08', 'Filmforum', 54),
    (9, 'S-09', 'Galerie', 42),
    (10, 'S-10', 'Lichtblick', 36);

INSERT INTO seats (
    hall_id,
    row_label,
    seat_number,
    category,
    accessible
) VALUES
    (1, 'A', 1, 'PARKETT', 1),
    (1, 'A', 2, 'PARKETT', 0),
    (1, 'B', 1, 'LOGE', 0),
    (1, 'B', 2, 'LOGE', 0),
    (2, 'A', 1, 'PARKETT', 1),
    (2, 'A', 2, 'PARKETT', 0),
    (2, 'B', 1, 'LOGE', 0),
    (3, 'A', 1, 'PARKETT', 1),
    (3, 'A', 2, 'PARKETT', 0),
    (4, 'A', 1, 'PARKETT', 1),
    (4, 'A', 2, 'PARKETT', 0),
    (4, 'B', 1, 'LOGE', 0),
    (5, 'A', 1, 'PARKETT', 1),
    (5, 'A', 2, 'PARKETT', 0),
    (6, 'A', 1, 'PARKETT', 1),
    (6, 'A', 2, 'PARKETT', 0),
    (7, 'A', 1, 'PARKETT', 1),
    (8, 'A', 1, 'PARKETT', 1),
    (9, 'A', 1, 'PARKETT', 1),
    (10, 'A', 1, 'PARKETT', 1);

INSERT INTO screenings (
    id,
    screening_code,
    movie_id,
    hall_id,
    starts_at,
    language,
    projection_format
) VALUES
    (1, 'V-26001', 1, 1, '2026-03-05 18:00:00', 'DE', 'DCP_2D'),
    (2, 'V-26002', 2, 2, '2026-03-06 20:15:00', 'DE', 'DCP_2D'),
    (3, 'V-26003', 1, 3, '2026-03-07 17:30:00', 'DE', 'DCP_2D'),
    (4, 'V-26004', 3, 1, '2026-03-12 19:00:00', 'OMU', 'DCP_2D'),
    (5, 'V-26005', 4, 4, '2026-03-14 20:30:00', 'DE', 'DCP_2D'),
    (6, 'V-26006', 5, 5, '2026-03-20 18:45:00', 'DE', 'DCP_2D'),
    (7, 'V-26007', 6, 6, '2026-04-02 19:15:00', 'DE', 'DCP_2D'),
    (8, 'V-26008', 7, 1, '2026-04-10 21:00:00', 'OV', '35MM'),
    (9, 'V-26009', 2, 2, '2026-04-16 17:00:00', 'DE', 'DCP_2D'),
    (10, 'V-26010', 8, 7, '2026-04-18 20:00:00', 'DE', 'DCP_2D'),
    (11, 'V-26011', 9, 1, '2026-04-25 18:30:00', 'DE', 'DCP_2D'),
    (12, 'V-26012', 10, 8, '2026-05-02 20:45:00', 'OMU', 'DCP_2D'),
    (13, 'V-26013', 11, 9, '2026-05-09 19:30:00', 'OV', 'DCP_2D'),
    (14, 'V-26014', 12, 10, '2026-05-15 16:00:00', 'DE', 'DCP_2D');

INSERT INTO customers (
    id,
    customer_number,
    first_name,
    last_name,
    email,
    registered_on,
    active
) VALUES
    (1, 'K-1001', 'Miriam', 'Koch', 'miriam.koch@example.org', '2026-01-08', 1),
    (2, 'K-1002', 'Deniz', 'Yilmaz', 'deniz.yilmaz@example.org', '2026-01-11', 1),
    (3, 'K-1003', 'Lea', 'Winter', 'lea.winter@example.org', '2026-01-19', 1),
    (4, 'K-1004', 'Stefan', 'Reuter', 'stefan.reuter@example.org', '2026-01-24', 1),
    (5, 'K-1005', 'Aylin', 'Demir', 'aylin.demir@example.org', '2026-02-02', 1),
    (6, 'K-1006', 'Nora', 'Schmitz', 'nora.schmitz@example.org', '2026-02-07', 1),
    (7, 'K-1007', 'Jonas', 'Falk', 'jonas.falk@example.org', '2026-02-10', 1),
    (8, 'K-1008', 'Hanna', 'Berg', 'hanna.berg@example.org', '2026-02-15', 1),
    (9, 'K-1009', 'Mehmet', 'Kaya', 'mehmet.kaya@example.org', '2026-02-22', 1),
    (10, 'K-1010', 'Sophie', 'Lange', 'sophie.lange@example.org', '2026-03-01', 1),
    (11, 'K-1011', 'Daniel', 'Krüger', 'daniel.krueger@example.org', '2026-03-04', 0),
    (12, 'K-1012', 'Elif', 'Öztürk', 'elif.oeztuerk@example.org', '2026-03-09', 0);

INSERT INTO tickets (
    id,
    ticket_number,
    screening_id,
    hall_id,
    row_label,
    seat_number,
    customer_id,
    price_cents,
    sold_at
) VALUES
    (1, 'T-260001', 1, 1, 'A', 1, 1, 1150, '2026-02-18 14:12:00'),
    (2, 'T-260002', 1, 1, 'A', 2, NULL, 1150, '2026-02-18 14:15:00'),
    (3, 'T-260003', 1, 1, 'B', 1, 2, 1300, '2026-02-19 09:04:00'),
    (4, 'T-260004', 1, 1, 'B', 2, NULL, 1300, '2026-02-20 20:31:00'),
    (5, 'T-260005', 2, 2, 'A', 1, 3, 1000, '2026-02-21 11:22:00'),
    (6, 'T-260006', 2, 2, 'A', 2, 3, 1000, '2026-02-21 11:25:00'),
    (7, 'T-260007', 2, 2, 'B', 1, NULL, 1150, '2026-02-23 16:40:00'),
    (8, 'T-260008', 3, 3, 'A', 1, 4, 1150, '2026-02-24 10:01:00'),
    (9, 'T-260009', 3, 3, 'A', 2, 5, 1150, '2026-02-24 10:03:00'),
    (10, 'T-260010', 4, 1, 'A', 1, NULL, 950, '2026-03-01 18:52:00'),
    (11, 'T-260011', 5, 4, 'A', 1, 6, 1200, '2026-03-02 12:17:00'),
    (12, 'T-260012', 5, 4, 'A', 2, 7, 1200, '2026-03-02 12:20:00'),
    (13, 'T-260013', 5, 4, 'B', 1, NULL, 1350, '2026-03-03 08:42:00'),
    (14, 'T-260014', 6, 5, 'A', 1, 8, 1050, '2026-03-05 17:11:00'),
    (15, 'T-260015', 7, 6, 'A', 1, 9, 1000, '2026-03-12 09:35:00'),
    (16, 'T-260016', 7, 6, 'A', 2, NULL, 1000, '2026-03-12 09:38:00'),
    (17, 'T-260017', 8, 1, 'A', 1, 10, 1250, '2026-03-20 18:26:00'),
    (18, 'T-260018', 9, 2, 'A', 1, NULL, 1000, '2026-04-01 13:14:00');

COMMIT;
