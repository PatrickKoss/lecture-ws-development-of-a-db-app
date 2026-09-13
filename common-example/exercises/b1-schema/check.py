#!/usr/bin/env python3
from pathlib import Path
import sqlite3
import sys

BASE = Path(__file__).resolve().parent
schema = Path(sys.argv[1]).resolve() if len(sys.argv) > 1 else BASE / "starter/schema.sql"
seed = Path(sys.argv[2]).resolve() if len(sys.argv) > 2 else BASE / "starter/seed.sql"

db = sqlite3.connect(":memory:")
db.execute("PRAGMA foreign_keys = ON")

try:
    db.executescript(schema.read_text(encoding="utf-8"))
    db.executescript(seed.read_text(encoding="utf-8"))
except (sqlite3.Error, OSError) as error:
    raise SystemExit(f"Schema oder Seed-Daten nicht ausführbar: {error}")

required = {"departments", "lecturers", "students", "courses", "enrollments"}
actual = {
    row[0]
    for row in db.execute("SELECT name FROM sqlite_master WHERE type = 'table'")
}
missing = required - actual
assert not missing, f"Fehlende Tabellen: {sorted(missing)}"

for table in sorted(required):
    count = db.execute(f"SELECT COUNT(*) FROM {table}").fetchone()[0]
    assert count > 0, f"{table} enthält keine Seed-Daten"

assert db.execute("PRAGMA foreign_key_check").fetchall() == [], "Fremdschlüssel verletzt"


def rejected(sql: str, values: tuple) -> None:
    try:
        db.execute(sql, values)
    except sqlite3.IntegrityError:
        db.rollback()
        return
    raise AssertionError(f"Constraint fehlt oder greift nicht: {sql}")


rejected(
    "INSERT INTO students(first_name,last_name,email,student_number,enrollment_date) VALUES(?,?,?,?,?)",
    ("Test", "Duplikat", "neu@example.org", "M2023001", "2026-01-01"),
)
rejected(
    "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES(?,?,?,?)",
    ("BAD-FK", "Fehlende Lehrperson", 5, 99999),
)
rejected(
    "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES(?,?,?,?)",
    ("BAD-CREDITS", "Keine Credits", 0, 1),
)
existing = db.execute("SELECT student_id, course_id FROM enrollments LIMIT 1").fetchone()
rejected(
    "INSERT INTO enrollments(student_id,course_id,grade,enrolled_on) VALUES(?,?,?,?)",
    (*existing, 1.0, "2026-01-01"),
)

print("B1-Checkpoint erfüllt: Tabellen, Seed-Daten, Fremdschlüssel und Constraints.")
