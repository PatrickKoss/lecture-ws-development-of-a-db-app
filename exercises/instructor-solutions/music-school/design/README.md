# Musterlösung für A0 bis A3

## A0: Domänenidee

Die Programmleitung will Kursangebote, Lehrkräfte, Räume, Schüler,
Anmeldungen und benötigte Instrumente gemeinsam verwalten. Die Hauptentität
ist das Kursangebot. Es beschreibt ein buchbares Angebot der Musikschule, zum
Beispiel "Klavier für Einsteiger" mit dem Kurscode `MU-01`.

Die offene Frage lautet, ob mehrere Lehrkräfte ein Kursangebot gemeinsam
unterrichten. Diese Lösung nimmt genau eine verantwortliche Lehrkraft an. Das
ergibt einen Fremdschlüssel `teacher_id` in `music_courses`. Bei Team-Teaching
wäre eine n:m-Beziehung erforderlich.

Zwei weitere Geschäftsregeln konkretisieren die Domäne:

- Eine Kombination aus Kursangebot und Schüler darf höchstens einmal als
  Anmeldung vorkommen.
- Die Zahl eines benötigten Instruments muss größer als null sein.

## A1: ER-Modell

`er.mmd` enthält sechs Entitäten neben der Hauptentität. `ENROLLMENT` löst die
n:m-Beziehung zwischen Kursangebot und Schüler auf. Die Beziehung hat mit
`enrolled_at` und `status` eigene Attribute. `COURSE_INSTRUMENT` löst eine
zweite n:m-Beziehung auf und speichert `quantity_required`.

Die Kardinalitäten lesen sich so:

- Jede Lehrkraft kann für null bis viele Kursangebote verantwortlich sein.
  Jedes Kursangebot verweist auf genau eine Lehrkraft.
- Jeder Raum kann null bis viele Kursangebote aufnehmen. Ein Kursangebot hat
  höchstens einen Raum. Bei einem Online-Kurs bleibt `room_id` leer.
- Ein Kursangebot kann null bis viele Anmeldungen und Instrumentanforderungen
  haben. Eine konkrete Anmeldung oder Instrumentanforderung gehört immer zu
  genau einem Kursangebot.

## A2: Relationenmodell

```text
teachers(id PK, staff_code UK NOT NULL, name NOT NULL)
rooms(id PK, room_code UK NOT NULL, capacity NOT NULL)
students(id PK, email UK NOT NULL, name NOT NULL)
instruments(id PK, instrument_code UK NOT NULL, name NOT NULL)
music_courses(id PK, course_code UK NOT NULL, title NOT NULL, fee NOT NULL,
              level NOT NULL, teacher_id FK NOT NULL, room_id FK NULL)
enrollments(course_id PK FK, student_id PK FK, enrolled_at NOT NULL,
            status NOT NULL)
course_instruments(course_id PK FK, instrument_id PK FK,
                   quantity_required NOT NULL)
```

Die beiden Zuordnungstabellen brauchen keine künstliche ID. Ihre beiden
Fremdschlüssel identifizieren eine Zuordnung bereits eindeutig. `room_id` ist
der einzige optionale Fremdschlüssel. NULL bedeutet hier nicht "unbekannt",
sondern "Online-Kurs ohne Raum". Eine Anmeldung ohne zugewiesenen Schüler
würde dagegen ein anderes Modell erfordern. Ein NULL-Wert im zusammengesetzten
Primärschlüssel wäre dafür keine saubere Lösung.

## A3: Normalisierung

Als problematische Exportrelation dient:

```text
COURSE_EXPORT(course_code, title, fee, level,
              teacher_staff_code, teacher_name,
              room_code, room_capacity,
              student_email, student_name, enrolled_at, enrollment_status,
              instrument_code, instrument_name, quantity_required)
```

Unter der Annahme, dass der Export das Kreuzprodukt aus Anmeldungen und
Instrumentanforderungen enthält, ist
`(course_code, student_email, instrument_code)` ein Kandidatenschlüssel. Es
gelten diese funktionalen Abhängigkeiten:

```text
course_code -> title, fee, level, teacher_staff_code, room_code
teacher_staff_code -> teacher_name
room_code -> room_capacity
student_email -> student_name
(course_code, student_email) -> enrolled_at, enrollment_status
instrument_code -> instrument_name
(course_code, instrument_code) -> quantity_required
```

Die breite Relation hat partielle und transitive Abhängigkeiten. Der Kurstitel
hängt nur von `course_code` ab. Der Name der Lehrkraft hängt über
`teacher_staff_code` vom Kurscode ab. Bei jeder Anmeldung und jedem Instrument
wiederholen sich deshalb Kurs-, Raum- und Lehrkraftdaten.

Eine konkrete Änderungsanomalie: Wird Raum `R-204` auf acht Plätze erweitert,
müsste `room_capacity` in jeder Exportzeile dieses Raums geändert werden. Bleibt
eine Zeile bei sechs, enthält der Export zwei Kapazitäten für denselben Raum.

Die Zerlegung aus A2 beseitigt diese Abhängigkeiten. Jede Nichtschlüsselspalte
hängt dann vom Schlüssel ihrer Relation ab und nicht transitiv von einer
anderen Nichtschlüsselspalte. Die Zerlegung ist verlustfrei, weil jede
Zuordnung ihren Fremdschlüssel auf die zugehörige Stammtabelle behält. JOINs
über diese Schlüssel setzen die fachlich vorhandenen Kombinationen wieder
zusammen.

Alle Relationen erfüllen auch BCNF: Jeder hier aufgeführte Determinant ist in
seiner eigenen Relation ein Kandidatenschlüssel.
