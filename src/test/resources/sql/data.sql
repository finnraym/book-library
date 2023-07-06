INSERT INTO develop.book (title, year_of_issue, number_of_pages)
VALUES ('Effective Java', 2019, 466),
       ('Spring in Action', 2022, 546),
       ('Cloud native Java', 2019, 624),
       ('Spring Microservices in Action', 2022, 491);

INSERT INTO develop.author (first_name, second_name, date_of_birth)
VALUES ('Joshua', 'Bloch', '1961-08-28'),
       ('Craig', 'Walls', '1964-04-13'),
       ('Josh', 'Long', '1972-09-24'),
       ('Kenny', 'Bastani', '1975-02-18'),
       ('John', 'Carnell', '1971-06-05'),
       ('Illary', 'Sanchez', '1978-11-10');

INSERT INTO develop.genre (name)
VALUES ('directory'),
       ('textbook'),
       ('tutorial'),
       ('encyclopedia'),
       ('monograph');

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