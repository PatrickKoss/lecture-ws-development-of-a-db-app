# Relationenmodell

Die Primärschlüssel sind mit PK, Fremdschlüssel mit FK und alternative eindeutige Schlüssel mit UK markiert.

```text
DEPARTMENTS(
  id INTEGER PK,
  name TEXT UK NOT NULL,
  code TEXT UK NOT NULL
)

LECTURERS(
  id INTEGER PK,
  first_name TEXT NOT NULL,
  last_name TEXT NOT NULL,
  email TEXT UK NOT NULL,
  department_id INTEGER FK -> DEPARTMENTS.id NOT NULL
)

STUDENTS(
  id INTEGER PK,
  first_name TEXT NOT NULL,
  last_name TEXT NOT NULL,
  email TEXT UK NOT NULL,
  student_number TEXT UK NOT NULL,
  enrollment_date DATE NOT NULL
)

COURSES(
  id INTEGER PK,
  course_code TEXT UK NOT NULL,
  title TEXT NOT NULL,
  credits INTEGER NOT NULL,
  lecturer_id INTEGER FK -> LECTURERS.id NOT NULL
)

ENROLLMENTS(
  id INTEGER PK,
  student_id INTEGER FK -> STUDENTS.id NOT NULL,
  course_id INTEGER FK -> COURSES.id NOT NULL,
  grade DECIMAL NULL,
  enrolled_on DATE NOT NULL,
  UK(student_id, course_id)
)
```

`ENROLLMENTS` löst die n:m-Beziehung zwischen Studierenden und Kursen auf. `enrolled_on` und `grade` beschreiben diese Beziehung. Die künstliche ID erleichtert spätere API-Pfade. Der zusammengesetzte alternative Schlüssel verhindert trotzdem eine zweite Belegung derselben Person im selben Kurs.

Keine Fremdschlüsselspalte ist optional. Eine Lehrperson braucht einen Fachbereich, ein Kurs eine Lehrperson und eine Belegung beide Enden der Beziehung. Optional ist nur `grade`, weil eine Belegung vor der Bewertung existiert.
