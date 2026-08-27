PRAGMA foreign_keys = ON;

BEGIN TRANSACTION;

INSERT INTO customers (
    id,
    customer_number,
    first_name,
    last_name,
    email,
    phone,
    created_on,
    active
) VALUES
    (1, 'K-1001', 'Julia', 'Neumann', 'julia.neumann@example.org', '0151 24031001', '2026-01-03', 1),
    (2, 'K-1002', 'Cem', 'Aydin', 'cem.aydin@example.org', '0151 24031002', '2026-01-04', 1),
    (3, 'K-1003', 'Hannah', 'Berger', 'hannah.berger@example.org', '0151 24031003', '2026-01-07', 1),
    (4, 'K-1004', 'David', 'Özdemir', 'david.oezdemir@example.org', '0151 24031004', '2026-01-09', 1),
    (5, 'K-1005', 'Lea', 'Hoffmann', 'lea.hoffmann@example.org', '0151 24031005', '2026-01-12', 1),
    (6, 'K-1006', 'Murat', 'Yilmaz', 'murat.yilmaz@example.org', '0151 24031006', '2026-01-18', 1),
    (7, 'K-1007', 'Sofia', 'Rossi', 'sofia.rossi@example.org', '0151 24031007', '2026-01-23', 1),
    (8, 'K-1008', 'Noah', 'Schneider', 'noah.schneider@example.org', '0151 24031008', '2026-01-28', 1),
    (9, 'K-1009', 'Aylin', 'Kaya', 'aylin.kaya@example.org', '0151 24031009', '2026-02-01', 1),
    (10, 'K-1010', 'Jonas', 'Klein', 'jonas.klein@example.org', '0151 24031010', '2026-02-06', 1),
    (11, 'K-1011', 'Mina', 'Saleh', 'mina.saleh@example.org', '0151 24031011', '2026-02-12', 0),
    (12, 'K-1012', 'Felix', 'Braun', 'felix.braun@example.org', '0151 24031012', '2026-02-20', 1);

INSERT INTO addresses (
    id,
    customer_id,
    label,
    street,
    house_number,
    postal_code,
    city
) VALUES
    (1, 1, 'Zuhause', 'Wittener Straße', '110', '44803', 'Bochum'),
    (2, 1, 'Büro', 'Universitätsstraße', '150', '44801', 'Bochum'),
    (3, 2, 'Zuhause', 'Kortumstraße', '66', '44787', 'Bochum'),
    (4, 3, 'Zuhause', 'Castroper Straße', '207', '44791', 'Bochum'),
    (5, 4, 'Zuhause', 'Alleestraße', '58', '44793', 'Bochum'),
    (6, 5, 'Zuhause', 'Herner Straße', '95', '44791', 'Bochum'),
    (7, 6, 'Zuhause', 'Hattinger Straße', '218', '44795', 'Bochum'),
    (8, 7, 'Zuhause', 'Oskar-Hoffmann-Straße', '24', '44789', 'Bochum'),
    (9, 8, 'Zuhause', 'Wasserstraße', '105', '44803', 'Bochum'),
    (10, 9, 'Zuhause', 'Dorstener Straße', '184', '44809', 'Bochum'),
    (11, 10, 'Zuhause', 'Markstraße', '118', '44803', 'Bochum'),
    (12, 12, 'Zuhause', 'Brenscheder Straße', '62', '44799', 'Bochum');

INSERT INTO drivers (
    id,
    driver_number,
    first_name,
    last_name,
    phone,
    hired_on,
    active
) VALUES
    (1, 'F-01', 'Marco', 'Bianchi', '0176 33021001', '2026-01-02', 1),
    (2, 'F-02', 'Aylin', 'Demir', '0176 33021002', '2026-01-02', 1),
    (3, 'F-03', 'Luca', 'Romano', '0176 33021003', '2026-01-05', 1),
    (4, 'F-04', 'Sarah', 'Klein', '0176 33021004', '2026-01-05', 1),
    (5, 'F-05', 'Mehmet', 'Arslan', '0176 33021005', '2026-01-09', 1),
    (6, 'F-06', 'Nina', 'Scholz', '0176 33021006', '2026-01-12', 1),
    (7, 'F-07', 'Jonas', 'Weber', '0176 33021007', '2026-01-12', 1),
    (8, 'F-08', 'Elena', 'Conti', '0176 33021008', '2026-01-20', 1),
    (9, 'F-09', 'Tim', 'Franke', '0176 33021009', '2026-02-01', 0),
    (10, 'F-10', 'Derya', 'Çelik', '0176 33021010', '2026-02-08', 0);

INSERT INTO pizzas (
    id,
    pizza_number,
    name,
    category,
    oven_station,
    base_price,
    active
) VALUES
    (1, 'P-01', 'Margherita', 'Klassiker', 'OFEN-A', 9.50, 1),
    (2, 'P-02', 'Salami', 'Klassiker', 'OFEN-A', 9.50, 1),
    (3, 'P-03', 'Funghi', 'Vegetarisch', 'OFEN-B', 10.00, 1),
    (4, 'P-04', 'Diavola', 'Spezial', 'OFEN-C', 12.50, 1),
    (5, 'P-05', 'Hawaii', 'Klassiker', 'OFEN-A', 11.00, 1),
    (6, 'P-06', 'Tonno', 'Fisch', 'OFEN-D', 12.00, 1),
    (7, 'P-07', 'Vegetaria', 'Vegetarisch', 'OFEN-B', 10.50, 1),
    (8, 'P-08', 'Quattro Formaggi', 'Spezial', 'OFEN-C', 13.00, 1),
    (9, 'P-09', 'Prosciutto', 'Klassiker', 'OFEN-A', 11.50, 1),
    (10, 'P-10', 'Spinaci', 'Vegetarisch', 'OFEN-B', 12.00, 1),
    (11, 'P-11', 'Frutti di Mare', 'Fisch', 'OFEN-D', 14.50, 0),
    (12, 'P-12', 'Enzo', 'Spezial', 'OFEN-C', 13.50, 0);

INSERT INTO toppings (id, name, vegetarian, allergen) VALUES
    (1, 'Mozzarella', 1, 'Milch'),
    (2, 'Salami', 0, NULL),
    (3, 'Champignons', 1, NULL),
    (4, 'Peperoni', 1, NULL),
    (5, 'Kochschinken', 0, NULL),
    (6, 'Ananas', 1, NULL),
    (7, 'Thunfisch', 0, 'Fisch'),
    (8, 'Zwiebeln', 1, NULL),
    (9, 'Paprika', 1, NULL),
    (10, 'Spinat', 1, NULL),
    (11, 'Gorgonzola', 1, 'Milch'),
    (12, 'Meeresfrüchte', 0, 'Krebstiere');

INSERT INTO pizza_toppings (pizza_id, topping_id, extra_charge) VALUES
    (1, 1, 0.00),
    (2, 1, 0.00),
    (2, 2, 1.50),
    (3, 1, 0.00),
    (3, 3, 1.00),
    (4, 2, 1.50),
    (4, 4, 0.80),
    (5, 5, 1.50),
    (5, 6, 0.80),
    (6, 7, 1.80),
    (6, 8, 0.50),
    (7, 3, 1.00),
    (7, 9, 0.80),
    (8, 1, 0.00),
    (8, 11, 1.80),
    (9, 5, 1.50),
    (10, 10, 1.00),
    (11, 12, 2.50),
    (12, 2, 1.50),
    (12, 4, 0.80);

INSERT INTO orders (
    id,
    order_number,
    customer_id,
    delivery_address_id,
    driver_id,
    ordered_on,
    order_type,
    status,
    delivery_fee
) VALUES
    (1, 'B-26001', 1, 1, 1, '2026-01-08', 'DELIVERY', 'COMPLETED', 2.50),
    (2, 'B-26002', 2, NULL, NULL, '2026-01-10', 'PICKUP', 'COMPLETED', 0.00),
    (3, 'B-26003', 1, 2, 2, '2026-01-14', 'DELIVERY', 'COMPLETED', 2.50),
    (4, 'B-26004', 3, 4, 1, '2026-01-17', 'DELIVERY', 'COMPLETED', 2.50),
    (5, 'B-26005', 4, 5, 3, '2026-01-21', 'DELIVERY', 'COMPLETED', 2.50),
    (6, 'B-26006', 5, NULL, NULL, '2026-01-25', 'PICKUP', 'COMPLETED', 0.00),
    (7, 'B-26007', 6, 7, 4, '2026-02-02', 'DELIVERY', 'OUT_FOR_DELIVERY', 2.50),
    (8, 'B-26008', 2, 3, 5, '2026-02-05', 'DELIVERY', 'COMPLETED', 2.50),
    (9, 'B-26009', 7, NULL, NULL, '2026-02-09', 'PICKUP', 'READY', 0.00),
    (10, 'B-26010', 8, 9, NULL, '2026-02-11', 'DELIVERY', 'RECEIVED', 2.50),
    (11, 'B-26011', 3, 4, 6, '2026-02-14', 'DELIVERY', 'PREPARING', 2.50),
    (12, 'B-26012', 9, 10, 7, '2026-02-18', 'DELIVERY', 'COMPLETED', 2.50),
    (13, 'B-26013', 4, 5, 3, '2026-03-11', 'DELIVERY', 'OUT_FOR_DELIVERY', 2.50),
    (14, 'B-26014', 7, NULL, NULL, '2026-02-21', 'PICKUP', 'COMPLETED', 0.00),
    (15, 'B-26015', 9, 10, NULL, '2026-03-15', 'DELIVERY', 'RECEIVED', 2.50),
    (16, 'B-26016', 2, 3, 2, '2026-03-01', 'DELIVERY', 'PREPARING', 2.50);

INSERT INTO order_items (
    order_id,
    position_number,
    pizza_id,
    quantity,
    unit_price
) VALUES
    (1, 1, 1, 2, 9.50),
    (1, 2, 4, 1, 12.50),
    (2, 1, 2, 1, 8.50),
    (3, 1, 3, 1, 10.00),
    (3, 2, 9, 1, 11.50),
    (4, 1, 4, 2, 12.50),
    (5, 1, 5, 1, 11.00),
    (6, 1, 6, 2, 12.00),
    (7, 1, 1, 1, 9.50),
    (8, 1, 8, 1, 13.00),
    (8, 2, 12, 1, 12.50),
    (9, 1, 7, 1, 10.50),
    (10, 1, 10, 2, 12.00),
    (11, 1, 11, 1, 9.00),
    (12, 1, 2, 3, 8.50),
    (13, 1, 3, 2, 10.00),
    (13, 2, 5, 1, 11.00),
    (14, 1, 1, 1, 9.50),
    (15, 1, 6, 1, 12.00),
    (16, 1, 4, 1, 12.50);

COMMIT;
