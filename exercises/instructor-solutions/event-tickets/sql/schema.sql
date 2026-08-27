PRAGMA foreign_keys = ON;

-- Spielorte mit eindeutigen Codes und fester Gesamtkapazität.
CREATE TABLE IF NOT EXISTS venues (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    venue_code TEXT NOT NULL UNIQUE
        CHECK (length(trim(venue_code)) BETWEEN 2 AND 12),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 120),
    street TEXT NOT NULL CHECK (length(trim(street)) BETWEEN 1 AND 120),
    postal_code TEXT NOT NULL
        CHECK (
            length(postal_code) = 5
            AND postal_code NOT GLOB '*[^0-9]*'
        ),
    city TEXT NOT NULL CHECK (length(trim(city)) BETWEEN 1 AND 80),
    capacity INTEGER NOT NULL CHECK (capacity BETWEEN 1 AND 50000)
);

-- Externe Veranstalter mit genau einer Kontaktperson für das Kursmodell.
CREATE TABLE IF NOT EXISTS organizers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    organizer_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(organizer_number)) BETWEEN 2 AND 20),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 120),
    contact_person TEXT NOT NULL
        CHECK (length(trim(contact_person)) BETWEEN 1 AND 120),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    phone TEXT NOT NULL CHECK (length(trim(phone)) BETWEEN 6 AND 30)
);

-- Veranstaltungen belegen einen Spielort zu einem Datum und einer Startzeit.
CREATE TABLE IF NOT EXISTS events (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    event_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(event_number)) BETWEEN 2 AND 20),
    title TEXT NOT NULL CHECK (length(trim(title)) BETWEEN 1 AND 200),
    event_type TEXT NOT NULL CHECK (event_type IN ('KONZERT', 'LESUNG')),
    admission_code TEXT NOT NULL CHECK (admission_code IN ('K', 'L')),
    venue_id INTEGER NOT NULL,
    organizer_id INTEGER,
    event_on TEXT NOT NULL
        CHECK (
            date(event_on) IS NOT NULL
            AND event_on = date(event_on)
        ),
    doors_open TEXT NOT NULL
        CHECK (
            time(doors_open) IS NOT NULL
            AND doors_open = strftime('%H:%M:%S', doors_open)
        ),
    starts_at TEXT NOT NULL
        CHECK (
            time(starts_at) IS NOT NULL
            AND starts_at = strftime('%H:%M:%S', starts_at)
            AND starts_at >= doors_open
        ),
    CHECK (
        (event_type = 'KONZERT' AND admission_code = 'K')
        OR (event_type = 'LESUNG' AND admission_code = 'L')
    ),
    UNIQUE (title, admission_code),
    UNIQUE (venue_id, event_on, starts_at),
    FOREIGN KEY (venue_id) REFERENCES venues(id),
    FOREIGN KEY (organizer_id) REFERENCES organizers(id)
);

-- Kategorien sind durch Veranstaltung und Namen gemeinsam identifiziert.
CREATE TABLE IF NOT EXISTS ticket_categories (
    event_id INTEGER NOT NULL,
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 80),
    list_price NUMERIC NOT NULL CHECK (list_price >= 0),
    quota INTEGER NOT NULL CHECK (quota > 0),
    seating_type TEXT NOT NULL CHECK (seating_type IN ('SITZPLATZ', 'STEHPLATZ', 'FREIE_PLATZWAHL')),
    PRIMARY KEY (event_id, name),
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

-- Käufer mit eindeutiger Kundennummer und E-Mail-Adresse.
CREATE TABLE IF NOT EXISTS buyers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    buyer_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(buyer_number)) BETWEEN 2 AND 20),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    registered_on TEXT NOT NULL
        CHECK (
            date(registered_on) IS NOT NULL
            AND registered_on = date(registered_on)
        )
);

-- Bestellungen gehören zu genau einem Käufer.
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_number TEXT NOT NULL UNIQUE
        CHECK (length(trim(order_number)) BETWEEN 2 AND 20),
    buyer_id INTEGER NOT NULL,
    ordered_at TEXT NOT NULL
        CHECK (
            datetime(ordered_at) IS NOT NULL
            AND ordered_at = strftime('%Y-%m-%dT%H:%M:%S', ordered_at)
        ),
    status TEXT NOT NULL CHECK (status IN ('PENDING', 'PAID', 'CANCELLED', 'REFUNDED')),
    FOREIGN KEY (buyer_id) REFERENCES buyers(id)
);

-- Tickets lösen die n:m-Beziehung zwischen Bestellungen und Kategorien auf.
CREATE TABLE IF NOT EXISTS tickets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    category_name TEXT NOT NULL,
    ticket_number INTEGER NOT NULL CHECK (ticket_number > 0),
    seat_label TEXT CHECK (seat_label IS NULL OR length(trim(seat_label)) BETWEEN 1 AND 20),
    price_paid NUMERIC NOT NULL CHECK (price_paid >= 0),
    checked_in_at TEXT
        CHECK (
            checked_in_at IS NULL
            OR (
                datetime(checked_in_at) IS NOT NULL
                AND checked_in_at = strftime('%Y-%m-%dT%H:%M:%S', checked_in_at)
            )
        ),
    UNIQUE (order_id, ticket_number),
    UNIQUE (event_id, seat_label),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (event_id, category_name)
        REFERENCES ticket_categories(event_id, name)
);
