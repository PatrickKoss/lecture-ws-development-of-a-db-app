# Musterlösung für C1

Der Vertrag beschreibt die Hauptentität Ressource unter `/api/resources`. Die Datei `openapi.json` stammt aus dem laufenden Spring-Projekt. Der Test `OpenApiContractTest` ruft `/v3/api-docs` auf und schreibt denselben Stand nach `spring/build/openapi/openapi.json`.

| Methode | Pfad | Erfolg | Fehler |
| --- | --- | --- | --- |
| `GET` | `/api/resources` | `200` mit einem Array aus `ResourceResponse` | `406`, `500` |
| `POST` | `/api/resources` | `201` mit `ResourceResponse` und `Location` | `400`, `406`, `409`, `415`, `500` |
| `GET` | `/api/resources/{id}` | `200` mit `ResourceResponse` | `400`, `404`, `406`, `500` |
| `PUT` | `/api/resources/{id}` | `200` mit `ResourceResponse` | `400`, `404`, `406`, `409`, `415`, `500` |
| `DELETE` | `/api/resources/{id}` | `204` ohne Body | `400`, `406`, `409`, `500` |

`CreateResourceRequest` und `UpdateResourceRequest` enthalten `resourceCode, name, measure`. Die ID fehlt in beiden Eingaben, weil der Server sie vergibt und der Pfad die zu ändernde Ressource bestimmt. `ResourceResponse` ergänzt `id`. Die Feldbeschreibungen, Beispiele und Wertebereiche stehen unter `components.schemas`.

Ein bereits vorhandener Wert für `resourceCode` führt bei POST oder PUT zu `409 Conflict`. `400 Bad Request` bedeutet, dass der Request die Feldvalidierung verletzt oder kein gültiges JSON ist. `415 Unsupported Media Type` bedeutet, dass der Client für einen JSON-Request einen anderen Medientyp gesendet hat. Fehler verwenden immer `ApiError` mit `code`, `message`, `correlationId` und `fields`.

`406 Not Acceptable` gilt für einen nicht unterstützten Wert im `Accept`-Header. Unerwartete Serverfehler werden als `500 Internal Server Error` im selben Fehlerformat dokumentiert.

Alle dokumentierten Antworten tragen `X-Correlation-ID`. POST setzt auch `Location` auf die URL der neu angelegten Ressource. PUT ersetzt alle änderbaren Felder und ist idempotent. DELETE ist ebenfalls idempotent. Bei einer vorhandenen und bei einer bereits gelöschten ID antwortet der Server mit `204 No Content`.

Den Vertrag erzeugt und prüft der Spring-Test:

```bash
cd ../spring
./gradlew test
cmp build/openapi/openapi.json ../api/openapi.json
```

Swagger UI steht bei laufender Anwendung unter `/swagger-ui.html`. Der JSON-Vertrag ist unter `/v3/api-docs` abrufbar. `openapi.json` wird aus SpringDoc exportiert und nicht von Hand gepflegt.
