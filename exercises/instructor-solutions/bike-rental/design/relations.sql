-- Lesbare Relationsnotation, kein ausführbares SQL
stations(id PK, station_code UK NOT NULL, name UK NOT NULL, address UK NOT NULL, capacity NOT NULL, status NOT NULL)
bike_models(id PK, model_code UK NOT NULL, manufacturer NOT NULL, model_name NOT NULL, category NOT NULL, service_interval_days NOT NULL, UK(model_name, service_interval_days))
tariffs(id PK, tariff_code UK NOT NULL, name UK NOT NULL, base_fee_cents NOT NULL, minute_price_cents NOT NULL, active NOT NULL)
customers(id PK, customer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, registered_on NOT NULL, active NOT NULL)
customer_tariffs(customer_id PK FK NOT NULL -> customers.id, tariff_id FK NOT NULL -> tariffs.id, valid_from PK NOT NULL, UK(customer_id, tariff_id, valid_from))
bikes(id PK, bike_number UK NOT NULL, model_id FK NOT NULL -> bike_models.id, current_station_id FK NULL -> stations.id, status NOT NULL, commissioned_on NOT NULL)
maintenance_logs(bike_id PK FK NOT NULL -> bikes.id, sequence_number PK NOT NULL, logged_on NOT NULL, issue NOT NULL, action_taken NOT NULL, cost_cents NOT NULL)
rentals(id PK, rental_number UK NOT NULL, customer_id FK NOT NULL -> customers.id, bike_id FK NOT NULL -> bikes.id, start_station_id FK NOT NULL -> stations.id, end_station_id FK NULL -> stations.id, start_time NOT NULL, end_time NULL, price_cents NULL)

-- Die zusammengesetzten PK der Tarif- und Wartungshistorie entsprechen ihrer fachlichen Identität.
-- NULL bei current_station_id bedeutet: Das Rad ist ausgeliehen oder ohne aktuellen Standort.
-- Der partielle Schlüssel UK(bike_id) WHERE end_time IS NULL erlaubt nur eine offene Ausleihe je Rad.
