# Fahrradverleih

Der Verleih "StadtRad Münster" ersetzt Stationslogbücher und Wartungszettel durch eine Anwendung.

Eure Hauptentität ist **Station** (`Station`). Ihr führt sie durch alle elf Phasen. Beziehungen und Geschäftsprozesse kommen erst in der Vertiefung hinzu.

## Arbeitsweg

| Phase | Ergebnis                                 |
| ----- | ---------------------------------------- |
| A0    | Begriffe und offene Annahmen             |
| A1    | ER-Modell mit markierter Hauptentität    |
| A2    | Relationenmodell                         |
| A3    | begründete Zerlegung bis 3NF             |
| B1    | ausführbares Schema und Seed-Daten       |
| B2    | vier geprüfte Abfragen                   |
| B3    | JDBC-Mapping für `Station`               |
| B4    | Datenzugriff hinter einem Repository     |
| C1    | GET- und POST-Vertrag                    |
| C2    | GET-Endpunkte für `Station`              |
| C3    | POST, Validierung, 409-Konflikt und Test |

Die fertigen SQL-Lösungen liegen nicht in diesem Gruppenordner. Die Lehrperson verwaltet sie unter `exercises/instructor-solutions/bike-rental/`.
