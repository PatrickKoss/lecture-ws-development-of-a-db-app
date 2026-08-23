# 5. Repository-Pattern

> Zeitbox: 30 Minuten

## Ausgangspunkt

Verwendet `Vehicle` und `VehicleRepository` aus der JDBC-Aufgabe. Als zweite Entität kommt `WorkOrder` hinzu. Ein Arbeitsauftrag verweist über `vehicleId` auf `Vehicle`. Speichert außerdem die eindeutige Auftragsnummer, Eingangsdatum, optionales Abschlussdatum, Status, Kilometerstand bei Annahme und Beanstandung.

Ergänzt im WorkOrder-Repository `List<WorkOrder> findByVehicleId(Long vehicleId)`. Der Aufruf mit der Fahrzeug-ID des VW Golf soll drei Arbeitsaufträge liefern, eine unbekannte Fahrzeug-ID ergibt eine leere Liste.

## Aufgabe

1. Ergänzt `WorkOrder` mit Tabelle, Model und Repository.
2. Setzt den Foreign Key von `vehicleId` auf `vehicles.id`.
3. Ergänzt im WorkOrder-Repository die Methode `findByVehicleId`.
4. Implementiert die Abfrage mit `PreparedStatement` und `List<WorkOrder>` als Rückgabetyp.
5. Zeigt im CLI zu jedem Fahrzeug die gefundenen Arbeitsaufträge mit Status und Eingangsdatum an.
6. Haltet in zwei Sätzen fest, welcher wiederholte JDBC-Code durch das Repository gebündelt wird.

## Abgabe

- beide Repository-Implementierungen und Models
- SQL zum Anlegen beider Tabellen einschließlich Foreign Key
- Beispielaufruf und Ausgabe von `findByVehicleId`
