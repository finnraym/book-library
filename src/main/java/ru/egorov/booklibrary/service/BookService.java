package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.web.response.DataResponse;

public interface BookService {

    Book getById(Long id);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);
    DataResponse<Book> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    boolean addGenreForBook(Long bookId, Long genreId);
    boolean addAuthorForBook(Long bookId, Long authorId);

}
