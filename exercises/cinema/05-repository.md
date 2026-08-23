# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Movie` und `MovieRepository` aus der JDBC-Aufgabe. Als zweite Entität kommt `Ticket` hinzu. Arbeitet dafür mit den Tabellen aus `sql/schema.sql`, damit die referenzierten Vorstellungen und Sitze bereits vorhanden sind.

Ein Ticket verweist über `screeningId` auf `Screening` und über `hallId`, `rowLabel` und `seatNumber` auf `Seat`. Speichert außerdem die eindeutige Ticketnummer, den Preis in Cent, den Verkaufszeitpunkt und eine optionale `customerId`. Ergänzt im Ticket-Repository `List<Ticket> findByScreeningId(long screeningId)`. Der Aufruf mit der ID `1` soll vier Tickets für V-26001 liefern, eine unbekannte ID ergibt eine leere Liste.

## Aufgabe

1. Ergänzt `Ticket` mit Model und Repository.
2. Implementiert `findByScreeningId` mit `PreparedStatement` und `List<Ticket>` als Rückgabetyp.
3. Setzt die Foreign Keys auf `screenings`, `seats` und optional `customers`.
4. Zeigt im CLI zu einer eingegebenen Vorstellungs-ID alle Tickets mit Reihe, Sitznummer und Preis an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen von `movies` und `tickets` einschließlich der Foreign Keys
- Beispielaufruf und Ausgabe von `findByScreeningId`
