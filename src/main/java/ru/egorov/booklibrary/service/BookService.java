package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;

public interface BookService {

    Book getById(Long id);
    void saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);

    boolean addGenreForBook(Long bookId, Genre ... genre);

    boolean addAuthorForBook(Long bookId, Author ... author);

}
