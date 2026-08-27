# B4: Repository-Refactoring

Zeitbox: etwa 15 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Der lauffähige Stand liegt in `../b3-jdbc/starter`. `starter/RoomTypeCatalog.java` ist die vorbereitete aufrufende Schicht.

Starttest:

```bash
cd ../b3-jdbc/starter && ./gradlew test
```

## Kernauftrag

Kopiert `RoomTypeCatalog` in das B3-Projekt. Verschiebt alle SQL-Zugriffe hinter `RoomTypeRepository`. Die aufrufende Schicht darf weder Connection noch ResultSet kennen.

## Vertiefung

Implementiert ein In-Memory-Repository und testet den Catalog ohne SQLite.

## Vorbereiteter Zwischenstand

Wenn das Refactoring stockt, friert die öffentliche Repository-Schnittstelle ein und verschiebt nur eine Abfrage vollständig.

## Ausgang

Eine Quelltextsuche findet JDBC-Typen nur in Database und Jdbc-Repository.

Prüfbefehl:

```bash
cd ../b3-jdbc/starter && ! rg 'Connection|ResultSet|PreparedStatement' src/main/java --glob '!**/Database.java' --glob '!**/Jdbc*Repository.java'
```

## Auswertung

Welche Änderung am SQL-Schema bleibt jetzt innerhalb der Repository-Implementierung?
