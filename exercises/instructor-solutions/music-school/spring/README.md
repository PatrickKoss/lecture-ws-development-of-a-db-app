# Musterlösung für C2 und C3

Die Verzeichnisstruktur unter `src/` entspricht dem C2-Starter. Kopiert die
Dateien in das Starterprojekt, um die offenen Implementierungen zu ersetzen.
Die vorhandenen Klassen für Domain, Service und Fehlerbehandlung bleiben
unverändert.

## C2: GET

`JdbcMusicCourseRepository` hält SQL und Row Mapping zusammen. `findAll` gibt
eine nach ID sortierte Liste zurück. `findById` wandelt die Ergebnisliste in ein
`Optional` um. Der Service übersetzt ein leeres `Optional` in die vorbereitete
`NotFoundException`, der globale Handler daraus in 404.

Der Controller gibt `MusicCourseResponse` aus und nicht das Domain-Objekt. So
kann das interne Modell später weitere Felder erhalten, ohne dass sie
automatisch Teil des HTTP-Vertrags werden.

Die OpenAPI-Annotationen am Controller beschreiben Liste, Einzelabfrage und
POST. Die DTO-Annotationen ergänzen die fachlichen Feldbeschreibungen und
Beispiele. SpringDoc verbindet diese Angaben mit den Typen und
Validierungsregeln aus dem Java-Code.

## C3: POST und Fehler

Der Request validiert Kurscode und Titel mit `@NotBlank`. Die Gebühr muss
vorhanden sein, darf nicht negativ und nicht größer als 500 sein. `@Valid` am
Controller aktiviert diese Prüfung vor dem Service-Aufruf.

Der Service prüft `existsByCourseCode`. Ein vorhandener Kurscode ist formal
gültig, kollidiert aber mit dem aktuellen Datenbestand. Deshalb wirft der
Service eine `ConflictException`, die der globale Handler als 409 ausgibt. Das
UNIQUE-Constraint bleibt trotzdem bestehen. Die Vorabfrage liefert den
verständlichen Fachfehler, das Constraint schützt die Daten auch bei zwei
gleichzeitigen Requests. Eine produktive Anwendung sollte den seltenen
Constraint-Fehler zwischen Vorabfrage und INSERT ebenfalls in 409 übersetzen.

Das Repository bindet alle POST-Werte im `PreparedStatement` und liest den von
SQLite erzeugten Schlüssel. Der Controller antwortet mit 201, dem Response-DTO
und `Location: /api/courses/{id}`.

Die Controller-Tests prüfen genau die drei Ausgänge aus der Aufgabe: 201, 400
und 409. Feste Werte für `X-Correlation-ID` machen die Fehlerantworten
vorhersehbar. Der technische Stacktrace bleibt im Server-Log. `OpenApiTest`
ruft `/v3/api-docs` auf und prüft GET, POST, Modelle, Beispiele und den
`Location`-Header im generierten Vertrag.

## Prüfen

Vom Verzeichnis `exercises/music-school` aus:

```bash
cp -R ../instructor-solutions/music-school/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew test
```

Danach kann die Anwendung mit `SERVER_PORT=18081 ./gradlew bootRun` gestartet
und mit `requests.http` geprüft werden.
