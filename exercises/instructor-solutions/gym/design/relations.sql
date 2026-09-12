-- Lesbare Relationsnotation, kein ausführbares SQL
members(id PK, membership_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, joined_on NOT NULL, active NOT NULL)
plans(id PK, plan_code UK NOT NULL, name UK NOT NULL, monthly_fee_cents NOT NULL, minimum_term_months NOT NULL, active NOT NULL)
memberships(member_id PK FK NOT NULL -> members.id, plan_id FK NOT NULL -> plans.id, starts_on PK NOT NULL, ends_on NULL)
trainers(id PK, trainer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, specialty NOT NULL, hired_on NOT NULL, active NOT NULL)
rooms(id PK, room_code UK NOT NULL, name UK NOT NULL, capacity NOT NULL, floor NOT NULL)
courses(id PK, course_code UK NOT NULL, title NOT NULL, level NOT NULL, duration_minutes NOT NULL, room_id FK NULL -> rooms.id, UK(title, duration_minutes))
course_sessions(id PK, course_id FK NOT NULL -> courses.id, trainer_id FK NOT NULL -> trainers.id, session_date NOT NULL, start_time NOT NULL, maximum_participants NOT NULL, cancelled NOT NULL, UK(course_id, session_date, start_time), UK(trainer_id, session_date, start_time))
bookings(id PK, member_id FK NOT NULL -> members.id, course_session_id FK NOT NULL -> course_sessions.id, booked_on NOT NULL, attended NOT NULL, UK(member_id, course_session_id))

-- Mitglied und Startdatum identifizieren einen Abschnitt der Mitgliedschaftshistorie.
-- NULL bei room_id bedeutet, dass der Kurs keinen festen Stammraum hat.
