# Entwicklung einer Datenbankanwendung

Content-Spezifikation für 14 Reveal.js-Decks, drei Tage von 09:00 bis 16:00 Uhr. Alle Folientexte, Notizen und Übungen sind deutsch. Code, SQL und Identifier bleiben englisch. Ein technischer Begriff erscheint beim ersten Einsatz mit deutscher Erklärung. Die Studierenden arbeiten in VS Code. Es gibt keine Benotung. Lösungen liegen auf `main`. `cursor-simple` und `repository-simple` werden auf `Student` ausgerichtet. Das Lehrbeispiel nutzt durchgehend `Student`, `Course`, `Lecturer`, `Department` und `Enrollment`. Acht Gruppen bearbeiten je eine eigene Domäne.

## Stand

- Datum: 2026-08-23
- Status: geprüft gegen Java-Code, SQL-Dateien, SQLite-Ergebnisse, Spring-Beispiele und die Referenzkarten unter `exercises/library/`
- Übungsfolien: Die Mitte enthält zwei bis vier nummerierte Arbeitsschritte. Zeitbox und Abgabe bleiben im festen Rahmen.
- Codefolien: Das Highlight-Theme hat einen braunen Hintergrund. Falls der Kontrast nicht reicht, setzt der Deck-Agent den Hintergrund auf `--color-grey-dim`.
- Deck-Auswahl: Die Skripte und das Makefile nehmen `DECK` an, zum Beispiel `make debug DECK=day2`. Der Agent setzt `DECK`, statt fremde Decks zu ändern.

## Legende für Folienprompts

Jeder Prompt ist ein vollständiger Bauauftrag. Er nennt Layout, Grundzustand, Fragmente in Klickreihenfolge, Visual, exakten Folientext und Notizen. Die Überschrift `#### Folie NN.k: Titel` ist der exakte sichtbare Titel. Alles vor der ersten Fragmentangabe gehört zum Grundzustand. Fehlt eine Fragmentangabe, erscheint die ganze Folie im Grundzustand und hat keine Klickfragmente. `Highlights` auf Codefolien sind Fragmente in der genannten Reihenfolge. Sichtbar sind nur Titel, ausdrücklich genannter Text, Labels und Code. Auch Labels in einer Visual-Beschreibung sind exakter Folientext. Deck-Agenten ergänzen keine Prosa.

Die Prompt-Präfixe benennen das Layout: `Layout chapter-slide`, `Layout .lead-question`, `Layout .code-slide`, `Layout .exercise-slide` und `Layout citation-slide` verwenden die jeweilige CSS-Klasse. `Content slide`, `SVG`, `Pipeline`, `Sequence`, `Timeline`, `Grid`, `Terminal`, `Rote Linie` und `Full content SVG` bedeuten eine normale Content Slide mit dem beschriebenen Inline-SVG. `Two-column`, `Three-column` und `Four-way content` verwenden entsprechend viele Content-Spalten. Das beschriebene SVG oder der Codeblock ist das Visual. `blue` steht für Struktur und Daten, `amber` für Fokus, Warnung und Übung, `red` für Fehler und Anomalien, `green` für Erfolg. Deck-Agenten verwenden ausschließlich Theme-Tokens, keine Hexwerte.

Für normale Content Slides gilt dieses feste Raster, falls der einzelne Prompt nichts anderes sagt: Titel im Theme-Rahmen, Visual in der mittleren 70 Prozent breiten Fläche, Caption unten. Wiederholte Boxen sind gleich groß, haben Theme-Radius und 24 Pixel Innenabstand. Pfeile laufen von Boxkante zu Boxkante und enden vor dem Label. Zwei Spalten sind 46/46 Prozent breit und haben 8 Prozent Abstand. Tabellen haben eine blue Kopfzeile, linksbündigen Text und amber Fokuszellen. Fragmente ergänzen die bestehende Darstellung, sie verschieben keine Boxen. Eine `chapter-slide` eröffnet das Deck. Danach folgt immer die rote Linie. `.lead-question` hält die Frage zunächst allein fest. `.code-slide` nutzt Syntax-Highlighting und Zeilennummern. `.exercise-slide` zeigt oben Aufgabe und Zeit, in der Mitte zwei bis vier nummerierte Schritte und unten Abgabe sowie Dateipfad. `citation-slide` schließt jedes Deck mit System, überprüfbarem Fakt, vollständiger URL und Abrufdatum 2026-08-23.

Wenn ein Prompt mehrere Fragmente ohne Nummer nennt, gilt ihre Reihenfolge im Text. Bei Reihen, Spalten und Pfaden ist das links nach rechts, dann oben nach unten. Ein Deck-Agent zeigt auf jeder `citation-slide` die angegebene URL vollständig und ergänzt sichtbar `Abruf: 2026-08-23`.

## Die rote Linie

Alle Decks verwenden dasselbe horizontale SVG mit acht gleich großen, abgerundeten Boxen und Pfeilen. Inaktive Schritte sind grau. Der aktuelle Schritt hat amber Rahmen und amber Nummer. Erledigte Schritte sind blue, der nächste Schritt hat einen gestrichelten blue Rahmen. Bei mehreren aktiven Schritten dürfen mehrere Boxen amber sein. Die Beschriftungen lauten exakt:

1. Idee im Kopf
2. Darüber sprechen: Abstraktion und ER-Modell
3. Tabellen, die uns nicht anlügen: Normalisierung
4. Den Tabellen Fragen stellen: SQL
5. Aus Java fragen: JDBC, Cursor, Statements
6. Wiederholungen beenden: Repository Pattern
7. Andere zugreifen lassen: REST API mit Spring
8. Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware

## Verbindliche Repository-Fakten

Die Deck-Agenten übernehmen diese Namen und Zahlen unverändert.

- Das Universitäts-Schema besteht aus `departments`, `lecturers`, `students`, `courses` und `enrollments`. Die Seed-Datei enthält in dieser Reihenfolge 3, 5, 20, 7 und 40 Zeilen.
- `students` hat `id`, `first_name`, `last_name`, `email`, `student_number`, `enrollment_date`. `enrollments` hat `id`, `student_id`, `course_id`, `grade`, `enrolled_on`. Das Datum am Student heißt also `enrollment_date`, das Datum der Belegung heißt `enrolled_on`.
- `courses` hat `id`, `course_code`, `title`, `credits`, `lecturer_id`. `lecturers` hat `id`, `first_name`, `last_name`, `email`, `department_id`. `departments` hat `id`, `name`, `code`.
- `java/sql/university/queries.sql` enthält genau zwölf Anweisungen. Ihre erwarteten Zeilenzahlen sind der Reihe nach 20, 5, 1, 1, 16, 1, 7, 7, 3, 7, 24 und 3. Query 3 ändert Lenas E-Mail-Adresse. Query 4 löscht Samir Saleh. Die folgenden Queries laufen auf diesem veränderten Stand.
- Die Spring-Decks verwenden für den Kursvertrag `firstName`, `lastName`, `email`, `studentNumber` und `enrollmentDate`. Das größere Beispiel `java/rest-simple` nutzt dagegen `name`, `lastName`, `mnr` und `createdOn`. Code aus diesem Projekt bleibt verbatim und bekommt auf der Folie den Hinweis "bestehendes Zielbild, andere Namen".
- Die Übungsordner sind verbindlich und bauen aufeinander auf: A0 `a0-domain`, A1 `a1-er-model`, A2 `a2-relational-model`, A3 `a3-normalization`, B1 `b1-schema`, B2 `b2-sql`, B3 `b3-jdbc`, B4 `b4-repository`, C1 `c1-http-contract`, C2 `c2-spring-resource` und C3 `c3-tests-errors`. Jeder Pfad beginnt mit `exercises/<domain>/` und enthält ein Grundgerüst, eine Gruppenanleitung und einen Leitfaden.

## 00 Auftakt (Dateiname decks/00-opening.html)

### Ziel

Die Studierenden kennen Ziel, Arbeitsweise und Tagesstruktur. Sie sehen alle acht Schritte der roten Linie, wählen eine Gruppendomäne und wissen, dass sie dieselbe Anwendung drei Tage lang weiterbauen. Vorher gibt es nur ihre Java-Erfahrung. Danach klären wir, wie aus einer Idee ein gemeinsames Modell wird.

### Position auf der roten Linie

Vorher: noch kein Kursschritt. Aktuell: `Idee im Kopf`. Als Nächstes: `Darüber sprechen: Abstraktion und ER-Modell`.

### Erzählung

Ich beginne nicht mit Datenbankbegriffen. Ich frage, welche Apps heute vor dem Frühstück schon Daten gespeichert haben. Meist kommen Messenger, Fahrplan und Musik. Hinter jeder dieser Oberflächen steckt eine Entscheidung darüber, welche Dinge es gibt und wie sie zusammenhängen.

In drei Tagen bauen wir diesen Weg rückwärts sichtbar. Wir starten mit einer vagen Idee, zeichnen sie, bauen Tabellen, stellen SQL-Fragen und greifen aus Java darauf zu. Am dritten Tag kann ein anderer Rechner unsere Daten über HTTP verwenden.

Jede Gruppe bekommt eine andere Domäne. Das ist Absicht. Acht identische Lösungen würden nur zeigen, dass jemand mein Beispiel kopieren kann. Acht verschiedene Lösungen zeigen, ob die Methode trägt.

### Leitfragen

- Frage: "Was ist am Freitag sichtbar, das heute noch nicht existiert?" Erwartet: Eine laufende REST API mit Datenbank, Validierung und Tests. Typisch falsch: Nur ein ER-Diagramm; eine fertige Benutzeroberfläche.
- Frage: "Warum bleibt eine Gruppe drei Tage bei derselben Domäne?" Erwartet: Jede Stufe baut auf denselben Fachbegriffen und Regeln auf. Typisch falsch: Damit Gruppen nicht tauschen; weil die Domänen gleich schwer sind.

### Realitätsbezug

SQLite läuft laut Projektseite in jedem Android-Gerät und in Firefox, Chrome und Safari. Die kleine Datei aus unserem Kurs nutzt damit dieselbe Datenbank-Engine wie Software auf Milliarden Geräten.

### Folien

#### Folie 00.1: Entwicklung einer Datenbankanwendung

```text
Reveal.js Prompt: Layout chapter-slide. Grundzustand: Titel und "3 Tage · Java · SQLite · Spring". Keine Fragmente. Visual: frame.svg, darunter eine blue Linie von einer Sprechblase über Tabelle und Java-Datei zu einem HTTP-Pfeil. Notizen: "Wir bauen keine Folienanwendung. Wir bauen eine Idee Schritt für Schritt bis zu einer nutzbaren API. Alles, was ihr zeichnet, taucht später im Code wieder auf."
```

#### Folie 00.2: Der Weg durch den Kurs

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen aus `## Die rote Linie`. Status: erledigt keiner; aktiv `Idee im Kopf` in amber; als Nächstes `Darüber sprechen: Abstraktion und ER-Modell` mit gestricheltem blue Rahmen; alle übrigen Schritte grau. Fragmente: 1 Label `Tag 1` unter Schritten 1 bis 3, 2 `Tag 2` unter 4 bis 6, 3 `Tag 3` unter 7 bis 8. Folientext unten: "Eine Idee wird ausführbar." Notizen: "Heute beginnen wir bei einer Idee im Kopf. Jeder Pfeil löst ein konkretes Problem. Nach dem letzten Schritt kann ich zeigen, welche Datei zu welchem Kasten gehört."
```

#### Folie 00.3: Drei Etappen, ein durchgängiger API-Schnitt

```text
Reveal.js Prompt: Three-column content. Grundzustand: Spalten "Tag 1: schema.sql", "Tag 2: Java Repository", "Tag 3: REST API". Fragmente: die drei Dateisymbole nacheinander, jeweils green Haken. Visual: blue Dateikarten, amber Tagesnummern. Notizen: "Jeder Tag endet mit einem prüfbaren Ergebnis. Nichts wird benotet. Wir vergleichen Lösungen, führen Code aus und verbessern ihn gemeinsam."
```

#### Folie 00.4: Wie wir arbeiten

```text
Reveal.js Prompt: Two-column content. Grundzustand links "Frage → Versuch → Modell", rechts "Modell → Übung → Debrief". Fragmente: 1 "VS Code", 2 "Gruppenarbeit", 3 "nichts benotet". Visual: zwei blue Schleifen, Übungsphase amber. Notizen: "Ich werde oft erst fragen und später erklären. Falsche Antworten sind Material für die nächste Folie. Öffnet VS Code erst, wenn wir wirklich Code brauchen."
```

#### Folie 00.5: Welche Domäne gehört euch?

```text
Reveal.js Prompt: Content slide with table. Grundzustand: acht Zeilen: 1 library, 2 pizza-delivery, 3 gym, 4 cinema, 5 bike-rental, 6 vet-clinic, 7 car-workshop, 8 event-tickets. Spalten "Gruppe", "Domäne", "Namen". Fragmente: 1 amber Hinweis "Eine Domäne für alle drei Tage". Notizen: "Tragt eure Namen ein. Die Ersatzdomänen hotel und food-marketplace bleiben heute frei. Eure Aufgaben liegen unter exercises/<domain>/."
```

#### Folie 00.6: Was kann dabei schiefgehen?

```text
Reveal.js Prompt: Layout .lead-question. Grundzustand nur die Frage "Was geht schief, wenn wir sofort Tabellen tippen?" Fragmente: 1 kleine amber Zeile "30 Sekunden zu zweit". Notizen: "Erwartet sind widersprüchliche Begriffe, vergessene Regeln und teure Änderungen. Typisch falsch sind 'nichts, SQL zeigt Fehler' und 'wir ändern die Tabelle später'. Diese Frage öffnet das nächste Deck."
```

#### Folie 00.7: SQLite ist schon überall

```text
Reveal.js Prompt: Layout citation-slide. Grundzustand: "SQLite: eine Datei, Milliarden Geräte". Visual: blue Datenbankzylinder in Android-Telefon und Browserfenster, green Haken. Folientext: "In jedem Android-Gerät · in Firefox, Chrome und Safari", Quelle "https://sqlite.org/mostdeployed.html" und "Abruf: 2026-08-23". Notizen: "Unsere Kursdatenbank ist klein, aber nicht exotisch. SQLite nennt Android-Geräte und große Browser ausdrücklich als Einsatzorte. Morgen öffnen wir dieselbe Art Datei mit sqlite3 und Java."
```

### Übung

Keine eigene Übung. Die Domänenzuordnung dauert höchstens fünf Minuten und gehört zum Auftakt.

## 01 Warum Abstraktion? (Dateiname decks/01-why-abstraction.html)

### Ziel

Die Studierenden trennen fachliche Beschreibung und technische Umsetzung. Sie können in einer kurzen Geschichte Kandidaten für Dinge, Eigenschaften und Beziehungen markieren. Die Idee im Kopf liegt hinter uns. Jetzt schaffen wir eine gemeinsame Abstraktion, aus der im nächsten Deck ein ER-Modell wird.

### Position auf der roten Linie

Vorher: `Idee im Kopf`. Aktuell: `Darüber sprechen: Abstraktion und ER-Modell`. Als Nächstes: `Tabellen, die uns nicht anlügen: Normalisierung`.

### Erzählung

"Bau mir eine App für die Pizzeria." So beginnen echte Aufträge erstaunlich oft. Der Satz klingt klein. Nach zwei Rückfragen stehen dort Kunden, Adressen, Bestellungen, Pizzen, Beläge, Fahrer und Zahlungen. Nach zehn Minuten streiten drei Leute darüber, ob "Margherita" eine Pizza, ein Rezept oder eine bestellte Position ist.

Ich habe einmal ein System gesehen, in dem `address` an drei Stellen etwas anderes bedeutete. Im Kundenobjekt war es die Wohnadresse. In der Bestellung war es die Lieferadresse. Beim Fahrer war es der letzte bekannte Standort. Der Code lief. Die Fehler kamen erst, als ein Kunde umzog und alte Bestellungen plötzlich an der neuen Adresse erschienen.

Ein Modell ist eine Karte, nicht das Gebiet. Eine Stadtkarte zeigt keine Wasserleitungen, wenn ich zur Vorlesung laufen will. Das ist kein Mangel. Eine brauchbare Abstraktion lässt Dinge weg, aber sie lässt die Regeln stehen, die für unsere Frage zählen.

Ich will deshalb vor der ersten Tabelle fünf klare Sätze. Wer tut was? Welche Dinge müssen wir unterscheiden? Was bleibt über die Zeit erhalten? Die fünf Sätze sparen keine Tipparbeit. Sie verhindern, dass wir zehn Klassen mit drei Bedeutungen von `address` bauen.

### Leitfragen

- Frage: "Was bedeutet 'Pizza' in der Pizzeria-App?" Erwartet: Das Wort ist mehrdeutig, mindestens Rezept/Produkt und bestellte Position müssen getrennt werden. Typisch falsch: Eine Java-Klasse Pizza reicht; eine Pizza ist nur ein String.
- Frage: "Ist ein Modell falsch, wenn es nicht jedes Detail enthält?" Erwartet: Nein, es ist für einen Zweck vereinfacht und muss die relevanten Regeln enthalten. Typisch falsch: Ja, ein Modell muss vollständig sein; Details ergänzen wir erst nach dem Code.

### Realitätsbezug

Stripe trennt Product, Price und Checkout Line Item. Beim Erstellen einer Checkout Session verweist jede Line Item auf eine konkrete Price-ID. Das ist dieselbe Art Trennung wie Pizza, Preis und bestellte Position.

### Folien

#### Folie 01.1: Warum Abstraktion?

```text
Reveal.js Prompt: Layout chapter-slide. Titel "Warum Abstraktion?", Untertitel "Bevor wir Tabellen tippen". Visual: amber Sprechblase "Bau mir eine App für die Pizzeria", darunter leere blue Modellkarte. Keine Fragmente. Notizen: "Der Auftrag passt in einen Satz. Die Fachlichkeit passt nicht in einen Satz. Heute machen wir die Lücke sichtbar."
```

#### Folie 01.2: Von der Idee zum Gespräch

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: erledigt `Idee im Kopf` in blue; aktiv `Darüber sprechen: Abstraktion und ER-Modell` in amber; als Nächstes `Tabellen, die uns nicht anlügen: Normalisierung` mit gestricheltem blue Rahmen; Schritte 4 bis 8 grau. Keine Fragmente. Folientext unten: "Vorher: Idee im Kopf · Jetzt: Abstraktion · Danach: Normalisierung". Notizen: "Wir stehen beim zweiten Schritt. Das Modell ist noch keine Tabelle. Es liefert aber die Begriffe, aus denen wir später Tabellen bauen."
```

#### Folie 01.3: Bau mir eine App für die Pizzeria

```text
Reveal.js Prompt: Layout .lead-question. Grundzustand nur "Welche fünf Fragen stellt ihr zuerst?" Fragmente: 1 "Kunde? Bestellung? Lieferung? Zahlung? Änderung?" in amber. Notizen: "Erwartet sind Fragen zu Akteuren, Dingen, Abläufen, Regeln und Ausnahmen. Typisch falsch sind Fragen nach Framework und Farbe der Oberfläche. Ich sammle fünf Fragen ohne sie zu bewerten."
```

#### Folie 01.4: Ein Satz wird sieben Dinge

```text
Reveal.js Prompt: Content slide mit SVG. Grundzustand links eine amber Sprechblase mit dem exakten Text "Bau mir eine App für die Pizzeria". Rechts bleibt Platz für ein 4-mal-2-Raster. Fragmente in Reihenfolge: 1 Customer, 2 Address, 3 Order, 4 OrderItem, 5 Pizza, 6 Topping, 7 Driver als blue Karten im Raster; 8 amber Linien mit den Labels "wohnt an" von Customer zu Address, "enthält" von Order zu OrderItem und "liefert" von Driver zu Order. Folientext unten: "Wörter tragen verschiedene Rollen." Notizen: "Die Liste ist keine endgültige Lösung. Sie zeigt, wie schnell ein kurzer Auftrag wächst. OrderItem trennt das angebotene Produkt von der konkreten Bestellung."
```

#### Folie 01.5: Drei Bedeutungen von address

```text
Reveal.js Prompt: Two-column content. Links drei Codekarten `customer.address`, `order.address`, `driver.address`; rechts nach Klick die Bedeutungen Wohnadresse, historische Lieferadresse, Standort. Fragment 4: rote Pfeile von Umzug zu veränderter alter Bestellung. Notizen: "Gleiche Wörter garantieren keine gleiche Bedeutung. Alte Bestellungen brauchen die damalige Lieferadresse. Ein gemeinsames Modell zwingt uns, diesen Unterschied vor dem Bug zu benennen."
```

#### Folie 01.6: Karte und Gebiet

```text
Reveal.js Prompt: Content slide mit SVG. Links detailreiche graue Stadt, rechts vereinfachte blue Karte mit Weg zum Hörsaal. Fragmente: 1 Wasserleitungen verschwinden, 2 Weg bleibt amber, 3 Satz "Weglassen ist eine Entscheidung". Notizen: "Eine Karte ist für eine Frage gebaut. Für den Weg brauche ich Straßen, für eine Reparatur Leitungen. Unser Datenmodell lässt Oberfläche und Farben weg, behält aber Identität und Regeln."
```

#### Folie 01.7: Was gehört ins Modell?

```text
Reveal.js Prompt: Two-column content. Links green "Dinge, Beziehungen, Regeln, Identität". Rechts red "Buttons, CSS, Indexwahl, Controllerklassen". Fragmente: jede Zeile nacheinander. Notizen: "Wir trennen Fachlichkeit von Umsetzung. Später treffen wir technische Entscheidungen. Wer sie heute in das Modell mischt, erschwert das Gespräch mit Menschen ohne Java-Kontext."
```

#### Folie 01.8: Fünf Sätze reichen für den Start

```text
Reveal.js Prompt: Content slide. Exakter Text: "Wer nutzt das System? Welche Dinge bleiben erhalten? Was passiert? Welche Regeln gelten? Welche Ausnahme tut weh?" Fragmente: Fragen einzeln, letzte amber. Visual: fünf nummerierte Karten. Notizen: "Diese Fragen liefern kein perfektes Modell. Sie liefern genug Material für den ersten Entwurf. Die schmerzhafte Ausnahme verrät oft eine vergessene Beziehung."
```

#### Folie 01.9: Übung A0, eure Domäne in fünf Sätzen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "Übung A0", Timer "15 min". Dateipfad unten: `exercises/<domain>/a0-domain/README.md`. Drei Stufen: Eingang "Öffnet die Gruppenkarte und notiert die Hauptentität." Kern "Schreibt fünf Sätze. Markiert Nomen einmal und Verben doppelt." Vertiefung "Ergänzt eine Ausnahme und kennzeichnet eine offene Fachfrage mit ?." Ausgang: "fünf markierte Sätze, Hauptentität und eine offene Fachfrage". Keine Fragmente. Notizen: "Starttest: Die Gruppe kann ihre Hauptentität in einem Satz benennen. Nach zehn Minuten kann eine vorbereitete Beispielsatz-Schablone freigegeben werden."
```

#### Folie 01.10: Von Wörtern zu Modellbausteinen

```text
Reveal.js Prompt: Content slide mit SVG. Grundzustand Beispielsatz "Ein Student belegt einen Course." Fragmente: 1 Student und Course blue, 2 belegt amber, 3 Frage "mit welchem Datum?" amber, 4 Enrollment als neue blue Karte. Notizen: "Nomen sind Kandidaten, keine automatische Klassenliste. Verben sind Kandidaten für Beziehungen. Sobald die Beziehung eigene Daten trägt, kann daraus ein eigener Modellbaustein werden."
```

#### Folie 01.11: Stripe trennt Product und Price

```text
Reveal.js Prompt: Layout citation-slide. Visual: blue Karten Product, Price und Checkout Line Item; Line Item verweist mit amber Pfeil auf `price_id`. Folientext: "Stripe trennt Angebot, Preis und gekaufte Position." Quelle: "https://docs.stripe.com/products-prices/how-products-and-prices-work". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Stripe beschreibt Product und Price als getrennte Ressourcen. Eine Checkout Session nennt für jede Line Item die Price-ID. Im nächsten Deck geben wir solchen Trennungen eine feste ER-Notation."
```

### Übung

Übung A0, "Eure Domäne in fünf Sätzen", 15 Minuten. Die Gruppe arbeitet in `exercises/<domain>/a0-domain/`, wählt ihre Hauptentität, beschreibt den Auftrag in Alltagssprache und markiert eine unklare Regel. Ausgang sind fünf markierte Sätze, die Hauptentität und eine offene Fachfrage. Im Debrief frage ich: Welche Wörter waren mehrdeutig? Welches Verb wurde zur Beziehung? Welche Rückfrage verändert das Modell? Exakter Folientext steht auf Folie 01.9.

## 02 Das ER-Modell (Dateiname decks/02-er-model.html)

### Ziel

Die Studierenden zeichnen ein Entity-Relationship-Modell (Entitäts-Beziehungs-Modell) mit Entities (Entitäten), Attributes (Attributen), Keys (Schlüsseln), Relationships (Beziehungen), Cardinalities (Kardinalitäten) und Optionality (Optionalität). Sie können Chen- und Crow's-Foot-Notation lesen und nutzen danach Crow's Foot. Die Fachbegriffe aus Deck 01 werden jetzt ein prüfbares Modell. Im nächsten Schritt übersetzen wir dieses Modell in Tabellen.

### Position auf der roten Linie

Vorher: `Idee im Kopf`. Aktuell: `Darüber sprechen: Abstraktion und ER-Modell`. Als Nächstes: `Tabellen, die uns nicht anlügen: Normalisierung`.

### Erzählung

Im Flur sagt jemand: "Ein Student hat Kurse." Das klingt eindeutig, bis ich frage, ob ein Student ohne Kurs existieren darf und ob ein Kurs ohne Studierende stattfinden kann. Zwei kleine Fragen ändern die Linienenden im Diagramm und später die Constraints in der Datenbank.

Ich zeichne zuerst Chen, weil die Rollen sichtbar werden: Rechteck, Oval, Raute. Danach wechseln wir zu Crow's Foot. Es braucht weniger Platz und ähnelt den Diagrammen, die viele Werkzeuge aus einer Datenbank erzeugen.

Unser Universitätsmodell hat `Student`, `Course`, `Lecturer`, `Department` und `Enrollment`. `Enrollment` ist wichtig. Die Beziehung zwischen Student und Course trägt `enrollment_date` und `grade`. Eine bloße Linie könnte diese Daten nicht sauber aufnehmen.

Ein ER-Modell ist keine Tabelle und kein Klassendiagramm. Es legt weder SQL-Typen noch Indexe noch Controller fest. Ich will hier fachliche Identität, Beziehungen und Mengen verstehen. Technische Details kommen einen Schritt später.

### Leitfragen

- Frage: "Wo speichern wir das Einschreibedatum eines Studenten in einen Kurs?" Erwartet: An der Beziehung, praktisch in der assoziativen Entity Enrollment. Typisch falsch: In Student; in Course.
- Frage: "Kann ein Course genau einen Lecturer haben und ein Lecturer keinen Course?" Erwartet: Ja, Cardinality und Optionality werden an beiden Enden getrennt angegeben. Typisch falsch: 1:n bedeutet auf beiden Seiten mindestens eins; Optionality ist dasselbe wie n.
- Frage: "Ist das ER-Modell schon unser SQL-Schema?" Erwartet: Nein, es ist ein fachliches Modell. Typisch falsch: Ja, Rechtecke sind Tabellen; fast, es fehlen nur Datentypen.

### Realitätsbezug

Moodle führt pro Course eine Liste eingeschriebener Personen. Die Ansicht zeigt unter anderem Rolle, Gruppe, Status und Einschreibemethode, also Daten der Verbindung zwischen Person und Course.

### Folien

#### Folie 02.1: Das ER-Modell

```text
Reveal.js Prompt: Layout chapter-slide. Titel "Das ER-Modell", Untertitel "Dinge, Beziehungen, Mengen". Visual: Student und Course als blue Entity-Boxen, Enrollment amber dazwischen. Keine Fragmente. Notizen: "Jetzt geben wir unseren Wörtern eine Notation. Das Diagramm soll Fragen beantworten, bevor SQL sie teuer macht."
```

#### Folie 02.2: Wir präzisieren das Gespräch

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: erledigt `Idee im Kopf` in blue; aktiv `Darüber sprechen: Abstraktion und ER-Modell` in amber; als Nächstes `Tabellen, die uns nicht anlügen: Normalisierung` mit gestricheltem blue Rahmen; Schritte 4 bis 8 grau. Text unten: "Vorher: Idee im Kopf · Jetzt: ER-Modell · Danach: Normalisierung". Keine Fragmente. Notizen: "Wir bleiben im zweiten Schritt, aber werden genauer. Das Ergebnis ist noch unabhängig von SQLite und Java. Im nächsten Deck wird jede Modellentscheidung zu einer Tabellenentscheidung."
```

#### Folie 02.3: Was muss die Linie sagen?

```text
Reveal.js Prompt: Layout .lead-question. Frage: "Ein Student belegt Kurse. Wie viele, und muss er?" Fragmente: 1 "0..n Courses", 2 "jede Seite getrennt lesen". Notizen: "Erwartet ist null bis viele, wenn die Fachregel das erlaubt. Typisch falsch sind genau einer und mindestens einer, weil das Verb 'belegt' so klingt. Die Antwort kommt nicht aus der Grammatik, sondern aus der Fachregel."
```

#### Folie 02.4: Entity und Attribute

```text
Reveal.js Prompt: Content slide with SVG. Student-Box blue, Attributes `id`, `student_number`, `first_name`, `last_name`, `email`, `enrollment_date` erscheinen nacheinander. `id` erhält amber Schlüssel. Text: "Entity: unterscheidbares Ding · Attribute: beschreibende Werte". Notizen: "Eine Entity hat Identität. Zwei Studierende mit gleichem Namen bleiben zwei Studierende. Attribute beschreiben sie, ersetzen aber nicht automatisch die Identität."
```

#### Folie 02.5: Schlüssel beantworten "welcher?"

```text
Reveal.js Prompt: Two-column content. Links `id` als Primary Key amber, rechts `student_number` und `email` als Candidate Keys blue. Fragment 1 Duplikatnamen red, Fragment 2 eindeutige Nummer green. Notizen: "Der Primary Key identifiziert jeden Datensatz. Candidate Keys könnten fachlich ebenfalls eindeutig sein. Namen sind dafür ungeeignet, weil Menschen gleich heißen und Namen ändern."
```

#### Folie 02.6: Relationship

```text
Reveal.js Prompt: SVG Student links, Course rechts, Linie "enrolls in". Fragmente: 1 Lecturer "teaches", 2 Department "offers", 3 Pfeile ohne Richtung entfernen. Text: "Relationship: fachlicher Zusammenhang". Notizen: "Wir benennen Beziehungen mit einem Verb. Die Linie ist keine Java-Referenz. Sie sagt, welche Datensätze fachlich zusammengehören."
```

#### Folie 02.7: 1:1, 1:n, n:m

```text
Reveal.js Prompt: Three-row SVG. Zeile 1 Student zu StudentCard 1:1, Zeile 2 Department zu Course 1:n, Zeile 3 Student zu Course n:m. Fragmente: Zeilen einzeln, Crow's-Foot-Enden amber. Notizen: "Cardinality (Kardinalität) zählt mögliche Partner. Wir lesen jede Beziehung in beide Richtungen. n:m wird später eine eigene Tabelle brauchen."
```

#### Folie 02.8: Optionality ist eine eigene Frage

```text
Reveal.js Prompt: Content slide with SVG. Obere Zeile: Department links und Lecturer rechts. An Department steht `1`, an Lecturer `0..n`. Untere Zeile: Lecturer links und Course rechts. An Lecturer steht `1`, an Course `0..n`. Jede Lecturer- und Course-Box trägt zusätzlich den blue Pflichttext "gehört genau einem". Fragmente: 1 Kreisenden an den `0..n`-Seiten amber, 2 Pflichtstriche an den beiden `1`-Seiten blue, 3 red Gegenfrage "Darf ein Course ohne Lecturer existieren? Im Schema: nein." Notizen: "Optionality (Optionalität) sagt, ob null erlaubt ist. Im Universitäts-Schema gehört jeder Lecturer genau einem Department und jeder Course genau einem Lecturer. Ein Department oder Lecturer darf noch keine abhängigen Datensätze haben."
```

#### Folie 02.9: Chen macht Rollen sichtbar

```text
Reveal.js Prompt: Content slide with precise Chen SVG. Rechteck Student, Oval student_number, Raute enrolls, Rechteck Course; blue Formen, amber Schlüsselunterstreichung. Fragmente: 1 Rechtecklabel Entity, 2 Oval Attribute, 3 Raute Relationship. Notizen: "Chen trennt die Bausteine deutlich. Das hilft beim ersten Lesen. Große Modelle werden damit schnell breit, deshalb wechseln wir danach die Schreibweise."
```

#### Folie 02.10: Dasselbe in Crow's Foot

```text
Reveal.js Prompt: Two-column content. Links Chen aus 02.9, rechts Crow's-Foot-Boxen Student und Course mit Enrollment dazwischen. Fragmente: 1 gleiche Elemente durch blue Linien verbinden, 2 rechte Darstellung amber. Text: "Andere Notation, gleiches Modell". Notizen: "Die Notation ändert nicht die Fachregel. Ab jetzt zeichnen wir Crow's Foot. Ihr sollt Chen weiterhin erkennen können."
```

#### Folie 02.11: Enrollment trägt eigene Daten

```text
Reveal.js Prompt: SVG Student 1:n Enrollment n:1 Course. Enrollment hat `id`, `student_id`, `course_id`, `enrolled_on`, `grade`. Fragmente: 1 Datum amber, 2 Note amber, 3 `id` als Primary Key und `(student_id, course_id)` als amber Candidate Key. Notizen: "Enrollment löst die n:m-Beziehung auf und trägt eigene Attribute. Das Datum gehört weder nur zum Student noch nur zum Course. Die Paarung bleibt mit UNIQUE geschützt, obwohl die Tabelle eine eigene ID hat."
```

#### Folie 02.12: Weak Entity

```text
Reveal.js Prompt: Content slide. Course blue, CourseSession amber mit partieller Identität `session_no`; Schlüssel `{course_id, session_no}` erscheint als Fragment. Text: "Weak Entity (schwache Entität): Identität braucht den Owner". Notizen: "Eine Sitzung Nummer 3 ist nur innerhalb eines Kurses eindeutig. Ihr Schlüssel enthält deshalb den Course. Weak heißt abhängig, nicht unwichtig."
```

#### Folie 02.13: Das Universitätsmodell

```text
Reveal.js Prompt: Full content SVG. Fachliche Entities `Student`, `Course`, `Lecturer`, `Department`, `Enrollment` als Crow's-Foot-Modell. Linien mit sichtbaren Mengen: Department `1` zu Lecturer `0..n`, Lecturer `1` zu Course `0..n`, Student `1` zu Enrollment `0..n`, Course `1` zu Enrollment `0..n`. Jede Lecturer-, Course- und Enrollment-Instanz gehört auf ihrer Gegenseite genau zu einer Instanz. Fragmente: 1 Kern Student, Course und Enrollment, 2 Lecturer, 3 Department, 4 alle Mengen amber. Notizen: "Dieses Modell bleibt unser Beispiel bis Freitag. Entity-Namen stehen hier im Singular. Die späteren SQL-Tabellen heißen `students`, `courses`, `lecturers`, `departments` und `enrollments`."
```

#### Folie 02.14: Was ER nicht ist

```text
Reveal.js Prompt: Two-column content. Links green "fachliche Entities, Beziehungen, Mengen, Identität". Rechts red durchgestrichen "keine Tabellen · kein Klassendiagramm · keine SQL-Typen · keine Indexe". Fragmente: rechte Punkte einzeln. Notizen: "Ein Rechteck kann später eine Tabelle werden, ist heute aber noch keine. Methoden und Vererbung gehören nicht hierher. Auch die Frage nach VARCHAR-Längen vertagen wir bewusst."
```

#### Folie 02.15: Häufiger Fehler, Beziehung als Attribut

```text
Reveal.js Prompt: SVG Student mit rotem Attribut `courses="DB, Web"`; Fragment 1 Duplikate und Parsing red, Fragment 2 Enrollment-Modell green. Text: "Listen im Attribut verstecken Beziehungen". Notizen: "Ein kommagetrennter String verliert Identität und Regeln. Wir könnten keinen Kurs sauber umbenennen und keine Note zuordnen. Eine Beziehung bleibt eine Beziehung."
```

#### Folie 02.16: Häufiger Fehler, falsche Seite

```text
Reveal.js Prompt: SVG Department und Course. Grundzustand falsche Crow's-Foot-Seite red. Fragment 1 Lesesatz "Ein Department hat viele Courses", Fragment 2 Korrektur green. Notizen: "Lest die Linie laut in beide Richtungen. Wenn der Satz und das Symbol nicht passen, korrigiert das Symbol. Viele Fehler verschwinden durch diese einfache Probe."
```

#### Folie 02.17: Häufiger Fehler, alles ist eine Entity

```text
Reveal.js Prompt: Content slide. Karten Email, Name, Student, Course, Farbe. Fragmente: Student/Course wandern zu Entities blue, Email/Name zu Attributes, Farbe verschwindet grau. Notizen: "Nicht jedes Nomen wird eine Entity. Fragt nach eigener Identität, Lebensdauer und Beziehungen. Eine Farbe der Oberfläche gehört gar nicht in dieses Modell."
```

#### Folie 02.18: Übung A1, euer ER-Modell

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "Übung A1", Timer "35 min". Dateipfad unten: `exercises/<domain>/a1-er-model/README.md`. Eingang: "Öffnet den Ausgang aus A0 und prüft die Hauptentität." Kern: "Zeichnet Entitäten, Attribute und Beziehungen" sowie "Ergänzt Schlüssel, Kardinalitäten und Optionalitäten." Vertiefung: "Prüft Beziehungsattribute und eine schwache Entität." Ausgang: "er.svg oder er.md plus Annahmen; jede Linie lässt sich in beide Richtungen vorlesen". Keine Fragmente. Notizen: "Nach 25 Minuten kann die Lehrperson ein vorbereitetes Zwischenmodell freigeben. Danach zeigen zwei Gruppen je eine Modellentscheidung."
```

#### Folie 02.19: Debrief, Linien laut lesen

```text
Reveal.js Prompt: Two-column content. Links Präsentationsrahmen "Entity · Beziehung · Cardinality · offene Regel", rechts Prüfscript "Ein X hat ... Y / Ein Y gehört zu ... X". Fragmente: 1 amber Frage "Wo trägt die Beziehung Daten?", 2 "Wo ist null erlaubt?". Notizen: "Zwei Gruppen zeigen ihr Modell. Das Plenum prüft nicht den Zeichenstil, sondern liest zwei Beziehungen laut. Wir halten eine offene Fachentscheidung fest."
```

#### Folie 02.20: Moodle speichert die Einschreibung

```text
Reveal.js Prompt: Layout citation-slide. Visual: StudentCard und CourseCard, Enrollment-Datensatz dazwischen mit Rolle, Gruppe, Status und Methode. Folientext: "Moodle zeigt Daten der Einschreibung zwischen Person und Course." Quelle: "https://docs.moodle.org/en/Participants". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Moodles Participants-Ansicht kann nach Einschreibemethode, Gruppe, Rolle und Status filtern. Diese Werte beschreiben die Verbindung von Person und Course. Im nächsten Deck wird Enrollment zur Tabelle `enrollments`."
```

### Übung

Übung A1, "ER-Diagramm für eure Domäne", 35 Minuten plus Debrief. Die verbindliche Karte liegt in `exercises/<domain>/a1-er-model/`. Die Gruppe zeichnet Entitäten, Attribute, Beziehungen, Schlüssel, Kardinalitäten und Optionalitäten. Beziehungsattribute und schwache Entitäten sind Vertiefung. Ausgang sind `er.svg` oder `er.md` und die Annahmen. Zwei Gruppen zeigen anschließend je eine Modellentscheidung. Debrief-Fragen: Welche Linie lässt sich nicht eindeutig laut lesen? Wo liegen Beziehungsattribute? Welche Optionalität stammt aus einer echten Regel? Exakter Folientext steht auf Folie 02.18.

## 03 Vom ER-Modell zu Tabellen (Dateiname decks/03-er-to-tables.html)

### Ziel

Die Studierenden übersetzen Crow's-Foot-Modelle in relationale Tabellen. Sie setzen Primary Keys, Foreign Keys (Fremdschlüssel), Join Tables, zusammengesetzte Keys, Typen und NULL bewusst ein. Vorher hatten wir ein fachliches Modell. Jetzt entsteht ein erster Schemaentwurf, den wir im nächsten Deck auf Widersprüche und Wiederholungen prüfen.

### Position auf der roten Linie

Vorher: `Darüber sprechen: Abstraktion und ER-Modell`. Aktuell: `Tabellen, die uns nicht anlügen: Normalisierung`. Als Nächstes: `Den Tabellen Fragen stellen: SQL`.

### Erzählung

Jetzt muss jede Linie landen. Eine Entity wird meist eine Tabelle. Ein 1:n landet als Foreign Key auf der n-Seite. Wer ihn auf die 1-Seite legt, müsste dort eine Liste speichern und wäre wieder beim kommagetrennten Kursattribut.

Die n:m-Beziehung zwischen `students` und `courses` wird `enrollments`. Das ist keine technische Hilfstabelle, die wir verstecken sollten. Sie trägt `enrolled_on` und `grade` und hat damit fachliches Gewicht.

Datentypen sind die erste technische Festlegung. SQLite ist tolerant, unsere Regeln sollten es nicht sein. Daten schreiben wir als ISO-Text `YYYY-MM-DD`. NULL verwenden wir nur, wenn die Fachregel "noch unbekannt oder nicht vorhanden" wirklich erlaubt.

### Leitfragen

- Frage: "In welche Tabelle kommt `department_id` bei Department 1:n Lecturer?" Erwartet: In `lecturers`, die n-Seite. Typisch falsch: In `departments`; in beide Tabellen.
- Frage: "Bedeutet NULL eine leere Zeichenkette?" Erwartet: Nein, NULL bedeutet fehlend oder unbekannt. Typisch falsch: Ja; NULL ist die Zahl 0.

### Realitätsbezug

SQLite erzwingt Foreign Keys nur, wenn die Verbindung `PRAGMA foreign_keys = ON` aktiviert. Das zeigt, dass ein gezeichneter Pfeil allein noch keine laufende Regel ist.

### Folien

#### Folie 03.1: Vom ER-Modell zu Tabellen

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Jede Box und jede Linie bekommt einen Platz". Visual: Crow's-Foot-Modell links, SQL-Tabellen rechts, amber Pfeil. Notizen: "Heute wird das Modell technisch. Wir übersetzen systematisch, statt Tabellen nach Gefühl zu erfinden."
```

#### Folie 03.2: Der erste Schemaentwurf

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: erledigt `Idee im Kopf` und `Darüber sprechen: Abstraktion und ER-Modell` in blue; aktiv `Tabellen, die uns nicht anlügen: Normalisierung` in amber; als Nächstes `Den Tabellen Fragen stellen: SQL` mit gestricheltem blue Rahmen; Schritte 5 bis 8 grau. Text unten: "Vorher: ER-Modell · Jetzt: Tabellenentwurf · Danach: SQL". Keine Fragmente. Notizen: "Wir betreten Schritt drei mit einem ersten Entwurf. Normalisierung prüft ihn gleich. Morgen stellt SQL diesen Tabellen Fragen."
```

#### Folie 03.3: Wo landet die 1:n-Linie?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Department 1:n Lecturer, wo liegt `department_id`?" Fragment 1 "auf der n-Seite: lecturers" amber. Notizen: "Erwartet ist `lecturers.department_id`. Typisch falsch sind eine Lecturer-Liste in departments und zwei gegenseitige Foreign Keys. Ein Lecturer gehört genau einem Department, deshalb trägt er dessen Key."
```

#### Folie 03.4: Entity wird Tabelle

```text
Reveal.js Prompt: Two-column SVG. Student-Entity links; rechts Tabelle `students(id, student_number, first_name, last_name, email, enrollment_date)`. Fragmente: Box wird Tabelle, Attribute werden Spalten, `id` wird amber PK. Notizen: "Die Regel ist ein Startpunkt, kein Automatismus. Namen werden plural und snake_case. Identität und Attribute bleiben erhalten."
```

#### Folie 03.5: 1:n wird Foreign Key

```text
Reveal.js Prompt: SVG `departments(id, name, code)` über `lecturers(id, first_name, last_name, email, department_id)`. Fragment 1 blue Pfeil von `lecturers.department_id` zu `departments.id`, Fragment 2 amber `NOT NULL`. Notizen: "Der Foreign Key liegt auf der n-Seite. `NOT NULL` setzt die Regel um, dass jeder Lecturer einem Department gehört. Ohne Constraint wäre die Linie nur Dokumentation."
```

#### Folie 03.6: n:m wird Join Table

```text
Reveal.js Prompt: SVG students und courses, Fragment 1 `enrollments` dazwischen, Fragment 2 zwei Foreign Keys, Fragment 3 `enrolled_on`, `grade`, Fragment 4 `id` plus `UNIQUE(student_id, course_id)`. Text: "Join Table (Verknüpfungstabelle) mit Fachlichkeit". Notizen: "Die Join Table zerlegt n:m in zwei 1:n-Beziehungen. Eigene Attribute landen dort. Die eigene ID und der UNIQUE-Constraint schützen zwei verschiedene Formen von Identität."
```

#### Folie 03.7: Weak Entity wird zusammengesetzter Key

```text
Reveal.js Prompt: Content slide. Sichtbarer Hinweis oben: "Didaktisches Beispiel, nicht im Universitäts-Schema". Tabelle `course_sessions(course_id, session_no, starts_at, room)`; Fragment 1 amber Klammer um `(course_id, session_no)`, Fragment 2 Foreign-Key-Pfeil von `course_id` zu einer kleinen blue Karte `courses.id`. Notizen: "Die Sitzungsnummer ist nur im Course eindeutig. Der zusammengesetzte Primary Key bewahrt diese Fachregel. Das Repository enthält keine Tabelle `course_sessions`."
```

#### Folie 03.8: Typen und NULL sind Fachentscheidungen

```text
Reveal.js Prompt: Two-column content. Links blue `TEXT`, `INTEGER`, ISO-Datum als `TEXT`; rechts amber Fragen "kann fehlen?", "muss eindeutig sein?". Fragment 1 `grade TEXT NULL`, Fragment 2 `email TEXT NOT NULL UNIQUE`. Notizen: "SQLite speichert Datumswerte oft als Text. Wir legen das Format fest. NULL erlauben wir bei einer noch nicht vergebenen Note, nicht aus Bequemlichkeit."
```

#### Folie 03.9: Erster Blick auf CREATE TABLE

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/schema.sql`, Zeilen 31 bis 38, vollständig und verbatim. Code: `CREATE TABLE IF NOT EXISTS courses (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    course_code TEXT NOT NULL UNIQUE,\n    title TEXT NOT NULL,\n    credits INTEGER NOT NULL CHECK (credits > 0),\n    lecturer_id INTEGER NOT NULL,\n    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)\n);`. Zeilen-Highlights 1|2-5|6-7. Notizen: "Der Tabellenkopf stammt aus der Entity. Constraints setzen Keys und Pflichtfelder um. Der Foreign Key ist die frühere 1:n-Linie."
```

#### Folie 03.10: Übung A2, ER-Modell in Relationen übersetzen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "Übung A2", Timer "25 min". Arbeitsstand unten: `exercises/<domain>/a2-relational-model/README.md`. Eingang: "Öffnet euer geprüftes ER-Modell aus A1." Kern: "Schreibt Tabellen mit Primär- und Fremdschlüsseln" und "Löst n:m auf und ordnet Beziehungsattribute zu." Vertiefung: "Begründet jede NULL-Spalte und ergänzt UNIQUE-Regeln." Ausgang: "relational-model.md mit Tabellen, Schlüsseln und begründeten NULL-Spalten". Keine Fragmente. Notizen: "Nach 15 Minuten kann die Lehrperson eine vorbereitete Tabellenliste freigeben. Noch ist kein SQL nötig."
```

#### Folie 03.11: SQLite braucht den Schalter

```text
Reveal.js Prompt: Layout citation-slide. Visual: Foreign-Key-Pfeil zunächst grau, Schalter `PRAGMA foreign_keys = ON` amber, danach green Schloss. Folientext: "SQLite prüft Foreign Keys pro Verbindung nur bei aktivierter Option." Quelle: "https://sqlite.org/foreignkeys.html". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Ein Constraint im Schema reicht bei SQLite nicht ohne die Verbindungsoption. Das ist ein gutes Beispiel für den Abstand zwischen Diagramm und laufendem System. Als Nächstes prüfen wir, ob unser Schema Wiederholungen und Anomalien enthält."
```

### Übung

Übung A2, "ER-Modell in Relationen übersetzen", 25 Minuten. Die Gruppe arbeitet in `exercises/<domain>/a2-relational-model/`. Sie schreibt für jede Entität eine Tabelle, legt Fremdschlüssel auf die n-Seite und baut Zwischentabellen für n:m. Ausgang ist `relational-model.md`. Debrief-Fragen: Welche ER-Linie wurde zu welchem Fremdschlüssel? Welche Zwischentabelle trägt eigene Attribute? Wo ist NULL erlaubt und warum? Exakter Folientext steht auf Folie 03.10.

## 04 Normalisierung (Dateiname decks/04-normalization.html)

### Ziel

Die Studierenden erkennen Einfüge-, Änderungs- und Löschanomalien an einer flachen Enrollment-Tabelle. Sie leiten Functional Dependencies (funktionale Abhängigkeiten) ab und prüfen 1NF, 2NF, 3NF und BCNF. Der erste Tabellenentwurf liegt vor. Jetzt machen wir daraus Tabellen, die uns nicht anlügen. Danach können wir am zweiten Tag mit SQL verlässlich fragen.

### Position auf der roten Linie

Vorher: `Darüber sprechen: Abstraktion und ER-Modell`. Aktuell: `Tabellen, die uns nicht anlügen: Normalisierung`. Als Nächstes: `Den Tabellen Fragen stellen: SQL`.

### Erzählung

Ich starte nicht mit Normalformen. Ich starte mit einer Tabelle, die weh tut. In `enrollments_flat` stehen Student, Course, Lecturer und Department in jeder Zeile. Ein Kurs mit 80 Belegungen wiederholt den Namen der Lehrperson 80-mal.

Wenn der Lecturer den Namen ändert, muss jemand 80 Zeilen treffen. Trifft das Update 79, hat die Datenbank zwei Wahrheiten. Wenn der letzte Student einen Course verlässt, verschwindet vielleicht auch die einzige Information über diesen Course. Das sind keine Schönheitsfehler.

Functional Dependencies schreiben die Regeln knapp: `student_id -> student_number, first_name, last_name, email`. Der Candidate Key `(student_id, course_id)` bestimmt `enrolled_on` und `grade`. Danach sind die Normalformen nur noch Prüfungen gegen diese Regeln.

3NF reicht in vielen Anwendungssystemen. BCNF findet noch Fälle, in denen ein Determinant kein Superschlüssel ist. Ich normalisiere nicht aus Sport. Ich stoppe, wenn die wichtigen Anomalien weg sind, Constraints klar sind und gemessene Abfragen noch vernünftig laufen.

### Leitfragen

- Frage: "Was passiert, wenn Anna Weber in einer angenommenen flachen Tabelle in 80 Zeilen zu Weber-Stein wird?" Erwartet: Ein Update muss alle Zeilen treffen, sonst entsteht eine Update-Anomalie. Typisch falsch: SQL aktualisiert automatisch ähnliche Werte; das ist nur langsam.
- Frage: "Bestimmt `student_id` die Note?" Erwartet: Nein, erst `(student_id, course_id)` bestimmt die Note. Typisch falsch: Ja, jeder Student hat eine Note; `course_id` allein reicht.
- Frage: "Muss jede Produktionsdatenbank BCNF haben?" Erwartet: Nein, bewusste Denormalisierung kann für gemessene Lesewege sinnvoll sein. Typisch falsch: Ja, sonst ist das Schema falsch; nein, Normalisierung ist nur Theorie.

### Realitätsbezug

Google BigQuery empfiehlt für häufig gemeinsam abgefragte hierarchische Daten nested und repeated fields. Die dokumentierte Denormalisierung soll Joins verringern und Query Performance verbessern.

### Folien

#### Folie 04.1: Normalisierung

```text
Reveal.js Prompt: Layout chapter-slide. Titel "Normalisierung", Untertitel "Tabellen, die uns nicht anlügen". Visual: rote flache Tabelle zerfällt in fünf blue Tabellen mit green Verbindungen. Notizen: "Wir beginnen mit Fehlern, nicht mit Definitionen. Jede Normalform bekommt erst dann einen Namen, wenn ihr Problem sichtbar ist."
```

#### Folie 04.2: Wir prüfen den Schemaentwurf

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: erledigt `Idee im Kopf` und `Darüber sprechen: Abstraktion und ER-Modell` in blue; aktiv `Tabellen, die uns nicht anlügen: Normalisierung` in amber; als Nächstes `Den Tabellen Fragen stellen: SQL` mit gestricheltem blue Rahmen; Schritte 5 bis 8 grau. Text unten: "Vorher: Tabellenentwurf · Jetzt: Anomalien entfernen · Danach: SQL". Keine Fragmente. Notizen: "Der Entwurf aus dem ER-Modell ist unser Ausgangspunkt. Normalisierung prüft, ob eine Tatsache an genau dem passenden Ort liegt. Morgen verlassen wir uns bei jeder Abfrage darauf."
```

#### Folie 04.3: Eine Tabelle für alles?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Was kostet die Umbenennung eines Lecturers bei 80 Enrollments?" Fragment 1 "80 Updates, 80 Chancen auf Widerspruch" amber. Notizen: "Erwartet ist, dass alle 80 Zeilen geändert werden müssen. Typisch falsch sind ein Update an einer zentralen Stelle und 'die Datenbank erkennt gleiche Namen'. Jetzt zeige ich die flache Tabelle."
```

#### Folie 04.4: enrollments_flat

```text
Reveal.js Prompt: Content slide with spreadsheet SVG. Quellenhinweis unten: "Abgeleitet aus `java/sql/university/seed.sql`, Zeilen 5 bis 15, 46 bis 53 und 62 bis 71." Spalten exakt `student_id`, `student_name`, `course_id`, `course_code`, `course_title`, `lecturer_id`, `lecturer_name`, `department_name`, `enrolled_on`, `grade`. Sechs Zeilen exakt: `1 | Lena Hoffmann | 1 | INF-201 | Datenbanken | 1 | Anna Weber | Informatik | 2024-04-08 | 1.7`; `2 | Jonas Becker | 1 | INF-201 | Datenbanken | 1 | Anna Weber | Informatik | 2024-04-08 | 2.3`; `3 | Aylin Kaya | 1 | INF-201 | Datenbanken | 1 | Anna Weber | Informatik | 2024-04-08 | 1.3`; `1 | Lena Hoffmann | 2 | INF-202 | Programmierung II | 2 | Mehmet Yilmaz | Informatik | 2024-10-07 | 2.0`; `3 | Aylin Kaya | 2 | INF-202 | Programmierung II | 2 | Mehmet Yilmaz | Informatik | 2024-10-07 | 1.7`; `4 | Paul Schneider | 2 | INF-202 | Programmierung II | 2 | Mehmet Yilmaz | Informatik | 2024-10-07 | 2.7`. Fragmente: 1 alle wiederholten Course-Zellen amber, 2 alle wiederholten Lecturer-Zellen amber, 3 alle wiederholten Department-Zellen amber. Notizen: "Die sechs Zeilen sind aus dem Seed abgeleitet. Die flache Tabelle existiert nicht im Repository. Sie zeigt, welche Wiederholungen eine unnormalisierte Ablage erzeugen würde."
```

#### Folie 04.5: Update-Anomalie

```text
Reveal.js Prompt: Spreadsheet-Ausschnitt mit den ersten drei `INF-201`-Zeilen aus Folie 04.4. Grundzustand: `lecturer_id = 1`, `lecturer_name = Anna Weber` dreimal. Fragment 1 ändert in den ersten zwei Zeilen den Namen auf `Anna Weber-Stein` amber. Fragment 2 lässt die dritte Zeile mit `Anna Weber` red stehen. Fragment 3 zeigt die exakte Frage "Welcher Name gilt für lecturer_id 1?". Hinweis unten: "Hypothetische Namensänderung auf Seed-Daten". Notizen: "Ein ausgelassenes Update erzeugt zwei Namen für dieselbe ID. Die normalisierte Tabelle `lecturers` speichert den Namen nur einmal."
```

#### Folie 04.6: Einfügeanomalie

```text
Reveal.js Prompt: Leere neue flache Zeile für den ausdrücklich hypothetischen Course `INF-260`, Titel `Datenvisualisierung`, Lecturer `Jana Vogel`, Department `Informatik`. Die Student- und Enrollment-Zellen bleiben leer. Fragment 1 `student_id`, `student_name` und `enrolled_on` leuchten red, Fragment 2 die ganze Zeile erhält red Kreuz. `grade` bleibt grau, weil eine noch unbekannte Note fehlen darf. Sichtbarer Hinweis: "Hypothetischer neuer Course, nicht in seed.sql". Text unten: "Course ohne Enrollment nicht speicherbar". Notizen: "Ein neuer Course existiert fachlich vor der ersten Belegung. Die flache Tabelle zwingt uns zu erfundenen Student-Daten oder verhindert das Insert."
```

#### Folie 04.7: Löschanomalie

```text
Reveal.js Prompt: Eine einzige hypothetische flache Zeile für `INF-260`, `Datenvisualisierung`, `Jana Vogel` und eine Belegung von Lena Hoffmann. Sichtbarer Hinweis: "Fortsetzung des hypothetischen Beispiels aus 04.6". Fragment 1 die Belegungszellen werden rot gestrichen, Fragment 2 Course- und Lecturer-Zellen verblassen zugleich, Fragment 3 red Text "Course-Information unbeabsichtigt gelöscht". Notizen: "Wir wollten eine Belegung löschen. In der flachen Tabelle verlieren wir zugleich den einzigen Datensatz über diesen Course."
```

#### Folie 04.8: Welche Spalte bestimmt welche?

```text
Reveal.js Prompt: Content slide. Grundzustand `student_id`. Fragmente: Pfeile zu `student_number`, `first_name`, `last_name`, `email`; dann `course_id -> course_code, title, credits, lecturer_id`; dann `lecturer_id -> lecturer_name, department_id`. Text: "Functional Dependency: X → Y". Notizen: "X bestimmt Y, wenn zu einem X-Wert genau ein Y-Wert gehört. Das ist eine Fachregel, keine zufällige Beobachtung in sechs Zeilen."
```

#### Folie 04.9: Funktionale Abhängigkeit, formal

```text
Reveal.js Prompt: Content slide mit Titel "Funktionale Abhängigkeit, formal". Oben eine breite blue Definitionsbox mit dem exakten Text "X → Y gilt genau dann, wenn für alle Tupel t1, t2: t1[X] = t2[X] ⇒ t1[Y] = t2[Y]" und der amber Lesart "gleicher X-Wert erzwingt gleichen Y-Wert". Darunter zwei Tabellenkarten nebeneinander. Links stehen die Zeilen `t1 | Lena Hoffmann | lecturer_id 1 | Anna Weber` und `t2 | Jonas Becker | lecturer_id 1 | Anna Weber` unter der Prüfung `lecturer_id → lecturer_name`. Fragment 1 markiert beide `lecturer_id`-Zellen amber und zeigt `t1[X] = t2[X]: 1 = 1`. Fragment 2 markiert beide `lecturer_name`-Zellen amber und zeigt green `⇒ t1[Y] = t2[Y]: Anna Weber = Anna Weber` sowie `GILT ALS FACHREGEL`. Rechts erscheint als Fragment 3 eine red Gegenbeispielkarte `student_id ↛ grade` mit den Zeilen `t1 | Lena Hoffmann | INF-201 | student_id 1 | grade 1.7` und `t2 | Lena Hoffmann | INF-202 | student_id 1 | grade 2.0`; beide `student_id`-Zellen sind red markiert und darunter steht `t1[X] = t2[X]: 1 = 1`. Fragment 4 markiert beide `grade`-Zellen red und zeigt `t1[Y] ≠ t2[Y]: 1.7 ≠ 2.0` sowie `IMPLIKATION FALSCH`. Notizen: "Eine funktionale Abhängigkeit ist eine Regel der Domäne. Die beiden Lecturer-Zeilen machen die Regel konkret, beweisen sie aber nicht. Sechs Zeilen können eine funktionale Abhängigkeit widerlegen, niemals beweisen."
```

#### Folie 04.10: Schlüsselbegriffe

```text
Reveal.js Prompt: Content slide mit Titel "Schlüsselbegriffe". Vier gleich große blue Boxen in einem 2x2-Raster, jede Box erscheint als eigenes Fragment. Fragment 1 `SUPERSCHLÜSSEL`: Definition "Attributmenge K mit K → alle Attribute." und Beispiel `(student_id, course_id, enrolled_on) ist Superschlüssel, aber nicht minimal.` Fragment 2 `KANDIDATENSCHLÜSSEL`: Definition "Minimaler Superschlüssel: kein Attribut ist entfernbar." und Beispiel `(student_id, course_id) ist Kandidatenschlüssel.` Fragment 3 `SCHLÜSSELATTRIBUT / NICHTSCHLÜSSELATTRIBUT`: Definition "kommt in mindestens einem / keinem Kandidatenschlüssel vor." und Beispiele `student_id, course_id: Schlüsselattribute` sowie red `grade: Nichtschlüsselattribut`. Fragment 4 `VOLLE / PARTIELLE ABHÄNGIGKEIT`: Definition "Y hängt voll von K ab, wenn keine echte Teilmenge von K bereits Y bestimmt." und red Beispiel `student_id → student_name ⇒ partiell abhängig vom Kandidatenschlüssel`. Notizen: "Diese vier Begriffe tragen die Definitionen von 2NF und 3NF. Minimal bedeutet, dass wirklich kein Attribut aus dem Superschlüssel entfernt werden kann. Bei einem zusammengesetzten Kandidatenschlüssel suchen wir gezielt nach Abhängigkeiten von seinen echten Teilmengen."
```

#### Folie 04.11: Der Schlüssel der Belegung

```text
Reveal.js Prompt: SVG `(student_id, course_id)` als amber Candidate-Key-Doppelkarte. Fragmente: 1 Pfeil zu `enrolled_on`, 2 Pfeil zu `grade`, 3 einzelne `student_id` und `course_id` erhalten red Kreuz. Notizen: "Die Note gehört zur Paarung. Weder Student noch Course allein bestimmen sie. Für die 2NF-Prüfung zählt dieser zusammengesetzte Candidate Key, auch wenn `enrollments.id` der Primary Key ist."
```

#### Folie 04.12: 1NF, die Definition

```text
Reveal.js Prompt: Content slide mit prominentem blue Definitionskasten. Exakter Definitionstext: "Eine Relation ist in 1NF, wenn jedes Attribut in jeder Zeile genau EINEN atomaren Wert trägt: keine Listen, keine Wiederholungsgruppen, keine geschachtelten Tabellen." Das Wort "EINEN" ist amber, die drei verbotenen Formen sind red. Fragmente: 1 red Karte "1 · LISTE IN EINER ZELLE" mit `course_codes = "INF-201,INF-230"`; 2 red Karte "2 · WIEDERHOLUNGSGRUPPE" mit `course_code_1`, `course_code_2`, `course_code_3`; 3 red Karte "3 · GESCHACHTELTE TABELLE" mit einer kleinen Tabelle in einer Zelle und dem Text "EINE ZELLE ENTHÄLT ZEILEN". Notizen: "Atomar heißt: für unseren Zweck unteilbar. Eine Zelle enthält deshalb weder eine Liste noch eine Wiederholungsgruppe oder eine weitere Tabelle. Ein ISO-Datum ist trotzdem ein Wert, obwohl es aus Jahr, Monat und Tag besteht."
```

#### Folie 04.13: 1NF verletzt, die Prüfung

```text
Reveal.js Prompt: Content slide mit Tabelle `students_flat(student_id, student_name, course_codes)`. Zwei Zeilen exakt: `1 | Lena Hoffmann | "INF-201,INF-230"` und `2 | Jonas Becker | "INF-201"`. Fragment 1 zeigt die amber Prüffrage "Trägt jede Zelle genau einen Wert?". Fragment 2 markiert `"INF-201,INF-230"` red und zeigt "zwei Werte in einer Zelle, 1NF verletzt". Fragment 3 zeigt in einem red Kasten die exakte Zeile `WHERE course_code = 'INF-230' findet die Zeile nur mit String-Tricks (LIKE '%INF-230%').`. Notizen: "Wir prüfen die Definition Zelle für Zelle. Die erste problematische Zelle enthält zwei Course-Codes und verletzt damit die 1NF. Der Verstoß fällt durch die Prüffrage auf, nicht durch Intuition."
```

#### Folie 04.14: 1NF erfüllt

```text
Reveal.js Prompt: Content slide mit der Grundfrage "Trägt jede Zelle genau einen Wert?". Fragment 1 zeigt die blue Tabelle `enrollments(student_id, course_code)` mit den drei Zeilen exakt `1 | INF-201`, `1 | INF-230`, `2 | INF-201`. Fragment 2 zeigt mit green Text und green Rahmen "jede Zelle genau ein Wert ✓" sowie `WHERE course_code = 'INF-230' funktioniert ohne Tricks.`. Notizen: "Jede Belegung steht jetzt in einer eigenen Zeile. Damit besteht jede Zelle die Prüffrage, und die Datenbank kann direkt nach `course_code` filtern. 1NF ist die Eintrittskarte. Erst jetzt lassen sich funktionale Abhängigkeiten über Spalten sauber definieren."
```

#### Folie 04.15: 2NF, die Definition

```text
Reveal.js Prompt: Content slide. Oben eine große amber Definitionsbox. Fragment 1 zeigt die erste Bedingung: "Eine Relation ist in 2NF, wenn sie in 1NF ist". Fragment 2 ergänzt die zweite Bedingung: "UND jedes Nichtschlüsselattribut von JEDEM Kandidatenschlüssel voll funktional abhängig ist (keine partielle Abhängigkeit)." Darunter drei Prüfkarten. Fragment 3 blue Frage 1: "Was sind die Kandidatenschlüssel?" Fragment 4 blue Frage 2: "Welche Attribute sind Nichtschlüsselattribute?" Fragment 5 red Frage 3: "Hängt eines davon schon von einem ECHTEN Teil eines Schlüssels ab?" Fragment 6 zeigt green den Kontext: "Partielle Abhängigkeiten gibt es nur bei zusammengesetzten Schlüsseln. Sind alle Kandidatenschlüssel einspaltig, ist 2NF automatisch erfüllt." Notizen: "2NF ist eine Aussage über Teile zusammengesetzter Schlüssel. Wir bestimmen zuerst alle Kandidatenschlüssel und prüfen danach die Nichtschlüsselattribute. Sind alle Kandidatenschlüssel einspaltig, gibt es keinen echten Teil, der eine partielle Abhängigkeit erzeugen könnte."
```

#### Folie 04.16: 2NF verletzt, die Prüfung

```text
Reveal.js Prompt: Content slide zur schrittweisen Prüfung von `enrollments_flat`, oben links steht "RELATION · enrollments_flat". Die visuelle Grundidee des bisherigen 2NF-Slides bleibt: oben der zusammengesetzte Schlüssel, darunter werden verletzende Attribute red herausgezogen. Fragment 1 zeigt amber "Schritt 1 · Kandidatenschlüssel" mit `(student_id, course_id)`. Fragment 2 zeigt blue "Schritt 2 · Nichtschlüsselattribute" und die vollständige Liste `student_name · course_code · course_title · lecturer_id · lecturer_name · department_name · enrolled_on · grade`. Fragment 3 zeigt links red "Schritt 3a · partiell", die FD `student_id → student_name` und den Text "nur Teilschlüssel student_id → 2NF verletzt". Fragment 4 zeigt rechts red "Schritt 3b · gleiche Verletzung", die FD `course_id → course_title` und den Text "nur Teilschlüssel course_id → 2NF verletzt". Fragment 5 kontrastiert green `(student_id, course_id) → grade` mit "voll abhängig · in Ordnung ✓". Notizen: "`student_name` und `course_title` hängen jeweils schon von einem Teil des Kandidatenschlüssels ab. Das verletzt 2NF. `grade` und `enrolled_on` bleiben bei der Paarung, weil erst Student und Course zusammen diese Werte bestimmen."
```

#### Folie 04.17: 2NF erfüllt

```text
Reveal.js Prompt: Content slide als positives Beispiel nach der Zerlegung. Fragment 1 zeigt drei blue Tabellen nebeneinander: `students(student_id, student_name)`, `courses(course_id, course_title, ...)`, `enrollments(student_id, course_id, enrolled_on, grade)`. Die Schlüssel sind amber markiert, bei `enrollments` ist der Schlüssel `(student_id, course_id)` zusammengesetzt. Fragment 2 prüft `enrollments` erneut: "Jedes Nichtschlüsselattribut (enrolled_on, grade) hängt voll vom ganzen Schlüssel ab." und green "keine partielle Abhängigkeit ✓". Fragment 3 zeigt green: "In students und courses ist der Schlüssel einspaltig. 2NF ist dort automatisch erfüllt." Notizen: "Die Fakten bleiben dieselben, bekommen aber neue Tabellen. Die Paarungstabelle behält nur Fakten über die Paarung. `student_name` liegt bei `students`, `course_title` bei `courses`."
```

#### Folie 04.18: 3NF, Intuition und Prüfbedingung

```text
Reveal.js Prompt: Content slide mit prominentem amber Kasten, beschriftet als "INTUITION". Fragment 1 zeigt: "Eine Relation ist in 3NF, wenn sie in 2NF ist UND kein Nichtschlüsselattribut transitiv von einem Kandidatenschlüssel abhängt." Fragment 2 erläutert transitiv in einem blue Kasten: "K → B und B → C", "B ist ein Nichtschlüsselattribut" und "C hängt transitiv von K ab". Fragment 3 ergänzt in einem red Kasten die Bedingung "B → K gilt nicht zurück" und "B ist kein Schlüssel". Fragment 4 zeigt die "FORMALE PRÜFBEDINGUNG" in einem amber Kasten: "Für jede nichttriviale FD X → A gilt: X ist Superschlüssel ODER A ist Schlüsselattribut. Sonst: 3NF verletzt." Fragment 5 zeigt green: "2NF verbietet Abkürzungen über Teilschlüssel. 3NF verbietet Umleitungen über Nichtschlüsselattribute." Notizen: "Die obere Box gibt die übliche Intuition. Die vollständige formale Prüfbedingung steht unten."
```

#### Folie 04.19: 3NF verletzt, die Prüfung

```text
Reveal.js Prompt: Content slide als Ersatz für "3NF, keine Umleitung". Sichtbar ist die Signatur `courses_flat(course_id, title, lecturer_id, lecturer_name, department_id, department_name)`. Fragment 1 zeigt die Dependency Chain `course_id → lecturer_id → department_id → department_name` als amber Schlüsselkarte und blue Karten mit Pfeilen sowie den Text "Jeder Pfeil ist eine funktionale Abhängigkeit." Fragment 2 prüft `lecturer_id → lecturer_name` in einem red Kasten: "lecturer_id ist kein Superschlüssel von courses_flat, lecturer_name ist kein Schlüsselattribut → 3NF verletzt." Fragment 3 prüft entsprechend `department_id → department_name`: "department_id ist kein Superschlüssel, department_name ist kein Schlüsselattribut → 3NF verletzt." Fragment 4 zeigt amber: "Fachbereich umbenennen = eine Änderung pro Kurs. Die Änderungsanomalie kehrt zurück." Notizen: "Die Prüfregel ist mechanisch anwendbar. Wir prüfen jede nichttriviale FD einzeln und markieren beide Bedingungen, wenn sie scheitern. Genau so sollen die Studierenden in der Übung vorgehen."
```

#### Folie 04.20: 3NF erfüllt

```text
Reveal.js Prompt: Content slide. Fragment 1 zeigt drei blue Tabellen: `courses(course_id, title, lecturer_id)`, `lecturers(lecturer_id, lecturer_name, department_id)` und `departments(department_id, department_name)`. Schlüssel sind amber und unterstrichen, Fremdschlüssel blue; blue Pfeile verbinden `courses.lecturer_id` mit `lecturers.lecturer_id` und `lecturers.department_id` mit `departments.department_id`. Fragment 2 prüft die FDs jeder Tabelle: `course_id → title, lecturer_id`, `lecturer_id → lecturer_name, department_id`, `department_id → department_name`. Green Ergebnis: "Linke Seite jeder FD = ganzer Schlüssel. Keine Umleitung ✓". Fragment 3 zeigt green: "Umbenennung eines Departments ist wieder genau ein Update." Notizen: "Die Zerlegung entspricht der Struktur in `java/sql/university/schema.sql`. In jeder Tabelle bestimmt der ganze Schlüssel die übrigen Attribute. Ein Department-Name steht damit nur noch an einer Stelle."
```

#### Folie 04.21: Die Prüfliste der Normalformen

```text
Reveal.js Prompt: Content slide direkt vor "Das Schema nach 3NF". Ein kompakter Überblick hat die Spalten "NORMALFORM", "PRÜFFRAGE" und "VERBOTENES MUSTER". Fragment 1 zeigt die 1NF-Zeile: "Trägt jede Zelle genau einen Wert?" und red "Listen und Wiederholungsgruppen". Fragment 2 zeigt die 2NF-Zeile: "Hängt ein Nichtschlüsselattribut nur von einem Teil eines Schlüssels ab?" und red "partielle Abhängigkeit". Fragment 3 zeigt die 3NF-Zeile: "Hängt ein Nichtschlüsselattribut über ein anderes Nichtschlüsselattribut ab?" und red "transitive Abhängigkeit". Fragment 4 zeigt in einem amber Kasten: "Diese drei Fragen sind eure Übungsanleitung." Notizen: "Diese Prüfliste ist das Werkzeug für Übung A3. Die Fragen sind kurz genug für die Arbeit am Schema. Die formalen Definitionen machen präzise, was mit Teil, Umleitung und Abhängigkeit gemeint ist."
```

#### Folie 04.22: Das Schema nach 3NF

```text
Reveal.js Prompt: Full SVG mit den fünf Tabellen und exakten Spalten aus `java/sql/university/schema.sql`: `departments(id,name,code)`, `lecturers(id,first_name,last_name,email,department_id)`, `students(id,first_name,last_name,email,student_number,enrollment_date)`, `courses(id,course_code,title,credits,lecturer_id)`, `enrollments(id,student_id,course_id,grade,enrolled_on)`. Primary Keys amber unterstrichen, Foreign Keys blue mit Pfeil zur Zielspalte. Fragmente: 1 departments und lecturers, 2 courses, 3 students und enrollments, 4 alle Foreign-Key-Pfeile, 5 green Klammer "jede Tatsache an einem Ort". Notizen: "Das ist exakt das Schema aus `java/sql/university/schema.sql`. Enrollment behält `enrolled_on` und `grade`. Das Einschreibedatum am Student heißt `enrollment_date`."
```

#### Folie 04.23: BCNF prüft jeden Determinanten

```text
Reveal.js Prompt: Content slide. Beispiel `course_room_timeslot(course_id, room_id, timeslot)` mit Regeln `(course_id, timeslot) -> room_id` und `room_id -> course_id` für exklusiv reservierte Räume. Fragmente: zweite FD amber, Determinant `room_id` ist kein Superschlüssel red, Zerlegung blue. Notizen: "BCNF verschärft 3NF: Jeder Determinant muss ein Superschlüssel sein. Ein Superschlüssel muss nicht minimal sein."
```

#### Folie 04.24: 3NF oder BCNF?

```text
Reveal.js Prompt: Two-column content. Links "3NF: praktische Standardprüfung", rechts "BCNF: prüft restliche Determinanten". Fragmente: 1 green "Anomalien weg", 2 amber "Abhängigkeitserhalt prüfen". Notizen: "BCNF wird manchmal 3.5NF genannt. Eine Zerlegung kann Abhängigkeiten schwerer erzwingbar machen. Für eure Übung ist 3NF das Pflichtziel, BCNF die Kontrollfrage."
```

#### Folie 04.25: Wann reale Systeme stoppen

```text
Reveal.js Prompt: Balance SVG. Links blue "Korrektheit, klare Schreibwege, Constraints", rechts amber "gemessene Read-Latenz, Bericht, Cache". Fragment 1 green Linie bei 3NF, Fragment 2 bewusste Denormalisierung als amber Kopie mit Label. Notizen: "Wir stoppen nicht bei einer Nummer, sondern bei einem begründeten Schema. Denormalisierung folgt einer Messung und bekommt eine Aktualisierungsregel. Zufällige Duplikate sind keine Optimierung."
```

#### Folie 04.26: Übung A3, die flache Tabelle zerlegen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "Übung A3", Timer "35 min". Dateipfad unten: `exercises/<domain>/a3-normalization/README.md`. Eingang: "Öffnet flache Tabelle und Prüffragen der Gruppe." Kern: "Bestimmt Kandidatenschlüssel und Abhängigkeiten" sowie "Zerlegt bis 3NF und begründet jeden Schnitt." Vertiefung: "Prüft die BCNF-Falle und gleicht mit dem ER-Modell ab." Ausgang: "normalization.md mit Abhängigkeiten, 3NF-Tabellen und Begründung". Keine Fragmente. Notizen: "Nach 25 Minuten kann die Lehrperson einen vorbereiteten 2NF-Zwischenstand freigeben. BCNF bleibt Vertiefung, der prüfbare Kern endet bei 3NF."
```

#### Folie 04.27: Debrief, kann die Tabelle lügen?

```text
Reveal.js Prompt: Content slide. Vier Prüfkarten: "ein Fakt an einem Ort?", "Insert ohne erfundene Daten?", "Update an einer Stelle?", "Delete ohne Nebenverlust?" Fragmente einzeln, letzte amber. Notizen: "Wir prüfen eine Gruppentabelle mit diesen vier Fragen. Normalformen helfen beim Denken. Die Anomalien zeigen, ob das Ergebnis praktisch besser ist."
```

#### Folie 04.28: Tag 1 endet mit einem Schema

```text
Reveal.js Prompt: Content slide with SVG. Links fünf blue Tabellen, Mitte Datei `schema.sql`, rechts gestrichelte SQL-Sprechblase. Fragmente: 1 green Haken "Begriffe", 2 "Beziehungen", 3 "Constraints", 4 amber "morgen: Fragen". Notizen: "Heute hatten wir erst Sprache, dann Modell, dann Tabellen. Morgen führen wir das Schema in SQLite aus. Danach stellen wir ihm Fragen, zunächst direkt, später aus Java."
```

#### Folie 04.29: BigQuery denormalisiert bewusst

```text
Reveal.js Prompt: Layout citation-slide. Visual: normalisierte blue Tabellen links, BigQuery-Tabelle mit `ARRAY<STRUCT<...>>` rechts, amber Pfeil "weniger wiederholte JOINs". Folientext: "BigQuery empfiehlt nested und repeated fields für passende Hierarchien." Quelle: "https://docs.cloud.google.com/bigquery/docs/best-practices-performance-nested". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "BigQuery dokumentiert Denormalisierung als mögliche Leseoptimierung. Nested und repeated fields halten hierarchische Daten zusammen. Das ist eine bewusste Speicherform, keine zufällige flache Eingangstabelle."
```

### Übung

Übung A3, "Flache Tabelle bis 3NF zerlegen", 35 Minuten. Die verbindliche Karte liegt in `exercises/<domain>/a3-normalization/`. Jede Gruppe bestimmt Kandidatenschlüssel und funktionale Abhängigkeiten und zerlegt bis 3NF. BCNF ist Vertiefung. Ausgang ist `normalization.md` mit Abhängigkeiten, 3NF-Tabellen und Begründung. Debrief-Fragen: Welche Operation erzeugte den Widerspruch? Welche Abhängigkeit war partiell oder transitiv? Musste das ER-Modell geändert werden? Exakter Folientext steht auf Folie 04.26.

## 05 SQL-Grundlagen (Dateiname decks/05-sql-basics.html)

### Ziel

Die Studierenden erstellen das Universitäts-Schema in SQLite und verändern Daten mit DDL (Data Definition Language, Datendefinitionssprache) und DML (Data Manipulation Language, Datenbearbeitungssprache). Sie formulieren `SELECT`-Abfragen mit Filter, Sortierung und Begrenzung. Gestern entstand ein normalisiertes Schema. Jetzt stellen wir den Tabellen erste Fragen. Danach verbinden und verdichten wir mehrere Tabellen.

### Position auf der roten Linie

Vorher: `Tabellen, die uns nicht anlügen: Normalisierung`. Aktuell: `Den Tabellen Fragen stellen: SQL`. Als Nächstes: `Aus Java fragen: JDBC, Cursor, Statements`.

### Erzählung

Ich öffne eine neue `students.db`. Gestern lagen fünf gezeichnete Tabellen auf Papier. Heute antwortet eine echte Datei. Jetzt ist das Schema keine Skizze mehr, sondern eine ausführbare Regel.

SQL ist declarative (deklarativ). Ich sage, welches Ergebnis ich will, nicht welche Schleife Zeile für Zeile laufen soll. Die Datenbank darf einen Ausführungsweg wählen. Das fühlt sich nach Java zuerst ungewohnt an und ist die eigentliche Stärke der Sprache.

Wir trennen DDL, also Struktur, von DML, also Daten. `CREATE TABLE` setzt die Regeln aus Tag 1 um. `INSERT`, `SELECT`, `UPDATE` und `DELETE` verändern oder lesen Zeilen. Nach jedem schreibenden Befehl prüfen wir die Wirkung mit einer gezielten Abfrage.

### Leitfragen

- Frage: "Wo steht die Schleife in SELECT?" Erwartet: Wir beschreiben das Ergebnis, die Datenbank plant die Ausführung. Typisch falsch: SQL hat keine Schleifen und kann deshalb nur eine Zeile lesen; SQLite schreibt intern Java.
- Frage: "Reicht PRIMARY KEY für alle Fachregeln?" Erwartet: Nein, wir brauchen unter anderem NOT NULL, UNIQUE und FOREIGN KEY. Typisch falsch: Ja; Regeln gehören nur in Java.

### Realitätsbezug

SQLite besteht aus einer Datenbankdatei und läuft laut SQLite-Projekt in jedem Android-Gerät sowie in großen Browsern. Unsere CLI arbeitet direkt mit derselben eingebetteten Engine.

### Folien

#### Folie 05.1: SQL-Grundlagen

```text
Reveal.js Prompt: Layout chapter-slide. Titel "SQL-Grundlagen", Untertitel "Tabellen erstellen, Daten ändern, Fragen stellen". Visual: Terminalcursor vor blue Datenbankzylinder. Notizen: "Heute wird das Schema ausführbar. Jede Abfrage läuft gegen dieselben fünf Universitätstabellen."
```

#### Folie 05.2: Wir fragen die Tabellen

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte `Idee im Kopf`, `Darüber sprechen: Abstraktion und ER-Modell` und `Tabellen, die uns nicht anlügen: Normalisierung` erledigt in blue; aktiv `Den Tabellen Fragen stellen: SQL` in amber; als Nächstes `Aus Java fragen: JDBC, Cursor, Statements` mit gestricheltem blue Rahmen; Schritte 6 bis 8 grau. Text unten: "Vorher: schema.sql · Jetzt: SQL · Danach: JDBC". Keine Fragmente. Notizen: "Die Tabellen sind normalisiert. SQL liest und verändert sie direkt. Später sendet Java genau solche Strings über JDBC."
```

#### Folie 05.3: Was beschreibt SELECT?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Sage ich SQL wie oder was es holen soll?" Fragment 1 "was" amber, Fragment 2 `SELECT first_name FROM students;`. Notizen: "Erwartet ist das gewünschte Ergebnis. Typisch falsch sind 'die Schleife steht in SELECT' und 'SQL liest immer die ganze Datei'. Der Query Planner entscheidet über den Weg."
```

#### Folie 05.4: Drei Befehle im Terminal

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/README.md`, Zeilen 9 bis 13. Zeige Zeilen 10 bis 12 vollständig und verbatim: `sqlite3 students.db < schema.sql\nsqlite3 students.db < seed.sql\nsqlite3 -header -column students.db < queries.sql`. Highlights 1|2|3. Rechte Annotationen: "Schema laden", "40 Enrollments laden", "12 Queries ausführen". Fragment 4 zeigt als Terminalergebnis die fünf Tabellennamen `courses departments enrollments lecturers students`. Notizen: "Ich führe die drei Befehle im Verzeichnis `java/sql/university` aus. Die Dateien bauen eine neue Datenbank auf. `queries.sql` ändert Daten, deshalb starten wir jedes Mal mit einer leeren Datei."
```

#### Folie 05.5: DDL und DML

```text
Reveal.js Prompt: Two-column content. Links blue "DDL: CREATE, ALTER, DROP" mit Tabellenrahmen. Rechts amber "DML: INSERT, SELECT, UPDATE, DELETE" mit Zeilen. Fragmente: Spalten, dann Satz "Struktur ≠ Inhalt". Notizen: "DDL verändert das Schema. DML arbeitet mit Daten. Diese Trennung hilft beim Lesen von Dateien und Fehlermeldungen."
```

#### Folie 05.6: CREATE TABLE students

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/schema.sql`, Zeilen 18 bis 29, vollständig und verbatim. Code: `CREATE TABLE IF NOT EXISTS students (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    first_name TEXT NOT NULL,\n    last_name TEXT NOT NULL,\n    email TEXT NOT NULL UNIQUE,\n    student_number TEXT NOT NULL UNIQUE,\n    enrollment_date TEXT NOT NULL\n        CHECK (\n            date(enrollment_date) IS NOT NULL\n            AND enrollment_date = date(enrollment_date)\n        )\n);`. Highlights 1-2|3-6|7-11. Notizen: "Der Primary Key gibt Identität. UNIQUE schützt fachliche Schlüssel. Der CHECK erzwingt ein gültiges ISO-Datum."
```

#### Folie 05.7: CREATE TABLE enrollments

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/schema.sql`, Zeilen 40 bis 53, vollständig und verbatim. Code: `CREATE TABLE IF NOT EXISTS enrollments (\n    id INTEGER PRIMARY KEY AUTOINCREMENT,\n    student_id INTEGER NOT NULL,\n    course_id INTEGER NOT NULL,\n    grade REAL CHECK (grade BETWEEN 1.0 AND 5.0),\n    enrolled_on TEXT NOT NULL\n        CHECK (\n            date(enrolled_on) IS NOT NULL\n            AND enrolled_on = date(enrolled_on)\n        ),\n    UNIQUE (student_id, course_id),\n    FOREIGN KEY (student_id) REFERENCES students(id),\n    FOREIGN KEY (course_id) REFERENCES courses(id)\n);`. Highlights 1-4|5-10|11-13. Notizen: "Die Join Table trägt `enrolled_on` und die optionale Note. UNIQUE verhindert doppelte Belegung. Zwei Foreign Keys bewahren die ER-Linien."
```

#### Folie 05.8: Constraints sind laufende Regeln

```text
Reveal.js Prompt: Four-card content. Die Karten erscheinen einzeln in dieser Reihenfolge. Karte 1 blue `PRIMARY KEY` mit Text "id identifiziert eine Zeile" und red Gegenbeispiel "id 7 zweimal". Karte 2 blue `NOT NULL` mit Text "Pflichtwert fehlt nicht" und red Gegenbeispiel "last_name = NULL". Karte 3 blue `UNIQUE` mit Text "Fachwert bleibt eindeutig" und red Gegenbeispiel "student_number M2023001 zweimal". Karte 4 blue `FOREIGN KEY` mit Text "Verweis braucht ein Ziel" und red Gegenbeispiel "course_id = 99 ohne Course". Schlussfragment green "Fehler beim Write". Notizen: "Constraints prüfen jede passende Schreiboperation. Die vier Gegenbeispiele nennen genau die Regel, die sie verletzt."
```

#### Folie 05.9: INSERT setzt Seed-Zeilen ein

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/seed.sql`, Zeilen 46 bis 53, vollständig und verbatim. Zeige den INSERT für alle sieben Courses. Highlights 46|47-49|50-53. Rechte Ergebnisbox: `7 Courses`, darunter exakt `INF-201`, `INF-202`, `INF-230`, `MAT-110`, `MAT-210`, `WI-101`, `INF-250` in Seed-Reihenfolge. Keine zusätzlichen SQL-Zeilen. Notizen: "Der Seed nennt Spalten explizit und setzt sieben feste IDs. Die Lecturer-IDs 1 bis 5 existieren bereits, weil `seed.sql` Eltern vor Kindern lädt."
```

#### Folie 05.10: Constraint-Fehler lesen

```text
Reveal.js Prompt: Terminal SVG. Quellenhinweis unten: "Didaktischer Fehlversuch gegen `java/sql/university/schema.sql`, Zeilen 18 bis 29; `M2023001` stammt aus `seed.sql`, Zeile 25." Grundzustand exakt `INSERT INTO students (first_name, last_name, email, student_number, enrollment_date) VALUES ('Lea', 'Test', 'lea.test@stud.example', 'M2023001', '2025-10-01');`. Fragment 1 red Meldung `UNIQUE constraint failed: students.student_number`. Fragment 2 markiert `M2023001` im INSERT amber und `students.student_number` in der Meldung amber. Notizen: "Der Seed belegt M2023001 bereits für Lena Hoffmann. SQLite nennt verletzte Tabelle und Spalte. Der Fehlversuch steht nicht in einer Repository-Datei."
```

#### Folie 05.11: SELECT wählt Spalten

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 3 bis 6, vollständig und verbatim. Code zeigt Kommentar und Query 1. Highlights 3-4|5-6. Fragment 1 Ergebniskarte `20 Zeilen`, darunter erste Zeile `1 · Lena · Hoffmann · M2023001` und letzte Zeile `20 · Samir · Saleh · M2025005`. Fragment 2 red Kreuz über `SELECT *` als Standard im Anwendungscode. Notizen: "Query 1 nennt vier Spalten und liefert vor den Writes genau 20 Zeilen. Die erste und letzte sichtbare Zeile stammen aus `seed.sql`."
```

#### Folie 05.12: WHERE filtert Zeilen

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 8 bis 14, vollständig und verbatim. Highlights 8-9|10-12|13-14. Fragment 1 Ergebniskarten genau in Query-Reihenfolge: `Elif Aydin`, `Zeynep Demir`, `Nele Hartmann`, `Amira Hassan`, `Finn Krüger`. Label `5 Zeilen`. Notizen: "WHERE behält Studierende ab `2024-10-01`. ISO-Daten lassen sich in diesem Format vergleichen. ORDER BY und LIMIT bestimmen, welche fünf Namen sichtbar werden."
```

#### Folie 05.13: ORDER BY und LIMIT

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 10 bis 14, ein klar als "gekürzt aus Query 2" markierter Ausschnitt. Code verbatim: `SELECT id, first_name, last_name, enrollment_date\nFROM students\nWHERE enrollment_date >= '2024-10-01'\nORDER BY last_name, first_name\nLIMIT 5;`. Highlights 1-3|4|5. Ergebnisfragment: `5 Zeilen`, Reihenfolge `Aydin, Demir, Hartmann, Hassan, Krüger`. Notizen: "Sortierung ist ohne ORDER BY nicht zugesichert. LIMIT greift nach Filter und Sortierung. Der Ausschnitt ist Query 2 ohne Kommentarzeilen."
```

#### Folie 05.14: UPDATE braucht ein WHERE

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 16 bis 21, vollständig und verbatim. Highlights 16-17|18-20|21. Fragment 1 WHERE amber. Fragment 2 Ergebnisbox `1 · lena.hoffmann@campus.example` und Label `1 Zeile`. Fragment 3 zeigt nur als red Warnkarte den Text `Ohne WHERE: alle Students`, keinen erfundenen SQL-Block. Notizen: "Query 3 ändert Lenas E-Mail-Adresse und liefert mit RETURNING genau eine Zeile. Die nächsten Queries sehen bereits den neuen Wert."
```

#### Folie 05.15: DELETE braucht dieselbe Sorgfalt

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 23 bis 27, vollständig und verbatim. Highlights 23-24|25-26|27. Fragmente: 1 `student_number = 'M2025005'` amber, 2 Ergebnis `20 · Samir · Saleh`, 3 Label `1 Zeile gelöscht`, 4 Zähler `students: 20 → 19`. Notizen: "Query 4 löscht die kurslose Testperson Samir Saleh und liefert genau eine Zeile mit RETURNING. Die Queries 5 bis 12 laufen danach mit 19 Students."
```

#### Folie 05.16: CRUD und SQL

```text
Reveal.js Prompt: Four-row mapping. Create→INSERT, Read→SELECT, Update→UPDATE, Delete→DELETE. Fragmente Zeile für Zeile, SQL blue, CRUD amber. Notizen: "CRUD ist eine Gruppe von Operationen, keine Architektur. Diese vier Zuordnungen kehren in JDBC, Repository und REST wieder."
```

#### Folie 05.17: Ein sicherer Arbeitsrhythmus

```text
Reveal.js Prompt: Circular SVG: `.schema` → `SELECT` → write statement → `changes()` → `SELECT`. Fragmente entlang Kreis, Schreibschritt amber. Notizen: "Wir schauen vor und nach einem Write auf die Daten. Das ist langsamer als blindes Tippen und schneller als Datenrettung. Speichert eure Befehle in Dateien. Die History reicht dafür nicht."
```

#### Folie 05.18: Übung B1, Schema und Constraints

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "Übung B1", Timer "35 min". Dateipfad unten: `exercises/<domain>/b1-schema/README.md`. Eingang: "Startet `schema.sql` und den vorbereiteten Seed." Kern: "Ergänzt Schlüssel, NOT NULL, UNIQUE und zwei Fachregeln" sowie "Prüft zwei ungültige INSERTs und erklärt die Fehler." Vertiefung: "Ergänzt eine weitere Fachregel und eine passende Gegenprobe." Ausgang: "schema.sql läuft mit seed.sql; zwei gezielte Gegenproben scheitern am erwarteten Constraint". Keine Fragmente. Notizen: "Nach 25 Minuten kann die Lehrperson einen vollständigen Constraint-Block freigeben. Seed-Daten werden nicht von Hand erzeugt."
```

#### Folie 05.19: Debrief, bricht das Schema richtig?

```text
Reveal.js Prompt: Content slide. Drei Testkarten: duplicate Key, fehlendes Pflichtfeld, unbekannter Foreign Key. Fragmente: red Testdaten, danach green erwartete Fehler. Notizen: "Ein gutes Schema akzeptiert gültige Daten und weist drei gezielte Fehler ab. Der Happy Path reicht nicht. Jede Gruppe zeigt eine nützliche Fehlermeldung."
```

#### Folie 05.20: SQLite steckt in Gerät und Browser

```text
Reveal.js Prompt: Layout citation-slide. Visual: eine blue `.db`-Datei verbindet Android-Gerät und Browserprofile. Text: "SQLite nennt jedes Android-Gerät sowie Firefox, Chrome und Safari." Quelle: "https://sqlite.org/mostdeployed.html". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Embedded bedeutet, dass die Engine im Programm läuft und eine Datei bearbeitet. Unser Setup ist klein, die Technik weit verbreitet. Im nächsten Deck kombinieren wir mehrere Tabellen."
```

### Übung

Übung B1, "Schema und Constraints", 35 Minuten. Die verbindliche Karte liegt in `exercises/<domain>/b1-schema/`. Die Gruppe startet das vorbereitete Schema und die Seed-Daten, ergänzt Constraints und schreibt zwei gezielte Gegenproben. Ausgang ist ein Schema, das gültige Daten lädt und beide Fehler am erwarteten Constraint zurückweist. Debrief-Fragen: Welcher Constraint fing einen echten Fehler? Welche Spalte erlaubt NULL? Exakter Folientext steht auf Folie 05.18.

## 06 JOINs und Aggregation (Dateiname decks/06-sql-joins-aggregation.html)

### Ziel

Die Studierenden verbinden Zeilen mit `JOIN` und bewahren fehlende Partner mit `LEFT JOIN`. Sie bilden Gruppen, filtern diese mit `HAVING`, nutzen Aggregatfunktionen, Subqueries, `DISTINCT` und behandeln NULL korrekt. Einfache Fragen an einzelne Tabellen funktionieren bereits. Jetzt beantworten wir fachliche Fragen über Beziehungen. Danach sendet Java dieselben Abfragen über JDBC.

### Position auf der roten Linie

Vorher: `Tabellen, die uns nicht anlügen: Normalisierung`. Aktuell: `Den Tabellen Fragen stellen: SQL`. Als Nächstes: `Aus Java fragen: JDBC, Cursor, Statements`.

### Erzählung

Ein JOIN ist kein Venn-Diagramm. Ich lege zwei Tabellen nebeneinander, nehme eine Student-Zeile und suche alle Enrollment-Zeilen mit derselben ID. Danach wiederhole ich das. Das Ergebnis besteht aus kombinierten Zeilen, nicht aus zwei überlappenden Kreisen.

`LEFT JOIN` ist die wichtige Variante für "auch die ohne". Wenn ich alle Courses mit ihrer Zahl an Belegungen brauche, dürfen leere Courses nicht verschwinden. Dann taucht rechts NULL auf. `COUNT(*)` und `COUNT(enrollments.student_id)` liefern dort verschiedene Antworten.

`GROUP BY` faltet viele Ergebniszeilen zu einer Zeile pro Gruppe. `WHERE` filtert vorher einzelne Zeilen, `HAVING` filtert danach Gruppen. Diese Reihenfolge erklärt mehr als jede Merkhilfe.

### Leitfragen

- Frage: "Was passiert beim JOIN mit einer Student-Zeile ohne passenden Enrollment?" Erwartet: Beim INNER JOIN verschwindet sie, beim LEFT JOIN bleibt sie mit NULL rechts. Typisch falsch: Sie bleibt immer; JOIN erzeugt eine leere Enrollment-Zeile in der Tabelle.
- Frage: "Warum ist COUNT(_) nach LEFT JOIN gefährlich?" Erwartet: Es zählt auch die erhaltene linke Zeile ohne Partner. Typisch falsch: COUNT ignoriert immer NULL; COUNT(_) und COUNT(column) sind gleich.
- Frage: "WHERE oder HAVING für Courses mit mehr als zwei Belegungen?" Erwartet: HAVING nach GROUP BY. Typisch falsch: WHERE COUNT(*) > 2; ORDER BY.

### Realitätsbezug

GitHubs REST API hat einen Endpoint "List repository contributors". Solche Ergebnislisten fassen viele einzelne Beiträge pro Person zu einer Sicht pro Contributor zusammen.

### Folien

#### Folie 06.1: JOINs und Aggregation

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Beziehungen abfragen, viele Zeilen zusammenfassen". Visual: zwei blue Tabellen werden zeilenweise verbunden, amber Summenzeichen. Notizen: "Heute lesen wir die Linien aus dem ER-Modell rückwärts. Foreign Keys werden zu Matching-Bedingungen."
```

#### Folie 06.2: Beziehungen werden Abfragen

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 3 erledigt in blue; aktiv `Den Tabellen Fragen stellen: SQL` in amber; als Nächstes `Aus Java fragen: JDBC, Cursor, Statements` mit gestricheltem blue Rahmen; Schritte 6 bis 8 grau. Text unten: "Vorher: einzelne Tabelle · Jetzt: Beziehungen und Gruppen · Danach: JDBC". Keine Fragmente. Notizen: "Wir bleiben bei SQL, aber gehen über eine Tabelle hinaus. Diese Queries kopieren wir später fast unverändert in Java."
```

#### Folie 06.3: Wie findet JOIN passende Zeilen?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Welche Enrollment-Zeilen gehören zu Student 3?" Fragment 1 `students.id = enrollments.student_id` amber. Notizen: "Erwartet ist der Vergleich der Key-Spalten. Typisch falsch sind gleiche Zeilennummer und gleicher Name. IDs verbinden Zeilen, nicht ihre Position."
```

#### Folie 06.4: JOIN als Row Matching

```text
Reveal.js Prompt: Animated SVG. Links `students` mit IDs 1,2,3, rechts `enrollments` mit student_id 1,1,3. Grundzustand Tabellen. Fragmente: 1 Student 1 amber, passende zwei Zeilen amber und Pfeile; 2 zwei Ergebniszeilen blue; 3 Student 2 red ohne Treffer verschwindet; 4 Student 3 erzeugt eine Zeile. Notizen: "Wir gehen linke Zeile für linke Zeile. Ein Treffer kann mehrere Ergebniszeilen erzeugen. Ohne Treffer liefert INNER JOIN nichts."
```

#### Folie 06.5: INNER JOIN in SQL

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 29 bis 40, vollständig und verbatim. Code zeigt Kommentar, Erwartung und Query 5. Highlights 29-30|31-35|36-38|39-40. Fragment 1 Ergebnislabel `16 Zeilen`. Fragment 2 erste Ergebniszeile `Finn · Krüger · Diskrete Mathematik · 1.0`, letzte Ergebniszeile `David · Wolf · Software Engineering · 1.7`. Notizen: "Jede ON-Bedingung folgt einem Foreign Key. Der Filter behält Noten bis 1,7. Gegen den Seed liefert die Query genau 16 Zeilen."
```

#### Folie 06.6: LEFT JOIN behält links alles

```text
Reveal.js Prompt: Row-matching SVG aus 06.4. Fragment 1 Student 2 bleibt blue, Fragment 2 rechte Felder zeigen NULL amber, Fragment 3 Titel "auch ohne Enrollment". Notizen: "LEFT JOIN bewahrt jede linke Zeile. Fehlende rechte Partner werden NULL. Das ist die Form für 'alle Courses, auch leere'."
```

#### Folie 06.7: JOIN ist kein Venn-Diagramm

```text
Reveal.js Prompt: Two-column content. Links durchgestrichene red Venn-Kreise, rechts green Tabellenzeilen mit mehrfachen Treffern. Text: "Mengenbild versteckt Duplikate und Zeilenform". Notizen: "Venn-Bilder können Mengenoperationen erklären, aber JOINs schlecht. Ein Student mit zwei Enrollments erzeugt zwei Zeilen. Unser Row-Matching zeigt das direkt."
```

#### Folie 06.8: GROUP BY faltet Zeilen

```text
Reveal.js Prompt: SVG sechs Enrollment-Zeilen, nach `course_id` farblich blue gruppiert. Fragmente: 1 Klammern pro Course, 2 jede Gruppe wird zu einer Ergebniszeile, 3 Count-Wert amber. Notizen: "GROUP BY bestimmt die Körnung des Ergebnisses. Alle nicht aggregierten SELECT-Spalten müssen zu dieser Körnung passen."
```

#### Folie 06.9: COUNT, SUM, AVG, MIN, MAX

```text
Reveal.js Prompt: Five-card content. Grundzustand Titel und fünf leere gleich große Karten. Fragmente: 1 `COUNT(e.id) = 8` für INF-201, 2 `AVG(e.grade) = 2.00` für INF-201, 3 `MIN(e.grade) = 1.0`, 4 `MAX(e.grade) = 3.0`, 5 `SUM(credits) = 38` über alle sieben Courses. Karten blue, Zahlen amber. Text unten: "Viele Zeilen → ein Wert pro Gruppe". Notizen: "COUNT und AVG stammen aus den verifizierten Queries 7 und 8. MIN, MAX und SUM lassen sich direkt aus `seed.sql` prüfen. Aggregatfunktionen fassen eine Gruppe zu einem Wert zusammen."
```

#### Folie 06.10: Courses mit Zahl der Belegungen

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 50 bis 56, vollständig und verbatim. Highlights 50-51|52-54|55|56. Fragment 1 Ergebnistabelle mit genau sieben Zeilen: `INF-201 8`, `INF-230 7`, `INF-202 6`, `INF-250 5`, `MAT-110 5`, `MAT-210 5`, `WI-101 4`; die Titel stehen in einer mittleren Spalte wie in Query 7. Notizen: "LEFT JOIN hält jeden Course. `COUNT(e.id)` zählt nur echte Enrollments. Der Seed hat keinen leeren Course, die sieben Zähler sind 8, 7, 6, 5, 5, 5 und 4."
```

#### Folie 06.11: COUNT(*) gegen COUNT(column)

```text
Reveal.js Prompt: Two-column result. Sichtbarer Hinweis oben: "Hypothetischer achter Course ohne Enrollment, im Seed sind alle sieben belegt". Links liefert der LEFT JOIN `COUNT(*) = 1` red, rechts `COUNT(e.student_id) = 0` green. Fragment 1 zeigt die synthetische Ergebniszeile mit `e.student_id = NULL`, Fragment 2 markiert die beiden Zähler. Notizen: "COUNT(*) zählt die erhaltene linke Ergebniszeile. COUNT(column) ignoriert NULL in dieser Spalte. Das Beispiel ergänzt den Seed ausdrücklich um einen leeren Course."
```

#### Folie 06.12: WHERE vor, HAVING nach der Gruppe

```text
Reveal.js Prompt: Pipeline SVG FROM/JOIN → WHERE → GROUP BY → HAVING → SELECT → ORDER BY. Fragmente entlang Pfeil; WHERE blue, HAVING amber. Text: "Zeilen filtern · Gruppen filtern". Notizen: "Die Schreibreihenfolge von SQL ist nicht die gedankliche Ausführungsreihenfolge. WHERE sieht noch einzelne Zeilen. HAVING sieht Aggregatwerte."
```

#### Folie 06.13: HAVING

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 66 bis 73, vollständig und verbatim. Highlights 66-67|68-70|71-72|73. Fragment 1 Ergebnistabelle mit genau drei Zeilen: `INF-201 · Datenbanken · 8`, `INF-230 · Webentwicklung · 7`, `INF-202 · Programmierung II · 6`. Notizen: "Query 9 filtert Gruppen mit `HAVING COUNT(e.id) >= 6`. Gegen den Seed bleiben genau drei Courses. `WHERE COUNT(...)` wäre zu früh."
```

#### Folie 06.14: NULL ist unbekannt, nicht falsch

```text
Reveal.js Prompt: Content slide. Ausdrücke `grade = NULL` red, `grade IS NULL` green, `NULL = NULL` ergibt `UNKNOWN` amber. Fragmente einzeln. Notizen: "NULL wird nicht mit Gleichheit geprüft. SQL arbeitet hier mit TRUE, FALSE und UNKNOWN. WHERE behält nur TRUE."
```

#### Folie 06.15: LEFT JOIN sucht fehlende Partner

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 42 bis 48, vollständig und verbatim. Highlights 42-43|44-46|47-48. Links der Code, rechts eine kleine Row-Matching-Grafik mit Student 19 ohne Enrollment. Fragment 1 markiert `LEFT JOIN`, Fragment 2 markiert `e.id IS NULL`, Fragment 3 zeigt Ergebnis `19 · Hannah · Maier` und Label `1 Zeile`. Notizen: "Query 4 hat Samir Saleh bereits gelöscht. Danach bleibt Hannah Maier als einzige Studentin ohne Course. Der NULL-Test sucht den fehlenden rechten Partner."
```

#### Folie 06.16: Subquery beantwortet eine Teilfrage

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 75 bis 90, vollständig und verbatim. Highlights 75-76|79-88|77-78|89-90. Fragment 1 Ergebnislabel `7 Zeilen`. Fragment 2 Namen in Query-Reihenfolge: `Zeynep Demir`, `Nele Hartmann`, `Lena Hoffmann`, `Aylin Kaya`, `Finn Krüger`, `Noah Schulz`, `Mia Wagner`. Notizen: "Die innere Subquery berechnet den Gesamtdurchschnitt. Die gruppierte mittlere Query vergleicht jeden Student-Durchschnitt damit. Kleinere Noten sind besser, deshalb nutzt HAVING das Kleinerzeichen."
```

#### Folie 06.17: DISTINCT entfernt Duplikate

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/sql/university/queries.sql`, Zeilen 101 bis 108, vollständig und verbatim. Highlights 101-102|103|104-107|108. Fragment 1 zeigt vor DISTINCT drei mehrfach wiederkehrende Codes als graue Karten. Fragment 2 zeigt exakt drei Ergebniszeilen: `INF · Informatik`, `MAT · Mathematik`, `WI · Wirtschaftswissenschaften`. Fragment 3 amber Hinweis "DISTINCT entfernt Ergebnisduplikate, keinen falschen JOIN". Notizen: "Query 12 verbindet alle fünf Tabellen und liefert genau drei Fachbereiche. DISTINCT ist hier fachlich gewollt, weil jeder Department-Code einmal erscheinen soll."
```

#### Folie 06.18: Übung B2, vier geprüfte Fragen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "Übung B2", Timer "45 min". Dateipfad unten: `exercises/<domain>/b2-sql/README.md`. Eingang: "Startet Schema und Seed aus B1. Prüft mindestens zehn Zeilen." Kern: "Schreibt zwei JOIN-Abfragen und eine Aggregation" sowie "Ergänzt eine LEFT-JOIN-Abfrage mit einem fehlenden Partner." Vertiefung: "Ergänzt HAVING oder eine Subquery aus der Gruppenkarte." Ausgang: "queries.sql läuft ohne Fehler; die Gruppe erklärt, warum eine Zeile beim LEFT JOIN erhalten bleibt". Keine Fragmente. Notizen: "Nach 35 Minuten kann die Lehrperson eine vorbereitete JOIN-Abfrage freigeben. Danach folgen fünf Minuten gemeinsame Auswertung."
```

#### Folie 06.19: (Reserve) Ein Fenster behält jede Zeile

```text
Reveal.js Prompt: Layout .code-slide. Markiert "(Reserve)". Quellenhinweis sichtbar: "Didaktische Erweiterung, nicht in `queries.sql`. Verwendet die Spalten aus `java/sql/university/schema.sql`, Zeilen 40 bis 53." Code: `SELECT course_id, student_id,\n       COUNT(*) OVER (PARTITION BY course_id) AS course_count\nFROM enrollments;`. Fragment 1 GROUP-BY-Ergebnis eine Zeile pro Course, Fragment 2 Window-Ergebnis alle 40 Enrollment-Zeilen green. Notizen: "Window Functions aggregieren, ohne die Detailzeilen zu falten. Der Seed hat 40 Enrollments, deshalb liefert der Ausschnitt 40 Zeilen. Diese Folie entfällt zuerst."
```

#### Folie 06.20: GitHub fasst Beiträge pro Person zusammen

```text
Reveal.js Prompt: Layout citation-slide. Visual: viele Commit-Karten mit Autor laufen in eine Contributor-Liste, eine Zeile pro Person. Text: "GitHub: List repository contributors" und "viele Beiträge → Sicht pro Contributor". Quelle: "https://docs.github.com/en/rest/repos/repos#list-repository-contributors". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "GitHub bietet eine Ergebnisliste pro Contributor statt einer ungefilterten Commit-Liste. Wir behaupten damit nichts über GitHubs interne Query. Das sichtbare Ergebnis hat aber dieselbe Gruppenkörnung wie unsere Aggregation."
```

### Übung

Übung B2, "Vier geprüfte SQL-Fragen", 45 Minuten. Die verbindliche Karte liegt in `exercises/<domain>/b2-sql/`. Die Gruppe schreibt zwei JOIN-Abfragen, eine Aggregation und eine LEFT-JOIN-Abfrage mit fehlendem Partner. HAVING oder eine Subquery ist Vertiefung. Ausgang ist `queries.sql`; die Gruppe erklärt, warum eine Zeile beim LEFT JOIN erhalten bleibt. Debrief-Fragen: Welche Abfrage brauchte LEFT JOIN? Wo hätte COUNT(*) falsch gezählt? Exakter Folientext steht auf Folie 06.18.

## 07 JDBC und Cursor (Dateiname decks/07-jdbc-cursor.html)

### Ziel

Die Studierenden erklären den Weg von Java über den JDBC Driver (Treiber) zur SQLite-Datei. Sie öffnen Ressourcen sicher, binden Werte mit `PreparedStatement` (vorbereitetem Statement), bewegen einen `ResultSet`-Cursor und bilden Zeilen auf ihre Hauptressource ab. Der Kern sind `findById`, `findAll` und sichtbares Row Mapping. Schreiben mit `save` ist Vertiefung. Danach kapseln wir SQL und Mapping hinter einem Repository.

### Position auf der roten Linie

Vorher: `Den Tabellen Fragen stellen: SQL`. Aktuell: `Aus Java fragen: JDBC, Cursor, Statements`. Als Nächstes: `Wiederholungen beenden: Repository Pattern`.

### Erzählung

Bis jetzt war ich selbst der Client: Ich tippte SQL in `sqlite3`. Nun übernimmt Java. Der JDBC Driver übersetzt die einheitlichen Java-Aufrufe in das Protokoll oder Dateiformat der Datenbank. Für SQLite endet der Weg in `students.db`.

Ein `ResultSet` ist kein fertiges `List<Student>`. Der Cursor steht zuerst vor der ersten Zeile. `next()` bewegt ihn. Danach lese ich Spalten und baue ein Objekt. Genau diese Zuordnung wiederholt sich im einfachen Programm mehrmals.

Beim Live-Demo baue ich absichtlich eine unsichere Suche mit String-Verkettung. Die Eingabe `' OR 1=1 --` macht aus einem Filter eine Bedingung, die immer wahr ist. Danach ersetze ich den String durch `?` und binde den Wert. Das ist keine kosmetische Änderung. SQL und Daten bleiben getrennt.

SQLite-Daten sind der kleine Stolperstein. Unser Schema speichert das Datum als ISO-Text, Java erwartet `LocalDate`. Deck 07 zeigt die zweimal kopierte Umwandlung mit `LocalDate.parse`. Deck 08 zieht das gesamte Row Mapping in `rowToStudent`.

### Leitfragen

- Frage: "Wo steht der ResultSet-Cursor direkt nach executeQuery()?" Erwartet: Vor der ersten Zeile. Typisch falsch: Auf Zeile 0; auf der ersten Zeile.
- Frage: "Warum schützt ein Fragezeichen vor SQL Injection?" Erwartet: Der Driver bindet den Wert als Daten, nicht als SQL-Syntax. Typisch falsch: Das Fragezeichen entfernt Sonderzeichen; PreparedStatement verschlüsselt die Datenbank.
- Frage: "Wer schließt Connection und ResultSet bei einer Exception?" Erwartet: try-with-resources. Typisch falsch: Der Garbage Collector sofort; SQLite braucht kein Schließen.

### Realitätsbezug

Der PostgreSQL JDBC Driver erlaubt Java-Programmen den Zugriff auf PostgreSQL mit standardisiertem, datenbankunabhängigem JDBC-Code. Intern spricht der Driver das native PostgreSQL-Netzwerkprotokoll.

### Folien

#### Folie 07.1: JDBC und Cursor

```text
Reveal.js Prompt: Layout chapter-slide. Titel "JDBC und Cursor", Untertitel "SQL aus Java". Sichtbares Label "Leitfragen", darunter: "Wie wird aus einer Tabellenzeile ein Java-Objekt?" und "Wo bündeln wir SQL und Row Mapping, damit beides nur einmal vorkommt?" Visual: Java-Kaffeetasse → blue Driver-Box → SQLite-Datei. Notizen: "Wir ändern nicht die Abfragen, sondern ihren Absender. Java übernimmt Terminalarbeit und Objektabbildung."
```

#### Folie 07.2: Java fragt die Tabellen

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 4 erledigt in blue; aktiv `Aus Java fragen: JDBC, Cursor, Statements` in amber; als Nächstes `Wiederholungen beenden: Repository Pattern` mit gestricheltem blue Rahmen; Schritte 7 und 8 grau. Text unten: "Vorher: SQL im Terminal · Jetzt: JDBC · Danach: Repository Pattern". Keine Fragmente. Notizen: "JDBC ist die Brücke. Am Ende funktioniert CRUD aus Java. Die Wiederholung im Code liefert morgen früh den Grund für das Repository Pattern."
```

#### Folie 07.3: Was liegt zwischen Java und SQLite?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Wer übersetzt `prepareStatement` für SQLite?" Fragment 1 "JDBC Driver" amber. Notizen: "Erwartet ist der SQLite JDBC Driver. Typisch falsch sind der Java-Compiler und Spring. JDBC ist die API, der Driver die konkrete Umsetzung."
```

#### Folie 07.4: Der JDBC-Weg

```text
Reveal.js Prompt: SVG mit Boxen Application, JDBC API, SQLite Driver, `students.db`. Fragmente: Pfeile nacheinander blue; Rückweg ResultSet amber. Text: "eine Java-API, konkrete Driver". Notizen: "Unser Anwendungscode spricht JDBC. Der Driver kennt SQLite. Ein anderer JDBC-Driver kann denselben Grundcode mit PostgreSQL verbinden, Details bleiben trotzdem verschieden."
```

#### Folie 07.5: Connection öffnen

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeile 13 sowie Zeilen 65 bis 67. Als zwei klar getrennte, verbatim Quellkarten zeigen: `private static final String DATABASE_URL = "jdbc:sqlite:students.db";` und `try (Connection connection = DriverManager.getConnection(DATABASE_URL);\n     Statement statement = connection.createStatement()) {\n    statement.execute(sql);`. Label über der zweiten Karte: "gekürzt: Block endet in Zeile 71". Highlights 13|65-66|67. Notizen: "Die URL wählt Driver und Datei. Die Connection begrenzt eine Sitzung. Der vollständige try-with-resources-Block in den Zeilen 65 bis 71 schließt Connection und Statement."
```

#### Folie 07.6: Statement oder PreparedStatement?

```text
Reveal.js Prompt: Two-column content. Links red `Statement` plus String-Verkettung, rechts green `PreparedStatement` plus `?`. Fragmente: 1 Syntax und Daten vermischt, 2 getrennte Bindung, 3 amber "Standard für Werte". Notizen: "Statement ist für statisches SQL möglich. Sobald Werte aus Eingaben kommen, verwende ich PreparedStatement. Es schützt und behandelt Typen sauberer."
```

#### Folie 07.7: SQL Injection live

```text
Reveal.js Prompt: Layout .code-slide. Quellenhinweis sichtbar: "Didaktisches Gegenbeispiel, nicht im Repository. Abgeleitet von der sicheren SELECT-Struktur in `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeilen 137 bis 145." Grundcode red: `String sql = "SELECT * FROM students WHERE email = '" + input + "'";`. Fragment 1 Eingabe `' OR 1=1 --`, Fragment 2 resultierendes SQL, Fragment 3 alle Student-Zeilen red. Notizen: "Ich führe die harmlose Demo nur gegen lokale Seed-Daten aus. Das Repository enthält diese unsichere Variante nicht. Die Eingabe beendet den String und ergänzt SQL-Syntax."
```

#### Folie 07.8: PreparedStatement trennt Code und Daten

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeilen 137 bis 147, gekürzt und mit `…` markiert. Zeige verbatim die SQL-Zeilen 137 bis 141, danach `…`, dann verbatim die Zeilen 143 bis 147. Highlights SQL mit `?`|`prepareStatement`|`setInt`|`executeQuery`. Fragment: Wert `id` läuft als blue Datenpfeil zum Placeholder, nicht in den SQL-Text. Notizen: "Main bindet die ID als Datenwert. Der gezeigte Ausschnitt lässt nur Leerzeilen aus und markiert die ausgelassene Stelle. Escaping von Hand ist kein gleichwertiger Ersatz."
```

#### Folie 07.9: Der Cursor startet vor Zeile 1

```text
Reveal.js Prompt: SVG Ergebnistabelle mit Cursorpfeil oberhalb. Fragmente: 1 `rs.next()` bewegt zu Zeile 1, 2 liest Spalten, 3 nächstes `next()`, 4 `false` hinter letzter Zeile. Notizen: "Ohne erstes next() darf ich keine Spalte lesen. Die while-Schleife passt, weil sie Bewegung und Ende verbindet."
```

#### Folie 07.10: ResultSet wird Student

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeilen 115 bis 122, vollständig und verbatim. Code: `Student student = new Student(\n        resultSet.getInt("id"),\n        resultSet.getString("first_name"),\n        resultSet.getString("last_name"),\n        resultSet.getString("email"),\n        resultSet.getString("student_number"),\n        LocalDate.parse(resultSet.getString("enrollment_date"))\n);`. Highlights 1|2-6|7-8. Notizen: "Die Namen entsprechen `students` in `schema.sql`. Hier wird eine Zeile zum Objekt. Ein falscher Spaltenname fällt erst zur Laufzeit auf."
```

#### Folie 07.11: SELECT und Cursor zusammen

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeilen 103 bis 124, vollständig und verbatim. Highlights 103-107|109-111|112-115|116-122|123-124. Annotation rechts: "Query · Ressourcen · Cursor · Row Mapping". Notizen: "PreparedStatement und ResultSet haben getrennte Lebensdauern. try-with-resources schließt in umgekehrter Reihenfolge. Die while-Schleife verarbeitet null bis viele Zeilen."
```

#### Folie 07.12: try-with-resources räumt auf

```text
Reveal.js Prompt: SVG verschachtelte Rahmen Connection, PreparedStatement, ResultSet. Fragment 1 Exception red innen, Fragment 2 Schließpfeile ResultSet → Statement → Connection green. Text: "Schließen auch im Fehlerfall". Notizen: "Offene Ressourcen sammeln sich sonst unter Last. try-with-resources macht den Lebenszyklus im Code sichtbar. Jeder Typ implementiert AutoCloseable."
```

#### Folie 07.13: INSERT mit gebundenen Werten

```text
Reveal.js Prompt: Layout .code-slide. Sichtbares Label "Vertiefung · Schreiben". Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeilen 82 bis 95, vollständig und verbatim. Highlights 82-85|87-88|89-93|94-95. Annotation rechts: "SQL · PreparedStatement · fünf Bindings · executeUpdate". Notizen: "Parameterpositionen beginnen bei 1. Ihre Reihenfolge folgt den Fragezeichen. Das Datum wird im vereinbarten ISO-Format gebunden."
```

#### Folie 07.14: executeQuery oder executeUpdate

```text
Reveal.js Prompt: Two-column content. Links `SELECT → executeQuery() → ResultSet`; rechts `INSERT/UPDATE/DELETE → executeUpdate() → int`. Fragmente je Pfad, Zeilenzahl amber. Notizen: "Der Rückgabetyp zeigt die Absicht. Bei Writes prüfen wir die betroffene Zeilenzahl. Null betroffene Zeilen sind oft ein 404-Kandidat für später."
```

#### Folie 07.15: SQLite und LocalDate

```text
Reveal.js Prompt: Content slide. Pfad 1 SQLite-Spalte `enrollment_date TEXT`, Pfad 2 CHECK mit `date(enrollment_date)`, Pfad 3 `LocalDate.parse(resultSet.getString("enrollment_date"))` green. Fragment 4 NULL und anderes Format red. Notizen: "SQLite hat keinen eigenständigen Date-Typ wie Java. Unser Schema legt ISO-Text fest und prüft das Format. Java wandelt denselben Text mit LocalDate.parse um."
```

#### Folie 07.16: Dasselbe Row Mapping zweimal

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/Main.java`. Zwei gleich breite Codekarten: links Zeilen 115 bis 122, rechts Zeilen 149 bis 156, jeweils vollständig und verbatim. Fragmente: 1 linke Karte, 2 rechte Karte, 3 rote Klammer "derselbe Konstruktorblock zweimal". Notizen: "Das Programm funktioniert, aber dieselbe Abbildung kann auseinanderlaufen. Deck 08 zeigt `MainRefactored.rowToStudent` als gemeinsame Stelle."
```

#### Folie 07.17: Fehler enthalten Kontext

```text
Reveal.js Prompt: Two-column content. Links red `catch (SQLException e)`, rechts amber Kontext `operation`, `student id`, `constraint`, ohne Passwort oder komplettes SQL. Fragment 1 schlechte Meldung "Database error", Fragment 2 konkrete Meldung green. Notizen: "Eine Fehlermeldung soll die Operation und den betroffenen Identifier nennen. Sensible Werte gehören nicht ins Log. SQLException bleibt die technische Ursache."
```

#### Folie 07.18: Walkthrough durch cursor-simple

```text
Reveal.js Prompt: Content slide mit Dateipfad `java/cursor-simple/src/main/java/org/lecture/Main.java`. Fünf gleich große Ablaufkarten: `initializeDatabase`, `createStudent`, `listAllStudents`, `readStudent`, `updateStudent/deleteStudent`; darunter kleine Karte `Input-Helfer`. Fragmente entlang blue Pfeil. Notizen: "Die Datei hat 248 Zeilen. Wir folgen einem Create und einem Read. Danach markieren wir die beiden Row-Mapping-Blöcke für das nächste Kapitel."
```

#### Folie 07.19: B3, eure Hauptressource lesen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "B3 · Kernauftrag", Timer "45 min". Dateipfad unten: `exercises/<domain>/b3-jdbc/`. Mitte mit vier nummerierten Schritten: 1 "Startet das vorbereitete Projekt und führt den Starttest aus." 2 "Implementiert findById mit Optional." 3 "Implementiert findAll mit sichtbarem Row Mapping." 4 "Prüft einen Treffer, eine fehlende ID und die Seed-Daten." Abgabe: "grüner Testlauf und Ausgabe eines vorhandenen Datensatzes". Keine Fragmente. Notizen: "Model, Connection und Tests sind vorbereitet. Nach der Kernzeit kann die Lehrperson den vorbereiteten Zwischenstand freigeben."
```

#### Folie 07.20: B3, Schreiben als Vertiefung

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "B3 · Vertiefung", Timer "20 min". Dateipfad unten: `exercises/<domain>/b3-jdbc/`. Mitte mit vier nummerierten Schritten: 1 "Implementiert save für dieselbe Hauptressource." 2 "Bindet jeden Wert mit PreparedStatement." 3 "Prüft die betroffene Zeilenzahl und die erzeugte ID." 4 "Lest den neuen Datensatz wieder mit findById." Abgabe: "grüner Schreibtest und gelesener Datensatz". Keine Fragmente. Notizen: "Schreiben ist Vertiefung. Der vorbereitete Zwischenstand stellt sicher, dass jede Gruppe mit einem lesenden Repository in B4 weiterarbeiten kann."
```

#### Folie 07.21: Debrief, wo wiederholt sich CRUD?

```text
Reveal.js Prompt: Content slide. Fünf Karten Create, Read one, Read all, Update, Delete; darunter wiederholte blue Teile SQL, bind, execute, map, close. Fragment 1 rote Klammer um Wiederholung, Fragment 2 Frage "Was kann generisch werden?". Notizen: "Wir vergleichen zwei Repositories. Tabellennamen und Mapping ändern sich, der Ablauf kaum. Das ist der Übergang zum Repository Pattern."
```

#### Folie 07.22: JDBC ist die gemeinsame Java-Form

```text
Reveal.js Prompt: Layout citation-slide. Visual: Java-Anwendung oben, JDBC-API in blue, pgJDBC-Driver darunter, PostgreSQL-Server rechts. Text: "Standard-JDBC-Code · natives PostgreSQL-Protokoll". Quelle: "https://jdbc.postgresql.org/documentation/". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "pgJDBC verbindet Java über standardisierten JDBC-Code mit PostgreSQL. Der Driver spricht darunter das PostgreSQL-Protokoll. Als Nächstes kapseln wir diesen Zugriff hinter Repositories."
```

### Übung

B3, "Eure Hauptressource mit JDBC lesen", dauert 45 Minuten. Die Gruppe arbeitet im vorbereiteten Projekt unter `exercises/<domain>/b3-jdbc/` und implementiert `findById` sowie `findAll`. Das Row Mapping bleibt sichtbar. Ein Treffer, eine fehlende ID und die Seed-Daten werden getestet. `save` ist eine 20-minütige Vertiefung. Ausgang sind ein grüner Testlauf und ein gelesener Datensatz. Debrief-Fragen: Wo passiert Row Mapping? Welcher Input wird gebunden? Welche Teile wiederholen sich? Exakte Folientexte stehen auf 07.19 und 07.20.

### Code

Gezeigt wird zuerst `java/cursor-simple/src/main/java/org/lecture/Student.java`, Zeilen 1 bis 58 vollständig. Danach folgt `java/cursor-simple/src/main/java/org/lecture/Main.java` in dieser Reihenfolge: Datenbank-URL, main und Menü in den Zeilen 12 bis 47, Initialisierung 49 bis 72, Create 74 bis 99, List und Cursor 101 bis 132, Read 134 bis 166, Update 168 bis 201 und Delete 203 bis 220. Die beiden Row-Mapping-Blöcke in den Zeilen 115 bis 122 und 149 bis 156 werden vollständig und direkt nebeneinander gezeigt.

Der für die Folie relevante Anfang der Student-Klasse, Zeilen 5 bis 21, lautet verbatim:

```java
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String studentNumber;
    private LocalDate enrollmentDate;

    public Student(int id, String firstName, String lastName, String email,
                   String studentNumber, LocalDate enrollmentDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentNumber = studentNumber;
        this.enrollmentDate = enrollmentDate;
    }
```

Der absichtlich duplizierte Mapping-Block lautet an beiden Stellen verbatim, einmal mit der Variablen `student`:

```java
Student student = new Student(
        resultSet.getInt("id"),
        resultSet.getString("first_name"),
        resultSet.getString("last_name"),
        resultSet.getString("email"),
        resultSet.getString("student_number"),
        LocalDate.parse(resultSet.getString("enrollment_date"))
);
```

Für die Injection-Demo zeigt Folie 07.7 ein didaktisches Gegenbeispiel. Es steht nicht im Repository. Die sichere SELECT-Struktur, von der die Folie ausgeht, steht in `java/cursor-simple/src/main/java/org/lecture/Main.java`, Zeilen 137 bis 147. Der unsichere Einzeiler ist deshalb auf der Folie ausdrücklich als abgeleitet und nicht als Repo-Code markiert:

```java
String sql = "SELECT * FROM students WHERE email = '" + input + "'";
```

Die sichere Fassung auf Folie 07.8 ist ein gekürzter, mit `…` markierter Ausschnitt aus `Main.java`, Zeilen 137 bis 147. Sie behält die echten Namen `statement`, `id` und `resultSet`. Deck-Agenten ersetzen sie nicht durch ein frei erfundenes E-Mail-Beispiel.

## 08 Das Repository Pattern (Dateiname decks/08-repository-pattern.html)

### Ziel

Die Studierenden ziehen SQL und Row Mapping aus dem Aufrufer hinter ein fachlich typisiertes Repository. `findById` und `findAll` bleiben der prüfbare Kern. Reflection (Laufzeitinspektion), eigene Annotationen und das generische `AbstractRepository<T>` stehen im Anhang. Als Nächstes übernehmen wir Repository und Migration in die Spring-Anwendung.

### Position auf der roten Linie

Vorher: `Aus Java fragen: JDBC, Cursor, Statements`. Aktuell: `Wiederholungen beenden: Repository Pattern`. Als Nächstes: `Andere zugreifen lassen: REST API mit Spring`.

### Erzählung

Gestern endeten wir mit fünf fast gleichen Abläufen. SQL bauen, Werte binden, ausführen, Zeilen abbilden, Ressourcen schließen. Wenn ich morgen `Course` ergänze, kopiere ich sonst wieder hundert Zeilen.

Ein Repository gibt dem Rest der Anwendung eine Sprache für gespeicherte Objekte. `findById`, `findAll`, `save`, `delete`. Die aufrufende Klasse muss nicht wissen, ob dahinter SQLite, eine andere Datenbank oder ein Test-Doppel steckt.

Der Kern endet an einer klaren Grenze: Der Aufrufer kennt das Repository-Interface, aber weder SQL noch Spaltennamen. Im Anhang zeigt `AbstractRepository<T>`, wie Reflection Felder liest und eigene Annotationen Tabellen- und Spaltennamen liefern. Das erklärt Framework-Mechanik, ist aber keine Voraussetzung für C2.

Eine laufende Datenbank braucht zudem Geschichte. `CREATE TABLE IF NOT EXISTS` kann eine neue Spalte nicht sauber auf bereits bestehende Installationen verteilen. Das größere Spring-Beispiel hat `V1__Create_student_table.sql`, `V2__Create_admin_table.sql` und `V3__Create_refresh_token_table.sql`. Flyway protokolliert Version und Checksumme.

### Leitfragen

- Frage: "Welche Teile ändern sich zwischen StudentRepository und CourseRepository?" Erwartet: Typ, Tabellenname, Spalten und spezielle Queries, nicht der CRUD-Ablauf. Typisch falsch: Alles; nur der Klassenname.
- Frage: "Ersetzt ein Repository SQL?" Erwartet: Nein, es kapselt Datenzugriff, SQL oder ORM bleibt darunter. Typisch falsch: Ja; Repositories speichern im Arbeitsspeicher.
- Frage: "Warum reicht CREATE TABLE IF NOT EXISTS nach Release 1 nicht?" Erwartet: Es versioniert Änderungen an bestehenden Schemas nicht. Typisch falsch: Es überschreibt Daten; es läuft nur einmal.

### Realitätsbezug

Spring Data beschreibt sein Repository-Konzept als Weg, wiederholten Datenzugriff zu reduzieren. `JpaRepository` erbt CRUD-Methoden wie `save`, `findById`, `findAll` und `delete`.

### Folien

#### Folie 08.1: Das Repository Pattern

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Datenzugriff einmal schreiben". Sichtbares Label "Leitfragen", darunter: "Welche Klasse kennt SQL und Spaltennamen?" und "Wie bleibt der Aufrufer unabhängig von JDBC?" Visual: drei Entity-Karten laufen durch ein blue Repository-Tor zur Datenbank. Notizen: "Das Kapitel beginnt mit unserem eigenen Schmerz. Wiederholter JDBC-Code wird zu einer gemeinsamen Abstraktion."
```

#### Folie 08.2: Wiederholungen beenden

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 5 erledigt in blue; aktiv `Wiederholungen beenden: Repository Pattern` in amber; als Nächstes `Andere zugreifen lassen: REST API mit Spring` mit gestricheltem blue Rahmen; Schritt 8 grau. Text unten: "Vorher: JDBC pro Entity · Jetzt: Repository Pattern · Danach: REST API mit Spring". Keine Fragmente. Notizen: "Wir kapseln JDBC, damit der Rest der Anwendung fachlich und testbar bleibt. Morgen stellt Spring das Repository hinter HTTP."
```

#### Folie 08.3: Was bleibt bei jeder Entity gleich?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Was kopiert ihr für Course aus StudentRepository?" Fragmente: 1 "Connection · bind · execute · map · close", 2 amber "Ablauf bleibt, Daten ändern sich". Notizen: "Erwartet ist der gemeinsame CRUD-Ablauf. Typisch falsch sind 'nichts' und 'die ganze Datei'. Wir suchen die stabile Form."
```

#### Folie 08.4: Interface zuerst

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/repository-simple/src/main/java/org/lecture/AbstractRepository.java`, Zeilen 12 bis 17, vollständig und verbatim. Code: `public interface AbstractRepository<T> {\n\n    Connection getConnection();\n\n    Class<T> getClassType();`. Am unteren Rand steht der klare Hinweis `gekürzt: Default-Methoden all/get/create/update/delete folgen ab Zeile 18`. Highlights 12|14|16. Fragment 1 fünf Methodenkarten `all`, `get`, `create`, `update`, `delete` unter dem Interface. Notizen: "Das echte Interface fordert Connection und Laufzeittyp. Die CRUD-Operationen sind Default-Methoden im selben Interface. StudentRepository liefert die beiden fehlenden Angaben."
```

#### Folie 08.5: rowToStudent zieht die Kopie zusammen

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/cursor-simple/src/main/java/org/lecture/MainRefactored.java`, Zeilen 152 bis 161, vollständig und verbatim. Links der Methodenblock, rechts zwei Aufrufkarten mit den verbatim Zeilen 115 und 142: `System.out.println(rowToStudent(resultSet));`. Fragmente: 1 Methode, 2 Aufruf aus List, 3 Aufruf aus Read, 4 green Klammer "eine Abbildung". Notizen: "Der erste Refactor ist klein. Eine Stelle entscheidet über Spaltennamen und Datum. List und Read rufen dieselbe Methode auf."
```

#### Folie 08.6: AbstractRepository<T>

```text
Reveal.js Prompt: Content slide with SVG, sichtbar als "Anhang" markiert. Oben `AbstractRepository<T>`, darunter blue Methoden all/get/create/update/delete; rechts konkrete `StudentRepository` liefert Connection und `Student.class`. Fragmente: generischer Ablauf, konkrete Typinfo, Datenbank. Notizen: "Der generische Typ hält Rückgaben typisiert. Zwei konkrete Methoden liefern die fehlende Laufzeitinformation. Der Rest kommt aus Default-Methoden. Dieser Weg ist keine Voraussetzung für C2."
```

#### Folie 08.7: Reflection liest die Felder

```text
Reveal.js Prompt: Layout .code-slide, sichtbar als "Anhang · nicht Teil des Kernpfads" markiert. Quelle sichtbar unten: `java/repository-simple/src/main/java/org/lecture/AbstractRepository.java`, Zeilen 126 bis 138, vollständig und verbatim. Highlights 126-127|129-131|132-134|137. Annotation rechts: "Instanz bauen · Annotation lesen · Spaltenwert setzen". Notizen: "Reflection untersucht Klassen zur Laufzeit. Unsere Annotation sagt, welche Felder Datenbankspalten sind. Das spart Code, verschiebt Fehler aber zur Laufzeit."
```

#### Folie 08.8: @Entity und @Column

```text
Reveal.js Prompt: Layout .code-slide, sichtbar als "Anhang · Reflection" markiert. Quelle sichtbar unten: `java/repository-simple/src/main/java/org/lecture/Student.java`, Zeilen 3 bis 21, vollständig und verbatim. Highlights 3|5-9|11-15|17-21. Rechte Annotationen: "Klasse → students", "Felder → id, first_name, last_name, email, student_number, enrollment_date". Notizen: "Die Annotation ist Metadaten. Sie führt keinen SQL-Befehl aus. AbstractRepository liest alle sechs Spaltennamen später mit Reflection."
```

#### Folie 08.9: Generisches create

```text
Reveal.js Prompt: Layout .code-slide, sichtbar als "Anhang · Reflection" markiert. Quelle sichtbar unten: `java/repository-simple/src/main/java/org/lecture/AbstractRepository.java`, Zeilen 43 bis 64, vollständig und verbatim. Highlights 43-46|48-51|53-54|56-63. Annotation rechts: "Felder · Spalten und ? · Bindung · Write". Notizen: "Der Ablauf hängt nicht mehr von Student ab. Reflection liefert Feldnamen und Werte. In Produktionscode müssten wir Typkonvertierung und weitere Fehlerfälle sauberer lösen."
```

#### Folie 08.10: Die Kosten der Reflection

```text
Reveal.js Prompt: Two-column content, sichtbar als "Anhang · Reflection" markiert. Links green "weniger CRUD-Code, einheitliches Mapping". Rechts amber/red "Laufzeitfehler, private Felder, Typkonvertierung, verstecktes SQL". Fragmente einzeln. Notizen: "Das Beispiel soll Mechanik zeigen, nicht Hibernate nachbauen. Reflection tauscht sichtbaren Code gegen Konventionen. Frameworks investieren viel Arbeit in die schwierigen Ränder."
```

#### Folie 08.11: Das macht Spring Data für euch

```text
Reveal.js Prompt: Two-column mapping. Links `AbstractRepository<Student>` mit all/get/create/update/delete, rechts `JpaRepository<StudentJpaEntity, String>` mit findAll/findById/save/delete. Fragmente: Methoden paarweise verbinden, Schluss amber "gleiche Idee, robuste Umsetzung". Notizen: "Spring Data erzeugt die konkrete Repository-Implementierung. Die Grundidee habt ihr selbst gebaut. Morgen sehen wir diese eine Interface-Zeile im Spring-Projekt."
```

#### Folie 08.12: ORM in zwei Sätzen

```text
Reveal.js Prompt: Content slide. Exakter Text groß: "ORM bildet Objekte auf relationale Daten ab. Es entfernt weder das Schema noch die Kosten einer Query." Visual: Java-Objekt ↔ blue Tabelle, amber Query-Lupe. Fragmente: Satz 1, Satz 2. Notizen: "Object-Relational Mapping übernimmt Zuordnung und viele CRUD-Queries. Joins, Keys und Transaktionen bleiben real. Wer SQL versteht, kann ORM-Verhalten prüfen."
```

#### Folie 08.13: IF NOT EXISTS kennt keine Geschichte

```text
Reveal.js Prompt: Timeline SVG mit sichtbarem Hinweis "Hypothetischer Release-Verlauf, keine vorhandenen Migrationsdateien". Links Datenbank A `Version 1: students ohne phone`, rechts Datenbank B `Neuinstallation: students mit phone`. Fragment 1 derselbe Befehl `CREATE TABLE IF NOT EXISTS students (...)` läuft auf beiden, Fragment 2 auf A bleibt `phone` red fehlend, Fragment 3 Frage "Welche Schema-Version läuft?" amber. Notizen: "IF NOT EXISTS erstellt eine fehlende Tabelle, verändert aber keine vorhandene. Die nächste Folie zeigt die tatsächlichen drei Flyway-Dateien des Spring-Beispiels."
```

#### Folie 08.14: Flyway nummeriert Schemaänderungen

```text
Reveal.js Prompt: Content slide with file timeline. Quelle sichtbar unten: `java/rest-simple/src/main/resources/db/migration/`. Drei gleich breite Dateikarten mit den vorhandenen Namen `V1__Create_student_table.sql` → `V2__Create_admin_table.sql` → `V3__Create_refresh_token_table.sql`. Fragmente: 1 V1, 2 V2, 3 V3, 4 green Tabelle `flyway_schema_history` mit Spalten `version`, `description`, `checksum`, `success`. Notizen: "Diese drei Dateien existieren im größeren Spring-Beispiel. Flyway führt offene Versionen in Reihenfolge aus und protokolliert Checksummen. Eine angewandte Version ändern wir nicht nachträglich."
```

#### Folie 08.15: (Reserve) Eine Transaktion ist eine Einheit

```text
Reveal.js Prompt: Content slide mit SVG, markiert "(Reserve)". Sichtbarer Hinweis: "Hypothetischer Fachfall, `course capacity` steht nicht im Repository-Schema". Grundzustand: zwei blue Writes `INSERT enrollment` und `UPDATE course capacity` innerhalb eines amber Rahmens `BEGIN ... COMMIT`. Fragmente: 1 erster Write green, 2 zweiter Write red, 3 `ROLLBACK` nimmt beide zurück, 4 beide erfolgreich und `COMMIT` green. Folientext: "ganz oder gar nicht". Notizen: "Eine Transaktion verbindet mehrere Writes zu einer Einheit. Bei einem Fehler macht ROLLBACK die bisherigen Änderungen rückgängig. Diese Folie entfällt zuerst und wird nicht geübt."
```

#### Folie 08.16: B4, Repository-Grenze freiziehen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "B4 · Kernauftrag", Timer "15 min". Dateipfad unten: `exercises/<domain>/b4-repository/`. Mitte mit vier nummerierten Schritten: 1 "Verschiebt SQL und Row Mapping aus dem Aufrufer ins Repository." 2 "Lasst den Aufrufer nur das Repository-Interface kennen." 3 "Prüft findById und findAll erneut." 4 "Sichert Repository und Migration für C2." Abgabe: "Aufrufer ohne SQL, grüner Test und Übergabestand". Keine Fragmente. Notizen: "Diese Übung ist verpflichtend. Der vorbereitete Zwischenstand enthält das fertige Refactoring, falls eine Gruppe blockiert ist. Reflection bleibt Anhang und ist keine Voraussetzung für C2."
```

#### Folie 08.17: Spring Data und Flyway bauen auf derselben Idee

```text
Reveal.js Prompt: Layout citation-slide. Visual: eigenes AbstractRepository links, Spring Data JpaRepository rechts, Flyway-History darunter. Text: "CRUD als Repository · Schema als Versionsfolge". Quellen: "https://docs.spring.io/spring-data/jpa/reference/repositories/definition.html" und "https://documentation.red-gate.com/flyway/flyway-concepts/migrations/flyway-schema-history-table". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Spring Data dokumentiert CRUD-Repositories als gemeinsame Basis. Flyway speichert angewandte Migrationen mit Checksumme und Status. Unser Java-Zugriff ist sauber, aber noch nicht von außen erreichbar."
```

### Übung

B4, "Repository-Grenze freiziehen", ist ein verpflichtender 15-minütiger Kernauftrag in `exercises/<domain>/b4-repository/`. Die Gruppe verschiebt SQL und Row Mapping aus dem Aufrufer ins Repository, lässt den Aufrufer nur das Interface kennen und prüft `findById` und `findAll`. Repository und Migration werden als Übergabestand für C2 gesichert. Ein vorbereiteter Zwischenstand verhindert, dass eine blockierte Gruppe den Anschluss verliert. Debrief-Fragen: Welche Klasse kennt SQL? Was musste fachlich bleiben? Welche Dateien gehen in C2 mit? Exakter Folientext steht auf Folie 08.16.

### Code

Zuerst wird `java/cursor-simple/src/main/java/org/lecture/MainRefactored.java` gezeigt. `listAllStudents` in den Zeilen 101 bis 124 und `readStudent` in den Zeilen 126 bis 150 rufen dieselbe Methode auf. `rowToStudent` in den Zeilen 152 bis 161 lautet verbatim:

```java
private static Student rowToStudent(ResultSet resultSet) throws SQLException {
    return new Student(
            resultSet.getInt("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email"),
            resultSet.getString("student_number"),
            LocalDate.parse(resultSet.getString("enrollment_date"))
    );
}
```

Danach folgen `java/repository-simple/src/main/java/org/lecture/Entity.java` vollständig, `Column.java` vollständig und `Student.java` vollständig. Aus `java/repository-simple/src/main/java/org/lecture/AbstractRepository.java` kommen in dieser Reihenfolge: Interface-Kopf 12 bis 17, `all()` 18 bis 30, `get()` 32 bis 41, `create()` 43 bis 64, `update()` 66 bis 85, `getTableName()` 96 bis 102 und `fromResultSet()` 126 bis 138. Der letzte Ausschnitt lautet verbatim:

```java
T instance = getClassType().getDeclaredConstructor().newInstance();

for (Field field : getClassType().getDeclaredFields()) {
    Column column = field.getAnnotation(Column.class);
    if (column != null) {
        field.setAccessible(true);
        field.set(instance, resultSet.getObject(column.name()));
    }
}

return instance;
```

Als Brücke zu Tag 3 wird `java/rest-simple/src/main/java/com/example/restsimple/adapter/out/persistence/StudentJpaRepository.java`, Zeilen 1 bis 17, gezeigt. Der Kern in den Zeilen 11 bis 17 lautet verbatim:

```java
public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, String> {
    @Query("SELECT s FROM StudentJpaEntity s WHERE s.id = :id")
    Optional<StudentJpaEntity> findByIdColumn(@Param("id") String id);

    @Query("DELETE FROM StudentJpaEntity s WHERE s.id = :id")
    @Modifying
    void deleteByIdColumn(@Param("id") String id);
}
```

## 09 HTTP, REST und OpenAPI (Dateiname decks/09-http-rest-openapi.html)

### Ziel

Die Studierenden entwerfen eine HTTP-API mit Ressourcen, passenden Methoden, Statuscodes und JSON-Nachrichten. Sie unterscheiden Vertrag und Implementierung und beschreiben OpenAPI als maschinenlesbaren Vertrag. Das Repository macht Daten intern zugänglich. Jetzt planen wir den Zugriff für andere Programme. Im nächsten Deck setzt Spring diesen Vertrag um.

### Position auf der roten Linie

Vorher: `Wiederholungen beenden: Repository Pattern`. Aktuell: `Andere zugreifen lassen: REST API mit Spring`. Als Nächstes: `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware`.

### Erzählung

Bis gestern musste jemand unser Java-Programm auf demselben Rechner starten. Heute sitzt der Client auf der anderen Seite einer Verbindung. Er kennt unsere Klassen nicht. Er kennt Methode, URL, Header und Body.

Ich schreibe nicht `/getAllStudents` in die URL. Die Ressource heißt `/api/students`, das HTTP-Verb trägt die Aktion. Das hält Namen stabil und lässt Statuscodes ihre Arbeit tun. Ein erfolgreiches `POST` liefert 201, ein erfolgreiches `DELETE` meist 204.

REST ist kein Synonym für "JSON über HTTP". Wir modellieren Ressourcen, nutzen die Semantik von Methoden und halten Requests zustandslos. Für diesen Kurs reicht diese praktische Linie. Wir bauen keine Debatte über den perfekten REST-Reifegrad.

OpenAPI ist der Vertrag. Ein Client kann Endpoints sehen, Schemas prüfen und Requests erzeugen, ohne den Java-Code zu lesen. GitHub veröffentlicht seine REST API als OpenAPI-Beschreibung. Bei Stripe sehen wir, warum Idempotency Keys bei wiederholten POST-Requests wichtig werden.

### Leitfragen

- Frage: "Was unterscheidet GET /api/students/7 von GET /api/students?" Erwartet: Einzelressource gegen Collection. Typisch falsch: nur die Antwortgröße; die erste URL ist eine Aktion.
- Frage: "Welcher Status passt zu einem neu angelegten Student?" Erwartet: 201 Created. Typisch falsch: immer 200; 204 mit JSON-Body.
- Frage: "Ist PUT idempotent?" Erwartet: Derselbe Request hat bei Wiederholung denselben beabsichtigten Zustand. Typisch falsch: PUT darf nur einmal gesendet werden; idempotent heißt ohne Datenbankwrite.

### Realitätsbezug

GitHub beschreibt seine REST API vollständig mit einem öffentlich verfügbaren OpenAPI-Dokument und erzeugt daraus Referenzdokumentation und SDKs. Stripe akzeptiert für POST einen Idempotency Key, damit ein Retry nicht versehentlich ein zweites Objekt erzeugt.

### Folien

#### Folie 09.1: HTTP, REST und OpenAPI

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Andere Programme greifen zu". Sichtbares Label "Leitfragen", darunter: "Wie wird unser Repository zu einem Vertrag für Clients?" und "Welche Antwort gilt bei Erfolg und bei Fehlern?" Visual: Client links, HTTP-Umschlag amber, API und Datenbank blue rechts. Notizen: "Heute verlässt unsere Anwendung den lokalen Prozess. Wir entwerfen erst den Vertrag und implementieren ihn danach mit Spring."
```

#### Folie 09.2: Andere dürfen zugreifen

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 6 erledigt in blue; aktiv `Andere zugreifen lassen: REST API mit Spring` in amber; als Nächstes `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware` mit gestricheltem blue Rahmen. Text unten: "Vorher: Repository Pattern · Jetzt: HTTP-Vertrag · Danach: solide API". Keine Fragmente. Notizen: "Das Repository bleibt hinter der API. HTTP bringt neue Fehlerfälle und eine öffentliche Sprache. Danach sichern wir diese Grenze ab."
```

#### Folie 09.3: Was sieht ein Client?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Welche vier Teile braucht ein HTTP-Request?" Fragment 1 "Methode · URL · Header · Body" amber. Notizen: "Erwartet sind diese vier Teile. Typisch falsch sind Java-Methode und Datenbanktabelle. Ein Client sieht unseren internen Code nicht."
```

#### Folie 09.4: Request und Response

```text
Reveal.js Prompt: Two-lane SVG. Oben Client → Server mit `POST /api/students`, `Content-Type`, JSON. Unten Server → Client mit `201`, `Location: /api/students/42`, JSON. Fragmente: Requestteile, Verarbeitung, Responseteile. Notizen: "HTTP ist ein Austausch klarer Nachrichten. Status und Header gehören genauso zum Vertrag wie JSON. Die Datenbank bleibt hinter dem Server."
```

#### Folie 09.5: Methoden tragen Absicht

```text
Reveal.js Prompt: Five-card content: GET lesen, POST anlegen, PUT vollständig ersetzen, PATCH teilweise ändern, DELETE löschen. Fragmente einzeln, Methoden amber, Ressourcen blue. Notizen: "Die Methode ist Teil der Semantik. Unser Pflichtumfang nutzt GET, POST, PUT und DELETE. PATCH bleibt für später."
```

#### Folie 09.6: Ressourcen sind Nomen

```text
Reveal.js Prompt: Two-column content. Green `/api/students`, `/api/students/42`, `/api/courses/7/enrollments`; red `/getStudents`, `/createStudent`, `/doDelete`. Fragmente paarweise. Notizen: "URLs benennen Ressourcen und Collections. Das Verb steht bereits in HTTP. Verschachtelte URLs nutzen wir nur, wenn die Beziehung fachlich klar ist."
```

#### Folie 09.7: Collection und Einzelressource

```text
Reveal.js Prompt: SVG `/api/students` als blue Stapel, `/api/students/42` als einzelne Karte. Fragmente: GET Collection, POST Collection, GET/PUT/DELETE Einzelressource. Notizen: "POST geht an die Collection, weil der Server die neue Identität erzeugt. Operationen an einer bekannten Identität gehen an die Einzelressource."
```

#### Folie 09.8: Statuscodes sind Teil des Vertrags

```text
Reveal.js Prompt: Grid mit 200 OK, 201 Created, 204 No Content green; 400 Bad Request, 404 Not Found, 409 Conflict amber; 500 Internal Server Error red. Fragmente nach Gruppen. Notizen: "Statuscodes erlauben Clients eine Entscheidung ohne Textanalyse. 400 ist ungültige Eingabe, 409 ein Konflikt mit dem aktuellen Zustand. 500 verrät keinen internen Stacktrace."
```

#### Folie 09.9: Ein POST von Anfang bis Ende

```text
Reveal.js Prompt: Pipeline JSON Request → Validation → Repository → new Student → `201 Created`. Fragmente: Schritte nacheinander, Fehlerzweig Validation → 400 red, Unique-Konflikt → 409 amber. Notizen: "Ein Endpoint hat mehr als einen Ausgang. Der Vertrag nennt Erfolg und erwartete Fehler. Genau diese Zweige testen wir später."
```

#### Folie 09.10: JSON ist die Nachricht, nicht die Entity

```text
Reveal.js Prompt: Two-column content. Links Request `{"firstName":"Aylin","lastName":"Kaya","email":"..."}`. Rechts Response ergänzt `id`, `studentNumber`, `enrollmentDate`. Fragment 1 servergenerierte Felder amber, Fragment 2 internes Feld durchgestrichen red. Notizen: "Request und Response brauchen nicht dieselbe Form. Clients dürfen keine IDs oder internen Flags setzen, nur weil die Entity sie hat."
```

#### Folie 09.11: Idempotency

```text
Reveal.js Prompt: SVG gleicher PUT zweimal → ein Zustand green; gleicher DELETE zweimal → Zustand bleibt gelöscht, zweite Antwort kann 404 sein; POST zweimal → zwei Ressourcen red. Fragmente pro Fall. Notizen: "Idempotent beschreibt die beabsichtigte Zustandswirkung, nicht identische Antworten. GET, PUT und DELETE sind idempotent. POST ist es ohne Idempotency Key meist nicht."
```

#### Folie 09.12: Header tragen Metadaten

```text
Reveal.js Prompt: Content slide. Requestkarte mit `Content-Type: application/json`, `Accept: application/json`, `Authorization`, `X-Correlation-ID`. Fragmente einzeln; sensible Authorization rot maskiert. Notizen: "Header beschreiben Darstellung, Authentifizierung und Nachverfolgung. Sie gehören nicht in jedes JSON-Fachobjekt. Correlation ID kommt in Deck 12 zurück."
```

#### Folie 09.13: OpenAPI beschreibt den Vertrag

```text
Reveal.js Prompt: SVG OpenAPI-Datei in Mitte, Pfeile zu Swagger UI, Clientgenerator, Contract Test und Mensch. Fragmente nacheinander, Datei amber. Text: "maschinenlesbar und menschenlesbar". Notizen: "OpenAPI nennt Pfade, Methoden, Schemas und Antworten. Swagger UI ist eine Oberfläche dafür, nicht das Format selbst."
```

#### Folie 09.14: Ein kleiner OpenAPI-Ausschnitt

```text
Reveal.js Prompt: Layout .code-slide. Quellenhinweis sichtbar: "Gekürztes Lehrbeispiel zum OpenAPI-Endpunkt aus `java/rest-simple-exercise/README.md`, Zeilen 30 bis 35. Kein gespeicherter Repo-Ausschnitt." YAML: `paths:\n  /api/students:\n    post:\n      responses:\n        '201':\n          description: Student created\n        '400':\n          description: Invalid request`. Highlights 1-3|4-8. Notizen: "Der Vertrag zeigt zwei Ausgänge. Das Skeleton liefert OpenAPI unter `/v3/api-docs`. SpringDoc erzeugt die Beschreibung zur Laufzeit, deshalb gibt es dafür keine YAML-Datei im Repository."
```

#### Folie 09.15: GitHub und Stripe

```text
Reveal.js Prompt: Two-column content. Links GitHub OpenAPI → docs und Octokit; rechts Stripe POST mit `Idempotency-Key` → sicherer Retry. Fragmente: GitHub, Stripe, amber Satz "Vertrag und Wiederholung sind Produktionsfragen". Notizen: "GitHub veröffentlicht seine OpenAPI-Beschreibung und erzeugt daraus Dokumentation und SDKs. Stripe speichert das erste Ergebnis pro Idempotency Key, damit ein Retry kein Doppelobjekt anlegt."
```

#### Folie 09.16: C1, GET und POST auf Papier

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "C1 · Kernauftrag", Timer "25 min". Dateipfad unten: `exercises/<domain>/c1-http-contract/`. Mitte mit vier nummerierten Schritten: 1 "Verwendet dieselbe flache Hauptressource wie in B3 und B4." 2 "Plant GET /api/<resources> und POST /api/<resources>." 3 "Schreibt Request- und Response-JSON." 4 "Legt 200, 201, 400 und genau einen 409-Fachkonflikt fest." Abgabe: "Endpoint-Tabelle und JSON-Beispiele". Keine Fragmente. Notizen: "Schreibt noch keinen Controller. Beziehungen und weitere Methoden sind Vertiefung. Der Fehlerkörper enthält mindestens code und message."
```

#### Folie 09.17: Debrief, Vertrag ohne Java

```text
Reveal.js Prompt: Content slide with review matrix "Ressource als Nomen? · Methode passend? · Status eindeutig? · JSON ohne interne Felder?" Fragmente einzeln. Notizen: "Eine andere Gruppe soll euren Vertrag ohne Erklärung lesen. Wo sie raten muss, fehlt Information. Diese Tabelle wird morgen zur Controller-Checkliste."
```

#### Folie 09.18: GitHub veröffentlicht seinen API-Vertrag

```text
Reveal.js Prompt: Layout citation-slide. Visual: `openapi.yaml` in amber, Pfeile zu GitHub REST Docs und Octokit SDK. Text: "GitHubs REST API ist vollständig als OpenAPI beschrieben." Quellen: "https://docs.github.com/en/rest/about-the-rest-api/about-the-openapi-description-for-the-rest-api" und "https://docs.stripe.com/api/idempotent_requests". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "GitHub erzeugt aus OpenAPI Referenzdokumentation und SDKs. Stripe zeigt den nächsten Randfall: sichere Wiederholung. Jetzt implementieren wir unseren Vertrag mit Spring Boot."
```

### Übung

C1, "GET und POST auf Papier", dauert 25 Minuten. Die verbindliche Karte liegt in `exercises/<domain>/c1-http-contract/`. Die Gruppe verwendet dieselbe flache Hauptressource wie in B3 und B4 und entwirft GET und POST unter `/api`. Der Vertrag enthält Request- und Response-JSON, 200, 201, 400 und genau einen 409-Fachkonflikt. Beziehungen und weitere Methoden sind Vertiefung. Abgabe sind die Endpoint-Tabelle und JSON-Beispiele. Debrief-Fragen: Ist jede URL ein Nomen? Wo unterscheiden sich Request und Response? Ist der Fehlerkörper stabil? Exakter Folientext steht auf Folie 09.16.

## 10 Spring Boot (Dateiname decks/10-spring-boot.html)

### Ziel

Die Studierenden übernehmen Migration, Repository und Row Mapping aus B4 in die Spring-Anwendung. Sie starten einen Health-Endpoint und implementieren GET für Collection und vorhandene ID mit `findAll` und `findById`. Sie erklären Dependency Injection (Abhängigkeitsinjektion) und ordnen Spring-Annotationen den bekannten Grenzen zu. POST folgt erst in C3.

### Position auf der roten Linie

Vorher: `Wiederholungen beenden: Repository Pattern`. Aktuell: `Andere zugreifen lassen: REST API mit Spring`. Als Nächstes: `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware`.

### Erzählung

Ein Framework nimmt uns wiederkehrende Verkabelung ab. Es startet einen Webserver, liest Konfiguration, findet Controller und baut Objekte. Es nimmt uns nicht die Entscheidung ab, welche Ressource existiert und welcher Statuscode stimmt.

Dependency Injection ist in einem Bild erklärt: Der Controller braucht ein Repository. Er baut es nicht selbst. Spring erstellt beide und übergibt das Repository im Konstruktor. Dadurch kann ein Test später ein anderes Objekt einsetzen.

Die Annotationen sind Etiketten auf bekannten Ideen. `@RestController` markiert den HTTP-Rand, `@GetMapping` verbindet Methode und Pfad, `@Repository` markiert den Datenzugriff. Row Mapper und Repository-Port stammen unverändert aus B4. JPA und `JpaRepository` bleiben als alternativer Adapter in der Vertiefung sichtbar.

Im Live-Teil gehe ich in kleinen Schritten: Anwendung starten, Migration und Repository übernehmen, Seed-Datensatz lesen, GET Collection und GET per ID über Swagger UI prüfen. Nach jedem Schritt gibt es eine sichtbare Antwort.

### Leitfragen

- Frage: "Wer ruft den Konstruktor von StudentController auf?" Erwartet: Spring erstellt und verbindet die Beans. Typisch falsch: der Browser; das Repository selbst.
- Frage: "Welche Teile aus B4 bleiben erhalten?" Erwartet: Migration, Repository-Port, JDBC-Adapter und Row Mapping. Typisch falsch: Wir beginnen mit einer neuen JPA-Entity.
- Frage: "Welche Repository-Methode gehört zu GET Collection und GET per ID?" Erwartet: `findAll` und `findById`. Typisch falsch: `save` für beide.

### Realitätsbezug

Spring Data JPA erzeugt Implementierungen für Repository-Interfaces. Die offizielle Dokumentation zeigt, dass CRUD-Methoden aus den Basisschnittstellen bereitgestellt werden.

### Folien

#### Folie 10.1: Spring Boot

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Vom Vertrag zum laufenden Endpoint". Sichtbares Label "Leitfragen", darunter: "Wie übernehmen wir Migration und Repository aus dem letzten Stand?" und "Welche Spring-Bausteine machen daraus zwei GET-Endpunkte?" Visual: Spring-Box verbindet HTTP, Java und blue Datenbank. Notizen: "Heute starten wir mit einem leeren Health-Endpoint. Danach binden wir Migration und Repository aus B4 ein und lesen dieselbe Ressource über zwei GET-Endpunkte."
```

#### Folie 10.2: REST mit Spring

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 6 erledigt in blue; aktiv `Andere zugreifen lassen: REST API mit Spring` in amber; als Nächstes `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware` mit gestricheltem blue Rahmen. Text unten: "Vorher: API auf Papier · Jetzt: Spring-Implementierung · Danach: klare Schichten". Keine Fragmente. Notizen: "Der Vertrag aus Deck 09 führt. Spring liefert Server, Routing und Integration. Danach prüfen wir, welche Klasse welche Verantwortung tragen soll."
```

#### Folie 10.3: Was übernimmt ein Framework?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Welche Arbeit soll Spring für uns wiederholen?" Fragment 1 "Server starten · Objekte bauen · Requests routen · JSON wandeln". Notizen: "Erwartet ist Infrastrukturarbeit. Typisch falsch sind Fachregeln und Datenmodell. Spring nimmt Mechanik ab, nicht Entscheidungen."
```

#### Folie 10.4: Der Stand aus B4 kommt mit

```text
Reveal.js Prompt: Drei Karten mit dem sichtbaren Label "Übergabe statt Neustart": 1 "Migration: V1__schema.sql und Seed-Daten übernehmen", 2 "Repository: Interface, JDBC-Implementierung und Row Mapping kopieren", 3 "Starttest: vorhandenen Datensatz mit findById lesen". Notizen: "Der Spring-Starter setzt auf dem B4-Stand auf. Erst wenn Migration und Repository denselben Seed-Datensatz lesen, ergänzen wir den Controller."
```

#### Folie 10.5: Ohne und mit Spring

```text
Reveal.js Prompt: Two-column content. Links graue Liste Socket, HTTP parser, routing, JSON, lifecycle; rechts blue `@RestController`, `@GetMapping`, Jackson, Bean lifecycle. Fragmente paarweise. Notizen: "Wir könnten alles selbst bauen. Das wäre für diesen Kurs nur Arbeit an der falschen Stelle. Das Framework standardisiert die wiederkehrenden Teile."
```

#### Folie 10.6: Dependency Injection

```text
Reveal.js Prompt: Precise SVG. Box Spring Container oben. Er erzeugt `StudentRepository` und `StudentController`, dann amber Pfeil "Konstruktorparameter" vom Repository zum Controller. Controller zeigt keine `new`-Anweisung. Fragmente: Bedarf, Erzeugung, Injection, Request. Notizen: "Dependency Injection bedeutet hier: Der Controller erhält seine Abhängigkeit. Er entscheidet nicht über deren Bau. Diese Trennung macht Austausch und Tests möglich."
```

#### Folie 10.7: Der Startpunkt

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/rest-simple-exercise/src/main/java/com/example/restsimple/DemoApplication.java`, Zeilen 6 bis 13, gekürzt um Leerzeilen und mit diesem Hinweis markiert. Code verbatim ohne Leerzeilen: `@SpringBootApplication\npublic class DemoApplication {\n    public static void main(String[] args) {\n        SpringApplication.run(DemoApplication.class, args);\n    }\n}`. Highlights 1|2-5|4. Notizen: "Diese Annotation bündelt Konfiguration und Component Scan. `run` baut den Application Context und startet den Webserver. Der Package-Ort bestimmt, was gefunden wird."
```

#### Folie 10.8: Annotationen auf gestern abbilden

```text
Reveal.js Prompt: Two-column mapping. `@RestController`→HTTP-Rand, `@GetMapping`→Methode+Pfad, `@Service`→Use Case, `@Repository`→Datenzugriff, `Row Mapper`→Zeile zu Objekt, `Repository-Port`→Grenze zum Adapter. Fragmente Zeile für Zeile. Notizen: "Keine Annotation erfindet ein neues Fachkonzept. Wir hängen bekannte Aufgaben an Springs Laufzeit. Diese Karte bleibt für Deck 11 sichtbar."
```

#### Folie 10.9: Ein Health-Endpoint

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/rest-simple-exercise/src/main/java/com/example/restsimple/controller/StudentController.java`, Zeilen 17 bis 29, vollständig und verbatim. Code zeigt `@GetMapping("/health")`, OpenAPI-Annotationen und die Response-Map. Highlights 17|18-22|23-29. Notizen: "Der Endpoint braucht noch keine Datenbank. Wir prüfen Routing, JSON und Port. Sein vollständiger Pfad ist `/api/students/health`, weil die Klasse in Zeile 13 das Präfix setzt."
```

#### Folie 10.10: application.properties

```text
Reveal.js Prompt: Layout .code-slide. Zeige `spring.datasource.url`, SQLite-Treiber, `spring.datasource.hikari.connection-init-sql=PRAGMA foreign_keys=ON`, Zugangsdaten, `spring.flyway.enabled=true`, `server.port=${SERVER_PORT:8081}` sowie die drei SpringDoc-Einstellungen. Rechte Annotationen: `Flyway für die vorhandene Migration`, `Fremdschlüssel pro Connection`, `Port 8081 als Default`, `OpenAPI und Swagger UI`. Notizen: "Flyway spielt die vorhandene Migration ein. Hikari führt das SQLite-PRAGMA für jede neue Connection aus. Swagger UI liegt unter `/swagger-ui.html`."
```

#### Folie 10.11: Starten und prüfen

```text
Reveal.js Prompt: Terminal-Content-Slide. Quellen sichtbar unten: `java/rest-simple-exercise/README.md`, Zeilen 14 bis 35. Grundzustand exakt `cd java/rest-simple-exercise` und `make run`; der zweite Befehl steht verbatim in README-Zeile 18, der Verzeichniswechsel ist als Arbeitskontext markiert. Fragment 1 green Label `Server auf :8081`. Fragment 2 exakte URL `http://localhost:8081/api/students/health`. Fragment 3 Response-JSON mit `status`, `service` und `timestamp`, ohne erfundene feste Timestamp. Notizen: "Wir warten auf den gestarteten Server. Das README nennt `make run` und die Health-URL. Erst nach der Health-Antwort öffnen wir Swagger UI."
```

#### Folie 10.12: Der erste GET

```text
Reveal.js Prompt: Layout .code-slide. Quellenhinweis sichtbar: "Gekürztes Zielbild aus `java/rest-simple-exercise/README.md`, Zeilen 97 bis 147. Im Skeleton noch nicht vorhanden." Zeige nur die drei Zielkarten `@GetMapping`, `repository.findAll()`, `StudentResponse`, verbunden durch blue Pfeile. Darunter exakter Pfad `GET /api/students` aus README-Zeilen 99 und 147. Keine frei erfundene Methodensignatur. Notizen: "Phase 1 verlangt zunächst zwei bis drei feste Students und ein Response-DTO. Phase 2 ersetzt die feste Liste durch `repository.findAll()`. Der Controller gibt im Kursstandard ein DTO zurück, nicht die Entity."
```

#### Folie 10.13: JPA-Entity

```text
Reveal.js Prompt: Layout .code-slide mit sichtbarem Label "Vertiefung · alternativer Adapter". Quellenhinweis sichtbar: "Gekürztes und auf Student angepasstes Zielbild aus `java/rest-simple-exercise/README.md`, Zeilen 149 bis 208. Im Skeleton noch nicht vorhanden." Code: `@Entity\n@Table(name = "students")\nclass Student {\n    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)\n    private Long id;\n    @Column(name = "email", nullable = false, unique = true)\n    private String email;\n}`. Highlights 1-2|3-5|6-7. Rechte Feldliste für den Kurs: `firstName, lastName, email, studentNumber, enrollmentDate`. Notizen: "Das Snippet ist ein alternativer JPA-Adapter, nicht der C2-Kernpfad."
```

#### Folie 10.14: JpaRepository = AbstractRepository

```text
Reveal.js Prompt: Layout .code-slide mit sichtbarem Label "Vertiefung · statt JDBC-Adapter". Quellenhinweis sichtbar: "Auf Student angepasstes Zielbild aus `java/rest-simple-exercise/README.md`, Zeilen 211 bis 221. Im Skeleton noch nicht vorhanden." Code: `@Repository\npublic interface StudentRepository extends JpaRepository<Student, Long> {\n}`. Fragment 1 ererbte Methoden `findAll`, `findById`, `save`, `deleteById`, `existsById`. Fragment 2 zeigt `AbstractRepository<Student>` als kleine blue Karte. Notizen: "Spring Data stellt die alternative Implementierung bereit. C2 verwendet den übernommenen JDBC-Adapter."
```

#### Folie 10.15: GET per ID

```text
Reveal.js Prompt: Layout .code-slide mit sichtbarem Label "C2 · Kernpfad". Code: `@GetMapping("/{id}")\nStudentResponse findById(@PathVariable long id) {\n    return repository.findById(id)\n            .map(StudentResponse::from).orElseThrow();\n}`. Quellenhinweis: "Zielbild für den vorhandenen Repository-Port aus B4. Das Starterprojekt enthält die Methodengerüste." Notizen: "Der Controller delegiert die Suche an findById. Das Row Mapping bleibt im JDBC-Adapter aus B4. Nach außen geht ein Response-DTO."
```

#### Folie 10.16: Request durch die Anwendung

```text
Reveal.js Prompt: SVG Swagger UI → Controller → Repository → JDBC-Adapter → SQLite. Fragmente je Pfeil blue, Rückweg JSON green. Notizen: "Jeder Pfeil entspricht einem bekannten Begriff. Der JDBC-Adapter erzeugt SQL. Der Controller sieht den Repository-Port, nicht die JDBC-Details."
```

#### Folie 10.17: Swagger UI ist ein Client

```text
Reveal.js Prompt: Content slide with browser wireframe `localhost:8081/swagger-ui.html`. Fragmente: `GET /api/students/42` aufklappen, Response-JSON mit ID und Fachfeldern, Execute, 200 Response. Amber Hinweis "Vertrag testen, nicht Datenbank ansehen". Notizen: "Swagger UI liest OpenAPI und sendet echte HTTP-Requests. Es ist unser erster Client. Prüft Status und Body."
```

#### Folie 10.18: Vier Live-Checkpoints

```text
Reveal.js Prompt: Timeline "health 200" → "Seed 42" → "GET Liste" → "GET /42 200". Fragmente mit green Haken. Fehlerabzweig Port belegt red. Notizen: "Erst startet die Anwendung, dann lesen Collection und Einzelpfad denselben Seed-Datensatz. Das verkürzt die Fehlersuche."
```

#### Folie 10.19: C2, vorhandene Ressource lesen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "C2 · Kernauftrag", Timer "50 min". Dateipfad unten: `exercises/<domain>/c2-spring-resource/`. Mitte mit vier nummerierten Schritten: 1 "Übernehmt Migration und Repository aus B4." 2 "Startet den Test, der einen Seed-Datensatz liest." 3 "Implementiert GET Collection mit findAll." 4 "Implementiert GET per ID mit findById." Abgabe: "GET 200 für Collection und vorhandene ID". Keine Fragmente. Notizen: "Das Starterprojekt enthält Controller- und Response-DTO-Gerüste. Nach der Kernzeit kann der vorbereitete Zwischenstand freigegeben werden. POST folgt in C3."
```

#### Folie 10.20: Debrief, welche Annotation ersetzt was?

```text
Reveal.js Prompt: Content slide. Sechs Karten ohne Erklärung; Fragmente decken Zuordnung aus 10.8 auf. Schlussfrage amber "Welche SQL-Query lief?". Notizen: "Eine Gruppe verfolgt GET per ID vom Mapping bis SQLite. Wer kann, loggt die SELECT-Abfrage und gleicht sie mit findById ab."
```

#### Folie 10.21: Spring Data erzeugt Repository-Implementierungen

```text
Reveal.js Prompt: Layout citation-slide. Visual: einzeiliges `JpaRepository<Student, Long>` links, `SimpleJpaRepository` als erzeugte blue Implementierung rechts. Text: "CRUD-Methoden kommen aus den Repository-Basisschnittstellen." Quelle: "https://docs.spring.io/spring-data/jpa/reference/repositories/definition.html". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Die Spring-Dokumentation beschreibt, wie passende Methoden an die Basisimplementierung geroutet werden. Unser AbstractRepository war das Modell in klein. Jetzt ordnen wir den Code in klare Verantwortungen."
```

### Übung

C2, "Vorhandene Ressource lesen", dauert 50 Minuten. Die Gruppe arbeitet in `exercises/<domain>/c2-spring-resource/`, übernimmt Migration und Repository aus B4 und startet mit einem Test gegen den Seed-Datensatz. Danach implementiert sie GET Collection mit `findAll` und GET per ID mit `findById`. Abgabe sind zwei 200-Antworten für Collection und vorhandene ID. POST, Beziehungen und weitere Methoden folgen später oder bleiben Vertiefung. Debrief-Fragen: Welche Annotation routet HTTP? Wer stellt das Repository bereit? Welche SELECT-Abfrage lief? Exakter Folientext steht auf Folie 10.19.

### Code

Gezeigt werden in dieser Reihenfolge: `java/rest-simple-exercise/src/main/java/com/example/restsimple/DemoApplication.java` Zeilen 1 bis 13 vollständig; `java/rest-simple-exercise/src/main/java/com/example/restsimple/controller/StudentController.java` Zeilen 12 bis 29 für Health; die Datasource-, Flyway-, Port- und SpringDoc-Einstellungen aus `application.properties`; danach die Controller- und Response-DTO-Gerüste aus `exercises/<domain>/c2-spring-resource/starter/`. Migration, Repository-Port, JDBC-Adapter und Row Mapping stammen aus B4.

`StudentJpaRepository` und die JPA-Entity bleiben als klar markierter alternativer Adapter in der Vertiefung. Sie ersetzen nicht den C2-Übergabestand. Der Kursvertrag verwendet `firstName`, `lastName`, `email`, `studentNumber` und `enrollmentDate`. Deck 11 ergänzt POST, DTOs, Validation und genau einen 409-Konflikt.

## 11 Gutes Anwendungsdesign (Dateiname decks/11-good-design.html)

### Ziel

Die Studierenden trennen Presentation (HTTP-Darstellung), Service (Anwendungslogik) und Repository, ohne für jeden trivialen Fall eine leere Service-Klasse zu erzwingen. Sie verwenden DTOs (Data Transfer Objects, Datentransferobjekte), `@Valid` und ein gemeinsames Fehlerformat. Die Spring-API läuft. Jetzt machen wir ihre Grenze klar und belastbar. Danach sichern Tests, Logging und Betriebsendpunkte das Verhalten ab.

### Position auf der roten Linie

Vorher: `Andere zugreifen lassen: REST API mit Spring`. Aktuell: `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware`. Als Nächstes: derselbe Schritt `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware`, dann mit Tests und Middleware.

### Erzählung

Die beiden GET-Endpunkte aus C2 funktionieren. Jetzt ergänzen wir POST für dieselbe flache Hauptressource. Request und Response dürfen dabei nicht an das Persistenzmodell gekoppelt sein. Ein neues internes Feld würde sonst versehentlich Teil des Vertrags.

Drei Schichten reichen für den Kurs: Presentation verarbeitet HTTP, Service koordiniert Fachregeln, Repository speichert. Ich baue aber keine Service-Klasse, die nur `repository.save` weiterreicht und nie eine Regel bekommt. Für kleines CRUD darf der Controller validieren und speichern. Sobald mehrere Repositories, Transaktionen oder Regeln zusammenkommen, lohnt der Service.

DTOs schützen den Vertrag. `CreateStudentRequest` enthält nur erlaubte Eingaben. `StudentResponse` enthält nur zugesagte Ausgaben. `@Valid` stoppt ungültige Daten am Rand. `@ControllerAdvice` übersetzt erwartete Exceptions in ein stabiles `ErrorResponse` mit `code`, `message` und `correlationId`. Der C3-Kern enthält genau einen fachlichen 409-Konflikt.

Das vollständige `rest-simple` zeigt das größere Bild als hexagonale Architektur. Ports sind Interfaces, Adapter verbinden HTTP und JPA. Wir schauen darauf, verlangen es aber nicht in der Übung.

### Leitfragen

- Frage: "Darf der Controller direkt repository.save aufrufen?" Erwartet: Bei einfachem CRUD ja, bei Fachregeln oder Koordination lohnt ein Service. Typisch falsch: nie; immer.
- Frage: "Warum geben wir nicht die JPA-Entity zurück?" Erwartet: Öffentlicher Vertrag und Persistenzmodell sollen getrennt änderbar bleiben. Typisch falsch: Entities können kein JSON; DTOs sind schneller.
- Frage: "Wo wird eine ungültige E-Mail zu HTTP 400?" Erwartet: Validation am Request und zentraler Exception Handler. Typisch falsch: in SQLite zu 500; im Browser.

### Realitätsbezug

Stripe- und GitHub-APIs veröffentlichen stabile Request- und Response-Schemas, nicht ihre internen Datenbankobjekte. Clients programmieren gegen den Vertrag.

### Folien

#### Folie 11.1: Gutes Anwendungsdesign

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Klare Grenzen statt mehr Klassen". Sichtbares Label "Leitfragen", darunter: "Welche Schicht entscheidet über HTTP, Fachregel und SQL?" und "Wie ergänzen wir POST für dieselbe Ressource, die GET bereits liefert?" Visual: drei gestapelte blue Schichten, HTTP oben, Datenbank unten. Notizen: "Wir verbessern eine laufende API. Jede neue Klasse braucht eine konkrete Verantwortung."
```

#### Folie 11.2: Die API solide machen

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 7 erledigt in blue; aktiv `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware` in amber; als Nächstes innerhalb des aktiven Schritts `Tests und Middleware` als gestrichelte blue Unterzeile. Text unten: "Vorher: REST API mit Spring · Jetzt: Schichten, Validierung, Fehler · Danach: Tests und Middleware". Keine Fragmente. Notizen: "Die rote Linie erreicht den letzten Schritt. Heute sichern wir zuerst den Vertrag im Design. Deck 12 prüft und beobachtet ihn."
```

#### Folie 11.3: Brauchen wir immer einen Service?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Ist `return repository.save(dto.toEntity())` schon ein Service?" Fragment 1 "Nein, eine Klasse ohne eigene Entscheidung hilft nicht" amber. Notizen: "Erwartet ist eine abgewogene Antwort. Typisch falsch sind 'jede Architektur braucht drei Klassen' und 'Services sind immer unnötig'. Wir entscheiden nach Verantwortung."
```

#### Folie 11.4: Drei Schichten

```text
Reveal.js Prompt: Precise SVG, Presentation oben mit Controller/DTO, Service Mitte mit Regeln/Transaktion, Repository unten mit JPA/SQLite. Blue Pfeile nur nach unten, Rückgaben nach oben. Fragmente je Schicht. Notizen: "Presentation kennt HTTP. Repository kennt Speicherung. Service koordiniert einen Use Case, wenn mehr als Weiterreichen passiert."
```

#### Folie 11.5: Wann der Service lohnt

```text
Reveal.js Prompt: Two-column content. Green "mehrere Repositories · Fachregel · Transaktion · wiederverwendbarer Use Case". Grey "einfaches validate + save" mit Controller-Pfeil zum Repository. Fragmente paarweise. Notizen: "Für unsere kleinste CRUD-Ressource kann direktes Speichern lesbar sein. Bei Enrollment mit Kapazitätsprüfung und zwei Writes gehört die Koordination in einen Service."
```

#### Folie 11.6: Entity nach außen ist eine Kopplung

```text
Reveal.js Prompt: SVG JPA Entity links mit den Kursfeldern `id`, `firstName`, `lastName`, `email`, `studentNumber`, `enrollmentDate`, `internalNote`; alle Pfeile laufen zunächst red zum Client rechts. Fragment 1 `internalNote` leuchtet red, Fragment 2 DTO-Filter in blue lässt nur `id`, `firstName`, `lastName`, `email`, `studentNumber`, `enrollmentDate` green durch. Notizen: "Eine Entity ändert sich für Datenbankgründe. Ein API-Vertrag ändert sich für Clientgründe. Dasselbe Objekt koppelt beide Änderungsgründe."
```

#### Folie 11.7: Request DTO und Response DTO

```text
Reveal.js Prompt: Two-column content. Links `CreateStudentRequest(firstName,lastName,email)`, rechts `StudentResponse(id,studentNumber,firstName,lastName,email,enrollmentDate)`. Fragmente: servergenerierte Felder amber, Mapping-Pfeile blue. Notizen: "Der Request erlaubt nur Eingaben. Der Response verspricht nur Ausgaben. Unterschiedliche DTOs machen Besitz sichtbar."
```

#### Folie 11.8: @Valid stoppt am Rand

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/rest-simple/src/main/java/com/example/restsimple/adapter/in/dto/CreateStudentRequest.java`, Zeilen 7 bis 18, vollständig und verbatim. Highlights 7|8-13|15-17. Amber Hinweis rechts: "Bestehendes Zielbild nutzt `name`; Kursstandard nutzt `firstName`." Notizen: "Bean Validation prüft Form und einfache Grenzen vor dem Use Case. Der Ausschnitt bleibt wegen der Faktentreue bei `name`. Die Übungsimplementierung verwendet den Kursstandard `firstName`, `lastName`, `email`, `studentNumber`, `enrollmentDate`."
```

#### Folie 11.9: Validierung hat zwei Orte

```text
Reveal.js Prompt: Two-column content. Links blue Randregeln "leer, Länge, Format". Rechts amber Fachregeln "Course voll, Nummer eindeutig, Enrollment offen". Fragment 1 `@Valid`, Fragment 2 Service/DB-Constraint, Fragment 3 green "beide". Notizen: "Syntaxnahe Regeln liegen am Request. Fachliche und konkurrierende Regeln brauchen Service oder Datenbank. Ein Unique Constraint bleibt nötig, auch wenn Java vorher prüft."
```

#### Folie 11.10: Statuscode aus einer Exception

```text
Reveal.js Prompt: SVG `StudentNotFoundException` → `@ControllerAdvice` → `404 ErrorResponse`. Fragmente entlang Pfeil, unerwartete Exception → 500 red. Notizen: "Der Controller muss nicht jeden Fehler lokal fangen. Ein zentraler Handler übersetzt bekannte Exceptions konsistent. Unbekannte Fehler werden geloggt und als knappe 500-Antwort ausgegeben."
```

#### Folie 11.11: @ControllerAdvice

```text
Reveal.js Prompt: Layout .code-slide. Quellenhinweis sichtbar: "Zielcode im C3-Starter: GlobalExceptionHandler.java". Code: `@ExceptionHandler(ResourceNotFoundException.class)\nResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex) {\n    var body = new ErrorResponse(\n            "RESOURCE_NOT_FOUND", ex.getMessage(), correlationId());\n    return ResponseEntity.status(404).body(body);\n}`. Highlights 1|2-4|5. Notizen: "Die Handler-Methode verbindet Exception-Typ, stabilen Body und Status. Ein 500-Handler loggt die technische Ursache, gibt sie aber nicht an den Client zurück."
```

#### Folie 11.12: Ein stabiler Fehlerkörper

```text
Reveal.js Prompt: Two-column content. Quellenhinweis sichtbar: "C3-Starter · ErrorResponse.java". Links als instabil markiert: `{"message":"Student 42 not found","details":null}`. Rechts als stabiler Vertrag: `{"code":"STUDENT_NOT_FOUND","message":"Student 42 not found","correlationId":"4d2a..."}`. Fragmente: Felder nacheinander, danach red Kreuz über Stacktrace. Notizen: "code ist der maschinenlesbare Vertrag. message bleibt für Menschen. Die Correlation ID verbindet Antwort und Log. Bei 500 bleibt die technische Exception ausschließlich im Log."
```

#### Folie 11.13: Statuscodes aus Fachfällen

```text
Reveal.js Prompt: Mapping grid: ungültiges DTO→400, Student fehlt→404, duplicate email→409, erstellt→201, gelöscht→204, unbekannter Fehler→500. Fragmente nach Fall. Notizen: "Wir wählen Status aus dem Fall, nicht aus der Exception-Hierarchie. 409 passt, wenn die Eingabe formal gültig ist, aber mit vorhandenem Zustand kollidiert."
```

#### Folie 11.14: Naming hält Ebenen lesbar

```text
Reveal.js Prompt: Content slide. Kette `CreateStudentRequest` → `createStudent` → `Student` → `StudentJpaEntity` → `students`. Fragmente entlang Kette. Danach erscheinen die vorhandenen `rest-simple`-Namen `name`, `lastName`, `mnr`, `createdOn` in amber und die Kursnamen `firstName`, `lastName`, `email`, `studentNumber`, `enrollmentDate` in blue. Schlussfragment verbindet `studentNumber` mit SQL `student_number` und `enrollmentDate` mit `enrollment_date`. Notizen: "Ein Begriff pro Ding reduziert Übersetzungsfehler. Das größere Repo-Beispiel hat andere Namen und bleibt im Code unverändert. Die Übungsdecks verwenden den Kursstandard."
```

#### Folie 11.15: Hexagonal als größeres Bild

```text
Reveal.js Prompt: SVG Hexagon Domain/Application innen; Ports als blue Buchsen; REST-Adapter links, JPA-Adapter rechts; Spring außen grau. Fragmente: Kern, Ports, Adapter, Dependency-Pfeile nach innen. Text: "gezeigt, nicht gefordert" amber. Notizen: "Das vollständige rest-simple trennt Use Cases von Adaptern. Der Kern kennt HTTP und JPA nicht. Eure Übung darf bei drei Schichten bleiben."
```

#### Folie 11.16: Derselbe POST in drei Schichten

```text
Reveal.js Prompt: Sequence SVG Client → Controller validates/maps → Service checks/coordinates → Repository saves → Response DTO. Fragmente je Hop, Fehlerzweige 400/409/500. Notizen: "Jede Schicht übersetzt eine andere Grenze. Der Controller spricht HTTP, der Service Fachfall, das Repository Persistenz. Bei trivialem CRUD kann der mittlere Hop entfallen."
```

#### Folie 11.17: C3, POST für dieselbe Hauptressource

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "C3 · Kernauftrag", Timer "45 min". Dateipfad unten: `exercises/<domain>/c3-tests-errors/`. Mitte mit vier nummerierten Schritten: 1 "Ergänzt Request- und Response-DTO für POST." 2 "Ergänzt Bean Validation und @Valid." 3 "Setzt genau eine Fachregel als 409 um." 4 "Gebt Fehler als code, message und correlationId aus." Abgabe: "POST 201 sowie ein reproduzierbarer 409". Keine Fragmente. Notizen: "POST verwendet dieselbe flache Hauptressource wie die GET-Endpunkte aus C2. PUT, DELETE und Beziehungen sind Vertiefung. Der vorbereitete Zwischenstand enthält den vollständigen POST-Kernpfad."
```

#### Folie 11.18: Debrief, ein Request durch alle Schichten

```text
Reveal.js Prompt: Content slide. Leere Sequenzkarten Controller, Service optional, Repository, DB. Fragmente: Gruppe trägt Klassenname und Status an jeder Station ein. Schlussfragen "Wo validiert? Wo übersetzt?" amber. Notizen: "Eine Gruppe verfolgt ihren schwierigsten Fehlerfall. Wir prüfen, ob der Response DTO und ErrorResponse stabil bleiben. Wir zählen keine Klassen, sondern Verantwortungen."
```

#### Folie 11.19: Vertragstest gegen Entity-Änderung

```text
Reveal.js Prompt: SVG Entity bekommt internes Feld `createdBy` amber; DTO bleibt unverändert green; direkte Entity-Response zeigt neues Feld red. Fragmente in Reihenfolge. Notizen: "Das ist der konkrete Gewinn der DTO-Grenze. Eine Persistenzänderung wird nicht versehentlich API-Änderung. Tests können den Response-Vertrag festhalten."
```

#### Folie 11.20: Öffentliche APIs veröffentlichen Schemas

```text
Reveal.js Prompt: Layout citation-slide. Visual: GitHub und Stripe als Clientkarten, beide zeigen Request/Response-Schema, interne DB grau verborgen. Text: "Clients programmieren gegen den Vertrag, nicht gegen Tabellen." Quellen vollständig: "https://docs.github.com/en/rest/about-the-rest-api/about-the-openapi-description-for-the-rest-api" und "https://docs.stripe.com/api". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "GitHub veröffentlicht eine OpenAPI-Beschreibung. Stripe dokumentiert Request- und Response-Felder seiner Ressourcen. Beide Verträge bleiben von internen Datenbankobjekten getrennt. Als Nächstes testen und beobachten wir unseren Vertrag."
```

### Übung

C3, "POST für dieselbe Hauptressource", dauert 45 Minuten. Die Gruppe arbeitet in `exercises/<domain>/c3-tests-errors/` auf dem C2-Stand weiter. Sie ergänzt Request- und Response-DTO, Bean Validation, `@Valid` und genau eine Fachregel als 409. Der Fehlerkörper enthält `code`, `message` und `correlationId`. PUT, DELETE und Beziehungen sind Vertiefung. Abgabe sind POST 201 und ein reproduzierbarer 409. Debrief-Fragen: Wo lohnt ein Service? Welches Persistenzfeld bleibt intern? Welche Exception erzeugt welchen Status? Exakter Folientext steht auf Folie 11.17.

### Code

Gezeigt werden zuerst Ausschnitte aus `java/rest-simple/src/main/java/com/example/restsimple/adapter/in/web/StudentController.java`: Konstruktorinjektion, GET und POST. Danach folgen `CreateStudentRequest`, `StudentResponse`, `StudentService`, `GlobalExceptionHandler` und `ErrorResponse`. Die C3-Zielstruktur des Fehlerkörpers ist `code`, `message`, `correlationId` und optional `fields`. Ein sichtbarer Hinweis trennt vorhandene Namen des größeren Beispiels vom Kursstandard.

Die Konstruktorinjektion aus `StudentController.java`, Zeilen 36 bis 44, wird verbatim gezeigt:

```java
public StudentController(CreateStudentUseCase createStudentUseCase,
                         GetStudentUseCase getStudentUseCase,
                         UpdateStudentUseCase updateStudentUseCase,
                         DeleteStudentUseCase deleteStudentUseCase) {
    this.createStudentUseCase = createStudentUseCase;
    this.getStudentUseCase = getStudentUseCase;
    this.updateStudentUseCase = updateStudentUseCase;
    this.deleteStudentUseCase = deleteStudentUseCase;
}
```

Das auf den Kursvertrag mit `/api` angepasste Zielbild der POST-Methode lautet:

```java
public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
    logger.info("POST /api/students - Creating student: {} {}", request.name(), request.lastName());

    try {
        Student student = createStudentUseCase.createStudent(request.toCommand());
        StudentResponse response = StudentResponse.fromDomain(student);

        logger.info("POST /api/students - Successfully created student with ID: {}", student.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
        logger.error("POST /api/students - Failed to create student: {} {} - Error: {}",
                    request.name(), request.lastName(), e.getMessage());
        throw e;
    }
```

Der Exception-Handler wird als C3-Zielcode gezeigt und verbindet Exception-Typ, Status und stabilen Fehlerkörper. Für das hexagonale Zielbild werden nur die Pfade `domain`, `application/port`, `application/service`, `adapter/in/web` und `adapter/out/persistence` aus `java/rest-simple` eingeblendet. Das Deck verlangt keine Übernahme dieser Struktur.

## 12 Die Anwendung absichern (Dateiname decks/12-making-it-solid.html)

### Ziel

Die Studierenden schreiben einen Controller-Test mit MockMvc und einen Service-Test mit Mockito. Sie erklären Correlation ID (Korrelations-ID), Actuator, CORS, Security Filter Chain (Sicherheitsfilterkette) und Containergrenze auf Überblicksniveau. Design und Fehlerverhalten stehen. Jetzt prüfen und beobachten wir sie. Danach ordnet der Abschluss alle Artefakte der roten Linie zu.

### Position auf der roten Linie

Vorher: `Andere zugreifen lassen: REST API mit Spring`. Aktuell: `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware`. Als Nächstes: Abschluss, dort sind `Idee im Kopf` bis `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware` erledigt.

### Erzählung

Eine API ist nicht fertig, weil Swagger einmal grün war. Ich will einen Test, der beim nächsten Umbau beweist: `POST /api/students` liefert 201 und das vereinbarte JSON. MockMvc startet dafür den Web-Rand ohne echten Browser.

Der Service-Test braucht keine Datenbank. Mockito stellt Ports als Test-Doubles bereit. Wir prüfen eine Fachentscheidung und ob der richtige Port aufgerufen wurde. Wir testen nicht private Methoden und nicht, ob Spring seine eigenen Annotationen versteht.

Im Betrieb braucht jeder Request eine Spur. Ein Filter übernimmt oder erzeugt `X-Correlation-ID`, legt sie in MDC und sendet sie zurück. Actuator liefert Health und Metrics. CORS entscheidet, welche Browser-Origin zugreifen darf.

Security bleibt ein Überblick: Eine Filter Chain prüft Requests vor dem Controller, JWT trägt signierte Claims. Docker packt Anwendung und Laufzeit in ein Image. Kein einzelner dieser Bausteine ersetzt die anderen.

### Leitfragen

- Frage: "Was beweist ein MockMvc-Test ohne echte Datenbank?" Erwartet: HTTP-Mapping, Status, JSON, Validation und Fehlerübersetzung am Web-Rand. Typisch falsch: SQL ist korrekt; das ganze System läuft in Produktion.
- Frage: "Warum reicht eine Request-ID im Response nicht?" Erwartet: Sie muss auch in Logs und nachgelagerten Aufrufen stehen. Typisch falsch: Clients brauchen nur den Status; UUIDs verhindern Fehler.
- Frage: "Was schützt CORS?" Erwartet: Browserzugriffe zwischen Origins, nicht die API allgemein. Typisch falsch: CORS ist Authentifizierung; CORS verschlüsselt HTTP.

### Realitätsbezug

Spring Boot Actuator stellt standardmäßig einen Health-Endpoint unter `/actuator/health` bereit. Die Dokumentation warnt, weitere Endpoints bewusst freizugeben und zu sichern.

### Folien

#### Folie 12.1: Die Anwendung absichern

```text
Reveal.js Prompt: Layout chapter-slide. Titel und Untertitel "Tests, Logs, Betrieb". Sichtbares Label "Leitfragen", darunter: "Welcher Test beweist unseren HTTP-Vertrag?" und "Wie bleibt ein Fehler für Client und Log eindeutig?" Visual: API-Box mit green Testhaken, amber Correlation ID und blue Health-Puls. Notizen: "Wir machen aus einer laufenden Demo ein System, dessen Verhalten prüfbar und beobachtbar ist."
```

#### Folie 12.2: Der letzte Schritt

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Status: Schritte 1 bis 7 erledigt in blue; aktiv `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware` in amber; als Nächstes außerhalb der Linie `Abschluss: Dateien zuordnen` mit gestricheltem blue Rahmen unter dem SVG. Text unten: "Vorher: REST API mit Spring · Jetzt: Tests und Middleware · Danach: Abschluss". Keine Fragmente. Notizen: "Wir bleiben beim achten Schritt. Design schützt Änderungen im Code. Tests und Betriebsmittel schützen Verhalten über Zeit und im laufenden Prozess."
```

#### Folie 12.3: Was soll ein Controller-Test beweisen?

```text
Reveal.js Prompt: Layout .lead-question. Frage "Braucht jeder Controller-Test eine echte SQLite-Datei?" Fragment 1 "Nein, Web-Vertrag isoliert testen" amber. Notizen: "Erwartet ist nein. Typisch falsch sind 'ja, sonst ist es kein Test' und 'Controller brauchen gar keine Tests'. Integrationstests mit DB ergänzen, ersetzen aber nicht den schnellen Web-Test."
```

#### Folie 12.4: MockMvc testet den HTTP-Rand

```text
Reveal.js Prompt: SVG Test → MockMvc → Controller; Use Cases als Mockito-Mocks, Datenbank grau abgetrennt. Fragmente: Request, Mock-Antwort, Assertions green. Notizen: "MockMvc führt Spring MVC ohne echten Netzwerkport aus. Die Use Cases liefern kontrollierte Daten. Wir prüfen Status und JSON-Vertrag."
```

#### Folie 12.5: Ein MockMvc-Test

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/rest-simple/src/test/java/com/example/restsimple/adapter/in/web/StudentControllerTest.java`, Zeilen 70 bis 74, vollständig und verbatim. Code zeigt GET, Status und alle drei JSON-Assertions einschließlich `Jane`. Highlights 70|71|72-74. Amber Hinweis: "Bestehender Vertrag nutzt `name`; Kursstandard nutzt `firstName`." Notizen: "Der Mock liefert vorher zwei Students. Der Test prüft den vorhandenen HTTP-Vertrag mit `name`. Die Gruppen schreiben ihre Tests gegen den Kursvertrag mit `firstName`."
```

#### Folie 12.6: Service-Test mit Mockito

```text
Reveal.js Prompt: SVG StudentService in Mitte, drei Port-Mocks außen. Fragment 1 Given stub, 2 When call, 3 Then assert, 4 verify green. Text: "Fachfall ohne HTTP und Datenbank". Notizen: "Mockito kontrolliert Antworten der Ports. Wir testen die Entscheidung im Service. Ein Test pro sinnvoller Regel ist wertvoller als einer pro privater Methode."
```

#### Folie 12.7: Ein Mockito-Test

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/rest-simple/src/test/java/com/example/restsimple/application/service/StudentServiceTest.java`, Zeilen 51 bis 60, vollständig und verbatim. Highlights 51|54|57-59|60. Notizen: "Stub, Aufruf, zwei Zustandsprüfungen und eine Interaktionsprüfung sind sichtbar. Wir prüfen nur die Interaktion, die zum Fachfall gehört. Der vorhandene Typ nutzt `getName`; das Kursprojekt nutzt `getFirstName`."
```

#### Folie 12.8: Was wir nicht testen

```text
Reveal.js Prompt: Two-column content. Green "Status, JSON, Validation, Fachregeln, Fehlerpfade". Red "Getter, private Methoden, Spring selbst, jede Logzeile". Fragmente einzeln. Notizen: "Tests kaufen Sicherheit für Änderungen. Triviale Getter und Framework-Verhalten liefern wenig Wert. Ein fehlender 404-Test ist dagegen ein echtes Vertragsloch."
```

#### Folie 12.9: Eine Correlation ID pro Request

```text
Reveal.js Prompt: Sequence SVG Client sendet optional `X-Correlation-ID`; Filter erzeugt UUID falls leer; MDC trägt ID durch Controller/Service/Repository; Response gibt Header zurück. Fragmente nacheinander, ID amber. Notizen: "Die gleiche ID steht in allen Logzeilen eines Requests. Ein Client kann sie bei einer Fehlermeldung nennen. Nach dem Request räumt der Filter MDC auf."
```

#### Folie 12.10: LoggingFilter

```text
Reveal.js Prompt: Layout .code-slide. Quelle sichtbar unten: `java/rest-simple/src/main/java/com/example/restsimple/config/LoggingFilter.java`, Zeilen 30 bis 59, vollständig und verbatim. Kürze optisch nur die langen Log-Argumentlisten mit CSS-Fade, nicht den Code. Highlights 31|33-35|41|56-58. Folientext rechts: "Correlation ID übernehmen oder erzeugen · in MDC und Response setzen · im finally löschen". Notizen: "Der Filter liegt vor dem Controller. finally verhindert, dass eine Thread-Wiederverwendung die alte ID trägt. Passwörter und Tokens gehören nie in detaillierte Logs."
```

#### Folie 12.11: Actuator zeigt Gesundheit und Messwerte

```text
Reveal.js Prompt: Content slide. Quelle sichtbar unten: `java/rest-simple-exercise/src/main/resources/application.properties`, Zeilen 43 bis 48. Vier gleich große Endpoint-Karten: `/actuator/health` green, `/actuator/info`, `/actuator/metrics`, `/actuator/prometheus` blue. Fragment 1 markiert `health` als Springs Standardfreigabe. Fragment 2 amber Rahmen um `info, metrics, prometheus` mit Text "im Exercise ausdrücklich freigegeben". Fragment 3 amber Schloss "in Produktion absichern". Notizen: "Die Exercise-Konfiguration exponiert genau health, info, metrics und prometheus. `/actuator/flyway` ist dort nicht freigegeben. Spring Boot gibt über HTTP standardmäßig nur Health frei."
```

#### Folie 12.12: CORS ist eine Browserregel

```text
Reveal.js Prompt: SVG Browser-Origin `http://localhost:3000` → API `:8081`; Preflight OPTIONS amber; API erlaubt Origin green. Red Seitenpfad curl ohne CORS-Prüfung. Notizen: "CORS steuert, ob Browser JavaScript eine andere Origin lesen darf. Es ersetzt keine Authentifizierung. Ein Server-Client wird nicht durch Browser-CORS geschützt."
```

#### Folie 12.13: Security als Filter Chain

```text
Reveal.js Prompt: SVG Request → LoggingFilter → SecurityFilterChain → JwtAuthenticationFilter → Controller. Fragmente: Kette, signiertes JWT, 401-Zweig red, autorisierter Request green. Text: "Überblick, keine Übung". Notizen: "JWT trägt signierte Claims, keine geheime Datenablage. Die Filter Chain entscheidet vor dem Controller über Zugriff. Das vollständige rest-simple zeigt eine mögliche Umsetzung."
```

#### Folie 12.14: Docker in einer Folie

```text
Reveal.js Prompt: Content SVG. Sichtbarer amber Hinweis oben: "Architekturdiagramm, kein vorhandener Produktions-Dockerfile". Links drei gleich große Karten `hypothetischer Dockerfile`, `Spring-JAR`, `Java Runtime`; ihre Pfeile laufen in eine blue Karte `Image`. Von dort führt ein Pfeil zu einer blue Container-Box mit den Labels `Port 8081` und `Volume students.db`. Fragmente: 1 die drei Eingaben, 2 `Image`, 3 `Container`, 4 Port amber, 5 Volume amber. Quellenhinweis unten: "Der vorhandene `java/rest-simple/Dockerfile` baut die VS-Code-Lernumgebung." Notizen: "Das Diagramm erklärt Verpackung, es zeigt keinen vorhandenen Anwendungs-Dockerfile. Eine dauerhafte SQLite-Datei bräuchte ein Volume. Docker ersetzt weder Tests noch Security."
```

#### Folie 12.15: (Reserve) Pagination begrenzt Collections

```text
Reveal.js Prompt: Content slide mit SVG, markiert "(Reserve)". Grundzustand: Collection mit 10.000 Student-Karten. Fragmente: 1 Request `GET /api/students?page=2&size=20`, 2 nur Karten 21 bis 40 blue, 3 Response-Metadaten `page`, `size`, `totalElements` amber. Folientext: "Nicht jede Collection in einer Antwort". Notizen: "Pagination begrenzt Antwortgröße und Datenbankarbeit. Eine stabile Sortierung gehört dazu. Diese Reservefolie enthält keine Übungsanforderung."
```

#### Folie 12.16: (Reserve) PATCH ändert einen Teil

```text
Reveal.js Prompt: Two-column content, markiert "(Reserve)". Links PUT mit vollständigem Student-DTO, rechts PATCH `{"email":"neu@uni.example"}`. Fragmente: 1 fehlende PUT-Felder red, 2 einzelnes PATCH-Feld amber, 3 Validierung und Konfliktprüfung green. Folientext: "PUT ersetzt · PATCH ändert teilweise". Notizen: "PATCH braucht klare Regeln für fehlend, NULL und unverändert. Es ist deshalb mehr als ein kleiner PUT. Diese Reservefolie wird nicht geübt."
```

#### Folie 12.17: C3, Vertrag und Fehler testen

```text
Reveal.js Prompt: Layout .exercise-slide. Tag "C3 · Kernauftrag", Timer "35 min". Dateipfad unten: `exercises/<domain>/c3-tests-errors/`. Mitte mit drei nummerierten Schritten: 1 "Testet den POST-Erfolg mit 201, ID und einem Fachfeld." 2 "Testet 409 sowie code, message und correlationId." 3 "Vertiefung: ergänzt X-Correlation-ID in Response und Log." Abgabe: "zwei grüne Tests und ein stabiler 409-Fehlerkörper". Keine Fragmente. Notizen: "Die beiden Testfälle gehören zum Kernauftrag. Ein vorbereiteter Zwischenstand enthält den laufenden POST-Pfad. Der Filter ist Vertiefung, falls die Correlation ID im Starter noch nicht gesetzt wird."
```

#### Folie 12.18: Actuator liefert eingebaute Betriebsendpunkte

```text
Reveal.js Prompt: Layout citation-slide. Visual: laufende Spring-App mit `/actuator/health` green und gesperrten Detailendpoints amber. Text: "Health ist standardmäßig verfügbar. Weitere Endpoints bewusst freigeben und sichern." Quelle: "https://docs.spring.io/spring-boot/reference/actuator/endpoints.html". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "Spring Boot nennt Health, Metrics und viele weitere Endpoints. Die Dokumentation warnt vor sensiblen Informationen. Jetzt haben wir die rote Linie vollständig gebaut und geprüft."
```

### Übung

C3, "Vertrag und Fehler testen", dauert 35 Minuten. Die Gruppe arbeitet in `exercises/<domain>/c3-tests-errors/` und schreibt zwei verbindliche Tests: POST-Erfolg mit 201 sowie 409 mit `code`, `message` und `correlationId`. Der Correlation-ID-Filter ist Vertiefung, falls der Starter die ID noch nicht setzt. Abgabe sind zwei grüne Tests und ein stabiler 409-Fehlerkörper. Debrief-Fragen: Welche Grenze prüft der Test? Welcher Port wurde ersetzt? Welche Felder bilden den stabilen Fehlervertrag? Exakter Folientext steht auf Folie 12.17.

### Code

Gezeigt werden `java/rest-simple/src/test/java/com/example/restsimple/adapter/in/web/StudentControllerTest.java`, Zeilen 60 bis 75 für GET und 77 bis 114 für POST-Erfolg und Validation; danach `java/rest-simple/src/test/java/com/example/restsimple/application/service/StudentServiceTest.java`, Zeilen 25 bis 61. Die Kernsnippets stehen verbatim auf Folien 12.5 und 12.7.

Danach folgt `java/rest-simple/src/main/java/com/example/restsimple/config/LoggingFilter.java`: Annotationen und Konstanten Zeilen 16 bis 23, `doFilterInternal` Zeilen 25 bis 59 und `getOrGenerateCorrelationId` Zeilen 61 bis 68. Folie 12.10 zeigt die Zeilen 30 bis 59 vollständig und verbatim. `DetailedLoggingFilter.java` wird nur für die Warnung vor Body- und Header-Logging geöffnet, insbesondere die Methoden zum Maskieren sensibler Header und zur Größenbegrenzung. Für Actuator zeigt das Deck `java/rest-simple-exercise/src/main/resources/application.properties`, Zeilen 43 bis 48. Für Security zeigt es nur die Dateinamen `SecurityConfig.java` und `JwtAuthenticationFilter.java`, keinen vollständigen Code. Der vorhandene `java/rest-simple/Dockerfile` baut eine VS-Code-Lernumgebung und enthält kein `COPY`/`EXPOSE`/`ENTRYPOINT` für die Spring-Anwendung. Folie 12.14 bleibt deshalb ein klar markiertes Architekturdiagramm und behauptet keinen vorhandenen Produktions-Dockerfile.

## 13 Abschluss (Dateiname decks/13-closing.html)

### Ziel

Die Studierenden ordnen ihre Dateien allen acht Schritten der roten Linie zu und verfolgen einen vorhandenen Datensatz durch Controller, optionalen Service, Repository, SQLite und JSON-Antwort. Nicht jede Vertiefung muss fertig sein. Entscheidend ist der nachweisbare Kernpfad der eigenen Gruppe.

### Position auf der roten Linie

Vorher: `Solide machen: Schichten, Validierung, Fehler, OpenAPI, Tests, Middleware`. Aktuell: Abschluss nach diesem Schritt, alle acht exakten Schritte aus `## Die rote Linie` sind erledigt. Als Nächstes: ein begründeter Lern- oder Projektschritt außerhalb der roten Linie.

### Erzählung

Am ersten Morgen hatten wir einen Satz über eine App. Jetzt kann jede Gruppe auf ihren Arbeitsstand zeigen: Domänenbeschreibung, ER-Modell, normalisiertes Schema, SQL, JDBC-Repository, Spring-Controller, DTOs und Tests.

Wenn jemand in einer echten Anwendung auf "Buchen" klickt, sieht der Weg erstaunlich ähnlich aus. Ein Client sendet HTTP. Ein Controller prüft den Vertrag. Fachlogik entscheidet. Ein Repository schreibt in Tabellen. Tests und Logs helfen, wenn etwas schiefgeht.

Das Backend von GitHub oder Stripe ist größer, verteilt und jahrelang gewachsen. Die Grundfragen bleiben: Welche Ressource gibt es? Welche Regel schützt ihre Daten? Was bedeutet ein Fehler? Welcher Vertrag gilt für Clients?

Ich will am Ende keine Liste neuer Frameworks. Ich will, dass jede Gruppe eine Stelle benennt, die sie als Nächstes vertiefen würde, und warum genau dort ihr aktuelles System an eine Grenze kommt.

### Leitfragen

- Frage: "Welche Datei beweist Schritt 3?" Erwartet: normalisiertes `schema.sql` mit Keys und Constraints. Typisch falsch: ER-Diagramm; Controller.
- Frage: "Wo würdet ihr morgen bei einem falschen 409 suchen?" Erwartet: Vertrag, Validation/Fachregel, Exception Mapping, Repository-Constraint und Logs entlang Correlation ID. Typisch falsch: nur Browser; Datenbank löschen.

### Realitätsbezug

GitHubs veröffentlichte OpenAPI-Beschreibung verbindet Ressourcen, Statuscodes, Schemas, Dokumentation und SDKs. Dieselbe Kette ist in kleiner Form im Kursprojekt sichtbar.

### Folien

#### Folie 13.1: Was ihr gebaut habt

```text
Reveal.js Prompt: Layout chapter-slide. Titel "Was euer Arbeitsstand zeigt", Untertitel "Ein Datensatz, ein Repository, eine API". Sichtbares Label "Leitfragen", darunter: "Wie kommt ein vorhandener Datensatz bis zur JSON-Antwort?" und "Welcher Teil der Kette ist in eurer Gruppe bereits nachweisbar?" Visual: acht kleine Artefakte entlang blue Linie, letzter Haken green. Notizen: "Wir schließen nicht mit neuen Begriffen. Wir verbinden den Arbeitsstand zu einem System."
```

#### Folie 13.2: Der Kernpfad ist sichtbar

```text
Reveal.js Prompt: Rote-Linie-SVG mit allen acht exakten Beschriftungen. Der Kernpfad durch alle acht Schritte ist blue markiert; Vertiefungen werden nicht als zwingend fertig behauptet. Unter dem SVG steht ein gestrichelter blue Kasten mit dem exakten Text `Als Nächstes außerhalb des Kurses: euren nächsten Engpass bearbeiten`. Fragmente entlang der Schritte. Notizen: "Nicht jede Vertiefung muss fertig sein. Entscheidend ist, dass die Gruppe ihren durchgängigen Kernpfad an vorhandenen Dateien und einem Datensatz zeigen kann."
```

#### Folie 13.3: Acht Schritte, vorhandene Dateien

```text
Reveal.js Prompt: Two-column content mit acht gleich hohen Zeilen. Jede Zeile zeigt Schrittnummer, exakten Schritttext und den Gruppenpfad. Schritt 1 `exercises/<domain>/a0-domain/`; 2 `a1-er-model/`; 3 `a3-normalization/`; 4 `b2-sql/`; 5 `b3-jdbc/`; 6 `b4-repository/`; 7 `c2-spring-resource/`; 8 `c3-tests-errors/`. Fragmente: Zeilen 1 bis 8 einzeln. Notizen: "Jede Phase hat einen eigenen Ordner. Die Dateien bleiben über die Tage bei derselben Hauptressource und werden nicht durch ein neues Beispiel ersetzt."
```

#### Folie 13.4: Datensatz 42 durch das ganze Backend

```text
Reveal.js Prompt: Sequence SVG `GET /api/.../42` → Controller/@Valid → optionaler Service → Repository → SQLite-Zeile 42 → JSON 200. Fragmente je Schritt; Fehlerzweige 400, 404 und 500; Correlation ID amber durchgehend. Notizen: "Wir verfolgen einen vorhandenen Datensatz durch genau die Grenzen, die in den Übungen entstanden sind. Jede Gruppe benennt an jedem Kasten ihre konkrete Datei oder Klasse."
```

#### Folie 13.5: Was als Nächstes Sinn ergibt

```text
Reveal.js Prompt: Four-way content: "mehr Datenbank: Indexe, Transaktionen", "mehr API: Pagination, Versionierung", "mehr Betrieb: Deployment, Metrics", "mehr Schutz: Auth, Threat Modeling". Fragmente einzeln, kein Ranking. Notizen: "Wählt nach dem Engpass eures Projekts. Ein langsamer Bericht braucht eher Query-Plan und Index als Microservices. Eine öffentliche API braucht zuerst Auth und saubere Rechte."
```

#### Folie 13.6: Rückblick und Feedback

```text
Reveal.js Prompt: Content slide. Drei kurze Fragen: "Wo hat es geklickt?", "Wo fehlt ein Zwischenschritt?", "Welche Übung würdet ihr kürzen?" Fragmente einzeln. QR-Platzhalter amber ohne externe URL. Notizen: "Ich sammle zuerst still, dann mündlich. Die Fragen zielen auf konkrete Kursstellen. Es gibt keine Bewertung der Studierenden."
```

#### Folie 13.7: GitHubs Backend beginnt mit denselben Verträgen

```text
Reveal.js Prompt: Layout citation-slide. Visual: Kurskette Ressource → Schema → Repository → HTTP → OpenAPI, daneben GitHub-Silhouette größer, gleiche Kette blue. Text: "Andere Größenordnung, dieselben Grundfragen." Quelle: "https://docs.github.com/en/rest/about-the-rest-api/about-the-openapi-description-for-the-rest-api". Darunter sichtbar: "Abruf: 2026-08-23". Notizen: "GitHub beschreibt seine REST API vollständig mit OpenAPI und erzeugt daraus Dokumentation und SDKs. Euer System ist kleiner, aber Ressourcen, Datenregeln und Verträge sind dieselbe Art Arbeit. Das ist der konkrete Abschluss der roten Linie."
```

### Übung

Keine neue Übung. Jede Gruppe nennt in höchstens einer Minute eine Datei, ein beobachtetes Problem und den nächsten Test oder Lernschritt.
