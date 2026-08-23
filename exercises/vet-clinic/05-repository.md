# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Pet` und `PetRepository` aus der JDBC-Aufgabe. Als zweite Entität kommt `Appointment` hinzu. Ein Termin verweist über `petId` auf `Pet` und über `vetId` auf einen Tierarzt. Speichert außerdem Terminzeitpunkt, Anlass und Status.

Ergänzt im Appointment-Repository `List<Appointment> findByPetId(Long petId)`. Der Aufruf mit `petId` 2 soll die Termine des Hundes Balu nach Terminzeitpunkt sortiert liefern. Eine unbekannte Tier-ID ergibt eine leere Liste.

## Aufgabe

1. Ergänzt `Appointment` mit Tabelle, Model und Repository.
2. Setzt die Foreign Keys auf `pets.id` und `vets.id`.
3. Implementiert `findByPetId` mit `PreparedStatement` und `List<Appointment>` als Rückgabetyp.
4. Zeigt im CLI zu einem gefundenen Tier seine Termine mit Tierarzt, Zeitpunkt und Status an.
5. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen der Tabellen einschließlich Foreign Keys
- Beispielaufruf und Ausgabe von `findByPetId`
