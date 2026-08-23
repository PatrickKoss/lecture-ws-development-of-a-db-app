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

### Pizza ergänzen

Ergänzt die Entität `Pizza` und die Tabelle `pizzas`. Das Model hat sieben Felder: `id` als `Long`, `pizzaNumber` als `String`, `name` als `String`, `category` als `String`, `ovenStation` als `String`, `basePrice` als `BigDecimal` und `active` als `Boolean`. Alle Felder außer `id` sind Pflichtfelder. Die Pizzanummer ist eindeutig. Name und Ofenstation bilden ebenfalls eine eindeutige Kombination. Der Grundpreis muss größer als 0 sein. Pizzanummern haben das Format `P-01`, Namen höchstens 100 Zeichen und Kategorien sowie Ofenstationen höchstens 30 beziehungsweise 20 Zeichen.

1. Legt Model, Repository-Interface und Repository-Implementierung für `Pizza` an.
2. Legt die Tabelle beim Start an und greift mit vorbereiteten Statements darauf zu.
3. Ergänzt genau zwei CLI-Menüpunkte: `7. Pizza anlegen` und `8. Pizzen auflisten`.
4. Prüft beide Menüpunkte mit gültigen Daten. Prüft beim Anlegen zusätzlich eine doppelte Pizzanummer und einen Grundpreis von 0.

## Abgabe

- das ausführbare Projekt `java/repository-simple-exercise`
- kurze Notiz mit den getesteten CLI-Abläufen
