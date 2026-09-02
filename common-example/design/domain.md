# Fachliche Ausgangslage

Eine Hochschule verwaltet Studierende, Lehrende und Lehrveranstaltungen. Jede Lehrveranstaltung hat einen eindeutigen Kurscode, einen Titel und eine Zahl von Credit Points. Genau eine Lehrperson hält die Lehrveranstaltung. Lehrende gehören zu einem Fachbereich.

Studierende können mehrere Lehrveranstaltungen belegen. Eine Lehrveranstaltung kann von vielen Studierenden belegt werden. Zu jeder Belegung speichern wir das Belegdatum und später eine optionale Note. Dieselbe Person darf dieselbe Lehrveranstaltung nur einmal belegen.

## Fragen für die gemeinsame Modellierung

- Darf eine Lehrveranstaltung ohne Belegung existieren?
- Darf eine Lehrperson noch keine Lehrveranstaltung halten?
- Wo gehört das Belegdatum hin?
- Identifiziert der Kurscode eine Lehrveranstaltung dauerhaft?
- Was bedeutet eine fehlende Note?

## Begriffe im Code

| Deutscher Fachbegriff | Code und Datenbank          |
| --------------------- | --------------------------- |
| Studierende           | `Student`, `students`       |
| Lehrende              | `Lecturer`, `lecturers`     |
| Lehrveranstaltung     | `Course`, `courses`         |
| Belegung              | `Enrollment`, `enrollments` |
| Fachbereich           | `Department`, `departments` |
