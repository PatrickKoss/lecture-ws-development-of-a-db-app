CREATE TABLE galleries (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    floor INTEGER NOT NULL CHECK (floor BETWEEN 0 AND 9)
);

CREATE TABLE artists (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    birth_year INTEGER CHECK (birth_year BETWEEN 1000 AND 2100)
);

CREATE TABLE exhibits (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    inventory_code TEXT NOT NULL UNIQUE,
    title TEXT NOT NULL,
    insured_value NUMERIC NOT NULL CHECK (insured_value >= 0),
    gallery_id INTEGER,
    FOREIGN KEY (gallery_id) REFERENCES galleries(id)
);

CREATE TABLE exhibit_artists (
    exhibit_id INTEGER NOT NULL,
    artist_id INTEGER NOT NULL,
    role TEXT NOT NULL CHECK (role IN ('CREATOR', 'RESTORER')),
    PRIMARY KEY (exhibit_id, artist_id, role),
    FOREIGN KEY (exhibit_id) REFERENCES exhibits(id) ON DELETE CASCADE,
    FOREIGN KEY (artist_id) REFERENCES artists(id)
);

CREATE TABLE loans (
    id INTEGER PRIMARY KEY,
    exhibit_id INTEGER NOT NULL,
    borrower TEXT NOT NULL,
    starts_on TEXT NOT NULL,
    ends_on TEXT NOT NULL,
    CHECK (ends_on >= starts_on),
    FOREIGN KEY (exhibit_id) REFERENCES exhibits(id)
);
