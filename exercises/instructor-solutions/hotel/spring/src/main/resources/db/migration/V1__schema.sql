-- Gäste und optionale Angaben zu einem Firmenkonto.
CREATE TABLE IF NOT EXISTS guests (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    guest_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    phone TEXT NOT NULL CHECK (length(trim(phone)) BETWEEN 7 AND 30),
    company_name TEXT,
    company_account_number TEXT UNIQUE,
    registered_on TEXT NOT NULL
        CHECK (
            date(registered_on) IS NOT NULL
            AND registered_on = date(registered_on)
        ),
    CHECK (
        (company_name IS NULL AND company_account_number IS NULL)
        OR (
            company_name IS NOT NULL
            AND company_account_number IS NOT NULL
            AND length(trim(company_name)) > 0
            AND length(trim(company_account_number)) > 0
        )
    )
);

-- Zimmertypen mit Kapazität und aktuellem Standardpreis.
CREATE TABLE IF NOT EXISTS room_types (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type_code TEXT NOT NULL UNIQUE CHECK (length(trim(type_code)) BETWEEN 1 AND 10),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 80),
    capacity INTEGER NOT NULL CHECK (capacity BETWEEN 1 AND 6),
    standard_price_cents INTEGER NOT NULL CHECK (standard_price_cents > 0)
);

-- Zimmer, identifiziert durch Etage und Zimmernummer.
CREATE TABLE IF NOT EXISTS rooms (
    floor INTEGER NOT NULL CHECK (floor BETWEEN 1 AND 4),
    room_number INTEGER NOT NULL CHECK (room_number BETWEEN 1 AND 99),
    room_type_id INTEGER NOT NULL,
    cleaning_area TEXT NOT NULL CHECK (cleaning_area IN ('RHEIN', 'HAFEN', 'MEDIENHAFEN', 'PARK')),
    status TEXT NOT NULL CHECK (status IN ('AVAILABLE', 'OCCUPIED', 'MAINTENANCE')),
    accessible INTEGER NOT NULL DEFAULT 0 CHECK (accessible IN (0, 1)),
    PRIMARY KEY (floor, room_number),
    UNIQUE (room_number, cleaning_area),
    FOREIGN KEY (room_type_id) REFERENCES room_types(id),
    CHECK (
        (floor = 1 AND cleaning_area = 'RHEIN')
        OR (floor = 2 AND cleaning_area = 'HAFEN')
        OR (floor = 3 AND cleaning_area = 'MEDIENHAFEN')
        OR (floor = 4 AND cleaning_area = 'PARK')
    )
);

-- Mitarbeitende der Rezeption und des Gästeservices.
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_code TEXT NOT NULL UNIQUE CHECK (length(trim(employee_code)) BETWEEN 2 AND 12),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    role TEXT NOT NULL CHECK (role IN ('RECEPTION', 'NIGHT_AUDIT', 'MANAGEMENT', 'GUEST_SERVICE')),
    hired_on TEXT NOT NULL
        CHECK (
            date(hired_on) IS NOT NULL
            AND hired_on = date(hired_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Buchbare Zusatzleistungen mit aktuellem Listenpreis.
CREATE TABLE IF NOT EXISTS services (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    service_code TEXT NOT NULL UNIQUE CHECK (length(trim(service_code)) BETWEEN 2 AND 12),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 100),
    list_price_cents INTEGER NOT NULL CHECK (list_price_cents >= 0),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Buchungen eines Gasts mit optionalem Mitarbeiter beim Check-in.
CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_number TEXT NOT NULL UNIQUE CHECK (length(trim(booking_number)) BETWEEN 3 AND 20),
    guest_id INTEGER NOT NULL,
    checked_in_by_employee_id INTEGER,
    booked_on TEXT NOT NULL
        CHECK (
            date(booked_on) IS NOT NULL
            AND booked_on = date(booked_on)
        ),
    arrival_on TEXT NOT NULL
        CHECK (
            date(arrival_on) IS NOT NULL
            AND arrival_on = date(arrival_on)
            AND arrival_on >= booked_on
        ),
    departure_on TEXT NOT NULL
        CHECK (
            date(departure_on) IS NOT NULL
            AND departure_on = date(departure_on)
            AND departure_on > arrival_on
        ),
    status TEXT NOT NULL
        CHECK (status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED')),
    FOREIGN KEY (guest_id) REFERENCES guests(id),
    FOREIGN KEY (checked_in_by_employee_id) REFERENCES employees(id),
    CHECK (
        status NOT IN ('CHECKED_IN', 'CHECKED_OUT')
        OR checked_in_by_employee_id IS NOT NULL
    )
);

-- Zimmer einer Buchung mit tatsächlichem Check-in und Check-out pro Zimmer.
CREATE TABLE IF NOT EXISTS booking_rooms (
    booking_id INTEGER NOT NULL,
    floor INTEGER NOT NULL,
    room_number INTEGER NOT NULL,
    check_in_on TEXT
        CHECK (
            check_in_on IS NULL
            OR (date(check_in_on) IS NOT NULL AND check_in_on = date(check_in_on))
        ),
    check_out_on TEXT
        CHECK (
            check_out_on IS NULL
            OR (
                date(check_out_on) IS NOT NULL
                AND check_out_on = date(check_out_on)
                AND check_in_on IS NOT NULL
                AND check_out_on >= check_in_on
            )
        ),
    nightly_price_cents INTEGER NOT NULL CHECK (nightly_price_cents > 0),
    PRIMARY KEY (booking_id, floor, room_number),
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
    FOREIGN KEY (floor, room_number) REFERENCES rooms(floor, room_number)
);

-- Berechnete Leistungen einer Buchung mit Menge, Datum und historischem Preis.
CREATE TABLE IF NOT EXISTS booking_services (
    booking_id INTEGER NOT NULL,
    service_id INTEGER NOT NULL,
    service_on TEXT NOT NULL
        CHECK (
            date(service_on) IS NOT NULL
            AND service_on = date(service_on)
        ),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price_cents INTEGER NOT NULL CHECK (unit_price_cents >= 0),
    PRIMARY KEY (booking_id, service_id, service_on),
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id)
);

CREATE INDEX IF NOT EXISTS booking_rooms_by_room
    ON booking_rooms (floor, room_number, booking_id);

CREATE INDEX IF NOT EXISTS bookings_by_guest
    ON bookings (guest_id, arrival_on);
