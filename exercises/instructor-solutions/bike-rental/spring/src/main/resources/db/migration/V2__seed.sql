INSERT INTO stations (id, station_code, name, address, capacity, status) VALUES
    (1, 'DOM', 'Domplatz', 'Domplatz 28, 48143 Münster', 24, 'ACTIVE'),
    (2, 'HBF', 'Hauptbahnhof', 'Berliner Platz 25, 48143 Münster', 36, 'ACTIVE'),
    (3, 'HAF', 'Hafen', 'Hafenweg 8, 48155 Münster', 20, 'ACTIVE'),
    (4, 'AAS', 'Aasee', 'Annette-Allee 3, 48149 Münster', 18, 'ACTIVE'),
    (5, 'COK', 'Coesfelder Kreuz', 'Einsteinstraße 62, 48149 Münster', 30, 'ACTIVE'),
    (6, 'GIE', 'Gievenbeck', 'Rüschhausweg 17, 48161 Münster', 16, 'ACTIVE'),
    (7, 'PRE', 'Preußenstadion', 'Hammer Straße 302, 48153 Münster', 22, 'PLANNED'),
    (8, 'HIL', 'Hiltrup Markt', 'Marktallee 66, 48165 Münster', 18, 'PLANNED'),
    (9, 'ALT', 'Alter Steinweg', 'Alter Steinweg 21, 48143 Münster', 10, 'CLOSED'),
    (10, 'KRE', 'Kreuzviertel', 'Hoyastraße 12, 48147 Münster', 12, 'CLOSED');

INSERT INTO bike_models (
    id,
    model_code,
    manufacturer,
    model_name,
    category,
    service_interval_days
) VALUES
    (1, 'BM-01', 'Gazelle', 'Arroyo C7+', 'CITY', 120),
    (2, 'BM-02', 'Kalkhoff', 'Endeavour 1.B Move', 'E_BIKE', 90),
    (3, 'BM-03', 'Cube', 'Touring Hybrid ONE 500', 'E_BIKE', 90),
    (4, 'BM-04', 'Stevens', 'City Flight', 'CITY', 120),
    (5, 'BM-05', 'Diamant', '247', 'CITY', 120),
    (6, 'BM-06', 'Riese & Müller', 'Charger4', 'E_BIKE', 90),
    (7, 'BM-07', 'Trek', 'FX 2 Disc', 'TREKKING', 150),
    (8, 'BM-08', 'Brompton', 'C Line Explore', 'FOLDING', 180),
    (9, 'BM-09', 'Babboe', 'City Mountain', 'CARGO', 60),
    (10, 'BM-10', 'Cannondale', 'Quick 4', 'TREKKING', 150);

INSERT INTO tariffs (
    id,
    tariff_code,
    name,
    base_fee_cents,
    minute_price_cents,
    active
) VALUES
    (1, 'BASIS', 'Basis', 100, 12, 1),
    (2, 'PENDLER', 'Pendler', 0, 9, 1),
    (3, 'KOMFORT', 'Komfort', 250, 7, 1),
    (4, 'WOCHENEND', 'Wochenende', 150, 8, 1),
    (5, 'FAMILIE', 'Familie', 300, 6, 1),
    (6, 'STUDENT', 'Campus', 0, 8, 1),
    (7, 'JOBRAD', 'JobRad Münster', 0, 5, 1),
    (8, 'TOURIST', 'Tagestour', 500, 4, 1),
    (9, 'WINTER', 'Winter 2026', 0, 6, 0),
    (10, 'PILOT', 'Pilotbetrieb', 0, 10, 0);

INSERT INTO customers (
    id,
    customer_number,
    first_name,
    last_name,
    email,
    registered_on,
    active
) VALUES
    (1, 'K-1001', 'Anna', 'Krüger', 'anna.krueger@example.org', '2026-01-02', 1),
    (2, 'K-1002', 'Bilal', 'Demir', 'bilal.demir@example.org', '2026-01-03', 1),
    (3, 'K-1003', 'Clara', 'Hoffmann', 'clara.hoffmann@example.org', '2026-01-04', 1),
    (4, 'K-1004', 'Daniel', 'Yilmaz', 'daniel.yilmaz@example.org', '2026-01-06', 1),
    (5, 'K-1005', 'Elif', 'Schneider', 'elif.schneider@example.org', '2026-01-10', 1),
    (6, 'K-1006', 'Felix', 'Wagner', 'felix.wagner@example.org', '2026-01-12', 1),
    (7, 'K-1007', 'Greta', 'Özdemir', 'greta.oezdemir@example.org', '2026-01-15', 1),
    (8, 'K-1008', 'Hasan', 'Kaya', 'hasan.kaya@example.org', '2026-01-18', 1),
    (9, 'K-1009', 'Ida', 'Becker', 'ida.becker@example.org', '2026-01-22', 1),
    (10, 'K-1010', 'Jonas', 'Roth', 'jonas.roth@example.org', '2026-02-01', 1),
    (11, 'K-1011', 'Leyla', 'Aydin', 'leyla.aydin@example.org', '2026-02-08', 1),
    (12, 'K-1012', 'Moritz', 'Klein', 'moritz.klein@example.org', '2026-02-14', 0);

INSERT INTO customer_tariffs (customer_id, tariff_id, valid_from) VALUES
    (1, 2, '2026-01-01'),
    (1, 3, '2026-03-01'),
    (3, 3, '2026-01-01'),
    (4, 1, '2026-01-01'),
    (5, 2, '2026-02-01'),
    (7, 4, '2026-02-01'),
    (8, 5, '2026-01-15'),
    (10, 6, '2026-02-01'),
    (11, 7, '2026-02-10'),
    (12, 9, '2026-02-14');

INSERT INTO bikes (
    id,
    bike_number,
    model_id,
    current_station_id,
    status,
    commissioned_on
) VALUES
    (1, 'MS-1001', 1, 1, 'AVAILABLE', '2026-01-02'),
    (2, 'MS-1002', 2, NULL, 'RENTED', '2026-01-02'),
    (3, 'MS-1003', 3, 2, 'AVAILABLE', '2026-01-03'),
    (4, 'MS-1004', 4, 4, 'AVAILABLE', '2026-01-03'),
    (5, 'MS-1005', 5, 5, 'AVAILABLE', '2026-01-04'),
    (6, 'MS-1006', 6, NULL, 'RENTED', '2026-01-04'),
    (7, 'MS-1007', 7, 6, 'AVAILABLE', '2026-01-05'),
    (8, 'MS-1008', 8, 2, 'AVAILABLE', '2026-01-05'),
    (9, 'MS-1009', 9, NULL, 'RENTED', '2026-01-06'),
    (10, 'MS-1010', 10, 4, 'AVAILABLE', '2026-01-06'),
    (11, 'MS-1011', 1, 1, 'AVAILABLE', '2026-01-07'),
    (12, 'MS-1012', 2, NULL, 'MAINTENANCE', '2026-01-07'),
    (13, 'MS-1013', 3, 3, 'MAINTENANCE', '2026-01-08'),
    (14, 'MS-1014', 4, 5, 'AVAILABLE', '2026-01-08'),
    (15, 'MS-1015', 5, 6, 'AVAILABLE', '2026-01-09');

INSERT INTO maintenance_logs (
    bike_id,
    sequence_number,
    logged_on,
    issue,
    action_taken,
    cost_cents
) VALUES
    (1, 1, '2026-01-06', 'Licht ohne Funktion', 'Dynamo neu verkabelt', 1800),
    (1, 2, '2026-02-14', 'Kette springt', 'Kette und Ritzel ersetzt', 4600),
    (1, 3, '2026-03-12', 'Sattel locker', 'Sattelklemme ersetzt', 900),
    (2, 1, '2026-01-09', 'Bremse schleift', 'Bremssattel ausgerichtet', 1200),
    (3, 1, '2026-01-13', 'Akku lädt nicht', 'Ladebuchse ersetzt', 6800),
    (3, 2, '2026-03-02', 'Motor setzt aus', 'Motorsensor erneuert', 8900),
    (4, 1, '2026-01-20', 'Schutzblech locker', 'Strebe befestigt', 700),
    (5, 1, '2026-01-23', 'Reifen platt', 'Schlauch ersetzt', 1500),
    (6, 1, '2026-02-03', 'Display ausgefallen', 'Displaystecker ersetzt', 3200),
    (6, 2, '2026-03-10', 'Akkuhalter locker', 'Halter verschraubt', 1100),
    (7, 1, '2026-02-10', 'Schaltung verstellt', 'Schaltung justiert', 1400),
    (8, 1, '2026-02-16', 'Faltgelenk schwergängig', 'Gelenk gereinigt und gefettet', 1900),
    (9, 1, '2026-02-20', 'Ständer gebrochen', 'Doppelständer ersetzt', 5400),
    (9, 2, '2026-03-18', 'Bremsbeläge verschlissen', 'Bremsbeläge ersetzt', 3800),
    (9, 3, '2026-03-21', 'Lenkung hat Spiel', 'Steuersatz eingestellt', 2200),
    (10, 1, '2026-02-01', 'Klingel fehlt', 'Klingel montiert', 600),
    (12, 1, '2026-02-26', 'Akku tiefentladen', 'Akku geprüft und geladen', 2500),
    (12, 2, '2026-03-20', 'Controller meldet Fehler', 'Controller zur Prüfung ausgebaut', 7200);

INSERT INTO rentals (
    id,
    rental_number,
    customer_id,
    bike_id,
    start_station_id,
    end_station_id,
    start_time,
    end_time,
    price_cents
) VALUES
    (1, 'R-26001', 1, 1, 1, 3, '2026-01-05T08:10:00', '2026-01-05T08:42:00', 420),
    (2, 'R-26002', 2, 2, 3, 4, '2026-01-08T17:20:00', '2026-01-08T18:05:00', 690),
    (3, 'R-26003', 3, 3, 2, 1, '2026-01-12T07:48:00', '2026-01-12T08:16:00', 510),
    (4, 'R-26004', 4, 4, 4, 5, '2026-01-17T11:05:00', '2026-01-17T11:39:00', 480),
    (5, 'R-26005', 5, 5, 5, 3, '2026-01-22T14:30:00', '2026-01-22T15:14:00', 570),
    (6, 'R-26006', 6, 6, 3, 2, '2026-02-02T09:12:00', '2026-02-02T09:50:00', 620),
    (7, 'R-26007', 1, 7, 1, 6, '2026-02-09T16:03:00', '2026-02-09T16:51:00', 560),
    (8, 'R-26008', 7, 8, 6, 1, '2026-02-14T10:20:00', '2026-02-14T11:02:00', 590),
    (9, 'R-26009', 8, 9, 2, 3, '2026-02-19T13:15:00', '2026-02-19T14:10:00', 850),
    (10, 'R-26010', 9, 3, 4, 2, '2026-02-23T18:40:00', '2026-02-23T19:09:00', 450),
    (11, 'R-26011', 10, 11, 1, 5, '2026-02-27T07:55:00', '2026-02-27T08:31:00', 390),
    (12, 'R-26012', 2, 1, 5, 1, '2026-03-01T12:10:00', '2026-03-01T12:44:00', 510),
    (13, 'R-26013', 4, 3, 2, 4, '2026-03-03T09:25:00', '2026-03-03T10:03:00', 540),
    (14, 'R-26014', 9, 9, 3, NULL, '2026-03-05T18:40:00', NULL, NULL),
    (15, 'R-26015', 11, 6, 2, NULL, '2026-03-07T06:50:00', NULL, NULL),
    (16, 'R-26016', 1, 2, 4, NULL, '2026-03-10T15:18:00', NULL, NULL);


