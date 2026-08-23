# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Appointment` unter `/appointments`. Der Beziehungsendpunkt liefert die optionale `Treatment`, die aus einem Termin entstanden ist. Ein ausgefallener Termin oder eine Kontrolle ohne dokumentierte Behandlung hat keine zugehörige Behandlung.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Tiername, Halternummer und Tierarztname enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/appointments` | keiner | Liste von `AppointmentResponse` | `200` |
| `GET` | `/appointments/{id}` | keiner | `AppointmentResponse` | `200`, `404` |
| `POST` | `/appointments` | `CreateAppointmentRequest` | angelegte `AppointmentResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/appointments/{id}` | `UpdateAppointmentRequest` | geänderte `AppointmentResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/appointments/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/appointments/{id}/treatment` | keiner | `TreatmentResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Appointment` an. Startet mit einer festen Liste und `GET /appointments`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregel für Termine eines Tierarztes zum selben Zeitpunkt und eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /appointments/{id}/treatment`.

## Vorgaben

- `petId`, `vetId`, `scheduledAt`, `reason` und `status` sind Pflichtfelder. Numerische IDs müssen positiv sein.
- `scheduledAt` ist ein Pflichtfeld im ISO-Format `YYYY-MM-DDTHH:MM`.
- `reason` hat zwischen 3 und 200 Zeichen.
- `status` ist einer der Werte `PLANNED`, `COMPLETED`, `CANCELLED` oder `NO_SHOW`.
- Ein Tierarzt darf zum selben Zeitpunkt höchstens einen Termin haben.
- Der Status `COMPLETED` ist nur erlaubt, wenn eine Behandlung zum Termin vorhanden ist. `NO_SHOW` darf keine Behandlung haben.

Gebt für ungültiges JSON, fehlende Pflichtfelder, ungültige Statuswerte und zu kurze Anlässe `400 Bad Request` zurück. Ein unbekannter Termin, ein unbekanntes Tier, ein unbekannter Tierarzt oder eine fehlende Behandlung am Beziehungsendpunkt ergibt `404 Not Found`. Ist der Tierarzt zum gewünschten Zeitpunkt schon belegt, antwortet die API mit `409 Conflict`. Das Löschen eines Termins mit Behandlung ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /appointments` einen gültigen Termin an und prüft Status `201`, Response-ID und Terminzeitpunkt. Der zweite versucht, für denselben Tierarzt einen weiteren Termin zum selben Zeitpunkt anzulegen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
