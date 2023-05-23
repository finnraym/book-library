package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Book;

public interface BookService {

    Book getById(Long id);
    void saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);
}
