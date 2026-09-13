# B4-Lehrbeispiel: Repository-Grenze

Dieses Projekt refaktoriert die B3-JDBC-Lösung. `StudentRepository` nennt keine
`SQLException` mehr. Die JDBC-Adapter fangen Datenbankfehler und übersetzen sie
in `RepositoryException`.

```bash
./gradlew test
```

`StudentCatalog` hängt am Interface. Das enthaltene In-Memory-Repository zeigt,
dass derselbe Catalog ohne SQLite funktioniert.

[`JdbcStudentRepository.java`](src/main/java/org/lecture/JdbcStudentRepository.java)
bewahrt die ursprüngliche `SQLException` als Cause der `RepositoryException`.
[`StudentCatalog.java`](src/main/java/org/lecture/StudentCatalog.java) kennt nur
das Interface. Der SQLite-Test liest weiterhin Lena Hoffmann mit `M2023001`;
der In-Memory-Test führt denselben Catalog ohne Datenbank aus.
[`JdbcDepartmentLookup.java`](src/main/java/org/lecture/JdbcDepartmentLookup.java)
setzt die Umbenennung der vorbereiteten B3-Lookup-Klasse fort. Dieses
Lehrprojekt liest das gemeinsame Schema und den Seed weiterhin aus `../sql/`.
