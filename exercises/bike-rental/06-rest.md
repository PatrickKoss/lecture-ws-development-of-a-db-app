# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Rental` unter `/rentals`. Der Beziehungsendpunkt liefert den optionalen Tarif des Kunden zum Start der Ausleihe. Die fachliche Richtung geht von der Ausleihe über den Kunden zur gültigen Tarifzuordnung.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Kundennummer, Radnummer sowie Namen der Start- und Endstation enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/rentals` | keiner | Liste von `RentalResponse` | `200` |
| `GET` | `/rentals/{id}` | keiner | `RentalResponse` | `200`, `404` |
| `POST` | `/rentals` | `CreateRentalRequest` | angelegte `RentalResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/rentals/{id}` | `UpdateRentalRequest` | geänderte `RentalResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/rentals/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/rentals/{id}/tariff` | keiner | `TariffResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Rental` an. Startet mit einer festen Liste und `GET /rentals`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregel für offene Ausleihen eines Rads und eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /rentals/{id}/tariff`.

## Vorgaben

- `customerId`, `bikeId`, `startStationId` und `startTime` sind Pflichtfelder. Numerische IDs müssen positiv sein.
- `endTime` und `endStationId` dürfen nur gemeinsam fehlen oder gemeinsam gesetzt sein. Die Endzeit darf nicht vor der Startzeit liegen.
- `priceCents` darf bei einer offenen Ausleihe fehlen. Bei einer beendeten Ausleihe ist der Preis Pflicht und darf nicht negativ sein.
- Die Startstation muss aktiv sein. Sie muss beim Anlegen dem aktuellen Standort des verfügbaren Rads entsprechen.
- Ein Rad darf höchstens eine Ausleihe ohne Endzeit haben.

Gebt für ungültiges JSON, fehlende Pflichtfelder und widersprüchliche Zeit- oder Preisangaben `400 Bad Request` zurück. Eine unbekannte Ausleihe, ein unbekannter Kunde, ein unbekanntes Rad oder eine unbekannte Station ergibt `404 Not Found`. Ist das Rad bereits ausgeliehen, steht es nicht an der Startstation oder ist die Station geschlossen, antwortet die API mit `409 Conflict`. Das Löschen einer noch offenen Ausleihe ergibt ebenfalls `409 Conflict`. `GET /rentals/{id}/tariff` liefert `404 Not Found`, wenn die Ausleihe unbekannt ist oder der Kunde zum Startzeitpunkt keinen Tarif hatte.

Schreibt genau zwei Tests. Der erste legt mit `POST /rentals` eine gültige Ausleihe an und prüft Status `201`, Response-ID und Startzeit. Der zweite versucht, dasselbe Rad erneut ohne Endzeit auszuleihen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
