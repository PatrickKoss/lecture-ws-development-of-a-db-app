# Übungen: eine Domäne in drei Tagen

Jede Gruppe arbeitet drei Tage lang an derselben Domäne. Ihr beginnt mit einer Idee, klärt sie im ER-Modell und überführt sie in normalisierte Tabellen. Am zweiten Tag setzt ihr das Schema in SQL um, fragt die Daten ab und greift mit JDBC darauf zu. Danach kapselt ihr den Datenzugriff in einem Repository. Am dritten Tag macht ihr die Anwendung als REST API mit Spring zugänglich und ergänzt Schichten, Validierung, Fehlerbehandlung, OpenAPI und Tests.

## Domänen

| Domäne | Geschichte |
| --- | --- |
| `library` | Eine Stadtteilbibliothek möchte Exemplare, Ausleihen und Vormerkungen verwalten, damit Mitarbeitende jederzeit sehen, welches Buch verfügbar ist. |
| `pizza-delivery` | Eine Pizzeria möchte Bestellungen mit frei wählbaren Belägen erfassen und einer Lieferadresse sowie einem Fahrer zuordnen. |
| `gym` | Ein Fitnessstudio möchte Mitgliedschaften, Kurse und Buchungen verwalten und dabei Trainer und Räume einplanen. |
| `cinema` | Ein Kino möchte Filme in Sälen einplanen und für jede Vorstellung sitzplatzgebundene Tickets verkaufen. |
| `bike-rental` | Ein Fahrradverleih möchte Räder an Stationen ausgeben, Rückgaben abrechnen und Wartungen dokumentieren. |
| `vet-clinic` | Eine Tierarztpraxis möchte Tiere ihren Haltern zuordnen und Termine mit Behandlungen und Medikamenten festhalten. |
| `car-workshop` | Eine Autowerkstatt möchte Fahrzeuge, Arbeitsaufträge, Mechaniker und die pro Auftrag verbauten Teile verwalten. |
| `event-tickets` | Eine Ticketplattform möchte Veranstaltungen an Spielorten veröffentlichen und Tickets verschiedener Kategorien an Käufer verkaufen. |
| `hotel` (Reserve) | Ein Hotel möchte Zimmer nach Typ buchen und Leistungen wie Frühstück oder Parkplatz einer Buchung zurechnen. |
| `food-marketplace` (Reserve) | Ein Liefermarktplatz möchte Gerichte mehrerer Restaurants bestellen, durch Kuriere ausliefern und anschließend bewerten lassen. |

## Gruppe zu Domäne

Diese Tabelle wird am ersten Tag ausgefüllt.

| Gruppe | Gruppenname | Domäne |
| --- | --- | --- |
| 1 |  |  |
| 2 |  |  |
| 3 |  |  |
| 4 |  |  |
| 5 |  |  |
| 6 |  |  |
| 7 |  |  |
| 8 |  |  |

## Abgabe

### Tag 1

- Foto oder SVG des ER-Diagramms
- Liste der normalisierten Tabellen mit Primary Keys und Foreign Keys

### Tag 2

- `schema.sql`
- `seed.sql`
- `queries.sql`
- das bearbeitete JDBC-Projekt

### Tag 3

- das bearbeitete Spring-Projekt
- Screenshot der eigenen API in Swagger UI

## Lösungen

Öffnet `solutions/` erst nach der gemeinsamen Auswertung. Die Dateien sind für den Vergleich im Debrief gedacht, nicht als Vorlage während der Bearbeitung.
