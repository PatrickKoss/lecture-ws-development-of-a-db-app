# Musterlösung für C1

Der Vertrag beschreibt die Hauptentität Kurs unter `/api/courses`. Die Datei `openapi.json` stammt aus dem laufenden Spring-Projekt. Der Test `OpenApiContractTest` ruft `/v3/api-docs` auf und schreibt denselben Stand nach `spring/build/openapi/openapi.json`.

| Methode | Pfad | Erfolg | Fehler |
| --- | --- | --- | --- |
| `GET` | `/api/courses` | `200` mit einem Array aus `CourseResponse` | `406`, `500` |
| `POST` | `/api/courses` | `201` mit `CourseResponse` und `Location` | `400`, `406`, `409`, `415`, `500` |
| `GET` | `/api/courses/{id}` | `200` mit `CourseResponse` | `400`, `404`, `406`, `500` |
| `PUT` | `/api/courses/{id}` | `200` mit `CourseResponse` | `400`, `404`, `406`, `409`, `415`, `500` |
| `DELETE` | `/api/courses/{id}` | `204` ohne Body | `400`, `406`, `409`, `500` |

`CreateCourseRequest` und `UpdateCourseRequest` enthalten `courseCode, title, level, durationMinutes, roomId`. Die ID fehlt in beiden Eingaben, weil der Server sie vergibt und der Pfad die zu ändernde Ressource bestimmt. `CourseResponse` ergänzt `id`. Die Feldbeschreibungen, Beispiele und Wertebereiche stehen unter `components.schemas`.

Ein bereits vorhandener Wert für `courseCode` führt bei POST oder PUT zu `409 Conflict`. `400 Bad Request` bedeutet, dass der Request die Feldvalidierung verletzt oder kein gültiges JSON ist. `415 Unsupported Media Type` bedeutet, dass der Client für einen JSON-Request einen anderen Medientyp gesendet hat. Fehler verwenden immer `ApiError` mit `code`, `message`, `correlationId` und `fields`.

`406 Not Acceptable` gilt für einen nicht unterstützten Wert im `Accept`-Header. Unerwartete Serverfehler werden als `500 Internal Server Error` im selben Fehlerformat dokumentiert.

Alle dokumentierten Antworten tragen `X-Correlation-ID`. POST setzt auch `Location` auf die URL der neu angelegten Ressource. PUT ersetzt alle änderbaren Felder und ist idempotent. DELETE ist ebenfalls idempotent. Bei einer vorhandenen und bei einer bereits gelöschten ID antwortet der Server mit `204 No Content`.

Den Vertrag erzeugt und prüft der Spring-Test:

```bash
cd ../spring
./gradlew test
cmp build/openapi/openapi.json ../api/openapi.json
```

Swagger UI steht bei laufender Anwendung unter `/swagger-ui.html`. Der JSON-Vertrag ist unter `/v3/api-docs` abrufbar. `openapi.json` wird aus SpringDoc exportiert und nicht von Hand gepflegt.
