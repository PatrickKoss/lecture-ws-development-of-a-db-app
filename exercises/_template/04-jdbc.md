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

### Eine Entität aus eurer Domäne ergänzen

<!-- TODO(domain): Nenne genau eine geeignete Entität aus der Domäne. Sie soll 4 bis 6 Felder einschließlich ID besitzen. Nenne Tabellennamen, Felder, Java-Typen und Constraints, aber liefere keinen fertigen Java-Code. -->

1. Legt Model, Repository-Interface und Repository-Implementierung für diese Entität an.
2. Legt ihre Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt CLI-Menüpunkte zum Anlegen, Auflisten, Suchen, Ändern und Löschen.
4. Prüft jeden neuen Menüpunkt mit mindestens einem gültigen und einem fehlerhaften Aufruf.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
