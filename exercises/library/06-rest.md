# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Loan` unter `/loans`. Der Beziehungsendpunkt liefert die optionale `Reservation`, aus der eine Ausleihe entstanden ist. Die fachliche Richtung geht von der Ausleihe zur Vormerkung.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Mitgliedsnummer, Titel und Exemplarnummer enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/loans` | keiner | Liste von `LoanResponse` | `200` |
| `GET` | `/loans/{id}` | keiner | `LoanResponse` | `200`, `404` |
| `POST` | `/loans` | `CreateLoanRequest` | angelegte `LoanResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/loans/{id}` | `UpdateLoanRequest` | geänderte `LoanResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/loans/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/loans/{id}/reservation` | keiner | `ReservationResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Loan` an. Startet mit einer festen Liste und `GET /loans`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregel für aktive Ausleihen eines Exemplars und eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /loans/{id}/reservation`.

## Vorgaben

- `memberId`, `bookId`, `copyNumber` und `loanedOn` sind Pflichtfelder. Numerische IDs und die Exemplarnummer müssen positiv sein.
- `dueOn` ist ein Pflichtfeld im ISO-Datumsformat und darf nicht vor `loanedOn` liegen.
- `returnedOn` darf fehlen. Wenn es gesetzt ist, darf es nicht vor `loanedOn` liegen.
- Eine `reservationId` darf fehlen. Wenn sie gesetzt ist, muss die Vormerkung zu demselben Mitglied und Buch gehören.
- Ein Exemplar darf höchstens eine Ausleihe ohne Rückgabedatum haben.

Gebt für ungültiges JSON, fehlende Pflichtfelder und widersprüchliche Datumswerte `400 Bad Request` zurück. Eine unbekannte Ausleihe, ein unbekanntes Mitglied, ein unbekanntes Exemplar oder eine unbekannte Vormerkung ergibt `404 Not Found`. Ist das Exemplar bereits ausgeliehen oder wurde die Vormerkung schon einer anderen Ausleihe zugeordnet, antwortet die API mit `409 Conflict`. Das Löschen einer noch offenen Ausleihe ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /loans` eine gültige Ausleihe an und prüft Status `201`, Response-ID und Fälligkeitsdatum. Der zweite versucht, dasselbe Exemplar erneut ohne Rückgabedatum auszuleihen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
