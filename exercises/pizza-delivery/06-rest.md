# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Order` unter `/orders`. Der Beziehungsendpunkt liefert den optionalen `Driver`, der die Bestellung ausliefert. Abholbestellungen haben keinen Fahrer.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Kundennummer, Fahrername und die Pizzanamen der Positionen enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/orders` | keiner | Liste von `OrderResponse` | `200` |
| `GET` | `/orders/{id}` | keiner | `OrderResponse` | `200`, `404` |
| `POST` | `/orders` | `CreateOrderRequest` | angelegte `OrderResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/orders/{id}` | `UpdateOrderRequest` | geänderte `OrderResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/orders/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/orders/{id}/driver` | keiner | `DriverResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Order` an. Startet mit einer festen Liste und `GET /orders`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die eindeutige Bestellnummer und passende Exceptions.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /orders/{id}/driver`.

## Vorgaben

- `orderNumber`, `customerId`, `orderedOn`, `orderType`, `status` und mindestens eine Position sind Pflichtfelder. Numerische IDs und Positionsnummern müssen positiv sein.
- `orderNumber` hat das Format `B-26001`. Mengen liegen zwischen 1 und 20, Einzelpreise sind größer als 0.
- Positionsnummern und Pizzen dürfen sich innerhalb einer Bestellung nicht wiederholen.
- Bei einer Lieferung ist `deliveryAddressId` Pflicht und muss zum Kunden gehören. `driverId` darf fehlen, muss aber auf einen aktiven Fahrer verweisen, sobald es gesetzt ist.
- Bei einer Abholung müssen `deliveryAddressId` und `driverId` fehlen.
- Abgeschlossene oder stornierte Bestellungen dürfen nicht mehr geändert werden. Nur Bestellungen im Status `RECEIVED` dürfen gelöscht werden.

Gebt für ungültiges JSON, fehlende Pflichtfelder und widersprüchliche Angaben zu Abholung oder Lieferung `400 Bad Request` zurück. Eine unbekannte Bestellung, ein unbekannter Kunde, eine unbekannte Adresse, eine unbekannte Pizza oder ein unbekannter Fahrer ergibt `404 Not Found`. Der Fahrer-Endpunkt antwortet auch dann mit `404 Not Found`, wenn die Bestellung keinen Fahrer hat. Eine doppelte Bestellnummer, ein nicht aktiver Fahrer oder der Änderungsversuch an einer abgeschlossenen Bestellung ergibt `409 Conflict`. Das Löschen einer Bestellung außerhalb des Status `RECEIVED` ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /orders` eine gültige Lieferbestellung mit zwei Positionen an und prüft Status `201`, Response-ID und Bestellnummer. Der zweite sendet dieselbe Bestellnummer erneut und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
