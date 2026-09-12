-- Lesbare Relationsnotation, kein ausführbares SQL
reference_items(id PK, name UK NOT NULL)
locations(id PK, location_code UK NOT NULL, name UK NOT NULL)
custodians(id PK, personnel_code UK NOT NULL, name NOT NULL, email UK NOT NULL)
resources(id PK, resource_code UK NOT NULL, name NOT NULL, measure NOT NULL, reference_item_id FK NULL -> reference_items.id, current_location_id FK NULL -> locations.id)
allocations(resource_id PK FK NOT NULL -> resources.id, sequence_number PK NOT NULL, custodian_id FK NOT NULL -> custodians.id, allocated_at NOT NULL, returned_at NULL)
inspections(resource_id PK FK NOT NULL -> resources.id, sequence_number PK NOT NULL, inspected_on NOT NULL, result NOT NULL)

-- Ressource und laufende Nummer identifizieren Ausgabe und Prüfung ohne künstliche ID.
-- NULL bei current_location_id bedeutet, dass aktuell kein Standort zugeordnet ist.
-- Der partielle Schlüssel UK(resource_id) WHERE returned_at IS NULL erlaubt nur eine offene Ausgabe je Ressource.
