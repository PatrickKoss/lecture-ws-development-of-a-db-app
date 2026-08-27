CREATE TABLE reference_items (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE resources (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    resource_code TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    measure NUMERIC NOT NULL CHECK (measure >= 0)
);

CREATE TABLE fk_probe_child (
    id INTEGER PRIMARY KEY,
    lookup_id INTEGER NOT NULL,
    FOREIGN KEY (lookup_id) REFERENCES reference_items(id)
);
