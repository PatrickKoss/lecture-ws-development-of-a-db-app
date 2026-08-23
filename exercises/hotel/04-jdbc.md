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

### Zimmer ergänzen

Ergänzt die Entität `Room` und die Tabelle `rooms`. Das Model hat fünf Felder: `floor` als `Integer`, `roomNumber` als `Integer`, `roomTypeCode` als `String`, `status` als `String` und `accessible` als `Boolean`. Alle Felder sind Pflichtfelder. Etage und Zimmernummer bilden gemeinsam den Primary Key. Die Etage liegt zwischen 1 und 4, die Zimmernummer zwischen 1 und 99. Der Typ-Code hat höchstens 10 Zeichen. Als Status sind `AVAILABLE`, `OCCUPIED` und `MAINTENANCE` erlaubt.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Room` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Zimmer anlegen` und `8. Zimmer auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Kombination aus Etage und Zimmernummer und einen unbekannten Status.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
