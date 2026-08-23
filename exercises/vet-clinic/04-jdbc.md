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

### Tier ergänzen

Ergänzt die Entität `Pet` und die Tabelle `pets`. Das Model hat acht Felder: `id` als `Long`, `ownerId` als `Long`, `petNumber` als `Integer`, `name` als `String`, `species` als `String`, `birthDate` als `LocalDate`, `insurancePolicyNumber` als `String` und `active` als `Boolean`. Alle Felder außer `id`, `birthDate` und `insurancePolicyNumber` sind Pflichtfelder. Tiernummern müssen positiv sein. Ein Halter darf jede Tiernummer nur einmal vergeben. Die Versicherungsnummer ist optional und, falls vorhanden, eindeutig. Name und Tierart haben höchstens 80 beziehungsweise 30 Zeichen.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Pet` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Tier anlegen` und `8. Tiere auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Tiernummer für denselben Halter und eine doppelte Versicherungsnummer.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
