PRAGMA foreign_keys = ON;

-- Halter mit eindeutiger Kundennummer und Kontaktdaten.
CREATE TABLE IF NOT EXISTS owners (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    phone TEXT NOT NULL CHECK (length(trim(phone)) BETWEEN 7 AND 30),
    city TEXT NOT NULL CHECK (length(trim(city)) BETWEEN 1 AND 80)
);

-- Tiere eines Halters; die Tiernummer beginnt je Halter wieder bei 1.
CREATE TABLE IF NOT EXISTS pets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    owner_id INTEGER NOT NULL,
    pet_number INTEGER NOT NULL CHECK (pet_number > 0),
    name TEXT NOT NULL CHECK (length(trim(name)) BETWEEN 1 AND 80),
    species TEXT NOT NULL
        CHECK (species IN ('HUND', 'KATZE', 'KANINCHEN', 'VOGEL', 'MEERSCHWEINCHEN')),
    birth_date TEXT
        CHECK (
            birth_date IS NULL
            OR (
                date(birth_date) IS NOT NULL
                AND birth_date = date(birth_date)
            )
        ),
    insurance_policy_number TEXT UNIQUE,
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1)),
    UNIQUE (owner_id, pet_number),
    FOREIGN KEY (owner_id) REFERENCES owners(id)
);

-- Tierärzte mit Approbationsnummer, Fachgebiet und Behandlungsraum.
CREATE TABLE IF NOT EXISTS vets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    license_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    specialization TEXT NOT NULL CHECK (length(trim(specialization)) BETWEEN 1 AND 80),
    consultation_room TEXT NOT NULL CHECK (length(trim(consultation_room)) BETWEEN 1 AND 20),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1)),
    UNIQUE (first_name, last_name, consultation_room)
);

-- Termine verbinden ein Tier mit einem Tierarzt zu einem festen Zeitpunkt.
CREATE TABLE IF NOT EXISTS appointments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pet_id INTEGER NOT NULL,
    vet_id INTEGER NOT NULL,
    scheduled_at TEXT NOT NULL
        CHECK (
            datetime(scheduled_at) IS NOT NULL
            AND scheduled_at = strftime('%Y-%m-%dT%H:%M', scheduled_at)
        ),
    reason TEXT NOT NULL CHECK (length(trim(reason)) BETWEEN 3 AND 200),
    status TEXT NOT NULL
        CHECK (status IN ('PLANNED', 'COMPLETED', 'CANCELLED', 'NO_SHOW')),
    UNIQUE (vet_id, scheduled_at),
    UNIQUE (pet_id, scheduled_at),
    FOREIGN KEY (pet_id) REFERENCES pets(id),
    FOREIGN KEY (vet_id) REFERENCES vets(id)
);

-- Behandlungen sind die optionale Dokumentation zu einem Termin.
CREATE TABLE IF NOT EXISTS treatments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    appointment_id INTEGER NOT NULL UNIQUE,
    treated_on TEXT NOT NULL
        CHECK (
            date(treated_on) IS NOT NULL
            AND treated_on = date(treated_on)
        ),
    diagnosis TEXT NOT NULL CHECK (length(trim(diagnosis)) BETWEEN 3 AND 200),
    notes TEXT CHECK (notes IS NULL OR length(trim(notes)) BETWEEN 1 AND 1000),
    fee_cents INTEGER NOT NULL CHECK (fee_cents >= 0),
    FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

-- Medikamente aus dem praxisinternen Katalog.
CREATE TABLE IF NOT EXISTS medications (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pzn TEXT NOT NULL UNIQUE
        CHECK (
            length(pzn) = 8
            AND pzn NOT GLOB '*[^0-9]*'
        ),
    product_name TEXT NOT NULL CHECK (length(trim(product_name)) BETWEEN 1 AND 120),
    active_ingredient TEXT NOT NULL CHECK (length(trim(active_ingredient)) BETWEEN 1 AND 160),
    dosage_form TEXT NOT NULL CHECK (length(trim(dosage_form)) BETWEEN 1 AND 50),
    prescription_required INTEGER NOT NULL CHECK (prescription_required IN (0, 1)),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1)),
    UNIQUE (product_name, dosage_form)
);

-- Verschreibungen verbinden Behandlungen und Medikamente mit Dosis und Dauer.
CREATE TABLE IF NOT EXISTS prescriptions (
    treatment_id INTEGER NOT NULL,
    medication_id INTEGER NOT NULL,
    dose TEXT NOT NULL CHECK (length(trim(dose)) BETWEEN 1 AND 100),
    duration_days INTEGER NOT NULL CHECK (duration_days BETWEEN 1 AND 365),
    instructions TEXT CHECK (instructions IS NULL OR length(trim(instructions)) BETWEEN 1 AND 300),
    PRIMARY KEY (treatment_id, medication_id),
    FOREIGN KEY (treatment_id) REFERENCES treatments(id) ON DELETE CASCADE,
    FOREIGN KEY (medication_id) REFERENCES medications(id)
);
