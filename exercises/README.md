# Übungen: eine Hauptentität durch alle Schichten

Jede Gruppe vergleicht in A1 und A3 zwei Themen. Danach wandert die Hauptentität aus Thema A weiter in SQL, JDBC und eine Spring-API. Jeder Aufgabenordner hat einen eigenen Startstand, einen Prüfbefehl und eine Frage für die gemeinsame Auswertung.

## Gruppenzuordnung

| Gruppe | Thema A                 | Thema B                 |
| -----: | ----------------------- | ----------------------- |
|      1 | `1 · library`           | `10 · food-marketplace` |
|      2 | `2 · pizza-delivery`    | `9 · hotel`             |
|      3 | `3 · gym`               | `8 · event-tickets`     |
|      4 | `4 · cinema`            | `7 · car-workshop`      |
|      5 | `5 · bike-rental`       | `6 · vet-clinic`        |
|      6 | `6 · vet-clinic`        | `5 · bike-rental`       |
|      7 | `7 · car-workshop`      | `4 · cinema`            |
|      8 | `8 · event-tickets`     | `3 · gym`               |
|      9 | `9 · hotel`             | `2 · pizza-delivery`    |
|     10 | `10 · food-marketplace` | `1 · library`           |

In A1 und A3 teilt ihr euch in zwei Teilteams und bearbeitet beide Themen parallel. Danach arbeitet ihr nur mit Thema A weiter. Die Ordner `museum` und `parcel-delivery` sind Reserveübungen. `music-school` ist eine weitere vollständige Übung mit einer ausführlich kommentierten Musterlösung.

## Domänen und Hauptentitäten

| Nr. | Ordner             | Domäne             | Hauptentität  | Einsatz  |
| --- | ------------------ | ------------------ | ------------- | -------- |
| 1   | `library`          | Bibliothek         | `Book`        | regulär  |
| 2   | `pizza-delivery`   | Pizza-Lieferdienst | `Pizza`       | regulär  |
| 3   | `gym`              | Fitnessstudio      | `Course`      | regulär  |
| 4   | `cinema`           | Kino               | `Movie`       | regulär  |
| 5   | `bike-rental`      | Fahrradverleih     | `Station`     | regulär  |
| 6   | `vet-clinic`       | Tierarztpraxis     | `Medication`  | regulär  |
| 7   | `car-workshop`     | Kfz-Werkstatt      | `Part`        | regulär  |
| 8   | `event-tickets`    | Ticketplattform    | `Venue`       | regulär  |
| 9   | `hotel`            | Hotel              | `RoomType`    | regulär  |
| 10  | `food-marketplace` | Liefermarktplatz   | `Restaurant`  | regulär  |
| R1  | `museum`           | Museum             | `Exhibit`     | Reserve  |
| R2  | `parcel-delivery`  | Paketdienst        | `Parcel`      | Reserve  |
| R3  | `music-school`     | Musikschule        | `MusicCourse` | Beispiel |

`_template` ist die vollständig ausführbare Vorlage für weitere Themen.

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
| C1  | `c1-http-contract`    | OpenAPI aus dem Code   |  ca. 25 Min. |
| C2  | `c2-spring-resource`  | Spring-Ressource       |  ca. 50 Min. |
| C3  | `c3-tests-errors`     | POST, Tests und Fehler | 45 + 35 Min. |

Die Zeiten gelten für den Kernauftrag. Die Vertiefung ist kein Pflichtstoff. Nach B2 und B3 sind fünf bis zehn Minuten für die Auswertung nötig.

## Start

1. Öffnet das README eurer Domäne.
2. Arbeitet die Phasen in der angegebenen Reihenfolge ab.
3. Führt vor und nach jeder Phase den genannten Befehl aus.
4. Nutzt den vorbereiteten Zwischenstand nur, wenn die Lehrperson ihn freigibt.

B3 und C2 sind eigenständige Gradle-Projekte. Sie enthalten den Gradle-Wrapper aus dem Kursprojekt, aber keine Caches oder Build-Ausgaben. Java 21 genügt für `./gradlew test`. SQLite-Fremdschlüssel werden pro Verbindung eingeschaltet. Das Spring-Projekt nutzt Flyway und Spring Data JPA. Hibernate verwendet den SQLite-Dialekt und erzeugt kein Schema, weil `ddl-auto=none` gesetzt ist.

B3 zeigt JDBC und sichtbares Row-Mapping. B4 zieht eine JDBC-freie Repository-Schnittstelle davor. Das In-Memory-Repository ist eine Vertiefung. C2 übernimmt die Repository-Grenze als Idee. Der vorbereitete `Jpa<Entity>Repository` passt zwischen Domänen-Record und `SpringData<Entity>Repository`; die JDBC-Implementierung aus B3 wird nicht kopiert.

## Lösungen

Vollständige Lösungen für A0 bis C3 gehören zum separaten Lehrendenpaket und sind in diesem Branch nicht enthalten. Verweise auf `instructor-solutions/` in den Aufgaben bezeichnen Dateien aus diesem Paket, die die Lehrperson bei Bedarf freigibt. Die Gruppenordner enthalten TODOs, wenige Beispieldaten und lauffähige technische Grundgerüste.

## OpenAPI in C1 bis C3

Alle 13 Themen und `_template` enthalten SpringDoc im C2-Starter. In C1 bauen die Gruppen die Request- und Response-Records aus einer Feldvorgabe und dokumentieren GET und POST. `/v3/api-docs` generiert die Spec, `/swagger-ui.html` zeigt sie an. Es gibt keine von Hand zu pflegende YAML-Datei.

Der Baseline-Build läuft trotz offener Endpunktkörper. `OpenApiStarterTest` prüft von Beginn an nur das Gerüst. C1 endet mit dem aktivierten `OpenApiContractExerciseTest`. In C2 implementieren die Gruppen erst die Liste und aktivieren `readsSortedSeedRows` sowie `readsSeedData`. Danach folgt GET nach ID mit `readsKnownIdAndReportsMissingId`. Jeder Weg führt durch Response-Mapping, JPA-Adapter, Service und Controller. In C3 folgen Request-Validation, Konvertierung, Speichern, Konfliktregel und POST. Tests für einen erfolgreichen und einen ungültigen POST sind vorgegeben; die Gruppen implementieren die TODO-Tests für 404 und 409 selbst.
