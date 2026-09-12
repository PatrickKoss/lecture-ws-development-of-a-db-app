# OpenAPI-Overlay für cinema

Das Overlay ersetzt `MovieController`, `CreateMovieRequest` und `MovieResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/movies` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `movieCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/cinema` aus:

```bash
cp -R ../instructor-solutions/cinema/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
