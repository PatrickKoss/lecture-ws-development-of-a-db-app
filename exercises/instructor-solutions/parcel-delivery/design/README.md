# Musterlösung Paketdienst

Das Referenzmodell trennt Pakete, Depots, Absender, Statusmeldungen und Zustellversuche. `status_events` ist über Paket und laufende Nummer identifiziert. So bleibt die Reihenfolge der Scans pro Paket erhalten.

## Funktionale Abhängigkeiten

- `tracking_code -> recipient, weight, origin_depot_id, destination_depot_id, sender_id`
- `depot_id -> depot_code, depot_city`
- `sender_id -> sender_email, sender_name`
- `(parcel_id, event_number) -> status, recorded_at, depot_id`
- `attempt_id -> parcel_id, attempted_at, outcome`

Die breite Exportrelation wiederholt Depotstadt und Absendername bei jeder Statusmeldung. Ändert sich eine Depotbezeichnung, entstehen sonst widersprüchliche Zeilen. Die Tabellen `depots`, `customers` und `status_events` entfernen diese Redundanz.

## Dateien

- `er.mmd` ist das Referenzmodell in Mermaid.
- `er.svg` ist die gerenderte Fassung für den Debrief.
- `../sql/` enthält Schema, Seed-Daten und geprüfte Abfragen.
