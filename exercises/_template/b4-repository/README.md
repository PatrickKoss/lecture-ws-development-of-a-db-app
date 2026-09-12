# B4: Repository-Refactoring

Zeitbox: etwa 15 Minuten für den Kernauftrag. Die Vertiefung beginnt erst danach.

## Eingang

Arbeitet mit eurem lauffähigen Stand aus `../b3-jdbc/starter`. `starter/ResourceCatalog.java` ist die vorbereitete aufrufende Schicht. Ihr kopiert sie in das B3-Projekt.

Der Catalog enthält absichtlich kein `SQLException`. Nach dem Kopieren kompiliert er erst, wenn ihr die Repository-Schnittstelle im Kernauftrag bereinigt habt.

Starttest:

```bash
cd ../b3-jdbc/starter && ./gradlew test
```

## Kernauftrag

1. Entfernt `SQLException` aus der öffentlichen Schnittstelle `ResourceRepository`.
2. Fangt `SQLException` ausschließlich in `JdbcResourceRepository` ab und übersetzt sie in eine eigene ungeprüfte `RepositoryException`.
3. Benennt den vorbereiteten JDBC-Adapter `LookupRepository` in `JdbcLookupRepository` um und passt seinen Test an. Der Klassenname soll die technische Implementierung sichtbar machen.
4. Lasst `ResourceCatalog` nur über `ResourceRepository` auf Resource-Daten zugreifen. Im Catalog stehen weder SQL noch JDBC-Typen.
5. Führt die vorhandenen Tests aus. Der JDBC-Test muss weiter dieselben Zeilen lesen.

## Vertiefung

Implementiert `InMemoryResourceRepository`. Es vergibt IDs und lehnt einen doppelten fachlichen Schlüssel genauso ab wie die UNIQUE-Regel der Datenbank. Testet `ResourceCatalog` mit diesem Repository, ohne SQLite zu öffnen.

## Vorbereiteter Zwischenstand

Wenn das Refactoring stockt, legt zuerst die endgültigen Methodensignaturen von `ResourceRepository` fest. Verschiebt danach eine Methode vollständig in `JdbcResourceRepository` und übersetzt ihren SQL-Fehler an der Repository-Grenze.

## Ausgang

Die aufrufende Schicht kennt nur `ResourceRepository` und die Domänenklasse `Resource`. Eine Quelltextsuche findet JDBC-Typen nur in `Database` und JDBC-Repositorys. Der Catalog-Test läuft mit der In-Memory-Implementierung.

Prüfbefehle:

```bash
cd ../b3-jdbc/starter
./gradlew test
! rg 'Connection|ResultSet|PreparedStatement|SQLException' src/main/java \
  --glob '!**/Database.java' \
  --glob '!**/Jdbc*Repository.java'
```

## Übergabe an C2

C2 ist ein vorbereitetes, eigenständiges Spring-Boot-Projekt. Übernehmt Schema und Seed-Daten als Flyway-Migrationen. Übertragt die Repository-Idee, aber kopiert `JdbcResourceRepository` nicht in den Spring-Starter. Dort implementiert Spring Data die Datenbankabfragen über JPA und Hibernate.

## Auswertung

Welche Änderung am SQL-Schema bleibt innerhalb der Repository-Implementierung? Warum sollte ein Catalog weder `SQLException` noch Hibernate-Typen kennen?
