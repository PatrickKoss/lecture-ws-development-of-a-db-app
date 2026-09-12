# OpenAPI-Overlay für library

Das Overlay ersetzt `BookController`, `CreateBookRequest` und `BookResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/books` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `isbn` und den
`Location`-Header.

Vom Verzeichnis `exercises/library` aus:

```bash
cp -R ../instructor-solutions/library/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
