# 6. REST API

> Zeitbox: 125 Minuten, davon 25 Minuten für den API-Entwurf, 45 Minuten für den ersten laufenden Stand und 55 Minuten für CRUD und Fehlerfälle. Der Beziehungsendpunkt ist freiwillige Zusatzarbeit

## Ausgangspunkt

Arbeitet im Projekt `java/rest-simple-exercise`. Für den Unterricht bearbeitet ihr die Phasen 1 bis 3 aus dessen README. Phase 4 ist nicht Teil dieser Aufgabe. Die Beispiele verwenden `Student`; ihr setzt dieselben Schritte für eure eigene Domäne um.

Die Hauptressource ist `Order` unter `/orders`. Der Beziehungsendpunkt liefert die optionale `Review` einer Bestellung. Die fachliche Richtung geht von der Bestellung zur Bewertung.

## Aufgabe

### API auf Papier

Plant Request- und Response-JSON für diese Endpunkte. IDs verwandter Datensätze stehen im Request, die Response darf zusätzlich Kundennummer, Restaurantname, Kuriername und die Positionen enthalten.

| Methode | Pfad | Request | Response | Statuscodes |
| --- | --- | --- | --- | --- |
| `GET` | `/orders` | keiner | Liste von `OrderResponse` | `200` |
| `GET` | `/orders/{id}` | keiner | `OrderResponse` | `200`, `404` |
| `POST` | `/orders` | `CreateOrderRequest` | angelegte `OrderResponse` | `201`, `400`, `404`, `409` |
| `PUT` | `/orders/{id}` | `UpdateOrderRequest` | geänderte `OrderResponse` | `200`, `400`, `404`, `409` |
| `DELETE` | `/orders/{id}` | keiner | kein Inhalt | `204`, `404`, `409` |
| `GET` | `/orders/{id}/review` | keiner | `ReviewResponse` | `200`, `404` |

### Phasen 1 bis 3 umsetzen

1. Legt ein einfaches Model und ein Response-DTO für `Order` an. Startet mit einer festen Liste und `GET /orders`.
2. Fügt eine Flyway-Migration, eine JPA-Entity und ein `JpaRepository` hinzu. Stellt vollständiges CRUD über Request- und Response-DTOs bereit.
3. Ergänzt Bean Validation, `@Valid`, die Regeln für Positionen und Kurierzuordnung sowie passende Exceptions.
4. Verschiebt die Fachlogik in einen `@Service` mit Transaktionsgrenzen. Der Controller behandelt nur HTTP-Belange.
5. Freiwillige Zusatzarbeit: implementiert `GET /orders/{id}/review`.

## Vorgaben

- `customerId`, `restaurantId`, `orderNumber`, `orderedAt`, `orderType`, `status` und mindestens eine Position sind Pflichtfelder. Numerische IDs, Positionsnummern, Mengen und Preise müssen positiv sein.
- Jede Position nennt `dishId`, `quantity` und `unitPrice`. Das Gericht muss zum Restaurant der Bestellung gehören. Eine Positionsnummer darf innerhalb einer Bestellung nur einmal vorkommen.
- `courierId`, `pickedUpAt` und `deliveredAt` dürfen fehlen. Für eine Abholbestellung müssen alle drei Werte fehlen.
- Sobald `pickedUpAt` oder `deliveredAt` gesetzt ist, muss ein Kurier zugeordnet sein. `deliveredAt` darf nicht vor `pickedUpAt` liegen.
- Eine Bestellung darf höchstens eine Bewertung haben. Die Bewertung selbst wird nicht über die Order-Endpunkte angelegt.

Gebt für ungültiges JSON, fehlende Pflichtfelder und widersprüchliche Zeitpunkte `400 Bad Request` zurück. Eine unbekannte Bestellung, ein unbekannter Kunde, ein unbekanntes Restaurant, ein unbekanntes Gericht oder ein unbekannter Kurier ergibt `404 Not Found`. Eine doppelte Bestellnummer, ein Gericht aus einem anderen Restaurant oder eine bereits verwendete Positionsnummer ergibt `409 Conflict`. Das Löschen einer bereits abgeholten oder ausgelieferten Bestellung ergibt ebenfalls `409 Conflict`.

Schreibt genau zwei Tests. Der erste legt mit `POST /orders` eine gültige Bestellung an und prüft Status `201`, Response-ID, Bestellnummer und die Anzahl der Positionen. Der zweite versucht, eine Bestellung mit einem Gericht aus einem anderen Restaurant anzulegen, und prüft Status `409` sowie den Fehlercode im Response-DTO.

## Abgabe

- das ausführbare Spring-Projekt mit den Phasen 1 bis 3 für die eigene Domäne
- Liste der Endpunkte mit Methoden und Statuscodes
- Screenshot der Endpunkte in Swagger UI
- Ergebnisse der zwei beschriebenen Tests
