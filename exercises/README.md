# Übungen: eine Hauptentität durch alle Schichten

Jede Gruppe behält ihre Domäne und dieselbe flache Hauptentität. Die Arbeit wandert vom ER-Modell in SQL, JDBC und eine Spring-API. Jeder Aufgabenordner hat einen eigenen Startstand, einen Prüfbefehl und eine Frage für die gemeinsame Auswertung.

## Domänen und Hauptentitäten

| Ordner             | Domäne             | Hauptentität |
| ------------------ | ------------------ | ------------ |
| `bike-rental`      | Fahrradverleih     | `Station`    |
| `car-workshop`     | Kfz-Werkstatt      | `Part`       |
| `cinema`           | Kino               | `Movie`      |
| `event-tickets`    | Ticketplattform    | `Venue`      |
| `food-marketplace` | Liefermarktplatz   | `Restaurant` |
| `gym`              | Fitnessstudio      | `Course`     |
| `hotel`            | Hotel              | `RoomType`   |
| `library`          | Bibliothek         | `Book`       |
| `pizza-delivery`   | Pizza-Lieferdienst | `Pizza`      |
| `vet-clinic`       | Tierarztpraxis     | `Medication` |

`_template` ist die elfte, vollständig ausführbare Vorlage für weitere Gruppen.

## Stabile Phasen

| ID  | Ordner                | Ergebnis               |     Kernzeit |
| --- | --------------------- | ---------------------- | -----------: |
| A0  | `a0-domain`           | Domänenidee            |  ca. 15 Min. |
| A1  | `a1-er-model`         | ER-Modell              |  ca. 35 Min. |
| A2  | `a2-relational-model` | Relationenmodell       |  ca. 25 Min. |
| A3  | `a3-normalization`    | Normalisierung         |  ca. 35 Min. |
| B1  | `b1-schema`           | Schema und Seed-Daten  |  ca. 35 Min. |
| B2  | `b2-sql`              | SQL-Abfragen           |  ca. 45 Min. |
| B3  | `b3-jdbc`             | JDBC                   |  ca. 45 Min. |
| B4  | `b4-repository`       | Repository-Refactoring |  ca. 15 Min. |
| C1  | `c1-http-contract`    | HTTP-Vertrag           |  ca. 25 Min. |
| C2  | `c2-spring-resource`  | Spring-Ressource       |  ca. 50 Min. |
| C3  | `c3-tests-errors`     | POST, Tests und Fehler | 45 + 35 Min. |

Die Zeiten gelten für den Kernauftrag. Die Vertiefung ist kein Pflichtstoff. Nach B2 und B3 sind fünf bis zehn Minuten für die Auswertung nötig.

## Start

1. Öffnet das README eurer Domäne.
2. Arbeitet die Phasen in der angegebenen Reihenfolge ab.
3. Führt vor und nach jeder Phase den genannten Befehl aus.
4. Nutzt den vorbereiteten Zwischenstand nur, wenn die Lehrperson ihn freigibt.

B3 und C2 sind eigenständige Gradle-Projekte. Sie enthalten den Gradle-Wrapper aus dem Kursprojekt, aber keine Caches oder Build-Ausgaben. Java 21 genügt für `./gradlew test`. SQLite-Fremdschlüssel werden pro Verbindung eingeschaltet. Das Spring-Projekt nutzt Flyway und setzt Hibernate ausdrücklich auf `ddl-auto=none`.

## Lösungen

Vollständige SQL-Dateien und ER-Vergleichsstände liegen unter `instructor-solutions/`. Dieser Ordner gehört zum Lehrendenpaket und wird nicht an Teilnehmer verteilt. Die Gruppenordner enthalten TODOs, wenige Beispieldaten und lauffähige technische Grundgerüste.
