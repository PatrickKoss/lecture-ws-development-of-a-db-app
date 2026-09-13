# Musterlösung für C1

Der Vertrag beschreibt Studierende unter `/api/students`. `openapi.json` stammt aus dem Spring-Projekt und wird durch `OpenApiContractTest` geprüft und exportiert.

| Methode | Pfad | Erfolg | Fehler |
| --- | --- | --- | --- |
| `GET` | `/api/students` | `200` mit einer nach ID sortierten Liste | `500` |
| `GET` | `/api/students/{id}` | `200` mit `StudentResponse` | `400`, `404`, `500` |
| `POST` | `/api/students` | `201` mit `StudentResponse` und `Location` | `400`, `409`, `500` |

`CreateStudentRequest` enthält `firstName`, `lastName`, `email` und `studentNumber`. Der Server vergibt die ID und setzt `enrollmentDate`. Die Response enthält alle sechs Felder. Fehler verwenden `ErrorResponse` mit Korrelations-ID.

Den Vertrag erzeugt und prüft der Spring-Test:

```bash
cd ../spring
./gradlew test
cmp build/openapi/openapi.json ../api/openapi.json
```
