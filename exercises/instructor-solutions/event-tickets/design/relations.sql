-- Lesbare Relationsnotation, kein ausführbares SQL
venues(id PK, venue_code UK NOT NULL, name UK NOT NULL, street NOT NULL, postal_code NOT NULL, city NOT NULL, capacity NOT NULL)
organizers(id PK, organizer_number UK NOT NULL, name UK NOT NULL, contact_person NOT NULL, email UK NOT NULL, phone NOT NULL)
events(id PK, event_number UK NOT NULL, title NOT NULL, event_type NOT NULL, admission_code NOT NULL, venue_id FK NOT NULL -> venues.id, organizer_id FK NULL -> organizers.id, event_on NOT NULL, doors_open NOT NULL, starts_at NOT NULL, UK(title, admission_code), UK(venue_id, event_on, starts_at))
ticket_categories(event_id PK FK NOT NULL -> events.id, name PK NOT NULL, list_price NOT NULL, quota NOT NULL, seating_type NOT NULL)
buyers(id PK, buyer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, registered_on NOT NULL)
orders(id PK, order_number UK NOT NULL, buyer_id FK NOT NULL -> buyers.id, ordered_at NOT NULL, status NOT NULL)
tickets(id PK, order_id FK NOT NULL -> orders.id, event_id FK NOT NULL, category_name FK NOT NULL, ticket_number NOT NULL, seat_label NULL, price_paid NOT NULL, checked_in_at NULL, FK(event_id, category_name) -> ticket_categories(event_id, name), UK(order_id, ticket_number), UK(event_id, seat_label))

-- event_id und Kategoriename identifizieren eine Ticketkategorie vollständig.
-- NULL bei organizer_id kennzeichnet eine Eigenveranstaltung der Plattform.
