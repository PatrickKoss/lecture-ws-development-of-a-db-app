# OpenAPI-Overlay für food-marketplace

Das Overlay ersetzt `RestaurantController`, `CreateRestaurantRequest` und `RestaurantResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/restaurants` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `partnerNumber` und den
`Location`-Header.

Vom Verzeichnis `exercises/food-marketplace` aus:

```bash
cp -R ../instructor-solutions/food-marketplace/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
