# OpenAPI-Overlay für parcel-delivery

Das Overlay ersetzt `ParcelController`, `CreateParcelRequest` und `ParcelResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/parcels` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `trackingCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/parcel-delivery` aus:

```bash
cp -R ../instructor-solutions/parcel-delivery/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
