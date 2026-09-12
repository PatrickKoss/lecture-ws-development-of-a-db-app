# OpenAPI-Overlay für car-workshop

Das Overlay ersetzt `PartController`, `CreatePartRequest` und `PartResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/parts` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `partNumber` und den
`Location`-Header.

Vom Verzeichnis `exercises/car-workshop` aus:

```bash
cp -R ../instructor-solutions/car-workshop/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
