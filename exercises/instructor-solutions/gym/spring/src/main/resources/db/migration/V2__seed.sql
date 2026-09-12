INSERT INTO members (
    id,
    membership_number,
    first_name,
    last_name,
    email,
    joined_on,
    active
) VALUES
    (1, 'M-2001', 'Lena', 'Fischer', 'lena.fischer@example.org', '2026-01-05', 1),
    (2, 'M-2002', 'Mehmet', 'Kaya', 'mehmet.kaya@example.org', '2026-02-01', 1),
    (3, 'M-2003', 'Sophie', 'Wagner', 'sophie.wagner@example.org', '2026-03-01', 1),
    (4, 'M-2004', 'Jonas', 'Becker', 'jonas.becker@example.org', '2026-01-12', 1),
    (5, 'M-2005', 'Aylin', 'Demir', 'aylin.demir@example.org', '2026-04-01', 1),
    (6, 'M-2006', 'Felix', 'Schulte', 'felix.schulte@example.org', '2026-02-15', 1),
    (7, 'M-2007', 'Nina', 'Roth', 'nina.roth@example.org', '2026-05-01', 1),
    (8, 'M-2008', 'David', 'Nguyen', 'david.nguyen@example.org', '2026-06-01', 1),
    (9, 'M-2009', 'Laura', 'Yilmaz', 'laura.yilmaz@example.org', '2026-01-20', 1),
    (10, 'M-2010', 'Tim', 'Hoffmann', 'tim.hoffmann@example.org', '2026-03-18', 1),
    (11, 'M-2011', 'Maja', 'Klein', 'maja.klein@example.org', '2026-07-02', 0),
    (12, 'M-2012', 'Karim', 'Haddad', 'karim.haddad@example.org', '2026-01-08', 0);

INSERT INTO plans (
    id,
    plan_code,
    name,
    monthly_fee_cents,
    minimum_term_months,
    active
) VALUES
    (1, 'BASIC', 'Basis', 2990, 12, 1),
    (2, 'FLEX', 'Flex 12', 4990, 1, 1),
    (3, 'PREMIUM', 'Premium Plus', 7990, 12, 1),
    (4, 'STUDENT', 'Campus Fit', 3490, 6, 1),
    (5, 'MORNING', 'Frühstarter', 3990, 6, 1),
    (6, 'WEEKEND', 'Wochenende', 3290, 6, 1),
    (7, 'FAMILY', 'Familie', 6990, 12, 1),
    (8, 'CORPORATE', 'Firmenfitness', 4490, 3, 1),
    (9, 'SENIOR', 'Aktiv 60', 2790, 6, 1),
    (10, 'DAYPASS', 'Tageskarte', 1490, 0, 0);

INSERT INTO memberships (member_id, plan_id, starts_on, ends_on) VALUES
    (1, 2, '2026-01-05', '2026-06-30'),
    (1, 3, '2026-07-01', NULL),
    (2, 3, '2026-02-01', NULL),
    (3, 4, '2026-03-01', NULL),
    (4, 1, '2026-01-12', NULL),
    (5, 5, '2026-04-01', '2026-06-30'),
    (5, 2, '2026-07-01', NULL),
    (6, 2, '2026-02-15', NULL),
    (7, 6, '2026-05-01', NULL),
    (8, 8, '2026-06-01', NULL),
    (9, 7, '2026-01-20', NULL),
    (10, 9, '2026-03-18', NULL),
    (11, 10, '2026-07-02', '2026-07-02'),
    (12, 2, '2026-01-08', '2026-03-31'),
    (12, 1, '2026-04-01', '2026-06-30');

INSERT INTO trainers (
    id,
    trainer_number,
    first_name,
    last_name,
    email,
    specialty,
    hired_on,
    active
) VALUES
    (1, 'T-01', 'Sarah', 'König', 'sarah.koenig@kraftwerk.example', 'Functional Training', '2026-01-02', 1),
    (2, 'T-02', 'Miriam', 'Scholz', 'miriam.scholz@kraftwerk.example', 'Yoga', '2026-01-03', 1),
    (3, 'T-03', 'Daniel', 'Krüger', 'daniel.krueger@kraftwerk.example', 'Indoor Cycling', '2026-01-04', 1),
    (4, 'T-04', 'Patrick', 'Wolf', 'patrick.wolf@kraftwerk.example', 'HIIT', '2026-01-05', 1),
    (5, 'T-05', 'Anna', 'Lorenz', 'anna.lorenz@kraftwerk.example', 'Rückentraining', '2026-01-06', 1),
    (6, 'T-06', 'Can', 'Arslan', 'can.arslan@kraftwerk.example', 'Boxen', '2026-01-07', 1),
    (7, 'T-07', 'Robert', 'Seidel', 'robert.seidel@kraftwerk.example', 'Langhantel', '2026-01-08', 1),
    (8, 'T-08', 'Elena', 'Marino', 'elena.marino@kraftwerk.example', 'Pilates', '2026-01-09', 1),
    (9, 'T-09', 'Jan', 'Peters', 'jan.peters@kraftwerk.example', 'Mobility', '2026-01-10', 1),
    (10, 'T-10', 'Derya', 'Öztürk', 'derya.oeztuerk@kraftwerk.example', 'Dance Fitness', '2026-01-11', 1);

INSERT INTO rooms (id, room_code, name, capacity, floor) VALUES
    (1, 'KRAFT', 'Kraftzone', 18, 0),
    (2, 'KR-1', 'Kursraum 1', 20, 1),
    (3, 'YOGA', 'Yogaraum', 16, 1),
    (4, 'BIKE', 'Cyclingraum', 14, 0),
    (5, 'BOX', 'Boxraum', 16, 0),
    (6, 'CARDIO', 'Cardiobereich', 24, 0),
    (7, 'OUTDOOR', 'Innenhof', 30, 0),
    (8, 'REHA', 'Reharaum', 12, 1),
    (9, 'DANCE', 'Spiegelsaal', 22, 1),
    (10, 'PT', 'Personal-Training-Raum', 4, 1);

INSERT INTO courses (
    id,
    course_code,
    title,
    level,
    duration_minutes,
    room_id
) VALUES
    (1, 'C-101', 'Functional Basics', 'BEGINNER', 45, 2),
    (2, 'C-102', 'Yoga Flow', 'BEGINNER', 45, 3),
    (3, 'C-103', 'Indoor Cycling', 'INTERMEDIATE', 60, 4),
    (4, 'C-104', 'HIIT Express', 'INTERMEDIATE', 60, 2),
    (5, 'C-105', 'Rückenfit', 'BEGINNER', 45, 3),
    (6, 'C-106', 'Boxtechnik', 'INTERMEDIATE', 60, 5),
    (7, 'C-107', 'Langhantel Pro', 'ADVANCED', 75, 1),
    (8, 'C-108', 'Pilates Core', 'INTERMEDIATE', 60, 3),
    (9, 'C-109', 'Mobility Online', 'BEGINNER', 45, NULL),
    (10, 'C-110', 'Power Yoga', 'ADVANCED', 75, 3),
    (11, 'C-111', 'Zumba Energy', 'INTERMEDIATE', 60, 2),
    (12, 'C-112', 'Lauftraining Online', 'INTERMEDIATE', 60, NULL);

INSERT INTO course_sessions (
    id,
    course_id,
    trainer_id,
    session_date,
    start_time,
    maximum_participants,
    cancelled
) VALUES
    (1, 1, 1, '2026-09-01', '18:00', 12, 0),
    (2, 2, 2, '2026-09-03', '17:30', 15, 0),
    (3, 3, 3, '2026-09-05', '10:00', 12, 0),
    (4, 4, 4, '2026-09-07', '19:00', 10, 0),
    (5, 5, 5, '2026-09-09', '09:00', 15, 0),
    (6, 6, 6, '2026-09-10', '18:30', 12, 0),
    (7, 7, 7, '2026-09-12', '11:00', 10, 0),
    (8, 8, 8, '2026-09-14', '17:00', 14, 0),
    (9, 9, 9, '2026-09-15', '19:00', 40, 0),
    (10, 10, 2, '2026-09-16', '18:00', 12, 0),
    (11, 1, 1, '2026-09-18', '18:00', 12, 0),
    (12, 2, 2, '2026-09-19', '09:00', 15, 0),
    (13, 3, 3, '2026-09-21', '18:00', 12, 0),
    (14, 4, 4, '2026-09-22', '19:00', 10, 0),
    (15, 5, 5, '2026-09-23', '09:00', 15, 0),
    (16, 6, 6, '2026-09-25', '18:30', 12, 0),
    (17, 11, 10, '2026-09-26', '16:00', 18, 1),
    (18, 12, 9, '2026-09-28', '18:00', 40, 0);

INSERT INTO bookings (
    id,
    member_id,
    course_session_id,
    booked_on,
    attended
) VALUES
    (1, 1, 1, '2026-08-20', 1),
    (2, 2, 1, '2026-08-21', 1),
    (3, 3, 1, '2026-08-22', 0),
    (4, 1, 2, '2026-08-24', 1),
    (5, 4, 2, '2026-08-25', 1),
    (6, 2, 3, '2026-08-26', 1),
    (7, 5, 3, '2026-08-27', 1),
    (8, 6, 3, '2026-08-28', 0),
    (9, 7, 3, '2026-08-29', 1),
    (10, 3, 4, '2026-08-30', 1),
    (11, 8, 4, '2026-08-31', 1),
    (12, 9, 4, '2026-09-01', 0),
    (13, 4, 5, '2026-09-02', 1),
    (14, 5, 6, '2026-09-03', 1),
    (15, 6, 6, '2026-09-04', 0),
    (16, 7, 7, '2026-09-04', 1),
    (17, 8, 8, '2026-09-05', 1),
    (18, 9, 8, '2026-09-06', 1),
    (19, 10, 9, '2026-09-07', 0),
    (20, 1, 10, '2026-09-08', 1);


