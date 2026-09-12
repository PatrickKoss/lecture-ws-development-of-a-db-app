CREATE TABLE teachers (
    id INTEGER PRIMARY KEY,
    staff_code TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL
);

CREATE TABLE rooms (
    id INTEGER PRIMARY KEY,
    room_code TEXT NOT NULL UNIQUE,
    capacity INTEGER NOT NULL CHECK (capacity > 0)
);

CREATE TABLE students (
    id INTEGER PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL
);

CREATE TABLE instruments (
    id INTEGER PRIMARY KEY,
    instrument_code TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL
);

CREATE TABLE music_courses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_code TEXT NOT NULL UNIQUE,
    title TEXT NOT NULL,
    fee NUMERIC NOT NULL CHECK (fee >= 0 AND fee <= 500),
    level TEXT NOT NULL DEFAULT 'BEGINNER'
        CHECK (level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),
    teacher_id INTEGER NOT NULL DEFAULT 1,
    room_id INTEGER DEFAULT 1,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

CREATE TABLE enrollments (
    course_id INTEGER NOT NULL,
    student_id INTEGER NOT NULL,
    enrolled_at TEXT NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('ACTIVE', 'WAITING', 'CANCELLED')),
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES music_courses(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

CREATE TABLE course_instruments (
    course_id INTEGER NOT NULL,
    instrument_id INTEGER NOT NULL,
    quantity_required INTEGER NOT NULL CHECK (quantity_required > 0),
    PRIMARY KEY (course_id, instrument_id),
    FOREIGN KEY (course_id) REFERENCES music_courses(id) ON DELETE CASCADE,
    FOREIGN KEY (instrument_id) REFERENCES instruments(id)
);
