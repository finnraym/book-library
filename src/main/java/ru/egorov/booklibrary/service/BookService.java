package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Book;

public interface BookService {

    Book getById(Long id);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);

    boolean addGenreForBook(Long bookId, Long genreId);

    boolean addAuthorForBook(Long bookId, Long authorId);

}
