#!/usr/bin/env python3
from pathlib import Path
import re
import sqlite3
import sys

BASE = Path(__file__).resolve().parent
query_file = Path(sys.argv[1]).resolve() if len(sys.argv) > 1 else BASE / "starter/queries.sql"

db = sqlite3.connect(":memory:")
db.execute("PRAGMA foreign_keys = ON")
db.executescript((BASE / "../../sql/schema.sql").read_text(encoding="utf-8"))
db.executescript((BASE / "../../sql/seed.sql").read_text(encoding="utf-8"))

source = query_file.read_text(encoding="utf-8")
without_comments = re.sub(r"--[^\n]*", "", source)
statements = [part.strip() for part in without_comments.split(";") if part.strip()]
assert len(statements) == 4, f"Erwartet vier Statements, gefunden: {len(statements)}"
assert all(statement.upper().startswith("SELECT") for statement in statements), "Nur SELECT ist erlaubt"

expected = [
    [
        ("M2025001", "Marlon", "König"),
        ("M2025002", "Zeynep", "Demir"),
        ("M2025003", "Felix", "Lehmann"),
        ("M2025004", "Hannah", "Maier"),
        ("M2025005", "Samir", "Saleh"),
    ],
    [
        ("M2023001", "MAT-110", 1.3),
        ("M2023003", "INF-201", 1.3),
        ("M2023005", "MAT-210", 1.3),
        ("M2024003", "INF-202", 1.3),
        ("M2024007", "MAT-210", 1.3),
        ("M2025002", "INF-230", 1.3),
    ],
    [
        ("M2025004", "Hannah", "Maier"),
        ("M2025005", "Samir", "Saleh"),
    ],
    [
        ("INF-201", 8),
        ("INF-230", 7),
        ("INF-202", 6),
        ("INF-250", 5),
        ("MAT-110", 5),
        ("MAT-210", 5),
        ("WI-101", 4),
    ],
]

for number, (statement, wanted) in enumerate(zip(statements, expected), start=1):
    actual = db.execute(statement).fetchall()
    assert actual == wanted, f"Abfrage {number}\nerwartet: {wanted}\nerhalten: {actual}"

db.execute(
    "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES(?,?,?,?)",
    ("TEST-EMPTY", "Kurs ohne Belegung", 1, 1),
)
empty_course = [row for row in db.execute(statements[3]).fetchall() if row[0] == "TEST-EMPTY"]
assert empty_course == [("TEST-EMPTY", 0)], (
    "Abfrage 4 verliert einen Kurs ohne Belegung. Verwendet LEFT JOIN."
)

print("B2-Checkpoint erfüllt: vier read-only Abfragen mit deterministischen Ergebnissen.")
