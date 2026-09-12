# OpenAPI-Overlay für hotel

Das Overlay ersetzt `RoomTypeController`, `CreateRoomTypeRequest` und `RoomTypeResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/room-types` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `typeCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/hotel` aus:

```bash
cp -R ../instructor-solutions/hotel/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
