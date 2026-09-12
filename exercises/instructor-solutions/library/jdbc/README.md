# B3: JDBC, Musterlösung

Dieses eigenständige Java-21-Projekt enthält die vollständige B3-Lösung samt Vertiefung.

`JdbcBookRepository` liest `books` mit vorbereiteten Statements und ordnet jede Spalte sichtbar dem Java-Record zu. `insert` setzt alle Werte über Parameter, liest den von SQLite erzeugten Schlüssel auf derselben Verbindung und gibt die gespeicherte Zeile zurück. Der Test deckt sortiertes Lesen, eine unbekannte ID, Einfügen und den UNIQUE-Verstoß des fachlichen Schlüssels ab.

`Database.initialize()` führt Schema und Seed-Daten in einer Transaktion aus. Schlägt ein Statement fehl, setzt die Methode die gesamte Initialisierung zurück.

```bash
./gradlew test
```
