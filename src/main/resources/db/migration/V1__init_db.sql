CREATE SCHEMA IF NOT EXISTS develop;

CREATE TABLE IF NOT EXISTS develop.book (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL ,
    year_of_issue INTEGER NOT NULL ,
    number_of_pages INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS develop.author (
    id BIGSERIAL PRIMARY KEY ,
    first_name VARCHAR(255) NOT NULL ,
    second_name VARCHAR(255) NOT NULL ,
    date_of_birth DATE
);

CREATE TABLE IF NOT EXISTS develop.genre (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS develop.book_author (
    book_id BIGINT NOT NULL ,
    author_id BIGINT NOT NULL ,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_book_author FOREIGN KEY (book_id) REFERENCES develop.book (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_author_book_author FOREIGN KEY (author_id) REFERENCES develop.author (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS develop.book_genre (
    book_id BIGINT NOT NULL ,
    genre_id BIGINT NOT NULL ,
    PRIMARY KEY (book_id, genre_id),
    CONSTRAINT fk_book_book_genre FOREIGN KEY (book_id) REFERENCES develop.book (id) ON DELETE CASCADE ON UPDATE NO ACTION ,
    CONSTRAINT fk_genre_book_genre FOREIGN KEY (genre_id) REFERENCES develop.genre (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS develop.users (
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(80) NOT NULL,
    password VARCHAR(255) NOT NULL ,
    role VARCHAR(50) NOT NULL
);
