# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Booking` unter `/bookings`. Der Beziehungsendpunkt liefert den `CourseSession`, für den eine Buchung angelegt wurde. Die fachliche Richtung geht von der Buchung zum konkreten Kurstermin.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Mitgliedsnummer, Kurstitel, Datum und Startzeit enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/bookings` | keiner | Liste von `BookingResponse` | `200` |
| `GET` | `/bookings/{id}` | keiner | `BookingResponse` | `200`, `404` |
| `POST` | `/bookings` | `CreateBookingRequest` | angelegte `BookingResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/bookings/{id}` | `UpdateBookingRequest` | geänderte `BookingResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/bookings/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/bookings/{id}/course-session` | keiner | `CourseSessionResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Booking` an. Startet mit einer festen Liste und `GET /bookings`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregel für ein Mitglied und einen Kurstermin sowie eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /bookings/{id}/course-session`.

## Vorgaben

- `memberId`, `courseSessionId` und `bookedOn` sind Pflichtfelder. Numerische IDs müssen positiv sein.
- `bookedOn` steht im ISO-Datumsformat und darf nicht nach dem Kurstag liegen.
- `attended` ist ein Pflichtfeld und darf nur `true` oder `false` sein.
- Ein Mitglied darf denselben Kurstermin höchstens einmal buchen.
- Abgesagte oder bereits vergangene Kurstermine dürfen nicht neu gebucht werden.
- Ein Kurstermin darf nicht über seine maximale Teilnehmerzahl hinaus gebucht werden.

Gebt für ungültiges JSON, fehlende Pflichtfelder und ein Buchungsdatum nach dem Kurstag `400 Bad Request` zurück. Eine unbekannte Buchung, ein unbekanntes Mitglied oder ein unbekannter Kurstermin ergibt `404 Not Found`. Eine doppelte Buchung, ein abgesagter Termin oder ein ausgebuchter Termin führt zu `409 Conflict`. Das Löschen einer Buchung mit bestätigter Teilnahme ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /bookings` eine gültige Buchung an und prüft Status `201`, Response-ID und Kurstermin-ID. Der zweite versucht, dasselbe Mitglied erneut für denselben Kurstermin anzumelden, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
