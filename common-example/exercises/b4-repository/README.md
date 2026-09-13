# B4-Lehrübung: Repository-Grenze

Zeit: 15 Minuten

## Eingang

Verwendet euren fertigen B3-Stand. Kopiert `starter/StudentCatalog.java` nach
`../b3-jdbc/starter/src/main/java/org/lecture/StudentCatalog.java`.

## Gemeinsame Arbeit

1. Entfernt `SQLException` aus `StudentRepository`.
2. Fangt die Exception nur in `JdbcStudentRepository`.
3. Übersetzt sie dort in eine ungeprüfte `RepositoryException`.
4. Benennt `DepartmentLookup` in `JdbcDepartmentLookup` um und übersetzt auch
   dort den Datenbankfehler.

## Checkpoint

`StudentCatalog` kompiliert ohne `SQLException`, JDBC-Imports oder SQL. Führt
den Check aus dem B4-Ordner so aus:

```bash
cd ../b3-jdbc/starter
./gradlew test
```

## Lösung

Vergleicht mit der [vollständigen B4-Lösung](../../repository-basic/README.md).

## Transfer zur eigenen Domäne

Implementiert optional `InMemoryStudentRepository` und verwendet denselben
Catalog ohne SQLite. Übertragt danach die Grenze vom Repository-Root aus auf
`exercises/<domain>/b4-repository/`: Der Catalog eurer Domäne darf nur das
Repository-Interface kennen.
