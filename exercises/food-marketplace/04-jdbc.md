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

### Restaurant ergänzen

Ergänzt die Entität `Restaurant` und die Tabelle `restaurants`. Das Model hat acht Felder: `id` als `Long`, `partnerNumber` als `String`, `name` als `String`, `street` als `String`, `postalCode` als `String`, `city` als `String`, `commissionRate` als `BigDecimal` und `active` als `Boolean`. Alle Felder außer `id` sind Pflichtfelder. Die Partnernummer ist eindeutig. Der Provisionssatz muss zwischen 0 und 100 liegen. Name und Straße haben höchstens 100 Zeichen, Ort höchstens 60 Zeichen. Die Postleitzahl besteht aus fünf Ziffern.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Restaurant` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Restaurant anlegen` und `8. Restaurants auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Partnernummer und einen ungültigen Provisionssatz.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
