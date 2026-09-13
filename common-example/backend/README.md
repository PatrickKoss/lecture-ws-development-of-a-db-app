# Musterlösung für die Studierenden-API

Dieses Projekt ist die vollständige JPA-Lösung für das gemeinsame Beispiel. Die Übungsfassung liegt unter [`../exercises/c2-spring-resource/starter`](../exercises/c2-spring-resource/starter).

Die API verwendet dieselben Felder wie das Frontend: `id`, `firstName`, `lastName`, `email`, `studentNumber` und `enrollmentDate`. Der Create-Request enthält die vier String-Eingabefelder ohne ID und Datum. `StudentService` setzt das Einschreibedatum mit der injizierten `Clock`. Dadurch kontrolliert der Server das Datum und der Test kann die Zeit festsetzen.

Der Weg durch die Anwendung ist absichtlich sichtbar:

1. `StudentController` verarbeitet HTTP und wandelt Request und Response um.
2. `StudentService` prüft Matrikelnummer und E-Mail-Adresse und vergibt das Datum.
3. `JpaStudentRepository` verwendet Spring Data und übersetzt zwischen Domainobjekt und `StudentJpaEntity`.
4. `saveAndFlush` löst Datenbankregeln noch innerhalb des Adapters aus. `SQLiteConstraintTranslator` übersetzt SQLite-Constraintfehler.

Flyway lädt das Schema und alle Seed-Daten aus `../sql`. Die Migrationskopie entfernt nur `PRAGMA`, `BEGIN TRANSACTION` und `COMMIT`, weil Flyway Verbindung und Transaktion selbst verwaltet.

## Start und Tests

```sh
./gradlew build
./gradlew bootRun
```

Die Anwendung läuft standardmäßig auf Port 8081. OpenAPI ist unter `http://localhost:8081/swagger-ui.html` verfügbar.

```sh
curl -i http://localhost:8081/api/students/health
curl -i http://localhost:8081/api/students
curl -i http://localhost:8081/api/students/1
curl -i -X POST http://localhost:8081/api/students \
  -H 'Content-Type: application/json' \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@campus.example","studentNumber":"M2026999"}'
```

Alle Tests sind aktiv. `StudentApiIntegrationTest` startet SQLite mit einer eigenen temporären Datenbank und prüft Seed-Daten, 404, POST mit `Location`, 409 und Validation mit Korrelations-ID.
