-- Lesbare Relationsnotation, kein ausführbares SQL
customers(id PK, customer_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, phone UK NOT NULL, created_on NOT NULL, active NOT NULL)
addresses(id PK, customer_id FK NOT NULL -> customers.id, label NOT NULL, street NOT NULL, house_number NOT NULL, postal_code NOT NULL, city NOT NULL, UK(id, customer_id), UK(customer_id, label))
drivers(id PK, driver_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, phone UK NOT NULL, hired_on NOT NULL, active NOT NULL)
pizzas(id PK, pizza_number UK NOT NULL, name NOT NULL, category NOT NULL, oven_station NOT NULL, base_price NOT NULL, active NOT NULL, UK(name, oven_station))
toppings(id PK, name UK NOT NULL, vegetarian NOT NULL, allergen NULL)
pizza_toppings(pizza_id PK FK NOT NULL -> pizzas.id, topping_id PK FK NOT NULL -> toppings.id, extra_charge NOT NULL)
orders(id PK, order_number UK NOT NULL, customer_id FK NOT NULL -> customers.id, delivery_address_id FK NULL, driver_id FK NULL -> drivers.id, ordered_on NOT NULL, order_type NOT NULL, status NOT NULL, delivery_fee NOT NULL, FK(delivery_address_id, customer_id) -> addresses(id, customer_id))
order_items(order_id PK FK NOT NULL -> orders.id, position_number PK NOT NULL, pizza_id FK NOT NULL -> pizzas.id, quantity NOT NULL, unit_price NOT NULL, UK(order_id, pizza_id))

-- Pizza und Belag identifizieren eine Rezeptzuordnung vollständig.
-- NULL bei delivery_address_id bedeutet eine Abholbestellung.
