# OpenAPI-Overlay für vet-clinic

Das Overlay ersetzt `MedicationController`, `CreateMedicationRequest` und `MedicationResponse` im
C2-Starter. Damit beschreibt `/v3/api-docs` die beiden GET-Operationen und POST
unter `/api/medications` vollständig. `OpenApiSolutionTest` prüft die Pfade,
Statuscodes, Schemareferenzen, das Beispiel für `pzn` und den
`Location`-Header.

Vom Verzeichnis `exercises/vet-clinic` aus:

```bash
cp -R ../instructor-solutions/vet-clinic/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
```
