PRAGMA foreign_keys = ON;

-- Kunden mit fachlichen Alternativschlüsseln und Anlagedatum.
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_number TEXT NOT NULL UNIQUE
        CHECK (customer_number GLOB 'K-[0-9][0-9][0-9][0-9]'),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    phone TEXT NOT NULL UNIQUE CHECK (length(trim(phone)) BETWEEN 8 AND 30),
    created_on TEXT NOT NULL
        CHECK (
            date(created_on) IS NOT NULL
            AND created_on = date(created_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Lieferadressen gehören genau einem Kunden.
CREATE TABLE IF NOT EXISTS addresses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    label TEXT NOT NULL CHECK (length(trim(label)) BETWEEN 1 AND 30),
    street TEXT NOT NULL CHECK (length(trim(street)) BETWEEN 1 AND 100),
    house_number TEXT NOT NULL CHECK (length(trim(house_number)) BETWEEN 1 AND 10),
    postal_code TEXT NOT NULL
        CHECK (
            length(postal_code) = 5
            AND postal_code NOT GLOB '*[^0-9]*'
        ),
    city TEXT NOT NULL CHECK (length(trim(city)) BETWEEN 1 AND 80),
    UNIQUE (customer_id, label),
    UNIQUE (id, customer_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Fahrer können aus dem aktiven Lieferplan genommen werden.
CREATE TABLE IF NOT EXISTS drivers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    driver_number TEXT NOT NULL UNIQUE
        CHECK (driver_number GLOB 'F-[0-9][0-9]'),
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    phone TEXT NOT NULL UNIQUE CHECK (length(trim(phone)) BETWEEN 8 AND 30),
    hired_on TEXT NOT NULL
        CHECK (
            date(hired_on) IS NOT NULL
            AND hired_on = date(hired_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Pizzen bilden die Speisekarte mit Grundpreis und Ofenstation.
CREATE TABLE IF NOT EXISTS pizzas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pizza_number TEXT NOT NULL UNIQUE
        CHECK (pizza_number GLOB 'P-[0-9][0-9]'),
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 100),
    category TEXT NOT NULL CHECK (length(trim(category)) BETWEEN 1 AND 30),
    oven_station TEXT NOT NULL CHECK (length(trim(oven_station)) BETWEEN 1 AND 20),
    base_price REAL NOT NULL CHECK (base_price > 0),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1)),
    UNIQUE (name, oven_station)
);

-- Beläge stehen unabhängig von einzelnen Pizzen zur Verfügung.
CREATE TABLE IF NOT EXISTS toppings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 1 AND 80),
    vegetarian INTEGER NOT NULL CHECK (vegetarian IN (0, 1)),
    allergen TEXT CHECK (allergen IS NULL OR length(trim(allergen)) BETWEEN 1 AND 80)
);

-- Die Pizza-Belag-Zuordnung speichert den Aufpreis der Kombination.
CREATE TABLE IF NOT EXISTS pizza_toppings (
    pizza_id INTEGER NOT NULL,
    topping_id INTEGER NOT NULL,
    extra_charge REAL NOT NULL CHECK (extra_charge >= 0),
    PRIMARY KEY (pizza_id, topping_id),
    FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE CASCADE,
    FOREIGN KEY (topping_id) REFERENCES toppings(id)
);

-- Bestellungen verweisen auf Kunde, optionale Lieferadresse und optionalen Fahrer.
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_number TEXT NOT NULL UNIQUE
        CHECK (order_number GLOB 'B-[0-9][0-9][0-9][0-9][0-9]'),
    customer_id INTEGER NOT NULL,
    delivery_address_id INTEGER,
    driver_id INTEGER,
    ordered_on TEXT NOT NULL
        CHECK (
            date(ordered_on) IS NOT NULL
            AND ordered_on = date(ordered_on)
        ),
    order_type TEXT NOT NULL CHECK (order_type IN ('DELIVERY', 'PICKUP')),
    status TEXT NOT NULL
        CHECK (status IN (
            'RECEIVED',
            'PREPARING',
            'READY',
            'OUT_FOR_DELIVERY',
            'COMPLETED',
            'CANCELLED'
        )),
    delivery_fee REAL NOT NULL DEFAULT 0 CHECK (delivery_fee >= 0),
    CHECK (
        (order_type = 'PICKUP' AND delivery_address_id IS NULL AND driver_id IS NULL)
        OR (order_type = 'DELIVERY' AND delivery_address_id IS NOT NULL)
    ),
    CHECK (status <> 'OUT_FOR_DELIVERY' OR driver_id IS NOT NULL),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (delivery_address_id, customer_id)
        REFERENCES addresses(id, customer_id),
    FOREIGN KEY (driver_id) REFERENCES drivers(id)
);

-- Bestellpositionen sind nur zusammen mit ihrer Bestellung eindeutig.
CREATE TABLE IF NOT EXISTS order_items (
    order_id INTEGER NOT NULL,
    position_number INTEGER NOT NULL CHECK (position_number > 0),
    pizza_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity BETWEEN 1 AND 20),
    unit_price REAL NOT NULL CHECK (unit_price > 0),
    PRIMARY KEY (order_id, position_number),
    UNIQUE (order_id, pizza_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (pizza_id) REFERENCES pizzas(id)
);
