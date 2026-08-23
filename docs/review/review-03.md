# Review Deck 03

| Folie (h/v/f) | Datei (PNG name) | Befund | Schwere (blockiert / sollte / nit) |
|---|---|---|---|
| 3/0/0 | `slide-38-fragment-00.png` | In der orangefarbenen Box `enrollments` wird `course_id` am rechten Rand nach `course_` abgeschnitten. | sollte |
| 3/1/0 | `slide-39-fragment-00.png` | Die Texte in den acht Stationen sind mit 24 px kleiner als die geforderten 28 px. | sollte |
| 3/7/1 | `slide-45-fragment-01.png` | Die Folie zeigt `grade TEXT NULL`. In `java/sql/university/schema.sql` ist `grade` als `REAL CHECK (grade BETWEEN 1.0 AND 5.0)` definiert. | blockiert |
| 3/9/0 | `slide-47-fragment-00.png` | Die vier Arbeitsschritte sind mit 1,15 rem deutlich kleiner als die geforderten 28 px. | sollte |

## Gesamteindruck

Deck 03 erklärt die Übersetzung vom Modell zum Schema meist klar, und die Codefolie passt zur Referenzdatei. Der Typ von `grade` widerspricht jedoch dem Schema und muss vor der Freigabe korrigiert werden. Dazu kommen abgeschnittener Text auf der Kapitelstartfolie und zu kleine Texte in Überblick und Übung.
