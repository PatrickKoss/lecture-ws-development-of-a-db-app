# Verbindlicher Ablauf

Der Kurs läuft an drei aufeinanderfolgenden Tagen von 09:00 bis 16:00 Uhr. Die Mittagspause ist täglich von 12:15 bis 13:00 Uhr. Vormittags und nachmittags gibt es je 15 Minuten Pause.

## Legende

- "Block" bezeichnet Vortrag, Leitfragen und kurze Gespräche im Plenum.
- "Übung" bezeichnet die Arbeitszeit der Gruppen. Das kurze Debrief liegt in den letzten Minuten der Übung, wenn keine eigene Zeile dafür vorgesehen ist.
- `<domain>` steht für die Domäne der Gruppe, zum Beispiel `library` oder `gym`.
- Die Deckpfade in den Tabellen sind relativ zum Verzeichnis `slides/`.
- Block 11 ist durch die Mittagspause geteilt. Beide Teile ergeben zusammen die im Plan festgelegte Dauer.
- Puffer ist echte Reserve. Dort beginnt kein neuer Pflichtstoff.

## Die rote Linie

1. Idee im Kopf
2. Darüber sprechen: Abstraktion und ER-Modell
3. Tabellen, die uns nicht anlügen: Normalisierung
4. Den Tabellen Fragen stellen: SQL
5. Aus Java fragen: JDBC, Cursor, Statements
6. Wiederholungen beenden: Repository Pattern
7. Andere zugreifen lassen: REST API mit Spring
8. Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware

## Tag 1

| Start | Ende  | Block                               | Deck                            | Übung und Karte                                | Minuten |
| ----- | ----- | ----------------------------------- | ------------------------------- | ---------------------------------------------- | ------: |
| 09:00 | 09:20 | 00 Auftakt                          | `decks/00-opening.html`         | keine                                          |      20 |
| 09:20 | 09:55 | 01 Warum Abstraktion?               | `decks/01-why-abstraction.html` | keine                                          |      35 |
| 09:55 | 10:10 | Übung 0, Domäne in fünf Sätzen      | `decks/01-why-abstraction.html` | Ex 0, `exercises/<domain>/README.md`           |      15 |
| 10:10 | 10:25 | Pause                               | keine                           | keine                                          |      15 |
| 10:25 | 11:10 | 02 Das ER-Modell                    | `decks/02-er-model.html`        | keine                                          |      45 |
| 11:10 | 11:50 | Übung 1, ER-Modell                  | `decks/02-er-model.html`        | Ex 1, `exercises/<domain>/01-er.md`            |      40 |
| 11:50 | 12:05 | Zwei Gruppen präsentieren           | `decks/02-er-model.html`        | Debrief Ex 1, `exercises/<domain>/01-er.md`    |      15 |
| 12:05 | 12:15 | Puffer für offene Modellfragen      | `decks/02-er-model.html`        | keine                                          |      10 |
| 12:15 | 13:00 | Mittagspause                        | keine                           | keine                                          |      45 |
| 13:00 | 13:30 | 03 Vom ER-Modell zu Tabellen        | `decks/03-er-to-tables.html`    | keine                                          |      30 |
| 13:30 | 13:50 | Übung 2, Tabellenentwurf auf Papier | `decks/03-er-to-tables.html`    | Ex 2, `exercises/<domain>/01-er.md`            |      20 |
| 13:50 | 14:05 | Puffer für Tabellenvergleich        | `decks/03-er-to-tables.html`    | keine                                          |      15 |
| 14:05 | 14:50 | 04 Normalisierung                   | `decks/04-normalization.html`   | keine                                          |      45 |
| 14:50 | 15:05 | Pause                               | keine                           | keine                                          |      15 |
| 15:05 | 15:45 | Übung 3, bis 3NF zerlegen           | `decks/04-normalization.html`   | Ex 3, `exercises/<domain>/02-normalization.md` |      40 |
| 15:45 | 15:55 | Tagesabschluss, das Schema steht    | `decks/04-normalization.html`   | keine                                          |      10 |
| 15:55 | 16:00 | Puffer und Dateien sichern          | `decks/04-normalization.html`   | keine                                          |       5 |

Tagessumme: 420 Minuten. Davon sind 315 Minuten Inhalt und Übung, 30 Minuten Puffer, 30 Minuten kurze Pausen und 45 Minuten Mittagspause.

## Tag 2

| Start | Ende  | Block                                      | Deck                                  | Übung und Karte                        | Minuten |
| ----- | ----- | ------------------------------------------ | ------------------------------------- | -------------------------------------- | ------: |
| 09:00 | 09:45 | 05 SQL-Grundlagen                          | `decks/05-sql-basics.html`            | keine                                  |      45 |
| 09:45 | 10:25 | Übung 4, Schema und Seed                   | `decks/05-sql-basics.html`            | Ex 4, `exercises/<domain>/03-sql.md`   |      40 |
| 10:25 | 10:40 | Pause                                      | keine                                 | keine                                  |      15 |
| 10:40 | 11:25 | 06 JOINs und Aggregation                   | `decks/06-sql-joins-aggregation.html` | keine                                  |      45 |
| 11:25 | 12:15 | Übung 5, acht SQL-Fragen                   | `decks/06-sql-joins-aggregation.html` | Ex 5, `exercises/<domain>/03-sql.md`   |      50 |
| 12:15 | 13:00 | Mittagspause                               | keine                                 | keine                                  |      45 |
| 13:00 | 13:45 | 07 JDBC und Cursor                         | `decks/07-jdbc-cursor.html`           | keine                                  |      45 |
| 13:45 | 14:30 | Übung 6A, `StudentRepositoryImpl`          | `decks/07-jdbc-cursor.html`           | Ex 6A, `exercises/<domain>/04-jdbc.md` |      45 |
| 14:30 | 15:00 | Übung 6B, eigene Entity                    | `decks/07-jdbc-cursor.html`           | Ex 6B, `exercises/<domain>/04-jdbc.md` |      30 |
| 15:00 | 15:15 | Pause                                      | keine                                 | keine                                  |      15 |
| 15:15 | 15:55 | 08 Das Repository Pattern                  | `decks/08-repository-pattern.html`    | Ex 7 entfällt                          |      40 |
| 15:55 | 16:00 | Tagesabschluss, Java-Zugriff steht         | `decks/08-repository-pattern.html`    | keine                                  |       5 |

Tagessumme: 420 Minuten. Davon sind 345 Minuten Inhalt und Übung, 30 Minuten kurze Pausen und 45 Minuten Mittagspause.

## Tag 3

| Start | Ende  | Block                                 | Deck                              | Übung und Karte                        | Minuten |
| ----- | ----- | ------------------------------------- | --------------------------------- | -------------------------------------- | ------: |
| 09:00 | 09:40 | 09 HTTP, REST und OpenAPI             | `decks/09-http-rest-openapi.html` | keine                                  |      40 |
| 09:40 | 10:05 | Übung 8, API auf Papier               | `decks/09-http-rest-openapi.html` | Ex 8, `exercises/<domain>/06-rest.md`  |      25 |
| 10:05 | 10:20 | Pause                                 | keine                             | keine                                  |      15 |
| 10:20 | 11:05 | 10 Spring Boot                        | `decks/10-spring-boot.html`       | keine                                  |      45 |
| 11:05 | 11:50 | Übung 9, erste Ressource              | `decks/10-spring-boot.html`       | Ex 9, `exercises/<domain>/06-rest.md`  |      45 |
| 11:50 | 12:15 | 11 Gutes Anwendungsdesign, Teil 1     | `decks/11-good-design.html`       | keine                                  |      25 |
| 12:15 | 13:00 | Mittagspause                          | keine                             | keine                                  |      45 |
| 13:00 | 13:15 | 11 Gutes Anwendungsdesign, Teil 2     | `decks/11-good-design.html`       | keine                                  |      15 |
| 13:15 | 14:10 | Übung 10, CRUD für die Hauptressource | `decks/11-good-design.html`       | Ex 10, `exercises/<domain>/06-rest.md` |      55 |
| 14:10 | 14:45 | 12 Die Anwendung absichern            | `decks/12-making-it-solid.html`   | keine                                  |      35 |
| 14:45 | 15:00 | Pause                                 | keine                             | keine                                  |      15 |
| 15:00 | 15:40 | Übung 11, zwei Tests und ein Filter   | `decks/12-making-it-solid.html`   | Ex 11, `exercises/<domain>/06-rest.md` |      40 |
| 15:40 | 16:00 | 13 Abschluss                          | `decks/13-closing.html`           | keine neue Übung                       |      20 |

Tagessumme: 420 Minuten. Davon sind 345 Minuten Inhalt und Übung, 30 Minuten kurze Pausen und 45 Minuten Mittagspause.

## Puffer und Kürzungen

Tag 1 hat 30 Minuten Puffer. Er liegt bewusst an Modellübergängen und am Tagesende. Wenn alles glatt läuft, nutzt die Lehrperson ihn für Vergleiche zwischen Domänen, nicht für Reservefolien.

An Tag 2 entfällt Übung 7 vollständig. Das spart die im Plan vorgesehenen 30 Minuten. Die Übungsfolie 08.16 wird übersprungen. Die Karte `exercises/<domain>/05-repository.md` bleibt als freiwillige Weiterarbeit erhalten.

An Tag 3 dauert Übung 10 statt 75 nur 55 Minuten. Pflicht sind CRUD, DTOs, Validation sowie die Fehlerfälle 400, 404 und 409 für die Hauptressource. Der Beziehungsendpunkt wird nicht umgesetzt. Deck 12 und Übung 11 bleiben dadurch im Ablauf.

Die Reserveinhalte zu Window Functions in Deck 06, Transaktionen in Deck 08 sowie Pagination und PATCH in Deck 12 gehören nicht zum verbindlichen Ablauf. Wenn Tag 3 trotz der Kürzung zurückliegt, entfällt zuerst Übung 11. Danach kann der gesamte Block 12 entfallen, wie im Plan vorgesehen.
