# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Bike` und `BikeRepository` aus der JDBC-Aufgabe. Ergänzt `List<Rental> findByCustomerId(long customerId)` im neuen `RentalRepository`. Der Aufruf mit der ID von K-1001 soll deren Ausleihen nach Startzeit sortiert liefern, eine unbekannte Kunden-ID ergibt eine leere Liste.

Als zweite Entität kommt `Rental` hinzu. Eine Ausleihe verweist über `customerId` auf `Customer`, über `bikeId` auf `Bike` und über `startStationId` auf `Station`. Speichert außerdem Startzeit, optionale Endzeit, optionale Endstation und den Preis in Cent.

## Aufgabe

1. Ergänzt `RentalRepository` um die Methode `findByCustomerId`.
2. Implementiert die Abfrage mit `PreparedStatement` und `List<Rental>` als Rückgabetyp.
3. Ergänzt `Rental` mit Tabelle, Model und Repository.
4. Setzt die Foreign Keys und zeigt im CLI zu einem Kunden seine Ausleihen mit Radnummer und Startstation an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen beider Tabellen einschließlich Foreign Keys
- Beispielaufruf und Ausgabe von `findByCustomerId`
