PRAGMA foreign_keys = ON;

-- Filme im Programm des Kinos.
CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    movie_code TEXT NOT NULL UNIQUE
        CHECK (length(trim(movie_code)) BETWEEN 1 AND 20),
    title TEXT NOT NULL CHECK (length(trim(title)) BETWEEN 1 AND 200),
    release_year INTEGER NOT NULL CHECK (release_year BETWEEN 1888 AND 2100),
    duration_minutes INTEGER NOT NULL CHECK (duration_minutes BETWEEN 1 AND 600),
    fsk_code TEXT NOT NULL
        CHECK (fsk_code IN ('FSK_0', 'FSK_6', 'FSK_12', 'FSK_16', 'FSK_18')),
    minimum_age INTEGER NOT NULL CHECK (minimum_age IN (0, 6, 12, 16, 18)),
    UNIQUE (title, minimum_age),
    CHECK (
        (fsk_code = 'FSK_0' AND minimum_age = 0)
        OR (fsk_code = 'FSK_6' AND minimum_age = 6)
        OR (fsk_code = 'FSK_12' AND minimum_age = 12)
        OR (fsk_code = 'FSK_16' AND minimum_age = 16)
        OR (fsk_code = 'FSK_18' AND minimum_age = 18)
    )
);

-- Säle des Programmkinos.
CREATE TABLE IF NOT EXISTS halls (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    hall_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(hall_number)) BETWEEN 1 AND 10),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 80),
    capacity INTEGER NOT NULL CHECK (capacity BETWEEN 1 AND 500)
);

-- Sitzplätze, die nur innerhalb eines Saals eindeutig sind.
CREATE TABLE IF NOT EXISTS seats (
    hall_id INTEGER NOT NULL,
    row_label TEXT NOT NULL CHECK (length(trim(row_label)) BETWEEN 1 AND 3),
    seat_number INTEGER NOT NULL CHECK (seat_number > 0),
    category TEXT NOT NULL CHECK (category IN ('PARKETT', 'LOGE')),
    accessible INTEGER NOT NULL DEFAULT 0 CHECK (accessible IN (0, 1)),
    PRIMARY KEY (hall_id, row_label, seat_number),
    FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE CASCADE
);

-- Geplante Vorstellungen eines Films in einem Saal.
CREATE TABLE IF NOT EXISTS screenings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    screening_code TEXT NOT NULL UNIQUE
        CHECK (length(trim(screening_code)) BETWEEN 1 AND 20),
    movie_id INTEGER NOT NULL,
    hall_id INTEGER NOT NULL,
    starts_at TEXT NOT NULL
        CHECK (
            datetime(starts_at) IS NOT NULL
            AND starts_at = strftime('%Y-%m-%d %H:%M:%S', starts_at)
        ),
    language TEXT NOT NULL CHECK (language IN ('DE', 'OV', 'OMU')),
    projection_format TEXT NOT NULL
        CHECK (projection_format IN ('DCP_2D', 'DCP_3D', '35MM')),
    UNIQUE (hall_id, starts_at),
    UNIQUE (id, hall_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (hall_id) REFERENCES halls(id)
);

-- Registrierte Kundenkonten für optionale Ticketzuordnungen.
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(customer_number)) BETWEEN 1 AND 20),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    registered_on TEXT NOT NULL
        CHECK (
            date(registered_on) IS NOT NULL
            AND registered_on = date(registered_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Verkauf eines Sitzes für eine Vorstellung, optional mit Kundenkonto.
CREATE TABLE IF NOT EXISTS tickets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ticket_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(ticket_number)) BETWEEN 1 AND 30),
    screening_id INTEGER NOT NULL,
    hall_id INTEGER NOT NULL,
    row_label TEXT NOT NULL,
    seat_number INTEGER NOT NULL,
    customer_id INTEGER,
    price_cents INTEGER NOT NULL CHECK (price_cents >= 0),
    sold_at TEXT NOT NULL
        CHECK (
            datetime(sold_at) IS NOT NULL
            AND sold_at = strftime('%Y-%m-%d %H:%M:%S', sold_at)
        ),
    UNIQUE (screening_id, hall_id, row_label, seat_number),
    FOREIGN KEY (screening_id, hall_id) REFERENCES screenings(id, hall_id),
    FOREIGN KEY (hall_id, row_label, seat_number)
        REFERENCES seats(hall_id, row_label, seat_number),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
