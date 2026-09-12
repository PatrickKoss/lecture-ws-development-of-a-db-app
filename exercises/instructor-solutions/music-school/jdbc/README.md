# Musterlösung für B3

`JdbcMusicCourseRepository.java` ersetzt die gleichnamige TODO-Datei im
B3-Starter. Beide Abfragen nennen ihre Spalten ausdrücklich. Das schützt das
Mapping vor einer stillen Abhängigkeit von der Spaltenreihenfolge der Tabelle.

`findById` bindet die ID mit einem `PreparedStatement`. Bei null Zeilen gibt die
Methode `Optional.empty()` zurück. Bei einer Zeile ruft sie `map` auf.
`findAll` verwendet dasselbe Mapping in einer Schleife, weil die Ergebnismenge
null bis viele Zeilen enthalten kann.

Alle JDBC-Ressourcen stehen in `try`-with-resources-Blöcken. Java schließt
`ResultSet`, `PreparedStatement` und `Connection` auch dann, wenn das Mapping
eine `SQLException` auslöst.

Die Spalte `course_code` wird bewusst in das Java-Feld `courseCode` übersetzt.
Ein positionsbasiertes Mapping wie `getString(2)` würde nach einer geänderten
SELECT-Liste leicht den falschen Wert lesen. Der Spaltenname macht die Zuordnung
sichtbar.

Zum Prüfen die Datei kopieren, den Test aktivieren und ausführen:

```bash
cp ../instructor-solutions/music-school/jdbc/JdbcMusicCourseRepository.java \
  b3-jdbc/starter/src/main/java/course/musicschool/
cd b3-jdbc/starter
./gradlew test
```
