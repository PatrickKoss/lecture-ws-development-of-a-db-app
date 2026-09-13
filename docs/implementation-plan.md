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
- C1 erstellt Request- und Response-Felder nach der Domänenspezifikation und dokumentiert GET und POST unter `/api/<resource>`. Die Methoden bleiben zunächst offen.
- C2 implementiert zuerst die GET-Liste mit Response-Mapping, Repository, Service und Controller. Danach folgt GET nach ID mit dem 404-Fall.
- C3 ergänzt Request-Validation, Mapping, Repository-Schreiben, die Konfliktregel im Service und POST mit `201` und `Location`. Die Gruppen schreiben eigene Tests für unbekannte IDs und doppelte Fachschlüssel. PUT und DELETE werden als Vertiefung in allen betroffenen Schichten ergänzt; Beziehungen bleiben ebenfalls Vertiefung.

Vor jeder Gruppenphase demonstriert die Lehrperson den entsprechenden Schritt am Hochschulbeispiel. Die Gruppen erhalten kompilierende Klassen mit Arbeitsstellen. Konfiguration, JPA-Entity, Spring-Data-Interface, Konstruktoren und technische Fehlerbehandlung bleiben vorbereitet. Ein erfolgreicher Startbuild prüft das Grundgerüst; erst die phasenweise aktivierten Vertragstests und Verhaltenstests prüfen die studentische Implementierung.

## Lösungen

Das gemeinsame Hochschulbeispiel enthält Aufgaben A0 bis C3 unter `common-example/exercises/` und vollständige Lösungen zum Nachschlagen. JDBC und Spring haben zusätzlich kompilierende Starter für die gemeinsame Implementierung. Die Repository-Lösung für den Pflichtteil steht in `repository-basic/`; die Reflection-Variante bleibt Vertiefung.

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
