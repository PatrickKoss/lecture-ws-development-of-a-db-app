# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Screening` unter `/screenings`. Der Beziehungsendpunkt liefert die Tickets, die für eine Vorstellung verkauft wurden. Die fachliche Richtung geht von der Vorstellung zu ihren Tickets.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Filmnummer, Filmtitel, Saalnummer und Saalname enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/screenings` | keiner | Liste von `ScreeningResponse` | `200` |
| `GET` | `/screenings/{id}` | keiner | `ScreeningResponse` | `200`, `404` |
| `POST` | `/screenings` | `CreateScreeningRequest` | angelegte `ScreeningResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/screenings/{id}` | `UpdateScreeningRequest` | geänderte `ScreeningResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/screenings/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/screenings/{id}/tickets` | keiner | Liste von `TicketResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Screening` an. Startet mit einer festen Liste und `GET /screenings`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregel für Saal und Startzeit sowie eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /screenings/{id}/tickets`.

## Vorgaben

- `movieId`, `hallId`, `startsAt`, `language` und `format` sind Pflichtfelder. Numerische IDs müssen positiv sein.
- `startsAt` muss ein gültiger ISO-Zeitpunkt sein und Sekunden enthalten.
- `language` ist auf `DE`, `OV` und `OMU` beschränkt.
- `format` ist auf `DCP_2D`, `DCP_3D` und `35MM` beschränkt.
- Ein Saal darf zur selben Startzeit höchstens eine Vorstellung haben.

Gebt für ungültiges JSON, fehlende Pflichtfelder und ungültige Sprachfassungen, Formate oder Zeitpunkte `400 Bad Request` zurück. Eine unbekannte Vorstellung, ein unbekannter Film oder ein unbekannter Saal ergibt `404 Not Found`. Ist der Saal zur Startzeit bereits belegt, antwortet die API mit `409 Conflict`. Das Löschen einer Vorstellung mit bereits verkauften Tickets ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /screenings` eine gültige Vorstellung an und prüft Status `201`, Response-ID, Film-ID und Startzeit. Der zweite versucht, zur selben Startzeit eine weitere Vorstellung im selben Saal anzulegen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
