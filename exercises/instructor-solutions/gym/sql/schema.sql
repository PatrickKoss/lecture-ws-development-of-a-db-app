PRAGMA foreign_keys = ON;

-- Mitglieder des Fitnessstudios mit eindeutigen Kontaktdaten.
CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    membership_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    joined_on TEXT NOT NULL
        CHECK (
            date(joined_on) IS NOT NULL
            AND joined_on = date(joined_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Tarife mit Monatsbeitrag und Mindestlaufzeit.
CREATE TABLE IF NOT EXISTS plans (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    plan_code TEXT NOT NULL UNIQUE CHECK (length(trim(plan_code)) BETWEEN 2 AND 20),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 2 AND 80),
    monthly_fee_cents INTEGER NOT NULL CHECK (monthly_fee_cents > 0),
    minimum_term_months INTEGER NOT NULL CHECK (minimum_term_months BETWEEN 0 AND 24),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Mitgliedschaften verbinden Mitglieder und Tarife über einen Zeitraum.
CREATE TABLE IF NOT EXISTS memberships (
    member_id INTEGER NOT NULL,
    plan_id INTEGER NOT NULL,
    starts_on TEXT NOT NULL
        CHECK (
            date(starts_on) IS NOT NULL
            AND starts_on = date(starts_on)
        ),
    ends_on TEXT
        CHECK (
            ends_on IS NULL
            OR (
                date(ends_on) IS NOT NULL
                AND ends_on = date(ends_on)
                AND ends_on >= starts_on
            )
        ),
    PRIMARY KEY (member_id, starts_on),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (plan_id) REFERENCES plans(id)
);

-- Trainer mit Schwerpunkt und Beschäftigungsstatus.
CREATE TABLE IF NOT EXISTS trainers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    trainer_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    specialty TEXT NOT NULL CHECK (length(trim(specialty)) BETWEEN 2 AND 80),
    hired_on TEXT NOT NULL
        CHECK (
            date(hired_on) IS NOT NULL
            AND hired_on = date(hired_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Räume für Präsenzkurse mit fester Kapazität.
CREATE TABLE IF NOT EXISTS rooms (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    room_code TEXT NOT NULL UNIQUE CHECK (length(trim(room_code)) BETWEEN 2 AND 12),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 2 AND 80),
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    floor INTEGER NOT NULL CHECK (floor BETWEEN -1 AND 5)
);

-- Kurskatalog, bei Online-Kursen bleibt room_id leer.
CREATE TABLE IF NOT EXISTS courses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_code TEXT NOT NULL UNIQUE CHECK (length(trim(course_code)) BETWEEN 2 AND 20),
    title TEXT NOT NULL CHECK (length(trim(title)) BETWEEN 1 AND 100),
    level TEXT NOT NULL CHECK (level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),
    duration_minutes INTEGER NOT NULL,
    room_id INTEGER,
    CHECK (
        (level = 'BEGINNER' AND duration_minutes = 45)
        OR (level = 'INTERMEDIATE' AND duration_minutes = 60)
        OR (level = 'ADVANCED' AND duration_minutes = 75)
    ),
    UNIQUE (title, duration_minutes),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- Einzelne Termine eines Kurses mit Trainer und Teilnehmergrenze.
CREATE TABLE IF NOT EXISTS course_sessions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_id INTEGER NOT NULL,
    trainer_id INTEGER NOT NULL,
    session_date TEXT NOT NULL
        CHECK (
            date(session_date) IS NOT NULL
            AND session_date = date(session_date)
        ),
    start_time TEXT NOT NULL
        CHECK (
            length(start_time) = 5
            AND start_time GLOB '[0-2][0-9]:[0-5][0-9]'
            AND CAST(substr(start_time, 1, 2) AS INTEGER) BETWEEN 0 AND 23
        ),
    maximum_participants INTEGER NOT NULL CHECK (maximum_participants > 0),
    cancelled INTEGER NOT NULL DEFAULT 0 CHECK (cancelled IN (0, 1)),
    UNIQUE (course_id, session_date, start_time),
    UNIQUE (trainer_id, session_date, start_time),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (trainer_id) REFERENCES trainers(id)
);

-- Buchungen verbinden Mitglieder mit Kursterminen und speichern Teilnahmeangaben.
CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    member_id INTEGER NOT NULL,
    course_session_id INTEGER NOT NULL,
    booked_on TEXT NOT NULL
        CHECK (
            date(booked_on) IS NOT NULL
            AND booked_on = date(booked_on)
        ),
    attended INTEGER NOT NULL DEFAULT 0 CHECK (attended IN (0, 1)),
    UNIQUE (member_id, course_session_id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (course_session_id) REFERENCES course_sessions(id)
);
