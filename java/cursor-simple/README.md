# Wo stehen wir auf der roten Linie

Deck 07 nutzt dieses Projekt für den ersten Zugriff aus Java auf die Studierenden-Datenbank. Hier üben wir JDBC mit `DriverManager`, `PreparedStatement`, `ResultSet` und `try-with-resources`. Die doppelte Zuordnung einer Datenbankzeile zu `Student` ist beabsichtigt. Deck 08 löst diese Wiederholung mit dem Repository Pattern auf.

## Starten

```sh
./gradlew run
```

Die aufgeräumte Vergleichsversion startet so:

```sh
./gradlew runRefactored
```
