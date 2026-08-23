# rest-simple-exercise-example

Dies ist die ausgearbeitete Lösung der REST-Übung am Beispiel der Domäne `Zone`. Sie ist für die Lehrperson gedacht. Die Studierenden arbeiten in `../rest-simple-exercise` mit ihrer eigenen Domäne und sehen dieses Projekt erst im Debrief.

## Was das Projekt zeigt

- `ZoneController` mit vollständigem CRUD unter `/api/zones`
- Request- und Response-DTOs in `request/` und `response/`, die Entity `Zone` verlässt die API nie
- Bean Validation an den Request-DTOs und `@Valid` im Controller
- `GlobalExceptionHandler` mit `ErrorResponse` für 400, 404 und 500
- JPA-Entity, `ZoneRepository` als `JpaRepository` und eine Flyway-Migration unter `src/main/resources/db/migration`
- Swagger UI über springdoc

Das entspricht dem Stand nach Übung 10. Ein separater Service-Layer und die hexagonale Architektur sind bewusst nicht enthalten; die zeigt `../rest-simple`.

## Starten

```bash
./gradlew bootRun
```

Ohne gesetztes `SERVER_PORT` läuft die Anwendung auf Port 8081.

- Swagger UI: <http://localhost:8081/swagger-ui.html>
- OpenAPI JSON: <http://localhost:8081/v3/api-docs>

Build prüfen:

```bash
./gradlew build
```

## Bekannte Eigenheit

Die Flyway-Datei heißt `V1_Create_zone.sql`. Flyway erwartet zwei Unterstriche nach der Version und überspringt die Datei deshalb. Das Schema entsteht trotzdem, weil `spring.jpa.hibernate.ddl-auto=update` gesetzt ist. Im Unterricht ist das ein brauchbares Beispiel dafür, dass Namenskonventionen Teil des Vertrags sind.
