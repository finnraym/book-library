INSERT INTO develop.book (id, name, year_of_issue, number_of_pages)
VALUES (1, 'Effective Java', 2019, 466),
       (2, 'Spring in Action', 2022, 546),
       (3, 'Cloud native Java', 2019, 624),
       (4, 'Spring Microservices in Action', 2022, 491);

INSERT INTO develop.author (id, first_name, second_name, date_of_birth)
VALUES (1, 'Joshua', 'Bloch', '1961-08-28'),
       (2, 'Craig', 'Walls', '1964-04-13'),
       (3, 'Josh', 'Long', '1972-09-24'),
       (4, 'Kenny', 'Bastani', '1975-02-18'),
       (5, 'John', 'Carnell', '1971-06-05'),
       (6, 'Illary', 'Sanchez', '1978-11-10');

INSERT INTO develop.genre (id, name)
VALUES (1, 'directory'),
       (2, 'textbook'),
       (3, 'tutorial'),
       (4, 'encyclopedia'),
       (5, 'monograph');

INSERT INTO develop.book_author(book_id, author_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (3, 4),
       (4, 5),
       (4, 6);

INSERT INTO develop.book_genre(book_id, genre_id)
VALUES (1, 1),
       (1, 5),
       (2, 2),
       (2, 3),
       (3, 3),
       (4, 3);

INSERT INTO develop.users(id, username, password, role)
VALUES (1, 'admin@mail.ru', '$2a$10$GuoRpDS/mC7S.TUtkClvS.bO8qNRz.2K3BqaBuYbO0bDOV3y7Lc2G', 'ROLE_ADMIN'),
       (2, 'usertest@mail.ru', '$2a$10$IaqwC3BvT9.0Lh7eBZ1B9uOmdR8srmCXwy8yYPCd.v3EX418a7XbS', 'ROLE_USER');