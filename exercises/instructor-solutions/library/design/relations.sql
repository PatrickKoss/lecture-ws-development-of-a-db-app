-- Lesbare Relationsnotation, kein ausführbares SQL
members(id PK, membership_number UK NOT NULL, first_name NOT NULL, last_name NOT NULL, email UK NOT NULL, joined_on NOT NULL, active NOT NULL)
authors(id PK, first_name NOT NULL, last_name NOT NULL, birth_year NULL, UK(first_name, last_name, birth_year))
books(id PK, isbn UK NOT NULL, title NOT NULL, publication_year NOT NULL, subject_area NOT NULL, shelf_code NOT NULL, UK(title, shelf_code))
book_authors(book_id PK FK NOT NULL -> books.id, author_id PK FK NOT NULL -> authors.id, author_order NOT NULL, UK(book_id, author_order))
copies(book_id PK FK NOT NULL -> books.id, copy_number PK NOT NULL, barcode UK NOT NULL, branch_code NOT NULL, acquired_on NOT NULL, condition NOT NULL)
reservations(id PK, member_id FK NOT NULL -> members.id, book_id FK NOT NULL -> books.id, reserved_on NOT NULL, expires_on NOT NULL, status NOT NULL, UK(member_id, book_id, reserved_on), UK(id, member_id, book_id))
loans(id PK, member_id FK NOT NULL -> members.id, book_id FK NOT NULL, copy_number FK NOT NULL, reservation_id FK UK NULL, loaned_on NOT NULL, due_on NOT NULL, returned_on NULL, FK(book_id, copy_number) -> copies(book_id, copy_number), FK(reservation_id, member_id, book_id) -> reservations(id, member_id, book_id))

-- Buch und Exemplarnummer identifizieren ein physisches Exemplar.
-- NULL bei reservation_id bedeutet, dass die Ausleihe ohne Vormerkung entstand.
-- Der partielle Schlüssel UK(book_id, copy_number) WHERE returned_on IS NULL erlaubt nur eine offene Ausleihe je Exemplar.
