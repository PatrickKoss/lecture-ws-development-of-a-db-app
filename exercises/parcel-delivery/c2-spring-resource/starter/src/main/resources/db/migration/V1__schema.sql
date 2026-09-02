CREATE TABLE depots (
    id INTEGER PRIMARY KEY,
    code TEXT NOT NULL UNIQUE,
    city TEXT NOT NULL
);

CREATE TABLE customers (
    id INTEGER PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL
);

CREATE TABLE parcels (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tracking_code TEXT NOT NULL UNIQUE,
    recipient TEXT NOT NULL,
    weight NUMERIC NOT NULL CHECK (weight >= 0 AND weight <= 31.5),
    origin_depot_id INTEGER DEFAULT 1,
    destination_depot_id INTEGER DEFAULT 2,
    sender_id INTEGER DEFAULT 1,
    CHECK (origin_depot_id <> destination_depot_id),
    FOREIGN KEY (origin_depot_id) REFERENCES depots(id),
    FOREIGN KEY (destination_depot_id) REFERENCES depots(id),
    FOREIGN KEY (sender_id) REFERENCES customers(id)
);

CREATE TABLE status_events (
    parcel_id INTEGER NOT NULL,
    event_number INTEGER NOT NULL CHECK (event_number > 0),
    status TEXT NOT NULL CHECK (status IN ('ACCEPTED', 'IN_TRANSIT', 'OUT_FOR_DELIVERY', 'DELIVERED')),
    recorded_at TEXT NOT NULL,
    depot_id INTEGER,
    PRIMARY KEY (parcel_id, event_number),
    FOREIGN KEY (parcel_id) REFERENCES parcels(id) ON DELETE CASCADE,
    FOREIGN KEY (depot_id) REFERENCES depots(id)
);

CREATE TABLE delivery_attempts (
    id INTEGER PRIMARY KEY,
    parcel_id INTEGER NOT NULL,
    attempted_at TEXT NOT NULL,
    outcome TEXT NOT NULL CHECK (outcome IN ('DELIVERED', 'ABSENT', 'REFUSED')),
    FOREIGN KEY (parcel_id) REFERENCES parcels(id)
);
