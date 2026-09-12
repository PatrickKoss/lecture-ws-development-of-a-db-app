-- Lesbare Relationsnotation, kein ausführbares SQL
teachers(id PK, staff_code UK NOT NULL, name NOT NULL)
rooms(id PK, room_code UK NOT NULL, capacity NOT NULL)
students(id PK, email UK NOT NULL, name NOT NULL)
instruments(id PK, instrument_code UK NOT NULL, name NOT NULL)
music_courses(id PK, course_code UK NOT NULL, title NOT NULL, fee NOT NULL, level NOT NULL, teacher_id FK NOT NULL -> teachers.id, room_id FK NULL -> rooms.id)
enrollments(course_id PK FK NOT NULL -> music_courses.id, student_id PK FK NOT NULL -> students.id, enrolled_at NOT NULL, status NOT NULL)
course_instruments(course_id PK FK NOT NULL -> music_courses.id, instrument_id PK FK NOT NULL -> instruments.id, quantity_required NOT NULL)

-- Die beiden Zuordnungstabellen brauchen keine künstliche ID, weil ihre FK-Paare eindeutig sind.
-- NULL bei room_id bedeutet einen Online-Kurs ohne Raum.
