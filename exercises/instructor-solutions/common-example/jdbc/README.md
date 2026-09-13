# B3-Lehrbeispiel: JDBC

Dieses Projekt ist die vollständige Universitätslösung für B3. Es liest das
verbindliche Schema und den Seed aus `../sql/`.

```bash
./gradlew test
```

Der gemeinsame Weg lautet `Database` → `StudentRepository` →
`JdbcStudentRepository`. Das Repository nennt seine Spalten, verwendet
Prepared Statements, sortiert nach ID und bündelt das Row Mapping in einer
Methode. In B3 steht `SQLException` noch im Interface. B4 entfernt dieses
Datenbankdetail aus dem Aufrufer.

Der Test liest Lena Hoffmann als ID 1 mit der Matrikelnummer `M2023001`. In
[`JdbcStudentRepository.java`](src/main/java/org/lecture/JdbcStudentRepository.java)
bindet `setLong` die gesuchte ID. Das Mapping wandelt das ISO-Datum mit
`LocalDate.parse` um. [`Database.java`](src/main/java/org/lecture/Database.java)
aktiviert Foreign Keys für jede neue Verbindung.

`Main` und `MainRefactored` bleiben als ältere interaktive JDBC-Demos erhalten.
Sie starten weiterhin mit `./gradlew run` und `./gradlew runRefactored`. Für die
B3-Lösung sind die Repository-Klassen maßgeblich.
