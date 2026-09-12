# OpenAPI-Overlay für pizza-delivery

Das Overlay ersetzt `PizzaController`, `CreatePizzaRequest` und `PizzaResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/pizzas` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `pizzaNumber` und den
`Location`-Header.

Vom Verzeichnis `exercises/pizza-delivery` aus:

```bash
cp -R ../instructor-solutions/pizza-delivery/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
