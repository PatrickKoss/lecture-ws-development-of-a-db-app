# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `WorkOrder` unter `/work-orders`. Der Beziehungsendpunkt liefert die optionale `Invoice` des Arbeitsauftrags. Offene Arbeitsaufträge haben noch keine Rechnung.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Kundennummer, Kennzeichen und Fahrzeugmodell enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/work-orders` | keiner | Liste von `WorkOrderResponse` | `200` |
| `GET` | `/work-orders/{id}` | keiner | `WorkOrderResponse` | `200`, `404` |
| `POST` | `/work-orders` | `CreateWorkOrderRequest` | angelegte `WorkOrderResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/work-orders/{id}` | `UpdateWorkOrderRequest` | geänderte `WorkOrderResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/work-orders/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/work-orders/{id}/invoice` | keiner | `InvoiceResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `WorkOrder` an. Startet mit einer festen Liste und `GET /work-orders`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Eindeutigkeitsregel für offene Arbeitsaufträge eines Fahrzeugs und eine passende Exception.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /work-orders/{id}/invoice`.

## Vorgaben

- `orderNumber`, `vehicleId`, `openedOn`, `mileageIn` und `complaint` sind Pflichtfelder. `vehicleId` und `mileageIn` müssen positiv sein.
- `orderNumber` hat das Format `A-` gefolgt von fünf Ziffern. Die Beanstandung hat 10 bis 500 Zeichen.
- `closedOn` darf fehlen. Wenn es gesetzt ist, darf es nicht vor `openedOn` liegen.
- Der Status ist `OPEN`, `IN_PROGRESS`, `COMPLETED` oder `INVOICED`. Ein abgeschlossener oder abgerechneter Auftrag braucht ein Abschlussdatum.
- Ein Fahrzeug darf höchstens einen Arbeitsauftrag mit dem Status `OPEN` oder `IN_PROGRESS` haben.
- Eine Rechnung darf fehlen. Wenn sie vorhanden ist, muss sie genau zu diesem Arbeitsauftrag gehören.

Gebt für ungültiges JSON, fehlende Pflichtfelder und widersprüchliche Datumswerte `400 Bad Request` zurück. Ein unbekannter Arbeitsauftrag, ein unbekanntes Fahrzeug oder eine fehlende Rechnung am Beziehungsendpunkt ergibt `404 Not Found`. Ist die Auftragsnummer bereits vergeben oder hat das Fahrzeug schon einen offenen Arbeitsauftrag, antwortet die API mit `409 Conflict`. Das Löschen eines Auftrags mit Rechnung ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /work-orders` einen gültigen Arbeitsauftrag an und prüft Status `201`, Response-ID und Eingangsdatum. Der zweite versucht, für dasselbe Fahrzeug einen weiteren offenen Arbeitsauftrag anzulegen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
