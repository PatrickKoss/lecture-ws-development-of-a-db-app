# Gemeinsames JDBC-Beispiel

Dieses Projekt bleibt als Beispiel für das Student-Repository erhalten. Die Gruppen arbeiten in den domänenspezifischen Projekten unter:

```text
exercises/<domain>/b3-jdbc/starter/
exercises/<domain>/b4-repository/
```

Dort sind das Schema, die Seed-Daten, die Hauptentität und ein Read-Repository für abhängige Daten bereits vorbereitet. In B3 implementiert die Gruppe `findById`, `findAll` und das Row-Mapping. B4 zieht den Datenzugriff hinter die Repository-Grenze und übergibt Schema sowie Seed-Daten als Flyway-Migrationen.

Das gemeinsame Beispiel lässt sich weiterhin prüfen:

```sh
./gradlew build
./verify-exercise.sh
```

Die TODOs in diesem Projekt sind nicht mehr der verbindliche Gruppenauftrag.
