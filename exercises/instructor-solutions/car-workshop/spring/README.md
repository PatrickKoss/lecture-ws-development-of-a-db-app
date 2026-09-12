# Spring-Lösung: car-workshop

Dieses Projekt ist die vollständige Instructor-Lösung für C1 bis C3. Es läuft eigenständig mit Java 21:

```bash
./gradlew test
SERVER_PORT=18081 ./gradlew bootRun
```

Die API liegt unter `/api/parts`. Sie unterstützt Liste, Lesen nach ID, POST, vollständiges PUT und idempotentes DELETE. `requests.http` enthält passende Aufrufe. SpringDoc stellt Swagger UI unter `/swagger-ui.html` und das JSON-Dokument unter `/v3/api-docs` bereit. Der OpenAPI-Test schreibt denselben generierten Vertrag nach `build/openapi/openapi.json`.

Der Controller wandelt Request-DTOs in `PartCommand` um und gibt nur Response-DTOs zurück. `PartService` setzt die Transaktionsgrenzen und prüft fachliche Konflikte. `PartRepository` ist die vom Service verwendete Abstraktion. `JpaPartRepository` und die Spring-Data-Schnittstelle bilden die Persistenzschicht. Hibernate mappt die Domainwerte auf `parts`; im Anwendungscode steht kein SQL. Auch das vorbereitete Lookup liest über Spring Data JPA.

Flyway bleibt die einzige Schemaquelle. `ddl-auto=none` verhindert, dass Hibernate Tabellen anlegt oder verändert. Jede Pool-Verbindung aktiviert SQLite-Fremdschlüssel. `saveAndFlush` meldet Constraints innerhalb der Service-Transaktion, damit der HTTP-Handler sie als 409 ohne SQL- oder Treiberdetails zurückgibt.

Fehler verwenden `ApiError` mit `code`, `message`, `correlationId` und `fields`. Ein syntaktisch gültiger `X-Correlation-ID`-Wert wird gespiegelt. Unsichere Werte ersetzt der Filter durch eine UUID. Bean Validation ergibt 400, unbekannte IDs 404, fachliche und relationale Konflikte 409, ein falscher Content-Type 415.

Quellen für die technische Entscheidung: [Spring Data JPA zu Transaktionsgrenzen](https://docs.spring.io/spring-data/jpa/reference/jpa/transactions.html) und [Hibernate-Dialekte einschließlich SQLite](https://docs.hibernate.org/stable/orm/dialect/).
