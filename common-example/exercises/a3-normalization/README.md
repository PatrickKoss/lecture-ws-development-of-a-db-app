# A3: Normalisierung bis 3NF

Zeitbox: etwa 25 Minuten vor der Gruppenphase.

## Eingang

`starter/normalization.md` enthält die breite Exportrelation aus Belegungsdaten.

## Gemeinsame Arbeit

1. Bestimmt den Kandidatenschlüssel der Exportrelation.
2. Notiert alle funktionalen Abhängigkeiten.
3. Nennt je eine Änderungs-, Einfüge- und Löschanomalie.
4. Entfernt für 2NF die Abhängigkeiten von Teilen des zusammengesetzten Schlüssels.
5. Entfernt für 3NF die transitiven Abhängigkeiten über Lehrperson und Fachbereich.
6. Vergleicht die Relationen mit dem Ergebnis aus A2.

## Checkpoint

Die Herleitung zeigt 1NF, 2NF und 3NF. Jede Zerlegung nennt die entfernte Abhängigkeit. `student_enrollment_date` bleibt bei Student, `enrolled_on` bei Enrollment.

```sh
rg -n 'Kandidatenschlüssel|1NF|2NF|3NF|Anomalie|student_enrollment_date|enrolled_on|->' starter/normalization.md
! rg -n 'TODO|^Kandidatenschlüssel:$|^Anomalie:$' starter/normalization.md
```

## Lösung

[Die ausgearbeitete Normalisierung](../../design/normalization.md) enthält Zwischenrelationen, Schlüssel, Abhängigkeiten und die konkreten Anomalien. [Das Relationenmodell](../../design/relational-model.md) zeigt anschließend die Variante mit künstlichen IDs.

## Transfer zur eigenen Domäne

Bearbeitet vom Repository-Root `exercises/<domain>/a3-normalization/`. Beginnt mit einer echten breiten Exportrelation. Das fertige ER-Modell allein ist kein Nachweis für 3NF.
