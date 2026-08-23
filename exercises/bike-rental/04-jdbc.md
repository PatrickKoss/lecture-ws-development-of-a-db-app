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

### Rad ergänzen

Ergänzt die Entität `Bike` und die Tabelle `bikes`. Das Model hat sechs Felder: `id` als `Long`, `bikeNumber` als `String`, `modelId` als `Long`, `currentStationId` als `Long`, `status` als `String` und `commissionedOn` als `LocalDate`. Alle Felder außer `id` und `currentStationId` sind Pflichtfelder. Die Radnummer ist eindeutig. `modelId` muss positiv sein. `currentStationId` darf fehlen, solange das Rad ausgeliehen oder in der Werkstatt ist. Der Status ist `AVAILABLE`, `RENTED` oder `MAINTENANCE`. Das Inbetriebnahmedatum darf nicht vor `2026-01-01` liegen.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Bike` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Rad anlegen` und `8. Räder auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Radnummer und einen unbekannten Status.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
