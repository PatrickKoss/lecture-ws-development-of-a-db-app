INSERT INTO depots (id, code, city) VALUES
    (1, 'MS-01', 'Münster'),
    (2, 'DO-01', 'Dortmund'),
    (3, 'BI-01', 'Bielefeld');

INSERT INTO customers (id, email, name) VALUES
    (1, 'versand@buchladen.de', 'Buchladen Ost'),
    (2, 'lager@radteile.de', 'Radteile West'),
    (3, 'shop@keramik.de', 'Keramikwerkstatt Mohn');

INSERT INTO parcels (id, tracking_code, recipient, weight, origin_depot_id, destination_depot_id, sender_id) VALUES
    (1, 'PK-01', 'Nora Weiss', 2.4, 1, 2, 1),
    (2, 'PK-02', 'David Kern', 8.7, 2, 3, 2),
    (3, 'PK-03', 'Samira Yilmaz', 1.1, 3, 1, 3),
    (4, 'PK-04', 'Leo Hartung', 4.5, 1, 3, 1);

INSERT INTO status_events (parcel_id, event_number, status, recorded_at, depot_id) VALUES
    (1, 1, 'ACCEPTED', '2026-09-01T08:15:00Z', 1),
    (1, 2, 'IN_TRANSIT', '2026-09-01T18:40:00Z', 1),
    (2, 1, 'ACCEPTED', '2026-09-01T09:05:00Z', 2),
    (3, 1, 'ACCEPTED', '2026-09-02T07:20:00Z', 3);

INSERT INTO delivery_attempts (id, parcel_id, attempted_at, outcome) VALUES
    (1, 2, '2026-09-02T14:10:00Z', 'ABSENT'),
    (2, 3, '2026-09-02T16:25:00Z', 'DELIVERED'),
    (3, 2, '2026-09-03T10:20:00Z', 'DELIVERED');
