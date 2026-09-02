# Spring-Vorlage für das gemeinsame Beispiel

Dieses Projekt ist der vorbereitete Spring-Stand der Hochschulverwaltung. Der Controller, der JDBC-Adapter, die Fehlerbehandlung und die Verbindung zum Frontend sind vorhanden. Wir schreiben in der Vorlesung die Migrationen, vervollständigen die DTO-Regeln und implementieren den Service.

## Was vorbereitet ist

- Spring Web, Spring JDBC, Validation und Flyway
- SQLite mit `PRAGMA foreign_keys=ON` für jede Pool-Verbindung
- OpenAPI unter `/swagger-ui.html`
- ein Fehlerkörper mit `code`, `message`, `correlationId` und optionalen `fields`
- `CorrelationIdFilter`, der `X-Correlation-ID` übernimmt oder erzeugt
- CORS für das Frontend unter `http://localhost:3000`

Technische Exceptions bleiben im Log. Der Handler sendet bei einem unerwarteten Fehler weder SQL noch Dateipfade oder Exception-Texte an den Client.

## Start

```sh
./gradlew build
./gradlew bootRun
```

Ohne `SERVER_PORT` läuft die Anwendung auf Port 8081. Die beiden Dateien unter `db/migration/` enden zunächst auf `.sql.todo`. Wir füllen sie mit dem Schema und den Seed-Daten aus `../sql/` und entfernen erst danach die Endung `.todo`. So registriert Flyway keine leere Migration.

Der Health Check läuft sofort:

```text
GET http://localhost:8081/api/students/health
```

## Arbeitsstellen

- `src/main/resources/db/migration/`: Schema und Seed-Daten aus `../sql/` übernehmen, dann beide Dateien von `.sql.todo` nach `.sql` umbenennen
- `dto/CreateStudentRequest.java`: Eingabefelder und Validation begründen
- `dto/StudentResponse.java`: zugesagte Ausgabefelder und Mapping festlegen
- `service/StudentService.java`: `findAll`, `findById` und `create` implementieren
- `src/test/java/`: vorbereitete Tests aktivieren und als Rückmeldung während der Implementierung nutzen

`requests.http` enthält die Aufrufe für jeden Zwischenstand. Das Frontend erwartet `GET /api/students` als JSON-Array und `POST /api/students` mit dem Kursvertrag.
