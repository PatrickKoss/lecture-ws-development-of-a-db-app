INSERT INTO restaurants (
    id,
    partner_number,
    name,
    street,
    postal_code,
    city,
    commission_rate,
    active
) VALUES
    (1, 'R-101', 'Pott Pizza', 'Rüttenscheider Straße 118', '45131', 'Essen', 18.0, 1),
    (2, 'R-102', 'Curry am Markt', 'Bongardstraße 12', '44787', 'Bochum', 16.0, 1),
    (3, 'R-103', 'Levante Küche', 'Kaiserstraße 44', '44135', 'Dortmund', 20.0, 1),
    (4, 'R-104', 'Grünzeug', 'Viehofer Platz 7', '45127', 'Essen', 15.0, 1),
    (5, 'R-105', 'Mamas Pasta', 'Dellplatz 3', '47051', 'Duisburg', 19.0, 1),
    (6, 'R-106', 'Hafen Burger', 'Philosophenweg 21', '47051', 'Duisburg', 17.0, 1),
    (7, 'R-107', 'Sushibar Nord', 'Cranger Straße 56', '45891', 'Gelsenkirchen', 21.0, 1),
    (8, 'R-108', 'Zeche Vegan', 'Kortumstraße 88', '44787', 'Bochum', 14.0, 1),
    (9, 'R-109', 'Tandoori West', 'Marktstraße 31', '46045', 'Oberhausen', 20.0, 1),
    (10, 'R-110', 'Kanal Falafel', 'Münsterstraße 15', '44534', 'Lünen', 16.5, 0);

INSERT INTO dishes (
    id,
    restaurant_id,
    name,
    category,
    vat_rate,
    current_price,
    active
) VALUES
    (1, 1, 'Margherita', 'PIZZA', 7, 9.40, 1),
    (2, 1, 'Tiramisu im Glas', 'DESSERT', 7, 5.50, 1),
    (3, 2, 'Currywurst Ruhrpott', 'HAUPTGERICHT', 7, 9.90, 1),
    (4, 3, 'Falafel-Teller', 'HAUPTGERICHT', 7, 12.90, 1),
    (5, 3, 'Baklava', 'DESSERT', 7, 5.10, 1),
    (6, 4, 'Kumpir Gemüse', 'HAUPTGERICHT', 7, 11.40, 1),
    (7, 5, 'Tagliatelle Pilze', 'HAUPTGERICHT', 7, 14.30, 1),
    (8, 5, 'Panna Cotta', 'DESSERT', 7, 5.90, 1),
    (9, 6, 'Schimanski Burger', 'HAUPTGERICHT', 7, 15.20, 1),
    (10, 7, 'Lachs Bento', 'HAUPTGERICHT', 7, 17.50, 1),
    (11, 8, 'Seitan-Schnitzel', 'HAUPTGERICHT', 7, 15.80, 1),
    (12, 9, 'Chicken Tikka', 'HAUPTGERICHT', 7, 15.40, 1),
    (13, 10, 'Dönerteller am Kanal', 'HAUPTGERICHT', 7, 12.50, 0),
    (14, 2, 'Pommes Schranke', 'VORSPEISE', 7, 4.50, 0);

INSERT INTO customers (
    id,
    customer_number,
    first_name,
    last_name,
    email,
    registered_on,
    active
) VALUES
    (1, 'K-1001', 'Lena', 'Vogt', 'lena.vogt@example.org', '2026-01-02', 1),
    (2, 'K-1002', 'Jonas', 'Richter', 'jonas.richter@example.org', '2026-01-03', 1),
    (3, 'K-1003', 'Aylin', 'Demir', 'aylin.demir@example.org', '2026-01-04', 1),
    (4, 'K-1004', 'David', 'Özkan', 'david.oezkan@example.org', '2026-01-05', 1),
    (5, 'K-1005', 'Marie', 'Hoffmann', 'marie.hoffmann@example.org', '2026-01-06', 1),
    (6, 'K-1006', 'Cem', 'Yildiz', 'cem.yildiz@example.org', '2026-01-07', 1),
    (7, 'K-1007', 'Sophie', 'Nowak', 'sophie.nowak@example.org', '2026-01-09', 1),
    (8, 'K-1008', 'Noah', 'Peters', 'noah.peters@example.org', '2026-01-11', 1),
    (9, 'K-1009', 'Mina', 'Saleh', 'mina.saleh@example.org', '2026-01-13', 1),
    (10, 'K-1010', 'Felix', 'Braun', 'felix.braun@example.org', '2026-01-15', 1),
    (11, 'K-1011', 'Hannah', 'Krall', 'hannah.krall@example.org', '2026-02-01', 1),
    (12, 'K-1012', 'Murat', 'Aslan', 'murat.aslan@example.org', '2026-02-12', 0);

INSERT INTO couriers (
    id,
    courier_number,
    first_name,
    last_name,
    phone,
    vehicle_type,
    active
) VALUES
    (1, 'C-201', 'Mehmet', 'Kaya', '+491701100201', 'E_BIKE', 1),
    (2, 'C-202', 'Sarah', 'Krüger', '+491701100202', 'FAHRRAD', 1),
    (3, 'C-203', 'Tobias', 'Schulte', '+491701100203', 'ROLLER', 1),
    (4, 'C-204', 'Nina', 'Becker', '+491701100204', 'AUTO', 1),
    (5, 'C-205', 'Luca', 'Romano', '+491701100205', 'E_BIKE', 1),
    (6, 'C-206', 'Fatma', 'Acar', '+491701100206', 'FAHRRAD', 1),
    (7, 'C-207', 'Paul', 'Neumann', '+491701100207', 'ROLLER', 1),
    (8, 'C-208', 'Zeynep', 'Arslan', '+491701100208', 'E_BIKE', 1),
    (9, 'C-209', 'Jan', 'Kowalski', '+491701100209', 'AUTO', 1),
    (10, 'C-210', 'Leonie', 'Fuchs', '+491701100210', 'FAHRRAD', 0);

INSERT INTO orders (
    id,
    order_number,
    customer_id,
    restaurant_id,
    courier_id,
    ordered_at,
    order_type,
    status,
    picked_up_at,
    delivered_at
) VALUES
    (1, 'O-26001', 1, 1, 1, '2026-01-08 18:12:00', 'DELIVERY', 'DELIVERED', '2026-01-08 18:42:00', '2026-01-08 19:03:00'),
    (2, 'O-26002', 2, 2, NULL, '2026-01-10 12:05:00', 'PICKUP', 'PICKED_UP', NULL, NULL),
    (3, 'O-26003', 3, 3, 2, '2026-01-14 19:20:00', 'DELIVERY', 'DELIVERED', '2026-01-14 19:51:00', '2026-01-14 20:15:00'),
    (4, 'O-26004', 1, 4, 3, '2026-01-17 17:33:00', 'DELIVERY', 'PREPARING', NULL, NULL),
    (5, 'O-26005', 4, 5, 4, '2026-01-21 20:02:00', 'DELIVERY', 'DELIVERED', '2026-01-21 20:37:00', '2026-01-21 21:01:00'),
    (6, 'O-26006', 5, 6, NULL, '2026-01-25 13:14:00', 'PICKUP', 'PICKED_UP', NULL, NULL),
    (7, 'O-26007', 6, 7, 5, '2026-02-02 18:46:00', 'DELIVERY', 'DELIVERED', '2026-02-02 19:18:00', '2026-02-02 19:44:00'),
    (8, 'O-26008', 2, 1, 1, '2026-02-05 19:07:00', 'DELIVERY', 'DELIVERED', '2026-02-05 19:39:00', '2026-02-05 20:02:00'),
    (9, 'O-26009', 7, 8, 6, '2026-02-09 18:25:00', 'DELIVERY', 'PICKED_UP', '2026-02-09 18:59:00', NULL),
    (10, 'O-26010', 8, 9, 7, '2026-02-11 20:11:00', 'DELIVERY', 'DELIVERED', '2026-02-11 20:43:00', '2026-02-11 21:16:00'),
    (11, 'O-26011', 9, 10, NULL, '2026-02-14 17:49:00', 'DELIVERY', 'NEW', NULL, NULL),
    (12, 'O-26012', 10, 3, NULL, '2026-02-18 12:27:00', 'DELIVERY', 'CANCELLED', NULL, NULL),
    (13, 'O-26013', 3, 4, 8, '2026-02-22 18:04:00', 'DELIVERY', 'DELIVERED', '2026-02-22 18:31:00', '2026-02-22 18:54:00'),
    (14, 'O-26014', 11, 6, NULL, '2026-03-05 19:36:00', 'DELIVERY', 'ACCEPTED', NULL, NULL),
    (15, 'O-26015', 4, 2, 9, '2026-03-11 20:08:00', 'DELIVERY', 'DELIVERED', '2026-03-11 20:40:00', '2026-03-11 21:05:00');

INSERT INTO order_items (
    order_id,
    position_number,
    dish_id,
    quantity,
    unit_price
) VALUES
    (1, 1, 1, 2, 8.90),
    (1, 2, 2, 1, 5.20),
    (2, 1, 3, 1, 9.50),
    (3, 1, 4, 2, 12.40),
    (3, 2, 5, 2, 4.80),
    (4, 1, 6, 1, 10.90),
    (5, 1, 7, 1, 13.80),
    (5, 2, 8, 2, 5.60),
    (6, 1, 9, 2, 14.50),
    (7, 1, 10, 1, 16.90),
    (8, 1, 1, 1, 8.90),
    (8, 2, 2, 2, 5.20),
    (9, 1, 11, 1, 15.20),
    (10, 1, 12, 2, 14.90),
    (11, 1, 13, 1, 11.90),
    (12, 1, 4, 1, 12.40),
    (13, 1, 6, 3, 10.90),
    (14, 1, 9, 1, 14.50),
    (15, 1, 3, 2, 9.50),
    (15, 2, 14, 2, 4.20);

INSERT INTO reviews (
    id,
    order_id,
    rating,
    comment,
    reviewed_on
) VALUES
    (1, 1, 5, 'Pizza heiß, Lieferung pünktlich.', '2026-01-09'),
    (2, 2, 4, 'Abholung war nach zehn Minuten bereit.', '2026-01-10'),
    (3, 3, 4, 'Gute Portion, Baklava etwas süß.', '2026-01-15'),
    (4, 5, 3, 'Pasta gut, Lieferung dauerte lange.', '2026-01-22'),
    (5, 6, 5, 'Burger wie bestellt.', '2026-01-25'),
    (6, 7, 5, 'Bento kam sauber verpackt an.', '2026-02-03'),
    (7, 8, 4, NULL, '2026-02-06'),
    (8, 10, 4, 'Noch warm und gut gewürzt.', '2026-02-12'),
    (9, 13, 5, 'Schnelle Lieferung.', '2026-02-23'),
    (10, 15, 4, 'Currywurst und Pommes waren knusprig.', '2026-03-12');


