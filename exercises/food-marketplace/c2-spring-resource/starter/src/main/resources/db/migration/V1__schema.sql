-- Partnerrestaurants mit dem Provisionssatz der Plattform.
CREATE TABLE IF NOT EXISTS restaurants (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    partner_number TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 100),
    street TEXT NOT NULL CHECK (length(trim(street)) BETWEEN 1 AND 100),
    postal_code TEXT NOT NULL
        CHECK (
            length(postal_code) = 5
            AND postal_code NOT GLOB '*[^0-9]*'
        ),
    city TEXT NOT NULL CHECK (length(trim(city)) BETWEEN 1 AND 60),
    commission_rate REAL NOT NULL CHECK (commission_rate BETWEEN 0 AND 100),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Gerichte eines Restaurants mit aktuellem Listenpreis.
CREATE TABLE IF NOT EXISTS dishes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    restaurant_id INTEGER NOT NULL,
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 120),
    category TEXT NOT NULL CHECK (category IN ('VORSPEISE', 'HAUPTGERICHT', 'DESSERT', 'GETRAENK', 'PIZZA')),
    vat_rate REAL NOT NULL CHECK (vat_rate IN (7, 19)),
    current_price REAL NOT NULL CHECK (current_price > 0),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1)),
    UNIQUE (restaurant_id, name),
    UNIQUE (name, vat_rate),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- Registrierte Kunden der Plattform.
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

-- Kuriere, die Lieferbestellungen übernehmen können.
CREATE TABLE IF NOT EXISTS couriers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    courier_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    phone TEXT NOT NULL UNIQUE CHECK (length(trim(phone)) BETWEEN 8 AND 20),
    vehicle_type TEXT NOT NULL CHECK (vehicle_type IN ('FAHRRAD', 'E_BIKE', 'ROLLER', 'AUTO')),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

-- Bestellungen mit optionaler Kurierzuordnung und deren Zeitpunkten.
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_number TEXT NOT NULL UNIQUE,
    customer_id INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    courier_id INTEGER,
    ordered_at TEXT NOT NULL
        CHECK (
            datetime(ordered_at) IS NOT NULL
            AND ordered_at = datetime(ordered_at)
        ),
    order_type TEXT NOT NULL CHECK (order_type IN ('DELIVERY', 'PICKUP')),
    status TEXT NOT NULL CHECK (status IN ('NEW', 'ACCEPTED', 'PREPARING', 'READY', 'PICKED_UP', 'DELIVERED', 'CANCELLED')),
    picked_up_at TEXT
        CHECK (
            picked_up_at IS NULL
            OR (
                datetime(picked_up_at) IS NOT NULL
                AND picked_up_at = datetime(picked_up_at)
                AND picked_up_at >= ordered_at
            )
        ),
    delivered_at TEXT
        CHECK (
            delivered_at IS NULL
            OR (
                datetime(delivered_at) IS NOT NULL
                AND delivered_at = datetime(delivered_at)
                AND picked_up_at IS NOT NULL
                AND delivered_at >= picked_up_at
            )
        ),
    CHECK (courier_id IS NOT NULL OR (picked_up_at IS NULL AND delivered_at IS NULL)),
    CHECK (order_type = 'DELIVERY' OR (courier_id IS NULL AND picked_up_at IS NULL AND delivered_at IS NULL)),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
    FOREIGN KEY (courier_id) REFERENCES couriers(id)
);

-- Bestellpositionen als attributierte Beziehung zwischen Bestellung und Gericht.
CREATE TABLE IF NOT EXISTS order_items (
    order_id INTEGER NOT NULL,
    position_number INTEGER NOT NULL CHECK (position_number > 0),
    dish_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price REAL NOT NULL CHECK (unit_price > 0),
    PRIMARY KEY (order_id, position_number),
    UNIQUE (order_id, dish_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dishes(id)
);

-- Optionale Bewertung einer abgeschlossenen Bestellung.
CREATE TABLE IF NOT EXISTS reviews (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER NOT NULL UNIQUE,
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT CHECK (comment IS NULL OR length(trim(comment)) BETWEEN 1 AND 500),
    reviewed_on TEXT NOT NULL
        CHECK (
            date(reviewed_on) IS NOT NULL
            AND reviewed_on = date(reviewed_on)
        ),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
