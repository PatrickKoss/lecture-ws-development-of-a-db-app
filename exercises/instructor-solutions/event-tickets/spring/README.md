# OpenAPI-Overlay für event-tickets

Das Overlay ersetzt `VenueController`, `CreateVenueRequest` und `VenueResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/venues` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `venueCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/event-tickets` aus:

```bash
cp -R ../instructor-solutions/event-tickets/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
