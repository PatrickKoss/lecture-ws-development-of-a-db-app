INSERT INTO members (
    id,
    membership_number,
    first_name,
    last_name,
    email,
    joined_on,
    active
) VALUES
    (1, 'M-1001', 'Julia', 'Neumann', 'julia.neumann@example.org', '2022-03-14', 1),
    (2, 'M-1002', 'Cem', 'Aydin', 'cem.aydin@example.org', '2021-11-08', 1),
    (3, 'M-1003', 'Hannah', 'Berger', 'hannah.berger@example.org', '2023-01-21', 1),
    (4, 'M-1004', 'David', 'Özdemir', 'david.oezdemir@example.org', '2020-06-17', 1),
    (5, 'M-1005', 'Lea', 'Hoffmann', 'lea.hoffmann@example.org', '2024-02-03', 1),
    (6, 'M-1006', 'Murat', 'Yilmaz', 'murat.yilmaz@example.org', '2022-09-29', 1),
    (7, 'M-1007', 'Sofia', 'Rossi', 'sofia.rossi@example.org', '2025-05-11', 1),
    (8, 'M-1008', 'Noah', 'Schneider', 'noah.schneider@example.org', '2023-07-01', 1),
    (9, 'M-1009', 'Aylin', 'Kaya', 'aylin.kaya@example.org', '2024-10-19', 1),
    (10, 'M-1010', 'Jonas', 'Klein', 'jonas.klein@example.org', '2025-08-30', 1),
    (11, 'M-1011', 'Mina', 'Saleh', 'mina.saleh@example.org', '2019-04-12', 0),
    (12, 'M-1012', 'Felix', 'Braun', 'felix.braun@example.org', '2020-12-05', 0);

INSERT INTO authors (id, first_name, last_name, birth_year) VALUES
    (1, 'Wolfgang', 'Herrndorf', 1965),
    (2, 'Michael', 'Ende', 1929),
    (3, 'Saša', 'Stanišić', 1978),
    (4, 'Frank', 'Schätzing', 1957),
    (5, 'Marc-Uwe', 'Kling', 1982),
    (6, 'Daniel', 'Kehlmann', 1975),
    (7, 'Juli', 'Zeh', 1974),
    (8, 'Alfons', 'Kemper', 1958),
    (9, 'André', 'Eickler', 1961),
    (10, 'Patrick', 'Süskind', 1949);

INSERT INTO books (
    id,
    isbn,
    title,
    publication_year,
    subject_area,
    shelf_code
) VALUES
    (1, '9783446279899', 'Tschick', 2010, 'Roman', 'R-12'),
    (2, '9783442761619', 'Momo', 1973, 'Kinderbuch', 'K-04'),
    (3, '9783462050815', 'Herkunft', 2019, 'Roman', 'R-12'),
    (4, '9783404171211', 'Der Schwarm', 2004, 'Thriller', 'T-07'),
    (5, '9783550081648', 'QualityLand', 2017, 'Satire', 'S-03'),
    (6, '9783446193133', 'Die Vermessung der Welt', 2005, 'Roman', 'R-12'),
    (7, '9783423214185', 'Corpus Delicti', 2009, 'Roman', 'R-12'),
    (8, '9783446269234', 'Datenbanksysteme', 2015, 'Fachbuch', 'F-09'),
    (9, '9783257230009', 'Das Parfum', 1985, 'Roman', 'R-12'),
    (10, '9783522202802', 'Die unendliche Geschichte', 1979, 'Kinderbuch', 'K-04'),
    (11, '9783499267709', 'Unterleuten', 2016, 'Roman', 'R-12'),
    (12, '9783442719180', 'Die Känguru-Chroniken', 2009, 'Satire', 'S-03');

INSERT INTO book_authors (book_id, author_id, author_order) VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1),
    (4, 4, 1),
    (5, 5, 1),
    (6, 6, 1),
    (7, 7, 1),
    (8, 8, 1),
    (8, 9, 2),
    (9, 10, 1),
    (10, 2, 1),
    (11, 7, 1),
    (12, 5, 1);

INSERT INTO copies (
    book_id,
    copy_number,
    barcode,
    branch_code,
    acquired_on,
    condition
) VALUES
    (1, 1, 'N-000101', 'NORD', '2021-04-12', 'GUT'),
    (1, 2, 'S-000102', 'SUED', '2023-09-06', 'GUT'),
    (2, 1, 'S-000201', 'SUED', '2019-02-18', 'BESCHAEDIGT'),
    (2, 2, 'N-000202', 'NORD', '2024-11-02', 'NEU'),
    (3, 1, 'S-000301', 'SUED', '2020-01-10', 'GUT'),
    (4, 1, 'N-000401', 'NORD', '2018-07-27', 'GUT'),
    (5, 1, 'N-000501', 'NORD', '2022-05-15', 'GUT'),
    (5, 2, 'S-000502', 'SUED', '2025-08-20', 'NEU'),
    (6, 1, 'S-000601', 'SUED', '2017-03-09', 'GUT'),
    (7, 1, 'N-000701', 'NORD', '2021-10-23', 'GUT'),
    (8, 1, 'N-000801', 'NORD', '2020-09-14', 'GUT'),
    (9, 1, 'S-000901', 'SUED', '2016-06-30', 'BESCHAEDIGT'),
    (10, 1, 'N-001001', 'NORD', '2023-12-01', 'NEU'),
    (11, 1, 'S-001101', 'SUED', '2022-08-08', 'GUT'),
    (12, 1, 'S-001201', 'SUED', '2024-04-17', 'GUT');

INSERT INTO reservations (
    id,
    member_id,
    book_id,
    reserved_on,
    expires_on,
    status
) VALUES
    (1, 1, 1, '2025-12-20', '2026-01-10', 'FULFILLED'),
    (2, 1, 3, '2026-01-03', '2026-01-17', 'FULFILLED'),
    (3, 4, 5, '2026-01-11', '2026-01-25', 'FULFILLED'),
    (4, 2, 8, '2026-01-28', '2026-02-11', 'FULFILLED'),
    (5, 3, 10, '2026-02-01', '2026-02-15', 'OPEN'),
    (6, 4, 12, '2026-02-03', '2026-03-18', 'FULFILLED'),
    (7, 6, 7, '2026-02-04', '2026-02-18', 'CANCELLED'),
    (8, 7, 2, '2026-02-07', '2026-02-24', 'FULFILLED'),
    (9, 8, 4, '2026-02-10', '2026-02-24', 'OPEN'),
    (10, 9, 6, '2026-02-12', '2026-02-26', 'FULFILLED');

INSERT INTO loans (
    id,
    member_id,
    book_id,
    copy_number,
    reservation_id,
    loaned_on,
    due_on,
    returned_on
) VALUES
    (1, 1, 1, 1, 1, '2026-01-08', '2026-02-05', '2026-01-29'),
    (2, 2, 2, 1, NULL, '2026-01-10', '2026-02-07', '2026-02-09'),
    (3, 1, 3, 1, 2, '2026-01-14', '2026-02-11', NULL),
    (4, 3, 4, 1, NULL, '2026-01-17', '2026-02-14', '2026-02-12'),
    (5, 4, 5, 1, 3, '2026-01-21', '2026-02-18', NULL),
    (6, 5, 6, 1, NULL, '2026-01-25', '2026-02-22', '2026-02-20'),
    (7, 6, 7, 1, NULL, '2026-02-02', '2026-03-02', NULL),
    (8, 2, 8, 1, 4, '2026-02-05', '2026-03-05', NULL),
    (9, 7, 9, 1, NULL, '2026-02-09', '2026-03-09', NULL),
    (10, 8, 10, 1, NULL, '2026-02-11', '2026-03-11', '2026-03-01'),
    (11, 3, 11, 1, NULL, '2026-02-14', '2026-03-14', NULL),
    (12, 9, 12, 1, NULL, '2026-02-18', '2026-03-18', '2026-03-10'),
    (13, 4, 12, 1, 6, '2026-03-11', '2026-04-08', NULL),
    (14, 7, 2, 2, 8, '2026-02-21', '2026-03-21', NULL),
    (15, 9, 6, 1, 10, '2026-02-22', '2026-03-22', NULL),
    (16, 2, 1, 2, NULL, '2026-03-01', '2026-03-29', NULL);


