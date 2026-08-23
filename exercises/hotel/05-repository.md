# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Room` und `RoomRepository` aus der JDBC-Aufgabe. Als zweite Entität kommt `Booking` hinzu. Eine Buchung verweist über `guestId` auf einen vorhandenen Gast. Speichert außerdem Buchungsnummer, Buchungsdatum, geplante Anreise, geplante Abreise und Status.

Ergänzt `List<Booking> findByGuestId(Long guestId)`. Der Aufruf mit der ID des Gasts G-1002 soll dessen Buchungen nach Anreise sortiert liefern, eine unbekannte Gast-ID ergibt eine leere Liste.

## Aufgabe

1. Ergänzt im Booking-Repository die Methode `findByGuestId`.
2. Implementiert die Abfrage mit `PreparedStatement` und `List<Booking>` als Rückgabetyp.
3. Ergänzt `Booking` mit Tabelle, Model und Repository.
4. Setzt den Foreign Key auf `guests.id` und zeigt im CLI zu einem Gast alle gefundenen Buchungen mit Anreise und Status an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen der benötigten Tabellen einschließlich Foreign Key
- Beispielaufruf und Ausgabe von `findByGuestId`
