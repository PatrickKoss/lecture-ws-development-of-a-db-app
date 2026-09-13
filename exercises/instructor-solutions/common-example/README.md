# Musterlösung für das gemeinsame Beispiel

Diese Lösung gehört zu den Aufgaben unter [`common-example/exercises`](../../../common-example/exercises/README.md). Jeder Ordner bildet den Stand nach einer oder mehreren Phasen ab.

| Phasen | Lösung | Prüfung |
| --- | --- | --- |
| A0 bis A3 | [`design/`](design/README.md) | `npx --yes @mermaid-js/mermaid-cli -i design/er.mmd -o design/er.svg` |
| B1 und B2 | [`sql/`](sql/README.md) | die B1- und B2-Checker aus den Aufgaben |
| B3 | [`jdbc/`](jdbc/README.md) | `cd jdbc && ./gradlew test` |
| B4 | [`repository/`](repository/README.md) | `cd repository && ./gradlew test` |
| C1 | [`api/`](api/README.md) | Vergleich mit dem Spring-Export |
| C1 bis C3 | [`spring/`](spring/README.md) | `cd spring && ./gradlew test` |

Vom Repository-Stamm prüft ein Befehl das gesamte Paket:

```bash
bash exercises/instructor-solutions/verify.sh common-example
```

Die Java-Projekte sind eigenständig. JDBC und Repository lesen ihre SQL-Dateien aus dem benachbarten Ordner `sql/`. Die Spring-Anwendung enthält dieselben Dateien als Flyway-Migrationen.
