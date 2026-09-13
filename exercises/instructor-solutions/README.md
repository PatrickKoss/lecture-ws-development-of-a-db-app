# Lehrendenpaket

Dieser Ordner enthält die Lösungen für A0 bis C3 in allen 13 Domänen, im
gemeinsamen [Hochschulbeispiel](common-example/README.md) und in `_template`.
Gebt gezielt einzelne Abschnitte als vorbereiteten Zwischenstand frei. Der
Ordner gehört nicht in die Teilnehmerausgabe.

## Aufbau jeder Lösung

| Ordner | Phasen | Inhalt |
| --- | --- | --- |
| `design/` | A0 bis A3 | Fachbegriffe, Annahmen, ER-Modell, Relationen und Normalisierung |
| `sql/` | B1 und B2 | SQLite-Schema, Seed-Daten und gelöste Abfragen |
| `jdbc/` | B3 | Ausführbares Gradle-Projekt mit PreparedStatements, Row-Mapping und Datenbanktests |
| `repository/` | B4 | Ausführbares Repository-Refactoring mit Catalog und In-Memory-Tests |
| `api/` | C1 | Erklärung des HTTP-Vertrags und exportierte OpenAPI-Spec |
| `spring/` | C1 bis C3 | Ausführbare Spring-Anwendung mit REST-API, ORM und Integrationstests |

Die Java-Projekte enthalten ihren Build und Gradle-Wrapper. Startet sie direkt
im jeweiligen Lösungsordner. Kopieren in einen Teilnehmer-Starter ist nicht nötig.
Java 21 und für die SQL-Prüfung `sqlite3` müssen installiert sein. Beim ersten
Gradle-Aufruf werden Gradle und die Maven-Abhängigkeiten heruntergeladen.

```bash
cd exercises/instructor-solutions/library/jdbc
./gradlew test
cd ../repository
./gradlew test
cd ../spring
./gradlew test
SERVER_PORT=18081 ./gradlew bootRun
```

## Schichten der Spring-Lösung

Der Controller nimmt Request-DTOs entgegen, prüft sie mit Bean Validation und
liefert Response-DTOs mit dem passenden HTTP-Status. Der Service führt die
Anwendungsfälle in Transaktionen aus. Das Repository liest und schreibt über
Spring Data JPA und Hibernate. Persistenz-Entities werden nicht als
HTTP-Antwort ausgegeben.

Flyway legt die SQLite-Tabellen an und lädt die Referenzdaten.
`spring.jpa.hibernate.ddl-auto=none` verhindert, dass Hibernate das Schema
zusätzlich verändert. SQLite verwendet den Dialekt aus
`hibernate-community-dialects`. Die Spring-Repositories enthalten keine
handgeschriebenen SQL-Abfragen. In B2 und B3 bleiben SQL und JDBC sichtbar,
weil diese Phasen genau diese Techniken vermitteln.

Der Teilnehmer-Starter in C2 enthält dieselbe ORM-Struktur mit offenen
Adaptermethoden. In C2 ergänzt die Gruppe die Lesezugriffe, in C3 das Speichern
und die Eindeutigkeitsprüfung. Der JDBC-Stand aus B3/B4 bleibt zum Vergleich
erhalten. Das jeweilige Spring-README erklärt die Endpunkte der Hauptentität.

SpringDoc erzeugt den Vertrag unter `/v3/api-docs`; Swagger UI liegt unter
`/swagger-ui.html`. Der Export unter `api/` macht die Spec auch ohne laufende
Anwendung lesbar. Die Tests prüfen den Vertrag zusammen mit dem Verhalten der
Endpunkte. Hinweise zur Implementierung stehen in der
[Spring-Data-Dokumentation zu Transaktionen](https://docs.spring.io/spring-data/jpa/reference/jpa/transactions.html)
und der [Hibernate-Dokumentation zu Dialekten](https://docs.hibernate.org/stable/orm/dialect/).

## Alle Lösungen prüfen

Vom Repository-Stamm aus:

```bash
bash exercises/instructor-solutions/verify.sh
```

Der Befehl lädt Schema und Seed-Daten mit aktivierten Fremdschlüsseln in eine
temporäre SQLite-Datenbank im Speicher, führt die Abfragen aus und prüft die
Datenbankintegrität sowie den Indexvergleich. Danach startet er die JDBC-, Repository- und Spring-Tests
jeder Domäne und vergleicht den generierten OpenAPI-Vertrag mit dem Export unter
`api/`. Er bricht beim ersten Fehler ab. Die Protokolle liegen unter
`build/verification/<domain>/` in diesem Ordner.

Einzelne Domänen oder nur SQL lassen sich getrennt prüfen:

```bash
bash exercises/instructor-solutions/verify.sh library music-school
bash exercises/instructor-solutions/verify.sh common-example
bash exercises/instructor-solutions/verify.sh --sql-only
bash exercises/instructor-solutions/verify.sh --with-starters
```

`--with-starters` prüft zusätzlich die lauffähigen C2-Startstände aller
Teilnehmergruppen. Die mit `@Disabled` markierten Übungstests werden dort erst
nach der Bearbeitung aktiviert. In den Musterlösungen laufen alle Tests.

`museum` und `parcel-delivery` sind Reserveübungen. `music-school` ist das
zusätzliche Beispiel. Für alle drei gilt derselbe Aufbau wie für die zehn
regulären Themen.
