# Normalisierung des Hochschulbeispiels

Wir beginnen absichtlich mit einer flachen Tabelle. Eine Zeile beschreibt die Belegung einer Lehrveranstaltung durch eine Studentin oder einen Studenten.

```text
COURSE_PARTICIPATION(
  student_number, student_first_name, student_last_name, student_email,
  course_code, course_title, credits,
  lecturer_email, lecturer_first_name, lecturer_last_name,
  department_code, department_name,
  enrolled_on, grade
)
```

Der Kandidatenschlüssel ist `(student_number, course_code)`.

## Funktionale Abhängigkeiten

```text
student_number -> student_first_name, student_last_name, student_email
course_code -> course_title, credits, lecturer_email
lecturer_email -> lecturer_first_name, lecturer_last_name, department_code
department_code -> department_name
(student_number, course_code) -> enrolled_on, grade
```

## Gemeinsame Arbeit

1. Prüft die 1NF. Eine Zelle enthält genau einen Wert, und jede Zeile hat denselben Aufbau.
2. Markiert partielle Abhängigkeiten vom zusammengesetzten Schlüssel. Daraus entstehen `students`, `courses` und `enrollments`.
3. Markiert transitive Abhängigkeiten in `courses`. Daraus entstehen `lecturers` und `departments`.
4. Ergänzt Primärschlüssel, Fremdschlüssel und `UNIQUE (student_id, course_id)`.
5. Vergleicht das Ergebnis mit `../sql/schema.sql`.

Die Zerlegung bis zur 3NF verhindert drei konkrete Fehler: Studierendendaten werden nicht pro Belegung kopiert, ein Wechsel des Fachbereichs braucht keine Änderung an jeder Lehrveranstaltung, und eine Lehrperson kann vor ihrer ersten Lehrveranstaltung gespeichert werden.
