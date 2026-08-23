# Checkliste für die Domäne

> Zeitbox: 10 Minuten vor der Übergabe

## Aufgabe

Kreuze für jeden Punkt `Ja` oder `Nein` an und nenne die genaue Stelle in den Dateien. Ein `Nein` muss vor der Übergabe behoben oder kurz begründet werden.

| Prüffrage | Ja | Nein | Nachweis oder Begründung |
| --- | :---: | :---: | --- |
| Umfasst das Modell 4 bis 6 Entitäten? | [ ] | [ ] | <!-- TODO(domain): Entitäten nennen. --> |
| Enthält das Modell mindestens eine 1:n-Beziehung? | [ ] | [ ] | <!-- TODO(domain): Beziehung nennen. --> |
| Enthält das Modell mindestens eine n:m-Beziehung? | [ ] | [ ] | <!-- TODO(domain): Beziehung nennen. --> |
| Hat die n:m-Beziehung mindestens ein eigenes fachliches Attribut? | [ ] | [ ] | <!-- TODO(domain): Attribut nennen. --> |
| Enthält das Modell mindestens eine optionale Beziehung? | [ ] | [ ] | <!-- TODO(domain): Beziehung und optionale Seite nennen. --> |
| Gibt es einen plausiblen Kandidaten für eine schwache Entität? | [ ] | [ ] | <!-- TODO(domain): Entität und identifizierende Beziehung nennen. --> |
| Hat die flache Ausgangstabelle 12 bis 20 Zeilen und erkennbare Wiederholungen? | [ ] | [ ] | <!-- TODO(domain): Datei und Zeilen nennen. --> |
| Enthält die Ausgangstabelle eine transitive Abhängigkeit für die 3NF? | [ ] | [ ] | <!-- TODO(domain): Abhängigkeit A -> B -> C nennen. --> |
| Enthält die Ausgangstabelle eine konkrete BCNF-Falle? | [ ] | [ ] | <!-- TODO(domain): Determinante und verletzte Bedingung nennen. --> |
| Umfassen die acht SQL-Aufgaben einen `JOIN`? | [ ] | [ ] | <!-- TODO(domain): Abfragenummer nennen. --> |
| Umfassen die acht SQL-Aufgaben einen `LEFT JOIN`? | [ ] | [ ] | <!-- TODO(domain): Abfragenummer nennen. --> |
| Umfassen die acht SQL-Aufgaben `GROUP BY`? | [ ] | [ ] | <!-- TODO(domain): Abfragenummer nennen. --> |
| Umfassen die acht SQL-Aufgaben `HAVING`? | [ ] | [ ] | <!-- TODO(domain): Abfragenummer nennen. --> |
| Umfassen die acht SQL-Aufgaben eine Subquery? | [ ] | [ ] | <!-- TODO(domain): Abfragenummer nennen. --> |
| Umfassen die acht SQL-Aufgaben einen Datumsvergleich? | [ ] | [ ] | <!-- TODO(domain): Abfragenummer nennen. --> |
| Sind `schema.sql`, `seed.sql` und `queries.sql` mit SQLite 3 ausführbar? | [ ] | [ ] | <!-- TODO(domain): Prüfkommando und Ergebnis nennen. --> |
| Definiert die REST-Aufgabe eine Hauptressource und einen Beziehungsendpunkt? | [ ] | [ ] | <!-- TODO(domain): Pfade nennen. --> |
| Deckt die REST-Aufgabe Validierung sowie die Fehlercodes 400, 404 und 409 ab? | [ ] | [ ] | <!-- TODO(domain): Regeln und Fehlerfälle nennen. --> |

## Abgabe

- vollständig ausgefüllte Ja/Nein-Tabelle
- kurze Begründung für jeden noch offenen Punkt
