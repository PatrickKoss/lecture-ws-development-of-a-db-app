# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Event` unter `/events`. Der Beziehungsendpunkt liefert den optionalen `Organizer`, der die Veranstaltung betreut. Veranstaltungen des eigenen Teams haben keinen zugeordneten externen Veranstalter.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Spielortname, Veranstaltername sowie verkaufte und eingecheckte Ticketzahlen enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/events` | keiner | Liste von `EventResponse` | `200` |
| `GET` | `/events/{id}` | keiner | `EventResponse` | `200`, `404` |
| `POST` | `/events` | `CreateEventRequest` | angelegte `EventResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/events/{id}` | `UpdateEventRequest` | geänderte `EventResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/events/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/events/{id}/organizer` | keiner | `OrganizerResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Event` an. Startet mit einer festen Liste und `GET /events`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregeln für Veranstaltungsnummer und Spielortbelegung sowie passende Exceptions.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /events/{id}/organizer`.

## Vorgaben

- `eventNumber`, `title`, `eventType`, `venueId`, `eventOn`, `doorsOpen` und `startsAt` sind Pflichtfelder. Numerische IDs müssen positiv sein.
- Der Titel hat 1 bis 200 Zeichen. `eventType` ist `KONZERT` oder `LESUNG`.
- Datum und Uhrzeiten verwenden ISO-Formate. `doorsOpen` darf nicht nach `startsAt` liegen.
- Eine `organizerId` darf fehlen. Wenn sie gesetzt ist, muss der Veranstalter vorhanden sein.
- Veranstaltungsnummern sind eindeutig. Ein Spielort darf zu demselben Datum und derselben Startzeit nur einmal belegt sein.

Gebt für ungültiges JSON, fehlende Pflichtfelder, eine unbekannte Veranstaltungsart und widersprüchliche Uhrzeiten `400 Bad Request` zurück. Eine unbekannte Veranstaltung, ein unbekannter Spielort oder ein unbekannter Veranstalter ergibt `404 Not Found`. Eine doppelte Veranstaltungsnummer oder eine doppelte Spielortbelegung beantwortet die API mit `409 Conflict`. Das Löschen einer Veranstaltung mit verkauften Tickets ergibt ebenfalls `409 Conflict`. Für eine vorhandene Veranstaltung ohne externen Veranstalter liefert der Beziehungsendpunkt `404 Not Found`.

Schreibt genau zwei Tests. Der erste legt mit `POST /events` eine gültige Veranstaltung an und prüft Status `201`, Response-ID, Veranstaltungsnummer und Datum. Der zweite versucht, denselben Spielort zur gleichen Startzeit am gleichen Tag erneut zu belegen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
