-- Lesbare Relationsnotation, kein ausführbares SQL
guests(id PK, guest_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, phone NOT NULL, company_name NULL, company_account_number UK NULL, registered_on NOT NULL)
room_types(id PK, type_code UK NOT NULL, name UK NOT NULL, capacity NOT NULL, standard_price_cents NOT NULL)
rooms(floor PK NOT NULL, room_number PK NOT NULL, room_type_id FK NOT NULL -> room_types.id, cleaning_area NOT NULL, status NOT NULL, accessible NOT NULL, UK(room_number, cleaning_area))
employees(id PK, employee_code UK NOT NULL, first_name NOT NULL, last_name NOT NULL, role NOT NULL, hired_on NOT NULL, active NOT NULL)
services(id PK, service_code UK NOT NULL, name UK NOT NULL, list_price_cents NOT NULL, active NOT NULL)
bookings(id PK, booking_number UK NOT NULL, guest_id FK NOT NULL -> guests.id, checked_in_by_employee_id FK NULL -> employees.id, booked_on NOT NULL, arrival_on NOT NULL, departure_on NOT NULL, status NOT NULL)
booking_rooms(booking_id PK FK NOT NULL -> bookings.id, floor PK FK NOT NULL, room_number PK FK NOT NULL, check_in_on NULL, check_out_on NULL, nightly_price_cents NOT NULL, FK(floor, room_number) -> rooms(floor, room_number))
booking_services(booking_id PK FK NOT NULL -> bookings.id, service_id PK FK NOT NULL -> services.id, service_on PK NOT NULL, quantity NOT NULL, unit_price_cents NOT NULL)

-- Etage und Zimmernummer bilden die fachliche Identität eines Zimmers.
-- NULL bei checked_in_by_employee_id bedeutet, dass noch kein Check-in stattgefunden hat.
