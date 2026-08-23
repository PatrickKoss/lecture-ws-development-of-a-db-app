# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Course` und `CourseRepository` aus der JDBC-Aufgabe. Als zweite Entität kommt `Booking` hinzu. Eine Buchung verweist über `memberId` auf ein Mitglied und über `courseSessionId` auf einen Kurstermin. Speichert außerdem `bookedOn` und `attended`. Die Kombination aus `memberId` und `courseSessionId` ist eindeutig.

Ergänzt im `BookingRepository` die Methode `List<Booking> findByMemberId(Long memberId)`. Der Aufruf mit `memberId` 1 soll die drei Buchungen von Lena Fischer liefern, eine unbekannte Mitglieds-ID ergibt eine leere Liste.

## Aufgabe

1. Ergänzt `Booking` mit Tabelle, Model und Repository.
2. Setzt die Foreign Keys auf `members.id` und `course_sessions.id`.
3. Implementiert `findByMemberId` mit `PreparedStatement` und `List<Booking>` als Rückgabetyp.
4. Zeigt im CLI für ein Mitglied alle gebuchten Kurstermine mit Buchungsdatum und Teilnahmeangabe an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen der Kurs- und Buchungstabelle einschließlich Foreign Keys
- Beispielaufruf und Ausgabe von `findByMemberId`
