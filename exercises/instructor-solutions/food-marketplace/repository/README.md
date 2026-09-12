# B4: Repository-Refactoring, Musterlösung

Dieses Verzeichnis ist ein eigenständiges Java-21-Projekt. Es enthält den vollständigen B3-Stand und die B4-Lösung.

`RestaurantRepository` ist die öffentliche Grenze für den Datenzugriff. Die Schnittstelle enthält keine JDBC-Typen. `JdbcRestaurantRepository` übersetzt SQL-Fehler in `RepositoryException`; Verstöße gegen den eindeutigen fachlichen Schlüssel werden zu `DuplicateKeyException`.

`InMemoryRestaurantRepository` hat dieselbe Schnittstelle, vergibt IDs und prüft den fachlichen Schlüssel ohne SQLite. `RestaurantCatalog` kennt nur das Repository. Sein Test zeigt, dass Listen, Suchen und Einfügen vollständig im Speicher laufen.

```bash
./gradlew test
! rg 'Connection|ResultSet|PreparedStatement|SQLException' src/main/java \
  --glob '!**/Database.java' \
  --glob '!**/Jdbc*Repository.java'
```
