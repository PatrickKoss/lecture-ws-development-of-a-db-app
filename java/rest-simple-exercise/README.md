# Gemeinsames Spring-Grundgerüst

Dieses Projekt zeigt nur die gemeinsame Infrastruktur des Kurses. Die Gruppen arbeiten nicht direkt hier. Jede Domäne hat unter `exercises/<domain>/c2-spring-resource/starter/` ein eigenes Spring-Projekt mit passendem Schema, Seed-Daten, Hauptressource und TODOs.

## Was vorbereitet ist

- Spring Web, Spring Data JPA, Validation und Flyway
- SQLite mit `PRAGMA foreign_keys=ON` für jede Pool-Verbindung
- `spring.jpa.hibernate.ddl-auto=validate`, damit Hibernate keine fehlende Migration verdeckt
- OpenAPI unter `/swagger-ui.html`
- ein Fehlerkörper mit `code`, `message`, `correlationId` und optionalen `fields`
- `CorrelationIdFilter`, der `X-Correlation-ID` übernimmt oder erzeugt

Technische Exceptions bleiben im Log. Der Handler sendet bei einem unerwarteten Fehler weder SQL noch Dateipfade oder Exception-Texte an den Client.

## Start

```sh
./gradlew build
./gradlew bootRun
```

Ohne `SERVER_PORT` läuft die Anwendung auf Port 8081. Dieses Grundgerüst hat absichtlich keine Domänenmigration. Für die Übung ist immer das gruppenspezifische Projekt maßgeblich.
