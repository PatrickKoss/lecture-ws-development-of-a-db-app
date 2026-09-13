# A2: Vom ER-Modell zu Relationen

Zeitbox: etwa 15 Minuten vor der Gruppenphase.

## Eingang

Nutzt das ER-Modell aus A1 und ergänzt `starter/relations.md`.

## Gemeinsame Arbeit

1. Überführt jede starke Entität in eine Relation.
2. Setzt den Fremdschlüssel der 1:n-Beziehungen auf die n-Seite.
3. Erstellt für die n:m-Beziehung die Relation `ENROLLMENTS`.
4. Markiert PK, FK, UK, `NOT NULL` und optionale Attribute.
5. Prüft, ob die fachlichen Schlüssel trotz künstlicher IDs erhalten bleiben.

## Checkpoint

Jede Beziehung aus dem ER-Modell hat einen Fremdschlüssel. `ENROLLMENTS` hat zusätzlich `UK(student_id, course_id)`.

```sh
rg -n 'DEPARTMENTS|LECTURERS|STUDENTS|COURSES|ENROLLMENTS|PK|FK|UK' starter/relations.md
```

## Lösung

[Das vollständige Relationenmodell](../../design/relational-model.md) erklärt auch, warum nur `grade` optional ist und warum die Belegung neben ihrer ID einen zusammengesetzten alternativen Schlüssel braucht.

## Transfer zur eigenen Domäne

Bearbeitet vom Repository-Root `exercises/<domain>/a2-relational-model/`. Kontrolliert besonders die Richtung jedes Fremdschlüssels und die Auflösung jeder n:m-Beziehung.
