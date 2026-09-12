PRAGMA foreign_keys = ON;

CREATE TABLE reference_items (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 2 AND 80)
);

CREATE TABLE locations (
    id INTEGER PRIMARY KEY,
    location_code TEXT NOT NULL UNIQUE CHECK (length(trim(location_code)) BETWEEN 2 AND 12),
    name TEXT NOT NULL UNIQUE CHECK (length(trim(name)) BETWEEN 2 AND 80)
);

CREATE TABLE custodians (
    id INTEGER PRIMARY KEY,
    personnel_code TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 2 AND 100),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1)
);

CREATE TABLE resources (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    resource_code TEXT NOT NULL UNIQUE CHECK (length(trim(resource_code)) BETWEEN 2 AND 20),
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 2 AND 100),
    measure NUMERIC NOT NULL CHECK (measure >= 0),
    reference_item_id INTEGER,
    current_location_id INTEGER,
    FOREIGN KEY (reference_item_id) REFERENCES reference_items(id),
    FOREIGN KEY (current_location_id) REFERENCES locations(id)
);

CREATE TABLE allocations (
    resource_id INTEGER NOT NULL,
    sequence_number INTEGER NOT NULL CHECK (sequence_number > 0),
    custodian_id INTEGER NOT NULL,
    allocated_at TEXT NOT NULL CHECK (datetime(allocated_at) IS NOT NULL),
    returned_at TEXT CHECK (returned_at IS NULL OR datetime(returned_at) >= datetime(allocated_at)),
    PRIMARY KEY (resource_id, sequence_number),
    FOREIGN KEY (resource_id) REFERENCES resources(id) ON DELETE CASCADE,
    FOREIGN KEY (custodian_id) REFERENCES custodians(id)
);

CREATE UNIQUE INDEX one_open_allocation_per_resource
    ON allocations(resource_id)
    WHERE returned_at IS NULL;

CREATE TABLE inspections (
    resource_id INTEGER NOT NULL,
    sequence_number INTEGER NOT NULL CHECK (sequence_number > 0),
    inspected_on TEXT NOT NULL CHECK (date(inspected_on) = inspected_on),
    result TEXT NOT NULL CHECK (result IN ('PASSED', 'REPAIR_REQUIRED', 'RETIRED')),
    PRIMARY KEY (resource_id, sequence_number),
    FOREIGN KEY (resource_id) REFERENCES resources(id) ON DELETE CASCADE
);
