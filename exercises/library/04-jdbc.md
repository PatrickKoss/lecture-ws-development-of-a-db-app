# 4. JDBC

> Zeitbox: 75 Minuten, davon 45 Minuten für `StudentRepositoryImpl` und 30 Minuten für die eigene Domäne

## Ausgangspunkt

Arbeitet im Projekt `java/repository-simple-exercise`. Baut es mit `./gradlew build` und startet die Anwendung mit `./gradlew run`.

## Aufgabe

### Student-Repository fertigstellen

1. Implementiert alle Methoden von `StudentRepositoryImpl`: Datenbank anlegen, Create, Read, Update, Delete und Existenzprüfung.
2. Verwendet für alle Eingaben `PreparedStatement` und für Verbindungen, Statements und Result Sets `try-with-resources`.
3. Übernehmt erzeugte IDs, behandelt leere Ergebnisse mit `Optional` und fangt `SQLException` sowie verletzte `UNIQUE`-Constraints sinnvoll ab.
4. Aktiviert in `Main.java` die Repository-Initialisierung und die vorhandenen TODO-Blöcke des CLI-Menüs.
5. Testet alle CRUD-Fälle sowie eine doppelte E-Mail-Adresse oder Studierendennummer.

### Buch ergänzen

Ergänzt die Entität `Book` und die Tabelle `books`. Das Model hat sechs Felder: `id` als `Long`, `isbn` als `String`, `title` als `String`, `publicationYear` als `Integer`, `subjectArea` als `String` und `shelfCode` als `String`. Alle Felder außer `id` sind Pflichtfelder. Die ISBN ist eindeutig. Erscheinungsjahre müssen zwischen 1450 und 2100 liegen. Titel haben höchstens 200 Zeichen, Sachgebiet und Regalcode höchstens 50 beziehungsweise 10 Zeichen. Die Kombination aus Titel und Regalcode ist ebenfalls eindeutig.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Book` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Buch anlegen` und `8. Bücher auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte ISBN und ein ungültiges Erscheinungsjahr.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
