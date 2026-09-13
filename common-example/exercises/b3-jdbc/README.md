# B3-Lehrübung: JDBC

Zeit: 35 Minuten

## Eingang

Der Starter entspricht der Gruppenaufgabe, verwendet aber das
Universitäts-Schema. Die Kopien liegen unter `starter/src/main/resources/db/`.
Ersetzt sie in der Vorlesung durch eure B1-Dateien. Startet den Starter aus
diesem Ordner:

```bash
cd starter
./gradlew test
```

## Gemeinsame Arbeit

1. Implementiert `JdbcStudentRepository.findAll` und `map`.
2. Aktiviert `readsStudentsInIdOrder`.
3. Implementiert `findById` mit einem gebundenen Parameter.

## Checkpoint

Aktiviert `readsKnownStudentAndReportsMissingId`. Alle Tests müssen grün sein.

## Lösung

Vergleicht danach mit der [vollständigen B3-Lösung](../../jdbc/README.md).

## Transfer zur eigenen Domäne

`SQLException` bleibt in B3 auf `StudentRepository`. In B4 verschwindet es aus
dem Interface und damit aus dem Catalog. Übertragt danach denselben Ablauf vom
Repository-Root aus auf `exercises/<domain>/b3-jdbc/`: erst Listenabfrage und
Mapping, dann die gebundene ID-Abfrage.
