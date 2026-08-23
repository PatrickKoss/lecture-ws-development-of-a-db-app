PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    membership_number TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    email TEXT NOT NULL UNIQUE CHECK (instr(email, '@') > 1),
    joined_on TEXT NOT NULL
        CHECK (
            date(joined_on) IS NOT NULL
            AND joined_on = date(joined_on)
        ),
    active INTEGER NOT NULL DEFAULT 1 CHECK (active IN (0, 1))
);

CREATE TABLE IF NOT EXISTS authors (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL CHECK (length(trim(first_name)) > 0),
    last_name TEXT NOT NULL CHECK (length(trim(last_name)) > 0),
    birth_year INTEGER CHECK (birth_year BETWEEN 1000 AND 2100),
    UNIQUE (first_name, last_name, birth_year)
);

CREATE TABLE IF NOT EXISTS books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    isbn TEXT NOT NULL UNIQUE
        CHECK (
            length(isbn) = 13
            AND isbn NOT GLOB '*[^0-9]*'
        ),
    title TEXT NOT NULL CHECK (length(trim(title)) BETWEEN 1 AND 200),
    publication_year INTEGER NOT NULL CHECK (publication_year BETWEEN 1450 AND 2100),
    subject_area TEXT NOT NULL CHECK (length(trim(subject_area)) BETWEEN 1 AND 50),
    shelf_code TEXT NOT NULL CHECK (length(trim(shelf_code)) BETWEEN 1 AND 10),
    UNIQUE (title, shelf_code)
);

CREATE TABLE IF NOT EXISTS book_authors (
    book_id INTEGER NOT NULL,
    author_id INTEGER NOT NULL,
    author_order INTEGER NOT NULL CHECK (author_order > 0),
    PRIMARY KEY (book_id, author_id),
    UNIQUE (book_id, author_order),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE IF NOT EXISTS copies (
    book_id INTEGER NOT NULL,
    copy_number INTEGER NOT NULL CHECK (copy_number > 0),
    barcode TEXT NOT NULL UNIQUE,
    branch_code TEXT NOT NULL CHECK (branch_code IN ('NORD', 'SUED')),
    acquired_on TEXT NOT NULL
        CHECK (
            date(acquired_on) IS NOT NULL
            AND acquired_on = date(acquired_on)
        ),
    condition TEXT NOT NULL CHECK (condition IN ('NEU', 'GUT', 'BESCHAEDIGT')),
    PRIMARY KEY (book_id, copy_number),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    member_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    reserved_on TEXT NOT NULL
        CHECK (
            date(reserved_on) IS NOT NULL
            AND reserved_on = date(reserved_on)
        ),
    expires_on TEXT NOT NULL
        CHECK (
            date(expires_on) IS NOT NULL
            AND expires_on = date(expires_on)
            AND expires_on >= reserved_on
        ),
    status TEXT NOT NULL CHECK (status IN ('OPEN', 'FULFILLED', 'CANCELLED')),
    UNIQUE (member_id, book_id, reserved_on),
    UNIQUE (id, member_id, book_id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE IF NOT EXISTS loans (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    member_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    copy_number INTEGER NOT NULL,
    reservation_id INTEGER UNIQUE,
    loaned_on TEXT NOT NULL
        CHECK (
            date(loaned_on) IS NOT NULL
            AND loaned_on = date(loaned_on)
        ),
    due_on TEXT NOT NULL
        CHECK (
            date(due_on) IS NOT NULL
            AND due_on = date(due_on)
            AND due_on >= loaned_on
        ),
    returned_on TEXT
        CHECK (
            returned_on IS NULL
            OR (
                date(returned_on) IS NOT NULL
                AND returned_on = date(returned_on)
                AND returned_on >= loaned_on
            )
        ),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id, copy_number) REFERENCES copies(book_id, copy_number),
    FOREIGN KEY (reservation_id, member_id, book_id)
        REFERENCES reservations(id, member_id, book_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS one_open_loan_per_copy
    ON loans (book_id, copy_number)
    WHERE returned_on IS NULL;
