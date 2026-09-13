# Fachliche Ausgangslage

Eine Hochschule verwaltet Studierende, Lehrende, Lehrveranstaltungen, Belegungen und Fachbereiche. Für den Programmierteil bleibt `Student` die Hauptentität.

## Geschäftsregeln

- Jede studierende Person hat genau eine Studierendennummer und eine E-Mail-Adresse. Beide Werte sind hochschulweit eindeutig.
- `enrollment_date` am Studierenden bezeichnet den Beginn des Studiums. Das Datum gehört deshalb zu `Student`.
- Jeder Fachbereich hat einen eindeutigen Code und einen eindeutigen Namen.
- Jede Lehrperson hat eine eindeutige E-Mail-Adresse und gehört genau einem Fachbereich an.
- Jede Lehrveranstaltung hat einen eindeutigen Kurscode, einen Titel und eine positive Zahl von Credit Points. Genau eine Lehrperson hält sie.
- Studierende können mehrere Lehrveranstaltungen belegen. Eine Lehrveranstaltung kann ohne oder mit vielen Belegungen existieren.
- `enrolled_on` bezeichnet den Tag, an dem eine konkrete Person einen konkreten Kurs belegt hat. Das Datum gehört deshalb zu `Enrollment`.
- Dieselbe Person darf denselben Kurs nur einmal belegen.
- Eine fehlende Note bedeutet "noch nicht bewertet". Eine vorhandene Note liegt zwischen 1,0 und 5,0.

Die künstlichen IDs sind die Primärschlüssel der Tabellen. Fachliche Schlüssel bleiben zusätzlich eindeutig: `student_number`, die E-Mail-Adressen, `course_code`, `departments.code` und `departments.name`.

## Offene Entscheidungen für die Diskussion

- Darf eine E-Mail-Adresse später geändert werden, obwohl sie eindeutig ist?
- Darf eine Lehrperson den Fachbereich wechseln?
- Soll ein Kurs über mehrere Semester hinweg dieselbe Ressource bleiben?
- Reicht die Kombination aus Student und Kurs als fachliche Identität einer Belegung, oder braucht das Modell später ein Semester?

Die Lösung nimmt an, dass ein Kurs in diesem Ausschnitt nur einmal existiert. Für wiederkehrende Kursangebote wäre eine weitere Entität wie `CourseOffering` nötig.

## Begriffe im Code

| Deutscher Fachbegriff | Code und Datenbank          |
| --------------------- | --------------------------- |
| Studierende           | `Student`, `students`       |
| Lehrende              | `Lecturer`, `lecturers`     |
| Lehrveranstaltung     | `Course`, `courses`         |
| Belegung              | `Enrollment`, `enrollments` |
| Fachbereich           | `Department`, `departments` |
