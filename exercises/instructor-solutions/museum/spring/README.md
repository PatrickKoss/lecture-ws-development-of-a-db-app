# OpenAPI-Overlay für museum

Das Overlay ersetzt `ExhibitController`, `CreateExhibitRequest` und `ExhibitResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/exhibits` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `inventoryCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/museum` aus:

```bash
cp -R ../instructor-solutions/museum/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
