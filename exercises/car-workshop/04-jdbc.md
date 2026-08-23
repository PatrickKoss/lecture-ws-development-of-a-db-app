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

### Fahrzeug ergänzen

Ergänzt die Entität `Vehicle` und die Tabelle `vehicles`. Das Model hat sieben Felder: `id` als `Long`, `licencePlate` als `String`, `vin` als `String`, `manufacturer` als `String`, `model` als `String`, `constructionYear` als `Integer` und `ownerId` als `Long`. Alle Felder außer `id` sind Pflichtfelder. Kennzeichen und Fahrgestellnummer sind eindeutig. Baujahre müssen zwischen 1950 und 2026 liegen. Kennzeichen haben höchstens 12 Zeichen, Hersteller und Modell höchstens 50 beziehungsweise 80 Zeichen. `ownerId` muss positiv sein.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Vehicle` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Fahrzeug anlegen` und `8. Fahrzeuge auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich ein doppeltes Kennzeichen und ein ungültiges Baujahr.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
