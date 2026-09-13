# Kino

Das Programmkino "Lichtspiel" führt Filmprogramm und Kartenverkauf in einer Datenbank zusammen.

Eure Hauptentität ist **Film** (`Movie`). Ihr führt sie durch alle elf Phasen. Beziehungen und Geschäftsprozesse kommen erst in der Vertiefung hinzu.

## Arbeitsweg

| Phase | Ergebnis                                 |
| ----- | ---------------------------------------- |
| A0    | Begriffe und offene Annahmen             |
| A1    | ER-Modell mit markierter Hauptentität    |
| A2    | Relationenmodell                         |
| A3    | begründete Zerlegung bis 3NF             |
| B1    | ausführbares Schema und Seed-Daten       |
| B2    | vier geprüfte Abfragen                   |
| B3    | JDBC-Mapping für `Movie`                 |
| B4    | Datenzugriff hinter einem Repository     |
| C1    | DTOs und GET-/POST-Vertrag                    |
| C2    | GET durch Controller, Service und JPA                |
| C3    | POST, Validierung, 404-/409-Tests |

Die fertigen SQL-Lösungen liegen nicht in diesem Gruppenordner. Die Lehrperson verwaltet sie unter `exercises/instructor-solutions/cinema/`.
