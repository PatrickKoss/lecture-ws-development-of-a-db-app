-- Betriebsstandorte, darunter sechs aktive Stationen in Münster.
CREATE TABLE IF NOT EXISTS stations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    station_code TEXT NOT NULL UNIQUE
        CHECK (length(station_code) BETWEEN 3 AND 8),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 80),
    address TEXT NOT NULL UNIQUE CHECK (length(trim(address)) BETWEEN 1 AND 120),
    capacity INTEGER NOT NULL CHECK (capacity BETWEEN 5 AND 100),
    status TEXT NOT NULL CHECK (status IN ('ACTIVE', 'PLANNED', 'CLOSED'))
);

-- Herstellerdaten und technische Regeln für die eingesetzten Radmodelle.
CREATE TABLE IF NOT EXISTS bike_models (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    model_code TEXT NOT NULL UNIQUE
        CHECK (length(model_code) BETWEEN 3 AND 12),
    manufacturer TEXT NOT NULL CHECK (length(trim(manufacturer)) BETWEEN 1 AND 80),
    model_name TEXT NOT NULL CHECK (length(trim(model_name)) BETWEEN 1 AND 100),
    category TEXT NOT NULL
        CHECK (category IN ('CITY', 'E_BIKE', 'TREKKING', 'FOLDING', 'CARGO')),
    service_interval_days INTEGER NOT NULL CHECK (service_interval_days BETWEEN 30 AND 365),
    UNIQUE (model_name, service_interval_days)
);

-- Preisregeln, die Kunden ab einem bestimmten Datum zugeordnet werden können.
CREATE TABLE IF NOT EXISTS tariffs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tariff_code TEXT NOT NULL UNIQUE
        CHECK (length(tariff_code) BETWEEN 3 AND 12),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 80),
    base_fee_cents INTEGER NOT NULL CHECK (base_fee_cents >= 0),
    minute_price_cents INTEGER NOT NULL CHECK (minute_price_cents >= 0),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Registrierte Kunden; ein Tarif ist nicht für jeden Kunden erforderlich.
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_number TEXT NOT NULL UNIQUE,
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

-- Tarifhistorie eines Kunden; valid_from ist ein Attribut der Zuordnung.
CREATE TABLE IF NOT EXISTS customer_tariffs (
    customer_id INTEGER NOT NULL,
    tariff_id INTEGER NOT NULL,
    valid_from TEXT NOT NULL
        CHECK (
            date(valid_from) IS NOT NULL
            AND valid_from = date(valid_from)
        ),
    PRIMARY KEY (customer_id, valid_from),
    UNIQUE (customer_id, tariff_id, valid_from),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (tariff_id) REFERENCES tariffs(id)
);

-- Physische Räder mit Modell und optionalem aktuellem Standort.
CREATE TABLE IF NOT EXISTS bikes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    bike_number TEXT NOT NULL UNIQUE,
    model_id INTEGER NOT NULL,
    current_station_id INTEGER,
    status TEXT NOT NULL CHECK (status IN ('AVAILABLE', 'RENTED', 'MAINTENANCE')),
    commissioned_on TEXT NOT NULL
        CHECK (
            date(commissioned_on) IS NOT NULL
            AND commissioned_on = date(commissioned_on)
            AND commissioned_on >= '2026-01-01'
        ),
    CHECK (
        (status = 'AVAILABLE' AND current_station_id IS NOT NULL)
        OR status = 'MAINTENANCE'
        OR (status = 'RENTED' AND current_station_id IS NULL)
    ),
    FOREIGN KEY (model_id) REFERENCES bike_models(id),
    FOREIGN KEY (current_station_id) REFERENCES stations(id)
);

-- Reparaturhistorie; die laufende Nummer beginnt bei jedem Rad erneut.
CREATE TABLE IF NOT EXISTS maintenance_logs (
    bike_id INTEGER NOT NULL,
    sequence_number INTEGER NOT NULL CHECK (sequence_number > 0),
    logged_on TEXT NOT NULL
        CHECK (
            date(logged_on) IS NOT NULL
            AND logged_on = date(logged_on)
        ),
    issue TEXT NOT NULL CHECK (length(trim(issue)) BETWEEN 3 AND 200),
    action_taken TEXT NOT NULL CHECK (length(trim(action_taken)) BETWEEN 3 AND 200),
    cost_cents INTEGER NOT NULL CHECK (cost_cents >= 0),
    PRIMARY KEY (bike_id, sequence_number),
    FOREIGN KEY (bike_id) REFERENCES bikes(id) ON DELETE CASCADE
);

-- Attributtragende n:m-Beziehung zwischen Kunden und Rädern.
CREATE TABLE IF NOT EXISTS rentals (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    rental_number TEXT NOT NULL UNIQUE,
    customer_id INTEGER NOT NULL,
    bike_id INTEGER NOT NULL,
    start_station_id INTEGER NOT NULL,
    end_station_id INTEGER,
    start_time TEXT NOT NULL
        CHECK (
            datetime(start_time) IS NOT NULL
            AND start_time = strftime('%Y-%m-%dT%H:%M:%S', start_time)
        ),
    end_time TEXT
        CHECK (
            end_time IS NULL
            OR (
                datetime(end_time) IS NOT NULL
                AND end_time = strftime('%Y-%m-%dT%H:%M:%S', end_time)
                AND end_time >= start_time
            )
        ),
    price_cents INTEGER CHECK (price_cents >= 0),
    CHECK (
        (end_time IS NULL AND end_station_id IS NULL AND price_cents IS NULL)
        OR (end_time IS NOT NULL AND end_station_id IS NOT NULL AND price_cents IS NOT NULL)
    ),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (bike_id) REFERENCES bikes(id),
    FOREIGN KEY (start_station_id) REFERENCES stations(id),
    FOREIGN KEY (end_station_id) REFERENCES stations(id)
);

CREATE UNIQUE INDEX IF NOT EXISTS one_open_rental_per_bike
    ON rentals (bike_id)
    WHERE end_time IS NULL;
