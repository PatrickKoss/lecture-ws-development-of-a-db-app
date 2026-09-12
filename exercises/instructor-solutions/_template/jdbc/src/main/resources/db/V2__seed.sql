PRAGMA foreign_keys = ON;

BEGIN TRANSACTION;

INSERT INTO reference_items (id, name) VALUES
    (1, 'Messgerät'),
    (2, 'Werkzeug'),
    (3, 'Transportgerät');

INSERT INTO locations (id, location_code, name) VALUES
    (1, 'L-01', 'Hauptlager'),
    (2, 'L-02', 'Werkstatt'),
    (3, 'L-03', 'Außenstelle Nord');

INSERT INTO custodians (id, personnel_code, name, email) VALUES
    (1, 'P-100', 'Anna Weber', 'anna.weber@example.org'),
    (2, 'P-101', 'Bilal Demir', 'bilal.demir@example.org'),
    (3, 'P-102', 'Clara Jansen', 'clara.jansen@example.org');

INSERT INTO resources (id, resource_code, name, measure, reference_item_id, current_location_id) VALUES
    (1, 'R-01', 'Digitalwaage', 15.0, 1, 1),
    (2, 'R-02', 'Akkuschrauber', 18.0, 2, NULL),
    (3, 'R-03', 'Transportwagen', 250.0, 3, 3),
    (4, 'R-04', 'Drehmomentschlüssel', 80.0, 2, 2);

INSERT INTO allocations (resource_id, sequence_number, custodian_id, allocated_at, returned_at) VALUES
    (1, 1, 1, '2026-09-01T08:00:00', '2026-09-02T16:00:00'),
    (2, 1, 2, '2026-09-03T09:30:00', NULL),
    (3, 1, 3, '2026-09-04T07:45:00', '2026-09-04T12:10:00');

INSERT INTO inspections (resource_id, sequence_number, inspected_on, result) VALUES
    (1, 1, '2026-08-20', 'PASSED'),
    (2, 1, '2026-08-21', 'PASSED'),
    (3, 1, '2026-08-22', 'REPAIR_REQUIRED');

COMMIT;
