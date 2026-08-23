# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Booking` unter `/bookings`. Der Beziehungsendpunkt liefert den optionalen `Employee`, der den Gast eingecheckt hat. Bei einer Online-Buchung ohne Check-in ist noch kein Mitarbeiter zugeordnet.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Gastnummer, Gastname und Zimmernummern enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/bookings` | keiner | Liste von `BookingResponse` | `200` |
| `GET` | `/bookings/{id}` | keiner | `BookingResponse` | `200`, `404` |
| `POST` | `/bookings` | `CreateBookingRequest` | angelegte `BookingResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/bookings/{id}` | `UpdateBookingRequest` | geänderte `BookingResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/bookings/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/bookings/{id}/check-in-employee` | keiner | `EmployeeResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Booking` an. Startet mit einer festen Liste und `GET /bookings`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Regel gegen überlappende Zimmerbelegungen und eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /bookings/{id}/check-in-employee`.

## Vorgaben

- `guestId`, `bookingNumber`, `bookedOn`, `arrivalOn`, `departureOn` und mindestens ein Zimmer sind Pflichtangaben. Numerische IDs, Etagen und Zimmernummern müssen positiv sein.
- `bookedOn`, `arrivalOn` und `departureOn` verwenden das ISO-Datumsformat. `departureOn` muss nach `arrivalOn` liegen, `bookedOn` darf nicht nach `arrivalOn` liegen.
- Als Status sind `PENDING`, `CONFIRMED`, `CHECKED_IN`, `CHECKED_OUT` und `CANCELLED` erlaubt.
- Eine `checkedInByEmployeeId` darf fehlen. Bei Status `CHECKED_IN` oder `CHECKED_OUT` muss sie gesetzt sein.
- Ein Zimmer darf im Zeitraum von Anreise einschließlich bis Abreise ausschließlich nicht zwei aktive Buchungen haben. Stornierte Buchungen zählen nicht als Belegung.

Gebt für ungültiges JSON, fehlende Pflichtfelder und widersprüchliche Datumswerte `400 Bad Request` zurück. Eine unbekannte Buchung, ein unbekannter Gast, ein unbekanntes Zimmer oder ein unbekannter Mitarbeiter ergibt `404 Not Found`. Überschneidet sich der Zeitraum mit einer aktiven Buchung desselben Zimmers oder ist die Buchungsnummer bereits vergeben, antwortet die API mit `409 Conflict`. Das Löschen einer eingecheckten Buchung ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /bookings` eine gültige Buchung an und prüft Status `201`, Response-ID und geplantes Abreisedatum. Der zweite versucht, dasselbe Zimmer für einen überlappenden Zeitraum erneut zu buchen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
