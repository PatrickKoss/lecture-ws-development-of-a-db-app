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

### Kurs ergänzen

Ergänzt die Entität `Course` und die Tabelle `courses`. Das Model hat sechs Felder: `id` als `Long`, `courseCode` als `String`, `title` als `String`, `level` als `String`, `durationMinutes` als `Integer` und `roomId` als `Long`. Alle Felder außer `id` und `roomId` sind Pflichtfelder. Der Kurscode ist eindeutig. `level` erlaubt `BEGINNER`, `INTERMEDIATE` und `ADVANCED`; dazu gehören 45, 60 und 75 Minuten. Titel haben höchstens 100 Zeichen. Die Kombination aus Titel und Dauer ist ebenfalls eindeutig.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Course` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Kurs anlegen` und `8. Kurse auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich einen doppelten Kurscode und eine Dauer, die nicht zum Niveau passt.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
