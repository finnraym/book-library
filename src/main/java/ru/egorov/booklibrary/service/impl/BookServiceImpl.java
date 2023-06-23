package ru.egorov.booklibrary.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.BookRepository;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.service.BookService;
import ru.egorov.booklibrary.service.GenreService;
import ru.egorov.booklibrary.utils.AppUtils;
import ru.egorov.booklibrary.utils.consts.StringConstants;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    private static final String YEAR_OF_ISSUE = "yearOfIssue";
    private static final String NUMBER_OF_PAGES = "numberOfPages";

    @Transactional(readOnly = true)
    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Book with id " + id + " not found!"));
    }

    @Transactional
    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        Book updatedBook = getById(book.getId());

        updatedBook.setName(book.getName());
        updatedBook.setYearOfIssue(book.getYearOfIssue());
        updatedBook.setNumberOfPages(book.getNumberOfPages());

        return updatedBook;
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Book> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = AppUtils.getSort(sortBy, sortDirection);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> pagesOfBook = bookRepository.findAll(pageable);

        return DataResponse.<Book>builder()
                .data(pagesOfBook.getContent())
                .last(pagesOfBook.isLast())
                .pageNo(pagesOfBook.getNumber())
                .pageSize(pagesOfBook.getSize())
                .totalPages(pagesOfBook.getTotalPages())
                .totalElements(pagesOfBook.getTotalElements())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByName(String name) {
        return bookRepository.findAllByNameContainingIgnoreCase(name, Sort.by("name"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByYearOfIssue(@NotNull Integer year, String cmpr, String sortDir) {
        Sort sort = AppUtils.getSort(YEAR_OF_ISSUE, sortDir);

        List<Book> result;
        if (cmpr.equalsIgnoreCase(StringConstants.BEFORE)) {
            result = bookRepository.findAllByYearOfIssueLessThan(year, sort);
        } else if (cmpr.equalsIgnoreCase(StringConstants.AFTER)) {
            result = bookRepository.findAllByYearOfIssueGreaterThan(year, sort);
        } else {
            result = bookRepository.findAllByYearOfIssueEquals(year, sort);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByNumberOfPages(Integer year, String cmpr, String sortDir) {
        Sort sort = AppUtils.getSort(NUMBER_OF_PAGES, sortDir);

        List<Book> result;
        if (cmpr.equalsIgnoreCase(StringConstants.LESS)) {
            result = bookRepository.findAllByNumberOfPagesLessThan(year, sort);
        } else if (cmpr.equalsIgnoreCase(StringConstants.GREATER)) {
            result = bookRepository.findAllByNumberOfPagesGreaterThan(year, sort);
        } else {
            result = bookRepository.findAllByNumberOfPagesEquals(year, sort);
        }

        return result;
    }

    @Transactional
    @Override
    public boolean addGenreForBook(Long bookId, Long genreId) {
        Book updatedBook = getById(bookId);

        Set<Genre> bookGenres = updatedBook.getGenres();

        Genre genre = genreService.getById(genreId);
        if (!bookGenres.contains(genre)) {
            return bookGenres.add(genre);
        } else {
            throw new DataAlreadyExistsException("Genre with id  " + genreId + " is already attached to book with id " + bookId);
        }
    }

    @Transactional
    @Override
    public boolean addAuthorForBook(Long bookId, Long authorId) {
        Book updatedBook = getById(bookId);

        Set<Author> bookAuthors = updatedBook.getAuthors();

        Author author = authorService.getById(authorId);
        if (!bookAuthors.contains(author)) {
            return bookAuthors.add(author);
        } else {
            throw new DataAlreadyExistsException("Author with id  " + authorId + " is already attached to book with id " + bookId);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByAuthorId(Long id) {
        return bookRepository.findAllByAuthorId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByGenreId(Long id) {
        return bookRepository.findAllByGenreId(id);
    }

}
