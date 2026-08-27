# Stand der Kursüberarbeitung

Grundlage ist der Audit vom 27. August 2026. Die Überarbeitung erhält die fachliche Reihenfolge der Kapitel. Das Normalisierungskapitel bleibt vollständig.

## Durchgängiger Arbeitsstand

Jede Gruppe wählt in A0 eine flache Hauptentität. Dieselbe Entität erscheint danach im ER-Modell, Relationenmodell, SQL, JDBC-Repository und in der REST API.

Die Aufgaben liegen in eigenen Ordnern:

```text
exercises/<domain>/
  a0-domain/
  a1-er-model/
  a2-relational-model/
  a3-normalization/
  b1-schema/
  b2-sql/
  b3-jdbc/starter/
  b4-repository/
  c1-http-contract/
  c2-spring-resource/starter/
  c3-tests-errors/
```

Jeder Aufgabenordner nennt Eingang, Starttest, Kernauftrag, Vertiefung, Hilfe und Ausgang. B3 und C2 enthalten ausführbare Codegerüste. Die Gruppen implementieren die markierten Stellen und beginnen nicht mit einem leeren Projekt.

## Pflichtumfang

- A3 endet mit einer begründeten Zerlegung bis 3NF. BCNF bleibt Teil des Kapitels und eine zusätzliche Prüfaufgabe.
- B1 verwendet vorbereitete Seed-Daten. Die Gruppe prüft Schema und Constraints.
- B2 verlangt vier Abfragen, darunter zwei JOINs und eine Aggregation.
- B3 verlangt `findById`, `findAll`, sichtbares Row-Mapping und einen Fehlerfall. Schreiboperationen sind Vertiefung.
- B4 ist ein kurzes, verbindliches Repository-Refactoring. Schema und Seed-Daten werden als Flyway-Migrationen übergeben.
- C1 definiert GET und POST unter `/api/<resource>`.
- C2 implementiert GET für die Hauptentität auf dem vorbereiteten Spring-Stand.
- C3 ergänzt POST, Validation, genau eine Konfliktregel mit `409 Conflict` und die geforderten Tests. PUT, DELETE und Beziehungen sind Vertiefung.

## Lösungen

Teilnehmerordner enthalten keine fertigen SQL- oder Code-Lösungen an den Arbeitsstellen. Das Lehrendenpaket liegt unter `exercises/instructor-solutions/` und wird aus der Teilnehmerausgabe entfernt.

## Technische Regeln

- Flyway erzeugt das Schema. Die Gruppenstarter verwenden `ddl-auto=none`; das gemeinsame Übungsgerüst und das ausgearbeitete Zonenbeispiel verwenden `validate`. Hibernate schreibt in keinem Projekt das Schema.
- Jede SQLite-Verbindung aktiviert Fremdschlüssel mit `PRAGMA foreign_keys=ON`.
- Datumswerte verwenden die in den Migrationen sichtbare ISO-Darstellung. Ein globaler, von der Systemzeitzone abhängiger Epoch-Konverter wird nicht eingesetzt.
- Fehlerantworten haben `code`, `message`, `correlationId` und bei Validierungsfehlern `fields`.
- Ein Fehler mit Status 500 schreibt die technische Exception ins Log, aber nicht in die HTTP-Antwort.

## Folien

Die Kapitelanfänge markieren den Übergang mit ein bis zwei Leitfragen. Die Übungsfolien verwenden dieselben Phasenkennungen und Ordner wie die Aufgaben. Nach Änderungen werden die Decks gerendert und die betroffenen Folien bei 1920 mal 1080 Pixeln auf Beschnitt, Abstände und Überlagerungen geprüft.

Tag 2 und Tag 3 können als gemeinsamer Implementierungsblock stattfinden. Die Checkpoints B4 und C2 bleiben dabei bestehen, weil sie den tatsächlichen Übergang zwischen JDBC, Flyway und Spring sichtbar machen.
