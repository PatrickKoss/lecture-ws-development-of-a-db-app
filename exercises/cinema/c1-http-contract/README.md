# C1: OpenAPI aus dem Code

Zeitbox: etwa 25 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Arbeitet im Projekt `../c2-spring-resource/starter`. Das technische Spring-Gerüst kompiliert bereits. `CreateMovieRequest` und `MovieResponse` sind absichtlich leere Records. Der Controller enthält die Verdrahtung und die Routen für GET und POST. Die dokumentierte Listenroute dient als Beispiel. Bei GET nach ID und POST ergänzt ihr den Antwortvertrag selbst. Die Implementierungen folgen in C2 und C3.

Das gemeinsame Universitätsbeispiel zeigt vorher, wie Request- und Response-Modelle sowie `@Operation`, `@ApiResponse`, `@Schema` und `@ArraySchema` zusammen den Vertrag erzeugen.

Start:

```bash
cd ../c2-spring-resource/starter
./gradlew test
SERVER_PORT=18081 ./gradlew bootRun
```

Öffnet http://localhost:18081/swagger-ui.html. JSON liegt unter http://localhost:18081/v3/api-docs, YAML unter `/v3/api-docs.yaml`.

## Feldvorgabe

`MovieResponse` enthält diese Komponenten in dieser Reihenfolge:

```text
Long id, String movieCode, String title, Integer releaseYear, Integer durationMinutes, String fskCode, Integer minimumAge
```

`CreateMovieRequest` enthält dieselben fachlichen Komponenten ohne `Long id`. Die ID vergibt der Server. Verwendet die angegebenen Java-Typen und Feldnamen unverändert, damit die späteren Checkpoints anschließen können. Validierungsannotationen und Konvertierung ergänzt ihr in C3.

## Kernauftrag

1. Exportiert die unveränderte Spec als `openapi.before.json`.
2. Ergänzt die Komponenten von `CreateMovieRequest` und `MovieResponse`. Beide Records müssen weiter kompilieren. Lasst die vorbereiteten TODO-Methoden zunächst offen.
3. Beschreibt jedes Feld mit `@Schema` und einem konkreten Beispiel aus eurer Domäne. Markiert alle Komponenten im Schema als erforderlich. `id` ist zusätzlich nur lesbar.
4. Vervollständigt den OpenAPI-Vertrag im Controller. Die Liste liefert 200 mit einem Array aus `MovieResponse`. GET nach ID liefert 200 oder 404 mit `ApiError`. POST liefert 201 mit `Location` sowie 400 und 409. Nennt im Konfliktfall den Schlüssel `movieCode`.
5. Aktiviert `OpenApiContractExerciseTest` und führt `./gradlew test` aus. Startet die Anwendung neu und exportiert `openapi.after.json`. Vergleicht beide Dateien und prüft Modelle, Pflichtfelder und Statuscodes in Swagger UI.

Die Endpunktkörper dürfen in C1 noch `UnsupportedOperationException` werfen. `OpenApiStarterTest` prüft nur, ob die Spec mit den vorbereiteten Routen erzeugt wird. Den ergänzten Vertrag prüft `OpenApiContractExerciseTest`.

## Vertiefung

Entwerft den Vertrag für PUT oder DELETE. Haltet Pfad, Request, erfolgreiche Antwort und Fehlerantworten fest. Die Implementierung über alle Schichten gehört erst in die Vertiefung von C3.

## Ausgang

Die beiden DTO-Records enthalten die vorgegebenen Felder. Die generierte Spec beschreibt GET und POST. `OpenApiStarterTest` und der aktivierte `OpenApiContractExerciseTest` sind grün.

Prüfbefehl in einem zweiten Terminal:

```bash
curl --fail http://localhost:18081/v3/api-docs -o openapi.after.json
diff -u openapi.before.json openapi.after.json || true
```

## Auswertung

Welche Angaben erzeugt SpringDoc aus den Java-Typen? Welche fachlichen Angaben musstet ihr selbst formulieren? Warum steht die ID nur im Response?
