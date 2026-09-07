# C1: OpenAPI aus dem Code

Zeitbox: etwa 25 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Nutzt das Modell `MusicCourse` aus B3 und das Projekt `../c2-spring-resource/starter`.
SpringDoc ist eingebunden. `MusicCourseController` enthält ein dokumentiertes POST-Beispiel mit `CreateMusicCourseRequest`, `MusicCourseResponse`, 201, `Location`, 400 und 409.

Startet die Anwendung aus diesem Ordner:

```bash
cd ../c2-spring-resource/starter
./gradlew test
SERVER_PORT=18081 ./gradlew bootRun
```

Öffnet http://localhost:18081/swagger-ui.html. Die Spec steht unter http://localhost:18081/v3/api-docs, YAML unter `/v3/api-docs.yaml`.
Die Dokumentation funktioniert schon mit den offenen Repository-Methoden. Erfolgreiche Datenzugriffe folgen in C2 und C3.

## Kernauftrag

1. Lest das POST-Beispiel. `@Operation` beschreibt die Operation, `@ApiResponse` Statuscodes und Modelle. `@Schema(implementation = …)` verweist auf Java-DTOs.
2. Ergänzt Beschreibungen und passende Beispiele an den Feldern von `CreateMusicCourseRequest` und `MusicCourseResponse` mit `@Schema`. Beachtet die vorhandenen Validierungsregeln. Der Request enthält keine Server-ID.
3. Dokumentiert beide GET-Methoden mit `@Operation` und `@ApiResponse`. Die Liste liefert 200 mit einem Array aus `MusicCourseResponse`, GET nach ID liefert 200 oder 404 mit `ApiError`. Für das Array nutzt ihr `@Content(array = @ArraySchema(schema = @Schema(implementation = MusicCourseResponse.class)))` und importiert `io.swagger.v3.oas.annotations.media.ArraySchema`.
4. Beschreibt den 409-Fachkonflikt konkret: `course_code` darf nicht doppelt vorkommen. Startet nach Codeänderungen neu. Prüft in Swagger UI Request, Response, Pflichtfelder und Statuscodes.

`@Schema` dokumentiert. `@Valid` und Bean Validation prüfen Eingaben zur Laufzeit. Eine dokumentierte Antwort implementiert das Verhalten noch nicht.

## Vertiefung

Plant PUT oder DELETE und erklärt die Idempotenz. Ergänzt Mapping, Modelle und Annotationen im Controller, wenn ihr die Operation implementiert.

## Vorbereiteter Zwischenstand

Das POST-Beispiel bleibt die Vorlage. Ihr ergänzt GET und die noch fehlenden Feldbeschreibungen. Die Repository-TODOs bearbeitet ihr in C2 und C3.

## Ausgang

Die generierte Spec beschreibt GET und POST samt Modellen und Fehlerantworten. Gebt die geänderten Java-Dateien und einen Export ab. Bearbeitet den Export nicht von Hand.

Prüfbefehl in einem zweiten Terminal aus `c1-http-contract/`:

```bash
curl --fail http://localhost:18081/v3/api-docs -o openapi.generated.json
```

Prüft unter `paths` die GET- und POST-Antworten und unter `components.schemas` eure DTOs und Beispiele.

## Auswertung

Welche Angaben leitet SpringDoc aus Java ab? Welche fachlichen Angaben müsst ihr ergänzen? Was unterscheidet 400 und 409?
