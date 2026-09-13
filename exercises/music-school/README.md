# Musikschule

Eine kommunale Musikschule verwaltet ihr Kursprogramm bisher in mehreren Tabellenblättern. Kursangebote, Lehrkräfte, Räume, Anmeldungen und benötigte Instrumente sollen in eine gemeinsame Anwendung überführt werden. Ein Kursangebot hat einen eindeutigen Kurscode, einen Titel, eine Gebühr und eine Schwierigkeitsstufe.

Eure Hauptentität ist **Kursangebot** (`MusicCourse`). Ihr führt sie durch alle elf Phasen. Beziehungen und Geschäftsprozesse kommen erst in der Vertiefung hinzu.

## Arbeitsweg

| Phase | Ergebnis                                 |
| ----- | ---------------------------------------- |
| A0    | Begriffe und offene Annahmen             |
| A1    | ER-Modell mit markierter Hauptentität    |
| A2    | Relationenmodell                         |
| A3    | begründete Zerlegung bis 3NF             |
| B1    | ausführbares Schema und Seed-Daten       |
| B2    | vier geprüfte Abfragen                   |
| B3    | JDBC-Mapping für `MusicCourse`           |
| B4    | Datenzugriff hinter einem Repository     |
| C1    | DTOs und GET-/POST-Vertrag                    |
| C2    | GET durch Controller, Service und JPA          |
| C3    | POST, Validierung, 404-/409-Tests |

Die ausführliche Musterlösung liegt nicht in diesem Gruppenordner. Die Lehrperson verwaltet sie unter `exercises/instructor-solutions/music-school/`. Sie deckt alle Phasen von der Domänenklärung bis zu den Controller-Tests ab.
