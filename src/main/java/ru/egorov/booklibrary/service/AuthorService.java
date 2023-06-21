package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.web.response.DataResponse;

public interface AuthorService {

    Author getById(Long id);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);
    DataResponse<Author> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);

}
