# Musterlösung Musikschule

Diese Musterlösung zeigt einen durchgängigen Lösungsweg für die Phasen A0 bis
C3. Sie ist kein einzig zulässiges Ergebnis. Vor allem Kardinalitäten hängen
von den Annahmen der Gruppe ab. Entscheidend ist, dass Modell, Schema, Java-Code
und HTTP-Vertrag dieselben Annahmen umsetzen.

## Gewählte fachliche Annahme

Jedes Kursangebot hat genau eine verantwortliche Lehrkraft. Ein Raum ist
optional, weil die Musikschule auch Online-Kurse anbietet. Eine Lehrkraft und
ein Raum können mehreren Kursangeboten zugeordnet sein. Falls mehrere
Lehrkräfte gemeinsam unterrichten dürfen, müsste `teacher_id` durch eine
Zuordnungstabelle wie `course_teachers` ersetzt werden.

## Dateien nach Phase

| Phase     | Musterlösung                                                            |
| --------- | ----------------------------------------------------------------------- |
| A0 bis A3 | `design/README.md` und `design/er.mmd`                                  |
| B1 und B2 | `sql/schema.sql`, `sql/seed.sql`, `sql/queries.sql` und `sql/README.md` |
| B3        | `jdbc/JdbcMusicCourseRepository.java` und `jdbc/README.md`              |
| B4        | `repository/MusicCourseCatalog.java` und `repository/README.md`         |
| C1        | `api/README.md` und die Annotationen unter `spring/src/`                |
| C2 und C3 | `spring/`                                                               |

Die Dateien in `spring/` ersetzen die gleichnamigen Dateien im C2-Starter. Die
Musterlösung hält sich an den Kursumfang: flache GET- und POST-Repräsentation,
Bean Validation, genau ein Konfliktfall und gezielte Tests. SpringDoc erzeugt
die OpenAPI-Beschreibung aus diesen Java-Dateien. Es gibt keine separate YAML-
Datei, die parallel gepflegt werden muss.
