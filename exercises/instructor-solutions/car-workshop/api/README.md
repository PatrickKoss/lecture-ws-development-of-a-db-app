# Musterlösung für C1

`PartController` dokumentiert die Liste, die Einzelabfrage und POST unter
`/api/parts`. Die GET-Antworten verwenden `PartResponse`. POST nimmt
`CreatePartRequest` entgegen und liefert bei Erfolg 201 mit einem `Location`-Header.
Wenn `partNumber` bereits vergeben ist, beschreibt der Vertrag eine
409-Antwort mit `ApiError`.

Die `@Schema`-Annotationen an beiden DTOs erklären jedes Feld und enthalten
konkrete Beispiele, darunter `partNumber`. SpringDoc übernimmt Java-Typen und
Validierungsgrenzen. Es erzeugt die OpenAPI-Beschreibung aus dem laufenden
Spring-Projekt.

Kopiert das Overlay in den C2-Starter und baut das Projekt:

```bash
cp -R ../instructor-solutions/car-workshop/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
SERVER_PORT=18081 ./gradlew bootRun
```

Swagger UI läuft danach unter `http://localhost:18081/swagger-ui.html`.
SpringDoc liefert JSON unter `/v3/api-docs` und YAML unter
`/v3/api-docs.yaml`. Diese Exporte werden nicht von Hand bearbeitet.
