# OpenAPI-Overlay für bike-rental

Das Overlay ersetzt `StationController`, `CreateStationRequest` und `StationResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/stations` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `stationCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/bike-rental` aus:

```bash
cp -R ../instructor-solutions/bike-rental/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
