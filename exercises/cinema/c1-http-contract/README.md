# C1: OpenAPI aus dem Code

Zeitbox: etwa 25 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Arbeitet im Projekt `../c2-spring-resource/starter`. Es enthält das Modell `Movie`, getrennte Request- und Response-DTOs sowie SpringDoc. `MovieController` zeigt am POST-Endpunkt bereits, wie 201, `Location`, 400 und 409 dokumentiert werden.

`OpenApiStarterTest` ist aktiv und prüft, dass der Anwendungskontext und die Spec ohne Aufruf der noch offenen JPA-Adaptermethoden funktionieren.

Start:

```bash
cd ../c2-spring-resource/starter
./gradlew test
SERVER_PORT=18081 ./gradlew bootRun
```

Öffnet http://localhost:18081/swagger-ui.html. JSON liegt unter http://localhost:18081/v3/api-docs, YAML unter `/v3/api-docs.yaml`.

## Kernauftrag

1. Lest die Annotationen am POST-Endpunkt. `@Operation` beschreibt die Operation, `@ApiResponse` eine Antwort und `@Schema` ein Modell. Exportiert die unveränderte Spec als `openapi.before.json`.
2. Ändert an mindestens drei Feldern von `CreateMovieRequest` und `MovieResponse` die vorbereitete Beschreibung oder das Beispiel. Verwendet konkrete Werte aus eurer Domäne. Der Request enthält keine vom Server vergebene ID.
3. Formuliert Summary oder Beschreibung beider GET-Methoden fachlich genauer. Prüft dabei den vorbereiteten Vertrag: Die Liste liefert 200 mit einem Array aus `MovieResponse`. GET nach ID liefert 200 oder 404 mit `ApiError`. Die Liste verwendet `@ArraySchema`.
4. Formuliert die Konfliktbeschreibung mit eigenen Worten und nennt den Schlüssel `movieCode` ausdrücklich.
5. Startet nach Änderungen neu, exportiert `openapi.after.json` und vergleicht beide Dateien. Prüft in Swagger UI Pflichtfelder, Modelle und Statuscodes.

`@Schema` beschreibt den Vertrag. `@Valid` und Bean Validation prüfen einen Request zur Laufzeit. Die JPA-Abfragen implementiert ihr erst in C2 und C3.

## Vertiefung

Plant PUT oder DELETE. Haltet für jede Operation Pfad, Request, erfolgreiche Antwort und Fehlerantworten fest. Begründet, ob ein wiederholter identischer Request denselben Zustand erzeugt.

## Vorbereiteter Zwischenstand

Das dokumentierte POST dient als Muster. Die Controller- und DTO-Dateien sind vorbereitet. `OpenApiStarterTest` muss während C1 grün bleiben.

## Ausgang

Die generierte Spec beschreibt GET und POST mit ihren DTOs und Fehlerantworten. Gebt die geänderten Java-Dateien und einen Export ab. Bearbeitet den Export nicht von Hand.

Prüfbefehl in einem zweiten Terminal:

```bash
curl --fail http://localhost:18081/v3/api-docs -o openapi.after.json
diff -u openapi.before.json openapi.after.json || true
```

Prüft unter `paths` die GET- und POST-Antworten. Unter `components.schemas` müssen die DTOs, Pflichtfelder und Beispiele stehen.

## Auswertung

Welche Angaben erzeugt SpringDoc aus Java? Welche fachlichen Angaben musstet ihr selbst ergänzen? Was unterscheidet 400 und 409?
