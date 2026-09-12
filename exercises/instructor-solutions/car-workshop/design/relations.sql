-- Lesbare Relationsnotation, kein ausführbares SQL
customers(id PK, customer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, phone NOT NULL, registered_on NOT NULL, active NOT NULL)
vehicles(id PK, owner_id FK NOT NULL -> customers.id, previous_owner_id FK NULL -> customers.id, licence_plate UK NOT NULL, vin UK NOT NULL, manufacturer NOT NULL, model NOT NULL, construction_year NOT NULL, mileage NOT NULL, UK(owner_id, licence_plate))
mechanics(id PK, personnel_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, specialization NOT NULL, hourly_rate NOT NULL, active NOT NULL)
parts(id PK, part_number UK NOT NULL, name NOT NULL, category NOT NULL, shelf_code NOT NULL, stock_quantity NOT NULL, reorder_level NOT NULL, list_price NOT NULL, UK(name, shelf_code))
work_orders(id PK, order_number UK NOT NULL, vehicle_id FK NOT NULL -> vehicles.id, opened_on NOT NULL, closed_on NULL, status NOT NULL, mileage_in NOT NULL, complaint NOT NULL)
work_order_mechanics(work_order_id PK FK NOT NULL -> work_orders.id, mechanic_id PK FK NOT NULL -> mechanics.id, hours_worked NOT NULL)
work_order_parts(work_order_id PK FK NOT NULL -> work_orders.id, line_number PK NOT NULL, part_id FK NOT NULL -> parts.id, quantity NOT NULL, unit_price NOT NULL, UK(work_order_id, part_id))
invoices(id PK, invoice_number UK NOT NULL, work_order_id FK UK NOT NULL -> work_orders.id, issued_on NOT NULL, due_on NOT NULL, paid_on NULL, net_amount NOT NULL, tax_amount NOT NULL, status NOT NULL)

-- Die Zuordnung von Auftrag und Mechaniker braucht keine künstliche ID.
-- NULL bei previous_owner_id bedeutet, dass kein früherer Halter gespeichert ist.
-- Der partielle Schlüssel UK(vehicle_id) WHERE closed_on IS NULL erlaubt nur einen offenen Auftrag je Fahrzeug.
