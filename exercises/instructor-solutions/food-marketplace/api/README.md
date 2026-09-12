# Musterlösung für C1

`RestaurantController` dokumentiert die Liste, die Einzelabfrage und POST unter
`/api/restaurants`. Die GET-Antworten verwenden `RestaurantResponse`. POST nimmt
`CreateRestaurantRequest` entgegen und liefert bei Erfolg 201 mit einem `Location`-Header.
Wenn `partnerNumber` bereits vergeben ist, beschreibt der Vertrag eine
409-Antwort mit `ApiError`.

Die `@Schema`-Annotationen an beiden DTOs erklären jedes Feld und enthalten
konkrete Beispiele, darunter `partnerNumber`. SpringDoc übernimmt Java-Typen und
Validierungsgrenzen. Es erzeugt die OpenAPI-Beschreibung aus dem laufenden
Spring-Projekt.

Kopiert das Overlay in den C2-Starter und baut das Projekt:

```bash
cp -R ../instructor-solutions/food-marketplace/spring/src/* \
  c2-spring-resource/starter/src/
cd c2-spring-resource/starter
./gradlew clean build
SERVER_PORT=18081 ./gradlew bootRun
```

Swagger UI läuft danach unter `http://localhost:18081/swagger-ui.html`.
SpringDoc liefert JSON unter `/v3/api-docs` und YAML unter
`/v3/api-docs.yaml`. Diese Exporte werden nicht von Hand bearbeitet.
