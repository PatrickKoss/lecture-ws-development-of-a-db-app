# Gemeinsames Beispiel: Hochschulverwaltung

Der Kurs entwickelt eine kleine Hochschulverwaltung. Wir beginnen mit dem Satz "Studierende belegen Lehrveranstaltungen, die von Lehrenden angeboten werden". Aus diesem Satz entstehen nacheinander ER-Modell, normalisierte Tabellen, SQL-Abfragen, JDBC-Code, ein Repository, eine REST API und ein Frontend.

`Student` bleibt die Hauptressource für den Programmierteil. `Course`, `Lecturer`, `Department` und `Enrollment` bleiben im Schema erhalten. Dadurch verwenden SQL-Abfragen weiterhin Beziehungen, JOINs und Aggregationen.

## Arbeitsstände

| Phase     | Ordner                       | Gemeinsame Arbeit                         |
| --------- | ---------------------------- | ----------------------------------------- |
| A0 bis A3 | [`design/`](design/)         | Fachregeln, ER-Modell und Normalisierung  |
| B1 und B2 | [`sql/`](sql/)               | Schema, Seed-Daten und Abfragen           |
| B3        | [`jdbc/`](jdbc/)             | Verbindungen, Statements und Row-Mapping  |
| B4        | [`repository/`](repository/) | Repository und wiederverwendbares Mapping |
| C1 bis C3 | [`backend/`](backend/)       | Migrationen, DTOs und Service             |
| Abschluss | [`frontend/`](frontend/)     | API im Browser verwenden                  |

`repository-exercise/` enthält eine kurze Zwischenübung. `zone-example/` und `advanced-backend/` sind Reservebeispiele. Sie gehören nicht zum roten Faden.

## Gemeinsamer Ablauf

1. Ergänzt die Fachregeln in `design/domain.md` und zeichnet das ER-Modell.
2. Normalisiert die flache Ausgangstabelle in `design/normalization.md` bis zur 3NF.
3. Übertragt das Ergebnis nach `sql/schema.sql` und formuliert die Abfragen in `sql/queries.sql`.
4. Öffnet `jdbc/` und verfolgt eine Student-Zeile durch `Connection`, `PreparedStatement` und `ResultSet`.
5. Verschiebt den Datenzugriff in `repository/`.
6. Öffnet `backend/`. Implementiert dort Migrationen, DTO-Regeln und `StudentService`.
7. Startet `frontend/` gegen `http://localhost:8081`.

## Prüfen

```sh
make build-all
make test-all
make frontend-test
```

Die Spring-Vorlage kompiliert vor der gemeinsamen Implementierung. Fachliche Aufrufe liefern an den markierten Stellen noch einen Fehler. Der Health Check unter `/api/students/health` funktioniert sofort.
