# Audit der dreitägigen Vorlesung "Development of a database application"

Stand: 27. August 2026

## Auftrag und Prüfgrundlage

Dieser Audit bewertet die Vorlesung nach sechs Fragen:

- Trägt ein erkennbarer roter Faden durch alle drei Tage?
- Werden neue Begriffe so eingeführt, dass Einsteiger folgen können?
- Sind Folien und Beispiele einfach genug erklärt?
- Bauen die Übungen auf den Folien und aufeinander auf?
- Sind die fachlichen und technischen Aussagen korrekt?
- Ist der Stoff in drei Tagen von 09:00 bis 16:00 Uhr zu bewältigen?

Geprüft wurden die 14 Foliendecks in [`slides/decks`](../slides/decks), der Zeitplan in [`docs/schedule.md`](schedule.md), der Leitfaden in [`docs/instructor-guide.md`](instructor-guide.md), alle zehn Übungsdomänen unter [`exercises`](../exercises), die Java-Starterprojekte und die vorhandenen visuellen Einzelreviews in [`docs/review`](review).

Ich habe außerdem die SQL-Dateien aller Domänen gegen SQLite ausgeführt, alle Java-Projekte gebaut, das REST-Starterprojekt gestartet und Tag 1 vollständig gerendert. Damit trennt der Bericht beobachtete Fehler von bloßen Vermutungen.

## Kurzurteil

Die Vorlesung hat eine gute fachliche Grundidee. Sie beginnt bei einer Alltagsskizze, führt über ER-Modell und Tabellen zu SQL und endet bei einer HTTP-API. Die einzelnen Übergänge sind oft gut gewählt. Besonders gelungen sind "Beziehungen werden zu JOINs", "manuelles Row-Mapping führt zum Repository" und "erst HTTP-Vertrag, dann Spring".

Das größte Problem liegt nicht innerhalb einzelner Kapitel, sondern zwischen den Tagen. Die Materialien versprechen, dass jede Gruppe dieselbe Anwendung weiterbaut. Tatsächlich wechseln die Gruppen von ihren eigenen SQL-Dateien zu einem Student-JDBC-Projekt und danach zu einem neuen, fast leeren REST-Projekt. Es bleibt dieselbe Domänengeschichte, aber nicht dieselbe Anwendung. Dadurch verliert der Kurs genau dort seinen roten Faden, wo er am stärksten sein sollte.

Der Stoff passt in der jetzigen Form nicht belastbar in drei Tage. Tag 1 ist dicht, aber mit Kürzungen machbar. Tag 2 ist bei vorbereiteten Seed-Daten machbar. Tag 3 verlangt in kurzer Zeit zu viele neue Spring-Bausteine und zu viel Domänenlogik. Schwächere Gruppen werden dort Code abschreiben, ohne die Entscheidungen noch erklären zu können.

| Kriterium                            | Bewertung | Begründung                                                                                                                  |
| ------------------------------------ | --------: | --------------------------------------------------------------------------------------------------------------------------- |
| Roter Faden als Erzählung            |   4 von 5 | Die fachliche Folge ist klar und wird häufig wieder aufgegriffen.                                                           |
| Roter Faden in den Artefakten        |   2 von 5 | Datenbank, Modell und Hauptressource wechseln zwischen den Tagen.                                                           |
| Heranführung an das Thema            |   4 von 5 | Der Einstieg beginnt bei einer Idee statt bei SQL-Syntax.                                                                   |
| Einfachheit und Verständlichkeit     |   3 von 5 | Viele Beispiele sind konkret, Normalformen und die letzten Kapitel sind zu dicht.                                           |
| Integration der Übungen              |   2 von 5 | Gute Domänenkarten, aber Aufgaben, Zeiten, Nummerierung und Starterprojekte passen nicht sauber zusammen.                   |
| Fachliche und technische Korrektheit |   3 von 5 | Die Basis stimmt. Bei BCNF, Schemaerzeugung, Datumsabbildung und Fehlerverträgen gibt es konkrete Fehler oder Widersprüche. |
| Eignung für drei Tage                |   2 von 5 | 241 Folien plus umfangreiche Implementierung lassen kaum Zeit für Diagnose, Rückfragen und Auswertung.                      |

## Was bereits gut funktioniert

### Der Einstieg

Kapitel 00 und 01 erklären zuerst das Problem: Eine Anwendung muss Dinge, Beziehungen und Regeln dauerhaft speichern. Das ist für Einsteiger wesentlich zugänglicher als ein Start mit `CREATE TABLE`. Die wiederkehrende Universitätsdomäne gibt den Begriffen einen festen Bezugspunkt.

Die erste kleine Übung liegt früh genug. Teilnehmer formulieren eine eigene Domäne, bevor die formalen Begriffe beginnen. Das schafft einen brauchbaren Anker für die folgenden Kapitel.

### Die fachlichen Übergänge

Mehrere Kapitel beginnen mit einem Problem, das das nächste Werkzeug nötig macht:

- Ein ER-Modell reicht für die Implementierung nicht. Es muss in Tabellen übersetzt werden.
- Beziehungen sind nach der Übersetzung über Fremdschlüssel verteilt. JOINs setzen sie wieder zusammen.
- Wiederholtes JDBC-Mapping erzeugt Duplikate. Ein Repository bündelt den Datenzugriff.
- Ein Repository allein ist noch kein externer Vertrag. HTTP und OpenAPI definieren diesen Vertrag.
- Ein HTTP-Server von Hand enthält viel wiederkehrende Arbeit. Spring übernimmt Routing, JSON und Lebenszyklus.

Diese Übergänge würde ich behalten und auf den jeweiligen Kapitelfolien noch deutlicher als ein bis zwei Leitfragen markieren.

### Die Übungsdomänen

Zehn Domänen verhindern, dass alle Gruppen nur das Universitätsbeispiel kopieren. Die Karten enthalten konkrete Entitäten, Beziehungen und Geschäftsregeln. Die SQL-Skripte laufen, und jede Tabelle enthält die angekündigte Größenordnung von 10 bis 20 Datensätzen. Das ist eine solide Grundlage für Vergleiche im Plenum.

### Organisatorische Vorbereitung

Der Zeitplan summiert sich pro Tag korrekt auf 420 Minuten. Pausen, Mittag und einige Puffer sind eingeplant. Der Leitfaden enthält Rückfalloptionen und Hinweise für Vorführungen. Diese Vorbereitung ist für einen dreitägigen Kurs wertvoll.

## Befunde mit hoher Priorität

### 1. Die Gruppen bauen nicht dieselbe Anwendung weiter

Die Folien versprechen sinngemäß eine Anwendung, die über drei Tage wächst. Die tatsächliche Folge sieht anders aus:

1. Am ersten Tag erstellt jede Gruppe ein ER-Modell und arbeitet mit den SQL-Dateien ihrer Domäne.
2. Am zweiten Tag wechselt sie in [`java/repository-simple-exercise`](../java/repository-simple-exercise) und implementiert zunächst die Student-Datenbank. Erst danach kommt eine einzelne Domänenentität hinzu.
3. Am dritten Tag beginnt sie mit [`java/rest-simple-exercise`](../java/rest-simple-exercise). Dort existieren weder ihre Tabellen noch ihr Repository vom Vortag. Die Gruppe soll eine neue Migration und eine neue Ressource anlegen.

Auch die gewählte Hauptressource wechselt oft:

| Domäne         | Tag 2   | Tag 3     |
| -------------- | ------- | --------- |
| Bibliothek     | Book    | Loan      |
| Kino           | Movie   | Screening |
| Fitnessstudio  | Course  | Booking   |
| Autowerkstatt  | Vehicle | WorkOrder |
| Pizzalieferung | Pizza   | Order     |
| Hotel          | Room    | Booking   |

Das hat zwei Folgen. Die Teilnehmer erleben keine echte Entwicklungslinie, und bereits erledigte Arbeit hat am nächsten Tag keinen sichtbaren Wert.

#### Änderung

Jede Gruppe sollte ab Übung 1 ein eigenes Arbeitsverzeichnis behalten. Dieses enthält:

```text
group-work/<domain>/
  docs/er.md
  db/V1__schema.sql
  db/V2__seed.sql
  src/main/java/.../<resource>/
  src/test/java/.../<resource>/
```

Die Gruppe wählt am ersten Tag eine Hauptentität. Dieselbe Entität wird am zweiten Tag per JDBC gelesen und am dritten Tag als REST-Ressource angeboten. Komplexe Geschäftsprozesse wie `Loan`, `Booking` oder `Order` kommen erst als Erweiterung hinzu.

Wenn getrennte Starterprojekte technisch gewünscht sind, braucht es einen sichtbaren Übergabeschritt: SQL-Datei kopieren, Migration benennen, Repository übernehmen, Anwendung starten und einen bereits vorhandenen Datensatz über HTTP lesen. Dieser Schritt gehört in den Zeitplan.

### 2. Tag 3 verlangt mehr Implementierung, als der Starter vorbereitet

Das REST-Starterprojekt enthält einen Health-Endpunkt, Konfiguration, einen Fehlerhandler und Hilfsklassen. Fachmodell, Repository, Migrationen und Tests fehlen. Die Übungen verlangen danach in ungefähr 100 Minuten:

- Migration und Entity,
- Repository und Service,
- Request- und Response-DTOs,
- vollständiges CRUD,
- Validierung,
- Statuscodes und stabile Fehlercodes,
- mindestens einen fachlichen Konflikt,
- teils mehrere abhängige Entitäten.

Eine Bibliotheksausleihe braucht etwa Mitglied, Exemplar, Buch und Ausleihstatus. Eine Bestellung braucht Kunde, Adresse, Positionen und oft Fahrer oder Zahlung. Diese Ressourcen sind nicht gleich schwer wie ein einzelnes `Event` oder `Room`. Die Gruppen bekommen deshalb trotz identischer Zeit sehr unterschiedliche Aufgaben.

#### Änderung

Für Tag 3 sollte jede Domäne einen vorbereiteten Starterstand erhalten:

- Schema und Seed-Daten aus Tag 1 sind bereits als Flyway-Migrationen eingebunden.
- Abhängige Entities und Read-Repositories sind vorhanden.
- Nur die gewählte Hauptressource bleibt als Aufgabe offen.
- Ein GET- und ein POST-Endpunkt sind Pflicht.
- PUT und DELETE sind eine zweite Stufe.
- Genau eine Konfliktregel mit `409 Conflict` ist Pflicht.
- Beziehungen und weitere Regeln sind Zusatzaufgaben.

Damit üben die Teilnehmer den HTTP-Vertrag und die Schichtentrennung. Sie verbringen ihre Zeit nicht mit dem Nachbauen von Infrastruktur.

### 3. Normalisierung ist fachlich zu dicht und enthält einen BCNF-Fehler

Kapitel 04 hat 29 Folien. Im vollständigen Render von Tag 1 entstehen daraus 113 sichtbare Fragmentzustände. Dafür sind 45 Minuten vorgesehen. In dieser Zeit sollen Einsteiger funktionale Abhängigkeiten, Superschlüssel, Kandidatenschlüssel, Schlüsselattribute, volle und partielle Abhängigkeit, 1NF, 2NF, 3NF und BCNF verstehen.

Hinzu kommt eine falsche BCNF-Formulierung. Die Folie und der Leitfaden sagen, jeder Determinant müsse ein Kandidatenschlüssel sein. Für BCNF muss jeder Determinant ein **Superschlüssel** sein. Ein Superschlüssel darf zusätzliche Attribute enthalten und muss nicht minimal sein. Die Textstellen stehen in [`slides/decks/04-normalization.html`](../slides/decks/04-normalization.html), [`docs/draft.md`](draft.md) und [`docs/instructor-guide.md`](instructor-guide.md).

Die 3NF-Folie nennt die Formulierung "kein Nichtschlüsselattribut hängt transitiv von einem Kandidatenschlüssel ab" eine formale Definition. Das ist eine didaktische Näherung. Die auf derselben Folie gezeigte Prüfregel ist genauer: Für jede nichttriviale funktionale Abhängigkeit `X -> A` ist `X` ein Superschlüssel oder `A` ein Schlüsselattribut.

Die Aussage, bei einem einspaltigen Schlüssel sei 2NF automatisch erfüllt, braucht ebenfalls eine Präzisierung. Sie gilt, wenn alle Kandidatenschlüssel einspaltig sind.

#### Änderung

- Tag 1 behandelt Anomalien, funktionale Abhängigkeiten, 1NF, 2NF und 3NF.
- BCNF wandert in einen klar markierten Zusatzteil oder an den Anfang von Tag 2.
- Die obere 3NF-Box heißt "Intuition". Die Prüfregel wird als formale Bedingung bezeichnet.
- Der BCNF-Text verwendet durchgehend "Superschlüssel".
- Die Übung endet bei einer begründeten 3NF-Zerlegung. Eine BCNF-Prüfung ist eine Bonusaufgabe.

Das Lernziel sollte nicht lauten, vier Definitionen wiederzugeben. Die Teilnehmer sollen eine Redundanz erkennen, die bestimmende Attributmenge benennen und die Tabelle verlustfrei in verständliche Teile zerlegen können.

### 4. Übungszeit und Arbeitsmenge passen mehrfach nicht zusammen

| Block                     | Vorgesehene Zeit | Tatsächliche Arbeit                                              | Einschätzung                                    |
| ------------------------- | ---------------: | ---------------------------------------------------------------- | ----------------------------------------------- |
| Übung 3, Normalisierung   |          40 Min. | Abhängigkeiten, 1NF, 2NF, 3NF, BCNF, Vergleich mit ER-Modell     | Für Einsteiger zu viel.                         |
| Übung 4, Schema und Daten |          40 Min. | Mehrere Tabellen plus 10 bis 20 Zeilen je Tabelle                | Ohne vorbereitete Seeds nicht realistisch.      |
| Übung 5, SQL              |          50 Min. | Acht Abfragen mit JOINs und Aggregaten                           | Machbar, wenn Schema und Daten fertig sind.     |
| Übung 6A, JDBC            |          45 Min. | Sieben Methoden, Mapping, Fehlerbehandlung und CLI               | Sehr eng.                                       |
| Übung 6B, eigene Entität  |          30 Min. | Modell, Tabelle, Interface, Implementierung, Validierung und CLI | Zu viel.                                        |
| Übungen 9 bis 11, Spring  |         140 Min. | App-Start, CRUD, DTOs, Service, Fehler, Tests und Härtung        | Nur mit deutlich vorbereitetem Starter machbar. |

#### Änderung

Jede Übung erhält drei Stufen:

1. Kernauftrag mit einem überprüfbaren Ergebnis.
2. Vertiefung für schnelle Gruppen.
3. vorbereiteter Zwischenschritt, den die Lehrperson nach Ablauf der Kernzeit freigibt.

Beispiel für die JDBC-Übung:

- Kern: `findById`, `findAll`, sichtbares Row-Mapping und ein Fehlerfall.
- Vertiefung: `save` und `update`.
- Zusatz: generisches Mapping oder eine zweite Entität.

### 5. Lösungen liegen an den Arbeitsstellen der Teilnehmer

Die Domänenordner enthalten fertige Dateien wie [`exercises/library/sql/schema.sql`](../exercises/library/sql/schema.sql), [`seed.sql`](../exercises/library/sql/seed.sql) und [`queries.sql`](../exercises/library/sql/queries.sql). Gleichzeitig sollen die Teilnehmer genau dort Schema, Daten und Abfragen schreiben. Der Hinweis, Lösungen erst nach der Besprechung zu öffnen, schützt diese Dateien praktisch nicht.

#### Änderung

Die Teilnehmerausgabe enthält nur Dateien mit TODOs und wenige Beispieldaten. Vollständige Lösungen liegen in einem nicht ausgegebenen Lehrendenpaket oder in einem erst später freigeschalteten Git-Tag. Eine einfache Struktur wäre:

```text
student/<domain>/sql/schema.sql
student/<domain>/sql/seed.sql
student/<domain>/sql/queries.sql
instructor-solutions/<domain>/...
```

### 6. Die Nummerierung der Übungen ist widersprüchlich

Der Zeitplan und die Folien nummerieren von Übung 0 bis Übung 11. Die Domänenkarten heißen dagegen `01-er.md` bis `06-rest.md`. Eine Datei deckt teils mehrere nummerierte Übungen ab. So verweist Übung 2, Tabellenentwurf, auf `01-er.md`, obwohl dort kein eigener Tabellenauftrag steht. Übung 7 ist im Zeitplan gestrichen, Kapitel 08 wird aber weiter gelehrt.

#### Änderung

Die Dateinamen und Überschriften sollten dieselben Kennungen wie die Folien verwenden. Alternativ werden Nummern ganz entfernt und durch feste Phasen ersetzt:

- A0 Domänenidee
- A1 ER-Modell
- A2 Relationenmodell
- A3 Normalisierung
- B1 Schema und Seed-Daten
- B2 SQL-Abfragen
- B3 JDBC
- B4 Repository-Refactoring
- C1 HTTP-Vertrag
- C2 Spring-Ressource
- C3 Tests und Fehler

Die Phasenkennungen bleiben stabil, auch wenn einzelne Blöcke zeitlich verschoben werden.

## Audit nach Tag

### Tag 1: Vom Problem zum belastbaren Schema

Tag 1 hat 78 Folien. Der vollständige Render erzeugt 265 sichtbare Zustände:

| Kapitel           | Folien | sichtbare Zustände |
| ----------------- | -----: | -----------------: |
| 00 Eröffnung      |      7 |                 18 |
| 01 Abstraktion    |     11 |                 44 |
| 02 ER-Modell      |     20 |                 63 |
| 03 ER zu Tabellen |     11 |                 27 |
| 04 Normalisierung |     29 |                113 |

Der Vormittag ist gut aufgebaut. Die Teilnehmer beginnen mit ihrer Domäne, lernen Entitäten und Beziehungen und wenden das sofort an. Der Nachmittag wird mit der formalen Normalisierung abrupt schwerer. 113 Fragmentzustände in 45 Minuten lassen im Mittel weniger als 24 Sekunden pro Zustand. Darin sind Erklärungen und Rückfragen noch nicht enthalten.

#### Empfohlener Zuschnitt

- Eröffnung und Abstraktion bleiben weitgehend bestehen.
- Das ER-Kapitel kürzt Sonderfälle, die in keiner Übung gebraucht werden.
- Der Tabellenentwurf bekommt einen eigenen Arbeitsauftrag in der Domänenkarte.
- Normalisierung beginnt mit einer konkreten Änderungsanomalie.
- 1NF bis 3NF bilden den Pflichtteil.
- BCNF und das BigQuery-Beispiel kommen in den Anhang.
- Der Tag endet mit einem gespeicherten Schema-Checkpoint. Eine mündliche Zusammenfassung allein reicht dafür nicht.

Ein guter Abschlussauftrag wäre: "Zeigt eine Redundanz, die ihr entfernt habt. Markiert die funktionale Abhängigkeit, die eure Zerlegung begründet. Führt einen `INSERT` aus, der vorher problematisch war."

### Tag 2: Vom Schema zu einer kleinen Datenzugriffsschicht

Tag 2 enthält 79 Folien. Inhaltlich passen SQL-Grundlagen, JOINs und JDBC zusammen. Die Repository-Idee folgt aus wiederholtem JDBC-Code. Der Tagesablauf verliert aber Zeit durch doppelte Modellierung und durch das erneute Student-Beispiel.

Übung 4 verlangt die manuelle Erzeugung vieler Seed-Daten. Für das Lernziel ist das wenig ergiebig. Zehn bis zwanzig Zeilen pro Tabelle helfen beim Abfragen, aber nicht beim Verständnis von `CREATE TABLE`. Diese Daten sollten vorbereitet sein.

Kapitel 08 behandelt in 40 Minuten Repository Pattern, generisches CRUD, Reflection, ORM, Migrationen und optional Transaktionen. Reflection ist für das Kursziel entbehrlich. Sie erklärt eine mögliche Implementierung, aber nicht die zentrale Entscheidung: Welche Schicht kennt SQL, und welche Schicht kennt fachliche Objekte?

Übung 7 entfällt laut Plan. Damit bleibt das Repository-Kapitel ohne direkte Anwendung. Das ist didaktisch ungünstig, weil gerade dieser Umbau die Brücke zu Spring Data bilden soll.

#### Empfohlener Zuschnitt

- Schema-Datei prüfen und zwei Constraints ergänzen, statt alle Seeds zu schreiben.
- SQL-Übung auf vier Pflichtabfragen begrenzen. Zwei JOINs und eine Aggregation müssen enthalten sein.
- JDBC-Kern auf Lesen und Mapping begrenzen. Schreiben kommt als Vertiefung.
- Ein kurzes Repository-Refactoring von 15 Minuten bleibt verpflichtend.
- Reflection und generische Repository-Basen kommen in den Anhang.
- Flyway wird praktisch eingeführt, indem das Schema vom Vortag als `V1__schema.sql` übernommen wird.

### Tag 3: Von der Ressource zur getesteten API

Tag 3 enthält 84 Folien. Er führt HTTP, OpenAPI, Spring, Schichten, DTOs, Validierung, Fehler, Tests, Logging, Actuator, CORS, Security, Docker, Pagination und PATCH ein. Vieles davon ist nützlich, aber die Teilnehmer können es nicht am selben Tag verstehen und selbst anwenden.

Die Reihenfolge "HTTP vor Framework" ist richtig. Die Papierübung sollte bleiben. Danach braucht der Tag einen kleineren Implementierungsgegenstand. Ein erfolgreiches `GET /api/<resource>` mit DTO, Repository und Test ist als Lernergebnis wertvoller als fünf unvollständige Endpunkte.

Kapitel 12 sollte im Pflichtteil auf Controller-Test, einen Service-Test, einen stabilen Fehlerkörper und eine Korrelations-ID begrenzt werden. Actuator, CORS, Security, Docker, Pagination und PATCH gehören in klar markierte Anhänge oder in einen vierten Tag.

#### Empfohlener Zuschnitt

- Tagesstart mit einem zehnminütigen Checkpoint aus Tag 2.
- HTTP-Vertrag und Papierübung bleiben.
- Der Spring-Start wird gemeinsam durchgeführt.
- Jede Gruppe implementiert GET und POST für dieselbe Entität wie an Tag 2.
- Ein Fehlerfall und ein Test sind Pflicht.
- PUT, DELETE, weitere Beziehungen und Security sind Erweiterungen.
- Der Abschluss zeigt den Weg eines konkreten Datensatzes: SQLite, Repository, Service, Controller, JSON-Antwort.

## Vorschlag für einen realistischen Dreitageskurs

Die folgende Fassung behält das Kursziel bei. Sie reduziert die Zahl der gleichzeitig neuen Konzepte.

### Tag 1

|   Dauer | Inhalt                                   | Ergebnis                                            |
| ------: | ---------------------------------------- | --------------------------------------------------- |
| 45 Min. | Eröffnung, Abstraktion und Domänenwahl   | Domänenbeschreibung mit drei Dingen und zwei Regeln |
| 15 Min. | Übung A0                                 | Gemeinsame Begriffe                                 |
| 15 Min. | Pause                                    |                                                     |
| 40 Min. | ER-Modell                                | Entitäten, Attribute, Kardinalitäten                |
| 35 Min. | Übung A1                                 | Erstes ER-Modell                                    |
| 15 Min. | Vergleich im Plenum                      | Eine begründete Modellentscheidung                  |
| 60 Min. | Mittag                                   |                                                     |
| 25 Min. | ER zu Tabellen                           | Relationen, PK, FK, Zwischentabelle                 |
| 25 Min. | Übung A2                                 | Relationenmodell                                    |
| 35 Min. | Anomalien und funktionale Abhängigkeiten | Ursache von Redundanz                               |
| 15 Min. | Pause                                    |                                                     |
| 35 Min. | 1NF bis 3NF                              | Prüfmethode an einem Beispiel                       |
| 35 Min. | Übung A3                                 | Begründete Zerlegung                                |
| 25 Min. | Auswertung und Checkpoint                | Gespeichertes Schema für Tag 2                      |

### Tag 2

|   Dauer | Inhalt                          | Ergebnis                                      |
| ------: | ------------------------------- | --------------------------------------------- |
| 10 Min. | Rückblick mit Schema-Checkpoint | Alle Gruppen starten vom eigenen Stand        |
| 35 Min. | SQL-Grundlagen                  | Sichere Auswahl und Änderung                  |
| 35 Min. | Übung B1                        | Schema läuft, zwei Constraints sind getestet  |
| 15 Min. | Pause                           |                                               |
| 35 Min. | JOIN und Aggregation            | Beziehungen werden abgefragt                  |
| 45 Min. | Übung B2                        | Vier geprüfte Abfragen                        |
| 10 Min. | Auswertung                      | Ergebnisse und Fehlerbilder                   |
| 60 Min. | Mittag                          |                                               |
| 40 Min. | JDBC und Row-Mapping            | Datenbankzugriff aus Java                     |
| 45 Min. | Übung B3                        | `findById` und `findAll` für die Hauptentität |
| 25 Min. | Schreiben oder zweite Entität   | Vertiefung nach Gruppentempo                  |
| 15 Min. | Pause                           |                                               |
| 25 Min. | Repository-Refactoring          | SQL ist aus dem Aufrufer entfernt             |
| 15 Min. | Flyway-Übergabe                 | Eigenes Schema liegt als Migration vor        |
| 10 Min. | Checkpoint                      | Lauffähiger Stand für Tag 3                   |

### Tag 3

|   Dauer | Inhalt                           | Ergebnis                             |
| ------: | -------------------------------- | ------------------------------------ |
| 10 Min. | Rückblick und Starttest          | Migration und Repository laufen      |
| 35 Min. | HTTP, Ressourcen und Statuscodes | API-Vertrag ohne Framework           |
| 25 Min. | Übung C1 auf Papier              | GET- und POST-Vertrag                |
| 15 Min. | Pause                            |                                      |
| 35 Min. | Spring-Grundlagen                | Health und erster GET-Endpunkt       |
| 50 Min. | Übung C2                         | GET für die eigene Hauptentität      |
| 40 Min. | POST, DTO und Validierung        | Gültige und ungültige Anfrage        |
| 60 Min. | Mittag                           |                                      |
| 35 Min. | Service und Fehlervertrag        | Ein fachlicher Konflikt              |
| 45 Min. | Übung C3                         | POST plus `400` oder `409`           |
| 15 Min. | Pause                            |                                      |
| 35 Min. | Tests                            | Ein Controller- und ein Service-Test |
| 20 Min. | Ende-zu-Ende-Demo                | Ein Datensatz durch alle Schichten   |

Diese Planung hat weiter volle sieben Zeitstunden pro Tag. Sie gibt jeder Übung ein sichtbares Ergebnis und trennt Pflichtstoff von Erweiterungen.

## Review der einzelnen Foliendecks

| Deck                    | Behalten                                                | Problem                                                                                                                               | Konkrete Änderung                                                                                         |
| ----------------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| 00 Eröffnung            | Anwendung als gemeinsame Geschichte                     | Das Ziel "eine Anwendung in drei Tagen" ist gemessen an den Artefakten zu stark.                                                      | Ziel auf "einen durchgängigen API-Schnitt" ändern oder die Arbeitsstände wirklich übernehmen.             |
| 01 Abstraktion          | Start ohne Datenbankjargon                              | Einige Diagrammtexte und die rote-Faden-Navigation sind klein.                                                                        | Zwei dichte Diagramme teilen und Navigationsbeschriftung vergrößern.                                      |
| 02 ER-Modell            | Konkrete Kardinalitäten und frühe Anwendung             | Viele Fragmentzustände, teils leere Anfangszustände, überlagerte Pfeile.                                                              | Fragmente nur für echte Denkpausen verwenden. Diagramme vorab vollständig lesbar machen.                  |
| 03 ER zu Tabellen       | Gute Brücke zu PK und FK                                | Übung 2 steht nur auf der Folie, nicht klar in der Domänenkarte.                                                                      | Eigenen Tabellenauftrag mit Abgabeformat anlegen.                                                         |
| 04 Normalisierung       | Anomalien als Motivation                                | Zu dicht, BCNF falsch formuliert, 3NF-Näherung als formal bezeichnet. Mehrere neue Überschriften werden im Render oben abgeschnitten. | Pflichtteil auf 1NF bis 3NF kürzen, Begriffe korrigieren, fünf betroffene SVG-Folien niedriger setzen.    |
| 05 SQL-Grundlagen       | Kleine, ausführbare Beispiele                           | Ein Fehler-Highlight markiert nicht eindeutig die Ursache. Teilweise Sprachmischung.                                                  | Fehlerfolie mit tatsächlicher Parserstelle zeigen und Begriffe vereinheitlichen.                          |
| 06 JOIN und Aggregation | Beziehung aus dem ER-Modell wird Abfrage                | Schmale Tabellenköpfe umbrechen, Übungsfolie nutzt Raum schlecht.                                                                     | Ergebnisbilder vereinfachen und Pflichtabfragen direkt markieren.                                         |
| 07 JDBC                 | Sichtbares manuelles Mapping als Lernanlass             | Viele kleine Codezeilen, leere Fragmentstarts und zu großer Aufgabenumfang.                                                           | Kernpfad `findById` vollständig zeigen. Schreiboperationen in Vertiefung verschieben.                     |
| 08 Repository           | Richtige Brücke zu Spring Data                          | Repository Pattern, Reflection, ORM und Migrationen konkurrieren um Aufmerksamkeit. Keine feste Übung.                                | Auf Schichtgrenze und Refactoring begrenzen. Reflection in den Anhang. 15 Minuten Praxis einplanen.       |
| 09 HTTP, REST, OpenAPI  | Frameworkfreier Vertrag ist didaktisch stark            | Mehrere leere Anfangszustände, Pfeilüberlagerung auf Fehlerfolie, Pfade ohne einheitliches `/api`.                                    | Fragmentstarts entfernen, Pfade standardisieren, Vertrag direkt an die spätere Implementierung binden.    |
| 10 Spring               | Annotationen werden auf bekannte Aufgaben gemappt       | Die Gruppe startet technisch wieder bei null.                                                                                         | Folie für den Artefaktimport ergänzen. Erst vorhandene Migration und Repository starten.                  |
| 11 Design               | DTO, Service und Fehler gehören vor Tests               | Zu viele Architekturbegriffe für einen Block, während CRUD noch nicht steht.                                                          | An einem GET- und POST-Pfad erklären. Weitere Muster in den Anhang.                                       |
| 12 Stabilität           | Testen und beobachtbare Fehler sind ein guter Abschluss | MockMvc, Mockito, ID, Actuator, CORS, Security, Docker, Pagination und PATCH in 35 Minuten.                                           | Pflichtteil auf zwei Tests, Fehlerkörper und Korrelations-ID begrenzen. Rest als Nachschlagekapitel.      |
| 13 Abschluss            | Rückblick auf den gesamten Weg                          | Abschluss behauptet mehr Fertigstellung, als die Übungen garantieren.                                                                 | Jede Gruppe demonstriert einen Datensatz durch die eigene Kette. Unerledigte Erweiterungen klar benennen. |

## Integration der Übungen im Detail

### Jede Übung braucht Eingang, Arbeit und Ausgang

Derzeit beschreiben die Karten hauptsächlich die Arbeit. Für einen zuverlässigen Ablauf sollten sie immer dieselben drei Blöcke haben:

```text
Eingang
- Datei oder Commit, mit dem die Gruppe startet
- ein kurzer Starttest

Arbeit
- Kernauftrag
- Erweiterungen
- Zeitmarken nach 10 und 20 Minuten

Ausgang
- genau benannte Dateien
- ein ausführbarer Befehl
- eine Frage für die Auswertung
```

Beispiel für Übung B2:

- Eingang: `V1__schema.sql` läuft und `V2__seed.sql` liefert mindestens zehn Zeilen.
- Arbeit: zwei JOIN-Abfragen, eine Aggregation und eine Abfrage mit `LEFT JOIN`.
- Ausgang: `queries.sql` läuft ohne Fehler. Die Gruppe erklärt, warum eine Zeile bei `INNER JOIN` fehlt oder bei `LEFT JOIN` bleibt.

### Die Auswertung muss eigene Zeit erhalten

Nur nach dem ER-Modell ist eine Präsentationsphase klar sichtbar. Bei späteren Übungen steckt die Besprechung meist in den letzten Minuten. Unter Zeitdruck fällt genau dieser Teil aus.

Nach jedem größeren Block sollten fünf bis zehn Minuten außerhalb der Arbeitszeit stehen. Dabei wird nicht jede Lösung vorgelesen. Eine Gruppe zeigt eine Entscheidung, eine zweite zeigt einen Fehler und seine Ursache.

### Die Domänen müssen ähnlich schwer sein

Die Domänen sind für ER und SQL ungefähr vergleichbar. Für REST sind sie es nicht. `Event` ist eine flache Ressource. `Order`, `Loan`, `Booking` und `WorkOrder` sind Prozesse mit mehreren Abhängigkeiten und Konflikten.

Für Tag 3 gibt es zwei sinnvolle Wege:

- Alle Gruppen implementieren zuerst eine flache Ressource wie `Book`, `Movie`, `Course`, `Vehicle`, `Pizza`, `Room` oder `Event`.
- Komplexe Ressourcen erhalten vorbereitete Abhängigkeiten und genau eine offene Regel.

Ich würde den ersten Weg wählen. Er macht die Ergebnisse vergleichbarer und hält den Fokus auf HTTP, DTOs und Schichten.

## Fachliche und technische Korrektheit

### Normalformen

1. BCNF muss "jeder Determinant ist ein Superschlüssel" heißen.
2. Die transitive 3NF-Erklärung ist eine Intuition, keine vollständige formale Definition.
3. Die 2NF-Aussage muss sich auf alle Kandidatenschlüssel beziehen.

Diese drei Stellen sollten vor dem nächsten Einsatz geändert werden. Der BCNF-Fehler ist kein Geschmacksurteil, sondern ändert die Definition.

### SQLite und Fremdschlüssel

Die SQL-Skripte setzen `PRAGMA foreign_keys = ON`. Das ist richtig. SQLite deaktiviert Fremdschlüssel jedoch standardmäßig und verlangt die Aktivierung für jede Datenbankverbindung separat. Ein `PRAGMA` in einem CLI-Skript garantiert daher nicht, dass spätere JDBC- oder Pool-Verbindungen dieselbe Prüfung verwenden. Das steht in der [SQLite-Dokumentation zu Foreign Keys](https://www.sqlite.org/foreignkeys.html).

Der Kurs sollte die Aktivierung in der JDBC- und Spring-Konfiguration sichtbar machen. Ein kurzer Test sollte einen ungültigen Fremdschlüssel schreiben und den erwarteten Fehler prüfen.

### Flyway und Hibernate

Das Spring-Starterprojekt setzt `spring.jpa.hibernate.ddl-auto=update`, obwohl die Übungen Flyway als Schemaquelle verlangen. Damit kann Hibernate ein fehlendes oder falsch benanntes Migrationsskript verdecken. Genau dieser Effekt wird im Beispielprojekt sogar beobachtet.

Für den Kurs sollte nach der ersten Migration `ddl-auto=validate` oder `none` gelten. Spring Boot empfiehlt, nur einen Mechanismus für die Schemaerzeugung zu verwenden und Flyway nicht mit einer zweiten Initialisierung zu mischen. Siehe [Spring Boot, Database Initialization](https://docs.spring.io/spring-boot/how-to/data-initialization.html).

### Datumswerte

Der globale `LocalDateTimeAttributeConverter` speichert Zeitpunkte als Epoch-Millisekunden und verwendet die Systemzeitzone. Die SQL-Karten arbeiten dagegen mit ISO-Textwerten. Das erzeugt zwei unterschiedliche Speichermodelle und kann auf Rechnern mit anderer Zeitzone andere Werte liefern.

Für einen Einsteigerkurs würde ich den Konverter entfernen. SQLite kann den ISO-Text speichern, und der Code kann die Konvertierung an einer klar benannten Stelle vornehmen. Falls Epoch-Millisekunden gewollt sind, muss der Kurs UTC festlegen und den Wechsel vom SQL-Modell ausdrücklich erklären.

### Fehlervertrag

Die Übungen fordern stabile Fehlercodes. `ErrorResponse` besitzt aber nur `message` und `details`. Im README wird `details` einmal für einen maschinenlesbaren Code verwendet. Der globale 500-Handler schreibt außerdem `ex.getMessage()` in die Antwort. Das kann interne SQL-, Pfad- oder Implementierungsdetails an Clients weitergeben.

Der Starter sollte einen einheitlichen Körper vorgeben:

```json
{
  "code": "STUDENT_ALREADY_EXISTS",
  "message": "Student already exists",
  "correlationId": "..."
}
```

Validierungsfehler können zusätzlich ein `fields`-Objekt erhalten. Bei `500` bleibt die technische Ausnahme im Log und nicht in der HTTP-Antwort.

### HTTP-Aussagen

Die Erklärung der Idempotenz von PUT und DELETE ist korrekt. RFC 9110 nennt PUT, DELETE und sichere Methoden idempotent. Siehe [RFC 9110, Abschnitt 9.2.2](https://www.rfc-editor.org/rfc/rfc9110.html#section-9.2.2).

Die Actuator-Warnung ist ebenfalls sinnvoll. Spring Boot stellt standardmäßig nur `health` über HTTP bereit. Weitere Endpunkte brauchen eine bewusste Freigabe und gegebenenfalls Schutz. Siehe [Spring Boot Actuator Endpoints](https://docs.spring.io/spring-boot/3.4/reference/actuator/endpoints.html).

### Pfadkonvention

Die Domänenkarten verwenden Pfade wie `/loans` und `/orders`. Das Starterprojekt arbeitet unter `/api/students`, und die CORS-Konfiguration bezieht sich auf `/api/**`. Die Karten sollten durchgehend `/api/<resource>` verwenden oder ausdrücklich sagen, dass sie das gemeinsame Präfix abkürzen.

## Lesbarkeit und visuelle Qualität

Die 14 vorhandenen Einzelreviews enthalten 103 Einträge. Ein früher blockierter Befund zu `grade TEXT NULL` ist im aktuellen Deck bereits behoben. Es bleiben 102 offene Befunde, davon 82 mit der Einstufung "sollte" und 20 kleine sprachliche oder visuelle Hinweise. Die wiederkehrenden Ursachen sind wichtiger als einzelne Pixelkorrekturen:

- Text in Übersichten, Diagrammen und Aufgabenfeldern ist oft kleiner als 28 Pixel.
- Einige Fragmentfolien beginnen leer. Teilnehmer sehen kurz eine Folie ohne Aussage.
- Tabellenköpfe sind zu schmal und schneiden Bezeichner ab oder brechen sie ungünstig um.
- Pfeile laufen durch Beschriftungen.
- Übungsfolien lassen viel freien Raum, während der eigentliche Auftrag klein gesetzt ist.
- Deutsche Erklärungen enthalten unnötige englische Einzelwörter.

Diese Probleme sollten in gemeinsamen Komponenten gelöst werden. Ein Mindestwert für Text, ein einheitliches Aufgabenlayout und ein Fragmentmuster ohne leeren Anfang beseitigen viele Befunde auf einmal.

Im aktuell geänderten Normalisierungsdeck kommen mindestens fünf sichtbare Überläufe hinzu. Die Überschriften der Folien zu 1NF-Verletzung, 2NF-Verletzung, 3NF-Definition, 3NF-Verletzung und Prüfliste werden im oberen Rand abgeschnitten. Hier reicht es wahrscheinlich, die SVG-Inhalte niedriger zu setzen oder ihre Gesamthöhe zu verringern.

## Empfohlene Reihenfolge der Überarbeitung

### Vor dem nächsten Kurstermin

1. BCNF, 3NF-Beschriftung und 2NF-Präzisierung korrigieren.
2. Tag 3 auf GET, POST, einen Fehlerfall und Tests begrenzen.
3. Dieselbe Hauptentität über alle drei Tage verwenden.
4. Fertige SQL-Lösungen aus den Teilnehmerordnern entfernen.
5. `ddl-auto` auf `validate` oder `none` setzen und Fremdschlüssel pro Verbindung aktivieren.
6. Fehlerantworten vereinheitlichen und interne Exception-Texte entfernen.
7. Die abgeschnittenen Folien in Kapitel 04 reparieren.

### Danach

1. Übungskennungen und Dateinamen vereinheitlichen.
2. Für jede Übung Eingang, Kernauftrag, Erweiterung und Ausgang definieren.
3. Repository-Refactoring wieder als kurze Pflichtübung einplanen.
4. Reflection, BCNF, Security, Docker, Pagination und PATCH in Anhänge verschieben.
5. Wiederkehrende Schrift-, Tabellen- und Fragmentprobleme in den Folienkomponenten beheben.
6. Die Wurzel-[`README.md`](../README.md) durch eine kurze Kursstartseite mit Voraussetzungen, Startbefehlen, Zeitplan und Arbeitsablauf ersetzen.

## Prüfergebnisse

| Prüfung                          | Ergebnis                                                                                                  |
| -------------------------------- | --------------------------------------------------------------------------------------------------------- |
| Folien-Lint                      | Nach Installation der festgeschriebenen npm-Abhängigkeiten erfolgreich.                                   |
| Vollständiger Render Tag 1       | 78 Folien und 265 sichtbare Zustände erfolgreich erzeugt.                                                 |
| Visuelle Sichtprüfung Kapitel 04 | Mehrere abgeschnittene Überschriften im aktuellen Stand gefunden.                                         |
| SQL aller zehn Domänen           | Schema, Seed-Daten und Abfragen laufen mit SQLite.                                                        |
| Datensatzmengen                  | Alle geprüften Tabellen enthalten 10 bis 20 Zeilen.                                                       |
| Java-Build aller sechs Projekte  | Erfolgreich. Im vollständigen Beispiel erscheinen sieben Deprecation-Warnungen zu `@MockBean`.            |
| REST-Starter                     | Erfolgreich auf Port 18081 gestartet. Port 8081 war auf dem Prüfgerät bereits belegt.                     |
| Arbeitsbaum                      | Bestehende Änderungen an `docs/draft.md` und `slides/decks/04-normalization.html` wurden nicht verändert. |

## Schlussfolgerung

Die Vorlesung braucht keinen vollständigen Neuaufbau. Ihre fachliche Erzählung trägt. Die Überarbeitung sollte die Artefakte an diese Erzählung angleichen und den Pflichtstoff schärfer begrenzen. Der Kurs ist für drei Tage realistisch, wenn jede Gruppe eine einfache Hauptentität durch alle Schichten führt, Normalisierung bei 3NF endet und Tag 3 einen kleinen, getesteten API-Schnitt statt vollständiger Domänenprozesse liefert.
