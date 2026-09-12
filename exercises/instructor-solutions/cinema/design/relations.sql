-- Lesbare Relationsnotation, kein ausführbares SQL
movies(id PK, movie_code UK NOT NULL, title NOT NULL, release_year NOT NULL, duration_minutes NOT NULL, fsk_code NOT NULL, minimum_age NOT NULL, UK(title, minimum_age))
halls(id PK, hall_number UK NOT NULL, name UK NOT NULL, capacity NOT NULL)
seats(hall_id PK FK NOT NULL -> halls.id, row_label PK NOT NULL, seat_number PK NOT NULL, category NOT NULL, accessible NOT NULL)
screenings(id PK, screening_code UK NOT NULL, movie_id FK NOT NULL -> movies.id, hall_id FK NOT NULL -> halls.id, starts_at NOT NULL, language NOT NULL, projection_format NOT NULL, UK(hall_id, starts_at), UK(id, hall_id))
customers(id PK, customer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, registered_on NOT NULL, active NOT NULL)
tickets(id PK, ticket_number UK NOT NULL, screening_id FK NOT NULL, hall_id FK NOT NULL, row_label FK NOT NULL, seat_number FK NOT NULL, customer_id FK NULL -> customers.id, price_cents NOT NULL, sold_at NOT NULL, FK(screening_id, hall_id) -> screenings(id, hall_id), FK(hall_id, row_label, seat_number) -> seats(hall_id, row_label, seat_number), UK(screening_id, hall_id, row_label, seat_number))

-- Ein Sitz wird durch Saal, Reihe und Nummer identifiziert. Eine künstliche ID wäre fachlich leer.
-- NULL bei customer_id erlaubt den anonymen Verkauf an der Kinokasse.
