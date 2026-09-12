-- Lesbare Relationsnotation, kein ausführbares SQL
galleries(id PK, name UK NOT NULL, floor NOT NULL)
artists(id PK, name NOT NULL, birth_year NULL)
exhibits(id PK, inventory_code UK NOT NULL, title NOT NULL, insured_value NOT NULL, gallery_id FK NULL -> galleries.id)
exhibit_artists(exhibit_id PK FK NOT NULL -> exhibits.id, artist_id PK FK NOT NULL -> artists.id, role PK NOT NULL)
loans(id PK, exhibit_id FK NOT NULL -> exhibits.id, borrower NOT NULL, starts_on NOT NULL, ends_on NOT NULL)

-- Exponat, Urheber und Rolle identifizieren eine Beteiligung vollständig.
-- NULL bei gallery_id bedeutet, dass das Exponat aktuell keiner Galerie zugeordnet ist.
