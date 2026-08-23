# Leitfaden für die Lehrperson

Dieser Leitfaden ergänzt die Sprecherhinweise in den Decks. Der verbindliche Takt steht in `docs/schedule.md`. Die Übungskarten geben die Fachfälle vor. Ändere ihre Regeln im Unterricht nicht spontan, sonst lassen sich die Ergebnisse der acht Gruppen kaum vergleichen.

## Vorbereitung

### Werkzeuge und Ports

- Auf dem Lehrrechner und den Rechnern der Studierenden sind Java 21, VS Code, SQLite 3, Git und `make` nötig.
- Für die Folien braucht der Lehrrechner Node.js, npm und den von Playwright verwendeten Chromium-Browser.
- Die Folien laufen laut `slides/Makefile` standardmäßig auf Port 8000.
- `java/rest-simple-exercise` und `java/rest-simple` laufen standardmäßig auf Port 8081. Prüfe vor Tag 3, ob der Port frei ist.
- Im Übungsprojekt `java/rest-simple-exercise` ist der Security-Starter in `build.gradle.kts` auskommentiert. Swagger UI und die Endpunkte sind ohne Anmeldung erreichbar. Nur `java/rest-simple` verlangt ein JWT.

### Technischer Probelauf

Führe diese Befehle am Repository-Root aus:

```sh
make -C java build-all
make -C java test-all
make -C slides pdf-all
```

Prüfe danach die vier Live-Beispiele einzeln. Das Übungs-Skeleton unter `java/repository-simple-exercise` soll unvollständig bleiben. `./verify-exercise.sh` prüft dort nur Build, Dateien und TODO-Marker, nicht die fachliche Korrektheit einer Lösung.

Halte auf dem Lehrrechner zwei VS-Code-Fenster bereit. Eines zeigt das gemeinsame Universitätsbeispiel. Das zweite zeigt die jeweilige Domänenkarte und kann bei Gruppenfragen geteilt werden. Öffne keine Lösung, solange die Gruppen arbeiten.

### Gruppenzuordnung

Trage die Namen zu Beginn in `exercises/README.md` ein. Die feste Ausgangsverteilung ist:

| Gruppe | Domäne           |
| -----: | ---------------- |
|      1 | `library`        |
|      2 | `pizza-delivery` |
|      3 | `gym`            |
|      4 | `cinema`         |
|      5 | `bike-rental`    |
|      6 | `vet-clinic`     |
|      7 | `car-workshop`   |
|      8 | `event-tickets`  |

`hotel` und `food-marketplace` bleiben als Ersatz frei. Tausche eine Domäne nur vor Übung 0. Danach baut jeder Arbeitsstand auf dem vorherigen auf.

### Lösungen und Checklisten

Jede Domäne hat `exercises/<domain>/solutions/` und `exercises/<domain>/CHECKLIST.md`. Die Lösungen liegen sichtbar auf `main`, sind aber erst für das Debrief gedacht. Sage das ausdrücklich. Es gibt keine Benotung, deshalb dient die Regel dem gemeinsamen Denken und nicht der Kontrolle.

Nutze die Checkliste vor dem Kurs, um jede Domäne gegen dieselben Lehrpunkte zu prüfen. Im Debrief öffnest du zuerst die Gruppenlösung. Danach vergleichst du gezielt mit `solutions/er.svg`, `solutions/README.md` oder den drei Dateien unter `sql/`. Eine abweichende Lösung ist in Ordnung, wenn Keys, Kardinalitäten, Functional Dependencies und Fachregeln zusammenpassen.

## 00 Auftakt

### Ziel

Die Studierenden kennen Ziel, Arbeitsweise und Tagesstruktur. Sie übernehmen eine Domäne und verstehen, dass sie dieselbe Anwendung drei Tage lang weiterbauen.

### Leitfragen

| Frage                                                          | Erwartete Antwort                                                                                                                       |
| -------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| "Was ist am Freitag sichtbar, das heute noch nicht existiert?" | Eine laufende REST API mit Datenbank, Validierung und Tests. Es entsteht keine Benutzeroberfläche.                                      |
| "Warum bleibt eine Gruppe drei Tage bei derselben Domäne?"     | Jede Stufe nutzt dieselben Fachbegriffe und Regeln. Dadurch werden Folgen früher Entscheidungen im Schema, in SQL und im Code sichtbar. |

### Häufige Fehler und Steuerung

- Wenn die Gruppe eine Benutzeroberfläche erwartet, zeige die drei Tagesergebnisse `schema.sql`, Java Repository und REST API.
- Wenn Studierende sofort VS Code öffnen, lasse zuerst die Domäne und eine offene Regel benennen. Code beginnt erst an Tag 2.
- Wenn "nichts wird benotet" als "Ergebnisse sind egal" verstanden wird, erkläre den Zweck des Debriefs. Die Gruppe muss ihre Entscheidung ausführbar oder lesbar machen.
- Wenn die Zuordnung stockt, verwende die feste Tabelle aus der Vorbereitung. Die beiden Reservedomänen bleiben frei.

### Einstieg und Zuordnung

- Frage nach Apps, die seit dem Aufstehen Daten gespeichert haben.
- Lasse jede Gruppe ihre Domäne und einen Satz zum Auftrag laut nennen.
- Prüfe, ob alle acht Gruppen `exercises/<domain>/README.md` geöffnet haben.
- Halte fest, dass `solutions/` bis zum Debrief geschlossen bleibt.

### Wenn Zeit fehlt

Kürze den Realitätsbezug zu SQLite und die Vorstellungsrunde. Die rote Linie, die Tagesergebnisse und die Domänenzuordnung bleiben vollständig.

## 01 Warum Abstraktion?

### Ziel

Die Studierenden trennen Fachbeschreibung und technische Umsetzung. Sie erkennen in Alltagssätzen Kandidaten für Dinge, Eigenschaften und Beziehungen.

### Leitfragen

| Frage                                                        | Erwartete Antwort                                                                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------ |
| "Was bedeutet 'Pizza' in der Pizzeria-App?"                  | Das Wort ist mehrdeutig. Produkt oder Rezept und die konkret bestellte Position müssen mindestens getrennt werden. |
| "Ist ein Modell falsch, wenn es nicht jedes Detail enthält?" | Nein. Ein Modell vereinfacht für einen Zweck, muss aber die dafür wichtigen Regeln enthalten.                      |

### Häufige Fehler und Steuerung

- Wenn aus jedem Nomen sofort eine Java-Klasse wird, frage nach eigener Identität und Lebensdauer.
- Wenn technische Fragen zu Framework, Oberfläche oder Indexen auftauchen, parke sie sichtbar für einen späteren Tag.
- Wenn eine Gruppe denselben Begriff für verschiedene Sachverhalte nutzt, frage nach einer Änderung über die Zeit. Das Beispiel mit alter und neuer Lieferadresse macht den Unterschied sichtbar.
- Wenn fünf Sätze nur eine Liste sind, verlange Verben und eine schmerzhafte Ausnahme.

### Debrief zu Übung 0

- Frage, welches Wort in der Beschreibung mehr als eine Bedeutung hatte.
- Lasse ein markiertes Verb vorlesen und frage, welche beiden Dinge es verbindet.
- Frage nach der offenen Fachfrage, die das Modell am stärksten verändern würde.
- Eine gute Antwort trennt Alltagssprache von Java und begründet mindestens eine noch offene Entscheidung.
- Notiere ungelöste Fragen neben dem Gruppenmodell. Sie müssen in Übung 1 als Annahme auftauchen.

### Wenn Zeit fehlt

Überspringe die Stripe-Folie und kürze die Geschichte zu `address`. Übung 0 bleibt bei 15 Minuten, weil sie den Ausgangspunkt für das ER-Modell liefert.

## 02 Das ER-Modell

### Ziel

Die Studierenden zeichnen Entities, Attributes, Keys, Relationships, Cardinalities und Optionality in Crow's-Foot-Notation. Sie erkennen Enrollment als Beziehung mit eigenen Daten.

### Leitfragen

| Frage                                                                        | Erwartete Antwort                                                          |
| ---------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| "Wo speichern wir das Einschreibedatum eines Studenten in einen Kurs?"       | An der Beziehung, praktisch in der assoziativen Entity `Enrollment`.       |
| "Kann ein Course genau einen Lecturer haben und ein Lecturer keinen Course?" | Ja. Cardinality und Optionality werden an beiden Enden getrennt gelesen.   |
| "Ist das ER-Modell schon unser SQL-Schema?"                                  | Nein. Es ist ein fachliches Modell ohne SQL-Typen, Indexe oder Controller. |

### Häufige Fehler und Steuerung

- Wenn eine Beziehung als String-Liste in einem Attribut landet, lasse Umbenennen, Note und Datum an diesem String erklären.
- Wenn Crow's-Foot-Enden vertauscht sind, lasse die Linie in beiden Richtungen laut lesen.
- Wenn Optionality aus der Formulierung statt aus einer Fachregel abgeleitet wird, frage ausdrücklich, ob null Partner erlaubt sind.
- Wenn jedes Attribut zur Entity wird, frage nach Identität, eigener Lebensdauer und eigenen Beziehungen.
- Wenn eine n:m-Beziehung eigene Daten trägt, fordere eine assoziative Entity statt einer unbeschrifteten Linie.

### Debrief zu Übung 1

- Zwei Gruppen zeigen je eine Beziehung und lesen sie in beiden Richtungen vor.
- Frage, wo eine Beziehung eigene Attribute trägt und warum diese Attribute zu keiner Seite allein gehören.
- Frage, welche Seite optional ist und welche konkrete Fachregel das begründet.
- Lasse eine schwache Entität samt identifizierendem Owner erklären.
- Eine gute Antwort nennt Keys, Mengen und Annahmen. Der Zeichenstil ist zweitrangig.
- Vergleiche danach gezielt mit `exercises/<domain>/solutions/er.svg`, ohne das Referenzmodell als einzig mögliche Lösung darzustellen.

### Wenn Zeit fehlt

Zeige Chen nur als Lesebeispiel und arbeite sofort mit Crow's Foot weiter. Kürze Moodle und die Fehlerfolie "alles ist eine Entity". Keys, Cardinalities, Optionality und `Enrollment` bleiben Pflicht.

## 03 Vom ER-Modell zu Tabellen

### Ziel

Die Studierenden übersetzen ein Crow's-Foot-Modell in Tabellen. Sie setzen Primary Keys, Foreign Keys, Join Tables, zusammengesetzte Keys und NULL aus einer Fachregel heraus ein.

### Leitfragen

| Frage                                                                  | Erwartete Antwort                                                   |
| ---------------------------------------------------------------------- | ------------------------------------------------------------------- |
| "In welche Tabelle kommt `department_id` bei Department 1:n Lecturer?" | In `lecturers`, also auf die n-Seite.                               |
| "Bedeutet NULL eine leere Zeichenkette?"                               | Nein. NULL steht für fehlend oder unbekannt, nicht für `""` oder 0. |

### Häufige Fehler und Steuerung

- Wenn der Foreign Key auf der 1-Seite landet, frage, wie dort mehrere IDs ohne Liste gespeichert würden.
- Wenn beide Tabellen gegenseitige Foreign Keys bekommen, lasse die ursprüngliche Beziehung noch einmal laut lesen.
- Wenn eine Join Table nur als technische Hilfe gilt, frage nach Datum, Menge, Rolle oder Status der Beziehung.
- Wenn jede Spalte NULL erlaubt, verlange für jede Ausnahme einen vollständigen Fachsatz.
- Wenn zusammengesetzte Identität verloren geht, nutze `CourseSession` oder den Domänenfall aus `01-er.md`.

### Debrief zu Übung 2

- Lasse eine ER-Linie und den daraus entstandenen Foreign Key zeigen.
- Frage, welche n:m-Beziehung zur Join Table wurde und welche eigenen Attribute sie trägt.
- Frage nach einer NULL-Spalte und der Fachregel, die den fehlenden Wert erlaubt.
- Eine gute Antwort kann jede Tabelle auf eine Entity oder Beziehung zurückführen.
- Halte Abweichungen fest. Übung 3 darf den Tabellenentwurf ändern.

### Wenn Zeit fehlt

Kürze das didaktische Beispiel zur schwachen Entity und die SQLite-Quellenfolie. Die Regeln für 1:n, n:m, Keys und NULL bleiben vollständig.

## 04 Normalisierung

### Ziel

Die Studierenden erkennen Insert-, Update- und Delete-Anomalien. Sie leiten Functional Dependencies ab und zerlegen bis 3NF. BCNF bleibt eine begründete Kontrollfrage.

### Leitfragen

| Frage                                                                                                   | Erwartete Antwort                                                                                                        |
| ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| "Was passiert, wenn Anna Weber in einer angenommenen flachen Tabelle in 80 Zeilen zu Weber-Stein wird?" | Alle 80 Zeilen müssen geändert werden. Ein ausgelassenes Update erzeugt zwei Wahrheiten.                                 |
| "Bestimmt `student_id` die Note?"                                                                       | Nein. Erst die Kombination aus `student_id` und `course_id` bestimmt die Note.                                           |
| "Muss jede Produktionsdatenbank BCNF haben?"                                                            | Nein. 3NF ist oft ausreichend. Eine Denormalisierung braucht einen gemessenen Grund und eine klare Aktualisierungsregel. |

### Häufige Fehler und Steuerung

- Wenn Wiederholung nur als Speicherproblem gilt, spiele ein unvollständiges Update und das Löschen der letzten Zeile durch.
- Wenn Functional Dependencies aus den Beispielzeilen geraten werden, frage nach der Fachregel, die für alle gültigen Daten gelten soll.
- Wenn `student_id` allein als Enrollment-Key gilt, frage nach zwei Courses desselben Studenten.
- Wenn 2NF ohne zusammengesetzten Candidate Key erklärt wird, markiere zuerst den ganzen Key.
- Wenn BCNF die Gruppe blockiert, behandle 3NF als Pflichtziel und die konkrete Falle der Karte als kurze Zusatzprüfung.

### Debrief zu Übung 3

- Frage, welche konkrete Operation den ersten Widerspruch erzeugt hat.
- Lasse eine partielle und eine transitive Functional Dependency nennen.
- Frage, welche Tatsache vor und nach der Zerlegung nur noch an einem Ort steht.
- Frage, ob das ER-Modell geändert werden musste und welche vergessene Entity sichtbar wurde.
- Eine gute Antwort zeigt Tabellen, Keys und Foreign Keys sowie einen verständlichen Satz zur BCNF-Falle.
- Nutze `exercises/<domain>/solutions/README.md` erst nach der Erklärung der Gruppe.

### Wenn Zeit fehlt

Kürze BCNF auf die Prüffrage "Ist jeder Determinant ein Candidate Key?". Überspringe die BigQuery-Folie. 1NF bis 3NF und die drei Anomalien bleiben Pflicht.

## 05 SQL-Grundlagen

### Ziel

Die Studierenden machen das Schema in SQLite ausführbar. Sie unterscheiden DDL und DML und nutzen `SELECT`, `WHERE`, `ORDER BY`, `LIMIT`, `INSERT`, `UPDATE` und `DELETE` mit Constraints.

### Leitfragen

| Frage                                     | Erwartete Antwort                                                              |
| ----------------------------------------- | ------------------------------------------------------------------------------ |
| "Wo steht die Schleife in SELECT?"        | SQL beschreibt das Ergebnis. Die Datenbank plant die Ausführung.               |
| "Reicht PRIMARY KEY für alle Fachregeln?" | Nein. Das Schema braucht unter anderem `NOT NULL`, `UNIQUE` und `FOREIGN KEY`. |

### Live-Demo

Arbeite in einer frischen temporären Datenbank. `queries.sql` ändert Lenas E-Mail-Adresse und löscht Samir Saleh.

```sh
cd java/sql/university
UNIVERSITY_DB="$(mktemp /tmp/university-demo.XXXXXX.db)"
sqlite3 "$UNIVERSITY_DB" < schema.sql
sqlite3 "$UNIVERSITY_DB" < seed.sql
sqlite3 "$UNIVERSITY_DB" ".tables"
sqlite3 -header -column "$UNIVERSITY_DB" < queries.sql
```

Stoppe nach Schema und Seed kurz. Zeige einen gültigen `SELECT` und einen absichtlichen `UNIQUE`-Fehler, bevor du die zwölf vorbereiteten Queries ausführst.

### Häufige Fehler und Steuerung

- Wenn die Gruppe gegen eine alte Datenbankdatei arbeitet, lasse eine neue Datei anlegen. Wiederholtes Seeding erzeugt sonst irreführende Fehler.
- Wenn Eltern und Kinder in falscher Reihenfolge geladen werden, zeige den verletzten Foreign Key und leite daraus die Reihenfolge ab.
- Wenn `UPDATE` oder `DELETE` kein `WHERE` hat, verlange zuerst den passenden `SELECT` mit derselben Bedingung.
- Wenn `PRAGMA foreign_keys = ON` fehlt, lasse einen unbekannten Foreign Key absichtlich einsetzen und vergleiche das Verhalten.
- Wenn der Seed nur Happy-Path-Daten enthält, fordere NULL an einer erlaubten Stelle und mindestens einen Datensatz ohne optionale Zuordnung.

### Debrief zu Übung 4

- Frage, welcher Constraint einen echten Eingabefehler gefangen hat.
- Lasse die Ladefolge der Tabellen anhand zweier Foreign Keys erklären.
- Frage nach einer Spalte, die NULL erlaubt, und nach der Fachregel dazu.
- Eine gute Antwort zeigt, dass `schema.sql` und `seed.sql` eine leere Datenbank ohne Handarbeit aufbauen.
- Prüfe einen Duplicate Key, ein fehlendes Pflichtfeld und einen unbekannten Foreign Key.

### Wenn Zeit fehlt

Kürze die CRUD-Zuordnung und den SQLite-Realitätsbezug. Die Terminal-Demo, Constraints und der sichere Rhythmus aus `SELECT`, Write und Kontrolle bleiben.

## 06 JOINs und Aggregation

### Ziel

Die Studierenden verbinden Zeilen mit `JOIN`, bewahren linke Zeilen mit `LEFT JOIN` und verdichten Ergebnisse mit `GROUP BY`, Aggregatfunktionen, `HAVING` und Subqueries.

### Leitfragen

| Frage                                                                       | Erwartete Antwort                                                                                        |
| --------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| "Was passiert beim JOIN mit einer Student-Zeile ohne passenden Enrollment?" | Beim INNER JOIN verschwindet sie. Beim LEFT JOIN bleibt sie mit NULL auf der rechten Seite.              |
| "Warum ist COUNT(*) nach LEFT JOIN gefährlich?"                             | Es zählt auch die erhaltene linke Ergebniszeile ohne Partner. `COUNT(right_column)` ignoriert dort NULL. |
| "WHERE oder HAVING für Courses mit mehr als zwei Belegungen?"               | `HAVING`, weil die Bedingung den Wert einer Gruppe nach `GROUP BY` prüft.                                |

### Häufige Fehler und Steuerung

- Wenn JOIN mit Zeilennummern oder Namen verbunden wird, markiere Primary Key und Foreign Key in beiden Tabellen.
- Wenn ein Venn-Diagramm die Erklärung bestimmt, gehe Zeile für Zeile durch einen Treffer mit zwei Partnern.
- Wenn `COUNT(*)` bei leeren Gruppen 1 liefert, zeige die erhaltene linke Zeile mit NULL rechts.
- Wenn `WHERE COUNT(...)` geschrieben wird, lasse die logische Reihenfolge bis `GROUP BY` und `HAVING` aufsagen.
- Wenn `DISTINCT` einen falschen JOIN verstecken soll, entferne es und prüfe zuerst die Matching-Bedingung.

### Debrief zu Übung 5

- Frage, welche fachliche Frage einen LEFT JOIN brauchte und welcher Datensatz sonst verschwunden wäre.
- Frage, wo `COUNT(*)` ein falsches Ergebnis geliefert hätte.
- Lasse die Subquery allein ausführen und ihre Teilfrage in einem Satz nennen.
- Frage, warum Query 7 `HAVING` statt `WHERE` verwendet.
- Eine gute Antwort enthält über jeder Query Nummer und erwartetes Ergebnis und kann Abweichungen erklären.

### Wenn Zeit fehlt

Die Reservefolie zu Window Functions entfällt immer. Kürze danach `DISTINCT` und die GitHub-Folie. INNER JOIN, LEFT JOIN, `COUNT(column)`, `GROUP BY`, `HAVING` und die Subquery bleiben.

## 07 JDBC und Cursor

### Ziel

Die Studierenden erklären den Weg von Java über JDBC Driver und Connection zur SQLite-Datei. Sie binden Werte mit `PreparedStatement`, bewegen den `ResultSet`-Cursor und bilden Zeilen auf `Student` ab.

### Leitfragen

| Frage                                                        | Erwartete Antwort                                                        |
| ------------------------------------------------------------ | ------------------------------------------------------------------------ |
| "Wo steht der ResultSet-Cursor direkt nach executeQuery()?"  | Vor der ersten Zeile. Erst `next()` bewegt ihn auf einen Datensatz.      |
| "Warum schützt ein Fragezeichen vor SQL Injection?"          | Der Driver bindet den Wert als Daten. Er wird nicht Teil der SQL-Syntax. |
| "Wer schließt Connection und ResultSet bei einer Exception?" | `try-with-resources` schließt alle dort deklarierten Ressourcen.         |

### Live-Demo

```sh
cd java/cursor-simple
./gradlew build
./gradlew run
./gradlew runRefactored
```

Zeige im ersten Lauf Create und List. Setze einen Breakpoint vor `resultSet.next()` und einen in den Konstruktorblock. Öffne danach `Main.java` und `MainRefactored.java` nebeneinander. Die unsichere Injection-Variante steht absichtlich nicht im Repository. Nutze dafür nur den klar markierten Einzeiler auf Folie 07.7 und lokale Seed-Daten.

### Häufige Fehler und Steuerung

- Wenn vor `next()` aus dem ResultSet gelesen wird, lasse den Cursorzustand im Debugger zeigen.
- Wenn Eingaben in SQL konkateniert werden, ersetze einen Wert durch `?` und binde ihn mit dem passenden Setter.
- Wenn Parameter verschoben sind, nummeriere Fragezeichen und Setter ab 1 in zwei parallelen Spalten.
- Wenn `LocalDate` direkt aus einem beliebigen String gebaut wird, verweise auf ISO-Text und `LocalDate.parse`.
- Wenn Ressourcen nur am Ende manuell geschlossen werden, wirf gedanklich vorher eine Exception und frage, welcher Pfad das Schließen noch erreicht.

### Debrief zu Übung 6

- Frage, welche Teile in Student-Repository und Domänen-Repository gleich geblieben sind.
- Lasse die Stelle zeigen, an der eine Datenbankzeile zum Java-Objekt wird.
- Frage, welche externen Werte gebunden und welche Teile des SQL statisch sind.
- Frage nach dem Verhalten bei leerem Ergebnis und bei einem `UNIQUE`-Fehler.
- Eine gute Lösung führt `./verify-exercise.sh` grün aus, zeigt aber zusätzlich echtes CRUD über die CLI. Der Verify-Lauf allein beweist die Implementierung nicht.
- Vergleiche die Domänenfelder mit `exercises/<domain>/04-jdbc.md`, nicht mit einem generischen `Book`-Beispiel.

### Wenn Zeit fehlt

Kürze pgJDBC, Fehlerlogging und den vollständigen Datei-Walkthrough. Behalte Connection, PreparedStatement, Cursor, Row Mapping, Datum und `try-with-resources`. Die 75 Minuten für Übung 6 bleiben unverändert.

## 08 Das Repository Pattern

### Ziel

Die Studierenden kapseln Datenzugriff hinter einem typisierten Repository. Sie erkennen den gemeinsamen CRUD-Ablauf und können erklären, wie Reflection, Annotationen und Spring Data darauf aufbauen.

### Leitfragen

| Frage                                                                       | Erwartete Antwort                                                                                                |
| --------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| "Welche Teile ändern sich zwischen StudentRepository und CourseRepository?" | Typ, Tabellenname, Spalten, Mapping und spezielle Queries ändern sich. Der CRUD-Ablauf bleibt weitgehend gleich. |
| "Ersetzt ein Repository SQL?"                                               | Nein. Es kapselt den Datenzugriff. SQL oder ORM arbeitet darunter weiter.                                        |
| "Warum reicht CREATE TABLE IF NOT EXISTS nach Release 1 nicht?"             | Der Befehl versioniert und verändert bereits vorhandene Schemas nicht. Dafür braucht die Anwendung Migrationen.  |

### Live-Demo

```sh
cd java/repository-simple
./gradlew build
./gradlew run
```

Der Lauf zeigt Create, All, Get, Update und Delete. Öffne danach `AbstractRepository.java`, `Student.java`, `Entity.java` und `Column.java`. Vergleiche zuletzt `java/cursor-simple/src/main/java/org/lecture/MainRefactored.java` mit dem Reflection-Mapping.

### Häufige Fehler und Steuerung

- Wenn das Repository als Datenbank bezeichnet wird, frage, wo die SQLite-Datei und das SQL geblieben sind.
- Wenn alles generisch werden soll, frage nach einer fachlichen Suche wie `findByIsbn` und nach dem Row Mapping.
- Wenn Reflection als kostenlose Abstraktion gilt, ändere gedanklich einen Spaltennamen und frage, wann der Fehler sichtbar wird.
- Wenn `IF NOT EXISTS` als Migration gilt, vergleiche eine alte Installation ohne neue Spalte mit einer Neuinstallation.
- Wenn Spring Data als Magie erscheint, ordne `all/get/create/update/delete` den Methoden `findAll/findById/save/delete` zu.

### Debrief zu Übung 7

Übung 7 entfällt im verbindlichen Ablauf. Falls du sie als freiwillige Weiterarbeit oder bei zusätzlicher Zeit nutzt:

- Frage, welcher Teil der Suche fachlich blieb und welcher JDBC-Ablauf wiederverwendet wurde.
- Lasse den Foreign Key der zweiten Entity bis zur Zieltabelle verfolgen.
- Frage, welcher Rückgabetyp einen fehlenden Treffer ausdrückt.
- Frage, welche Migration eine bereits verteilte Datenbank für die zweite Tabelle bräuchte.
- Eine gute Antwort zeigt `findByX`, beide Models, das CREATE SQL und einen echten CLI-Aufruf.

### Wenn Zeit fehlt

Übung 7 und die Reservefolie zu Transaktionen entfallen bereits. Kürze ORM auf zwei Sätze und zeige Flyway nur anhand der drei vorhandenen Migrationsdateien. Die Brücke zu Spring Data bleibt.

## 09 HTTP, REST und OpenAPI

### Ziel

Die Studierenden entwerfen einen HTTP-Vertrag mit Ressourcen, Methoden, Statuscodes und JSON. Sie unterscheiden diesen Vertrag von Java-Code und Datenbankmodell.

### Leitfragen

| Frage                                                   | Erwartete Antwort                                                                                                            |
| ------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| "Was unterscheidet GET /students/7 von GET /students?"  | Der erste Pfad bezeichnet eine Einzelressource, der zweite eine Collection.                                                  |
| "Welcher Status passt zu einem neu angelegten Student?" | `201 Created`. Ein Response-Body ist möglich. `204` hat keinen Body.                                                         |
| "Ist PUT idempotent?"                                   | Ja. Derselbe Request zielt bei Wiederholung auf denselben beabsichtigten Zustand. Die Antworten müssen nicht identisch sein. |

### Häufige Fehler und Steuerung

- Wenn URLs Verben wie `/getStudents` tragen, streiche das Verb und lasse Methode plus Ressource lesen.
- Wenn jeder Erfolg 200 liefert, ordne Create, Delete und einfache Reads den Codes 201, 204 und 200 zu.
- Wenn Request und Response identisch sind, frage, wer ID und serverseitige Felder besitzen darf.
- Wenn 400, 404 und 409 vermischt werden, trenne Formfehler, fehlende Ressource und Zustandskonflikt.
- Wenn Swagger UI mit OpenAPI gleichgesetzt wird, frage nach dem maschinenlesbaren Dokument unter `/v3/api-docs`.

### Debrief zu Übung 8

- Lasse eine andere Gruppe den Vertrag ohne mündliche Erklärung lesen.
- Frage, ob jede URL eine Ressource als Nomen benennt.
- Frage, welche Operation idempotent ist und was bei einer Wiederholung gleich bleibt.
- Lasse Request und Response desselben POST vergleichen.
- Eine gute Antwort nennt für jeden Pfad Erfolg und erwartete Fehler und übernimmt die Hauptressource sowie den Beziehungsendpunkt aus `exercises/<domain>/06-rest.md`.

### Wenn Zeit fehlt

Kürze Header, Stripe und die ausführliche Idempotency-Diskussion. Behalte Collection gegen Einzelressource, Methoden, 200/201/204/400/404/409/500, getrennte DTO-Formen und OpenAPI.

## 10 Spring Boot

### Ziel

Die Studierenden starten das Übungsprojekt, prüfen Health und bauen GET sowie POST für die erste Ressource. Sie ordnen Dependency Injection, JPA und `JpaRepository` den bekannten Konzepten zu.

### Leitfragen

| Frage                                                 | Erwartete Antwort                                                                              |
| ----------------------------------------------------- | ---------------------------------------------------------------------------------------------- |
| "Wer ruft den Konstruktor von StudentController auf?" | Spring erstellt die Beans und injiziert die Abhängigkeiten.                                    |
| "Ersetzt @Entity unser Schema?"                       | Nein. Die Annotation beschreibt Mapping. Migrationen und Datenbank-Constraints bleiben nötig.  |
| "Was ist JpaRepository im Vergleich zu gestern?"      | Dieselbe Repository-Idee mit einer von Spring Data erzeugten Implementierung und JPA darunter. |

### Live-Demo

Terminal 1:

```sh
cd java/rest-simple-exercise
./gradlew build
make run
```

Terminal 2:

```sh
curl -i http://localhost:8081/api/students/health
```

Falls Spring mit der Standardanmeldung antwortet, übernimm das Passwort aus dem Startlog:

```sh
SPRING_PASSWORD='Wert aus dem Startlog'
curl -i -u "user:${SPRING_PASSWORD}" http://localhost:8081/api/students/health
```

Öffne danach `http://localhost:8081/swagger-ui.html` und `http://localhost:8081/v3/api-docs`. Arbeite in vier sichtbaren Checkpoints: Health 200, GET mit Liste, POST 201, GET mit gespeichertem Datensatz.

### Häufige Fehler und Steuerung

- Wenn Port 8081 belegt ist, stoppe den fremden Prozess oder starte bewusst mit einer anderen `SERVER_PORT`-Variable. Passe dann alle URLs gemeinsam an.
- Wenn Spring Controller oder Repository nicht findet, prüfe Package-Lage und Component Scan ab `DemoApplication`.
- Wenn die Gruppe eine Entity direkt zurückgibt, fordere ein `StudentResponse` oder das DTO der Domänenressource.
- Wenn `ddl-auto=update` als Ersatz für Flyway behandelt wird, verweise auf die verlangte Migration und die Geschichte bestehender Datenbanken.
- Wenn POST grün ist, aber GET leer bleibt, prüfe zuerst, ob beide denselben Repository-Pfad und dieselbe Datenbankdatei nutzen.

### Debrief zu Übung 9

- Frage, welche Annotation den HTTP-Pfad und welche das Persistenzmodell markiert.
- Frage, wer die Repository-Implementierung erzeugt.
- Verfolge einen POST über Controller, DTO, Repository, JPA und SQLite.
- Lasse die tatsächlich erzeugte SQL-Anweisung im Log zeigen, wenn die Gruppe sie aktiviert hat.
- Eine gute Antwort zeigt GET 200, POST 201 und den gespeicherten Datensatz im folgenden GET.

### Wenn Zeit fehlt

Kürze die vollständige Annotationsübersicht und die Spring-Data-Quellenfolie. Behalte Dependency Injection und die vier Live-Checkpoints. Validation und Fehler gehören erst in Deck 11.

## 11 Gutes Anwendungsdesign

### Ziel

Die Studierenden trennen HTTP-Darstellung, Fachlogik und Speicherung. Sie nutzen Request- und Response-DTOs, Validation sowie ein gemeinsames Fehlerformat, ohne leere Schichten zu erzwingen.

### Leitfragen

| Frage                                                  | Erwartete Antwort                                                                                      |
| ------------------------------------------------------ | ------------------------------------------------------------------------------------------------------ |
| "Darf der Controller direkt repository.save aufrufen?" | Bei einfachem CRUD ja. Bei Fachregeln, mehreren Repositories oder einer Transaktion lohnt ein Service. |
| "Warum geben wir nicht die JPA-Entity zurück?"         | API-Vertrag und Persistenzmodell sollen sich unabhängig ändern können.                                 |
| "Wo wird eine ungültige E-Mail zu HTTP 400?"           | Validation prüft den Request am Rand. Ein zentraler Handler übersetzt den Fehler in die HTTP-Antwort.  |

### Häufige Fehler und Steuerung

- Wenn für jede Methode ein leerer Service entsteht, frage nach der Entscheidung, die diese Klasse besitzt.
- Wenn DTO und Entity dasselbe Objekt sind, ergänze gedanklich `internalNote` und frage, ob Clients das Feld sehen dürfen.
- Wenn nur Java auf Eindeutigkeit prüft, spiele zwei gleichzeitige Requests durch und fordere den Datenbank-Constraint.
- Wenn alle Exceptions zu 500 werden, sortiere einen Formfehler, eine fehlende ID und einen Konflikt in 400, 404 und 409.
- Wenn die Feldnamen aus `java/rest-simple` übernommen werden, weise auf den bestehenden Zielbild-Vertrag mit `name`, `mnr`, `createdOn` und den Kursstandard mit `firstName`, `studentNumber`, `enrollmentDate` hin.

### Debrief zu Übung 10

- Lasse den schwierigsten Fehlerfall durch Controller, optionalen Service, Repository und Datenbank verfolgen.
- Frage, wo syntaktische Validation endet und eine Fachregel beginnt.
- Lasse ein internes Entity-Feld nennen, das im Response nicht erscheinen soll.
- Frage, welche Exception welchen Statuscode und welches Fehlerformat erzeugt.
- Eine gute Antwort zeigt CRUD, getrennte DTOs sowie 400, 404 und 409 für die Hauptressource.
- Der verbindliche Ablauf kürzt auf die Hauptressource. Der Beziehungsendpunkt aus `exercises/<domain>/06-rest.md` bleibt offen.

### Wenn Zeit fehlt

Die Übung dauert laut Ablauf 55 statt 75 Minuten. Streiche zuerst den Beziehungsendpunkt. Zeige die hexagonale Struktur von `java/rest-simple` nur als Dateibaum. DTOs, `@Valid`, Fachregel, Service-Entscheidung und Fehlercodes bleiben.

## 12 Die Anwendung absichern

### Ziel

Die Studierenden prüfen den HTTP-Rand mit MockMvc und eine Fachentscheidung mit Mockito. Sie verstehen Correlation ID, Actuator, CORS und Security Filter Chain auf Überblicksniveau.

### Leitfragen

| Frage                                                | Erwartete Antwort                                                                                                                          |
| ---------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| "Was beweist ein MockMvc-Test ohne echte Datenbank?" | HTTP-Mapping, Status, JSON, Validation und Fehlerübersetzung am Web-Rand. Er beweist kein SQL und keinen vollständigen Produktionsbetrieb. |
| "Warum reicht eine Request-ID im Response nicht?"    | Dieselbe ID muss in Logs und nachgelagerten Aufrufen stehen, damit ein Request verfolgt werden kann.                                       |
| "Was schützt CORS?"                                  | Browserzugriffe zwischen Origins. CORS ist weder Authentifizierung noch Verschlüsselung.                                                   |

### Live-Demo

Führe nur die beiden passenden Testklassen aus:

```sh
cd java/rest-simple
./gradlew test \
  --tests 'com.example.restsimple.adapter.in.web.StudentControllerTest' \
  --tests 'com.example.restsimple.application.service.StudentServiceTest'
```

Öffne danach `java/rest-simple/src/main/java/com/example/restsimple/config/LoggingFilter.java`. Verfolge Übernahme oder Erzeugung von `X-Correlation-ID`, `MDC.put`, Response-Header und das Aufräumen im `finally`.

### Häufige Fehler und Steuerung

- Wenn ein Controller-Test eine echte Datenbank fordert, benenne zuerst die zu prüfende Grenze. Ergänzende Integrationstests sind ein anderer Testtyp.
- Wenn Mockito jede interne Zeile überprüft, beschränke die Prüfung auf Rückgabe, Fachentscheidung und eine wichtige Port-Interaktion.
- Wenn Getter oder Spring selbst getestet werden, frage, welche eigene Änderung dieser Test absichern soll.
- Wenn Correlation IDs nur erzeugt, aber nicht zurückgegeben oder aus MDC entfernt werden, verfolge den ganzen Request bis `finally`.
- Wenn CORS als API-Schutz gilt, vergleiche einen Browser mit `curl`.

### Debrief zu Übung 11

- Frage, welche Grenze jeder der zwei Tests prüft.
- Lasse zeigen, welches Repository oder welcher Service im Test ersetzt wurde.
- Frage, warum der Erfolgsfall 201, eine Response-ID und das Fachfeld der Karte prüft.
- Frage, warum der Konfliktfall 409 und einen stabilen Fehlercode braucht.
- Sende einen Request mit eigener `X-Correlation-ID` und suche dieselbe ID im Response und im Log.
- Eine gute Abgabe hat genau die zwei Fälle aus `exercises/<domain>/06-rest.md`, einen passenden Response-Header und eine Logzeile.

### Wenn Zeit fehlt

Pagination und PATCH sind Reserve und entfallen. Kürze Docker, CORS und Security auf je einen Satz. Wenn der Kurs zurückliegt, entfällt zuerst Übung 11. Danach kann der gesamte Block 12 entfallen. Öffne in diesem Fall wenigstens die vorhandenen Tests und den LoggingFilter im Abschluss.

## 13 Abschluss

### Ziel

Die Studierenden ordnen ihre Dateien allen acht Schritten der roten Linie zu. Sie erklären einen vollständigen Request-Weg und benennen einen konkreten nächsten Engpass.

### Leitfragen

| Frage                                                 | Erwartete Antwort                                                                                                                   |
| ----------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| "Welche Datei beweist Schritt 3?"                     | Das normalisierte `schema.sql` mit Keys und Constraints. Das ER-Diagramm gehört zum vorherigen Schritt.                             |
| "Wo würdet ihr morgen bei einem falschen 409 suchen?" | Im Vertrag, in Validation und Fachregel, im Exception Mapping, am Repository-Constraint und in den Logs entlang der Correlation ID. |

### Häufige Fehler und Steuerung

- Wenn nur Framework-Namen genannt werden, frage nach der Datei und der Regel, die dort sichtbar ist.
- Wenn die Gruppe als nächsten Schritt "mehr Features" nennt, fordere ein beobachtetes Problem und einen Test, der die Verbesserung belegt.
- Wenn ein 409 nur der Datenbank zugeschrieben wird, verfolge Vertrag, Handler, Service und Constraint gemeinsam.
- Wenn unvollständige Teile versteckt werden, erinnere daran, dass nichts benotet wird. Eine klare Grenze ist für den Rückblick nützlicher als eine Demo mit Handgriffen.

### Abschlussrunde

- Jede Gruppe nennt in höchstens einer Minute eine Datei, ein beobachtetes Problem und den nächsten Test oder Lernschritt.
- Lasse einen vollständigen POST vom HTTP-Request bis SQLite und zurück erklären.
- Ordne die gezeigten Dateien noch einmal den acht exakten Schritten aus `docs/schedule.md` zu.
- Sammle Feedback zu einer konkreten Stelle, an der ein Zwischenschritt fehlte oder eine Übung zu lang war.

### Wenn Zeit fehlt

Überspringe die GitHub-Quellenfolie und die breite Liste möglicher Folgethemen. Die Dateizuordnung, der Request-Weg und die einminütige Gruppenrunde bleiben.

## Was am dritten Tag eingesammelt wird

Es gibt keine Benotung. Die Abgabe dient dem gemeinsamen Abschluss und der Kursauswertung. Jede Gruppe zeigt oder teilt:

- das bearbeitete Spring-Projekt für die Hauptressource
- die Liste der Endpunkte mit Methoden und Statuscodes
- einen Swagger-Screenshot mit dem eigenen GET und POST
- den gespeicherten Datensatz aus Übung 9
- den Stand von CRUD, DTOs, Validation sowie 400, 404 und 409 aus der gekürzten Übung 10
- falls Übung 11 stattfand, die Ergebnisse der genau zwei geforderten Tests sowie einen `X-Correlation-ID`-Header mit passender Logzeile
- einen Satz mit Datei, beobachtetem Problem und nächstem Test

Die Lehrperson nutzt `exercises/<domain>/CHECKLIST.md` und `exercises/<domain>/solutions/` für den Vergleich. Sie vergibt keine Punkte und verlangt keine nachträgliche Fertigstellung des gestrichenen Beziehungsendpunkts oder der entfallenen Übung 7.
