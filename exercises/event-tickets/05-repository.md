# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Event` und `EventRepository` aus der JDBC-Aufgabe. Als zweite Entität kommt `Ticket` hinzu. Ein Ticket verweist über `eventId` auf `Event`; `orderId` und `ticketNumber` identifizieren es gemeinsam. Speichert außerdem den Kategorienamen, den optionalen Sitzplatz, den bezahlten Preis und den optionalen Check-in-Zeitpunkt.

Ergänzt im Ticket-Repository `List<Ticket> findByEventId(long eventId)`. Der Aufruf mit der ID von E-2601 soll vier Tickets liefern, eine Veranstaltungs-ID ohne verkaufte Tickets ergibt eine leere Liste.

## Aufgabe

1. Ergänzt `Ticket` mit Tabelle, Model und Repository.
2. Setzt den Foreign Key auf `events.id` und die Eindeutigkeit von `orderId` und `ticketNumber` um.
3. Implementiert `findByEventId` mit `PreparedStatement` und sortiert nach `orderId` und `ticketNumber`.
4. Zeigt im CLI zu jeder gefundenen Veranstaltung ihre Tickets mit Kategorie, Sitzplatz und Check-in-Status an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen beider Tabellen einschließlich Foreign Key
- Beispielaufruf und Ausgabe von `findByEventId`
