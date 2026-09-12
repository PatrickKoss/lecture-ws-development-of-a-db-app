# OpenAPI-Overlay für gym

Das Overlay ersetzt `CourseController`, `CreateCourseRequest` und `CourseResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/courses` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `courseCode` und den
`Location`-Header.

Vom Verzeichnis `exercises/gym` aus:

```bash
cp -R ../instructor-solutions/gym/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
