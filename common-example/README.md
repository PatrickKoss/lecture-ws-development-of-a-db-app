# Gemeinsames Beispiel: Hochschulverwaltung

Wir bearbeiten vor jeder Gruppenphase denselben Schritt an der Hochschulverwaltung. `Student` bleibt die Hauptentität: zuerst im Fachmodell, dann in Tabellen, JDBC und der REST-API. Lehrende, Lehrveranstaltungen, Fachbereiche und Belegungen liefern die Beziehungen für Modellierung und SQL.

Die [gemeinsamen Übungen](exercises/README.md) folgen denselben Phasen A0 bis C3 wie die [Gruppenübungen](../exercises/README.md). Sie enthalten den Arbeitsauftrag für die Vorlesung, vorbereitete Arbeitsstellen und Links zu den vollständigen Lösungen. Die Lösungen sind Teil dieses Repositories und dürfen während der Gruppenarbeit als Beispiel verwendet werden.

## Arbeitsauftrag und Lösung

| Phase | Gemeinsam bearbeiten | Vollständiges Beispiel |
| --- | --- | --- |
| A0 | [Domäne und Hauptentität](exercises/a0-domain/README.md) | [Fachregeln](design/domain.md) |
| A1 | [ER-Modell](exercises/a1-er-model/README.md) | [ER-Diagramm](design/er.mmd) |
| A2 | [Relationenmodell](exercises/a2-relational-model/README.md) | [Relationen](design/relational-model.md) |
| A3 | [Normalisierung](exercises/a3-normalization/README.md) | [Herleitung bis 3NF](design/normalization.md) |
| B1 | [Schema und Seed-Daten](exercises/b1-schema/README.md) | [Schema](sql/schema.sql) und [Seed-Daten](sql/seed.sql) |
| B2 | [SQL-Abfragen](exercises/b2-sql/README.md) | [SQL-Beispiele und Prüfung](sql/README.md) |
| B3 | [JDBC und Row-Mapping](exercises/b3-jdbc/README.md) | [JDBC-Lösung](jdbc/README.md) |
| B4 | [Repository-Refactoring](exercises/b4-repository/README.md) | [Repository-Lösung](repository-basic/README.md) |
| C1 | [DTOs und HTTP-Vertrag](exercises/c1-http-contract/README.md) | [Spring-Lösung](backend/README.md) |
| C2 | [GET durch alle Schichten](exercises/c2-spring-resource/README.md) | [Spring-Lösung](backend/README.md) |
| C3 | [POST, Fehler und Tests](exercises/c3-tests-errors/README.md) | [Spring-Lösung](backend/README.md) |

## Ablauf in der Vorlesung

1. Öffnet den gemeinsamen Arbeitsauftrag der aktuellen Phase. Haltet zunächst fest, welches Ergebnis ihr erwartet.
2. Bearbeitet die Schritte gemeinsam. Im Programmierteil verwendet ihr die Starter unter `exercises/`; die gelösten Projekte bleiben zum Vergleichen erhalten.
3. Führt den Checkpoint aus. Verfolgt dabei einen konkreten Datensatz oder Fehlerfall.
4. Vergleicht eure Entscheidungen mit der verlinkten Lösung. Die Aufgaben erklären, warum die Lösung so aufgebaut ist.
5. Übertragt denselben Schritt auf die eigene Gruppendomäne. Tabellen, Feldnamen und Fachregeln müsst ihr dafür selbst bestimmen.

B3 beginnt mit JDBC und sichtbarem Row-Mapping. B4 entfernt geprüfte SQL-Ausnahmen aus der Repository-Schnittstelle. C1 bis C3 verwenden Spring Data JPA mit getrennten Request-, Response-, Domänen- und Persistenzmodellen. Im GET-Teil bauen wir zuerst die Liste vollständig und ergänzen danach den Zugriff nach ID. Anschließend folgen POST, Validation, die Konfliktregel und eigene Tests.

## Prüfen und starten

Java 21 genügt für die Java-Projekte. Die SQL-Prüfung verwendet Python 3 mit dessen SQLite-Modul.

```sh
make check-sql
make build-core
make build-starters
```

`build-core` baut und testet die vollständigen Lösungen für JDBC, Repository und Spring. `build-starters` prüft die technischen Startstände. Dort werden die fachlichen Checkpoints erst während der gemeinsamen Arbeit aktiviert. Ein grüner Starterbuild bedeutet deshalb noch nicht, dass die Aufgabe gelöst ist.

Die vollständige API startet ohne vorherige Implementierung:

```sh
cd backend
./gradlew bootRun
```

Die API liegt unter `http://localhost:8081/api/students`, Swagger UI unter `http://localhost:8081/swagger-ui.html`. Das [Frontend](frontend/) verwendet denselben Vertrag.

## Weitere Beispiele

`repository/` zeigt ein generisches Repository mit Reflection. `repository-exercise/` ist eine ältere Zwischenübung. `zone-example/` und `advanced-backend/` enthalten weitere Vertiefungen. Der gemeinsame Pflichtweg verwendet `repository-basic/`.

```sh
make build-all
make test-all
npm --prefix frontend ci
make frontend-test
```

Diese Befehle prüfen auch die weiteren Java-Beispiele beziehungsweise die vorhandenen Frontend-Tests.
