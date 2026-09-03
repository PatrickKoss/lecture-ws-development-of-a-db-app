# C2: Spring-Ressource

Zeitbox: etwa 50 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

`starter/` enthält Spring Boot, das vollständige Schema und die Seed-Daten aus B2, Response-DTO, Fehlerkörper und ein fertiges Read-Repository für `rooms`. Offen sind nur die Methoden für `MusicCourse`.

Starttest:

```bash
cd starter && ./gradlew test
```

## Kernauftrag

Implementiert `findAll` und `findById` im `JdbcMusicCourseRepository`. GET liefert `MusicCourseResponse`, nie direkt die Domain-Entity. Unbekannte IDs ergeben 404.

## Vertiefung

Ergänzt einen Link oder eine ID zu vorbereiteten Nachschlagedaten im Response-DTO.

## Vorbereiteter Zwischenstand

Sichtbare Übergabe: Übernehmt eure B1-Dateien als `V1__schema.sql` und `V2__seed.sql`. Falls sie nicht laufen, gibt die Lehrperson den vorbereiteten Referenzstand frei. Hikari aktiviert `PRAGMA foreign_keys=ON` pro Verbindung. `ddl-auto=none` verhindert eine zweite Schemaquelle.

## Ausgang

Die Anwendung startet. GET-Liste und GET nach ID liefern die B2-Daten über das Response-DTO. ID 1 enthält `MU-01`.

Prüfbefehl:

```bash
cd starter && ./gradlew test && SERVER_PORT=18081 ./gradlew bootRun
```

## Auswertung

Welche Verantwortung liegt im Controller, welche im Service und welche im Repository?
