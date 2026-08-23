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

### Film ergänzen

Ergänzt die Entität `Movie` und die Tabelle `movies`. Das Model hat sieben Felder: `id` als `Long`, `movieCode` als `String`, `title` als `String`, `releaseYear` als `Integer`, `durationMinutes` als `Integer`, `fskCode` als `String` und `minimumAge` als `Integer`. Alle Felder außer `id` sind Pflichtfelder. Die Filmnummer ist eindeutig. Erscheinungsjahre müssen zwischen 1888 und 2100 liegen, die Laufzeit zwischen 1 und 600 Minuten. Titel haben höchstens 200 Zeichen. Erlaubt sind die FSK-Codes `FSK_0`, `FSK_6`, `FSK_12`, `FSK_16` und `FSK_18`; das Mindestalter muss dazu passen. Die Kombination aus Titel und Mindestalter ist ebenfalls eindeutig.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Movie` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Film anlegen` und `8. Filme auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Filmnummer und eine unpassende Kombination aus FSK-Code und Mindestalter.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
