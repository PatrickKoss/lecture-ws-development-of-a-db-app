# Normalisierung des Hochschulbeispiels

Wir beginnen mit einer flachen Exportrelation. Eine Zeile beschreibt die Belegung eines Kurses durch eine studierende Person.

```text
COURSE_PARTICIPATION(
  student_number, student_first_name, student_last_name, student_email,
  student_enrollment_date,
  course_code, course_title, credits,
  lecturer_email, lecturer_first_name, lecturer_last_name,
  department_code, department_name,
  enrolled_on, grade
)
```

Ein Kandidatenschlüssel ist `(student_number, course_code)`. Wegen der eindeutigen Studierenden-E-Mail ist `(student_email, course_code)` ein zweiter Kandidatenschlüssel. `lecturer_email`, `department_code` und `department_name` sind Schlüssel ihrer späteren Relationen. Sie identifizieren aber keine Zeile der flachen Relation allein.

## Funktionale Abhängigkeiten

```text
student_number ->
  student_first_name, student_last_name, student_email, student_enrollment_date

student_email ->
  student_number, student_first_name, student_last_name, student_enrollment_date

course_code -> course_title, credits, lecturer_email

lecturer_email ->
  lecturer_first_name, lecturer_last_name, department_code

department_code -> department_name
department_name -> department_code

(student_number, course_code) -> enrolled_on, grade
```

`student_enrollment_date` ist der Beginn des Studiums. `enrolled_on` ist das Datum einer Kursbelegung. Die beiden Attribute beantworten verschiedene Fragen.

## Erste Normalform

Jede Zelle enthält einen atomaren Wert, und jede Zeile hat denselben Aufbau. Die Exportrelation ist in 1NF. Sie hat trotzdem Redundanzen:

- Name und E-Mail einer Person wiederholen sich für jeden belegten Kurs.
- Titel, Credits und Lehrperson wiederholen sich für jede Person im Kurs.
- Der Name eines Fachbereichs wiederholt sich über Lehrpersonen, Kurse und Belegungen.

Eine Änderung der E-Mail-Adresse müsste viele Zeilen treffen. Eine neue Lehrveranstaltung ohne Belegung ließe sich noch nicht speichern. Beim Löschen der letzten Belegung könnten Kursdaten verloren gehen.

## Zweite Normalform

Für 2NF entfernen wir Abhängigkeiten von einem echten Teil des zusammengesetzten Schlüssels.

```text
STUDENTS_2NF(
  student_number PK,
  student_first_name, student_last_name, student_email UK,
  student_enrollment_date
)

COURSES_2NF(
  course_code PK,
  course_title, credits,
  lecturer_email, lecturer_first_name, lecturer_last_name,
  department_code, department_name
)

ENROLLMENTS_2NF(
  student_number PK/FK,
  course_code PK/FK,
  enrolled_on, grade
)
```

Die Zerlegung ist verlustfrei: `ENROLLMENTS_2NF` trägt beide ursprünglichen Schlüsselbestandteile und verbindet Studierende und Kurse wieder eindeutig.

## Dritte Normalform

`COURSES_2NF` hat transitive Abhängigkeiten. Der Kurscode bestimmt die E-Mail der Lehrperson, diese bestimmt den Fachbereich, und dessen Code bestimmt den Namen. Wir trennen diese Fakten.

```text
DEPARTMENTS(department_code PK, department_name UK)

LECTURERS(
  lecturer_email PK,
  lecturer_first_name, lecturer_last_name,
  department_code FK
)

COURSES(course_code PK, course_title, credits, lecturer_email FK)

STUDENTS(
  student_number PK,
  student_first_name, student_last_name,
  student_email UK,
  student_enrollment_date
)

ENROLLMENTS(
  student_number PK/FK,
  course_code PK/FK,
  enrolled_on, grade
)
```

In jeder Relation hängen die Nichtschlüsselattribute vom Schlüssel, vom ganzen Schlüssel und nicht transitiv von einem anderen Nichtschlüsselattribut ab. Das Ergebnis ist in 3NF.

Das ausführbare Schema unter `../sql/schema.sql` ergänzt künstliche numerische IDs. Die fachlichen Schlüssel bleiben mit `UNIQUE` erhalten. Dadurch ändert sich die fachliche Zerlegung nicht.

## Welche Fehler verschwinden?

- Eine geänderte Studierenden-E-Mail wird genau einmal gespeichert.
- Ein Kurs kann vor der ersten Belegung angelegt werden.
- Das Löschen der letzten Belegung löscht weder Kurs noch Lehrperson.
- Ein Wechsel des Fachbereichs erfordert eine Änderung an der Lehrperson, nicht an jeder Kursbelegung.
