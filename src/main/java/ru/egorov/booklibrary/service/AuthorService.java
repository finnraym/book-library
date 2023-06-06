package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Author;

public interface AuthorService {

    Author getById(Long id);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);

}
