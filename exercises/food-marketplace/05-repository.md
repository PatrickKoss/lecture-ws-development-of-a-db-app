# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Restaurant` und `RestaurantRepository` aus der JDBC-Aufgabe. Ergänzt eine zweite Entität `Order` und `List<Order> findByCustomerId(Long customerId)`. Der Aufruf mit `2L` soll die Bestellungen O-26002 und O-26008 liefern, eine unbekannte Kunden-ID ergibt eine leere Liste.

Eine Bestellung verweist über `restaurantId` auf `Restaurant` und über `customerId` auf einen vorhandenen Kunden. Speichert außerdem die eindeutige Bestellnummer, Bestellzeitpunkt, Bestellart, Status und optional `courierId`, `pickedUpAt` sowie `deliveredAt`.

## Aufgabe

1. Ergänzt `Order` mit Tabelle, Model und Repository.
2. Implementiert im Order-Repository die Methode `findByCustomerId` mit `PreparedStatement` und `List<Order>` als Rückgabetyp.
3. Setzt die Foreign Keys auf `restaurants.id` und `customers.id`.
4. Zeigt im CLI zu einer eingegebenen Kunden-ID alle Bestellungen mit Restaurantname und Status an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen der beteiligten Tabellen einschließlich Foreign Keys
- Beispielaufruf und Ausgabe von `findByCustomerId`
