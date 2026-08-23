# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Pizza` und `PizzaRepository` aus der JDBC-Aufgabe. Ergänzt als zweite Entität `Order` mit einem `OrderRepository`. Eine Bestellung speichert `id`, `orderNumber`, `customerId`, die optionale `driverId`, `orderedOn`, `orderType` und `status`. `customerId` verweist auf `customers.id`, `driverId` optional auf `drivers.id`.

Ergänzt im Order-Repository `List<Order> findByCustomerId(Long customerId)`. Der Aufruf mit `customerId` 2 soll die Bestellungen `B-26002`, `B-26008` und `B-26016` liefern, eine unbekannte Kunden-ID ergibt eine leere Liste.

## Aufgabe

1. Ergänzt `Order` mit Tabelle, Model und Repository.
2. Setzt die Foreign Keys auf `customers.id` und `drivers.id`.
3. Implementiert `findByCustomerId` mit `PreparedStatement` und `List<Order>` als Rückgabetyp.
4. Zeigt im CLI zu einem eingegebenen Kunden alle Bestellungen nach Bestelldatum an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen der beteiligten Tabellen einschließlich Foreign Keys
- Beispielaufruf und Ausgabe von `findByCustomerId`
