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

### Veranstaltung ergänzen

Ergänzt die Entität `Event` und die Tabelle `events`. Das Model hat acht Felder: `id` als `Long`, `eventNumber` als `String`, `title` als `String`, `eventType` als `String`, `eventOn` als `LocalDate`, `doorsOpen` als `LocalTime`, `startsAt` als `LocalTime` und `venueId` als `Long`. Alle Felder außer `id` sind Pflichtfelder. Die Veranstaltungsnummer ist eindeutig. Titel haben höchstens 200 Zeichen. Die Art ist `KONZERT` oder `LESUNG`. Spielort-IDs müssen positiv sein, und der Einlass darf nicht nach dem Beginn liegen.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Event` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Veranstaltung anlegen` und `8. Veranstaltungen auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Veranstaltungsnummer und eine Einlasszeit nach dem Beginn.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
