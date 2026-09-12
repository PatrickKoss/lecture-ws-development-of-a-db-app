INSERT INTO teachers (id, staff_code, name) VALUES
    (1, 'LK-01', 'Mara Klein'),
    (2, 'LK-02', 'Jonas Becker'),
    (3, 'LK-03', 'Aylin Demir');

INSERT INTO rooms (id, room_code, capacity) VALUES
    (1, 'R-101', 12),
    (2, 'R-204', 6),
    (3, 'SAAL', 30);

INSERT INTO students (id, email, name) VALUES
    (1, 'lea@example.org', 'Lea Sommer'),
    (2, 'noah@example.org', 'Noah Brandt'),
    (3, 'mina@example.org', 'Mina Yilmaz');

INSERT INTO instruments (id, instrument_code, name) VALUES
    (1, 'PIANO', 'Klavier'),
    (2, 'GUITAR', 'Gitarre'),
    (3, 'DRUMS', 'Schlagzeug');

INSERT INTO music_courses (id, course_code, title, fee, level, teacher_id, room_id) VALUES
    (1, 'MU-01', 'Klavier für Einsteiger', 120.00, 'BEGINNER', 1, 2),
    (2, 'MU-02', 'Bandwerkstatt', 95.00, 'INTERMEDIATE', 2, 3),
    (3, 'MU-03', 'Gitarre am Abend', 110.00, 'BEGINNER', 3, 1),
    (4, 'MU-04', 'Jazz-Ensemble online', 150.00, 'ADVANCED', 2, NULL);

INSERT INTO enrollments (course_id, student_id, enrolled_at, status) VALUES
    (1, 1, '2026-08-20', 'ACTIVE'),
    (1, 2, '2026-08-21', 'ACTIVE'),
    (2, 2, '2026-08-22', 'WAITING'),
    (3, 3, '2026-08-23', 'ACTIVE');

INSERT INTO course_instruments (course_id, instrument_id, quantity_required) VALUES
    (1, 1, 1),
    (2, 2, 2),
    (2, 3, 1),
    (3, 2, 1);
