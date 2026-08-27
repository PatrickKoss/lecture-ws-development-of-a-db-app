-- customers: Stammdaten und Kontaktangaben der Auftraggeber.
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_number TEXT NOT NULL UNIQUE
        CHECK (customer_number GLOB 'K-[0-9][0-9][0-9][0-9]'),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    phone TEXT NOT NULL CHECK (length(trim(phone)) BETWEEN 7 AND 25),
    registered_on TEXT NOT NULL
        CHECK (
            date(registered_on) IS NOT NULL
            AND registered_on = date(registered_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- vehicles: Fahrzeuge mit aktuellem und optionalem vorherigem Halter.
CREATE TABLE IF NOT EXISTS vehicles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    owner_id INTEGER NOT NULL,
    previous_owner_id INTEGER,
    licence_plate TEXT NOT NULL UNIQUE
        CHECK (length(trim(licence_plate)) BETWEEN 3 AND 12),
    vin TEXT NOT NULL UNIQUE
        CHECK (
            length(vin) = 17
            AND vin NOT GLOB '*[^A-HJ-NPR-Z0-9]*'
        ),
    manufacturer TEXT NOT NULL CHECK (length(trim(manufacturer)) BETWEEN 1 AND 50),
    model TEXT NOT NULL CHECK (length(trim(model)) BETWEEN 1 AND 80),
    construction_year INTEGER NOT NULL CHECK (construction_year BETWEEN 1950 AND 2026),
    mileage INTEGER NOT NULL CHECK (mileage >= 0),
    UNIQUE (owner_id, licence_plate),
    CHECK (previous_owner_id IS NULL OR previous_owner_id <> owner_id),
    FOREIGN KEY (owner_id) REFERENCES customers(id),
    FOREIGN KEY (previous_owner_id) REFERENCES customers(id)
);

-- mechanics: Beschäftigte, die Arbeitsaufträgen zugeordnet werden.
CREATE TABLE IF NOT EXISTS mechanics (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    personnel_number TEXT NOT NULL UNIQUE
        CHECK (personnel_number GLOB 'ME-[0-9][0-9]'),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    specialization TEXT NOT NULL
        CHECK (specialization IN ('ALLGEMEIN', 'MOTOR', 'ELEKTRIK', 'KAROSSERIE', 'DIAGNOSE')),
    hourly_rate REAL NOT NULL CHECK (hourly_rate BETWEEN 20 AND 250),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- parts: Teilekatalog mit Bestand, Meldebestand und Listenpreis.
CREATE TABLE IF NOT EXISTS parts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    part_number TEXT NOT NULL UNIQUE
        CHECK (part_number GLOB 'P-[0-9][0-9][0-9][0-9]'),
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 150),
    category TEXT NOT NULL CHECK (length(trim(category)) BETWEEN 1 AND 50),
    shelf_code TEXT NOT NULL CHECK (length(trim(shelf_code)) BETWEEN 1 AND 10),
    stock_quantity INTEGER NOT NULL CHECK (stock_quantity >= 0),
    reorder_level INTEGER NOT NULL CHECK (reorder_level >= 0),
    list_price REAL NOT NULL CHECK (list_price >= 0),
    UNIQUE (name, shelf_code)
);

-- work_orders: Werkstattaufträge für genau ein Fahrzeug.
CREATE TABLE IF NOT EXISTS work_orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_number TEXT NOT NULL UNIQUE
        CHECK (order_number GLOB 'A-[0-9][0-9][0-9][0-9][0-9]'),
    vehicle_id INTEGER NOT NULL,
    opened_on TEXT NOT NULL
        CHECK (
            date(opened_on) IS NOT NULL
            AND opened_on = date(opened_on)
        ),
    closed_on TEXT
        CHECK (
            closed_on IS NULL
            OR (
                date(closed_on) IS NOT NULL
                AND closed_on = date(closed_on)
                AND closed_on >= opened_on
            )
        ),
    status TEXT NOT NULL
        CHECK (status IN ('OPEN', 'IN_PROGRESS', 'COMPLETED', 'INVOICED')),
    mileage_in INTEGER NOT NULL CHECK (mileage_in >= 0),
    complaint TEXT NOT NULL CHECK (length(trim(complaint)) BETWEEN 10 AND 500),
    CHECK (
        (status IN ('OPEN', 'IN_PROGRESS') AND closed_on IS NULL)
        OR (status IN ('COMPLETED', 'INVOICED') AND closed_on IS NOT NULL)
    ),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE UNIQUE INDEX IF NOT EXISTS one_open_work_order_per_vehicle
    ON work_orders (vehicle_id)
    WHERE status IN ('OPEN', 'IN_PROGRESS');

-- work_order_mechanics: n:m-Zuordnung mit geleisteten Stunden.
CREATE TABLE IF NOT EXISTS work_order_mechanics (
    work_order_id INTEGER NOT NULL,
    mechanic_id INTEGER NOT NULL,
    hours_worked REAL NOT NULL CHECK (hours_worked > 0 AND hours_worked <= 100),
    PRIMARY KEY (work_order_id, mechanic_id),
    FOREIGN KEY (work_order_id) REFERENCES work_orders(id) ON DELETE CASCADE,
    FOREIGN KEY (mechanic_id) REFERENCES mechanics(id)
);

-- work_order_parts: schwache Teileposition mit Menge und historischem Einzelpreis.
CREATE TABLE IF NOT EXISTS work_order_parts (
    work_order_id INTEGER NOT NULL,
    line_number INTEGER NOT NULL CHECK (line_number > 0),
    part_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price REAL NOT NULL CHECK (unit_price >= 0),
    PRIMARY KEY (work_order_id, line_number),
    UNIQUE (work_order_id, part_id),
    FOREIGN KEY (work_order_id) REFERENCES work_orders(id) ON DELETE CASCADE,
    FOREIGN KEY (part_id) REFERENCES parts(id)
);

-- invoices: optionale Rechnung eines abgeschlossenen Arbeitsauftrags.
CREATE TABLE IF NOT EXISTS invoices (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    invoice_number TEXT NOT NULL UNIQUE
        CHECK (invoice_number GLOB 'R-[0-9][0-9][0-9][0-9][0-9]'),
    work_order_id INTEGER NOT NULL UNIQUE,
    issued_on TEXT NOT NULL
        CHECK (
            date(issued_on) IS NOT NULL
            AND issued_on = date(issued_on)
        ),
    due_on TEXT NOT NULL
        CHECK (
            date(due_on) IS NOT NULL
            AND due_on = date(due_on)
            AND due_on >= issued_on
        ),
    paid_on TEXT
        CHECK (
            paid_on IS NULL
            OR (
                date(paid_on) IS NOT NULL
                AND paid_on = date(paid_on)
                AND paid_on >= issued_on
            )
        ),
    net_amount REAL NOT NULL CHECK (net_amount >= 0),
    tax_amount REAL NOT NULL CHECK (tax_amount >= 0),
    status TEXT NOT NULL CHECK (status IN ('OPEN', 'PAID', 'CANCELLED')),
    CHECK (
        (status = 'PAID' AND paid_on IS NOT NULL)
        OR (status IN ('OPEN', 'CANCELLED') AND paid_on IS NULL)
    ),
    FOREIGN KEY (work_order_id) REFERENCES work_orders(id)
);
