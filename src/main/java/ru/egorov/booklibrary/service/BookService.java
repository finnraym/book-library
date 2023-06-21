package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.List;

public interface BookService {

    Book getById(Long id);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(Long id);
    DataResponse<Book> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);

    List<Book> getAllByName(String name);
    List<Book> getAllByYearOfIssue(Integer year, String cmpr, String sort);

    List<Book> getAllByNumberOfPages(Integer year, String cmpr, String sort);
    boolean addGenreForBook(Long bookId, Long genreId);
    boolean addAuthorForBook(Long bookId, Long authorId);

}
