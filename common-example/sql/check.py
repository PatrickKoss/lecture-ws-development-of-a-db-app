#!/usr/bin/env python3
"""Checks the canonical university schema, seed data, constraints, and B2 queries."""

from pathlib import Path
import re
import sqlite3

BASE = Path(__file__).resolve().parent


def load_database() -> sqlite3.Connection:
    connection = sqlite3.connect(":memory:")
    connection.execute("PRAGMA foreign_keys = ON")
    connection.executescript((BASE / "schema.sql").read_text(encoding="utf-8"))
    connection.executescript((BASE / "seed.sql").read_text(encoding="utf-8"))
    return connection


def expect_integrity_error(connection: sqlite3.Connection, sql: str, parameters: tuple) -> None:
    try:
        connection.execute(sql, parameters)
    except sqlite3.IntegrityError:
        connection.rollback()
        return
    raise AssertionError(f"Constraint akzeptierte ungueltige Daten: {sql}")


def main() -> None:
    db = load_database()

    counts = {
        table: db.execute(f"SELECT COUNT(*) FROM {table}").fetchone()[0]
        for table in ("departments", "lecturers", "students", "courses", "enrollments")
    }
    assert counts == {
        "departments": 3,
        "lecturers": 5,
        "students": 20,
        "courses": 7,
        "enrollments": 40,
    }, counts
    assert db.execute("PRAGMA foreign_key_check").fetchall() == []

    expect_integrity_error(
        db,
        "INSERT INTO students(first_name,last_name,email,student_number,enrollment_date) VALUES(?,?,?,?,?)",
        ("Duplikat", "Test", "duplikat@example.org", "M2023001", "2026-01-01"),
    )
    expect_integrity_error(
        db,
        "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES(?,?,?,?)",
        ("BAD-1", "Ohne Lehrperson", 5, 9999),
    )
    expect_integrity_error(
        db,
        "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES(?,?,?,?)",
        ("BAD-2", "Keine Credits", 0, 1),
    )
    expect_integrity_error(
        db,
        "INSERT INTO enrollments(student_id,course_id,grade,enrolled_on) VALUES(?,?,?,?)",
        (1, 1, 1.0, "2026-01-01"),
    )

    query_source = (BASE / "core-queries.sql").read_text(encoding="utf-8")
    query_source = re.sub(r"--[^\n]*", "", query_source)
    queries = [part.strip() for part in query_source.split(";") if part.strip()]
    assert len(queries) == 4, f"core-queries.sql enthält {len(queries)} statt vier Statements"
    assert all(query.upper().startswith("SELECT") for query in queries)

    recent = db.execute(queries[0]).fetchall()
    assert recent == [
        ("M2025001", "Marlon", "König"),
        ("M2025002", "Zeynep", "Demir"),
        ("M2025003", "Felix", "Lehmann"),
        ("M2025004", "Hannah", "Maier"),
        ("M2025005", "Samir", "Saleh"),
    ], recent

    best = db.execute(queries[1]).fetchall()
    assert best == [
        ("M2023001", "MAT-110", 1.3),
        ("M2023003", "INF-201", 1.3),
        ("M2023005", "MAT-210", 1.3),
        ("M2024003", "INF-202", 1.3),
        ("M2024007", "MAT-210", 1.3),
        ("M2025002", "INF-230", 1.3),
    ], best

    without_course = db.execute(queries[2]).fetchall()
    assert without_course == [("M2025004", "Hannah", "Maier"), ("M2025005", "Samir", "Saleh")], without_course

    course_counts = db.execute(queries[3]).fetchall()
    assert course_counts == [
        ("INF-201", 8),
        ("INF-230", 7),
        ("INF-202", 6),
        ("INF-250", 5),
        ("MAT-110", 5),
        ("MAT-210", 5),
        ("WI-101", 4),
    ], course_counts

    db.execute(
        "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES(?,?,?,?)",
        ("TEST-EMPTY", "Kurs ohne Belegung", 1, 1),
    )
    empty_course = [row for row in db.execute(queries[3]).fetchall() if row[0] == "TEST-EMPTY"]
    assert empty_course == [("TEST-EMPTY", 0)], empty_course

    print("SQL-Loesung geprueft: Schema, 75 Seed-Zeilen, Constraints und vier B2-Abfragen.")


if __name__ == "__main__":
    main()
