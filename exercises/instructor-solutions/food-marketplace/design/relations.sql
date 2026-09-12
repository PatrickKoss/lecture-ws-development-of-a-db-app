-- Lesbare Relationsnotation, kein ausführbares SQL
restaurants(id PK, partner_number UK NOT NULL, name NOT NULL, street NOT NULL, postal_code NOT NULL, city NOT NULL, commission_rate NOT NULL, active NOT NULL)
dishes(id PK, restaurant_id FK NOT NULL -> restaurants.id, name NOT NULL, category NOT NULL, vat_rate NOT NULL, current_price NOT NULL, active NOT NULL, UK(restaurant_id, name), UK(name, vat_rate))
customers(id PK, customer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, registered_on NOT NULL, active NOT NULL)
couriers(id PK, courier_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, phone UK NOT NULL, vehicle_type NOT NULL, active NOT NULL)
orders(id PK, order_number UK NOT NULL, customer_id FK NOT NULL -> customers.id, restaurant_id FK NOT NULL -> restaurants.id, courier_id FK NULL -> couriers.id, ordered_at NOT NULL, order_type NOT NULL, status NOT NULL, picked_up_at NULL, delivered_at NULL)
order_items(order_id PK FK NOT NULL -> orders.id, position_number PK NOT NULL, dish_id FK NOT NULL -> dishes.id, quantity NOT NULL, unit_price NOT NULL, UK(order_id, dish_id))
reviews(id PK, order_id FK UK NOT NULL -> orders.id, rating NOT NULL, comment NULL, reviewed_on NOT NULL)

-- Bestellnummer und Positionsnummer identifizieren jede Position ohne zusätzliche ID.
-- NULL bei courier_id bedeutet, dass die Lieferung noch keinem Kurier zugewiesen ist.
