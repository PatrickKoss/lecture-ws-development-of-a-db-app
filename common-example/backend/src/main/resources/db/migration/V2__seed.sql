INSERT INTO departments (id, name, code) VALUES
    (1, 'Informatik', 'INF'),
    (2, 'Mathematik', 'MAT'),
    (3, 'Wirtschaftswissenschaften', 'WI');

INSERT INTO lecturers (id, first_name, last_name, email, department_id) VALUES
    (1, 'Anna', 'Weber', 'anna.weber@hochschule.example', 1),
    (2, 'Mehmet', 'Yilmaz', 'mehmet.yilmaz@hochschule.example', 1),
    (3, 'Clara', 'Neumann', 'clara.neumann@hochschule.example', 2),
    (4, 'Tobias', 'Richter', 'tobias.richter@hochschule.example', 3),
    (5, 'Jana', 'Vogel', 'jana.vogel@hochschule.example', 1);

INSERT INTO students (
    id,
    first_name,
    last_name,
    email,
    student_number,
    enrollment_date
) VALUES
    (1, 'Lena', 'Hoffmann', 'lena.hoffmann@stud.example', 'M2023001', '2023-10-01'),
    (2, 'Jonas', 'Becker', 'jonas.becker@stud.example', 'M2023002', '2023-10-01'),
    (3, 'Aylin', 'Kaya', 'aylin.kaya@stud.example', 'M2023003', '2023-10-01'),
    (4, 'Paul', 'Schneider', 'paul.schneider@stud.example', 'M2023004', '2023-10-01'),
    (5, 'Mia', 'Wagner', 'mia.wagner@stud.example', 'M2023005', '2023-10-01'),
    (6, 'Emir', 'Öztürk', 'emir.oeztuerk@stud.example', 'M2024001', '2024-04-01'),
    (7, 'Sophie', 'Klein', 'sophie.klein@stud.example', 'M2024002', '2024-04-01'),
    (8, 'Noah', 'Schulz', 'noah.schulz@stud.example', 'M2024003', '2024-04-01'),
    (9, 'Leonie', 'Braun', 'leonie.braun@stud.example', 'M2024004', '2024-04-01'),
    (10, 'David', 'Wolf', 'david.wolf@stud.example', 'M2024005', '2024-04-01'),
    (11, 'Elif', 'Aydin', 'elif.aydin@stud.example', 'M2024006', '2024-10-01'),
    (12, 'Finn', 'Krüger', 'finn.krueger@stud.example', 'M2024007', '2024-10-01'),
    (13, 'Amira', 'Hassan', 'amira.hassan@stud.example', 'M2024008', '2024-10-01'),
    (14, 'Luis', 'Zimmermann', 'luis.zimmermann@stud.example', 'M2024009', '2024-10-01'),
    (15, 'Nele', 'Hartmann', 'nele.hartmann@stud.example', 'M2024010', '2024-10-01'),
    (16, 'Marlon', 'König', 'marlon.koenig@stud.example', 'M2025001', '2025-04-01'),
    (17, 'Zeynep', 'Demir', 'zeynep.demir@stud.example', 'M2025002', '2025-04-01'),
    (18, 'Felix', 'Lehmann', 'felix.lehmann@stud.example', 'M2025003', '2025-04-01'),
    (19, 'Hannah', 'Maier', 'hannah.maier@stud.example', 'M2025004', '2025-10-01'),
    (20, 'Samir', 'Saleh', 'samir.saleh@stud.example', 'M2025005', '2025-10-01');

INSERT INTO courses (id, course_code, title, credits, lecturer_id) VALUES
    (1, 'INF-201', 'Datenbanken', 6, 1),
    (2, 'INF-202', 'Programmierung II', 6, 2),
    (3, 'INF-230', 'Webentwicklung', 5, 5),
    (4, 'MAT-110', 'Diskrete Mathematik', 5, 3),
    (5, 'MAT-210', 'Statistik', 5, 3),
    (6, 'WI-101', 'Grundlagen der Betriebswirtschaft', 5, 4),
    (7, 'INF-250', 'Software Engineering', 6, 1);

INSERT INTO enrollments (
    id,
    student_id,
    course_id,
    grade,
    enrolled_on
) VALUES
    (1, 1, 1, 1.7, '2024-04-08'),
    (2, 1, 2, 2.0, '2024-10-07'),
    (3, 1, 4, 1.3, '2025-04-07'),
    (4, 2, 1, 2.3, '2024-04-08'),
    (5, 2, 3, 1.7, '2025-04-07'),
    (6, 2, 7, 2.0, '2025-10-06'),
    (7, 3, 1, 1.3, '2024-04-08'),
    (8, 3, 2, 1.7, '2024-10-07'),
    (9, 3, 5, 2.0, '2025-04-07'),
    (10, 4, 2, 2.7, '2024-10-07'),
    (11, 4, 3, 2.3, '2025-04-07'),
    (12, 5, 1, 1.0, '2024-04-08'),
    (13, 5, 4, 1.7, '2024-10-07'),
    (14, 5, 5, 1.3, '2025-04-07'),
    (15, 6, 3, 2.0, '2024-10-07'),
    (16, 6, 6, 2.3, '2025-04-07'),
    (17, 7, 1, 3.0, '2024-10-07'),
    (18, 7, 7, 2.7, '2025-04-07'),
    (19, 8, 2, 1.3, '2024-10-07'),
    (20, 8, 3, 1.0, '2025-04-07'),
    (21, 8, 4, 1.7, '2025-04-07'),
    (22, 9, 5, 2.7, '2024-10-07'),
    (23, 9, 6, 2.0, '2025-04-07'),
    (24, 10, 1, 2.0, '2024-10-07'),
    (25, 10, 2, 2.3, '2024-10-07'),
    (26, 10, 7, 1.7, '2025-04-07'),
    (27, 11, 3, 3.3, '2024-10-07'),
    (28, 11, 6, 2.7, '2025-04-07'),
    (29, 12, 4, 1.0, '2024-10-07'),
    (30, 12, 5, 1.3, '2025-04-07'),
    (31, 13, 1, NULL, '2025-04-07'),
    (32, 13, 3, NULL, '2025-04-07'),
    (33, 14, 2, 3.0, '2025-04-07'),
    (34, 14, 7, 2.3, '2025-04-07'),
    (35, 15, 4, 2.0, '2025-04-07'),
    (36, 15, 6, 1.7, '2025-04-07'),
    (37, 16, 1, 2.7, '2025-04-07'),
    (38, 16, 5, 3.0, '2025-04-07'),
    (39, 17, 3, 1.3, '2025-10-06'),
    (40, 18, 7, NULL, '2025-10-06');

