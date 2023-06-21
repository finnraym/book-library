package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;

import java.util.List;

public interface BookService {

    Book getById(Long id);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);

    boolean addGenreForBook(Long bookId, Long genreId);

    boolean addAuthorForBook(Long bookId, Long authorId);

}
