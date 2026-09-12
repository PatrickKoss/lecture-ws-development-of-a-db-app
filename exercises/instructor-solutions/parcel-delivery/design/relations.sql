-- Lesbare Relationsnotation, kein ausführbares SQL
depots(id PK, code UK NOT NULL, city NOT NULL)
customers(id PK, email UK NOT NULL, name NOT NULL)
parcels(id PK, tracking_code UK NOT NULL, recipient NOT NULL, weight NOT NULL, origin_depot_id FK NOT NULL -> depots.id, destination_depot_id FK NOT NULL -> depots.id, sender_id FK NOT NULL -> customers.id)
status_events(parcel_id PK FK NOT NULL -> parcels.id, event_number PK NOT NULL, status NOT NULL, recorded_at NOT NULL, depot_id FK NULL -> depots.id)
delivery_attempts(id PK, parcel_id FK NOT NULL -> parcels.id, attempted_at NOT NULL, outcome NOT NULL)

-- Paket und Ereignisnummer identifizieren eine Statusmeldung ohne künstliche ID.
-- NULL bei depot_id erlaubt Meldungen wie "in Zustellung", die keinem Depotscan entsprechen.
