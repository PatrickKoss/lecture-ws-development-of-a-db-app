CREATE TABLE IF NOT EXISTS student
(
    id         TEXT NOT NULL,
    mnr        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name       TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    created_on TEXT NOT NULL
);
