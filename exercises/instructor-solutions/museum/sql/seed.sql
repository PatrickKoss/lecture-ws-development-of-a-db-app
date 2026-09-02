INSERT INTO galleries (id, name, floor) VALUES
    (1, 'Stadtgeschichte', 0),
    (2, 'Kunst des 20. Jahrhunderts', 1),
    (3, 'Sonderausstellung', 2);

INSERT INTO artists (id, name, birth_year) VALUES
    (1, 'Marta Klein', 1888),
    (2, 'Josef Winter', 1921),
    (3, 'Aylin Demir', 1975);

INSERT INTO exhibits (id, inventory_code, title, insured_value, gallery_id) VALUES
    (1, 'EX-01', 'Marktplatz bei Nacht', 18000.00, 2),
    (2, 'EX-02', 'Zunfttruhe der Bäcker', 9500.00, 1),
    (3, 'EX-03', 'Modell des alten Bahnhofs', 4200.00, 1),
    (4, 'EX-04', 'Lichtinstallation Blau', 27000.00, NULL);

INSERT INTO exhibit_artists (exhibit_id, artist_id, role) VALUES
    (1, 1, 'CREATOR'),
    (2, 2, 'RESTORER'),
    (4, 3, 'CREATOR');

INSERT INTO loans (id, exhibit_id, borrower, starts_on, ends_on) VALUES
    (1, 1, 'Museum am Hafen', '2026-10-01', '2027-01-15'),
    (2, 3, 'Technikforum Nord', '2026-11-05', '2027-02-28');
