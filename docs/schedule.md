# Verbindlicher Ablauf

Die Aufgaben haben feste Phasenkennungen. Diese Kennungen bleiben gleich, wenn Tag 2 und Tag 3 als gemeinsamer Implementierungsblock stattfinden. In A1 und A3 bearbeitet jede Gruppe zwei Themen. In allen übrigen Phasen arbeitet sie in `exercises/<thema-a>/` mit derselben Hauptentität.

Jede Aufgabe endet mit einem lauffähigen oder lesbaren Checkpoint. Die Lehrperson kann danach den vorbereiteten Zwischenstand freigeben. Eine Gruppe beginnt die nächste Phase nicht mit einem leeren Projekt.

## Phasen

| Phase | Ergebnis                                      |
| ----- | --------------------------------------------- |
| A0    | Domänenbeschreibung und gewählte Hauptentität |
| A1    | ER-Modell                                     |
| A2    | Relationenmodell mit Schlüsseln               |
| A3    | begründete Zerlegung bis 3NF                  |
| B1    | ausführbares Schema mit Seed-Daten            |
| B2    | vier geprüfte SQL-Abfragen                    |
| B3    | JDBC-Mapping für `findById` und `findAll`     |
| B4    | Repository-Grenze und Flyway-Übergabe         |
| C1    | HTTP-Vertrag für GET und POST                 |
| C2    | laufende Spring-Ressource                     |
| C3    | POST, ein Konfliktfall und Tests              |

## Tag 1

| Start | Ende  | Block                        | Deck                            | Aufgabe                                   | Minuten |
| ----- | ----- | ---------------------------- | ------------------------------- | ----------------------------------------- | ------: |
| 09:00 | 09:20 | 00 Auftakt                   | `decks/00-opening.html`         | keine                                     |      20 |
| 09:20 | 09:55 | 01 Warum Abstraktion?        | `decks/01-why-abstraction.html` | keine                                     |      35 |
| 09:55 | 10:10 | A0 Domäne und Hauptentität   | `decks/01-why-abstraction.html` | `exercises/<domain>/a0-domain/`           |      15 |
| 10:10 | 10:25 | Pause                        | keine                           | keine                                     |      15 |
| 10:25 | 11:10 | 02 Das ER-Modell             | `decks/02-er-model.html`        | keine                                     |      45 |
| 11:10 | 11:45 | A1 ER-Modell                 | `decks/02-er-model.html`        | beide Themen in `a1-er-model/`            |      35 |
| 11:45 | 12:00 | Auswertung A1                | `decks/02-er-model.html`        | zwei Entscheidungen vergleichen           |      15 |
| 12:00 | 12:15 | Puffer                       | keine                           | offene Modellfragen                       |      15 |
| 12:15 | 13:00 | Mittagspause                 | keine                           | keine                                     |      45 |
| 13:00 | 13:30 | 03 Vom ER-Modell zu Tabellen | `decks/03-er-to-tables.html`    | keine                                     |      30 |
| 13:30 | 13:55 | A2 Relationenmodell          | `decks/03-er-to-tables.html`    | `exercises/<domain>/a2-relational-model/` |      25 |
| 13:55 | 14:05 | Auswertung A2                | `decks/03-er-to-tables.html`    | Schlüssel und Zwischentabellen prüfen     |      10 |
| 14:05 | 14:50 | 04 Normalisierung            | `decks/04-normalization.html`   | keine                                     |      45 |
| 14:50 | 15:05 | Pause                        | keine                           | keine                                     |      15 |
| 15:05 | 15:40 | A3 Normalisierung            | `decks/04-normalization.html`   | beide Themen in `a3-normalization/`       |      35 |
| 15:40 | 16:00 | Schema-Checkpoint            | `decks/04-normalization.html`   | Ergebnis für B1 sichern                   |      20 |

Tagessumme: 420 Minuten.

## Tag 2

| Start | Ende  | Block                     | Deck                                  | Aufgabe                              | Minuten |
| ----- | ----- | ------------------------- | ------------------------------------- | ------------------------------------ | ------: |
| 09:00 | 09:10 | Starttest des Schemas     | `decks/05-sql-basics.html`            | Checkpoint aus A3                    |      10 |
| 09:10 | 09:45 | 05 SQL-Grundlagen         | `decks/05-sql-basics.html`            | keine                                |      35 |
| 09:45 | 10:20 | B1 Schema und Constraints | `decks/05-sql-basics.html`            | `exercises/<domain>/b1-schema/`      |      35 |
| 10:20 | 10:35 | Pause                     | keine                                 | keine                                |      15 |
| 10:35 | 11:10 | 06 JOINs und Aggregation  | `decks/06-sql-joins-aggregation.html` | keine                                |      35 |
| 11:10 | 11:55 | B2 SQL-Abfragen           | `decks/06-sql-joins-aggregation.html` | `exercises/<domain>/b2-sql/`         |      45 |
| 11:55 | 12:15 | Auswertung B2             | `decks/06-sql-joins-aggregation.html` | JOIN-Fehler und Ergebnis vergleichen |      20 |
| 12:15 | 13:00 | Mittagspause              | keine                                 | keine                                |      45 |
| 13:00 | 13:40 | 07 JDBC und Row-Mapping   | `decks/07-jdbc-cursor.html`           | keine                                |      40 |
| 13:40 | 14:25 | B3 JDBC lesen             | `decks/07-jdbc-cursor.html`           | `exercises/<domain>/b3-jdbc/`        |      45 |
| 14:25 | 14:45 | B3 Vertiefung             | `decks/07-jdbc-cursor.html`           | Schreiben oder zweiter Fehlerfall    |      20 |
| 14:45 | 15:00 | Pause                     | keine                                 | keine                                |      15 |
| 15:00 | 15:25 | 08 Repository Pattern     | `decks/08-repository-pattern.html`    | keine                                |      25 |
| 15:25 | 15:40 | B4 Repository-Refactoring | `decks/08-repository-pattern.html`    | `exercises/<domain>/b4-repository/`  |      15 |
| 15:40 | 16:00 | Flyway-Übergabe           | `decks/08-repository-pattern.html`    | Starttest für C2                     |      20 |

Tagessumme: 420 Minuten.

## Tag 3

| Start | Ende  | Block                           | Deck                              | Aufgabe                                  | Minuten |
| ----- | ----- | ------------------------------- | --------------------------------- | ---------------------------------------- | ------: |
| 09:00 | 09:10 | Starttest                       | `decks/09-http-rest-openapi.html` | Migration und Repository aus B4          |      10 |
| 09:10 | 09:45 | 09 HTTP, REST und OpenAPI       | `decks/09-http-rest-openapi.html` | keine                                    |      35 |
| 09:45 | 10:10 | C1 API-Vertrag                  | `decks/09-http-rest-openapi.html` | `exercises/<domain>/c1-http-contract/`   |      25 |
| 10:10 | 10:25 | Pause                           | keine                             | keine                                    |      15 |
| 10:25 | 11:00 | 10 Spring Boot                  | `decks/10-spring-boot.html`       | gemeinsamer Start                        |      35 |
| 11:00 | 11:50 | C2 GET der Hauptressource       | `decks/10-spring-boot.html`       | `exercises/<domain>/c2-spring-resource/` |      50 |
| 11:50 | 12:15 | POST, Request- und Response-DTO | `decks/11-good-design.html`       | vorbereiteter Pfad für C3                |      25 |
| 12:15 | 13:00 | Mittagspause                    | keine                             | keine                                    |      45 |
| 13:00 | 13:35 | 11 Service und Fehlervertrag    | `decks/11-good-design.html`       | keine                                    |      35 |
| 13:35 | 14:20 | C3 POST und Konflikt            | `decks/11-good-design.html`       | `exercises/<domain>/c3-tests-errors/`    |      45 |
| 14:20 | 14:35 | Pause                           | keine                             | keine                                    |      15 |
| 14:35 | 15:10 | 12 Zwei gezielte Tests          | `decks/12-making-it-solid.html`   | C3 abschließen                           |      35 |
| 15:10 | 15:40 | Ende-zu-Ende-Demo               | `decks/13-closing.html`           | ein Datensatz durch alle Schichten       |      30 |
| 15:40 | 16:00 | Auswertung                      | `decks/13-closing.html`           | Entscheidung und Fehlerbild je Gruppe    |      20 |

Tagessumme: 420 Minuten.

## Pflicht und Vertiefung

Pflicht sind die Kernaufträge in A0 bis C3. Für REST gehören GET, POST, Validation, genau eine Konfliktregel mit `409 Conflict` und ein Controller-Test dazu. PUT, DELETE, weitere Beziehungen, Reflection, Security, Docker, Pagination und PATCH sind Vertiefungen oder Nachschlageinhalte.

Wenn Tag 2 und Tag 3 zusammengelegt werden, bleibt die Reihenfolge B1 bis C3 bestehen. Die Lehrperson kann Vortragsblöcke kürzen und vorbereitete Zwischenstände freigeben. Die Übergaben zwischen Schema, JDBC, Repository und Spring dürfen nicht entfallen.
